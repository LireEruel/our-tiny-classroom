package com.otc.tinyclassroom.mypage.event;

import com.otc.tinyclassroom.member.entity.Member;
import com.otc.tinyclassroom.member.exception.MemberErrorCode;
import com.otc.tinyclassroom.member.exception.MemberException;
import com.otc.tinyclassroom.member.repository.MemberRepository;
import com.otc.tinyclassroom.mypage.entity.Badge;
import com.otc.tinyclassroom.mypage.entity.MemberBadge;
import com.otc.tinyclassroom.mypage.exception.BadgeErrorCode;
import com.otc.tinyclassroom.mypage.exception.BadgeException;
import com.otc.tinyclassroom.mypage.repository.BadgeRepository;
import com.otc.tinyclassroom.mypage.repository.MemberBadgeRepository;
import com.otc.tinyclassroom.schedule.repository.MemberScheduleRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BadgeEventListener {

    private final MemberBadgeRepository memberBadgeRepository;
    private final BadgeRepository badgeRepository;
    private final MemberRepository memberRepository;
    private final MemberScheduleRepository memberScheduleRepository;

    @EventListener
    public void justBestowBadge(NoConditionBadgeEvent badgeEvent) {
        if (memberBadgeRepository.findByBadgeIdAndMemberId(badgeEvent.badgeId(), badgeEvent.memberId()).isEmpty()) {
            Badge badge = badgeRepository.findById(badgeEvent.badgeId()).orElseThrow(
                () -> new BadgeException(BadgeErrorCode.NOT_FOUND_BADGE)
            );
            Member member = memberRepository.findById(badgeEvent.memberId()).orElseThrow(
                () -> new MemberException(MemberErrorCode.NOT_FOUND_MEMBER)
            );
            memberBadgeRepository.save(MemberBadge.of(badge, member));
        }
    }

    @EventListener
    public void lectureBestowBadge(LectureBadgeEvent badgeEvent) {

        Member member = memberRepository.findById(badgeEvent.memberId()).orElseThrow(
            () -> new MemberException(MemberErrorCode.NOT_FOUND_MEMBER)
        );

        // 도합 10회가 넘으면 뱃지 지급
        if (memberBadgeRepository.findByBadgeIdAndMemberId(4L, badgeEvent.memberId()).isEmpty()) {
            if (memberScheduleRepository.countByMemberId(badgeEvent.memberId()) > 10) {
                Badge badge = badgeRepository.findById(4L).orElseThrow(
                    () -> new BadgeException(BadgeErrorCode.NOT_FOUND_BADGE)
                );
                memberBadgeRepository.save(MemberBadge.of(badge, member));
            }
        }

        // 특정과목이 5회를 넘으면 뱃지 지급
        if (memberBadgeRepository.findByBadgeIdAndMemberId(5L, badgeEvent.memberId()).isEmpty()) {
            List<Long> counts = memberScheduleRepository.countByMemberIdGroupByLectureCategoryType(badgeEvent.memberId());
            if (counts.get(0) >= 5) {
                Badge badge = badgeRepository.findById(5L).orElseThrow(
                    () -> new BadgeException(BadgeErrorCode.NOT_FOUND_BADGE)
                );
                memberBadgeRepository.save(MemberBadge.of(badge, member));
            }
        }
    }
}
