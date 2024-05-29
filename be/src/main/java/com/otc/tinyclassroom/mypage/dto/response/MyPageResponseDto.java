package com.otc.tinyclassroom.mypage.dto.response;

import com.otc.tinyclassroom.community.dto.response.MyPageArticleResponseDto;
import java.util.List;

/**
 * 마이페이지 전체 정보 리턴하는 Dto.
 */
public record MyPageResponseDto(String name, int grade, int classNumber, String classRoomTeacher,
                                String profileImage, int point, String thisWeekGoalContent,
                                Long lectureCnt, int totalLectureTime,
                                int attendanceRate, int attend, int lateOrLeaveFast, int absent,
                                String favoriteClassFirst, String favoriteClassSecond, String favoriteClassThird,
                                List<MemberBadgeResponseDto> myBadges,
                                List<MyPageArticleResponseDto> likedFiveArticles, List<MyPageArticleResponseDto> myFiveArticles) {

    public static MyPageResponseDto of(String name, int grade, int classNumber, String classRoomTeacher, String profileImage, int point, String thisWeekGoalContent,
        Long lectureCnt, int totalLectureTime,
        int attendanceRate, int attend, int lateOrLeaveFast, int absent,
        String favoriteClassFirst, String favoriteClassSecond, String favoriteClassThird,
        List<MemberBadgeResponseDto> myBadges,
        List<MyPageArticleResponseDto> likedFiveArticles, List<MyPageArticleResponseDto> myFiveArticles) {
        return new MyPageResponseDto(name, grade, classNumber, classRoomTeacher, profileImage, point, thisWeekGoalContent, lectureCnt, totalLectureTime, attendanceRate, attend, lateOrLeaveFast,
            absent, favoriteClassFirst, favoriteClassSecond, favoriteClassThird, myBadges, likedFiveArticles, myFiveArticles);
    }
}
