package com.otc.tinyclassroom.lecture.entity;

import com.otc.tinyclassroom.lecture.entity.type.LectureApprovalStatusType;
import com.otc.tinyclassroom.lecture.entity.type.LectureCategoryType;
import com.otc.tinyclassroom.lecture.entity.type.LectureType;
import com.otc.tinyclassroom.member.entity.Member;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

/**
 * 강의 엔티티 정의.
 */
@Getter
@Entity
@Table
public class Lecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Member member;

    @Setter
    @NotNull
    private String title;

    @Setter
    @NotNull
    private String description;

    @Setter
    @NotNull
    private int dayOfWeek;

    @Setter
    @NotNull
    private int timeTable;

    @Setter
    @NotNull
    @Enumerated(EnumType.STRING)
    private LectureType lectureType;

    @Setter
    @NotNull
    @Enumerated(EnumType.STRING)
    private LectureCategoryType lectureCategoryType;

    @Setter
    private String lectureUrl;

    @Setter
    @Enumerated(EnumType.STRING)
    private LectureApprovalStatusType approved; // 특강 승인 여부

    @Setter
    private LocalDate date;

    protected Lecture() {

    }

    /**
     * Lecture 기본 생성자.
     */
    private Lecture(Member member, String title, String description, int dayOfWeek, int timeTable, LectureType lectureType, LectureCategoryType lectureCategoryType, String lectureUrl,
        LectureApprovalStatusType approved, LocalDate date) {
        this.member = member;
        this.title = title;
        this.description = description;
        this.dayOfWeek = dayOfWeek;
        this.timeTable = timeTable;
        this.lectureType = lectureType;
        this.lectureCategoryType = lectureCategoryType;
        this.lectureUrl = lectureUrl;
        this.approved = approved;
        this.date = date;
    }

    /**
     * Lecture Entity 생성 메서드.
     */
    public static Lecture of(Member member, String title, String description, int dayOfWeek, int timeTable, LectureType lectureType, LectureCategoryType lectureCategoryType, String lectureUrl,
        LectureApprovalStatusType approved, LocalDate date) {
        return new Lecture(member, title, description, dayOfWeek, timeTable, lectureType, lectureCategoryType, lectureUrl, approved, date);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Lecture lecture)) {
            return false;
        }
        return id != null && id.equals(lecture.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
