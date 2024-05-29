package com.otc.tinyclassroom.community.entity;

import static jakarta.persistence.FetchType.LAZY;

import com.otc.tinyclassroom.community.entity.type.ArticleType;
import com.otc.tinyclassroom.member.entity.ClassRoom;
import com.otc.tinyclassroom.member.entity.Member;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

/**
 * Article 엔티티.
 */
@Getter
@Table(indexes = {
    @Index(columnList = "createdAt")
})
@Entity
public class Article extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    private Member member;
    // 몇 반 게시판에 들어가는 지 여부
    // 자유 게시판인 경우 null 값
    @ManyToOne(fetch = LAZY)
    private ClassRoom classRoom;

    @Setter
    @Column(nullable = false, length = 1000)
    private String title; // 제목
    @Setter
    @Column(nullable = false, length = 10000)
    private String content; // 내용
    @Column
    private int hit;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ArticleType articleType;
    @OrderBy("createdAt DESC")
    @OneToMany(mappedBy = "article", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>();

    protected Article() {
    }

    /**
     * 파라미터 생성자.
     */
    public Article(Member member, ClassRoom classRoom, String title, String content, ArticleType articleType) {
        this.member = member;
        this.classRoom = classRoom;
        this.title = title;
        this.content = content;
        this.articleType = articleType;
    }

    public static Article of(Member member, ClassRoom classRoom, String title, String content, ArticleType articleType) {
        return new Article(member, classRoom, title, content, articleType);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Article article)) {
            return false;
        }
        return id != null && id.equals(article.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void increaseHit() {
        this.hit += 1;
    }
}
