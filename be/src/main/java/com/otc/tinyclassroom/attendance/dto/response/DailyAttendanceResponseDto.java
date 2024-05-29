package com.otc.tinyclassroom.attendance.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 오늘 하루 출석 기록 반환을 위한 DTO.
 */
public record DailyAttendanceResponseDto(DailyAttendanceResponseDto.AttendanceDayInfo attendanceOnDate) {

    /**
     * DailyAttendanceResponseDto 에서 사용되는 오늘 출석 정보를 담는 내부 클래스.
     */
    // Todo : Dto -> Dto 사용하여 Service 단의 코드 경량화하기.
    public record AttendanceDayInfo(LocalDate date, LocalDateTime checkIn, LocalDateTime checkOut, int status) {

    }
}
