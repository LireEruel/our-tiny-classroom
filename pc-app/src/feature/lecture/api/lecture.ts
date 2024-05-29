import { commonAxios } from "@/lib/commonAxios";

export const createLecture = async (
  memberId: number,
  title: string,
  description: string,
  dayOfWeek: number,
  timeTable: number,
  lectureType: number,
  lectureCategoryType: number,
  selectDate: Date
) => {
  return commonAxios.post(`/lectures`, {
    memberId,
    title,
    description,
    dayOfWeek,
    timeTable,
    lectureType,
    lectureCategoryType,
    date: selectDate,
  });
};

export const getLecture = async () => {
  return commonAxios.get(`/lectures`);
};

export const getAddAbleSpecialLectures = () => {
  return commonAxios.get(`/lectures`, {
    params: {
      lectureType: "SPECIAL_LECTURE",
      approved: "APPROVED",
    },
  });
};

export const deleteLecture = async (lectureId: number) => {
  return commonAxios.delete(`/lectures/${lectureId}`);
};

export const getEditLecture = async (lectureId: number) => {
  return commonAxios.get(`/lectures/${lectureId}`);
};

export const editLecture = async (
  userId: number,
  title: string,
  description: string,
  dayOfWeek: number,
  timeTable: number,
  lectureType: number,
  lectureCategoryType: number,
  selectDate: Date,
  lectureId: number
) => {
  return commonAxios.patch(`/lectures/${lectureId}`, {
    userId,
    title,
    description,
    dayOfWeek,
    timeTable,
    lectureType,
    lectureCategoryType,
    date: selectDate,
  });
};

export const searchLecture = async (searchType: string, searchKeyword: string) => {
  const params = {
    [`${searchType}`]: searchKeyword,
  };

  console.log(params);
  return commonAxios.get(`/lectures`, {
    params: params,
  });
};
