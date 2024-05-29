package com.otc.tinyclassroom.member.dto.request;

import com.otc.tinyclassroom.member.entity.Role;

/**
 * 수정시 멤버 정보 받을 request Dto.
 */
public record MemberUpdateRequestDto(String name, String email, Role role) {

    public static MemberUpdateRequestDto of(String name, String email, Role role) {
        return new MemberUpdateRequestDto(name, email, role);
    }
}
