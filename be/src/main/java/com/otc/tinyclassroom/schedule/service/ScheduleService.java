package com.otc.tinyclassroom.schedule.service;

import com.otc.tinyclassroom.global.security.jwt.JwtProvider;
import com.otc.tinyclassroom.lecture.entity.Lecture;
import com.otc.tinyclassroom.lecture.entity.type.LectureApprovalStatusType;
import com.otc.tinyclassroom.lecture.entity.type.LectureType;
import com.otc.tinyclassroom.lecture.repository.LectureRepository;
import com.otc.tinyclassroom.member.entity.ClassRoom;
import com.otc.tinyclassroom.member.entity.Member;
import com.otc.tinyclassroom.member.entity.MemberClassRoom;
import com.otc.tinyclassroom.member.entity.Role;
import com.otc.tinyclassroom.member.repository.MemberClassRoomRepository;
import com.otc.tinyclassroom.member.repository.MemberRepository;
import com.otc.tinyclassroom.mypage.event.LectureBadgeEvent;
import com.otc.tinyclassroom.schedule.dto.ScheduleCheckDto;
import com.otc.tinyclassroom.schedule.dto.ScheduleDetailDto;
import com.otc.tinyclassroom.schedule.dto.ScheduleListDto;
import com.otc.tinyclassroom.schedule.dto.request.ScheduleInsertRequestDto;
import com.otc.tinyclassroom.schedule.dto.response.ScheduleListResponseDto;
import com.otc.tinyclassroom.schedule.dto.response.ScheduleUrlResponseDto;
import com.otc.tinyclassroom.schedule.entity.MemberSchedule;
import com.otc.tinyclassroom.schedule.entity.Schedule;
import com.otc.tinyclassroom.schedule.exception.ScheduleErrorCode;
import com.otc.tinyclassroom.schedule.exception.ScheduleException;
import com.otc.tinyclassroom.schedule.repository.MemberScheduleRepository;
import com.otc.tinyclassroom.schedule.repository.ScheduleRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 스케줄 로직 서비스.
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ScheduleService {

    private final LectureRepository lectureRepository;
    private final MemberRepository memberRepository;
    private final MemberClassRoomRepository memberClassRoomRepository;
    private final ScheduleRepository scheduleRepository;
    private final MemberScheduleRepository memberScheduleRepository;
    private final ApplicationEventPublisher publisher;
    private final JwtProvider jwtProvider;

    /**
     * 스케줄 조회 메서드.
     *
     * @param memberId     스케줄을 조회할 멤버의 pk.
     * @param scheduleDate 스케줄을 조회하려 하는 주(week)의 월요일 날짜.
     * @return 스케줄 리스트 반환.
     */
    @Transactional(readOnly = true)
    public ScheduleListResponseDto getScheduleList(String memberId, LocalDate scheduleDate) {
        // 멤버 검증
        Member member = getMemberByMemberId(memberId);
        // 가장 최근의 멤버 클래스룸 검증 및 선생님 확인
        Member teacher = getClassroomTeacher(member);
        // 스케줄 리스트 가져오기
        List<ScheduleListDto> scheduleList = scheduleRepository.findScheduleListById(memberId,
            teacher.getMemberId(), scheduleDate);
        return ScheduleListResponseDto.of(scheduleDate, scheduleList);
    }

    /**
     * 스케줄 삽입 메서드.
     *
     * @param requestDto 추가될 스케줄의 정보. 강의 pk이 저장되어 있음.
     */
    public void insertSchedule(ScheduleInsertRequestDto requestDto) {

        // 적용하려는 lecture가 존재하지 않은 경우 에러 발생.
        Lecture lecture = getLectureById(requestDto.lectureId());

        // 추가하려는 lecture가 승인되지 않은 경우 Exception 발생.
        if (!lecture.getApproved().equals(LectureApprovalStatusType.APPROVED)) {
            throw new ScheduleException(ScheduleErrorCode.NOT_ACCEPT_LECTURE);
        }

        // 추가하려는 강의가 특강인 경우 request의 날짜와 교시가 특강의 것과 정확히 일치해야 한다.
        if (lecture.getLectureType().equals(LectureType.SPECIAL_LECTURE)) {
            if (!lecture.getDate().equals(requestDto.scheduleDate()) || !(lecture.getTimeTable()
                == requestDto.timeTable())) {
                throw new ScheduleException(ScheduleErrorCode.NOT_ACCURATE_LECTURE);
            }
        }

        // 현재 사용자가 존재하지 않는 사용자인 경우 exception 발생.
        Long currentUserId = jwtProvider.getCurrentMemberId();
        Member member = getMemberById(currentUserId);

        // 현재 사용자가 추가하려는 강의가 정규 강의일 경우 Exception 발생.
        if (lecture.getLectureType().equals(LectureType.REGULAR_LECTURE) && member.getRole()
            .equals(Role.ROLE_STUDENT)) {
            throw new ScheduleException(ScheduleErrorCode.NO_AUTH_SCHEDULE_INSERT);
        }

        // 그 멤버가 배정된 반에 할당된 선생이 없을 경우 Exception 발생.
        Member teacher = getClassroomTeacher(member);

        // 사용자 role에 따라 삭제 가능여부를 판단.
        boolean deletable = member.getRole() == Role.ROLE_STUDENT;

        // 시간표에 있는지 확인하기.
        scheduleRepository.findScheduleByMemberIdAndScheduleDateAndTimeTable(
            member.getMemberId(),
            teacher.getMemberId(),
            requestDto.scheduleDate(),
            requestDto.timeTable()
        ).ifPresent(e -> {
            throw new ScheduleException(ScheduleErrorCode.ALREADY_EXISTED_SCHEDULE);
        });

        Schedule schedule = Schedule.of(
            member,
            lecture,
            requestDto.scheduleDate(),
            requestDto.scheduleDate().getDayOfWeek().getValue() - 1,
            requestDto.timeTable(),
            deletable
        );

        scheduleRepository.save(schedule);
        memberScheduleRepository.save(MemberSchedule.of(member, schedule));
        // schedule type에 따라 count 하는 것 querydsl로 만들기
        publisher.publishEvent(new LectureBadgeEvent(currentUserId));
    }

    /**
     * 스케줄 삭제 메소드.
     *
     * @param id 스케줄의 pk 값.
     */
    public void deleteSchedule(Long id) {

        // 삭제하려는 스케줄이 존재하지 않는 경우 exception 발생.
        ScheduleCheckDto schedule = scheduleRepository.findScheduleById(id)
            .orElseThrow(() -> new ScheduleException(ScheduleErrorCode.NOT_EXIST_SCHEDULE)
            );

        // 현재 사용자가 존재하지 않는 사용자인 경우 exception 발생.
        Long currentUserId = jwtProvider.getCurrentMemberId();
        Member currentMember = getMemberById(currentUserId);

        // 스케줄 삭제를 시도하는 사람이 학생인 경우.
        if (currentMember.getRole() == Role.ROLE_STUDENT) {
            // 스케줄을 추가한 사람이 관리자이거나, 스케줄을 추가한 사람과 삭제하려는 사람이 다른 경우 exception 발생.
            if (!schedule.deletable() || !Objects.equals(schedule.userId(),
                currentMember.getId())) {
                throw new ScheduleException(ScheduleErrorCode.NO_AUTH_SCHEDULE_DELETE);
            }
        }

        scheduleRepository.deleteScheduleById(id);
        memberScheduleRepository.deleteByMemberIdAndScheduleId(currentUserId, id);

    }

    /**
     * 현재 수강해야 할 스케줄 조회 메서드.
     */
    public ScheduleUrlResponseDto getCurrentScheduleWithUrl() {

        // 현재 사용자가 존재하지 않는 사용자인 경우 exception 발생.
        Long currentUserId = jwtProvider.getCurrentMemberId();
        Member member = getMemberById(currentUserId);
        // 그 멤버가 배정된 반에 할당된 선생이 없을 경우 Exception 발생.
        Member teacher = getClassroomTeacher(member);
        int timeTable = 0;
        // 10분 전부터 보여주기 수강해야할 스케줄을 보여주기 위해 10분 추가한 뒤 시간을 계산한다.
        // ex) 08시 53분에서 10분을 더하면 09시 3분 => 즉 08시 53분도 09시로 계산.
        int currentHour = LocalDateTime.now().getHour();
        if (currentHour < 9){
            timeTable = 0;
        }else if (currentHour >= 16){
            timeTable = 5;
        }else{
            int nextHour = LocalDateTime.now().plusMinutes(10).getHour();
            if (nextHour <= 12){
                timeTable = nextHour - 9;
            }else {
                timeTable = nextHour - 10;
            }
        }

        ScheduleDetailDto scheduleDetailDto = scheduleRepository.findSoonScheduleDetailByMemberIdAndScheduleDateAndTimeTable(
            member.getMemberId(),
            teacher.getMemberId(),
            LocalDate.now(),
            timeTable
        ).orElse(null);

        ScheduleUrlResponseDto scheduleUrlResponseDto = null;

        // 지금 참여해야 할 강의가 없으면 null 반환.
        if (Objects.isNull(scheduleDetailDto)) {
            return scheduleUrlResponseDto;
        }

        if (scheduleDetailDto.lectureType() == LectureType.SPECIAL_LECTURE) {
            // 참여해야 할 강의가 특강이라면 lectureUrl 사용.
            scheduleUrlResponseDto = ScheduleUrlResponseDto.from(scheduleDetailDto);
        } else {
            // 현재 학생의 반 찾아오기.
            List<MemberClassRoom> classRooms =  member.getMemberClassRooms();
            if (classRooms.isEmpty()){
                throw new ScheduleException(ScheduleErrorCode.CLASSROOM_NOT_ASSIGNED);
            }
            ClassRoom classRoom =  classRooms.get(classRooms.size() - 1).getClassRoom();

            // 참여해야 할 강의가 특강이 아니라면 roomUrl 사용.
            scheduleUrlResponseDto = ScheduleUrlResponseDto.createByRoomUrl(scheduleDetailDto, classRoom.getRoomUrl());
        }

        return scheduleUrlResponseDto;
    }

    private Lecture getLectureById(Long lectureId) {
        return lectureRepository.findById(lectureId)
            .orElseThrow(() -> new ScheduleException(ScheduleErrorCode.NOT_EXIST_LECTURE));
    }

    private Member getMemberById(Long id) {
        return memberRepository.findById(id)
            .orElseThrow(() -> new ScheduleException(ScheduleErrorCode.NOT_EXIST_SCHEDULE));
    }

    private Member getMemberByMemberId(String memberId) {
        return memberRepository.findByMemberId(memberId)
            .orElseThrow(() -> new ScheduleException(ScheduleErrorCode.NOT_EXIST_MEMBER));
    }

    private Member getClassroomTeacher(Member member) {
        List<MemberClassRoom> memberClassRooms = member.getMemberClassRooms();
        if (memberClassRooms.isEmpty()) {
            throw new ScheduleException(ScheduleErrorCode.CLASSROOM_NOT_ASSIGNED);
        }

        MemberClassRoom memberClassroom = memberClassRooms.get(memberClassRooms.size() - 1);
        List<Member> teachers = memberClassRoomRepository.findMemberByClassRoomIdAndRole(
            memberClassroom.getClassRoom().getId(), Role.ROLE_TEACHER);

        return teachers.stream().findFirst()
            .orElseThrow(() -> new ScheduleException(ScheduleErrorCode.NOT_EXIST_TEACHER));
    }
}
