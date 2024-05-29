package com.otc.tinyclassroom.community.dto.request;

/**
 * 댓글 수정요청을 위한 DTO.
 */
public record ArticleCommentUpdateRequestDto(String content) {

    public static ArticleCommentUpdateRequestDto of(String content) {
        return new ArticleCommentUpdateRequestDto(content);
    }
}
