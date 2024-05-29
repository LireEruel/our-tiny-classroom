package com.otc.tinyclassroom.community.dto.response;

import com.otc.tinyclassroom.community.entity.Article;
import com.otc.tinyclassroom.community.entity.type.ArticleType;
import com.otc.tinyclassroom.member.dto.ClassRoomDto;
import com.otc.tinyclassroom.member.dto.response.MemberArticleListResponseDto;
import java.time.LocalDateTime;

/**
 * 게시물 전체 반환 리스트.
 */
public record ArticleListResponseDto(Long id, MemberArticleListResponseDto member, ClassRoomDto classRoom, String title, String content, ArticleType articleType,
                                     LocalDateTime createdAt, LocalDateTime modifiedAt) {

    /**
     * 파라미터로 부터 Dto를 생성하기위한 factory 메소드.
     */
    public static ArticleListResponseDto of(Long id, MemberArticleListResponseDto member, ClassRoomDto classRoom, String title, String content, ArticleType articleType,
        LocalDateTime createdAt, LocalDateTime modifiedAt) {
        return new ArticleListResponseDto(id, member, classRoom, title, content, articleType, createdAt, modifiedAt);
    }

    /**
     * 게시글 entity로 부터 게시글.
     */
    public static ArticleListResponseDto from(Article entity) {
        return ArticleListResponseDto.of(
            entity.getId(),
            MemberArticleListResponseDto.from(entity.getMember()),
            ClassRoomDto.from(entity.getClassRoom()),
            entity.getTitle(),
            entity.getContent(),
            entity.getArticleType(),
            entity.getCreatedAt(),
            entity.getModifiedAt()
        );
    }
}