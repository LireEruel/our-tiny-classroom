package com.otc.tinyclassroom.community.repository;

import com.otc.tinyclassroom.community.entity.ArticleComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

/**
 * 댓글 Repository.
 */
@Repository
public interface ArticleCommentRepository extends JpaRepository<ArticleComment, Long> {

    @NonNull
    @Override
    <S extends ArticleComment> S save(@NonNull S entity);
}