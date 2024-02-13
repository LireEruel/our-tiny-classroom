package com.otc.tinyclassroom.global.common.exception;

import com.otc.tinyclassroom.attendance.exception.AttendanceException;
import com.otc.tinyclassroom.community.exception.CommunityException;
import com.otc.tinyclassroom.global.common.model.response.BaseResponse;
import com.otc.tinyclassroom.media.exception.MediaException;
import com.otc.tinyclassroom.member.exception.MemberException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * 전체 Exception Handler.
 */
@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {

    /**
     *  Member Exception Handler.
     */
    @ExceptionHandler(MemberException.class)
    public ResponseEntity<?> applicationHandler(MemberException e) {
        log.error("Error occurs {}", e.toString());
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
            .body(BaseResponse.error(e.getErrorCode().getHttpStatus().value(), e.getMessage()));
    }

    /**
     * Attendance Exception Handler.
     */
    @ExceptionHandler(AttendanceException.class)
    public ResponseEntity<?> applicationHandler1(AttendanceException e) {
        log.error("Error occurs {}", e.toString());
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
        .body(BaseResponse.error(e.getErrorCode().getHttpStatus().value(), e.getMessage()));
    }

    /**
     *   Media Exception Handler.
     */
    @ExceptionHandler(MediaException.class)
    public ResponseEntity<?> mediaExceptionHandler(MediaException e) {
        log.error("Error occurs {}", e.toString());
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(BaseResponse.error(e.getErrorCode().getHttpStatus().value(), e.getMessage()));
    }

    /**
     * Board Exception Handler.
     */
    @ExceptionHandler(CommunityException.class)
    public ResponseEntity<?> boardExceptionHandler(CommunityException e) {
        log.error("Board Error occurs {}", e.toString());
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
            .body(BaseResponse.error(e.getErrorCode().getHttpStatus().value(), e.getMessage()));
    }

    /**
     * RuntimeException Handler.
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> boardExceptionHandler(RuntimeException e) {
        log.error("Runtime Error occurs {}", e.toString());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(BaseResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal server error!!"));
    }
}
