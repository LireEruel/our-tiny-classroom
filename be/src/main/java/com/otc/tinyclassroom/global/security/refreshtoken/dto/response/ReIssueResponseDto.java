package com.otc.tinyclassroom.global.security.refreshtoken.dto.response;

/**
 * Access Token 재발급에 대한 응답 정보를 담은 DTO 클래스.
 */
public record ReIssueResponseDto(String refreshToken, String accessToken) {
    public static ReIssueResponseDto of(String refreshToken, String accessToken) {
        return new ReIssueResponseDto(refreshToken, accessToken);
    }
}
