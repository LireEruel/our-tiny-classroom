package com.otc.tinyclassroom.mypage.dto.response;

public record AttendanceResponseDto(int attendanceRate, int attend, int lateOrLeaveFast, int absent) {
    public static AttendanceResponseDto of(int attendanceRate, int attend, int lateOrLeaveFast, int absent) {

        return new AttendanceResponseDto(attendanceRate, attend, lateOrLeaveFast, absent);
    }
}

