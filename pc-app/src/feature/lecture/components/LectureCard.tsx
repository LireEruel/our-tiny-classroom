import { Card, CardHeader, CardBody, CardFooter, Avatar, Button } from "@nextui-org/react";
import { LectureCardProps } from "../types";
import { useNavigate } from "react-router-dom";
import { useRecoilValue } from "recoil";
import { userState } from "@/atoms/user";

export function LectureCard({ lecture, deleteHandler, addLectureToSchedule }: LectureCardProps): JSX.Element {
  const navigator = useNavigate();
  const user = useRecoilValue(userState);
  const currentRole = user.role === "ADMIN" ? "관리자" : user.role === "MENTOR" ? "멘토" : "학생";
  const lectureRole = lecture.member.role === "ROLE_ADMIN" ? "관리자" : lecture.member.role === "ROLE_MENTOR" ? "멘토" : "학생";

  return (
    <Card className="w-96 h-[15rem]">
      <div className="flex h-5/6 items-center">
        <CardHeader className="flex flex-col gap-5 w-2/6">
          <Avatar isBordered radius="full" size="lg" src="https://cdn.imweb.me/upload/S20220826948cbdc34dca3/a7e21622bc1f0.png" />
          <div className="flex flex-col gap-1 items-center justify-center">
            <h4 className="text-small font-semibold leading-none text-default-600">{lecture.member.name}</h4>
            <h5 className="text-small tracking-tight text-default-400">{lectureRole}</h5>
          </div>
        </CardHeader>
        <CardBody className="flex flex-col  h-[80%] px-3 py-0 text-small text-default-400 justify-between w-4/6 ">
          <div>
            <h6 className="pt-2 truncate my-1 text-lg text-black"> {lecture.title}</h6>
            <p className="">{lecture.description}</p>
          </div>
          <span className="pb-2 text-green-500">
            {" "}
            {lecture.date} / {lecture.timeTable + 1}교시
          </span>
        </CardBody>
      </div>
      <CardFooter className="justify-around gap-3 py-3">
        {currentRole === "관리자" || (currentRole === "멘토" && lecture.member.name == user.memberId) ? (
          <>
            <Button className="w-3/6 text-white" color="success" onClick={() => navigator(`./edit/${lecture.id}`)}>
              수정
            </Button>
            <Button className="w-3/6" color="danger" onClick={() => deleteHandler(lecture.id)}>
              삭제
            </Button>
          </>
        ) : (
          <>
            <Button onPress={() => addLectureToSchedule(lecture)} color="success" className="w-3/6 text-white">
              내 시간표에 추가하기
            </Button>
          </>
        )}
      </CardFooter>
    </Card>
  );
}
