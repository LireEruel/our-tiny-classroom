package com.otc.tinyclassroom.mypage.service;

import com.otc.tinyclassroom.global.security.jwt.JwtProvider;
import com.otc.tinyclassroom.member.entity.Member;
import com.otc.tinyclassroom.member.exception.MemberErrorCode;
import com.otc.tinyclassroom.member.exception.MemberException;
import com.otc.tinyclassroom.member.repository.MemberRepository;
import com.otc.tinyclassroom.mypage.dto.response.GoalResponseDto;
import com.otc.tinyclassroom.mypage.entity.Goal;
import com.otc.tinyclassroom.mypage.exception.MyPageErrorCode;
import com.otc.tinyclassroom.mypage.exception.MyPageException;
import com.otc.tinyclassroom.mypage.repository.GoalRepository;
import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 목표 관련 서비스.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class GoalService {

    private final GoalRepository goalRepository;
    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;

    /**
     * 목표를 추가하거나 수정한다.
     */
    public void saveGoal(String content) {
        Long currentMemberId = jwtProvider.getCurrentMemberId();
        Member member = memberRepository.findById(currentMemberId).orElseThrow(() -> new MemberException(MemberErrorCode.NOT_FOUND_MEMBER));
        LocalDateTime now = LocalDateTime.now();
        // 이번주의 주차 가져오기
        int year = now.getYear();
        int week = now.get(WeekFields.of(Locale.getDefault()).weekOfYear());

        // 있다면 수정, 없다면 추가
        Optional<Goal> existingGoal = goalRepository.findByMemberIdAndYearAndWeek(currentMemberId, year, week);

        if (existingGoal.isPresent()) {
            existingGoal.get().setContent(content);
        } else {
            Goal goal = Goal.of(member, year, week, content, 0);
            goalRepository.save(goal);
        }
    }

    /**
     * 이번주 목표를 가져온다.
     */
    public GoalResponseDto getThisWeekGoal() {
        Long currentMemberId = jwtProvider.getCurrentMemberId();
        Member member = memberRepository.findById(currentMemberId).orElseThrow(() -> new MemberException(MemberErrorCode.NOT_FOUND_MEMBER));
        LocalDateTime now = LocalDateTime.now();
        // 이번주의 주차 가져오기
        int year = now.getYear();
        int week = now.get(WeekFields.of(Locale.getDefault()).weekOfYear());

        Optional<Goal> existingGoal = goalRepository.findByMemberIdAndYearAndWeek(currentMemberId, year, week);

        if (existingGoal.isPresent()) {
            return GoalResponseDto.of(existingGoal.get().getContent(), existingGoal.get().getAchievement(), year, week);
        } else {
            return GoalResponseDto.of("", 0, 0, 0);
        }
    }

    /**
     * 목표 달성도를 수정한다.
     */
    public void updateGoalAchievement(Long goalId, int achievement) {

        Goal goal = goalRepository.findById(goalId).orElseThrow(
            () -> new MyPageException(MyPageErrorCode.NO_GOAL_FOUND)
        );
        goal.setAchievement(achievement);
    }

    /**
     * 지난 목표들을 조회한다.
     */
    public GoalResponseDto getPastGoals(int year, int week) {
        Long currentMemberId = jwtProvider.getCurrentMemberId();
        Goal lastGoal = goalRepository.findByMemberIdAndYearAndWeek(currentMemberId, year, week).orElseThrow(
            () -> new MyPageException(MyPageErrorCode.NO_GOAL_FOUND)
        );

        return GoalResponseDto.of(lastGoal.getContent(), lastGoal.getAchievement(), year, week);
    }
}
