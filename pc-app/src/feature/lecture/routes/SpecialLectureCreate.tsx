import { Input, Select, SelectItem, Textarea, Button } from "@nextui-org/react";
import { ClassTime, LectureCategory } from "../assets/textContent";
import { useState } from "react";
import ReactDatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import "../assets/datepicker.scss";
import { createLecture } from "../api/lecture";
import { useNavigate } from "react-router-dom";
import { userState } from "@/atoms/user";
import { useRecoilValue } from "recoil";

export const SpecialLectureCreate = () => {
  const userInfo = useRecoilValue(userState);
  const [title, setTitle] = useState<string>("");
  const [description, setDescription] = useState<string>("");
  // TODO : 요일은 추후 파싱처리 필요
  const [dayOfWeek] = useState<number>(0);
  const [startDate, setStartDate] = useState<Date | null>(new Date());
  const [lectureCategoryType, setLectureCategoryType] = useState<number>(0);
  // TODO : 강의 타입 특강은 2로 고정
  const [lectureType] = useState<number>(2);
  const [timeTable, setTimeTable] = useState<number>(0);

  const navigator = useNavigate();

  const selectTime = (e: React.ChangeEvent<HTMLSelectElement>): void => {
    setTimeTable(Number(e.target.value));
  };
  const selectCategory = (e: React.ChangeEvent<HTMLSelectElement>): void => {
    setLectureCategoryType(Number(e.target.value));
  };

  const LectureCreate = async () => {
    try {
      if (startDate != null) {
        await createLecture(
          userInfo.userId,
          title,
          description,
          dayOfWeek,
          timeTable,
          lectureType,
          lectureCategoryType,
          startDate
        );
        navigator("/lecture");
      }
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <section className="w-full  h-screen overflow-y-auto">
      <p className="text-2xl ml-12 my-5">특강 생성</p>
      <div className="flex flex-col ml-12 my-5 gap-5">
        <p className="text-lg">특강 분류</p>
        <Select
          color="default"
          aria-label="특강 분류 선택"
          defaultSelectedKeys={String(LectureCategory[0].id)}
          className="max-w-xs"
          onChange={(e) => selectCategory(e)}
        >
          {LectureCategory.map((category) => (
            <SelectItem key={category.id} value={category.id}>
              {category.name}
            </SelectItem>
          ))}
        </Select>
      </div>
      <div className="flex flex-col ml-12 my-5 gap-5">
        <p className="text-2xl">특강 제목</p>
        <Input
          className="bg-white w-11/12"
          type="text"
          size="sm"
          placeholder="제목을 입력하세요."
          value={title}
          onChange={(e) => setTitle(e.target.value)}
        />
      </div>
      <div className="flex flex-col ml-12 my-5 gap-5">
        <p className="text-lg">특강 설명</p>
        <Textarea
          aria-label="특강 설명"
          placeholder="특강 설명을 입력하세요"
          className="w-11/12"
          value={description}
          minRows={15}
          maxRows={15}
          cacheMeasurements={true}
          onChange={(e) => setDescription(e.target.value)}
        />
      </div>
      <div className="flex ml-12 my-5 gap-40">
        <div className="flex flex-col w-2/6 gap-5 relative">
          <p>특강 날짜</p>
          <ReactDatePicker
            className="relative px-3 w-full inline-flex shadow-sm tap-highlight-transparent bg-default-100 data-[hover=true]:bg-default-200 group-data-[focus=true]:bg-default-100 rounded-medium flex-col items-start justify-center gap-0 outline-none data-[focus-visible=true]:z-10 data-[focus-visible=true]:outline-2 data-[focus-visible=true]:outline-focus data-[focus-visible=true]:outline-offset-2 h-14 min-h-unit-14 py-2 cursor-pointer"
            dateFormat="yyyy년 MM월 dd일"
            dateFormatCalendar="yyyy년 MM월"
            selected={startDate}
            popperPlacement="bottom"
            onChange={(date) => setStartDate(date)}
          />
        </div>
        <div className="flex flex-col w-2/6 gap-5">
          <p>특강 교시</p>
          <Select
            color="default"
            aria-label="특강 분류 선택"
            defaultSelectedKeys={String(ClassTime[0].value)}
            className="max-w-full"
            onChange={(e) => selectTime(e)}
          >
            {ClassTime.map((time) => (
              <SelectItem key={time.value}>{time.name}</SelectItem>
            ))}
          </Select>
        </div>
      </div>
      <Button className="ml-12 my-5 w-2/12 bg-lime-500 text-white" onClick={() => LectureCreate()}>
        생성하기
      </Button>
    </section>
  );
};
