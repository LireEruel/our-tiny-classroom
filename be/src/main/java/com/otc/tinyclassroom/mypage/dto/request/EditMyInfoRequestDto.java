package com.otc.tinyclassroom.mypage.dto.request;

/**
 * 나의 정보 수정 요청 Dto.
 */
public record EditMyInfoRequestDto(String name, String beforePassword, String newPassword) {

    public static EditMyInfoRequestDto of(String name, String beforePassword, String newPassword) {
        return new EditMyInfoRequestDto(name, beforePassword, newPassword);
    }
}

