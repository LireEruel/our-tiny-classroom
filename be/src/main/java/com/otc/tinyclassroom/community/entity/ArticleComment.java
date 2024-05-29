package com.otc.tinyclassroom.community.entity;

import static jakarta.persistence.FetchType.LAZY;

import com.otc.tinyclassroom.member.entity.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

/**
 * 댓글 엔티티 정의.
 */
@Getter
@Table
@Entity
public class ArticleComment extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    private Member member; // 회원
    @ManyToOne(fetch = LAZY)
    private Article article; // 게시글
    @Setter
    @Column(length = 1000)
    private String content; // 본문

    protected ArticleComment() {
    }

    private ArticleComment(Member member, Article article, String content) {
        this.member = member;
        this.article = article;
        this.content = content;
    }

    public static ArticleComment of(Member member, Article article, String content) {
        return new ArticleComment(member, article, content);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ArticleComment that)) {
            return false;
        }
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
