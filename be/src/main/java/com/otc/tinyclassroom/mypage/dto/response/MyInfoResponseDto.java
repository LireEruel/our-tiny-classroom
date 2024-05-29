package com.otc.tinyclassroom.mypage.dto.response;

/**
 * 마이페이지에서 본인의 정보를 가져올때 사용하는 Dto.
 */
public record MyInfoResponseDto(String memberId, String name, String email) {

    /**
     * 나의 정보 수정시 팩토리 메서드.
     */
    public static MyInfoResponseDto of(String memberId, String name, String email) {
        return new MyInfoResponseDto(memberId, name, email);
    }
}