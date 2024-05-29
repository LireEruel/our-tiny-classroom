package com.otc.tinyclassroom.community.dto.response;

/**
 * 좋아요 누른 글 반환 Dto.
 */
public record MyPageArticleResponseDto(Long articleId, String title) {

    public static MyPageArticleResponseDto of(Long articleId, String title) {
        return new MyPageArticleResponseDto(articleId, title);
    }
}
