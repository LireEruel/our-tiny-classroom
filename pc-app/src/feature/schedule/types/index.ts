import { Dayjs } from "dayjs";

export interface Plan {
  id: number;
  date: Dayjs;
  scheduleList: Array<Schedule | null>;
  dayOfWeek: number;
}

export interface Schedule {
  lectureId: number;
  scheduleId: number;
  title: string;
  scheduleDate: string;
  dayOfWeek: 0 | 1 | 2 | 3 | 4;
  memberId: number;
  timeTable: number;
  deletable: boolean;
  lectureType: string;
  lectureCategoryType: string;
}
