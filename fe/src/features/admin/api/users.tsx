import { commonAxios } from "@/lib/commonAxios";
import {
  fetchUserListResponse,
  requestGetMentorCertDetailResponse,
  requestGetMentorCertListResponse,
  requestGetStudentCertDetailResponse,
  requestGetStudentCertListResponse,
} from "../types";

export const fetchUserList = (): Promise<fetchUserListResponse> => {
  return commonAxios.get("/members");
};

export const requestEditUserInfo = (
  userId: number,
  name: string,
  email: string,
  role: string
) => {
  return commonAxios.patch("/members/" + userId, {
    name,
    email,
    role,
  });
};

export const requestEditUserClass = (userId: number, classRoomId: number) => {
  return commonAxios.patch("/classrooms/member/updateClass", {
    memberId: userId,
    classRoomId,
  });
};

export const requestCreateClassroom = (
  year: number,
  grade: number,
  number: number
) => {
  return commonAxios.post("/classrooms", {
    year,
    grade,
    number,
  });
};

export const requestClassAssignment = (
  userList: Array<{ id: number; grade: number }>
) => {
  return commonAxios.patch("/classrooms/user-list-random-assignment", {
    userList,
  });
};

export const requestGetStudentCertList =
  (): Promise<requestGetStudentCertListResponse> => {
    return commonAxios.get("/admin/members/certification/student");
  };

export const requestGetMentorCertList =
  (): Promise<requestGetMentorCertListResponse> => {
    return commonAxios.get("/admin/members/certification/mentor");
  };

export const requestGetStudentCertDetail = (
  articleId: number
): Promise<requestGetStudentCertDetailResponse> => {
  return commonAxios.get("/admin/members/certification/student/" + articleId);
};

export const requestGetMentorCertDetail = (
  articleId: number
): Promise<requestGetMentorCertDetailResponse> => {
  return commonAxios.get("/admin/members/certification/mentor/" + articleId);
};

export const requestStudentCertChange = (
  articleId: number,
  isApprove: boolean,
  grade: number
) => {
  if (isApprove) {
    return commonAxios.post(
      `/admin/members/certification/student/${articleId}/approve`,
      {
        grade,
      }
    );
  } else {
    return commonAxios.post(
      `/admin/members/certification/student/${articleId}/deny`
    );
  }
};

export const requestMentorCertChange = (
  articleId: number,
  isApprove: boolean
) => {
  if (isApprove) {
    return commonAxios.post(
      `/admin/members/certification/mentor/${articleId}/approve`
    );
  } else {
    return commonAxios.post(
      `/admin/members/certification/mentor/${articleId}/deny`
    );
  }
};
