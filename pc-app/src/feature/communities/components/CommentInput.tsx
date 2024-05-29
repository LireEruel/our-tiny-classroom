import { Button } from "@nextui-org/button";
import { useState, useRef } from "react";
import { newComment } from "../api/comments";
import { Input } from "@nextui-org/react";
import Swal from "sweetalert2";
import { useRecoilValue } from "recoil";
import { userState } from "@/atoms/user";
import { Comment } from "..";
interface CommentProps {
  articleId: number;
  commentList: Comment[];
  setList: React.Dispatch<React.SetStateAction<Comment[]>>;
}

function CommentInput({ articleId, commentList, setList }: CommentProps) {
  const user = useRecoilValue(userState)
  const [createComment, setCreateComment] = useState<string>("");
  const inputFocus = useRef<HTMLInputElement | null>(null);
  const commentHandler = async () => {
    try {
      if (createComment.trim()) {
        const commentId = await newComment(articleId, createComment);
        setCreateComment("");
        setList([
          { id: commentId.data, content: createComment, name: user.name },
          ...commentList,
        ]);
      } else {
        Swal.fire({
          icon: "error",
          title: "실패!!",
          text: "댓글을 작성하세요",
        });
        inputFocus.current?.focus();
      }
    } catch (error) {
      console.error(error);
    }
  };
  return (
    <article className="w-full h-20 flex justify-between items-center gap-3">
      <Input
        type="text"
        placeholder="댓글을 입력하세요."
        value={createComment}
        onChange={(e) => setCreateComment(e.target.value)}
        ref={inputFocus}
      />
      <Button
        color="default"
        className="w-1/12 text-white text-xl shadow-2xl rounded-xl"
        size="lg"
        onClick={commentHandler}
      >
        작성
      </Button>
    </article>
  );
}

export default CommentInput;
