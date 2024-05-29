package com.otc.tinyclassroom.community.dto;

import com.otc.tinyclassroom.community.entity.ArticleComment;
import com.otc.tinyclassroom.member.dto.MemberDto;
import java.time.LocalDateTime;

/**
 * 댓글 DTO. DTO for {@link com.otc.tinyclassroom.community.entity.ArticleComment}.
 */
public record ArticleCommentDto(Long id, MemberDto member, ArticleDto article, String content, LocalDateTime createdAt, LocalDateTime modifiedAt) {

    public static ArticleCommentDto of(Long id, MemberDto member, ArticleDto article, String content, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        return new ArticleCommentDto(id, member, article, content, createdAt, modifiedAt);
    }

    /**
     * Article Comment DTO로 변환하는 메소드.
     */
    public static ArticleCommentDto from(ArticleComment entity) {
        return new ArticleCommentDto(
            entity.getId(),
            MemberDto.from(entity.getMember()),
            ArticleDto.from(entity.getArticle()),
            entity.getContent(),
            entity.getCreatedAt(),
            entity.getModifiedAt()
        );
    }

}