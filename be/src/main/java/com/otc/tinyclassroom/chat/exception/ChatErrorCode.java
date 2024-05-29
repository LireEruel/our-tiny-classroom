package com.otc.tinyclassroom.chat.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 채팅 에러 코드.
 */
@Getter
@AllArgsConstructor
public enum ChatErrorCode {
    NOT_FOUND_CHATROOM(HttpStatus.NOT_FOUND, "존재하지 않는 채팅룸 번호입니다."),
    NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND, "존재하지 않는 멤버 이름입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
