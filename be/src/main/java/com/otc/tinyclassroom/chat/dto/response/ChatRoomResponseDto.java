package com.otc.tinyclassroom.chat.dto.response;

import com.otc.tinyclassroom.chat.dto.ChatRoomMemberDto;
import java.util.List;

/**
 * ChatRoomDto 리스트를 담는 request dto.
 */
public record ChatRoomResponseDto(String roomId, List<ChatRoomMemberDto> roomMemberList, Long lastChatId, String lastMessage, List<ChatMessageResponseDto> chatList) {

    public static ChatRoomResponseDto of(String roomId, List<ChatRoomMemberDto> roomMemberList, Long lastChatId, String lastMessage, List<ChatMessageResponseDto> chatList) {
        return new ChatRoomResponseDto(roomId, roomMemberList, lastChatId, lastMessage, chatList);
    }
}
