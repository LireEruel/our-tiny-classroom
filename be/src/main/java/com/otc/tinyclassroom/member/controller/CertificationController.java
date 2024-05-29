package com.otc.tinyclassroom.member.controller;

import com.otc.tinyclassroom.global.common.model.response.BaseResponse;
import com.otc.tinyclassroom.member.dto.request.TempAssignRequestDto;
import com.otc.tinyclassroom.member.dto.response.MentorRoleUpdateDetailResponseDto;
import com.otc.tinyclassroom.member.dto.response.RoleUpdateListResponseDto;
import com.otc.tinyclassroom.member.dto.response.RoleUpdateResponseDto;
import com.otc.tinyclassroom.member.dto.response.StudentRoleUpdateDetailResponseDto;
import com.otc.tinyclassroom.member.entity.Role;
import com.otc.tinyclassroom.member.service.CertificationService;
import com.otc.tinyclassroom.member.service.ClassAssignmentService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ADMIN 입장에서 등업 요청을 관리하기 위한 컨트롤러.
 */
@RestController
@RequestMapping("/api/admin/members/certification")
@RequiredArgsConstructor
public class CertificationController {

    private final CertificationService certificationService;
    private final ClassAssignmentService classAssignmentService;

    /**
     * 학생 등업 리스트 조회.
     */
    @Operation(summary = "학생 등업 리스트 조회", description = "학생 등업 리스트를 조회합니다.", tags = { "등업" })
    @GetMapping("/student")
    public BaseResponse<List<RoleUpdateListResponseDto>> getStudentUpdateRequestList() {

        List<RoleUpdateListResponseDto> listResult =
            certificationService.findStudentAll().stream()
                .map(request ->
                    RoleUpdateListResponseDto.of(request.getId(), request.getMember().getName(), request.getMember().getBirthday()))
                .toList();

        return BaseResponse.success(HttpStatus.OK.value(), "학생 등업 리스트 조회성공", listResult);
    }

    /**
     * 멘토 등업 리스트 조회.
     */
    @Operation(summary = "멘토 등업 리스트 조회", description = "멘토 등업 리스트를 조회합니다.", tags = { "등업" })
    @GetMapping("/mentor")
    public BaseResponse<List<RoleUpdateListResponseDto>> getMentorUpdateRequestList() {
        List<RoleUpdateListResponseDto> listResult =
            certificationService.findMentorAll().stream()
                .map(request ->
                    RoleUpdateListResponseDto.of(request.getId(), request.getMember().getName(), request.getMember().getBirthday()))
                .toList();
        return BaseResponse.success(HttpStatus.OK.value(), "멘토 등업 리스트 조회성공", listResult);
    }

    /**
     * 학생 등업글 상세 조회.
     */
    @Operation(summary = "학생 등업글 상세 조회", description = "학생 등업요청 게시글을 상세조회합니다.", tags = { "등업" })
    @GetMapping("/student/{articleId}")
    public BaseResponse<StudentRoleUpdateDetailResponseDto> getStudentUpdateRequestDetail(@PathVariable(name = "articleId") Long articleId) {
        StudentRoleUpdateDetailResponseDto dto = certificationService.findStudentUpdateRequestDto(articleId);
        return BaseResponse.success(HttpStatus.OK.value(), "학생 등업글 상세조회 성공", dto);
    }

    /**
     * 멘토 등업글 상세 조회.
     */
    @Operation(summary = "멘토 등업글 상세 조회", description = "멘토 등업요청 게시글을 상세조회합니다.", tags = { "등업" })
    @GetMapping("/mentor/{articleId}")
    public BaseResponse<MentorRoleUpdateDetailResponseDto> getMentorUpdateRequestDetail(@PathVariable(name = "articleId") Long articleId) {
        MentorRoleUpdateDetailResponseDto dto = certificationService.findMentorUpdateRequestDto(articleId);
        return BaseResponse.success(HttpStatus.OK.value(), "멘토 등업글 리스트 조회성공", dto);
    }

    /**
     * 학생 등업 승인.
     */
    @Operation(summary = "학생 등업 승인", description = "학생 등업요청을 승인합니다.", tags = { "등업" })
    @PostMapping("/student/{articleId}/approve")
    public BaseResponse<RoleUpdateResponseDto> approveStudentRoleUpdate(@PathVariable(name = "articleId") Long articleId, @RequestBody TempAssignRequestDto request) {
        Long studentId = certificationService.findStudentIdByArticleId(articleId);
        classAssignmentService.tempAssignClassRoom(studentId, request.grade());
        RoleUpdateResponseDto result = certificationService.updateRole(studentId, Role.valueOf("ROLE_STUDENT"));
        certificationService.deleteStudentArticleByArticleId(articleId);
        return BaseResponse.success(HttpStatus.OK.value(), "학생 등업 성공", result);
    }

    /**
     * 멘토 등업 승인.
     */
    @Operation(summary = "멘토 등업 승인", description = "멘토 등업요청을 승인합니다.", tags = { "등업" })
    @PostMapping("/mentor/{articleId}/approve")
    public BaseResponse<RoleUpdateResponseDto> approveMentorRoleUpdate(@PathVariable(name = "articleId") Long articleId) {
        Long mentorId = certificationService.findMentorIdByArticleId(articleId);

        RoleUpdateResponseDto result = certificationService.updateRole(mentorId, Role.valueOf("ROLE_MENTOR"));
        certificationService.deleteMentorArticleByArticleId(articleId);
        return BaseResponse.success(HttpStatus.OK.value(), "멘토 등업 성공", result);
    }

    /**
     * 학생 등업 거절.
     */
    @Operation(summary = "학생 등업 거절", description = "학생 등업요청을 거절합니다.", tags = { "등업" })
    @PostMapping("/student/{articleId}/deny")
    public BaseResponse<Long> denyStudentRoleUpdate(@PathVariable(name = "articleId") Long articleId) {
        certificationService.deleteStudentArticleByArticleId(articleId);
        return BaseResponse.success(HttpStatus.OK.value(), "학생 등업 거절. 요청 삭제.", articleId);
    }

    /**
     * 멘토 등업 거절.
     */
    @Operation(summary = "멘토 등업 거절", description = "멘토 등업요청을 거절합니다.", tags = { "등업" })
    @PostMapping("/mentor/{articleId}/deny")
    public BaseResponse<Long> denyMentorRoleUpdate(@PathVariable(name = "articleId") Long articleId) {
        certificationService.deleteMentorArticleByArticleId(articleId);
        return BaseResponse.success(HttpStatus.OK.value(), "멘토 등업 거절. 요청 삭제.", articleId);
    }
}
