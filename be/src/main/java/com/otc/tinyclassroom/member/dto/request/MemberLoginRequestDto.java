package com.otc.tinyclassroom.member.dto.request;

/**
 * Login 시 필요한 Dto.
 */
public record MemberLoginRequestDto(String memberId, String password) {

    /**
     * 팩토리 메서드.
     */
    public static MemberLoginRequestDto of(String memberId, String password) {
        return new MemberLoginRequestDto(memberId, password);
    }
}
