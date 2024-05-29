package com.otc.tinyclassroom.community.entity;

import static jakarta.persistence.FetchType.LAZY;

import com.otc.tinyclassroom.member.entity.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * 게시글 좋아요 클래스.
 */
@Getter
@Entity
@Table(name = "heart")
public class Heart extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    private Member member;

    @ManyToOne(fetch = LAZY)
    private Article article;

    protected Heart() {
    }

    public static Heart of(Member member, Article article) {
        return new Heart(member, article);
    }

    private Heart(Member member, Article article) {
        this.member = member;
        this.article = article;
    }
}
