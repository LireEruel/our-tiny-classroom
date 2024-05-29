package com.otc.tinyclassroom.lecture.entity.type;

import lombok.Getter;

/**
 * 강의의 분류 카테고리를 위한 enum class 정의.
 */
@Getter
public enum LectureCategoryType {
    NO_CATEGORY("기타"),
    KOREAN("국어"),
    MATH("수학"),
    SCIENCE("과학"),
    ENGLISH("영어"),
    MUSIC("음악"),
    ART("미술"),
    ETHICS("도덕"),
    HOME_ECONOMICS("기술가정"),
    CAREER("진로"),
    HOBBY("취미");

    private final String korName;

    LectureCategoryType(String korName) {
        this.korName = korName;
    }
}
