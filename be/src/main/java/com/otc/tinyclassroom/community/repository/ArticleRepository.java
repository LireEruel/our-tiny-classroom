package com.otc.tinyclassroom.community.repository;

import com.otc.tinyclassroom.community.entity.Article;
import com.otc.tinyclassroom.community.entity.Heart;
import com.otc.tinyclassroom.community.entity.QArticle;
import com.otc.tinyclassroom.community.entity.type.ArticleType;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

/**
 * 게시글 관련 Repository.
 */
@Repository
public interface ArticleRepository extends JpaRepository<Article, Long>,
    QuerydslPredicateExecutor<Article>,
    QuerydslBinderCustomizer<QArticle> {

    // 전체 커뮤니티
    Page<Article> findByClassRoomIsNullAndArticleTypeAndTitleContaining(ArticleType articleType, String title, Pageable pageable);

    Page<Article> findByClassRoomIsNullAndArticleTypeAndContentContaining(ArticleType articleType, String content, Pageable pageable);

    Page<Article> findByClassRoomIsNullAndArticleTypeAndTitleContainingOrContentContaining(ArticleType articleType, String title, String content, Pageable pageable);

    Page<Article> findByClassRoomIsNullAndArticleTypeAndMember_NameContaining(ArticleType articleType, String name, Pageable pageable);

    // 기본 조회
    Page<Article> findByClassRoomIsNullAndArticleType(ArticleType articleType, Pageable pageable);

    // 반 별 게시판
    Page<Article> findByClassRoom_IdAndArticleTypeAndTitleContaining(Long classRoomId, ArticleType articleType, String title, Pageable pageable);

    Page<Article> findByClassRoom_IdAndArticleTypeAndContentContaining(Long classRoomId, ArticleType articleType, String content, Pageable pageable);

    Page<Article> findByClassRoom_IdAndArticleTypeAndTitleContainingOrContentContaining(Long classRoomId, ArticleType articleType, String title, String content, Pageable pageable);

    Page<Article> findByClassRoom_IdAndArticleTypeAndMember_Name(Long classRoomId, ArticleType articleType, String name, Pageable pageable);

    // 기본 조회
    Page<Article> findByClassRoom_IdAndArticleType(Long classRoomId, ArticleType articleType, Pageable pageable);

    /**
     * 대소문자 구분 없는 검색을 위한 QueryDSL 설정.
     */
    @Override
    default void customize(QuerydslBindings bindings, QArticle root) {
        // 특정 property에 대한 검색 열기
        bindings.excludeUnlistedProperties(true);
        bindings.including(root.title, root.content, root.member.name);
        bindings.bind(root.title).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.content).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.member.name).first(StringExpression::containsIgnoreCase);
    }

    Page<Article> findByMemberId(Long memberId, Pageable pageable);
}