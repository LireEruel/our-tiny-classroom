package com.otc.tinyclassroom.mypage.dto.request;

public record GoalRequestDto(String content) {

    public static GoalRequestDto of(String content) {
        return new GoalRequestDto(content);
    }

}
