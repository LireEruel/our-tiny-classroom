import { LectureCard } from "../components/LectureCard";
import { Input, Select, SelectItem, Button } from "@nextui-org/react";
import EditIcon from "@/assets/img/EditIcon";
import { useNavigate } from "react-router";

import { deleteLecture, getAddAbleSpecialLectures, searchLecture } from "../api/lecture";
import { useEffect, useState } from "react";
import { useRecoilState } from "recoil";
import { userState } from "@/atoms/user";
import { Lecture } from "../types";
import { SearchLecturesOptions } from "../assets/textContent";
import { createSchedule } from "@/feature/schedule/api/createSchedule";
import Swal from "sweetalert2";

// 검색기능

interface SearchBarProps {
  searchKeyword: string;
  setSearchKeyword: (searchKeyword: string) => void;
  searchType: string;
  setSearchType: (searchType: string) => void;
  lectuerSearch: () => void;
}

function SearchBar({ searchKeyword, setSearchKeyword, searchType, setSearchType, lectuerSearch }: SearchBarProps) {
  return (
    <form className="w-4/6 flex justify-between items-center">
      <Select
        className="ml-5 bg-white w-1/6 rounded-xl"
        size="sm"
        defaultSelectedKeys={[SearchLecturesOptions[0].name]}
        aria-label="게시글을 검색할 방법을 선택해주세요."
        value={searchType}
      >
        {SearchLecturesOptions.map((item) => (
          <SelectItem key={item.name} value={item.value} onClick={() => setSearchType(item.value)}>
            {item.name}
          </SelectItem>
        ))}
      </Select>
      <Input
        className="bg-white w-4/6"
        type="text"
        placeholder="검색할 내용을 입력해주세요."
        size="sm"
        value={searchKeyword}
        onChange={(e) => setSearchKeyword(e.target.value)}
      />
      <Button className="w-1/12 text-white text-xl rounded-xl bg-lime-500 shadow" size="lg" onClick={lectuerSearch}>
        검색
      </Button>
    </form>
  );
}

export function SpecialLecture() {
  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  const userInfo = useRecoilState(userState);
  const [lectureLists, setLectureLists] = useState<Lecture[]>([]);
  const [searchType, setSearchType] = useState<string>(SearchLecturesOptions[0].value);
  const [searchKeyword, setSearchKeyword] = useState<string>("");

  const navigator = useNavigate();
  // 특강 목록 조회
  useEffect(() => {
    const onLoading = async () => {
      try {
        const lectures = await getAddAbleSpecialLectures();
        setLectureLists(lectures.data);
      } catch (error) {
        console.error(error);
      }
    };
    onLoading();
  }, []);

  // 특강 삭제
  const lectureDelete = async (lectureId: number): Promise<void> => {
    try {
      await deleteLecture(lectureId);
      const renderLectures = lectureLists?.filter((lecture) => {
        return lecture.id !== lectureId;
      });
      setLectureLists(renderLectures);
    } catch (error) {
      console.error(error);
    }
  };

  // 검색 기능
  const lectuerSearch = async () => {
    try {
      const responseData = await searchLecture(searchType, searchKeyword);
      setLectureLists(responseData.data);
    } catch (error) {
      console.error(error);
    }
  };

  const addLectureToSchedule = async (lecture: Lecture) => {
    try {
      await createSchedule(lecture.id, lecture.date, lecture.timeTable);
      Swal.fire("성공", "특강이 내 시간표에 추가되었습니다.", "success");
      // eslint-disable-next-line @typescript-eslint/no-explicit-any
    } catch (error: any) {
      Swal.fire("실패", error.response.data.message, "error");
    }
  };

  return (
    <>
      <section className="w-[100%] h-screen overflow-y-auto">
        <div className="flex justify-center my-5 sticky">
          <SearchBar
            searchType={searchType}
            searchKeyword={searchKeyword}
            setSearchKeyword={setSearchKeyword}
            setSearchType={setSearchType}
            lectuerSearch={lectuerSearch}
          />
        </div>
        <h2 className="text-2xl px-12 py-7">모집중인 특강 목록</h2>
        <div className=" h-full px-12 py-5 flex flex-wrap gap-10 overflow-y-scroll scrollbar-hide">
          {lectureLists.length !== 0 ? (
            lectureLists.map((lectures: Lecture, index: number) => {
              return (
                <LectureCard
                  lecture={lectures}
                  key={index}
                  deleteHandler={lectureDelete}
                  addLectureToSchedule={addLectureToSchedule}
                />
              );
            })
          ) : (
            <div className=" flex justify-center items-center">
              <p className="text-xl text-lime-600 text-center">현재 모집중인 특강이 없습니다</p>
            </div>
          )}
        </div>
      </section>
      {userInfo[0].role === "MENTOR" || userInfo[0].role === "ADMIN" ? (
        <button
          className="fixed right-10 bottom-10 bg-lime-500 p-3 rounded-full"
          aria-label="특강 등록"
          onClick={() => navigator("./create")}
        >
          <EditIcon />
        </button>
      ) : null}
    </>
  );
}
