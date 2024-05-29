package com.otc.tinyclassroom.mypage.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 뱃지관련 에러코드.
 */
@Getter
@AllArgsConstructor
public enum BadgeErrorCode {
    NOT_FOUND_BADGE(HttpStatus.BAD_REQUEST, "뱃지가 존재하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}