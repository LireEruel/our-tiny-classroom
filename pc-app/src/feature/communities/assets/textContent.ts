import { CODE } from "@/types/Code";
import { TYPE } from "..";

export const CommunityHeaderContents = [
  {
    id: CODE.BOARD_CATEGORY.NOTICE,
    title: "공지사항 🔊",
    content: "학교에서 공지사항을 안내해드립니다.",
    location: "/notice",
  },
  {
    id: CODE.BOARD_CATEGORY.FREE,
    title: "자유 게시판 ✨",
    content: "어떤 생각이든 이야기든 자유롭게 공유해요",
    location: "/free",
  },
  {
    id: CODE.BOARD_CATEGORY.COUNSELING,
    title: "고민 나눔 🧶",
    content: "가슴속 품고 있는 고민들을 이야기해요",
    location: "/counseling",
  },
  {
    id: CODE.BOARD_CATEGORY.HOBBY,
    title: "취미 공유 🎨",
    subtitle: "상상만 해도 즐거운 취미가 있나요?",
    location: "/hobby",
  },
];

export const CommunityHeaderContentsClass = [
  {
    id: TYPE.CLASS_CATEGORY.NOTICE,
    title: "공지사항 🔊",
    content: "학교에서 공지사항을 안내해드립니다.",
    location: "/notice",
  },
  {
    id: TYPE.CLASS_CATEGORY.FREE,
    title: "자유 게시판 ✨",
    content: "어떤 생각이든 이야기든 자유롭게 공유해요",
    location: "/free",
  },
];

export const BoardCategoriesMap: Map<number, string> = new Map();
BoardCategoriesMap.set(CODE.BOARD_CATEGORY.NOTICE, "notice");
BoardCategoriesMap.set(CODE.BOARD_CATEGORY.FREE, "free");
BoardCategoriesMap.set(CODE.BOARD_CATEGORY.COUNSELING, "counseling");
BoardCategoriesMap.set(CODE.BOARD_CATEGORY.HOBBY, "hobby");

export const ClassCategoriesMap: Map<number, string> = new Map();
ClassCategoriesMap.set(TYPE.CLASS_CATEGORY.NOTICE, "notice");
ClassCategoriesMap.set(TYPE.CLASS_CATEGORY.FREE, "free");

export const SearchArticlesOptions = [
  {
    name: "제목",
    value: "title",
  },
  {
    name: "내용",
    value: "content",
  },
  {
    name: "제목 + 내용",
    value: "title_content",
  },
  {
    name: "작성자",
    value: "name",
  },
];
