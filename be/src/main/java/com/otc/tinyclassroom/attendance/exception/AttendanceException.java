package com.otc.tinyclassroom.attendance.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 출석관련 exception.
 */
@Getter
@AllArgsConstructor
public class AttendanceException extends RuntimeException {

    private AttendanceErrorCode errorCode;
    private String message;

    public AttendanceException(AttendanceErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
    }
}


