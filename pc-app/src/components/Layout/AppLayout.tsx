import CalendarIcon from "@/assets/img/CalendarIcon";
import ComunityIcon from "@/assets/img/ComunityIcon";
import BoardIcon from "@/assets/img/BoardIcon";
import { Button, Tooltip } from "@nextui-org/react";
import HomeIcon from "../../assets/img/HomeIcon";
import { useNavigate } from "react-router-dom";
import PersonIcon from "@/assets/img/PersonIcon";
import LogoutIcon from "@/assets/img/LogoutIcon";
import { useCallback } from "react";
import { logout } from "@/feature/auth/api/logout";
import Cookies from "js-cookie";

type AppLayoutProps = {
  children: React.ReactNode;
};

function SideBar() {
  const navigate = useNavigate();

  const goLogin = useCallback(() => navigate("/auth/login"), [navigate]);
  const goHome = () => {
    navigate("/");
  };

  const goComunity = () => {
    navigate("/communities");
  };

  const onClickLogoutBtn = useCallback(async () => {
    try {
      await logout();
    } catch (error) {
      console.log(error);
    } finally {
      Cookies.remove("accessToken");
      Cookies.remove("refreshToken");
      localStorage.clear();
      goLogin();
    }
  }, [goLogin]);

  return (
    <>
      <article className=" bg-lime-100 min-h-screen h-full flex flex-col items-center justify-between py-10 px-5 gap-5">
        <div className="flex flex-col items-center gap-5">
          <Tooltip
            color="success"
            content={"메인 대시보드"}
            placement="right"
            className="capitalize"
          >
            <Button
              onClick={goHome}
              isIconOnly
              className="p-2 bg-lime-500"
              size="lg"
            >
              <HomeIcon />
            </Button>
          </Tooltip>
          <Tooltip
            color="success"
            content={"커뮤니티"}
            placement="right"
            className="capitalize"
          >
            <Button
              isIconOnly
              className="p-2 bg-lime-500"
              onClick={goComunity}
              size="lg"
            >
              <BoardIcon />
            </Button>
          </Tooltip>

          <Tooltip
            color="success"
            content={"채팅"}
            placement="right"
            className="capitalize"
          >
            <Button isIconOnly className="p-2 bg-lime-500" size="lg">
              <ComunityIcon />
            </Button>
          </Tooltip>

          <Tooltip
            color="success"
            content={"일정 관리"}
            placement="right"
            className="capitalize"
          >
            <Button isIconOnly className="p-2 bg-lime-500" size="lg">
              <CalendarIcon />
            </Button>
          </Tooltip>
        </div>
        <div className="flex flex-col items-center gap-5">
          <Tooltip
            color="success"
            content={"마이 페이지"}
            placement="right"
            className="capitalize"
          >
            <Button isIconOnly className="p-2 bg-lime-500" size="lg">
              <PersonIcon />
            </Button>
          </Tooltip>
          <Tooltip
            color="success"
            content={"로그아웃"}
            placement="right"
            className="capitalize"
          >
            <Button
              onClick={onClickLogoutBtn}
              isIconOnly
              className="p-2 bg-lime-500"
              size="lg"
            >
              <LogoutIcon />
            </Button>
          </Tooltip>
        </div>
      </article>
    </>
  );
}

function AppLayout({ children }: AppLayoutProps) {
  return (
    <div className="flex">
      <SideBar />
      {children}
    </div>
  );
}

export default AppLayout;