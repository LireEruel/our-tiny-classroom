package com.otc.tinyclassroom.mypage.controller;

import com.otc.tinyclassroom.community.dto.response.MyPageArticleResponseDto;
import com.otc.tinyclassroom.community.service.ArticleService;
import com.otc.tinyclassroom.community.service.HeartService;
import com.otc.tinyclassroom.global.common.model.response.BaseResponse;
import com.otc.tinyclassroom.mypage.dto.request.EditMyInfoRequestDto;
import com.otc.tinyclassroom.mypage.dto.response.AttendanceResponseDto;
import com.otc.tinyclassroom.mypage.dto.response.ClassRoomMembersResponseDto;
import com.otc.tinyclassroom.mypage.dto.response.MyInfoResponseDto;
import com.otc.tinyclassroom.mypage.dto.response.MyPageResponseDto;
import com.otc.tinyclassroom.mypage.service.MyPageService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 마이페이지 컨트롤러.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/my-page")
public class MyPageController {

    private final MyPageService myPageService;
    private final HeartService heartService;
    private final ArticleService articleService;

    /**
     * 마이페이지의 main. 모든 정보를 불러와야 한다.
     */
    @Operation(summary = "기본 로드 정보 조회", description = "마이페이지 진입시 필요한 모든 정보를 조회합니다.", tags = { "마이페이지" })
    @GetMapping("")
    public BaseResponse<MyPageResponseDto> mainPage() {
        MyPageResponseDto dto = myPageService.getBasicInfos();
        return BaseResponse.success(HttpStatus.OK.value(), "모든 정보 불러오기 성공!", dto);
    }

    /**
     * 정보 수정을 위한 현재 내 정보 불러오기.
     */
    @Operation(summary = "내 정보 조회", description = "정보 수정을 위한 현재 내 정보를 조회합니다.", tags = { "마이페이지" })
    @GetMapping("/my-info")
    public BaseResponse<MyInfoResponseDto> existingInfo() {
        MyInfoResponseDto dto = myPageService.existingInfo();
        return BaseResponse.success(HttpStatus.OK.value(), "개인 정보 불러오기 성공!", dto);
    }

    /**
     * 비밀번호 수정.
     */
    @Operation(summary = "비밀번호 수정", description = "비밀번호를 수정합니다.", tags = { "마이페이지" })
    @PatchMapping("/my-info")
    public BaseResponse<String> updatePassword(@RequestBody EditMyInfoRequestDto dto) {
        String result = myPageService.updateMyInfo(dto);
        return BaseResponse.success(HttpStatus.OK.value(), "내 정보 수정 성공!", result);
    }

    /**
     * 같은 반 친구들을 불러온다.
     */
    @Operation(summary = "같은 반 친구 조회", description = "같은 반 친구들 정보를 조회합니다.", tags = { "마이페이지" })
    @GetMapping("/class-room-friends")
    public BaseResponse<List<ClassRoomMembersResponseDto>> findMemberClassRooms() {
        List<ClassRoomMembersResponseDto> result = myPageService.findMemberClassRooms();
        return BaseResponse.success(HttpStatus.OK.value(), "같은 반 정보 불러오기 성공!", result);
    }

    /**
     * 출석률을 확인한다.
     */
    @Operation(summary = "출석률 조회", description = "이번달 출석률을 조회합니다.", tags = { "마이페이지" })
    @GetMapping("/attendance-rate")
    public BaseResponse<AttendanceResponseDto> attendanceRate() {
        return BaseResponse.success(HttpStatus.OK.value(), "출석률 불러오기 성공!", myPageService.getAttendanceRate());
    }

    /**
     * 프로필 사진을 변경한다.
     */
    @Operation(summary = "프로필 사진 변경", description = "프로필 사진을 변경합니다.", tags = { "마이페이지" })
    @PostMapping("/profile-image")
    public BaseResponse<String> updateProfileImages(@RequestPart("image") MultipartFile imageFile) {
        return BaseResponse.success(HttpStatus.OK.value(), "프로필 이미지가 변경되었습니다.", myPageService.uploadProfile(imageFile));
    }

    /**
     * 좋아요 누른 게시글(최신순)을 가져온다.
     */
    @Operation(summary = "좋아요 누른 게시글 조회", description = "내가 좋아요 누른 게시글을 최신순으로 조회합니다.", tags = { "마이페이지" })
    @GetMapping("/recent-liked-list")
    public BaseResponse<List<MyPageArticleResponseDto>> getLikedArticleList(@RequestParam int pageNumber, @RequestParam int pageSize) {
        return BaseResponse.success(HttpStatus.OK.value(), "좋아요 게시글 목록 반환 성공",
            heartService.searchLikedArticles(pageNumber, pageSize));
    }

    /**
     * 내가 쓴 게시글(최신순)을 가져온다.
     */
    @Operation(summary = "내가 쓴 게시글 조회", description = "내가 쓴 게시글을 최신순으로 조회합니다.", tags = { "마이페이지" })
    @GetMapping("/recent-my-article-list")
    public BaseResponse<List<MyPageArticleResponseDto>> getMyArticleList(@RequestParam int pageNumber, @RequestParam int pageSize) {
        return BaseResponse.success(HttpStatus.OK.value(), "나의 게시글 목록 반환 성공",
            articleService.searchMyArticles(pageNumber, pageSize));
    }

}
