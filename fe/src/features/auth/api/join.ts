import { commonAxios } from "@/lib/commonAxios";
import { BaseResponse } from "@/types/BaseResponse";

export const join = (
  memberId: string,
  password: string,
  name: string,
  email: string,
  birthday: string
): Promise<BaseResponse> => {
  return commonAxios.post("/members/join", {
    memberId,
    password,
    name,
    email,
    birthday,
  });
};
