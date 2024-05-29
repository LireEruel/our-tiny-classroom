import { commonAxios } from "@/lib/commonAxios";
import { LoginResponse } from "..";

export const login = (memberId: string, password: string): Promise<LoginResponse> => {
	return commonAxios.post("/members/login", { memberId, password });
};

export const kakaoLogin = (accessToken: string): Promise<LoginResponse> => {
	return commonAxios.post("/members/kakao", { accessToken });
};
