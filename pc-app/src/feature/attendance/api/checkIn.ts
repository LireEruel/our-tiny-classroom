import { commonAxios } from "@/lib/commonAxios";
import { CheckInResponse } from "..";

const checkIn = (): Promise<CheckInResponse> => {
  return commonAxios.post("/attendances");
};

export { checkIn };
