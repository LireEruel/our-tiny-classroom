package com.otc.tinyclassroom.mypage.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 마이페이지 관련 에러코드.
 */
@Getter
@AllArgsConstructor
public enum MyPageErrorCode {
    NO_USER_FOUND(HttpStatus.BAD_REQUEST, "유저를 찾을 수 없습니다."),
    NO_BELONG(HttpStatus.BAD_REQUEST, "소속을 입력해야 합니다."),
    NOT_SCHOOL_TYPE(HttpStatus.BAD_REQUEST, "유효한 학교 값이 아닙니다."),
    PASSWORD_VALIDATION_FAILED(HttpStatus.BAD_REQUEST, "비밀번호는 8~20자리이며 특수문자, 영어 대소문자, 숫자를 포함해야 합니다."),
    NO_ARTICLE(HttpStatus.BAD_REQUEST, "게시글이 존재하지 않습니다."),
    NO_GOAL_FOUND(HttpStatus.BAD_REQUEST, "이번주 목표가 존재하지 않습니다.");
    private final HttpStatus httpStatus;
    private final String message;
}
