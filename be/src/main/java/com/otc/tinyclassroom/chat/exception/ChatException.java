package com.otc.tinyclassroom.chat.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 채팅 관련 Exception.
 */
@Getter
@AllArgsConstructor
public class ChatException extends RuntimeException {

    private ChatErrorCode errorCode;
    private String message;

    public ChatException(ChatErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
    }
}
