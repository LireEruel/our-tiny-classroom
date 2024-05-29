import { BaseResponse } from "./../../../types/BaseResponse";

export type ChatRoom = {
  roomId: string;
  roomMemberList: Array<{
    memberId: string;
    name: string;
    id: number;
    profileImage: string;
  }>;
  lastChatId: number; // 프론트가 갖고 있는데, < 백엔드가 로그인할 때 알려줘야함.
  lastMessage: string;
  chatList: Chat[];
};

export type Chat = {
  senderId: string;
  chatId?: number;
  roomId: string;
  message: string;
  createdAt: string;
  senderName: string;
};

export interface CreateChatRoomResponse extends BaseResponse {
  data: ChatRoom;
}

export interface GetChatRoomListResponse extends BaseResponse {
  data: ChatRoom[];
}

export interface GetChatListResponse extends BaseResponse {
  data: Chat[];
}

export interface GetChatRoomResponse extends BaseResponse {
  data: ChatRoom;
}
