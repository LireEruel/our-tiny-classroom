package com.otc.tinyclassroom.chat.dto;

/**
 * 채팅방에 속한 멤버 dto.
 */
public record ChatRoomMemberDto(Long id, String memberId, String name, String profileImage) {

    public static ChatRoomMemberDto of(Long id, String memberId, String name, String profileImage) {
        return new ChatRoomMemberDto(id, memberId, name, profileImage);
    }
}
