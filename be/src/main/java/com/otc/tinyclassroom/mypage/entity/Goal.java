package com.otc.tinyclassroom.mypage.entity;

import static jakarta.persistence.FetchType.LAZY;

import com.otc.tinyclassroom.member.entity.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * 주차별 목표 엔티티.
 */
@Getter
@Entity
@Table(name = "goal")
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = LAZY)
    private Member member;
    @Column(nullable = false)
    private int year;
    @Column(nullable = false)
    private int week;
    @Setter
    @Column(nullable = false)
    private String content;
    @Setter
    @Column(nullable = false)
    private int achievement;

    public static Goal of(Member member, int year, int week, String content, int achievement) {
        return new Goal(member, year, week, content, achievement);
    }

    protected Goal() {
    }

    private Goal(Member member, int year, int week, String content, int achievement) {
        this.member = member;
        this.year = year;
        this.week = week;
        this.content = content;
        this.achievement = achievement;
    }
}
