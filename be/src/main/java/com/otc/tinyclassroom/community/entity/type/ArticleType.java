package com.otc.tinyclassroom.community.entity.type;

import com.otc.tinyclassroom.community.exception.CommunityErrorCode;
import com.otc.tinyclassroom.community.exception.CommunityException;

/**
 * 게시글 타입 ENUM. 게시글 타입에는 공지, 자유게시판, 고민상담, 취미가 있다.
 */
public enum ArticleType {

    NOTICE, FREE, COUNSELING, HOBBY;


    /**
     * 쿼리파라미터의 대소문자 관계 없이 ENUM 맵핑을 위한 함수.
     *
     * @param articleType 게시글 타입
     * @return 게시글 타입
     */
    public static ArticleType fromString(String articleType) {
        for (ArticleType type : ArticleType.values()) {
            if (type.name().equalsIgnoreCase(articleType)) {
                return type;
            }
        }
        throw new CommunityException(CommunityErrorCode.INVALID_ARTICLE_TYPE);
    }
}
