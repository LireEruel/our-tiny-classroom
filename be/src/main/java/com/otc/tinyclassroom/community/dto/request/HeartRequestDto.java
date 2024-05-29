package com.otc.tinyclassroom.community.dto.request;

/**
 * 좋아요 요청 Dto.
 */
public record HeartRequestDto(Long memberId) {
    public static HeartRequestDto of(Long memberId) {
        return new HeartRequestDto(memberId);
    }
}
