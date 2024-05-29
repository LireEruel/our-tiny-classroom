package com.otc.tinyclassroom.mypage.controller;

import com.otc.tinyclassroom.global.common.model.response.BaseResponse;
import com.otc.tinyclassroom.mypage.dto.request.GoalRequestDto;
import com.otc.tinyclassroom.mypage.dto.response.GoalResponseDto;
import com.otc.tinyclassroom.mypage.service.GoalService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 한 주 목표 컨트롤러.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/goal")
public class GoalController {

    private final GoalService goalService;

    /**
     * 이번주 목표 등록 혹은 수정.
     *
     * @param dto 목표 내용
     */
    @Operation(summary = "금주 목표 저장", description = "이번주의 목표를 저장합니다.", tags = { "금주 목표" })
    @PostMapping("")
    public BaseResponse<Void> saveGoal(@RequestBody GoalRequestDto dto) {
        goalService.saveGoal(dto.content());
        return BaseResponse.success(HttpStatus.OK.value(), "이번주 목표가 변경되었습니다.", null);
    }

    /**
     * 달성률 수정.
     *
     * @param goalId 목표 아이디
     * @param achievement 달성률
     */
    @Operation(summary = "금주 목표 달성률 수정", description = "이번주의 목표 달성률을 수정합니다.", tags = { "금주 목표" })
    @PostMapping("/achievement/{goalId}")
    public BaseResponse<Void> updateAchievement(@PathVariable("goalId") Long goalId,  @RequestBody int achievement) {
        goalService.updateGoalAchievement(goalId, achievement);
        return BaseResponse.success(HttpStatus.OK.value(), "목표 달성률 변경 완료!", null);
    }

    /**
     * 지난 목표 조회하기.
     */
    @Operation(summary = "지난 목표 조회", description = "지난 목표와 달성률을 조회합니다.", tags = { "금주 목표" })
    @GetMapping("/past-goal")
    public BaseResponse<GoalResponseDto> getPastGoal(@RequestParam int year, @RequestParam int week) {
        return BaseResponse.success(HttpStatus.OK.value(), "지난 목표 조회 완료", goalService.getPastGoals(year, week));
    }
}
