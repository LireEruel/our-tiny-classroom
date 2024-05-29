package com.otc.tinyclassroom.schedule.entity;

import static jakarta.persistence.FetchType.LAZY;

import com.otc.tinyclassroom.lecture.entity.type.LectureCategoryType;
import com.otc.tinyclassroom.member.entity.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * Member와 Schedule 간 Mapping을 위한 테이블.
 */
@Entity
@Getter
@Table(name = "member_schedule")
public class MemberSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    private Member member;

    @ManyToOne(fetch = LAZY)
    private Schedule schedule;

    @Enumerated(EnumType.STRING)
    private LectureCategoryType lectureCategoryType;

    protected MemberSchedule() {
    }

    private MemberSchedule(Member member, Schedule schedule) {
        this.member = member;
        this.schedule = schedule;
        this.lectureCategoryType = schedule.getLecture().getLectureCategoryType();
    }

    public static MemberSchedule of(Member member, Schedule schedule) {
        return new MemberSchedule(member, schedule);
    }
}
