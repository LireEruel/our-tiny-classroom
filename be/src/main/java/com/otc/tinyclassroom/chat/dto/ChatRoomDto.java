package com.otc.tinyclassroom.chat.dto;

import com.otc.tinyclassroom.chat.entity.ChatRoom;

/**
 * 채팅룸 Dto.
 */
public record ChatRoomDto(String roomId, Long lastChatId, String lastMessage) {

    public static ChatRoomDto of(String roomId, Long lastChatId, String lastMessage) {
        return new ChatRoomDto(roomId, lastChatId, lastMessage);
    }

    /**
     * entity -> dto.
     */
    public static ChatRoomDto from(ChatRoom entity) {
        return new ChatRoomDto(
                entity.getId(),
                entity.getLastChatMessage().getId(),
                entity.getLastChatMessage().getMessage()
        );
    }

    /**
     * dto -> entity.
     */
    public static ChatRoom toEntity(ChatRoomDto dto) {
        return ChatRoom.of();
    }
}
