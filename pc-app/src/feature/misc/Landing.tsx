import { Button } from "@nextui-org/react";
import { useCallback } from "react";
import { useNavigate } from "react-router-dom";
import { logout } from "../auth/api/logout";
import Swal from "sweetalert2";
import { commonAxios } from "@/lib/commonAxios";
import { useRecoilValue } from "recoil";
import { userState } from "@/atoms/user";

function Landing() {
	const userInfo = useRecoilValue(userState);
	const isLogined = userInfo.memberId ? true : false;
	const navigate = useNavigate();
	const goLogin = useCallback(() => navigate("/auth/login"), [navigate]);

	const onClickLogoutBtn = useCallback(async () => {
		await logout();
		localStorage.clear();
		goLogin();
	}, [goLogin]);

	const onClickTestBtn = async () => {
		try {
			await commonAxios.post("/test/test1");
			Swal.fire("성공");
		} catch (e) {
			Swal.fire("실패");
			console.log(e);
		}
	};

	return (
		<div>
			{isLogined ? (
				<div className="flex gap-5">
					<Button onClick={onClickLogoutBtn}>로그아웃</Button>
					<Button onClick={onClickTestBtn}>테스트 </Button>
				</div>
			) : (
				<a href="#/auth/login">로그인</a>
			)}
		</div>
	);
}

export default Landing;
