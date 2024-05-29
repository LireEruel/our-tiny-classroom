package com.otc.tinyclassroom.member.service;

import com.otc.tinyclassroom.global.security.jwt.JwtProvider;
import com.otc.tinyclassroom.member.dto.request.MentorRoleUpdateRequestDto;
import com.otc.tinyclassroom.member.dto.request.StudentRoleUpdateRequestDto;
import com.otc.tinyclassroom.member.dto.response.MentorRoleUpdateDetailResponseDto;
import com.otc.tinyclassroom.member.dto.response.RoleUpdateResponseDto;
import com.otc.tinyclassroom.member.dto.response.StudentRoleUpdateDetailResponseDto;
import com.otc.tinyclassroom.member.entity.Member;
import com.otc.tinyclassroom.member.entity.MentorRoleUpdateRequest;
import com.otc.tinyclassroom.member.entity.Role;
import com.otc.tinyclassroom.member.entity.StudentRoleUpdateRequest;
import com.otc.tinyclassroom.member.exception.CertificationErrorCode;
import com.otc.tinyclassroom.member.exception.CertificationException;
import com.otc.tinyclassroom.member.repository.MemberRepository;
import com.otc.tinyclassroom.member.repository.MentorRoleUpdateRepository;
import com.otc.tinyclassroom.member.repository.StudentRoleUpdateRepository;
import com.otc.tinyclassroom.mypage.event.NoConditionBadgeEvent;
import com.otc.tinyclassroom.mypage.repository.BadgeRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 등업 요청을 관리하기 위한 서비스.
 */
@AllArgsConstructor
@Service
@Transactional
public class CertificationService {

    private final MentorRoleUpdateRepository mentorRoleUpdateRepository;
    private final StudentRoleUpdateRepository studentRoleUpdateRepository;
    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;
    private final ApplicationEventPublisher publisher;
    private final BadgeRepository badgeRepository;

    /**
     * 유효성 검사 후 MentorRoleUpdateRequest 저장.
     */
    public void saveMentor(List<String> originalFiles, List<String> savedFiles, MentorRoleUpdateRequestDto dto) {
        if (dto.belong() == null) {
            throw new CertificationException(CertificationErrorCode.NO_BELONG);
        }

        Member member = memberRepository.findById(jwtProvider.getCurrentMemberId()).orElseThrow(
            () -> new CertificationException(CertificationErrorCode.NO_USER_FOUND, "유저 아이디가 존재하지 않습니다.")
        );

        MentorRoleUpdateRequest mentorRoleUpdateRequest
            = MentorRoleUpdateRequest.of(member, dto.belong(), originalFiles, savedFiles);
        mentorRoleUpdateRepository.save(mentorRoleUpdateRequest);
        member.setRole(Role.ROLE_WAITING);
    }

    /**
     * 유효성 검사 후 StudentRoleUpdateRequest 저장.
     */
    public void saveStudent(List<String> originalFiles, List<String> savedFiles, StudentRoleUpdateRequestDto dto) {
        if (dto.beforeSchoolType() < 0 || dto.beforeSchoolType() > 3) {
            throw new CertificationException(CertificationErrorCode.NOT_SCHOOL_TYPE, "유효한 학교타입이 아닙니다.");
        }

        Member member = memberRepository.findById(jwtProvider.getCurrentMemberId()).orElseThrow(
            () -> new CertificationException(CertificationErrorCode.NO_USER_FOUND, "유저 아이디가 존재하지 않습니다.")
        );
        StudentRoleUpdateRequest studentRoleUpdateRequest
            = StudentRoleUpdateRequest.of(member, dto.beforeSchoolType(), dto.quitReason(), dto.beforeSchool(), originalFiles, savedFiles);
        studentRoleUpdateRepository.save(studentRoleUpdateRequest);
        member.setRole(Role.ROLE_WAITING);
    }

    /**
     * ROLE_ADMIN 을 가진 관리자 계정만 사용가능 memberId로 가져온 뒤 Role 을 update 함.
     */
    public RoleUpdateResponseDto updateRole(Long memberId, Role role) {
        Member member = memberRepository.findById(memberId).orElseThrow(
            () -> new CertificationException(CertificationErrorCode.NO_USER_FOUND, "유저 아이디가 존재하지 않습니다.")
        );
        if (isAuthorizedForRoleChange()) {
            member.setRole(role);

            publisher.publishEvent(new NoConditionBadgeEvent(memberId, 3L));
        }
        return RoleUpdateResponseDto.of(memberId, role);
    }

    /**
     * 학생 등업 리스트 전체 조회.
     */
    @Transactional(readOnly = true)
    public List<StudentRoleUpdateRequest> findStudentAll() {
        return studentRoleUpdateRepository.findAll();
    }

    /**
     * 멘토 등업 리스트 전체 조회.
     */
    @Transactional(readOnly = true)
    public List<MentorRoleUpdateRequest> findMentorAll() {
        return mentorRoleUpdateRepository.findAll();
    }

    /**
     * 학생 등업 요청 상세 조회.
     */
    @Transactional(readOnly = true)
    public StudentRoleUpdateDetailResponseDto findStudentUpdateRequestDto(Long articleId) {
        StudentRoleUpdateRequest request = studentRoleUpdateRepository.findById(articleId).orElseThrow(
            () -> new CertificationException(CertificationErrorCode.NO_ARTICLE)
        );
        return StudentRoleUpdateDetailResponseDto.of(articleId, request.getMember().getName(), request.getMember().getBirthday(),
                request.getBeforeSchoolType(), request.getQuitReason(),
                request.getBeforeSchool(), request.getQuitConfirmationPaths());

    }

    /**
     * 멘토 등업 요청 상세 조회.
     */
    @Transactional(readOnly = true)
    public MentorRoleUpdateDetailResponseDto findMentorUpdateRequestDto(Long articleId) {
        MentorRoleUpdateRequest request = mentorRoleUpdateRepository.findById(articleId).orElseThrow(
            () -> new CertificationException(CertificationErrorCode.NO_ARTICLE)
        );
        return MentorRoleUpdateDetailResponseDto.of(articleId, request.getMember().getName(), request.getMember().getBirthday(), request.getBelong(), request.getBelongDocumentPaths());
    }

    /**
     * 학생 등업 글로부터 유저 아이디를 가져온다.
     */
    @Transactional(readOnly = true)
    public Long findStudentIdByArticleId(Long articleId) {
        StudentRoleUpdateRequest studentRoleUpdateRequest = studentRoleUpdateRepository.findById(articleId).orElseThrow(
            () -> new CertificationException(CertificationErrorCode.NO_ARTICLE)
        );
        return studentRoleUpdateRequest.getMember().getId();
    }

    /**
     * 멘토 등업 글로부터 유저 아이디를 가져온다.
     */
    @Transactional(readOnly = true)
    public Long findMentorIdByArticleId(Long articleId) {
        MentorRoleUpdateRequest mentorRoleUpdateRequest = mentorRoleUpdateRepository.findById(articleId).orElseThrow(
            () -> new CertificationException(CertificationErrorCode.NO_ARTICLE)
        );
        return mentorRoleUpdateRequest.getMember().getId();
    }

    /**
     * 학생 요청이 완료되거나 거절시 삭제.
     */
    public void deleteStudentArticleByArticleId(Long articleId) {
        studentRoleUpdateRepository.deleteById(articleId);
    }

    /**
     * 멘토 요청이 완료되거나 거절시 삭제.
     */
    public void deleteMentorArticleByArticleId(Long articleId) {
        mentorRoleUpdateRepository.deleteById(articleId);
    }

    /**
     * ADMIN 유저만 role을 변경할 수 있다.
     */
    private boolean isAuthorizedForRoleChange() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return authentication != null && authentication.getAuthorities().stream()
            .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
    }
}
