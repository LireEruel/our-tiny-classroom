import { commonAxios } from "@/lib/commonAxios";
import { getTodayAttendanceResponse } from "..";

const getTodayAttendance = (
  memberId: string
): Promise<getTodayAttendanceResponse> => {
  return commonAxios.get("/attendances/daily/" + memberId);
};

export { getTodayAttendance };
