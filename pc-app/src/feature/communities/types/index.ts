import { BaseResponse } from "@/types/BaseResponse";

export interface Board {
  id: number;
  title: string;
  name: string;
  createdAt: string;
  hit: number;
}

export interface Comment {
  id: number;
  content: string;
  createdAt?: string;
  modifiedAt?: string;
  name: string;
  isEdit?: boolean;
}

export interface BoardDetail extends Board {
  content: string;
  modifiedAt: string;
  articleComments: Comment[];
}

export const TYPE = {
  BOARD_TYPE: {
    SCHOOL: 0, // 학교 게시판
    CLASS: 1, // 학급 게시판
  },
  BOARD_CATEGORY: {
    NOTICE: 0, // 공지사항 게시판
    FREE: 1, // 자유게시판
    COUNSELING: 2, // 고민나눔 게시판
    HOBBY: 3, // 취미 공유 게시판
  },
  CLASS_CATEGORY: {
    NOTICE: 0, // 공지사항 게시판
    FREE: 1, // 자유게시판
  },
};

export interface FetchArticleListResponse extends BaseResponse {
  data: {
    content: Array<Board>;
    pageNumber: number;
    totalPages: number;
    number: number;
    classRoomId?: number;
  };
}

export interface FetchArticleDetailResponse extends BaseResponse {
  data: BoardDetail;
}

export interface SendChatData {
  sender: string;
  channelId: string;
  content: string;
}
