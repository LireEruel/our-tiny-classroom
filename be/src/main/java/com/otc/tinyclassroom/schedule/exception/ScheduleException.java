package com.otc.tinyclassroom.schedule.exception;

import lombok.Getter;

/**
 * 스케줄 관련 exception.
 */
@Getter
public class ScheduleException extends RuntimeException {
    private final ScheduleErrorCode errorCode;
    private final String message;

    public ScheduleException(ScheduleErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
