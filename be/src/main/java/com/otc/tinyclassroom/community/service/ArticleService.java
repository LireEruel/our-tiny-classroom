package com.otc.tinyclassroom.community.service;

import com.otc.tinyclassroom.community.dto.ArticleDto;
import com.otc.tinyclassroom.community.dto.ArticleWithCommentDto;
import com.otc.tinyclassroom.community.dto.request.ArticleCreateRequestDto;
import com.otc.tinyclassroom.community.dto.request.ArticleUpdateRequestDto;
import com.otc.tinyclassroom.community.dto.response.MyPageArticleResponseDto;
import com.otc.tinyclassroom.community.dto.response.ArticleListResponseDto;
import com.otc.tinyclassroom.community.entity.Article;
import com.otc.tinyclassroom.community.entity.type.ArticleType;
import com.otc.tinyclassroom.community.entity.type.SearchType;
import com.otc.tinyclassroom.community.exception.CommunityErrorCode;
import com.otc.tinyclassroom.community.exception.CommunityException;
import com.otc.tinyclassroom.community.repository.ArticleRepository;
import com.otc.tinyclassroom.global.security.jwt.JwtProvider;
import com.otc.tinyclassroom.member.entity.ClassRoom;
import com.otc.tinyclassroom.member.entity.Member;
import com.otc.tinyclassroom.member.repository.ClassRoomRepository;
import com.otc.tinyclassroom.member.repository.MemberRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 게시글 service.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;
    private final ClassRoomRepository classRoomRepository;
    private final JwtProvider jwtProvider;

    /**
     * 검색을 통해 게시글을 조회한다.
     *
     * @param classRoomId   반 아이디
     * @param articleType   게시글 타입
     * @param searchType    검색타입
     * @param searchKeyword 검색어
     * @param pageable      페이지
     */
    @Transactional(readOnly = true)
    public Page<ArticleDto> searchArticles(Long classRoomId, ArticleType articleType, SearchType searchType, String searchKeyword, Pageable pageable) {
        // 검색 키워드가 비어있는 경우
        if (searchKeyword == null || searchKeyword.isBlank()) {
            return searchWithoutKeyword(classRoomId, articleType, pageable);
        }
        return searchWithKeyword(classRoomId, articleType, searchType, searchKeyword, pageable);
    }

    private Page<ArticleDto> searchWithoutKeyword(Long classRoomId, ArticleType articleType, Pageable pageable) {
        if (classRoomId == null) {
            return articleRepository.findByClassRoomIsNullAndArticleType(articleType, pageable).map(ArticleDto::from);
        }
        return articleRepository.findByClassRoom_IdAndArticleType(classRoomId, articleType, pageable).map(ArticleDto::from);
    }

    private Page<ArticleDto> searchWithKeyword(Long classRoomId, ArticleType articleType, SearchType searchType, String searchKeyword, Pageable pageable) {
        if (classRoomId == null) {
            return switch (searchType) {
                case TITLE -> articleRepository.findByClassRoomIsNullAndArticleTypeAndTitleContaining(articleType, searchKeyword, pageable).map(ArticleDto::from);
                case CONTENT -> articleRepository.findByClassRoomIsNullAndArticleTypeAndContentContaining(articleType, searchKeyword, pageable).map(ArticleDto::from);
                case TITLE_CONTENT ->
                    articleRepository.findByClassRoomIsNullAndArticleTypeAndTitleContainingOrContentContaining(articleType, searchKeyword, searchKeyword, pageable).map(ArticleDto::from);
                case NAME -> articleRepository.findByClassRoomIsNullAndArticleTypeAndMember_NameContaining(articleType, searchKeyword, pageable).map(ArticleDto::from);
            };
        }
        return switch (searchType) {
            case TITLE -> articleRepository.findByClassRoom_IdAndArticleTypeAndTitleContaining(classRoomId, articleType, searchKeyword, pageable).map(ArticleDto::from);
            case CONTENT -> articleRepository.findByClassRoom_IdAndArticleTypeAndContentContaining(classRoomId, articleType, searchKeyword, pageable).map(ArticleDto::from);
            case TITLE_CONTENT ->
                articleRepository.findByClassRoom_IdAndArticleTypeAndTitleContainingOrContentContaining(classRoomId, articleType, searchKeyword, searchKeyword, pageable).map(ArticleDto::from);
            case NAME -> articleRepository.findByClassRoom_IdAndArticleTypeAndMember_Name(classRoomId, articleType, searchKeyword, pageable).map(ArticleDto::from);
        };
    }

    /**
     * 내가 쓴 글을 조회한다.(페이징)
     *
     * @param pageNumber 페이징 시작 쪽수
     * @param pageSize 페이징 사이즈
     * @return  제목, 내용, 작성자.
     */
    public List<MyPageArticleResponseDto> searchMyArticles(int pageNumber, int pageSize) {
        Long currentMemberId = jwtProvider.getCurrentMemberId();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("createdAt").descending());

        // Paging하여 "createdAt" 순으로 5개 리턴
        Page<Article> articles = articleRepository.findByMemberId(currentMemberId, pageable);

        return articles.getContent().stream()
            .map(article -> MyPageArticleResponseDto.of(article.getId(), article.getTitle()))
            .collect(Collectors.toList());
    }

    /**
     * 게시글 상세조회하기.
     *
     * @param articleId 게시글 Id
     * @return 댓글을 포함한 게시글 상세정보
     */
    public ArticleWithCommentDto getArticle(Long articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow(
            () -> new CommunityException(CommunityErrorCode.ARTICLE_NOT_FOUND)
        );
        article.increaseHit();
        return ArticleWithCommentDto.from(article);
    }

    /**
     * 게시글은 생성한다.
     *
     * @param memberId 회원 아이디 (Long)
     * @param request  게시글 작성 정보
     */
    public void createArticle(Long memberId, ArticleCreateRequestDto request) {
        // member id 찾기
        Member member = memberRepository.findById(memberId).orElseThrow(
            () -> new CommunityException(CommunityErrorCode.MEMBER_NOT_FOUND)
        );
        // classRoom id 찾기, 존재하지 않는 경우 null 처리
        ClassRoom classRoom = Optional.ofNullable(request.classRoomId())
            .flatMap(classRoomRepository::findById)
            .orElse(null);
        Article article = Article.of(member, classRoom, request.title(), request.content(), ArticleType.fromString(request.articleType()));
        articleRepository.save(article);
    }

    /**
     * 게시글 삭제한다.
     *
     * @param articleId 게시글 아이디
     */
    public void deleteArticle(Long articleId) {
        articleRepository.deleteById(articleId);
    }

    /**
     * 게시글 수정정보를 통해 수정한다.
     *
     * @param articleId 게시글 아이디
     * @param request   게시글 수정정보
     */
    public void updateArticle(Long articleId, ArticleUpdateRequestDto request) {
        Article article = articleRepository.findById(articleId).orElseThrow(
            () -> new CommunityException(CommunityErrorCode.ARTICLE_NOT_FOUND)
        );
        if (request.title() != null && !(request.title().isBlank())) {
            article.setTitle(request.title());
        }
        if (request.content() != null && !(request.content().isBlank())) {
            article.setContent(request.content());
        }
    }

    /**
     * 게시글 아이디로부터 작성자의 Id(Long)을 가져온다.
     *
     * @param articleId 게시글 아이디
     * @return 작성자 아이디 (Long)
     */
    public Long getArticleUserId(Long articleId) {
        Article article = articleRepository.findById(articleId)
            .orElseThrow(() -> new CommunityException(CommunityErrorCode.ARTICLE_NOT_FOUND));

        return Optional.ofNullable(article.getMember())
            .map(Member::getId)
            .orElseThrow(() -> new CommunityException(CommunityErrorCode.MEMBER_NOT_FOUND));
    }

    /**
     * 게시글 아이디와 현재 유저 아이디를 비교하여 권환을 확인한다.
     *
     * @param articleId 게시글 아이디
     */
    public void validateAuthority(Long memberId, Long articleId) {
        Long articleUserId = getArticleUserId(articleId);
        if (!memberId.equals(articleUserId)) {
            throw new CommunityException(CommunityErrorCode.HAVE_NO_AUTHORITY);
        }
    }

    /**
     * 현재 로그인 중인 사용자의 아이디를 반환한다.
     *
     * @return 사용자 아이디
     */
    public Long getCurrentMemberId() {
        return jwtProvider.getCurrentMemberId();
    }

    /**
     * 전체 게시판 목록을 불러온다.
     */
    public List<ArticleListResponseDto> getArticleList() {
        List<Article> articles = articleRepository.findAll();
        return articles.stream()
            .map(ArticleListResponseDto::from)
            .collect(Collectors.toList());
    }

}
