package com.otc.tinyclassroom.schedule.entity;

import static jakarta.persistence.FetchType.LAZY;

import com.otc.tinyclassroom.lecture.entity.Lecture;
import com.otc.tinyclassroom.member.entity.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Getter;

/**
 * 학생 스케줄 엔티티 정의.
 */
@Getter
@Entity
@Table
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    private Member member;

    @ManyToOne(fetch = LAZY)
    private Lecture lecture;

    @NotNull
    private LocalDate scheduleDate;

    @NotNull
    private int dayOfWeek;

    @NotNull
    private int timeTable;

    @NotNull
    private Boolean deletable;

    private LocalDateTime deletedAt;

    protected Schedule() {

    }

    /**
     * Schedule 기본 생성자.
     */
    private Schedule(Member member, Lecture lecture, LocalDate scheduleDate, int dayOfWeek, int timeTable, Boolean deletable) {
        this.member = member;
        this.lecture = lecture;
        this.scheduleDate = scheduleDate;
        this.dayOfWeek = dayOfWeek;
        this.timeTable = timeTable;
        this.deletable = deletable;
    }

    /**
     * Schedule Entity 생성 메서드.
     */
    public static Schedule of(Member member, Lecture lecture, LocalDate scheduleDate, int dayOfWeek, int timeTable, Boolean deletable) {
        return new Schedule(member, lecture, scheduleDate, dayOfWeek, timeTable, deletable);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Schedule schedule)) {
            return false;
        }
        return id != null && id.equals(schedule.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
