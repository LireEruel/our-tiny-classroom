package com.otc.tinyclassroom.lecture.service;

import com.otc.tinyclassroom.global.security.jwt.JwtProvider;
import com.otc.tinyclassroom.lecture.dto.request.LectureCreateUpdateRequestDto;
import com.otc.tinyclassroom.lecture.dto.request.RoomCreateRequestDto;
import com.otc.tinyclassroom.lecture.dto.response.LectureResponseDto;
import com.otc.tinyclassroom.lecture.entity.Lecture;
import com.otc.tinyclassroom.lecture.entity.type.LectureApprovalStatusType;
import com.otc.tinyclassroom.lecture.entity.type.LectureCategoryType;
import com.otc.tinyclassroom.lecture.entity.type.LectureType;
import com.otc.tinyclassroom.lecture.exception.LectureErrorCode;
import com.otc.tinyclassroom.lecture.exception.LectureException;
import com.otc.tinyclassroom.lecture.repository.LectureRepository;
import com.otc.tinyclassroom.member.entity.Member;
import com.otc.tinyclassroom.member.entity.Role;
import com.otc.tinyclassroom.member.exception.MemberErrorCode;
import com.otc.tinyclassroom.member.exception.MemberException;
import com.otc.tinyclassroom.member.repository.MemberRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

/**
 * Lecture 관련 서비스.
 */
@Service
@Transactional
@AllArgsConstructor
public class LectureService {

    private final LectureRepository lectureRepository;
    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;
    private final WebClientService webClientService;

    /**
     * 강의 생성.
     *
     * @param request 강의 정보
     * @return 생성한 강의 정보.
     */
    public LectureResponseDto createLecture(LectureCreateUpdateRequestDto request) {
        checkTeacherOrAdmin();
        Member member = getMemberByMemberId(request.memberId());
        String lectureUrl = createUrlIfSpecialLecture(request);
        // 요청에서 제공된 데이터를 사용하여 새로운 Lecture 엔터티를 생성합니다.
        Lecture newLecture = Lecture.of(
            member,
            request.title(),
            request.description(),
            request.dayOfWeek(),
            request.timeTable(),
            request.lectureType(),
            request.lectureCategoryType(),
            lectureUrl,
            // SPECIAL_LECTURE일 경우 WAITING, 아닐 경우 APPROVED
            LectureApprovalStatusType.APPROVED,
            request.date()
        );
        lectureRepository.save(newLecture);

        return LectureResponseDto.from(newLecture);
    }

    /**
     * 모든 강의 불러오기.
     *
     * @return 모든 강의 정보
     */
    public List<LectureResponseDto> getLectureList() {
        Specification<Lecture> spec = buildApprovedLecturesSpecification();

        List<Lecture> lectures = lectureRepository.findAll(spec, Sort.by(Sort.Direction.ASC, "lectureCategoryType"));

        return lectures.stream()
            .map(LectureResponseDto::from)
            .collect(Collectors.toList());
    }


    /**
     * 필터된 강의 불러오기.
     */
    public List<LectureResponseDto> getFilteredLectureList(
        Long memberId,
        String title,
        String description,
        String dayOfWeek,
        String timeTable,
        LectureType lectureType,
        LectureCategoryType lectureCategoryType,
        LectureApprovalStatusType approved,
        LocalDate date
    ) {
        Specification<Lecture> spec = lectureRepository.buildSpecification(memberId, title, description, dayOfWeek, timeTable, lectureType, lectureCategoryType, approved, date);

        // special lecture 이면서 approved가 WAITING 혹은 DENIED 경우에만 checkTeacherOrAdmin 실행
        if (LectureType.SPECIAL_LECTURE.equals(lectureType) && (LectureApprovalStatusType.WAITING.equals(approved) || LectureApprovalStatusType.REJECTED.equals(approved))) {
            checkTeacherOrAdmin();
        }
        return lectureRepository.findAll(spec, Sort.by(Sort.Direction.ASC, "lectureCategoryType"))
            .stream()
            .map(LectureResponseDto::from)
            .collect(Collectors.toList());
    }

    private Specification<Lecture> buildApprovedLecturesSpecification() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction(); // 모든 상태 허용
    }

    /**
     * 강의 조회하기.
     *
     * @param lectureId 조회할 강의 Id
     * @return 조회한 강의 내용
     */
    public LectureResponseDto getLecture(Long lectureId) {
        // LectureId에 해당하는 Lecture 엔티티를 찾습니다.
        Lecture lecture = lectureRepository.findById(lectureId)
            .orElseThrow(() -> new LectureException(LectureErrorCode.NOT_FOUND_LECTURE));

        return LectureResponseDto.from(lecture);
    }

    /**
     * 강의 수정하기.
     *
     * @param lectureId  수정할 강의 Id
     * @param lectureDto 강의 정보.
     * @return 업데이트 된 강의 정보
     */
    public LectureResponseDto updateLecture(Long lectureId, LectureCreateUpdateRequestDto lectureDto) {
        checkTeacherOrAdmin();
        Lecture existingLecture = getLectureById(lectureId);
        if (lectureDto.memberId() != null) {
            Member member = getMemberByMemberId(lectureDto.memberId());
            // validateMemberRole(member.getRole());
            existingLecture.setMember(member);
        }
        if (lectureDto.title() != null) {
            existingLecture.setTitle(lectureDto.title());
        }
        if (lectureDto.description() != null) {
            existingLecture.setDescription(lectureDto.description());
        }
        if (lectureDto.dayOfWeek() != 0) {
            existingLecture.setDayOfWeek(lectureDto.dayOfWeek());
        }
        if (lectureDto.timeTable() != 0) {
            existingLecture.setTimeTable(lectureDto.timeTable());
        }
        if (lectureDto.lectureType() != null) {
            existingLecture.setLectureType(lectureDto.lectureType());
        }
        if (lectureDto.lectureCategoryType() != null) {
            existingLecture.setLectureCategoryType(lectureDto.lectureCategoryType());
        }
        if (lectureDto.lectureUrl() != null) {
            existingLecture.setLectureUrl(lectureDto.lectureUrl());
        }
        if (lectureDto.date() != null) {
            existingLecture.setDate(lectureDto.date());
        }

        Lecture savedLecture = lectureRepository.save(existingLecture);

        return LectureResponseDto.from(savedLecture);
    }

    /**
     * 강의 삭제하기.
     *
     * @param lectureId 삭제할 강의 Id
     */
    public void deleteLecture(Long lectureId) {
        checkTeacherOrAdmin();
        lectureRepository.deleteById(lectureId);
    }

    /**
     * 요청한 유저의 권한이 있는지 확인(선생, 멘토, 관리자).
     */
    private void checkTeacherOrAdmin() {
        Long currentMemberId = jwtProvider.getCurrentMemberId();
        Member currentMember = memberRepository.findById(currentMemberId)
            .orElseThrow(() -> new LectureException(
                LectureErrorCode.NO_AUTHORITY));  // 권한이 없습니다로 변경하기.
        Role userRole = currentMember.getRole();
        if (userRole != Role.ROLE_TEACHER && userRole != Role.ROLE_ADMIN && userRole != Role.ROLE_MENTOR) {
            throw new LectureException(LectureErrorCode.NO_AUTHORITY);
        }
    }

    /**
     * 특강 신청 승인 처리.
     *
     * @param lectureId 강의 ID
     */
    public void approveSpecialLecture(Long lectureId) {
        Lecture lecture = getLectureById(lectureId);

        // 특강이 아닌 경우 오류 응답
        if (lecture.getLectureType() != LectureType.SPECIAL_LECTURE) {
            throw new LectureException(LectureErrorCode.NO_SPECIAL_LECTURE);
        }

        // 특강을 승인하고 저장
        lecture.setApproved(LectureApprovalStatusType.APPROVED);
    }

    /**
     * 특강 신청 거절 처리.
     *
     * @param lectureId 강의 ID
     */
    public void rejectSpecialLecture(Long lectureId) {
        Lecture lecture = getLectureById(lectureId);

        // 특강이 아닌 경우 오류 응답
        if (lecture.getLectureType() != LectureType.SPECIAL_LECTURE) {
            throw new LectureException(LectureErrorCode.NO_SPECIAL_LECTURE);
        }

        // 특강을 거절하고 저장
        lecture.setApproved(LectureApprovalStatusType.REJECTED);
        lectureRepository.save(lecture);
    }

    /**
     * lecture 에 등록되는 멤버가 멘토나 선생인지 확인.
     */
    private void validateMemberRole(Role role) {
        if (!(role == Role.ROLE_TEACHER || role == Role.ROLE_MENTOR)) {
            throw new LectureException(LectureErrorCode.NO_TEACHER_OR_MENTOR);
        }
    }

    private Member getMemberByMemberId(Long memberId) {
        return memberRepository.findById(memberId)
            .orElseThrow(() -> new MemberException(MemberErrorCode.NOT_FOUND_MEMBER));
    }


    private Lecture getLectureById(Long lectureId) {
        return lectureRepository.findById(lectureId)
            .orElseThrow(() -> new LectureException(LectureErrorCode.NOT_FOUND_LECTURE));
    }

    private String createUrlIfSpecialLecture(LectureCreateUpdateRequestDto requestDto) {
        if (requestDto.lectureType() != LectureType.SPECIAL_LECTURE) {
            return null;
        }
        LocalDateTime nbfTime = LocalDateTime.of(requestDto.date(), LocalTime.of(9, 0));
        long nbf = nbfTime.atZone(ZoneId.systemDefault()).toEpochSecond();
        LocalDateTime expTime = LocalDateTime.of(requestDto.date(), LocalTime.of(17, 0));
        long exp = expTime.atZone(ZoneId.systemDefault()).toEpochSecond();
        int maxParticipants = 10;
        String identifier = String.valueOf(UUID.randomUUID());
        Mono<String> urlMono = webClientService.createClassRoomUrl(RoomCreateRequestDto.of(identifier, nbf, exp, maxParticipants));
        // Mono<String>을 String으로 동기적으로 변환
        return urlMono.block();
    }
}
