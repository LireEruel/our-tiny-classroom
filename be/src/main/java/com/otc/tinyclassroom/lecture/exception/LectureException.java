package com.otc.tinyclassroom.lecture.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Lecture 관련 exception.
 */
@Getter
@AllArgsConstructor
public class LectureException extends RuntimeException {

    private LectureErrorCode errorCode;
    private String message;

    /**
     * 메세지가 없는 생성자.
     */
    public LectureException(LectureErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
    }
}
