package com.otc.tinyclassroom.member.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 반 편성 에러 코드.
 */
@Getter
@AllArgsConstructor
public enum ClassAssignmentErrorCode {
    INVALID_FIELD_VALUE(HttpStatus.BAD_REQUEST, "빈 필드값이 있습니다."),
    NO_AUTHORITY(HttpStatus.BAD_REQUEST, "권한이 없습니다."),
    CLASSROOM_NOT_FOUND(HttpStatus.BAD_REQUEST, "반이 없습니다."),
    MEMBER_NOT_FOUND(HttpStatus.BAD_REQUEST, "학생이 없습니다."),
    CLASSROOM_NOT_ASSIGNED(HttpStatus.BAD_REQUEST, "아직 할당된 반이 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}