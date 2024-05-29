import { useCallback, useEffect, useRef, useState } from "react";
import { DailyProvider, useCallFrame } from "@daily-co/daily-react";
import { useRecoilValue } from "recoil";
import { userState } from "@/atoms/user";
import { useLocation, useNavigate } from "react-router-dom";
export default function Video() {
  const callRef = useRef<HTMLDivElement>(null);
  const location = useLocation();
  const locationState = location.state;
  const userInfo = useRecoilValue(userState);
  const navigator = useNavigate();
  const callFrame = useCallFrame({
    parentEl: callRef.current,
    options: {
      iframeStyle: {
        position: "absolute",
        width: "100%",
        height: "100%",
      },
      showLeaveButton: true,
      showFullscreenButton: true,
      userName: userInfo.name,
    },
    shouldCreateInstance: useCallback(() => Boolean(callRef.current), []),
  });
  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  const [_, setCallReady] = useState(false); // react가 callFrame의 변경 감지를 못하므로, 강제로 리렌더링 해주기 위한 state
  useEffect(() => {
    setCallReady(true);
    callFrame?.join({ url: locationState.url });
  }, [callFrame]);

  const leave = async () => {
    setCallReady(false);
    await callFrame?.leave();
    await callFrame?.destroy();
    navigator("/");
  };

  callFrame?.on("left-meeting", leave);
  return (
    <DailyProvider callObject={callFrame}>
      <div ref={callRef} />
    </DailyProvider>
  );
}
