import dayjs from "dayjs";
import { Button, Divider, useDisclosure } from "@nextui-org/react";
import { getCurrentDayName } from "@/utils/DateFormattingHelpers";
import { Tag } from "@/components/Elements/Tag/Tag";
import { useCallback, useEffect, useState } from "react";
import { Plan, Schedule } from "..";
import { getWeekSchedules } from "../api/getSchedule";
import { useRecoilValue } from "recoil";
import { userState } from "@/atoms/user";
import { faCaretDown, faCaretUp, faSquarePlus, faTrash } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { deleteSchedule } from "../api/deleteSchedule";
import ScheduleModal from "./ScheduleModal";

interface CalenderHeaderProps {
  planStartDate: dayjs.Dayjs;
  onClickChangePlanDate: (isBefore: boolean) => void;
}

function CalenderHeader({ planStartDate, onClickChangePlanDate }: CalenderHeaderProps) {
  return (
    <div className="flex justify-between">
      <div></div>
      <div className="flex gap-3">
        <span>{`${planStartDate.format("MM월 DD일")} - ${planStartDate.add(4, "day").format("MM월 DD일")}`}</span>
        <span
          onClick={() => onClickChangePlanDate(true)}
          className="cursor-pointer w-7 h-7 bg-lime-100 rounded-full flex items-center justify-center shadow hover:bg-lime-200"
        >
          <FontAwesomeIcon icon={faCaretUp} />
        </span>
        <span
          onClick={() => onClickChangePlanDate(false)}
          className="cursor-pointer w-7 h-7 bg-lime-100 rounded-full flex items-center justify-center shadow hover:bg-lime-200"
        >
          <FontAwesomeIcon icon={faCaretDown} />
        </span>
      </div>
    </div>
  );
}

interface ScheduleContentProp {
  schedule: Schedule;
}
function ScheduleContent({ schedule }: ScheduleContentProp) {
  return (
    <p className=" h-full w-full text-center flex items-center justify-center">
      {schedule.lectureType == "SPECIAL_LECTURE" ? <Tag color="red">특강</Tag> : null}
      {schedule.lectureType == "REGULAR_LECTURE" ? <Tag color="blue"> 정규 </Tag> : null}
      {schedule.title}
    </p>
  );
}

interface ScheduleItemProp {
  date: string;
  schedule: Schedule | null;
  editMode: boolean;
  dayOfWeek: number;
  onDelete: (scheduleId: number) => void;
  fetchScheduleList: () => void;
}
function ScheduleItem({ schedule, date, dayOfWeek, editMode, onDelete, fetchScheduleList }: ScheduleItemProp) {
  const { isOpen, onOpen, onOpenChange } = useDisclosure();
  const [modalOpen, setModalOpen] = useState<boolean>(false);
  if (editMode) {
    return (
      <div className="h-full p-2">
        {schedule == null ? (
          <div className="flex justify-center items-center h-full">
            <Button onPress={onOpen} isIconOnly color="success" onClick={() => setModalOpen(!modalOpen)}>
              <FontAwesomeIcon icon={faSquarePlus} />
            </Button>
            {modalOpen ? (
              <ScheduleModal
                isOpen={isOpen}
                dayOfWeek={dayOfWeek}
                date={date}
                onOpenChange={onOpenChange}
                fetchScheduleList={fetchScheduleList}
              />
            ) : null}
          </div>
        ) : (
          <div className="flex flex-col justify-center">
            <div className="flex justify-end">
              <Button className="flex text-red-500" isIconOnly onClick={() => onDelete(schedule.scheduleId)}>
                <FontAwesomeIcon icon={faTrash} size="lg" />
              </Button>
            </div>
            <ScheduleContent schedule={schedule} />
          </div>
        )}
      </div>
    );
  } else {
    return (
      <div className="flex justify-center items-center h-full">
        {schedule === null ? null : <ScheduleContent schedule={schedule} />}
      </div>
    );
  }
}
interface ScheduleBoardProps {
  editMode: boolean;
}

function ScheduleBoard({ editMode }: ScheduleBoardProps) {
  const userInfo = useRecoilValue(userState);
  const todayDate = dayjs().startOf("day");
  const [planStartDate, setPlanStartDate] = useState(dayjs().subtract(dayjs().day() - 1, "day"));
  const [planList, setPlanList] = useState([] as Plan[]);

  const makeDefaultPlanList = useCallback(() => {
    const newPlanList = [] as Plan[];
    for (let planIndex = 0; planIndex < 5; planIndex++) {
      const newScheduleList = Array(6).fill(null) as Array<Schedule | null>;
      newPlanList.push({
        id: planIndex,
        date: planStartDate.add(planIndex, "day").startOf("day"),
        scheduleList: newScheduleList,
        dayOfWeek: planIndex,
      });
    }
    return newPlanList;
  }, [planStartDate]);

  const fetchScheduleList = useCallback(async () => {
    const newPlanList = makeDefaultPlanList();

    try {
      const res = await getWeekSchedules(userInfo.memberId, planStartDate.format("YYYY-MM-DD"));
      if (res.data.monday == planStartDate.format("YYYY-MM-DD")) {
        let scheduleIndex = 0;
        res.data.scheduleList.forEach((schedule) => {
          if (scheduleIndex != schedule.timeTable) {
            for (let i = scheduleIndex; i < schedule.timeTable; i++) {
              if (newPlanList[schedule.dayOfWeek].scheduleList[scheduleIndex] === null) {
                newPlanList[schedule.dayOfWeek].scheduleList[scheduleIndex] = null;
              }
            }
          }
          newPlanList[schedule.dayOfWeek].scheduleList[schedule.timeTable] = schedule;
          scheduleIndex++;
        });
      } else {
        // 지금 화면에 보여야 할 스케줄 기간과 서버가 준 기간이 다르므로 아무 처리도 하지 않습니다.
      }
    } catch (e) {
      console.error(e);
    } finally {
      setPlanList(newPlanList);
    }
  }, [makeDefaultPlanList, planStartDate, userInfo.memberId]);

  const onClickChangePlanDate = (isBefore: boolean) => {
    if (isBefore) {
      setPlanStartDate(planStartDate.subtract(7, "day")); // 일주일 빼기
    } else {
      setPlanStartDate(planStartDate.add(7, "day")); // 일주일 더하기
    }
  };

  const onDeleteSchedule = async (ScheduleId: number) => {
    try {
      await deleteSchedule(ScheduleId);
      fetchScheduleList();
    } catch (e) {
      console.error(e);
    }
  };

  useEffect(() => {
    fetchScheduleList();
  }, [fetchScheduleList]);

  return (
    <div className="flex flex-col gap-5 h-full">
      <CalenderHeader planStartDate={planStartDate} onClickChangePlanDate={onClickChangePlanDate} />
      <div className="flex w-full h-full">
        <Divider orientation="vertical" />
        <ul className="flex flex-col">
          {/*가장 앞 목차 번호 */}
          <Divider />
          <li className=" h-10 w-10">{/* 왼쪽 맨 위 빈칸 */}</li>
          {Array.from({ length: 6 }, (_, i) => (
            <li key={i} className=" w-10 h-1/6 ">
              <Divider />
              <p className=" text-center flex justify-center items-center h-full">{i + 1}</p>
            </li>
          ))}
          <Divider />
          {/*가장 앞 목차 번호 끝 */}
        </ul>
        <div className="flex w-full">
          {/*시간표 부분 */}
          {planList.map((plan) => (
            <div key={plan.id} className="flex w-1/5 h-full">
              <Divider orientation="vertical" />
              {/*하루치 시간표 세로로 한줄씩 렌더링 */}
              <ul
                className={`flex flex-col h-full w-full ${
                  +todayDate.format("YYYYMMDD") == +plan.date.format("YYYYMMDD") ? "bg-green-50 font-semibold" : ""
                }`}
              >
                <Divider />
                {/* 시간표에서 날짜 보여주는 맨 윗칸 */}
                <li className=" w-full text-center h-10 flex items-center justify-center">
                  {`${plan.date.format("YYYY-MM-DD")} (${getCurrentDayName(plan.date.day())?.shortKr})`}
                </li>
                {plan.scheduleList.map((schedule, index) => (
                  <li key={index} className="h-1/6 w-full ">
                    <Divider />
                    <ScheduleItem
                      schedule={schedule}
                      date={plan.date.format("YYYY-MM-DD")}
                      dayOfWeek={index}
                      editMode={editMode}
                      onDelete={onDeleteSchedule}
                      fetchScheduleList={fetchScheduleList}
                    />
                  </li>
                ))}
                <Divider />
              </ul>
            </div>
          ))}
          <Divider orientation="vertical" />
        </div>
      </div>
    </div>
  );
}

export default ScheduleBoard;
