import AdminPage from "@/features/admin/routes/AdminPage";
import { AuthRoutes } from "@/features/auth";
import { LandingPage } from "@/features/landingpage/routes/LandingPage";
import { useRoutes } from "react-router-dom";

export const AppRoutes = () => {
  const defaultRoutes = [
    {
      path: "/",
      element: <LandingPage />,
    },
    {
      path: "/admin",
      element: <AdminPage />,
    },
    {
      path: "/auth/*",
      element: <AuthRoutes />,
    },
  ];
  const element = useRoutes([...defaultRoutes]);

  return <>{element}</>;
};
