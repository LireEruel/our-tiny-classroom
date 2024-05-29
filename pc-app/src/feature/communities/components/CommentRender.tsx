import dayjs from "dayjs";
import { EditIcon } from "../assets/EditIcon";
import { PencilIcon } from "../assets/PencilIcon";
import { TrashIcon } from "../assets/TrashIcon";
import { Comment } from "..";
import { Input, Button } from "@nextui-org/react";
import { useState } from "react";
import { useRecoilValue } from "recoil";
import { userState } from "@/atoms/user";

interface CommentRenderProps {
  comment: Comment;
  editHandler: (commentId: number, currentComment?: string) => void;
  edit: (commentId: number, content: string) => void;
  removeHandler: (commentId: number) => void
}

const CommentRender = ({ comment, editHandler, edit, removeHandler }: CommentRenderProps) => {
  const [currentComment, setCurrentComment] = useState<string>(comment.content);
  const user = useRecoilValue(userState)
  const completeHandler = (commentId: number) => {
    edit(commentId, currentComment);
    editHandler(comment.id, currentComment);
  };

  return (
    <div key={comment.id} className="grid gap-6 w-full">
      <div className="text-sm flex items-start gap-4 w-full my-5">
        <div className="grid gap-1.5 w-full">
          <div className="flex items-center gap-2">
            <div className="font-semibold">{comment.name}</div>
            <div className="text-gray-500 text-xs dark:text-gray-400">
              {dayjs(comment.modifiedAt).format("YYYY-MM-DD HH:mm")}
            </div>
            {
              user.name === comment.name
              ?
              <>
              {comment.isEdit ? (
                <Button
                  className="ml-auto"
                  size="lg"
                  variant="ghost"
                  isIconOnly
                  onClick={() => completeHandler(comment.id)}
                >
                  <EditIcon className="h-4 w-4" />
                  <span className="sr-only">complete comment</span>
                </Button>
              ) : (
                <Button
                  className="ml-auto"
                  size="lg"
                  variant="ghost"
                  isIconOnly
                  onClick={() => {
                    setCurrentComment(comment.content)
                    editHandler(comment.id)
                  }
                }
                >
                  <PencilIcon className="h-4 w-4" />
                  <span className="sr-only">Edit comment</span>
                </Button>
              )}
            <Button size="lg" variant="ghost" isIconOnly onClick={() => removeHandler(comment.id)}>
              <TrashIcon className="h-4 w-4" />
              <span className="sr-only">Delete comment</span>
            </Button>
              </>
              :null
            }

          </div>
          <div className="w-full text-lg">
            {comment.isEdit ? (
              <Input
                className="bg-white w-4/6"
                type="text"
                size="sm"
                value={currentComment}
                onChange={(e) => {
                  setCurrentComment(e.target.value);
                }}
              />
            ) : (
              <p>{comment.content}</p>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

export default CommentRender;
