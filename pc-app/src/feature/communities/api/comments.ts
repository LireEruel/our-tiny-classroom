import { commonAxios } from "@/lib/commonAxios";

export const newComment = (
  articleId: number | undefined,
  content: string | undefined
) => {
  return commonAxios.post("/community/comments", {
    articleId,
    content,
  });
};

export const editComment = (
  article_comment_id: number | undefined,
  content: string | undefined
) => {
  return commonAxios.patch(`/community/comments/${article_comment_id}`, {
    content,
  });
};

export const removeComment = (article_comment_id: number | undefined) => {
  return commonAxios.delete(`/community/comments/${article_comment_id}`);
};
