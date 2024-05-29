package com.otc.tinyclassroom.mypage.dto.request;

public record BadgeSaveRequestDto(String image) {

    public static BadgeSaveRequestDto of(String image) {
        return new BadgeSaveRequestDto(image);
    }
}
