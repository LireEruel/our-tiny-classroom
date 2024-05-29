package com.otc.tinyclassroom.attendance.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 지정된 연도, 달의 출석기록을 조회하기 위한 DTO.
 */
public record MonthlyAttendanceResponseDto(List<MonthlyAttendanceResponseDto.AttendanceMonthInfo> attendanceByDate) {

    /**
     * MonthlyAttendanceResponseDto 에서 사용되는 출석 월별 정보를 담는 내부 클래스.
     */
    // Todo : Dto -> Dto 사용하여 Service 단의 코드 경량화하기.
    public record AttendanceMonthInfo(LocalDate date, LocalDateTime checkIn, LocalDateTime checkOut, int status) {

    }
}