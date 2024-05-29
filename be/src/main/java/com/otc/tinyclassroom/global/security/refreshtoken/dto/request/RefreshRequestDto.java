package com.otc.tinyclassroom.global.security.refreshtoken.dto.request;

/**
 * Refresh 요청을 수행하기 위한 RequestDto.
 */
public record RefreshRequestDto(String refreshToken, String accessToken) {
    public static RefreshRequestDto of(String refreshToken, String accessToken) {
        return new RefreshRequestDto(refreshToken, accessToken);
    }
}
