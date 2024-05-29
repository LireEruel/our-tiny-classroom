import { atom } from "recoil";
import * as StompJs from "@stomp/stompjs";

export const connectionState = atom<StompJs.Client | undefined>({
  key: "connection",
  default: undefined,
});
