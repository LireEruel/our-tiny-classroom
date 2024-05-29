package com.otc.tinyclassroom.community.dto;

import com.otc.tinyclassroom.community.entity.Article;
import com.otc.tinyclassroom.community.entity.type.ArticleType;
import com.otc.tinyclassroom.member.dto.ClassRoomDto;
import com.otc.tinyclassroom.member.dto.MemberDto;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 댓글을 포함한 게시글 DTO. DTO for {@link com.otc.tinyclassroom.community.entity.Article} with {@link  com.otc.tinyclassroom.community.entity.ArticleComment}.
 */
public record ArticleWithCommentDto(Long id, MemberDto member, ClassRoomDto classRoom, String title, String content, ArticleType articleType, int hit, Set<ArticleCommentDto> articleComments,
                                    LocalDateTime createdAt, LocalDateTime modifiedAt) {

    public static ArticleWithCommentDto of(Long id, MemberDto member, ClassRoomDto classRoom, String title, String content, ArticleType articleType, int hit, Set<ArticleCommentDto> articleComments,
        LocalDateTime createdAt, LocalDateTime modifiedAt) {
        return new ArticleWithCommentDto(id, member, classRoom, title, content, articleType, hit, articleComments, createdAt, modifiedAt);
    }

    /**
     * 게시글 Entity로부터 댓글을 포함한 게시글 DTO로 변환하는 메서드.
     */
    public static ArticleWithCommentDto from(Article entity) {
        return ArticleWithCommentDto.of(
            entity.getId(),
            MemberDto.from(entity.getMember()),
            ClassRoomDto.from(entity.getClassRoom()),
            entity.getTitle(),
            entity.getContent(),
            entity.getArticleType(),
            entity.getHit(),
            entity.getArticleComments().stream()
                .map(ArticleCommentDto::from)
                .collect(Collectors.toCollection(LinkedHashSet::new)),
            entity.getCreatedAt(),
            entity.getModifiedAt()
        );
    }
}