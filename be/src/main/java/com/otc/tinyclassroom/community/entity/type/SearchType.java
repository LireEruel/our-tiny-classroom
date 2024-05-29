package com.otc.tinyclassroom.community.entity.type;

import com.otc.tinyclassroom.community.exception.CommunityErrorCode;
import com.otc.tinyclassroom.community.exception.CommunityException;

/**
 * 검색 설정을 위한 ENUM. 제목, 본문, 이름, 제목 + 본문
 */
public enum SearchType {
    TITLE, CONTENT, NAME, TITLE_CONTENT;

    /**
     * 쿼리파라미터의 대소문자 관계 없이 ENUM 맵핑을 위한 함수.
     *
     * @param searchType 검색 타입
     * @return 검색 타입
     */
    public static SearchType fromString(String searchType) {
        for (SearchType type : SearchType.values()) {
            if (type.name().equalsIgnoreCase(searchType)) {
                return type;
            }
        }
	return null;
    }
}
