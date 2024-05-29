type DayNamePack = {
  shortKr: string;
  fullKr: string;
};

const dayNameMap: Map<number, DayNamePack> = new Map();

dayNameMap.set(0, {
  shortKr: "일",
  fullKr: "일요일",
});

dayNameMap.set(1, {
  shortKr: "월",
  fullKr: "월요일",
});

dayNameMap.set(2, {
  shortKr: "화",
  fullKr: "화요일",
});

dayNameMap.set(3, {
  shortKr: "수",
  fullKr: "수요일",
});

dayNameMap.set(4, {
  shortKr: "목",
  fullKr: "목요일",
});

dayNameMap.set(5, {
  shortKr: "금",
  fullKr: "금요일",
});

dayNameMap.set(6, {
  shortKr: "토",
  fullKr: "토요일",
});

const getCurrentDayName = (dayValue: number) => {
  return dayNameMap.get(dayValue);
};

export { getCurrentDayName };
