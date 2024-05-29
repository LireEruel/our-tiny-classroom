package com.otc.tinyclassroom.community.dto.request;

/**
 * 댓글 작성요청을 위한 DTO.
 */
public record ArticleCommentRequestDto(Long articleId, String content) {

    public static ArticleCommentRequestDto of(Long articleId, String content) {
        return new ArticleCommentRequestDto(articleId, content);
    }
}
