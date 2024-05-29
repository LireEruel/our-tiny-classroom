export interface Lecture {
  id: number;
  approved: string;
  date: string;
  memberId: string;
  title: string;
  description: string;
  dayOfWeek: number;
  timeTable: number;
  lectureType: number;
  lectureCategoryType: number;
  member: {
    name: string;
    role: number | string;
  };
  lectureId?: number;
}

export interface LectureCardProps {
  lecture: Lecture;
  deleteHandler: (lectureId: number) => Promise<void>;
  addLectureToSchedule: (lecture: Lecture) => Promise<void>;
}
