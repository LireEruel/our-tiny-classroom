import { Navigate, useRoutes } from "react-router-dom";

import { protectedRoutes } from "./protected";
import { publicRoutes } from "./public";
import Video from "@/feature/classroom/pages/Video";
import { userState } from "@/atoms/user";
import {  useRecoilValue } from "recoil";
import Certification from "@/feature/auth/routes/Certification";

export const AppRoutes = () => {
  const auth = localStorage.getItem("accessToken");
  const userInfo = useRecoilValue(userState);
  const defaultRoutes = [
    { path: "video", element: <Video /> },
    {
      path: "*",
      element: <Navigate to={auth ?(userInfo.role == "USER" || userInfo.role == "WAITING")? "certificates" : "main-dashboard" : "auth/login"} />,
    },
  ];

  //const routes = auth ? protectedRoutes : publicRoutes;

  const routes = () => {
    if (auth) {
      if (userInfo.role == "USER" || userInfo.role == "WAITING") {
        return [{ path: "certificates", element: <Certification /> }]
      }
      else {
        return protectedRoutes;
      }
    } else {
      return publicRoutes
    }
  }

  const element = useRoutes([...defaultRoutes, ...routes()]);

  return <>{element}</>;
};
