import { commonAxios } from "@/lib/commonAxios";

export const logout = () => {
  return commonAxios.post("/members/logout");
};
