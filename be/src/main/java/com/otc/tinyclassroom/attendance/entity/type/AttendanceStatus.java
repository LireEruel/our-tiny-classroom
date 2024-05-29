package com.otc.tinyclassroom.attendance.entity.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 출석시 상태 ENUM.
 * 0: 정상, 1: 지각, 2: 조퇴, 3: 결석, 4: 소명됨
 */
@Getter
@AllArgsConstructor
public enum AttendanceStatus {
    ATTENDANCE(0),
    LATE(1),
    LEAVE_EARLY(2),
    ABSENCE(3),
    LATE_WITH_EXCUSE(4);

    private final int value;
}