package com.otc.tinyclassroom.member.dto.request;

/**
 * Role 변경 요청 DTO.
 */
public record StudentRoleUpdateRequestDto(int beforeSchoolType, String quitReason, String beforeSchool) {
    public static StudentRoleUpdateRequestDto of(int beforeSchoolType, String quitReason, String beforeSchool) {
        return new StudentRoleUpdateRequestDto(beforeSchoolType, quitReason, beforeSchool);
    }
}
