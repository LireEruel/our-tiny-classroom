package com.otc.tinyclassroom.schedule.controller;

import com.otc.tinyclassroom.global.common.model.response.BaseResponse;
import com.otc.tinyclassroom.schedule.dto.request.ScheduleInsertRequestDto;
import com.otc.tinyclassroom.schedule.dto.response.ScheduleListResponseDto;
import com.otc.tinyclassroom.schedule.dto.response.ScheduleUrlResponseDto;
import com.otc.tinyclassroom.schedule.service.ScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * 스케줄 컨트롤러.
 */
@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    /**
     * 스케줄을 조회하여 해당 주의 스케줄 리스트 반환하는 메서드.
     */
    @Operation(summary = "특정 주 시간표 조회", description = "특정 주의 시간표를 조회합니다.", tags = { "시간표" })
    @GetMapping("/{memberId}")
    public BaseResponse<?> findScheduleList(@PathVariable("memberId") String memberId, @RequestParam("scheduleDate") LocalDate scheduleDate) {
        ScheduleListResponseDto scheduleList = scheduleService.getScheduleList(memberId, scheduleDate);

        return BaseResponse.success(HttpStatus.OK.value(), "스케줄 테이블이 반환되었습니다.", scheduleList);
    }

    /**
     * 스케줄 삽입 메서드.
     */
    @Operation(summary = "시간표 생성", description = "시간표를 만듭니다.", tags = { "시간표" })
    @PostMapping("/insert")
    public BaseResponse<?> insertSchedule(@RequestBody ScheduleInsertRequestDto requestDto) {

        scheduleService.insertSchedule(requestDto);
        return BaseResponse.success(HttpStatus.CREATED.value(), "스케줄이 추가되었습니다.", null);
    }

    /**
     * 스케줄 삭제 메서드.
     */
    @Operation(summary = "시간표 삭제", description = "시간표를 삭제합니다.", tags = { "시간표" })
    @DeleteMapping("/delete/{id}")
    public BaseResponse<?> deleteSchedule(@PathVariable("id") Long id) {
        scheduleService.deleteSchedule(id);

        return BaseResponse.success(HttpStatus.OK.value(), "스케줄이 삭제되었습니다.", null);
    }

    /**
     * 현재 참여해야 할 강의 정보 반환 메서드.
     */
    @GetMapping("/detail/current")
    public BaseResponse<?> getCurrentSchedule() {

        ScheduleUrlResponseDto currentSchedule = scheduleService.getCurrentScheduleWithUrl();

        return BaseResponse.success(HttpStatus.OK.value(), "현재 강의 정보가 반환되었습니다.", currentSchedule);
    }
}
