package com.otc.tinyclassroom.member.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 반 편성 관련 exception.
 */
@Getter
@AllArgsConstructor
public class ClassAssignmentException extends RuntimeException {

    private ClassAssignmentErrorCode errorCode;
    private String message;

    /**
     * 메세지가 없는 생성자.
     */
    public ClassAssignmentException(ClassAssignmentErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
    }
}

