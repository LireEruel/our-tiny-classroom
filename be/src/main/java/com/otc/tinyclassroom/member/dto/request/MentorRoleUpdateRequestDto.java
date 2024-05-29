package com.otc.tinyclassroom.member.dto.request;

/**
 * Role 변경 요청 Dto.
 */
public record MentorRoleUpdateRequestDto(String belong) {
    public static MentorRoleUpdateRequestDto of(String belong) {
        return new MentorRoleUpdateRequestDto(belong);
    }
}
