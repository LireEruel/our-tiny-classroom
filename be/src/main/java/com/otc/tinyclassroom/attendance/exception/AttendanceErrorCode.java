package com.otc.tinyclassroom.attendance.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 출석 에러 코드.
 */
@Getter
@AllArgsConstructor
public enum AttendanceErrorCode {
    ALREADY_ATTEND(HttpStatus.BAD_REQUEST, "이미 등교하였습니다."),
    NOT_CHECK_IN_TIME(HttpStatus.BAD_REQUEST, "등교 시간이 아닙니다."),
    NOT_CHECKED_IN(HttpStatus.BAD_REQUEST, "등교하지 않아서 하교할 수 없습니다."),
    NOT_CHECK_OUT_TIME(HttpStatus.BAD_REQUEST, "하교 시간이 아닙니다."),
    WEEKEND_CHECKOUT_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "주말엔 등교할 수 없습니다."),
    NO_MEMBER_ID(HttpStatus.BAD_REQUEST, "로그인 되어있지 않습니다."),
    NO_ATTENDANCE_RECORD(HttpStatus.BAD_REQUEST, "출석 기록이 없습니다.");
    private final HttpStatus httpStatus;
    private final String message;
}
