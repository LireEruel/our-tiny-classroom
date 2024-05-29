import { commonAxios } from "@/lib/commonAxios";

export const createBoard = (
  title: string,
  content: string,
  articleType: string,
  classRoomId?: number
) => {
  if (classRoomId) {
    return commonAxios.post("/community/articles", {
      classRoomId,
      title,
      content,
      articleType,
    });
  } else {
    return commonAxios.post("/community/articles", {
      title,
      content,
      articleType,
    });
  }
};
