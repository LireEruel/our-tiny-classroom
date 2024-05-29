package com.otc.tinyclassroom.lecture.controller;

import com.otc.tinyclassroom.global.common.model.response.BaseResponse;
import com.otc.tinyclassroom.lecture.dto.request.LectureCreateUpdateRequestDto;
import com.otc.tinyclassroom.lecture.dto.response.LectureResponseDto;
import com.otc.tinyclassroom.lecture.entity.type.LectureApprovalStatusType;
import com.otc.tinyclassroom.lecture.entity.type.LectureCategoryType;
import com.otc.tinyclassroom.lecture.entity.type.LectureType;
import com.otc.tinyclassroom.lecture.service.LectureService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Lecture 컨트롤러 정의.
 */
@RequestMapping("/api/lectures")
@RestController
@RequiredArgsConstructor
public class LectureController {

    private final LectureService lectureService;

    /**
     * 강의 생성.
     *
     * @param request 입력받은 강의 정보
     * @return 생성한 강의 정보
     */
    @Operation(summary = "강의 생성", description = "강의를 생성합니다.", tags = { "강의" })
    @PostMapping("")
    public BaseResponse<LectureResponseDto> createLecture(@Valid @RequestBody LectureCreateUpdateRequestDto request) {
        LectureResponseDto createdLecture = lectureService.createLecture(request);
        return BaseResponse.success(HttpStatus.OK.value(), "강의 생성에 성공하였습니다.", createdLecture);
    }

    /**
     * 강의 조회(필터).
     *
     * @return 조회한 강의들.
     */
    @Operation(summary = "강의 조회", description = "필터에 맞게 강의를 조회합니다.", tags = { "강의" })
    @GetMapping("")
    public BaseResponse<?> getLecturesList(
        @RequestParam(required = false) Long memberId,
        @RequestParam(required = false) String title,
        @RequestParam(required = false) String description,
        @RequestParam(required = false) String dayOfWeek,
        @RequestParam(required = false) String timeTable,
        @RequestParam(required = false) LectureType lectureType,
        @RequestParam(required = false) LectureCategoryType lectureCategoryType,
        @RequestParam(required = false) LectureApprovalStatusType approved,
        @RequestParam(required = false) LocalDate date
    ) {
        List<LectureResponseDto> lectures;

        // 필터된 게시물 검색
        if (memberId != null || title != null || description != null || dayOfWeek != null || timeTable != null || lectureType != null || lectureCategoryType != null || approved != null) {
            lectures = lectureService.getFilteredLectureList(memberId, title, description, dayOfWeek, timeTable,
                lectureType, lectureCategoryType, approved, date);

            // 필터 없을 경우 전체 검색
        } else {
            lectures = lectureService.getLectureList();
        }

        return BaseResponse.success(HttpStatus.OK.value(), "강의 조회에 성공하였습니다.", lectures);
    }

    /**
     * 강의 하나 자세히 조회하기.
     *
     * @param lectureId 조회할 강의 Id
     * @return 조회한 강의의 정보
     */
    @Operation(summary = "강의 상세 조회", description = "강의를 상세조회합니다.", tags = { "강의" })
    @GetMapping("/{lectureId}")
    public BaseResponse<LectureResponseDto> getLecture(@PathVariable Long lectureId) {
        LectureResponseDto lecture = lectureService.getLecture(lectureId);
        return BaseResponse.success(HttpStatus.OK.value(), "강의를 조회하였습니다.", lecture);
    }


    /**
     * 강의 수정하기.
     *
     * @param lectureId 수정할 강의Id
     * @param request   수정될 강의 정보
     * @return 수정한 후 강의 정보
     */
    @Operation(summary = "강의 수정", description = "강의를 수정합니다.", tags = { "강의" })
    @PatchMapping("/{lectureId}")
    public BaseResponse<LectureResponseDto> updateLecture(@PathVariable Long lectureId,
        @RequestBody LectureCreateUpdateRequestDto request) {
        LectureResponseDto updatedLecture = lectureService.updateLecture(lectureId, request);
        return BaseResponse.success(HttpStatus.OK.value(), "강의 수정에 성공하였습니다.", updatedLecture);
    }

    /**
     * 강의 삭제하기.
     *
     * @param lectureId 삭제할 강의Id
     */
    @Operation(summary = "강의 삭제", description = "강의를 삭제합니다.", tags = { "강의" })
    @DeleteMapping("/{lectureId}")
    public BaseResponse<Void> deleteLecture(@PathVariable Long lectureId) {
        lectureService.deleteLecture(lectureId);
        return BaseResponse.success(HttpStatus.OK.value(), "강의 삭제를 성공하였습니다.", null);
    }

    // 특강 기능 추가
    @Operation(summary = "특강 추가", description = "특별 강의를 추가합니다.", tags = { "강의" })
    @PatchMapping("/{lectureId}/approve")
    public BaseResponse<Void> approveSpecialLecture(@PathVariable Long lectureId) {
        lectureService.approveSpecialLecture(lectureId);
        return BaseResponse.success(HttpStatus.OK.value(), "특강을 승인하였습니다.", null);
    }

    @Operation(summary = "특강 거절", description = "특별 강의 요청을 거절합니다.", tags = { "강의" })
    @PatchMapping("{lectureId}/reject")
    public BaseResponse<Void> rejectSpecialLecture(@PathVariable Long lectureId) {
        lectureService.rejectSpecialLecture(lectureId);
        return BaseResponse.success(HttpStatus.OK.value(), "특강을 거절하였습니다.", null);
    }
}
