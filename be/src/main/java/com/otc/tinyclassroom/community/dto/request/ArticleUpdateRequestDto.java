package com.otc.tinyclassroom.community.dto.request;

/**
 * 게시글 수정 요청을 위한 DTO.
 */
public record ArticleUpdateRequestDto(String title, String content) {

    public static ArticleUpdateRequestDto of(String title, String content) {
        return new ArticleUpdateRequestDto(title, content);
    }
}
