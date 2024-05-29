package com.otc.tinyclassroom.member.dto.response;

import java.time.LocalDate;
import java.util.List;

/**
 * 학생 등업 게시글 상세보기.
 */
public record StudentRoleUpdateDetailResponseDto(Long articleId, String memberName, LocalDate birthday, int beforeSchoolType, String quitReason, String beforeSchool,
                                                 List<String> quitConfirmationPaths) {

    public static StudentRoleUpdateDetailResponseDto of(Long articleId, String memberName, LocalDate birthday, int beforeSchoolType, String quitReason, String beforeSchool,
        List<String> quitConfirmationPaths) {
        return new StudentRoleUpdateDetailResponseDto(articleId, memberName, birthday, beforeSchoolType, quitReason, beforeSchool, quitConfirmationPaths);
    }
}

