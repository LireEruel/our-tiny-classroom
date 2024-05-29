package com.otc.tinyclassroom.chat.dto.request;

/**
 * 방 생성 request Dto.
 */
public record ChatRoomCreateRequestDto(String targetMemberId) {

    public static ChatRoomCreateRequestDto of(String targetMemberId) {
        return new ChatRoomCreateRequestDto(targetMemberId);
    }
}
