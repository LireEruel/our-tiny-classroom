package com.otc.tinyclassroom.attendance.dto.response;


import java.time.LocalDateTime;

/**
 * 등교시 결과 반환을 위한 DTO.
 */
public record AttendanceCheckInResponseDto(LocalDateTime checkInTime, int status) {

    /**
     * AttendanceCheckInResponseDto 객체를 생성하고 반환.
     */
    public static AttendanceCheckInResponseDto from(LocalDateTime checkInTime, int status) {
        return new AttendanceCheckInResponseDto(checkInTime, status);
    }
}
