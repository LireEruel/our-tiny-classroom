package com.otc.tinyclassroom.member.service;

import com.otc.tinyclassroom.global.security.jwt.JwtProvider;
import com.otc.tinyclassroom.global.common.model.response.BaseResponse;
import com.otc.tinyclassroom.global.security.refreshtoken.entity.RefreshToken;
import com.otc.tinyclassroom.global.security.refreshtoken.repository.RefreshTokenRepository;
import com.otc.tinyclassroom.member.client.KakaoClient;
import com.otc.tinyclassroom.member.dto.request.KakaoLoginRequestDto;
import com.otc.tinyclassroom.member.dto.request.MemberJoinRequestDto;
import com.otc.tinyclassroom.member.dto.request.MemberUpdateRequestDto;
import com.otc.tinyclassroom.member.dto.response.AdminMemberPkIdResponseDto;
import com.otc.tinyclassroom.member.dto.response.AdminMemberResponseDto;
import com.otc.tinyclassroom.member.dto.response.MemberClassRoomNumberResponseDto;
import com.otc.tinyclassroom.member.dto.response.MemberProfileDto;
import com.otc.tinyclassroom.member.entity.ClassRoom;
import com.otc.tinyclassroom.member.dto.response.KakaoOAuthResponse;
import com.otc.tinyclassroom.member.dto.response.MemberLoginResponseDto;
import com.otc.tinyclassroom.member.entity.Member;
import com.otc.tinyclassroom.member.entity.MemberClassRoom;
import com.otc.tinyclassroom.member.entity.Role;
import com.otc.tinyclassroom.member.exception.ClassAssignmentErrorCode;
import com.otc.tinyclassroom.member.exception.ClassAssignmentException;
import com.otc.tinyclassroom.member.exception.MemberErrorCode;
import com.otc.tinyclassroom.member.exception.MemberException;
import com.otc.tinyclassroom.member.repository.MemberClassRoomRepository;
import com.otc.tinyclassroom.member.repository.MemberRepository;
import com.otc.tinyclassroom.schedule.exception.ScheduleErrorCode;
import com.otc.tinyclassroom.schedule.exception.ScheduleException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 회원 Service.
 */
@Service
@AllArgsConstructor
public class MemberService {

    // 회원가입 초기화 포인트
    static final int INITIAL_POINT = 0;
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final MemberClassRoomRepository memberClassRoomRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    /**
     * 회원가입을 수행한다.
     *
     * @param request 회원가입정보
     */
    @Transactional
    public void join(MemberJoinRequestDto request) {
        // 빈 필드 확인
        if (request.memberId() == null || request.password() == null || request.name() == null || request.name().isBlank() || request.email() == null || request.birthday() == null) {
            throw new MemberException(MemberErrorCode.INVALID_FIELD_VALUE);
        }
        // 아이디 형식 확인
        if (!isValidMemberId(request.memberId())) {
            throw new MemberException(MemberErrorCode.INVALID_MEMBER_ID);
        }
        // 아이디 중복 확인
        memberRepository.findMemberByMemberId(request.memberId()).ifPresent(member -> {
            throw new MemberException(MemberErrorCode.DUPLICATED_USER_NAME);
        });

        // 비밀번호 형식 확인
        if (!isValidPassword(request.password())) {
            throw new MemberException(MemberErrorCode.PASSWORD_VALIDATION_FAILED);
        }
        // 이메일 확인
        if (!isValidEmail(request.email())) {
            throw new MemberException(MemberErrorCode.INVALID_EMAIL);
        }
        Member member = Member
            .of(request.memberId(), passwordEncoder.encode(request.password()), request.name(), request.email(), request.birthday(), INITIAL_POINT, null);

        memberRepository.save(member);
    }


    /**
     * 카카오 계정으로 로그인한 기록이 있으면 멤버를 가져오고 없는 경우 저장한다.
     */
    @Transactional
    public Member findOrCreateMember(KakaoOAuthResponse response) {
        String memberId = "kakao-" + response.id();
        return memberRepository.findByMemberId(memberId)
            .orElseGet(
                () -> {
                    String dummyPassword = "dummy" + UUID.randomUUID();
                    Member newMember = Member.of(memberId, passwordEncoder.encode(dummyPassword), response.nickname(), null, null, INITIAL_POINT, null);
                    return memberRepository.save(newMember);
                }
            );
    }


    private boolean isValidMemberId(String memberId) {
        String memberRegex = "^[a-z0-9_-]{5,20}$";
        Pattern pattern = Pattern.compile(memberRegex);
        Matcher matcher = pattern.matcher(memberId);
        return matcher.matches();
    }

    protected boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."
            + "[a-zA-Z0-9_+&*-]+)*@"
            + "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    protected boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+{}:\"<>?\\[\\];',./`~])(?=\\S+$).{8,20}$";
        return password.matches(passwordRegex);
    }

    /**
     * 멤버 전체 목록 조회 메서드.
     */
    @Transactional
    public List<AdminMemberResponseDto> getMemberList() {
        List<Member> members = memberRepository.findAll();
        return members.stream().map(AdminMemberResponseDto::from).collect(Collectors.toList());
    }

    /**
     * 멤버 전체 목록 조회 메서드 pk Id 사용.
     */
    @Transactional
    public List<AdminMemberPkIdResponseDto> getMemberPkIdList() {
        List<Member> members = memberRepository.findAll();
        return members.stream().map(AdminMemberPkIdResponseDto::from).collect(Collectors.toList());
    }

    /**
     * 멤버 삭제 메서드.
     */
    @Transactional
    public void deleteMember(Long memberId) {
        checkAdmin();
        memberRepository.deleteById(memberId);
    }

    /**
     * 멤버 조회 메서드.
     */
    @Transactional
    public AdminMemberResponseDto getMember(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new MemberException(MemberErrorCode.NOT_FOUND_MEMBER));
        return AdminMemberResponseDto.from(member);
    }

    /**
     * 멤버 정보 수정 메서드(관리자).
     */
    @Transactional
    public AdminMemberResponseDto updateMember(Long memberId, MemberUpdateRequestDto updatedMemberDto) {
        checkAdmin();

        // 해당 memberId로 멤버 엔티티를 찾아옴
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new MemberException(MemberErrorCode.NOT_FOUND_MEMBER));

        // 업데이트할 필드가 null이 아닌 경우에만 Setter를 사용하여 엔티티의 필드를 갱신
        if (updatedMemberDto.name() != null) {
            member.setName(updatedMemberDto.name());
        }
        if (updatedMemberDto.email() != null) {
            member.setEmail(updatedMemberDto.email());
        }
        if (updatedMemberDto.role() != null) {
            member.setRole(updatedMemberDto.role());
        }
        return AdminMemberResponseDto.from(member);
    }


    /**
     * 요청한 유저의 권한이 있는지 확인(관리자).
     */
    public void checkAdmin() {
        Long currentMemberId = jwtProvider.getCurrentMemberId();
        Member currentMember = memberRepository.findById(currentMemberId).orElseThrow(() -> new MemberException(MemberErrorCode.NO_AUTHORITY));
        Role userRole = currentMember.getRole();
        if (userRole != Role.ROLE_ADMIN) {
            throw new MemberException(MemberErrorCode.NO_AUTHORITY);
        }
    }

    /**
     * 같은 반의 멤버를(선생님 포함) 조회한다.
     */
    @Transactional
    public List<MemberProfileDto> getMyClassRoomMember(Member member) {

        List<ClassRoom> classRooms = memberClassRoomRepository.findClassRoomByMemberId(member.getId());
        if (classRooms.isEmpty()) {
            throw new ClassAssignmentException(ClassAssignmentErrorCode.CLASSROOM_NOT_ASSIGNED);
        }
        ClassRoom classRoom = classRooms.get(classRooms.size() - 1);
        List<Member> members = memberClassRoomRepository.findMemberByClassRoomId(classRoom.getId());
        return members.stream().map(MemberProfileDto::from).collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * 멤버의 현재 반을 넘겨준다.
     */
    @Transactional(readOnly = true)
    public MemberClassRoomNumberResponseDto getMemberClassRoomNumber(String memberId) {
        Member member = memberRepository.findByMemberId(memberId).orElseThrow(
            () -> new MemberException(MemberErrorCode.NOT_FOUND_MEMBER)
        );

        List<MemberClassRoom> memberClassRooms = member.getMemberClassRooms();

        if (memberClassRooms.isEmpty()) {
            throw new ClassAssignmentException(ClassAssignmentErrorCode.CLASSROOM_NOT_ASSIGNED);
        }
        MemberClassRoom memberClassRoom = memberClassRooms.get(memberClassRooms.size() - 1);
        ClassRoom classRoom = memberClassRoom.getClassRoom();
        return MemberClassRoomNumberResponseDto.of(classRoom.getId(), classRoom.getGrade(), classRoom.getNumber(), classRoom.getYear());
    }

    /**
     * 같은 반의 담임선생님을 조회한다.
     */
    @Transactional(readOnly = true)
    public String getMyTeacher(Long classRoomId) {
        List<Member> teachers = memberClassRoomRepository.findMemberByClassRoomIdAndRole(classRoomId, Role.ROLE_TEACHER);
        Member teacher = teachers.stream().findFirst().orElseThrow(
            () -> new ScheduleException(ScheduleErrorCode.NOT_EXIST_TEACHER)
        );
        return teacher.getName();
    }
}
