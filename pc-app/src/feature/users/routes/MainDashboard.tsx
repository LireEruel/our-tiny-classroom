import { getCurrentDayName } from "@/utils/DateFormattingHelpers";
import { Button, Card, CardBody, CardFooter, CardHeader } from "@nextui-org/react";
import dayjs from "dayjs";
import { useCallback, useEffect, useState } from "react";
import { checkIn } from "../../attendance/api/checkIn";
import { Attendance, attnedanceMap } from "@/feature/attendance";
import { getTodayAttendance } from "@/feature/attendance/api/getAttendance";
import Swal from "sweetalert2";
import { useMutation } from "@tanstack/react-query";
import { checkOut } from "@/feature/attendance/api/checkOut";
import { useRecoilValue } from "recoil";
import { userState } from "@/atoms/user";
import { NavigateFunction, useNavigate } from "react-router-dom";
import ScheduleBoard from "@/feature/schedule/components/ScheduleBoard";
import { searchBoard } from "@/feature/communities/api/communityBoard";
import { Board } from "@/feature/communities";
import { CurrentLecture, requestCurrentSchedule } from "@/feature/schedule/api/getSchedule";

interface AttendanceCardProps {
  todayDate: dayjs.Dayjs;
  checkIn?: string;
  checkOut?: string;
  status?: number;
  doCheckIn: () => void;
  doCheckOut: () => void;
}

//입실, 퇴실 정보를 조회하고 입, 퇴실을 할 수 있는 기능을 가진 카드
function AttendanceCard({ todayDate, checkIn, checkOut, doCheckIn, doCheckOut, status }: AttendanceCardProps) {
  return (
    <div className="bg-lime-200 p-4 shadow flex flex-col gap-3 space-y-4 rounded w-4/12 h-full">
      <h2 className="text-lg font-bold">출석체크 현황</h2>
      <div className="flex items-center space-x-2 justify-between w-full ">
        <div className="flex flex-col gap">
          <div className="w-3 bg-blue-500 h-1 rounded" />
          <span className="font-bold text-blue-500 text-lg mb-3">{todayDate.format("MM.DD")}</span>
          <span className="text-blue-500 text-xs">{getCurrentDayName(todayDate.day())?.fullKr ?? ""}</span>
        </div>
        <div className="flex h-16 gap-4">
          {checkIn ? (
            <div className="p-2 w-20 flex flex-col items-center bg-zinc-500 justify-evenly">
              <span className="text-white h-8">{dayjs(checkIn).format("HH:mm")}</span>
              <span className="text-white text-xs">{status ? attnedanceMap.get(status) : ""}</span>
            </div>
          ) : (
            <Button
              onClick={doCheckIn}
              className="text-blue-500 bg-white h-full rounded-none w-8 font-bold hover:bg-blue-600 hover:text-white "
            >
              입실하기
            </Button>
          )}

          {
            checkIn ? ( // 아직 입실을 하지 않았다면 퇴실 버튼을 보일 필요가 없으므로 입실 여부 확인.
              checkOut ? ( // 퇴실을 한 상태라면, 다시 갱신할 수 있는 버튼
                <Button
                  onClick={doCheckOut}
                  className="p-2 flex flex-col items-center bg-zinc-500 justify-evenly rounded-none h-full hover:bg-blue-500 font-bold"
                >
                  <span className="text-white h-8">{dayjs(checkOut).format("hh:mm")}</span>
                  <span className="text-white text-xs ">퇴실하기</span>
                </Button>
              ) : (
                // 입실을 했지만 아직 퇴실을 하지 않은 상태
                <Button onClick={doCheckOut} className="text-blue-500 bg-white h-full rounded-none w-8 font-bold">
                  퇴실하기
                </Button>
              )
            ) : null // 아직 입실을 하지 않았다면 퇴실 버튼을 보일 필요가 없음.
          }
        </div>
      </div>
    </div>
  );
}

function NoticeListCard() {
  const [notices, setNotices] = useState([] as Board[]);
  const getNotices = async () => {
    const response = await searchBoard({
      boardType: "notice",
      boardSize: 3,
    });
    setNotices(response.data.content);
  };
  const navigator = useNavigate();

  const goArticleDetail = (articleId: number) => {
    navigator("/communities/detail/" + articleId);
  };

  useEffect(() => {
    getNotices();
  }, []);
  return (
    <div className=" flex flex-grow bg-white p-4 rounded-lg shadow flex-col w-1/2">
      <h2 className="text-lg font-semibold mb-4">공지사항</h2>
      <ul className="space-y-2">
        {notices.slice(0, 3).map((notice) => (
          <li
            id={notice.id.toString()}
            key={notice.id}
            className="flex cursor-pointer hover:text-green-500"
            onClick={() => goArticleDetail(notice.id)}
          >
            <span className="text-sm mr-4 text-green-500 ">{notice.name}</span>
            <span className="text-sm truncate w-4/5">{notice.title}</span>
          </li>
        ))}
      </ul>
    </div>
  );
}

interface CurrentLectureCardProps {
  navigator: NavigateFunction;
}
function CurrentLectureCard({ navigator }: CurrentLectureCardProps) {
  const goClassRoom = (url: string) => navigator("/video", { state: { url: url } });
  const [lecture, setLecture] = useState<CurrentLecture | null>(null);

  useEffect(() => {
    const getData = async () => {
      const res = await requestCurrentSchedule();
      setLecture(res.data);
    };
    getData();
  }, []);

  const getLectureType = () => {
    switch (lecture?.lectureType) {
      case "SPECIAL_LECTURE":
        return "특강";
      case "REGULAR_LECTURE":
        return "정규 수업";
      case "FREE_LECTURE":
        return "자유 수업";
      default:
        return "자유 수업";
    }
  };
  return (
    <div>
      <Card className="p-4 rounded-lg">
        {lecture !== null ? (
          <>
            <CardHeader>
              <div className="flex justify-between w-full gap-3">
                <h6 className="text-lg font-semibold">{lecture?.title ?? ""}</h6>
              </div>
            </CardHeader>
            <CardBody>
              <div className="flex  items-center w-full">
                <p>
                  수업 종류 : <span> {getLectureType()} </span>
                </p>
              </div>
            </CardBody>

            <CardFooter>
              <Button color="success" onClick={() => goClassRoom(lecture.lectureUrl)}>
                참석하기
              </Button>
            </CardFooter>
          </>
        ) : (
          <CardBody>
            <p>현재 진행중인 수업이 없습니다.</p>
          </CardBody>
        )}
      </Card>
    </div>
  );
}
export default function MainDashBoard() {
  const todayDate = dayjs().startOf("day");

  const [attendanceState, setAttendanceState] = useState<Attendance | undefined>();
  const userInfo = useRecoilValue(userState);
  const navigator = useNavigate();

  const getAttendanceState = useCallback(async () => {
    try {
      const res = await getTodayAttendance(userInfo.memberId);
      setAttendanceState(res.data.attendanceOnDate);
    } catch (e) {
      console.log(e);
    }
  }, [userInfo.memberId]);

  useEffect(() => {
    getAttendanceState();
  }, [getAttendanceState]);

  const checkInMutation = useMutation({
    mutationFn: checkIn,
    onSuccess: (res) => {
      setAttendanceState({
        date: attendanceState?.date,
        checkOut: attendanceState?.checkOut,
        checkIn: res.data.checkInTime,
        status: res.data.status,
      });
    },
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    onError: (e: any) => {
      Swal.fire("주의", e?.response.data.message ?? "등교에 실패하였습니다. 다시 시도해주세요.", "warning");
    },
  });

  const checkOutMutation = useMutation({
    mutationFn: checkOut,
    onSuccess: (res) => {
      setAttendanceState({
        date: attendanceState?.date,
        checkIn: attendanceState?.checkIn,
        checkOut: res.data.checkOutTime,
        status: res.data.status,
      });
    },
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    onError: (e: any) => {
      Swal.fire("에러 발생!", e?.response.data.message ?? "하교에 실패하였습니다. 다시 시도해주세요.", "error");
    },
  });

  const doCheckIn = async () => {
    checkInMutation.mutate();
  };

  const doCheckOut = async () => {
    checkOutMutation.mutate();
  };

  return (
    <div className="flex w-full">
      <div className="bg-white min-h-screen  w-3/4">
        <header className="flex justify-between items-center mb-6 shadow w-full py-5 pl-24 pr-10">
          <h1 className="text-2xl font-bold">나만의 작은 코너</h1>
          <div className="flex space-x-2"></div>
        </header>
        <section className="flex flex-grow w-full gap-6 px-24">
          <AttendanceCard
            todayDate={todayDate}
            checkIn={attendanceState?.checkIn}
            checkOut={attendanceState?.checkOut}
            status={attendanceState?.status}
            doCheckIn={doCheckIn}
            doCheckOut={doCheckOut}
          />
          <NoticeListCard />
        </section>
        <div className="px-24 h-1/2 mt-5">
          <div className="flex flex-col gap-5 h-full">
            <ScheduleBoard editMode={false} />
          </div>
        </div>
      </div>
      <div className="bg-lime-100 w-1/4 p-10">
        <CurrentLectureCard navigator={navigator} />
      </div>
    </div>
  );
}
