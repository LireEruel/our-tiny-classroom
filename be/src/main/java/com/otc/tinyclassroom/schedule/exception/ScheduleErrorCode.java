package com.otc.tinyclassroom.schedule.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * 스케줄 에러 코드.
 */
@Getter
@RequiredArgsConstructor
public enum ScheduleErrorCode {
    NOT_EXIST_MEMBER(HttpStatus.BAD_REQUEST, "존재하지 않는 사용자입니다."),
    NOT_EXIST_SCHEDULE(HttpStatus.BAD_REQUEST, "존재하지 않는 스케줄입니다."),
    NOT_EXIST_LECTURE(HttpStatus.BAD_REQUEST, "존재하지 않는 강의입니다."),
    NOT_ACCEPT_LECTURE(HttpStatus.BAD_REQUEST, "승인되지 않은 강의입니다."),
    NOT_ACCURATE_LECTURE(HttpStatus.BAD_REQUEST, "부정확한 날짜 혹은 교시입니다."),
    NOT_EXIST_TEACHER(HttpStatus.BAD_REQUEST, "해당 학생의 반에 선생님이 존재하지 않습니다."),
    NO_AUTH_SCHEDULE_INSERT(HttpStatus.FORBIDDEN, "스케줄 추가 권한이 없습니다."),
    NO_AUTH_SCHEDULE_DELETE(HttpStatus.FORBIDDEN, "스케줄 삭제 권한이 없습니다."),
    CLASSROOM_NOT_ASSIGNED(HttpStatus.BAD_REQUEST, "아직 할당된 반이 없습니다."),
    ALREADY_EXISTED_SCHEDULE(HttpStatus.BAD_REQUEST, "이미 해당 시간에 등록된 강의가 있습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
