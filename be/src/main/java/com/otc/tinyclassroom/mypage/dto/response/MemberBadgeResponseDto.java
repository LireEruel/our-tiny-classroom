package com.otc.tinyclassroom.mypage.dto.response;

public record MemberBadgeResponseDto(Long badgeId, String badgeUrl) {
    public static MemberBadgeResponseDto of(Long badgeId, String badgeUrl) {
        return new MemberBadgeResponseDto(badgeId, badgeUrl);
    }
}
