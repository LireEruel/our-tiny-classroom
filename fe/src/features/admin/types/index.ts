import { BaseResponse } from "@/types/BaseResponse";

export interface SearchBarProps {
  searchKeyword: string;
  setSearchKeyword: (searchKeyword: string) => void;
  searchType: string;
  setSearchType: (searchType: string) => void;
  // onSubmit: () => void;
}

export interface fetchUserListResponse extends BaseResponse {
  data: User[];
}
export interface User {
  userId: number;
  memberId: string;
  classRooms: ClassRoom | null;
  email: string;
  birthday: string;
  name: string;
  point: number;
  role:
    | "ROLE_ADMIN"
    | "ROLE_TEACHER"
    | "ROLE_MENTOR"
    | "ROLE_STUDENT"
    | "ROLE_USER"
    | "ROLE_WAITNG";
}

export interface ClassRoom {
  id: number;
  year: number;
  grade: number;
  number: number;
  roomUrl: string | null;
}

export interface requestGetStudentCertListResponse extends BaseResponse {
  data: Array<{
    articleId: number;
    memberName: string;
    birthday: string;
  }>;
}

export interface requestGetMentorCertListResponse extends BaseResponse {
  data: Array<{
    articleId: number;
    memberName: string;
    birthday: string;
  }>;
}
export interface requestGetStudentCertDetailResponse extends BaseResponse {
  data: StudentCertDetail;
}

export interface requestGetMentorCertDetailResponse extends BaseResponse {
  data: MentorCertDetail;
}

export interface StudentCertDetail {
  articleId: number;
  memberName: string;
  birthday: string;
  beforeSchoolType: number;
  quitReason: string;
  beforeSchool: string;
  quitConfirmationPaths: string[];
}

export interface MentorCertDetail {
  articleId: number;
  memberName: string;
  birthday: string;
  belong: string;
  belongDocumentPaths: string[];
}
