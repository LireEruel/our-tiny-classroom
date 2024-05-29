package com.otc.tinyclassroom.mypage.dto.response;

/**
 * 같은반 친구 조회 Dto.
 */
public record ClassRoomMembersResponseDto(String name, String profileImage) {
    public static ClassRoomMembersResponseDto of(String name, String profileImage) {
        return new ClassRoomMembersResponseDto(name, profileImage);
    }
}
