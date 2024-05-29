import { commonAxios } from "@/lib/commonAxios";
import { BaseResponse } from "@/types/BaseResponse";
import { Schedule } from "..";

interface createScheduleProps extends BaseResponse {
	data: {
		monday: string;
		scheduleList: Schedule[];
	};
}

export const createSchedule = (lectureId: number, scheduleDate: string, timeTable: number): Promise<createScheduleProps> => {
	return commonAxios.post(`/schedules/insert`, {
		lectureId,
		scheduleDate, // 조회할 일주일중, 월요일의 날짜를 : YYYY-MM-DD 형식으로 보낸다.
		timeTable,
	});
};

export const getSpecialLecture = (date: string, timeTable: number) => {
	return commonAxios.get("/lectures", {
		params: {
			date,
			timeTable,
		},
	});
};

export const getFreeLecture = (lectureType: string) => {
	return commonAxios.get("/lectures", {
		params: {
			lectureType,
		},
	});
};
