import { CODE } from "@/types/Code";

export const LectureCategory = [
	{
		id: CODE.LECTURE_CATEGORY.ALL,
		name: "전체",
		image: "https://i.namu.wiki/i/oue1NCn0ejKPZgHqsUYAer_tvO-7Jarrq_6uqUT4Gkm9H3P0ADs9F-4-TU4R_RXPHXc06RcD9FrWlAlcQYH7fQ.svg",
	},
	{
		id: CODE.LECTURE_CATEGORY.MATH,
		name: "수학",
		Image: "https://wimg.mk.co.kr/meet/neds/2021/12/image_readtop_2021_1193610_16401132634890263.jpg",
	},
	{
		id: CODE.LECTURE_CATEGORY.SCIENCE,
		name: "과학",
		image: "https://humanities.snu.ac.kr/webdata/researchcenter/077z179zba1z4dez834zbbbz1d6z617z306z943ze5.jpg",
	},
	{
		id: CODE.LECTURE_CATEGORY.ART,
		name: "미술",
		Image: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSbgteG11UCyoK8kh66TLMFJZN3ZsuMYe3LPw&usqp=CAU",
	},
	{
		id: CODE.LECTURE_CATEGORY.HEALTH,
		name: "건강",
		image: "https://scsgozneamae10236445.cdn.ntruss.com/data2/content/image/2023/04/03/.cache/512/20230403580239.jpg",
	},
	{
		id: CODE.LECTURE_CATEGORY.READING,
		name: "독서",
		image: "https://t1.daumcdn.net/cfile/tistory/120D63464ED498CA02",
	},
	{
		id: CODE.LECTURE_CATEGORY.FUTURE,
		name: "상담",
		image: "https://cdn.imweb.me/upload/S20210317a96d22bffcce0/71b78c5062899.png",
	},
	{
		id: CODE.LECTURE_CATEGORY.COMMUNICATION,
		name: "소통",
		image: "https://images.christiantoday.co.kr/data/images/full/347383/image.jpg?w=654",
	},
	{
		id: CODE.LECTURE_CATEGORY.ETC,
		name: "기타",
		image: "https://velog.velcdn.com/images/wnduf8922/post/5feda343-e0ae-4527-b1b2-0e40400b5ff6/image.jpg",
	},
];

export const ClassTime = [
	{ name: "1교시(9:00 ~ 9:50)", value: 0 },
	{ name: "2교시(10:00 ~ 10:50)", value: 1 },
	{ name: "3교시(11:00 ~ 11:50)", value: 2 },
	{ name: "4교시(13:00 ~ 13:50)", value: 3 },
	{ name: "5교시(14:00 ~ 14:50)", value: 4 },
	{ name: "6교시(15:00 ~ 15:50)", value: 5 },
];

export const SearchLecturesOptions = [
	{ name: "제목", value: "title" },
	{ name: "내용", value: "description" },
	{ name: "요일", value: "dayOfWeek" },
	{ name: "시간", value: "timeTable" },
];
