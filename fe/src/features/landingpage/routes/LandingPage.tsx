import { userState } from "@/atoms/user";
import { logout } from "@/features/auth/api/logout";
import {
  Navbar,
  NavbarBrand,
  NavbarContent,
  NavbarItem,
  Button,
} from "@nextui-org/react";
import { useNavigate } from "react-router-dom";
import { useRecoilValue } from "recoil";

export function LandingPage() {
  const navigator = useNavigate();
  const goLogin = () => {
    navigator("/auth/login");
  };

  const goJoin = () => {
    navigator("/auth/join");
  };

  const goAdmin = () => {
    navigator("/admin");
  };

  const onClickLogoutBtn = async () => {
    try {
      await logout();
    } finally {
      localStorage.clear();
      window.location.reload();
    }
  };

  function downloadFile() {
    // `<a>` 요소 생성
    const a = document.createElement("a");
    a.href =
      "https://otc-image-bucket.s3.ap-northeast-2.amazonaws.com/OurTinyClassroom+Setup+0.0.0.exe"; // 다운로드할 파일의 URL 설정
    document.body.appendChild(a); // `<a>` 요소를 DOM에 추가
    a.click(); // 프로그램적으로 클릭 이벤트 발생
    document.body.removeChild(a); // `<a>` 요소 제거
  }

  const userInfo = useRecoilValue(userState);
  return (
    <section className="w-full h-lvh relative">
      <div className="w-full h-lvh relative bg-red-100 bg-cover bg-no-repeat bg-[url('./assets/img/landingback.png')]">
        <Navbar maxWidth="2xl">
          <NavbarBrand className="flex gap-7">
            <p className="font-bold text-inherit">우리들의 작은 교실</p>
          </NavbarBrand>
          <NavbarContent justify="end">
            {userInfo?.memberId ? (
              <>
                {userInfo.role === "ADMIN" ? (
                  <NavbarItem className="hidden lg:flex">
                    <Button onPress={goAdmin} color="primary" variant="flat">
                      관리자 페이지로 이동
                    </Button>
                  </NavbarItem>
                ) : null}
                <NavbarItem className="hidden lg:flex">
                  <Button
                    onPress={onClickLogoutBtn}
                    color="default"
                    variant="flat">
                    로그아웃
                  </Button>
                </NavbarItem>
              </>
            ) : (
              <>
                <NavbarItem className="hidden lg:flex">
                  <Button onPress={goLogin} color="default" variant="flat">
                    로그인
                  </Button>
                </NavbarItem>
                <NavbarItem>
                  <Button onPress={goJoin} color="primary" variant="flat">
                    회원가입
                  </Button>
                </NavbarItem>
              </>
            )}
          </NavbarContent>
        </Navbar>
      </div>
      <Button
        onPress={downloadFile}
        className="w-3/12 min-h-20 transform -translate-x-1/2 -translate-y-1/2 absolute bottom-24 left-1/2 text-3xl"
        color="primary">
        다운로드
      </Button>
    </section>
  );
}
