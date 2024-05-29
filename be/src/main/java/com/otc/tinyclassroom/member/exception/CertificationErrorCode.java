package com.otc.tinyclassroom.member.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 등업 에러 코드.
 */
@Getter
@AllArgsConstructor
public enum CertificationErrorCode {
    NO_USER_FOUND(HttpStatus.BAD_REQUEST, "유저를 찾을 수 없습니다."),
    NO_BELONG(HttpStatus.BAD_REQUEST, "소속을 입력해야 합니다."),
    NOT_SCHOOL_TYPE(HttpStatus.BAD_REQUEST, "유효한 학교 값이 아닙니다."),
    NO_ARTICLE(HttpStatus.BAD_REQUEST, "게시글이 존재하지 않습니다.");
    private final HttpStatus httpStatus;
    private final String message;
}
