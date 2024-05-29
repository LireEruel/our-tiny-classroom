package com.otc.tinyclassroom.community.dto.response;

import com.otc.tinyclassroom.community.dto.ArticleWithCommentDto;
import com.otc.tinyclassroom.community.entity.type.ArticleType;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 게시글 상세 보기시 댓글과 게시글을 함께 넘겨주기 위한 DTO.
 */
public record ArticleWithCommentsResponseDto(Long id, String title, String content, String name, int hit, Set<ArticleCommentResponseDto> articleComments,
                                             LocalDateTime createdAt, LocalDateTime modifiedAt, ArticleType articleType) {

    public static ArticleWithCommentsResponseDto of(Long id, String title, String content, String name, int hit, Set<ArticleCommentResponseDto> articleComments, LocalDateTime createdAt,
        LocalDateTime modifiedAt, ArticleType articleType) {
        return new ArticleWithCommentsResponseDto(id, title, content, name, hit, articleComments, createdAt, modifiedAt, articleType);
    }

    /**
     * 게시글과 댓글을 반환하는 Dto.
     */
    public static ArticleWithCommentsResponseDto from(ArticleWithCommentDto dto) {
        return new ArticleWithCommentsResponseDto(
            dto.id(),
            dto.title(),
            dto.content(),
            dto.member().name(),
            dto.hit(),
            dto.articleComments().stream()
                .map(ArticleCommentResponseDto::from)
                .collect(Collectors.toCollection(LinkedHashSet::new)),
            dto.createdAt(),
            dto.modifiedAt(),
            dto.articleType()
        );
    }
}
