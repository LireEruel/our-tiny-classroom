package com.otc.tinyclassroom.member.controller;

import com.otc.tinyclassroom.global.common.model.response.BaseResponse;
import com.otc.tinyclassroom.global.security.auth.PrincipalDetails;
import com.otc.tinyclassroom.global.security.refreshtoken.service.RefreshTokenService;
import com.otc.tinyclassroom.media.service.MediaService;
import com.otc.tinyclassroom.member.dto.request.MemberJoinRequestDto;
import com.otc.tinyclassroom.member.dto.request.MemberUpdateRequestDto;
import com.otc.tinyclassroom.member.dto.request.MentorRoleUpdateRequestDto;
import com.otc.tinyclassroom.member.dto.request.StudentRoleUpdateRequestDto;
import com.otc.tinyclassroom.member.dto.response.AdminMemberPkIdResponseDto;
import com.otc.tinyclassroom.member.dto.response.AdminMemberResponseDto;
import com.otc.tinyclassroom.member.dto.response.MemberClassRoomNumberResponseDto;
import com.otc.tinyclassroom.member.dto.response.MemberProfileDto;
import com.otc.tinyclassroom.member.service.CertificationService;
import com.otc.tinyclassroom.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 멤버 Controller.
 */
@RequestMapping("/api/members")
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final RefreshTokenService refreshTokenService;
    private final MediaService mediaService;
    private final CertificationService certificationService;

    /**
     * 회원가입 요청을 처리한다.
     *
     * @param request 회원가입 정보
     * @return 응답 코드, 메세지
     */
    @Operation(summary = "회원가입", description = "회원 가입합니다.", tags = { "유저 관련" })
    @PostMapping("/join")
    public BaseResponse<Void> join(@RequestBody MemberJoinRequestDto request) {
        memberService.join(request);
        return BaseResponse.success(HttpStatus.OK.value(), "회원가입에 성공하였습니다!", null);
    }

    /**
     *  로그아웃 메서드.
     *  Refresh Token 을 무효화 함.
     */
    @Operation(summary = "로그아웃", description = "로그아웃 합니다.", tags = { "유저 관련" })
    @PostMapping("/logout")
    public BaseResponse<Long> logoutMember() {
        Long memberId = refreshTokenService.getCurrentMemberId();
        String refreshToken = refreshTokenService.getRefreshTokenByUserId(memberId);
        refreshTokenService.deleteRefreshToken(refreshToken);

        return BaseResponse.success(HttpStatus.OK.value(), "로그아웃 성공!", memberId);
    }

    /**
     * 우리반 멤버 조회 (선생님 포함).
     */
    @Operation(summary = "같은 반 조회", description = "같은 반 학생, 선생님을 조회합니다.", tags = { "유저 관련" })
    @GetMapping("/myClass")
    public BaseResponse<List<MemberProfileDto>> getMyClassRoomMember(@AuthenticationPrincipal PrincipalDetails principalDetails){
        List<MemberProfileDto> result = memberService.getMyClassRoomMember(principalDetails.getMember());
        return BaseResponse.success(HttpStatus.OK.value(), "같은 반 멤버 조회 성공", result);
    }


    /**
     * 사용자 입장에서 학생 ROLE 변경 요청.
     */
    @Operation(summary = "학생 등업 요청", description = "학생으로 등업을 요청합니다.", tags = { "유저 관련" })
    @PostMapping("/certification/student")
    public BaseResponse<List<String>> studentRoleUpdate(@RequestPart(name = "image") List<MultipartFile> files, @RequestPart(name = "request") StudentRoleUpdateRequestDto requestDto) {
        Map<String, List<String>> result = mediaService.storeImagesWithOriginalName(files);
        List<String> urlList = result.get("urlList");
        List<String> originalName = result.get("originalName");
        certificationService.saveStudent(originalName, urlList, requestDto);
        return BaseResponse.success(HttpStatus.OK.value(), "Student Role 업데이트 요청 성공!", urlList);
    }

    /**
     * 사용자 입장에서 Mentor ROLE 변경 요청.
     */
    @Operation(summary = "멘토 등업 요청", description = "멘토으로 등업을 요청합니다.", tags = { "유저 관련" })
    @PostMapping("/certification/mentor")
    public BaseResponse<?> mentorRoleUpdate(@RequestParam("image") List<MultipartFile> files, @RequestPart(name = "request") MentorRoleUpdateRequestDto requestDto) {
        Map<String, List<String>> result = mediaService.storeImagesWithOriginalName(files);
        List<String> urlList = result.get("urlList");
        List<String> originalName = result.get("originalName");
        certificationService.saveMentor(originalName, urlList, requestDto);
        return BaseResponse.success(HttpStatus.OK.value(), "Mentor Role 업데이트 요청 성공!", urlList);
    }

    /**
     * 전체 멤버 목록을 반환하는 메서드.
     */
    @Operation(summary = "모든 유저 정보 조회", description = "모든 유저 정보 조회, PK값 ID를 가져옵니다.", tags = { "유저 관련" })
    @GetMapping("")
    public BaseResponse<List<AdminMemberPkIdResponseDto>> getAllMemberList() {
        return BaseResponse.success(HttpStatus.OK.value(), "멤버 전체 목록을 불러왔습니다.", memberService.getMemberPkIdList());
    }

    /**
     * 제공받은 멤버 삭제하는 메서드.
     */
    @Operation(summary = "유저 정보 삭제", description = "유저 정보를 삭제합니다. @SQLDelete를 이용하여 Soft Delete 처리합니다.", tags = { "유저 관련" })
    @DeleteMapping("/{memberId}")
    public BaseResponse<AdminMemberResponseDto> deleteMember(@PathVariable Long memberId) {
        memberService.deleteMember(memberId);
        return BaseResponse.success(HttpStatus.OK.value(), "멤버를 삭제하였습니다.", null);
    }

    /**
     * 멤버 정보 조회(불러오기).
     */
    @Operation(summary = "멤버 정보 조회", description = "멤버 정보를 조회합니다.", tags = { "유저 관련" })
    @GetMapping("/{memberId}")
    public BaseResponse<AdminMemberResponseDto> getMember(@PathVariable Long memberId) {
        AdminMemberResponseDto member = memberService.getMember(memberId);
        return BaseResponse.success(HttpStatus.OK.value(), "멤버 조회에 성공하였습니다.", member);
    }

    /**
     * 특정 멤버의 반을 조회한다.
     */
    @Operation(summary = "특정 멤버의 반 조회", description = "특정 멤버의 반을 조회합니다.", tags = { "유저 관련" })
    @GetMapping("/class-room-number/{memberId}")
    public BaseResponse<MemberClassRoomNumberResponseDto> getMemberClassRoomNumber(@PathVariable String memberId, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        return BaseResponse.success(HttpStatus.OK.value(), "멤버의 반을 조회하였습니다.", memberService.getMemberClassRoomNumber(memberId));
    }

    /**
     * 멤버 정보 수정 메서드(관리자).
     */
    @Operation(summary = "멤버 정보 수정", description = "관리자가 특정 멤버의 정보를 수정할 수 있습니다.", tags = { "유저 관련" })
    @PatchMapping("/{memberId}")
    public BaseResponse<AdminMemberResponseDto> updateMember(@PathVariable Long memberId, @RequestBody MemberUpdateRequestDto updatedMemberDto) {
        AdminMemberResponseDto updatedMember = memberService.updateMember(memberId, updatedMemberDto);
        return BaseResponse.success(HttpStatus.OK.value(), "멤버 수정에 성공하였습니다.", updatedMember);
    }
}
