package com.otc.tinyclassroom.community.dto;

import com.otc.tinyclassroom.community.entity.Article;
import com.otc.tinyclassroom.community.entity.type.ArticleType;
import com.otc.tinyclassroom.member.dto.ClassRoomDto;
import com.otc.tinyclassroom.member.dto.MemberDto;
import java.time.LocalDateTime;

/**
 * 게시글 DTO. DTO for {@link com.otc.tinyclassroom.community.entity.Article}.
 */
public record ArticleDto(Long id, MemberDto member, ClassRoomDto classRoom, String title, String content, ArticleType articleType, LocalDateTime createdAt, LocalDateTime modifiedAt, int hit) {

    public static ArticleDto of(MemberDto member, ClassRoomDto classRoom, Long id, String title, String content, ArticleType articleType, LocalDateTime createdAt,
        LocalDateTime modifiedAt, int hit) {
        return new ArticleDto(id, member, classRoom, title, content, articleType, createdAt, modifiedAt, hit);
    }

    /**
     * article Entity로부터 ArticleDto로 변환하는 메소드.
     */
    public static ArticleDto from(Article entity) {
        ClassRoomDto classRoom = null;
        if (entity.getClassRoom() != null) {
            classRoom = ClassRoomDto.from(entity.getClassRoom());
        }
        return new ArticleDto(
            entity.getId(),
            MemberDto.from(entity.getMember()),
            classRoom,
            entity.getTitle(),
            entity.getContent(),
            entity.getArticleType(),
            entity.getCreatedAt(),
            entity.getModifiedAt(),
            entity.getHit()
        );
    }
}
