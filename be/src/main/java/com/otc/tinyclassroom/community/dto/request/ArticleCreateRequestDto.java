package com.otc.tinyclassroom.community.dto.request;

/**
 * 게시글 작성요청을 위한 DTO.
 */
public record ArticleCreateRequestDto(Long classRoomId, String title, String content, String articleType) {

    public static ArticleCreateRequestDto of(Long classRoomId, String title, String content, String articleType) {
        return new ArticleCreateRequestDto(classRoomId, title, content, articleType);
    }
}
