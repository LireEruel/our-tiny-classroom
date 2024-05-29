import { useRecoilState, useRecoilValue } from "recoil";
import { chatListAtom, currentRoomIdAtom, roomList, subscribeListAtom } from "@/atoms/chatRoom";
import { userState } from "@/atoms/user";
import { Chat } from "@/feature/chat/types";
type StompClientProps = {
	children: React.ReactNode;
};
import { IMessage, useSubscription } from "react-stomp-hooks";
import { getChatRoom, getChatRoomList } from "@/feature/chat/api/chatRoom";
import { useEffect } from "react";

type ReceveMessageBody = {
	chatMessageType: "MESSAGE" | "SUBSCRIBE";
} & Chat;

export const StompClientProvider = ({ children }: StompClientProps) => {
	const userInfo = useRecoilValue(userState);
	const currentRoomId = useRecoilValue(currentRoomIdAtom);
	const [chatList, setChatList] = useRecoilState(chatListAtom);
	const [chatRoomList, setChatRoomList] = useRecoilState(roomList);
	const [subscribeList, setSubscribeList] = useRecoilState(subscribeListAtom);
	useSubscription(subscribeList, (message: IMessage) => recevHandler(message));

	function recevHandler(message: IMessage) {
		const messageBody: ReceveMessageBody = JSON.parse(message.body);
		switch (messageBody.chatMessageType) {
			case "MESSAGE":
				onRecevMessage(messageBody);
				break;
			case "SUBSCRIBE":
				onRecevRoomInvite(messageBody.roomId);
				break;
		}
		if (chatList && currentRoomId == messageBody.roomId) {
			setChatList([...chatList, messageBody]);
		}
	}

	function onRecevMessage(message: ReceveMessageBody) {
		if (chatList && currentRoomId == message.roomId) {
			setChatList([
				...chatList,
				{
					senderId: message.senderId,
					chatId: message.chatId,
					roomId: message.roomId,
					message: message.message,
					createdAt: message.createdAt,
					senderName: message.senderName,
				},
			]);
		} else {
			new Notification(message.senderName, { body: message.message });
		}
	}

	async function onRecevRoomInvite(roomId: string) {
		setSubscribeList((beforeList) => [...beforeList, "/ws/sub/room/" + roomId]);
		try {
			const res = await getChatRoom(roomId);
			setChatRoomList((_roomList) => [..._roomList, res.data]);
		} catch (e) {
			console.error(e);
		}
	}

	useEffect(() => {
		if (userInfo && !subscribeList.length) {
			setSubscribeList(["/ws/sub/room/" + userInfo.memberId]);
		}
	}, [userInfo]);

	useEffect(() => {
		const fetchChatRoomList = async () => {
			try {
				const res = await getChatRoomList();
				setChatRoomList(res.data);
				setSubscribeList(["/ws/sub/room/" + userInfo.memberId, ...res.data.map((chatRoom) => "/ws/sub/room/" + chatRoom.roomId)]);
			} catch (e) {
				console.error(e);
			}
		};
		fetchChatRoomList();
	}, []);

	useEffect(() => {
		setSubscribeList(["/ws/sub/room/" + userInfo.memberId, ...chatRoomList.map((chatRoom) => "/ws/sub/room/" + chatRoom.roomId)]);
	}, [chatRoomList]);

	return <>{children}</>;
};
