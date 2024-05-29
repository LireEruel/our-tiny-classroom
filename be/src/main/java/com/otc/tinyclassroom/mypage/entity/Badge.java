package com.otc.tinyclassroom.mypage.entity;

import com.otc.tinyclassroom.member.entity.MemberClassRoom;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;

/**
 * 뱃지 엔티티.
 */
@Entity
@Getter
@Table(name = "badge")
public class Badge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String imageUrl;

    @OneToMany(mappedBy = "badge")
    private Set<MemberBadge> memberBadges = new HashSet<>();

    protected Badge() {
    }

    public void setMemberBadges(Set<MemberBadge> memberBadges) {
        this.memberBadges = memberBadges;
    }

    public Badge(String imageUrl, Set<MemberBadge> memberBadges) {
        this.imageUrl = imageUrl;
        this.memberBadges = memberBadges;
    }



}
