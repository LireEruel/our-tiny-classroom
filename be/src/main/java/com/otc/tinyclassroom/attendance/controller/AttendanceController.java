package com.otc.tinyclassroom.attendance.controller;

import com.otc.tinyclassroom.attendance.dto.response.AttendanceCheckInResponseDto;
import com.otc.tinyclassroom.attendance.dto.response.AttendanceCheckOutResponseDto;
import com.otc.tinyclassroom.attendance.dto.response.DailyAttendanceResponseDto;
import com.otc.tinyclassroom.attendance.dto.response.MonthlyAttendanceResponseDto;
import com.otc.tinyclassroom.attendance.service.AttendanceService;
import com.otc.tinyclassroom.global.common.model.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 출석 컨트롤러 정의. 멤버 등교, 하교 멤버 출석시간 확인
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/attendances")
public class AttendanceController {

    private final AttendanceService attendanceService;


    /**
     * 등교시간 기록.
     */
    @Operation(summary = "등교시간 기록", description = "체크인을 하여 등교시간이 기록됩니다.", tags = { "출석" })
    @PostMapping("")
    public BaseResponse<AttendanceCheckInResponseDto> checkIn() {
        AttendanceCheckInResponseDto result = attendanceService.checkIn();
        return BaseResponse.success(HttpStatus.OK.value(), "등교에 성공하였습니다!", result);
    }

    /**
     * 하교시간 기록.
     */
    @Operation(summary = "하교시간 기록", description = "체크아웃을 하여 하교시간이 기록됩니다.", tags = { "출석" })
    @PatchMapping("/checkout")
    public BaseResponse<AttendanceCheckOutResponseDto> checkOut() {
        AttendanceCheckOutResponseDto result = attendanceService.checkOut();
        return BaseResponse.success(HttpStatus.OK.value(), "하교에 성공하였습니다!", result);
    }

    /**
     * 오늘 출석기록 조회.
     */
    @Operation(summary = "오늘 출석기록 조회.", description = "오늘의 출석을 조회합니다.", tags = { "출석" })
    @GetMapping("/daily/{memberId}")
    public BaseResponse<DailyAttendanceResponseDto> getDailyAttendanceRecord(
        @PathVariable("memberId") String memberId
    ) {
        LocalDateTime today = LocalDateTime.now();
        DailyAttendanceResponseDto result = attendanceService.getAttendanceTimeOnDate(memberId, today);
        String message = String.format("%s의 출석 조회에 성공하였습니다.", today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        return BaseResponse.success(HttpStatus.OK.value(), message, result);
    }

    /**
     * 한달치 출석기록 조회.
     */
    @Operation(summary = "한 달 출석기록 조회.", description = "특정 달의 출석을 조회합니다.", tags = { "출석" })
    @GetMapping("/monthly/{memberId}")
    public BaseResponse<MonthlyAttendanceResponseDto> getMonthlyTotalAttendanceRecord(
        @PathVariable("memberId") String memberId,
        @RequestParam int year,
        @RequestParam int month
    ) {
        MonthlyAttendanceResponseDto result = attendanceService.getAttendanceTimeOnMonth(memberId, year, month);
        String message = String.format("%d년 %d월 출석 조회에 성공하였습니다.", year, month);
        return BaseResponse.success(HttpStatus.OK.value(), message, result);
    }
}
