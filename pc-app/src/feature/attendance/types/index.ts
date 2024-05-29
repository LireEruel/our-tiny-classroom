import { BaseResponse } from "@/types/BaseResponse";

export interface Attendance {
	date?: string;
	status?: number;
	checkIn?: string;
	checkOut?: string;
}

export interface getTodayAttendanceResponse extends BaseResponse {
	data: {
		attendanceOnDate: Attendance;
	};
}

export interface CheckInResponse extends BaseResponse {
	data: {
		checkInTime: string;
		status: number;
	};
}

export const attnedanceMap = new Map<number, string>([
	[0, "출석"],
	[1, "지각"],
	[2, "조퇴"],
	[3, "결석"],
	[4, "사유 지각"],
]);
