package com.otc.tinyclassroom.member.service;

import com.otc.tinyclassroom.global.security.jwt.JwtProvider;
import com.otc.tinyclassroom.lecture.dto.request.RoomCreateRequestDto;
import com.otc.tinyclassroom.lecture.service.WebClientService;
import com.otc.tinyclassroom.member.dto.ClassRoomDto;
import com.otc.tinyclassroom.member.dto.request.RandomAssignmentRequestDto;
import com.otc.tinyclassroom.member.dto.request.UserListRandomAssignmentMemberRequestDto;
import com.otc.tinyclassroom.member.dto.response.MemberClassRoomResponseDto;
import com.otc.tinyclassroom.member.entity.ClassRoom;
import com.otc.tinyclassroom.member.entity.Member;
import com.otc.tinyclassroom.member.entity.MemberClassRoom;
import com.otc.tinyclassroom.member.entity.Role;
import com.otc.tinyclassroom.member.exception.ClassAssignmentErrorCode;
import com.otc.tinyclassroom.member.exception.ClassAssignmentException;
import com.otc.tinyclassroom.member.exception.MemberErrorCode;
import com.otc.tinyclassroom.member.exception.MemberException;
import com.otc.tinyclassroom.member.repository.ClassRoomRepository;
import com.otc.tinyclassroom.member.repository.MemberClassRoomRepository;
import com.otc.tinyclassroom.member.repository.MemberRepository;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

/**
 * 반 편성 관련 service.
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ClassAssignmentService {

    private final ClassRoomRepository classRoomRepository;
    private final MemberRepository memberRepository;
    private final MemberClassRoomRepository memberClassRoomRepository;
    private final WebClientService webClientService;
    private final JwtProvider jwtProvider;

    /**
     * 반 생성 메서드.
     */
    public ClassRoomDto createClassRoom(ClassRoomDto classRoomDto) {

        LocalDateTime nbfTime = LocalDateTime.of(classRoomDto.year(), Month.JANUARY, 1, 0, 0);
        long nbf = nbfTime.atZone(ZoneId.systemDefault()).toEpochSecond();
        LocalDateTime expTime = LocalDateTime.of(classRoomDto.year(), Month.DECEMBER, 31, 23, 59);
        long exp = expTime.atZone(ZoneId.systemDefault()).toEpochSecond();

        int maxParticipants = 20;
        String url = createClassRoomUrlIfNeeded(classRoomDto, nbf, exp, maxParticipants);

        // ClassRoom 객체 생성 로직 중복 제거
        ClassRoom classRoom = ClassRoom.of(classRoomDto.year(), classRoomDto.grade(), classRoomDto.number(), url);
        ClassRoom savedClassRoom = classRoomRepository.save(classRoom);

        return ClassRoomDto.from(savedClassRoom);
    }


    /**
     * 반 편성 메서드.
     */
    public List<MemberClassRoomResponseDto> placeMembers(List<Long> memberIds, Long classRoomId) {
        ClassRoom classRoom = getClassRoomById(classRoomId);
        List<MemberClassRoomResponseDto> members = new ArrayList<>();
        List<MemberClassRoom> result = new ArrayList<>();
        for (Long memberId : memberIds) {
            Member member = getMemberById(memberId);
            // 연관관계 맵핑 테이블에 추가
            MemberClassRoom memberClassRoom = MemberClassRoom.of(member, classRoom);
            result.add(memberClassRoom);
            // 업데이트된 멤버 정보를 DTO로 변환하여 리스트에 추가
            members.add(MemberClassRoomResponseDto.from(memberClassRoom));
        }
        memberClassRoomRepository.saveAll(result);
        return members;
    }

    /**
     * 반 수정 메서드.
     */
    public MemberClassRoomResponseDto updateMemberClass(Long memberId, Long classRoomId) {
        // 수정해야 하는 멤버와 반
        Member member = getMemberById(memberId);
        ClassRoom classRoom = getClassRoomById(classRoomId);

        // 멤버의 현재 반을 수정한다.
        List<MemberClassRoom> classRooms = member.getMemberClassRooms();

        if (classRooms.isEmpty()) {
            throw new ClassAssignmentException(ClassAssignmentErrorCode.CLASSROOM_NOT_ASSIGNED);
        }

        MemberClassRoom currentClass = classRooms.get(classRooms.size() - 1);
        currentClass.setClassRoom(classRoom);
        return MemberClassRoomResponseDto.from(currentClass);
    }

    /**
     * 반 인원 목록 조회.
     */
    public List<MemberClassRoomResponseDto> getMembersByClassRoom(Long classRoomId) {
        //TODO: QueryDSL로 변경
        List<Member> members = memberClassRoomRepository.findMemberByClassRoomIdAndRole(classRoomId, Role.ROLE_STUDENT);
        return members.stream().map(MemberClassRoomResponseDto::from).collect(Collectors.toList());
    }

    /**
     * 요청한 유저의 권한이 있는지 확인(선생, 관리자).
     */
    public void checkTeacherOrAdmin(Role userRole) {
        if (userRole != Role.ROLE_TEACHER && userRole != Role.ROLE_ADMIN) {
            throw new ClassAssignmentException(ClassAssignmentErrorCode.NO_AUTHORITY);
        }
    }


    /**
     * 랜덤으로 반 편성하기.
     */
    public List<MemberClassRoomResponseDto> randomAssignmentClassRooms(Long classRoomId) {

        ClassRoom classRoom = getClassRoomById(classRoomId);
        // targetClassRoomId에 해당하는 반 멤버 가져오기
        List<Member> members = memberClassRoomRepository.findMemberByClassRoomIdAndRole(classRoomId, Role.ROLE_STUDENT);
        List<ClassRoom> targetClassRooms = classRoomRepository.findClassRoomByGradeAndYearWithNonZeroNumber(classRoom.getGrade(), classRoom.getYear());

        // 멤버들을 랜덤한 순서로 섞기
        Collections.shuffle(members);

        List<MemberClassRoom> result = new ArrayList<>(members.size());
        // 남은 학생들에 대해 처음 반부터 다시 반복하여 배정
        int studentIndex = 0;
        for (Member student : members) {

            ClassRoom currentClassRoom = targetClassRooms.get((studentIndex % targetClassRooms.size()));
            MemberClassRoom memberClassRoom = MemberClassRoom.of(student, classRoom);
            result.add(memberClassRoom);

            // 다음 반으로 넘어가기
            studentIndex++;
        }
        memberClassRoomRepository.saveAll(result);
        // 저장된 멤버 엔터티들을 DTO로 변환하여 반환
        return result.stream()
            .map(MemberClassRoomResponseDto::from)
            .collect(Collectors.toList());
    }

    /**
     * 주어진 리스트에 대해 랜덤으로 반 편성하기.
     */
    public List<MemberClassRoomResponseDto> userListRandomAssignment(UserListRandomAssignmentMemberRequestDto dto) {
        List<RandomAssignmentRequestDto> randomAssignmentRequestDto = dto.userList();

        List<MemberClassRoom> result = new ArrayList<>();
        for (RandomAssignmentRequestDto listMember : randomAssignmentRequestDto) {
            List<ClassRoom> targetClassRooms = classRoomRepository.findClassRoomByGradeAndYearWithNonZeroNumber(listMember.grade(), LocalDateTime.now().getYear());
            int randomNumber = new Random().nextInt(targetClassRooms.size());

            Member student = memberRepository.findById(listMember.id()).orElseThrow(
                () -> new MemberException(MemberErrorCode.NOT_FOUND_MEMBER)
            );
            ClassRoom assignedClassRoom = targetClassRooms.get(randomNumber);
            MemberClassRoom memberClassRoom = MemberClassRoom.of(student, assignedClassRoom);
            result.add(memberClassRoom);

        }
        memberClassRoomRepository.saveAll(result);
        // 저장된 멤버 엔터티들을 DTO로 변환하여 반환
        return result.stream()
            .map(MemberClassRoomResponseDto::from)
            .collect(Collectors.toList());

    }

    private ClassRoom getClassRoomById(Long classRoomId) {
        return classRoomRepository.findById(classRoomId)
            .orElseThrow(() -> new ClassAssignmentException(
                ClassAssignmentErrorCode.CLASSROOM_NOT_FOUND));
    }

    private String createClassRoomUrlIfNeeded(ClassRoomDto classRoomDto, long nbf, long exp, int maxParticipants) {
        if (classRoomDto.number() == 0) {
            return null;
        }
        String identifier = classRoomDto.year() + "-" + classRoomDto.grade() + "-" + classRoomDto.number();
        Mono<String> urlMono = webClientService.createClassRoomUrl(RoomCreateRequestDto.of(identifier, nbf, exp, maxParticipants));

        // Mono<String>을 String으로 동기적으로 변환
        return urlMono.block();
    }

    private Member getMemberById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new ClassAssignmentException(
            ClassAssignmentErrorCode.MEMBER_NOT_FOUND));
    }

    public void tempAssignClassRoom(Long memberId, int grade) {
        Member member = getMemberById(memberId);
        ClassRoom classRoom = classRoomRepository.findTempClassRoomByGrade(grade).orElseThrow(
            () -> new ClassAssignmentException(ClassAssignmentErrorCode.CLASSROOM_NOT_FOUND)
        );
        MemberClassRoom memberClassRoom = MemberClassRoom.of(member, classRoom);
        memberClassRoomRepository.save(memberClassRoom);
    }
}

