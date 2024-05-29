import { BaseResponse } from "@/types/BaseResponse";

export interface MyPageUserInfo {
  name: string;
  grade: number;
  classNumber: number;
  profileImage: string;
  point: number;
  attendanceRate: number;
  attend: number;
  lateOrLeaveFast: number;
  absent: number;
  favoriteClassFirst: string;
  favoriteClassSecond: string;
  favoriteClassThird: string;
  classRoomTeacher: string; // 줄 예정
  lectureCnt: number;
  totalLectureTime: number; 
  likedFiveArticles: Array<{
    title: string;
    articleId: number;
  }>;
  thisWeekGoalContent: string;
  myBadges: Array<{
    badgeId: number;
    badgeUrl: string;
  }>;
  myFiveArticles: Array<{
    title: string;
    articleId: number;
  }>;
}

export interface getMyPageBaseInfoResponse extends BaseResponse {
  data: MyPageUserInfo;
}
