import { atom } from "recoil";
import { recoilPersist } from "recoil-persist";
export interface UserState {
	memberId: string;
	name: string;
	point: number;
	role: string;
}

const { persistAtom } = recoilPersist(); // atom을 localStorage에 저장하기 위한 라이브러리

export const userState = atom<UserState>({
	key: "userState",
	default: {
		memberId: "",
		name: "",
		point: -1,
		role: "",
	},
	effects_UNSTABLE: [persistAtom], // effects_UNSTABLE을 적어주어야 recoil-persist가 적용됨.
});
