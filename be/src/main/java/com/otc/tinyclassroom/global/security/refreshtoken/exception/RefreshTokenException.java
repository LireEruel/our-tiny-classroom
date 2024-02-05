package com.otc.tinyclassroom.global.security.refreshtoken.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Refresh Token 관련 exception.
 */
@Getter
@AllArgsConstructor
public class RefreshTokenException extends RuntimeException {

    private RefreshTokenErrorCode errorCode;
    private String message;

    /**
     * 메세지가 없는 생성자.
     */
    public RefreshTokenException(RefreshTokenErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
    }
}
