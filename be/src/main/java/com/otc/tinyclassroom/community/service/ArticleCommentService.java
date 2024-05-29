package com.otc.tinyclassroom.community.service;

import com.otc.tinyclassroom.community.dto.request.ArticleCommentRequestDto;
import com.otc.tinyclassroom.community.dto.request.ArticleCommentUpdateRequestDto;
import com.otc.tinyclassroom.community.entity.Article;
import com.otc.tinyclassroom.community.entity.ArticleComment;
import com.otc.tinyclassroom.community.exception.CommunityErrorCode;
import com.otc.tinyclassroom.community.exception.CommunityException;
import com.otc.tinyclassroom.community.repository.ArticleCommentRepository;
import com.otc.tinyclassroom.community.repository.ArticleRepository;
import com.otc.tinyclassroom.global.security.jwt.JwtProvider;
import com.otc.tinyclassroom.member.entity.Member;
import com.otc.tinyclassroom.member.repository.MemberRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 댓글 Service.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class ArticleCommentService {

    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;
    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;


    /**
     * 댓글을 작성한다.
     *
     * @param memberId 회원 아이디 (Long)
     * @param request  댓글 작성정보
     * @return 댓글 아이디
     */
    public Long createArticleComment(Long memberId, ArticleCommentRequestDto request) {
        //멤버 찾기
        Member member = memberRepository.findById(memberId).orElseThrow(
            () -> new CommunityException(CommunityErrorCode.MEMBER_NOT_FOUND)
        );
        // 게시글 찾기
        Article article = articleRepository.findById(request.articleId()).orElseThrow(
            () -> new CommunityException(CommunityErrorCode.ARTICLE_NOT_FOUND)
        );
        ArticleComment articleComment = ArticleComment.of(member, article, request.content());
        return articleCommentRepository.save(articleComment).getId();
    }

    /**
     * 댓글을 수정한다.
     *
     * @param articleCommentId 댓글 아이디
     * @param request          댓글 수정정보
     */
    public void updateArticleComment(Long articleCommentId, ArticleCommentUpdateRequestDto request) {
        ArticleComment articleComment = articleCommentRepository.findById(articleCommentId).orElseThrow(
            () -> new CommunityException(CommunityErrorCode.ARTICLE_COMMENT_NOT_FOUND)
        );
        if (request.content() != null && !(request.content().isBlank())) {
            articleComment.setContent(request.content());
        }
    }

    /**
     * 댓글을 삭제한다.
     *
     * @param articleCommentId 댓글 아이디
     */
    public void deleteArticleComment(Long articleCommentId) {
        articleCommentRepository.deleteById(articleCommentId);
    }


    /**
     * 댓글 작성자의 아이디를 가져온다.
     *
     * @param articleCommentId 댓글 아이디
     * @return 댓글 작성자 아이디(Long)
     */
    public Long getMemberIdFromArticleCommentId(Long articleCommentId) {
        ArticleComment articleComment = articleCommentRepository.findById(articleCommentId)
            .orElseThrow(() -> new CommunityException(CommunityErrorCode.ARTICLE_NOT_FOUND));

        return Optional.ofNullable(articleComment.getMember())
            .map(Member::getId)
            .orElseThrow(() -> new CommunityException(CommunityErrorCode.MEMBER_NOT_FOUND));
    }

    /**
     * 댓글 수정/삭제에 대한 권한이 있는지 확인한다.`
     *
     * @param articleCommentId 댓글 아이디
     */
    public void validateAuthority(Long memberId, Long articleCommentId) {
        Long articleCommentMemberId = getMemberIdFromArticleCommentId(articleCommentId);
        if (!memberId.equals(articleCommentMemberId)) {
            throw new CommunityException(CommunityErrorCode.HAVE_NO_AUTHORITY);
        }
    }

    /**
     * 현재 사용자의 아이디를 반환한다.
     *
     * @return 사용자 아이디
     */
    public Long getCurrentMemberId() {
        return jwtProvider.getCurrentMemberId();
    }
}
