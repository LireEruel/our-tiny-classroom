package com.otc.tinyclassroom.lecture.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Lecture 에러 코드.
 */
@Getter
@AllArgsConstructor
public enum LectureErrorCode {
    NO_TEACHER_OR_MENTOR(HttpStatus.BAD_REQUEST, "강사 혹은 선생님이 아닙니다."),
    NO_AUTHORITY(HttpStatus.BAD_REQUEST, "권한이 없습니다!"),
    NO_SPECIAL_LECTURE(HttpStatus.BAD_REQUEST, "해당 강의는 특강이 아닙니다"),
    NOT_FOUND_LECTURE(HttpStatus.BAD_REQUEST, "강의를 찾을 수 없습니다."),
    // DailyCo에 잘못된 Error 보낼 때
    INVALID_REQUEST_ERROR(HttpStatus.BAD_REQUEST, "서버 에러");

    private final HttpStatus httpStatus;
    private final String message;
}
