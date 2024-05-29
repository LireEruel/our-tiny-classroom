package com.otc.tinyclassroom.attendance.dto;

import com.otc.tinyclassroom.attendance.entity.Attendance;
import com.otc.tinyclassroom.attendance.entity.type.AttendanceStatus;
import com.otc.tinyclassroom.member.dto.MemberDto;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 출석 DTO.
 * DTO for {@link Attendance}.
 */
public record AttendanceDto(MemberDto member, LocalDateTime checkIn, LocalDateTime checkOut, com.otc.tinyclassroom.attendance.entity.type.AttendanceStatus status) implements Serializable {

    public static AttendanceDto of(MemberDto member, LocalDateTime checkIn, LocalDateTime checkOut, AttendanceStatus status) {
        return new AttendanceDto(member, checkIn, checkOut, status);
    }

    /**
     * Attendance 엔티티를 AttendanceDto 로 변환하는 메서드.
     */
    public static AttendanceDto from(Attendance attendance) {
        return new AttendanceDto(
            MemberDto.from(attendance.getMember()), // MemberDto 변환 메소드 호출
            attendance.getCheckIn(),
            attendance.getCheckOut(),
            attendance.getStatus()
        );
    }
}