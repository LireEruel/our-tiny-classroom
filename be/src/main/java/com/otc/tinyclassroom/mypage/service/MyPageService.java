package com.otc.tinyclassroom.mypage.service;

import com.otc.tinyclassroom.attendance.dto.response.MonthlyAttendanceResponseDto;
import com.otc.tinyclassroom.attendance.dto.response.MonthlyAttendanceResponseDto.AttendanceMonthInfo;
import com.otc.tinyclassroom.attendance.service.AttendanceService;
import com.otc.tinyclassroom.community.dto.response.MyPageArticleResponseDto;
import com.otc.tinyclassroom.community.service.ArticleService;
import com.otc.tinyclassroom.community.service.HeartService;
import com.otc.tinyclassroom.global.security.jwt.JwtProvider;
import com.otc.tinyclassroom.lecture.entity.type.LectureCategoryType;
import com.otc.tinyclassroom.media.service.MediaService;
import com.otc.tinyclassroom.member.entity.Member;
import com.otc.tinyclassroom.member.entity.MemberClassRoom;
import com.otc.tinyclassroom.member.exception.ClassAssignmentErrorCode;
import com.otc.tinyclassroom.member.exception.ClassAssignmentException;
import com.otc.tinyclassroom.member.exception.MemberErrorCode;
import com.otc.tinyclassroom.member.exception.MemberException;
import com.otc.tinyclassroom.member.repository.MemberClassRoomRepository;
import com.otc.tinyclassroom.member.repository.MemberRepository;
import com.otc.tinyclassroom.member.service.MemberService;
import com.otc.tinyclassroom.mypage.dto.request.EditMyInfoRequestDto;
import com.otc.tinyclassroom.mypage.dto.response.AttendanceResponseDto;
import com.otc.tinyclassroom.mypage.dto.response.ClassRoomMembersResponseDto;
import com.otc.tinyclassroom.mypage.dto.response.GoalResponseDto;
import com.otc.tinyclassroom.mypage.dto.response.MemberBadgeResponseDto;
import com.otc.tinyclassroom.mypage.dto.response.MyInfoResponseDto;
import com.otc.tinyclassroom.mypage.dto.response.MyPageResponseDto;
import com.otc.tinyclassroom.mypage.exception.MyPageErrorCode;
import com.otc.tinyclassroom.mypage.exception.MyPageException;
import com.otc.tinyclassroom.schedule.service.MemberScheduleService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * 마이페이지 서비스.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class MyPageService {

    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;
    private final MemberClassRoomRepository memberClassRoomRepository;
    private final BCryptPasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final AttendanceService attendanceService;
    private final HeartService heartService;
    private final MediaService mediaService;
    private final BadgeService badgeService;
    private final MemberService memberService;
    private final ArticleService articleService;
    private final GoalService goalService;
    private final MemberScheduleService memberScheduleService;


    /**
     * 첫 로딩시 모든 정보를 불러온다.
     */
    public MyPageResponseDto getBasicInfos() {
        Long currentMemberId = jwtProvider.getCurrentMemberId();

        int grade = 0;
        int classNumber = 0;

        List<MemberClassRoom> classes = memberClassRoomRepository.findByMemberId(currentMemberId);

        if (classes.isEmpty()) {
            throw new ClassAssignmentException(ClassAssignmentErrorCode.CLASSROOM_NOT_ASSIGNED);
        }

        MemberClassRoom recentClass = classes.get(classes.size() - 1);
        grade = recentClass.getClassRoom().getGrade();
        classNumber = recentClass.getClassRoom().getNumber();
        AttendanceResponseDto attendanceResponseDto = getAttendanceRate();

        Long lectureCnt = memberScheduleService.countByMemberId(currentMemberId);
        int totalLectureTime = (int) (lectureCnt * 5 / 6);

        List<LectureCategoryType> favoriteClass = memberScheduleService.findDistinctLectureCategoryTypeByMemberIdOrderByLectureCategoryTypeDesc(currentMemberId);

        String favoriteClassFirst = null;
        String favoriteClassSecond = null;
        String favoriteClassThird = null;

        if (favoriteClass != null && !favoriteClass.isEmpty()) {
            favoriteClassFirst = favoriteClass.get(0) != null ? favoriteClass.get(0).getKorName() : null;
            favoriteClassSecond = favoriteClass.size() > 1 && favoriteClass.get(1) != null ? favoriteClass.get(1).getKorName() : null;
            favoriteClassThird = favoriteClass.size() > 2 && favoriteClass.get(2) != null ? favoriteClass.get(2).getKorName() : null;
        }

        String classRoomTeacher = "";
        if (classNumber != 0) {
            classRoomTeacher = memberService.getMyTeacher(recentClass.getClassRoom().getId());
        }

        // 이번주 목표 가져오기
        GoalResponseDto thisWeekGoal = goalService.getThisWeekGoal();

        // 뱃지 리스트
        List<MemberBadgeResponseDto> myBadge = badgeService.getOnesBadge(currentMemberId);

        List<MyPageArticleResponseDto> likedArticlesDto = heartService.searchLikedArticles(0, 5);
        List<MyPageArticleResponseDto> myArticlesDto = articleService.searchMyArticles(0, 5);

        Member member = memberRepository.findById(currentMemberId).orElseThrow(
            () -> new MyPageException(MyPageErrorCode.NO_USER_FOUND)
        );
        return MyPageResponseDto.of(member.getName(), grade, classNumber, classRoomTeacher, member.getProfileUrl(), member.getPoint(), thisWeekGoal.content(),
            lectureCnt, totalLectureTime,
            attendanceResponseDto.attendanceRate(), attendanceResponseDto.attend(), attendanceResponseDto.lateOrLeaveFast(), attendanceResponseDto.absent(),
            favoriteClassFirst, favoriteClassSecond, favoriteClassThird, myBadge,
            likedArticlesDto, myArticlesDto);
    }

    /**
     * 현재 자신의 정보를 반환한다.
     */
    public MyInfoResponseDto existingInfo() {
        Long currentMemberId = jwtProvider.getCurrentMemberId();
        Member member = memberRepository.findById(currentMemberId).orElseThrow(
            () -> new MyPageException(MyPageErrorCode.NO_USER_FOUND)
        );
        return MyInfoResponseDto.of(member.getMemberId(), member.getName(), member.getEmail());
    }

    /**
     * 내 정보를 수정한다.
     */
    public String updateMyInfo(EditMyInfoRequestDto dto) {
        Long currentMemberId = jwtProvider.getCurrentMemberId();
        Member member = memberRepository.findById(currentMemberId).orElseThrow(
            () -> new MyPageException(MyPageErrorCode.NO_USER_FOUND)
        );
        StringBuilder sb = new StringBuilder();
        if (dto.name() != null && !dto.name().isEmpty()) {
            member.updateName(dto.name());
            sb.append("이름이 변경되었습니다.").append('\n');
        }

        if (dto.newPassword() != null && !dto.newPassword().isEmpty()) {
            if (!encoder.matches(dto.beforePassword(), member.getPassword())) {
                throw new MemberException(MemberErrorCode.PASSWORD_NOT_MATCH);
            }
            // 비밀번호 검증 로직
            if (!isValidPassword(dto.newPassword())) {
                throw new MemberException(MemberErrorCode.PASSWORD_VALIDATION_FAILED);
            }
            String encryptPassword = encoder.encode(dto.newPassword());
            member.updatePassword(encryptPassword);
            sb.append("비밀번호가 변경되었습니다.").append("\n");

            /* 1. 새로운 UsernamePasswordAuthenticationToken 생성하여 AuthenticationManager 을 이용해 등록 */
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(member.getMemberId(), dto.newPassword())
            );

            /* 2. SecurityContextHolder 안에 있는 Context를 호출해 변경된 Authentication으로 설정 */
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        return sb.toString();
    }

    /**
     * 같은 반 학생들을 불러온다.
     *
     * @return 프로필사진과 이름
     */
    public List<ClassRoomMembersResponseDto> findMemberClassRooms() {
        Long currentMemberId = jwtProvider.getCurrentMemberId();

        return memberClassRoomRepository
            .findByMemberId(currentMemberId)
            .stream()
            .map(MemberClassRoom::getMember)
            .map(m -> ClassRoomMembersResponseDto.of(m.getName(), m.getProfileUrl()))
            .collect(Collectors.toList());
    }

    /**
     * 출석률을 확인한다.
     */
    public AttendanceResponseDto getAttendanceRate() {
        Long currentMemberId = jwtProvider.getCurrentMemberId();
        String memberId = memberRepository.findById(currentMemberId).get().getMemberId();
        LocalDateTime now = LocalDateTime.now();

        MonthlyAttendanceResponseDto dto = attendanceService.getAttendanceTimeOnMonth(memberId, now.getYear(), now.getMonth().getValue());
        List<AttendanceMonthInfo> list = dto.attendanceByDate();
        int absent = 0;
        int lateOrLeaveFast = 0;
        int attend = 0;
        int attendanceRate = 0;
        for (AttendanceMonthInfo a : list) {
            switch (a.status()) {
                case 3 -> absent++;
                case 1, 2 -> lateOrLeaveFast++;
                case 0 -> attend++;
                default -> {
                }
            }
        }
        attendanceRate = list.size() == 0 ? 0 : 100 * (list.size() - absent) / list.size();
        return AttendanceResponseDto.of(attendanceRate, attend, lateOrLeaveFast, absent);
    }

    /**
     * 프로필 이미지를 업데이트 한다.
     */
    public String uploadProfile(MultipartFile file) {
        Long currentMemberId = jwtProvider.getCurrentMemberId();
        Member member = memberRepository.findById(currentMemberId).orElseThrow(
            () -> new MyPageException(MyPageErrorCode.NO_USER_FOUND)
        );
        String url = mediaService.storeImage(file);
        member.setProfileUrl(url);

        return url;
    }

    protected boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+{}:\"<>?\\[\\];',./`~])(?=\\S+$).{8,20}$";
        return password.matches(passwordRegex);
    }


}
