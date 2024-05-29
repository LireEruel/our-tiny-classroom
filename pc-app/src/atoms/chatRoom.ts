import { Chat, ChatRoom } from "@/feature/chat/types";
import { atom } from "recoil";
import { recoilPersist } from "recoil-persist";

const { persistAtom } = recoilPersist(); // atom을 localStorage에 저장하기 위한 라이브러리

export const roomList = atom<Array<ChatRoom>>({
  key: "chatRoomList",
  default: [],
  effects_UNSTABLE: [persistAtom], // effects_UNSTABLE을 적어주어야 recoil-persist가 적용됨.
});

export const currentRoomAtom = atom<ChatRoom | undefined>({
  key: "currentRoom",
  default: undefined,
})

export const subscribeListAtom = atom<Array<string>>({
    key: "subscribeList",
    default: []
  }
)

export const chatListAtom = atom<Chat[] | undefined>({
  key: "chatList",
  default: []
})

export const currentRoomIdAtom = atom<string | undefined>({
  key: "currentRoomId",
  default: undefined
})