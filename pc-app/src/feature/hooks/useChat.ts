import { useCallback } from "react";
import * as StompJs from "@stomp/stompjs";
import { SOCKET_URL } from "@/config";
import { useRecoilState } from "recoil";
import { connectionState } from "@/atoms/connection";

export const useChat = () => {
  const [connection, setConnection] = useRecoilState(connectionState);

  const subscribe = useCallback(
    (channel: string) => {
      try {
        if (connection) {
          connection.subscribe(`sub/channel/${channel}`, () => {
            console.log("구독 성공");
          });
        }
      } catch (err) {
        console.log(err);
      }
    },
    [connection]
  );

  const publish = useCallback(
    (url: string, data: object) => {
      try {
        console.log(connection?.connected);
        if (connection?.connected) {
          connection.publish({
            destination: url,
            body: JSON.stringify(data),
          });
        } else {
          connection?.activate();
        }
      } catch (e) {
        console.log(e);
      }
    },
    [connection]
  );

  const connect = () => {
    try {
      console.log("connection");
      const connectionData = new StompJs.Client({
        brokerURL: `ws://${SOCKET_URL}/stomp/chat`,
        connectHeaders: {
          Authorization: localStorage.getItem("accessToken"),
        },
        reconnectDelay: 5000, // 자동 재 연결
        heartbeatIncoming: 4000,
        heartbeatOutgoing: 4000,
        onConnect: () => {
          console.log("Connected to server");
          subscribe("3");
        },
      });
      connectionData.activate();
      setConnection(connectionData);
    } catch (err) {
      console.log(err);
    }
  };

  return { connection, connect, subscribe, publish };
};
