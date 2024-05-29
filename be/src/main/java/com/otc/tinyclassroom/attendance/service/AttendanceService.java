package com.otc.tinyclassroom.attendance.service;

import com.otc.tinyclassroom.attendance.dto.response.AttendanceCheckInResponseDto;
import com.otc.tinyclassroom.attendance.dto.response.AttendanceCheckOutResponseDto;
import com.otc.tinyclassroom.attendance.dto.response.DailyAttendanceResponseDto;
import com.otc.tinyclassroom.attendance.dto.response.MonthlyAttendanceResponseDto;
import com.otc.tinyclassroom.attendance.entity.Attendance;
import com.otc.tinyclassroom.attendance.entity.type.AttendanceStatus;
import com.otc.tinyclassroom.attendance.exception.AttendanceErrorCode;
import com.otc.tinyclassroom.attendance.exception.AttendanceException;
import com.otc.tinyclassroom.attendance.repository.AttendanceRepository;
import com.otc.tinyclassroom.global.security.jwt.JwtProvider;
import com.otc.tinyclassroom.member.entity.Member;
import com.otc.tinyclassroom.member.exception.MemberErrorCode;
import com.otc.tinyclassroom.member.exception.MemberException;
import com.otc.tinyclassroom.member.repository.MemberRepository;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 출석 Service.
 */
@Service
@AllArgsConstructor
@Slf4j
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;
    // Check-in 및 Check-out 시간을 구성하는 값들
    private static final int CHECKIN_START_TIME = 8;
    private static final int CHECKIN_END_TIME = 13;
    private static final int BASE_TIME = 9;

    /**
     * 출석 시간을 기록한다.
     *
     * @return 출석시간, 출석 상태
     */
    @Transactional
    public AttendanceCheckInResponseDto checkIn() {
        LocalDateTime now = LocalDateTime.now();
        if (now.toLocalDate().getDayOfWeek() == DayOfWeek.SATURDAY || now.toLocalDate().getDayOfWeek() == DayOfWeek.SUNDAY) {
            throw new AttendanceException(AttendanceErrorCode.WEEKEND_CHECKOUT_NOT_ALLOWED);
        }
        Long memberId = jwtProvider.getCurrentMemberId();
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new MemberException(MemberErrorCode.NOT_FOUND_MEMBER));
        // 회원이 오늘 이미 등교한 경우 예외 처리
        if (hasCheckedInToday(memberId)) {
            throw new AttendanceException(AttendanceErrorCode.ALREADY_ATTEND);
        }
        // 등교 시간을 확인
        validateCheckInTime(now);
        AttendanceStatus status = determineAttendanceStatus(now);
        // 등교를 위한 Attendance 엔티티 생성 및 저장
        Attendance attendance = Attendance.of(member, now, null, status);
        attendanceRepository.save(attendance);
        return AttendanceCheckInResponseDto.from(now, status.ordinal());
    }

    private void validateCheckInTime(LocalDateTime now) {
        int hour = now.getHour();
        // 현재 시간이 허용된 등교 시간 내에 있는지 확인
        if (hour < CHECKIN_START_TIME || hour >= CHECKIN_END_TIME) {
            throw new AttendanceException(AttendanceErrorCode.NOT_CHECK_IN_TIME);
        }
    }

    private boolean hasCheckedInToday(Long memberId) {
        LocalDateTime todayStart = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0)
            .withNano(0);
        LocalDateTime todayEnd = todayStart.plusDays(1);

        // 회원의 등교 기록이 오늘의 시작과 끝 사이에 있는지 확인
        return attendanceRepository.existsByMemberIdAndCheckInBetween(memberId,
            todayStart, todayEnd);
    }

    private AttendanceStatus determineAttendanceStatus(LocalDateTime now) {
        int checkInHour = now.getHour();
        return (checkInHour < BASE_TIME) ? AttendanceStatus.ATTENDANCE : AttendanceStatus.LATE;
    }

    /**
     * 퇴실을 처리한다.
     *
     * @return 퇴실 시간, 출결 상태
     */
    @Transactional
    public AttendanceCheckOutResponseDto checkOut() {
        LocalDateTime now = LocalDateTime.now();
        Long memberId = jwtProvider.getCurrentMemberId();
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new MemberException(MemberErrorCode.NOT_FOUND_MEMBER));

        // 회원이 오늘 등교하지 않았는데 하교를 요청한 경우 예외 처리
        if (!hasCheckedInToday(memberId)) {
            throw new AttendanceException(AttendanceErrorCode.NOT_CHECKED_IN);
        }
        LocalDate date = LocalDate.now();
        // 오늘 등교한 출결 기록을 가져와서 업데이트
        Attendance attendance = attendanceRepository.findByMemberIdAndCheckInBetween(
            member.getId(),
            LocalDateTime.of(date, LocalTime.MIN),
            LocalDateTime.now()
        ).orElseThrow(() -> new AttendanceException(AttendanceErrorCode.NOT_CHECKED_IN));
        // 하교시간을 확인하고 status 지정 ( 18시 넘을경우 제한 )
        AttendanceStatus status = validateCheckOutTimeAndDetermineStatus(now, attendance.getCheckIn());
        // 15시가 넘었을 경우 기존 status 를 반환
        if (status == null) {
            status = attendance.getStatus();
        }
        // 체크아웃 시간을 설정하고 업데이트된 출결 기록을 저장
        attendance.updateCheckOutTime(now, status);
        attendanceRepository.save(attendance);
        return AttendanceCheckOutResponseDto.from(now, status.ordinal());
    }

    private AttendanceStatus validateCheckOutTimeAndDetermineStatus(LocalDateTime now, LocalDateTime checkInTime) {
        int hour = now.getHour();

        // 현재 시간이 허용된 하교 시간 이후인지 확인
        int checkOutEndTime = 18;
        if (hour >= checkOutEndTime) {
            throw new AttendanceException(AttendanceErrorCode.NOT_CHECK_OUT_TIME);
        }
        // 등교 시간과 하교 시간의 차이를 계산
        Duration duration = Duration.between(checkInTime, now);
        // 등교시간 차이와 하교 시간을 계산하여 값 지정
        // TODO: 스케줄러 도입 -> 매일 18시 checkOut 하지 않을 시 결석으로 전환
        // 차이가 4시간 이내면 결석으로 표시
        if (duration.toHours() < 4) {
            return AttendanceStatus.ABSENCE;
        } else if (duration.toHours() < 15) {
            // 15시가 안됐으면 조퇴로 표시
            return AttendanceStatus.LEAVE_EARLY;
        } else {
            // 15시가 넘었으면 null 표시
            return null;
        }
    }


    /**
     * 하루의 출석을 확인한다.
     *
     * @param memberId 조회할 멤버
     * @param date     오늘날짜
     * @return 출석시간, 퇴실시간, 상태
     */
    @Transactional
    public DailyAttendanceResponseDto getAttendanceTimeOnDate(String memberId, LocalDateTime date) {
        LocalDate today = LocalDate.now();
        List<Attendance> attendanceListOnDate = attendanceRepository.findByMemberIdAndCheckInBetweenDay(
            memberId,
            LocalDateTime.of(today, LocalTime.MIN),
            LocalDateTime.now()
        );

        if (!attendanceListOnDate.isEmpty()) {
            Attendance attendance = attendanceListOnDate.get(0);
            LocalDateTime checkIn = attendance.getCheckIn();
            LocalDateTime checkOut = attendance.getCheckOut() != null ? attendance.getCheckOut() : null;
            int status = attendance.getStatus().ordinal();

            DailyAttendanceResponseDto.AttendanceDayInfo attendanceInfo = new DailyAttendanceResponseDto.AttendanceDayInfo(date.toLocalDate(), checkIn, checkOut, status);
            return new DailyAttendanceResponseDto(attendanceInfo);
        } else {
            // checkIn이 없을 경우 모든 필드를 null로 출력
            DailyAttendanceResponseDto.AttendanceDayInfo attendanceDayInfo = new DailyAttendanceResponseDto.AttendanceDayInfo(date.toLocalDate(), null, null, -1);
            return new DailyAttendanceResponseDto(attendanceDayInfo);
        }
    }

    /**
     * 회원의 한달 출석을 조회한다.
     *
     * @param memberId 조회할 회원
     * @param year     조회할 연도
     * @param month    조회할 월
     * @return 한달간의 출석기록
     */
    @Transactional
    public MonthlyAttendanceResponseDto getAttendanceTimeOnMonth(String memberId, int year, int month) {
        LocalDateTime startOfMonth = LocalDateTime.of(year, month, 1, 0, 0, 0);
        LocalDateTime endOfMonth = startOfMonth.plusMonths(1);
        List<Attendance> monthlyAttendanceList = attendanceRepository.findByMemberIdAndCheckInBetweenMonth(
            memberId,
            startOfMonth,
            endOfMonth
        );

        List<MonthlyAttendanceResponseDto.AttendanceMonthInfo> attendanceList = new ArrayList<>();
        for (Attendance attendance : monthlyAttendanceList) {
            LocalDateTime checkIn = attendance.getCheckIn();
            LocalDateTime checkOut = attendance.getCheckOut() != null ? attendance.getCheckOut() : null;
            int status = attendance.getStatus().ordinal();
            LocalDate date = checkIn.toLocalDate();
            MonthlyAttendanceResponseDto.AttendanceMonthInfo attendanceMonthInfo = new MonthlyAttendanceResponseDto.AttendanceMonthInfo(date, checkIn, checkOut, status);
            attendanceList.add(attendanceMonthInfo);
        }

        return new MonthlyAttendanceResponseDto(attendanceList);
    }

    /**
     * 출석률을 구한다.
     */
    @Transactional
    public MonthlyAttendanceResponseDto getAttendanceRate(String memberId, int year, int month) {
        LocalDateTime startOfMonth = LocalDateTime.of(year, month, 1, 0, 0, 0);
        LocalDateTime endOfMonth = startOfMonth.plusMonths(1);
        List<Attendance> monthlyAttendanceList = attendanceRepository.findByMemberIdAndCheckInBetweenMonth(
            memberId,
            startOfMonth,
            endOfMonth
        );

        List<MonthlyAttendanceResponseDto.AttendanceMonthInfo> attendanceList = new ArrayList<>();
        for (Attendance attendance : monthlyAttendanceList) {
            LocalDateTime checkIn = attendance.getCheckIn();
            LocalDateTime checkOut = attendance.getCheckOut() != null ? attendance.getCheckOut() : null;
            int status = attendance.getStatus().ordinal();
            LocalDate date = checkIn.toLocalDate();
            MonthlyAttendanceResponseDto.AttendanceMonthInfo attendanceMonthInfo = new MonthlyAttendanceResponseDto.AttendanceMonthInfo(date, checkIn, checkOut, status);
            attendanceList.add(attendanceMonthInfo);
        }

        return new MonthlyAttendanceResponseDto(attendanceList);
    }

    /**
     * Token 에서 memberId 가져오기.
     */
    public Long getMemberId() {
        return jwtProvider.getCurrentMemberId();
    }
}
