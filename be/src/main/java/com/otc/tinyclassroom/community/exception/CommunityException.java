package com.otc.tinyclassroom.community.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 교실 기능관련 예외처리 클래스.
 */
@Getter
@AllArgsConstructor
public class CommunityException extends RuntimeException {
    private CommunityErrorCode errorCode;
    private String message;

    /**
     * 메세지가 없는 생성자.
     */
    public CommunityException(CommunityErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
    }
}
