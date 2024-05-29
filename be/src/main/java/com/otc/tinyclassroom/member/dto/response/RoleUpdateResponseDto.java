package com.otc.tinyclassroom.member.dto.response;

import com.otc.tinyclassroom.member.entity.Role;

/**
 * ROLE 업데이트 후 Response Dto.
 */
public record RoleUpdateResponseDto(Long memberId, Role role) {

    public static RoleUpdateResponseDto of(Long memberId, Role role) {
        return new RoleUpdateResponseDto(memberId, role);
    }
}
