package com.otc.tinyclassroom.mypage.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 마이페이지 관련 exception.
 */
@Getter
@AllArgsConstructor
public class MyPageException extends RuntimeException {

    private MyPageErrorCode errorCode;
    private String message;

    public MyPageException(MyPageErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
    }

}
