package com.otc.tinyclassroom.mypage.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 뱃지 관련 Exception.
 */
@Getter
@AllArgsConstructor
public class BadgeException extends RuntimeException {

    private BadgeErrorCode errorCode;
    private String message;

    public BadgeException(BadgeErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
    }

}
