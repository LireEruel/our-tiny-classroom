import { commonAxios } from "@/lib/commonAxios";
import { BaseResponse } from "@/types/BaseResponse";

export const deleteSchedule = (scheduleId: number): Promise<BaseResponse> => {
  return commonAxios.delete(`/schedules/delete/${scheduleId}`);
};
