package com.otc.tinyclassroom.lecture.dto.web;

/**
 * Daily.co 응답 요청에 대한 에러처리를 위한 클래스.
 */
public record ErrorResponse(String error, String info) {
}
