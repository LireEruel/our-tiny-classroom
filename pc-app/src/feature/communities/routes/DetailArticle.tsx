import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { getDetail } from "../api/detailBoard";
import { editComment, removeComment } from "../api/comments";
import { BoardDetail } from "../types";
import {
  Button,
  Skeleton,
} from "@nextui-org/react";
import dayjs from "dayjs";
import CommentsList from "../components/CommentsList";
import Parser from "html-react-parser";
import CommentInput from "../components/CommentInput";
import { Comment } from "../types";
import { useRecoilValue } from "recoil";
import { userState } from "@/atoms/user";
import { deleteBoard } from "../api/communityBoard";

function DetailArticle() {
  const [isLoaded, setIsLoaded] = useState(false);
  const [commentList, setCommentList] = useState<Comment[]>([]);
  const [boardDetails, setBoardDetails] = useState<null | BoardDetail>(null);
  const { articleId } = useParams();
  const userInfo = useRecoilValue(userState);
  const navigator = useNavigate();

  // 렌더링 시 데이터 불러오는 부분
  useEffect(() => {
    const renderDetail = async () => {
      if (articleId) {
        try {
          setIsLoaded(false);
          const res = await getDetail(articleId);
          setBoardDetails(res.data);
          setCommentList(propertyAddHandler(res.data.articleComments));
        } catch (error) {
          console.error(error);
        } finally {
          setIsLoaded(true);
        }
      }
    };
    renderDetail();
  }, [articleId]);

  // 수정 중인지 체크하는 변수 추가 함수

  const propertyAddHandler = (propsComments: Comment[]) => {
    return propsComments.map((comment) => ({
      ...comment,
      isEdit: false,
    }));
  };

  // 댓글 삭제 함수
  const commentRemove = (commentId: number | undefined) => {
    try {
      removeComment(commentId);
    } catch (error) {
      console.error(error);
    }
  };
  const boardDelete = async (articleId: number) => {
    await deleteBoard(articleId);
    navigator("/communities");
  };
  // 댓글 수정 함수
  const commentEdit = (commentId: number, content: string) => {
    try {
      editComment(commentId, content);
    } catch (error) {
      console.error(error);
    }
  };
  const goEditArticle = () => {
    navigator(`/communities/write/${articleId}`);
  };

  return (
    <article className="w-10/12 h-screen overflow-y-auto">
      <div className="w-full xl:w-10/12 p-4 md:p-6 lg:p-8 ">
        <div className="flex flex-col gap-10 prose prose-gray dark:prose-invert">
          <div className="flex flex-col gap-3 space-y-2 not-prose">
            <Skeleton isLoaded={isLoaded} className="rounded-lg w-full">
              <h1 className="text-2xl font-extrabold tracking-tight lg:text-3xl xl:text-4xl lg:leading-[3.5rem] w-full truncate">
                {boardDetails ? boardDetails.title : ""}
              </h1>
            </Skeleton>

            <div className="flex items-center text-gray-500 dark:text-gray-400">
              <Skeleton isLoaded={isLoaded} className="rounded-lg w-full ">
                <div className="w-full flex justify-between content-between">
                  <div className="flex items-center">
                    <span>{boardDetails ? boardDetails.name : ""}</span>
                    <span className="mx-2">•</span>
                    <span>
                      {boardDetails
                        ? boardDetails.modifiedAt
                          ? `${dayjs(boardDetails?.modifiedAt).format(
                              "YYYY년 MM월 DD일"
                            )} 수정`
                          : `${dayjs(boardDetails?.createdAt).format(
                              "YYYY년 MM월 DD일"
                            )} 작성`
                        : ""}
                    </span>
                  </div>
                  {userInfo.name === boardDetails?.name ? (
                    <div className="flex gap-10">
                      <Button
                        onClick={goEditArticle}
                        variant="ghost"
                        color="success"
                        className=" font-bold"
                      >
                        수정
                      </Button>
                      <Button
                        onClick={() => boardDelete(boardDetails.id)}
                        variant="ghost"
                        color="danger"
                        className=" font-bold"
                      >
                        삭제
                      </Button>
                    </div>
                  ) : null}
                </div>
              </Skeleton>
            </div>
          </div>
          <Skeleton isLoaded={isLoaded} className="rounded-lg">
            <p>{boardDetails ? Parser(boardDetails.content) : ""}</p>
          </Skeleton>
          <CommentInput
            articleId={articleId ? +articleId : -1}
            commentList={commentList}
            setList={setCommentList}
          />
          <CommentsList
            commentList={commentList}
            remove={commentRemove}
            edit={commentEdit}
            setList={setCommentList}
          />
        </div>
      </div>
    </article>
  );
}

export default DetailArticle;
