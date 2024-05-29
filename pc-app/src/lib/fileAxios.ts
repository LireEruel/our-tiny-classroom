/* eslint-disable @typescript-eslint/no-explicit-any */
import Axios from "axios";

import { API_URL } from "@/config";
import { CODE } from "@/types/Code";
import { commonAxios } from "./commonAxios";

export const fileAxios = Axios.create({
  baseURL: API_URL,
});

fileAxios.interceptors.request.use(function (config): any {
  // eslint-disable-next-line valid-typeof
  if (typeof window === undefined) {
    return;
  }

  config.headers = Object.assign({}, config.headers, {
    "Content-Type": "multipart/form-data",
    Authorization: localStorage.getItem("accessToken"),
  });
  return config;
});

let isTokenRefreshing = false;

fileAxios.interceptors.response.use(
  (response) => {
    if (response.config.url?.includes("/login") || response.config.url?.includes("/token/refresh")) {
      const accessToken = response.headers.authorization;
      localStorage.setItem("accessToken", accessToken);
    }
    return response.data;
  },
  async (error) => {
    const {
      config,
      response: { status },
    } = error;
    //새로고침에 의해 저장된 accessToken이 없어진 경우
    if (status == CODE.HTTP_STATUS_CODE.UNAUTHORIZED) {
      // 요청상태저장
      const originalRequest = config;

      //token을 재발급 하는 동안 다른 요청이 발생할 경우 대기시킨다.

      if (!isTokenRefreshing) {
        isTokenRefreshing = true;
        // 에러가 발생했던 컴포넌트의 axios로 이동하고자 하는 경우
        // 반드시 return을 붙여야 한다.
        try {
          const refreshTokenResult = await commonAxios.post<{
            refreshToken: string;
          }>("/members/token/refresh", {
            refreshToken: localStorage.getItem("refreshToken"),
          });
          const newRefreshToken = refreshTokenResult.data.refreshToken;
          localStorage.setItem("refreshToken", newRefreshToken);
          fileAxios.defaults.headers.common["Authorization"] = localStorage.getItem("accessToken");
          return await fileAxios(originalRequest);
        } catch (e) {
          console.log(e);
        } finally {
          isTokenRefreshing = false;
        }
      }
    } else if (status == CODE.HTTP_STATUS_CODE.FORBIDDEN) {
      alert(error.response.data.message);
    }
    return Promise.reject(error);
  }
);
