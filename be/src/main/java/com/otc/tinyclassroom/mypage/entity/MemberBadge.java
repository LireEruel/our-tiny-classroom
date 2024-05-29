package com.otc.tinyclassroom.mypage.entity;

import static jakarta.persistence.FetchType.LAZY;

import com.otc.tinyclassroom.member.entity.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * 멤버, 뱃지 ManyToMany 해결을 위한 객체.
 */
@Getter
@Entity
@Table(name = "member_badge")
public class MemberBadge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    private Badge badge;

    @ManyToOne(fetch = LAZY)
    private Member member;

    protected MemberBadge() {
    }

    private MemberBadge(Badge badge, Member member) {
        this.badge = badge;
        this.member = member;
    }

    public static MemberBadge of(Badge badge, Member member) {
        return new MemberBadge(badge, member);
    }
}