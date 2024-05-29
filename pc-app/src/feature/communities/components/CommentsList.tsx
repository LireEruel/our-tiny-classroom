import { useState, useEffect } from "react";
import CommentRender from "./CommentRender";
import { Comment } from "..";

interface CommentsListProps {
  commentList: Comment[];
  remove: (id: number | undefined) => void;
  edit: (id: number, content: string) => void;
  setList: React.Dispatch<React.SetStateAction<Comment[]>>;
}

function CommentsList({
  commentList,
  remove,
  edit,
  setList,
}: CommentsListProps) {
  const [comments, setComments] = useState<Comment[] | JSX.Element[]>(commentList);

  const removeHandler = (commentId: number | undefined): void => {
    remove(commentId);
    setList((preComments: Comment[]) => {
      return preComments.filter((comment) => comment.id !== commentId);
    });
  };
  const editHandler = (commentId: number, content?:string) => {
    setList(
      // eslint-disable-next-line @typescript-eslint/no-explicit-any
      commentList.map((comment: any) => {
        return comment.id === commentId
          ? { ...comment, isEdit: !comment.isEdit, content: content}
          : comment;
      })
    );
  };

  // 댓글 값 변경하는 함수

  useEffect(() => {
    if (commentList) {
      const showList:JSX.Element[] = commentList.map((comment: Comment, index: number) => {
        return (
          <CommentRender
            key={index}
            comment={comment}
            editHandler={editHandler}
            edit={edit}
            removeHandler={removeHandler}
          />
        );
      });
      setComments(showList);
    }
  }, [commentList]);

  return <>{comments.length !== 0 ? comments : "댓글이 존재하지 않습니다."}</>;
}

export default CommentsList;
