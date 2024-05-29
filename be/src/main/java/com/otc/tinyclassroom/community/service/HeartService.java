package com.otc.tinyclassroom.community.service;

import com.otc.tinyclassroom.community.dto.response.MyPageArticleResponseDto;
import com.otc.tinyclassroom.community.entity.Article;
import com.otc.tinyclassroom.community.entity.Heart;
import com.otc.tinyclassroom.community.exception.CommunityErrorCode;
import com.otc.tinyclassroom.community.exception.CommunityException;
import com.otc.tinyclassroom.community.repository.ArticleRepository;
import com.otc.tinyclassroom.community.repository.HeartRepository;
import com.otc.tinyclassroom.global.security.jwt.JwtProvider;
import com.otc.tinyclassroom.member.entity.Member;
import com.otc.tinyclassroom.member.exception.MemberErrorCode;
import com.otc.tinyclassroom.member.exception.MemberException;
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
 * 좋아요 서비스.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class HeartService {

    private final MemberRepository memberRepository;
    private final HeartRepository heartRepository;
    private final ArticleRepository articleRepository;
    private final JwtProvider jwtProvider;

    /**
     * 좋아요가 눌려있지 않다면 추가하고, 눌려있다면 취소.
     */
    public boolean update(Long articleId) {
        Long currentMemberId = jwtProvider.getCurrentMemberId();
        Member member = memberRepository.findById(currentMemberId).orElseThrow(
            () -> new MemberException(MemberErrorCode.NOT_FOUND_MEMBER)
        );
        Article article = articleRepository.findById(articleId).orElseThrow(
            () -> new CommunityException(CommunityErrorCode.ARTICLE_NOT_FOUND)
        );

        Optional<Heart> heart = heartRepository.findByMemberIdAndArticleId(currentMemberId, articleId);
        if (heart.isEmpty()) {
            heartRepository.save(Heart.of(member, article));
            return true;
        } else {
            heartRepository.deleteById(heart.get().getId());
        }
        return false;
    }

    /**
     * 좋아요 누른 게시글을 리턴한다.
     */
    public List<MyPageArticleResponseDto> searchLikedArticles(int pageNumber, int pageSize) {
        Long currentMemberId = jwtProvider.getCurrentMemberId();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("createdAt").descending());
        // Paging 하여 "createdAt" 순으로 5개 리턴
        Page<Heart> hearts = heartRepository.findByMemberId(currentMemberId, pageable);

        return hearts.getContent().stream()
            .map(heart -> {
                Article article = heart.getArticle();
                return MyPageArticleResponseDto.of(article.getId(), article.getTitle());
            })
            .collect(Collectors.toList());
    }
}
