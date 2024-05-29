package com.otc.tinyclassroom.member.dto.response;

import java.time.LocalDate;

/**
 * 등업 요청 리스트 Response.
 */
public record RoleUpdateListResponseDto(Long articleId, String memberName, LocalDate birthday) {

    public static RoleUpdateListResponseDto of(Long articleId, String memberName, LocalDate birthday) {
        return new RoleUpdateListResponseDto(articleId, memberName, birthday);
    }
}
