package com.otc.tinyclassroom.chat.dto.request;

/**
 * 채팅 메세지를 전달받는 dto.
 */
public record ChatMessageCreateRequestDto(String senderId, String roomId, String message) {

    public static ChatMessageCreateRequestDto of(String senderId, String roomId, String message) {
        return new ChatMessageCreateRequestDto(senderId, roomId, message);
    }
}
