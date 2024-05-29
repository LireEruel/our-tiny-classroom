import { commonAxios } from "@/lib/commonAxios";
import { BaseResponse } from "@/types/BaseResponse";
import { Schedule } from "..";

interface GetWeekScheduleResponse extends BaseResponse {
  data: {
    monday: string;
    scheduleList: Schedule[];
  };
}

export const getWeekSchedules = (memberId: string, scheduleStartDate: string): Promise<GetWeekScheduleResponse> => {
  return commonAxios.get(`/schedules/${memberId}`, {
    params: {
      scheduleDate: scheduleStartDate, // 조회할 일주일중, 월요일의 날짜를 : YYYY-MM-DD 형식으로 보낸다.
    },
  });
};

export type CurrentLecture = {
  lectureId: number;
  title: string;
  lectureType: string;
  lectureCategoryType: string;
  scheduleDate: string;
  dayOfWeek: number;
  timeTable: number;
  lectureUrl: string;
};
interface RequestCurrentScheduleResponse extends BaseResponse {
  data: CurrentLecture;
}

export const requestCurrentSchedule = (): Promise<RequestCurrentScheduleResponse> => {
  return commonAxios.get(`/schedules/detail/current`);
};
