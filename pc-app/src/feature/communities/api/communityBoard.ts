import { commonAxios } from "@/lib/commonAxios";
import { FetchArticleListResponse } from "../types";

export const fetchArticles = async (
  boardType?: string,
  pages?: number
): Promise<FetchArticleListResponse> => {
  return await commonAxios.get("/community/articles", {
    params: {
      boardType: boardType ? boardType : "notice",
      page: pages,
    },
  });
};

interface SearchBoardProps {
  boardType: string;
  searchType?: string;
  searchValue?: string;
  page?: number;
  isClass?: boolean;
  boardSize?: number;
  classRoomId?: number | null;
}
export const searchBoard = async ({
  boardType,
  searchType,
  searchValue,
  page,
  isClass,
  boardSize,
  classRoomId,
}: SearchBoardProps): Promise<FetchArticleListResponse> => {
  if (classRoomId) {
    return await commonAxios.get(
      `/community/classRoom/${classRoomId}/articles`,
      {
        params: {
          boardType,
          page,
          searchValue,
          searchType,
          isClass,
          boardSize: boardSize ? boardSize : 15,
        },
      }
    );
  } else {
    return await commonAxios.get(`/community/articles`, {
      params: {
        boardType,
        page,
        searchValue,
        searchType,
        isClass,
        boardSize: boardSize ? boardSize : 15,
      },
    });
  }
};

export const deleteBoard = async (articleId: number) => {
  return await commonAxios.delete(`/community/articles/${articleId}`);
};

export const getClass = async (memberId: string) => {
  return await commonAxios.get(`/members/class-room-number/${memberId}`, {});
};
