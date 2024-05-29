package com.otc.tinyclassroom.chat.dto.response;

import com.otc.tinyclassroom.chat.entity.type.ChatMessageType;
import java.time.LocalDateTime;

/**
 * 채팅 메세지의 response dto.
 */
public record ChatMessageResponseDto(Long chatId, String roomId, String senderId, String senderName, String message, LocalDateTime createdAt, ChatMessageType chatMessageType) {

    public static ChatMessageResponseDto of(Long chatId, String roomId, String senderId, String senderName, String message, LocalDateTime createdAt, ChatMessageType chatMessageType) {
        return new ChatMessageResponseDto(chatId, roomId, senderId, senderName, message, createdAt, chatMessageType);
    }
}
