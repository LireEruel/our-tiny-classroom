package com.otc.tinyclassroom.mypage.dto.response;

/**
 * 목표를 리턴하는 Dto.
 */
public record GoalResponseDto(String content, int achievement, int year, int week) {

    public static GoalResponseDto of(String content, int achievement, int year, int week) {
        return new GoalResponseDto(content, achievement, year, week);
    }
}
