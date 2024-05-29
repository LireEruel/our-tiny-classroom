package com.otc.tinyclassroom.global.security.refreshtoken.dto.response;

/**
 * Refresh Token 을 body 에 담아주기 위한 DTO.
 */
public record RefreshResponseDto(String refreshToken) {
    public static RefreshResponseDto of(String refreshToken) {
        return new RefreshResponseDto(refreshToken);
    }
}
