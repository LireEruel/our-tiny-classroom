import { Modal, ModalContent, ModalHeader, ModalBody, ModalFooter, Button, Input } from "@nextui-org/react";
import { useEffect, useState } from "react";
import { Accordion, AccordionItem } from "@nextui-org/react";
import { createSchedule, getSpecialLecture, getFreeLecture } from "../api/createSchedule";
import Swal from "sweetalert2";

type CreateScheduleProps = {
  isOpen: boolean;
  onOpenChange: (isOpen: boolean) => void;
  date: string;
  dayOfWeek: number;
  fetchScheduleList: () => void;
};

type ResponseLecture = {
  approved: string;
  date: null;
  dayofWeek: number;
  description: string;
  id: number;
  lectureCategoryType: string;
  lectureType: string;
  member: {
    name: string;
    role: string;
  };
  timeTable: number;
  title: string;
};

type FreeLectureState = {
  [key: string]: ResponseLecture[];
};

export default function ScheduleModal({ isOpen, onOpenChange, date, dayOfWeek, fetchScheduleList }: CreateScheduleProps) {
  const [todoSchedule, setTodoSchedule] = useState<string>("");
  const [currentLecture, setCurrentLecture] = useState<number>(1);
  const [specialLecture, setSpecialLecture] = useState<ResponseLecture[]>([]);
  const [freeLecture, setFreeLecture] = useState<FreeLectureState>({});
  const [isSpecial, setIsSpecial] = useState<boolean>(false);

  useEffect(() => {
    const getCategories = async () => {
      try {
        const responseSpecial = await getSpecialLecture(date, dayOfWeek);
        const responseFree = await getFreeLecture("FREE_LECTURE");
        setSpecialLecture(responseSpecial.data);
        // 카테고리 별 매핑
        const groupedLectures = responseFree.data.reduce(
          (categoryFreeLecture: { [category: string]: ResponseLecture[] }, lecture: ResponseLecture) => {
            const category: string = lecture.lectureCategoryType;
            if (!categoryFreeLecture[category]) {
              categoryFreeLecture[category] = [];
            }
            categoryFreeLecture[category].push(lecture);
            return categoryFreeLecture;
          },
          {}
        );
        setFreeLecture(groupedLectures);

        if (responseSpecial.data.length > 0) {
          setSpecialLecture(responseSpecial.data);
          setIsSpecial(true);
        } else {
          setIsSpecial(false);
        }
      } catch (error) {
        console.error(error);
      }
    };
    getCategories();
  }, []);

  const scheduleCreate = async (lectureId: number) => {
    try {
      await createSchedule(lectureId, date, dayOfWeek);
      fetchScheduleList();
      // eslint-disable-next-line @typescript-eslint/no-explicit-any
    } catch (e: any) {
      Swal.fire({
        title: "실패!!",
        text: `${e.response.data.message}`,
        icon: "error",
      });
    }
  };

  const renderSpecialLecture = specialLecture.map((item: ResponseLecture) => {
    return (
      <p
        className="group-[.is-splitted]:px-4 group-[.is-splitted]:bg-content1 group-[.is-splitted]:shadow-medium group-[.is-splitted]:rounded-medium border-1 rounded-xl px-5 cursor-pointer"
        key={item.id}
        aria-label={`${item.id}`}
        onClick={() => {
          setTodoSchedule(item.title);
          setCurrentLecture(item.id);
        }}
      >
        <h2 className="flex py-4 w-full h-full gap-3 items-center tap-highlight-transparent outline-none data-[focus-visible=true]:z-10 data-[focus-visible=true]:outline-2 data-[focus-visible=true]:outline-focus data-[focus-visible=true]:outline-offset-2 transition-opacity">
          {item.title}
        </h2>
      </p>
    );
  });
  return (
    <>
      <Modal isOpen={isOpen} onOpenChange={onOpenChange} className="max-w-[800px]">
        <ModalContent>
          {(onClose) => (
            <>
              <ModalHeader className="flex flex-col gap-1">일정 추가</ModalHeader>
              <ModalBody>
                <Input type="text" label="할일" disabled placeholder="할 일을 고르세요" value={todoSchedule} />
                <div className="flex mt-5">
                  <p className="w-[400px]">일반 항목</p>
                  <p className="w-[400px]">특강 항목</p>
                </div>
                <div className="flex">
                  <div className="flex flex-col w-full overflow-y-scroll h-52 gap-5 scrollbar-hide">
                    {Object.keys(freeLecture).map((item: string, index: number) => {
                      return (
                        <Accordion key={index}>
                          <AccordionItem className="border-1 rounded-xl px-5" key={`${index}`} aria-label={item} title={item}>
                            {freeLecture[item].map((info: ResponseLecture) => {
                              return (
                                <p
                                  className="p-5 cursor-pointer"
                                  onClick={() => {
                                    setTodoSchedule(info.title);
                                    setCurrentLecture(info.id);
                                  }}
                                  key={info.id}
                                >
                                  {info.title}
                                </p>
                              );
                            })}
                          </AccordionItem>
                        </Accordion>
                      );
                    })}
                  </div>
                  <div className="flex flex-col w-full overflow-y-scroll h-52 gap-5 scrollbar-hide">
                    {isSpecial ? renderSpecialLecture : <p className="w-full h-full text-center">진행중인 특강이 없습니다.</p>}
                  </div>
                </div>
              </ModalBody>
              <ModalFooter>
                <Button color="danger" onPress={onClose}>
                  닫기
                </Button>
                <Button
                  color="primary"
                  onPress={onClose}
                  onClick={() => {
                    scheduleCreate(currentLecture);
                  }}
                >
                  추가
                </Button>
              </ModalFooter>
            </>
          )}
        </ModalContent>
      </Modal>
    </>
  );
}
