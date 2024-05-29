package com.otc.tinyclassroom.chat.dto;

import com.otc.tinyclassroom.chat.entity.ChatMessage;
import com.otc.tinyclassroom.chat.entity.type.ChatMessageType;
import com.otc.tinyclassroom.member.dto.MemberDto;

/**
 * 메세지 Dto.
 */
public record ChatMessageDto(ChatRoomDto chatRoomDto, MemberDto memberDto, String message,
                             ChatMessageType chatMessageType) {

    public static ChatMessageDto of(ChatRoomDto chatRoomDto, MemberDto memberDto, String message, ChatMessageType chatMessageType) {
        return new ChatMessageDto(chatRoomDto, memberDto, message, chatMessageType);
    }

    /**
     * entity -> dto.
     */
    public static ChatMessageDto from(ChatMessage entity) {
        return new ChatMessageDto(
                ChatRoomDto.from(entity.getChatRoom()),
                MemberDto.from(entity.getMember()),
                entity.getMessage(),
                entity.getChatMessageType()
        );
    }

    /**
     * dto -> entity.
     */
    public static ChatMessage toEntity(ChatMessageDto dto) {
        return ChatMessage.of(
                ChatRoomDto.toEntity(dto.chatRoomDto),
                MemberDto.toEntity(dto.memberDto),
                dto.message,
                dto.chatMessageType
        );
    }
}
