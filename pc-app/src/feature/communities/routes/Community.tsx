import { BoardCategoriesMap, ClassCategoriesMap, CommunityHeaderContents, SearchArticlesOptions } from "../assets/textContent";
import { Board } from "../types";
import {
  Button,
  Divider,
  Input,
  Pagination,
  Select,
  SelectItem,
  Spinner,
  Table,
  TableBody,
  TableCell,
  TableColumn,
  TableHeader,
  TableRow,
} from "@nextui-org/react";
import { useCallback, useEffect, useState } from "react";
import dayjs from "dayjs";
import { searchBoard } from "../api/communityBoard";
import { NavigateFunction, useLocation, useNavigate } from "react-router-dom";
import { getPathInfo } from "../utils/getPathInfo";
import EditIcon from "@/assets/img/EditIcon";
import { useRecoilValue } from "recoil";
import { userState } from "@/atoms/user";
import { CODE } from "@/types/Code";

interface CommunityHeaderProps {
  boardCategory: number;
}

function CommunityHeader({ boardCategory }: CommunityHeaderProps) {
  return (
    <div>
      <div className="flex w-full px-10 py-5 items-center">
        <p className="text-2xl w-2/12 f">{CommunityHeaderContents[boardCategory]?.title}</p>
        <p className="w-10/12">{CommunityHeaderContents[boardCategory]?.content}</p>
      </div>
      <Divider />
    </div>
  );
}

interface SearchBarProps {
  searchKeyword: string;
  setSearchKeyword: (searchKeyword: string) => void;
  searchType: string;
  setSearchType: (searchType: string) => void;

  onSubmit: () => void;
}

function SearchBar({ searchKeyword, setSearchKeyword, searchType, setSearchType, onSubmit }: SearchBarProps) {
  return (
    <form className="w-full flex justify-between items-center" onSubmit={onSubmit}>
      <Select
        className="ml-5 bg-white w-1/6 rounded-xl"
        size="sm"
        defaultSelectedKeys={[SearchArticlesOptions[0].name]}
        aria-label="게시글을 검색할 방법을 선택해주세요."
        value={searchType}
      >
        {SearchArticlesOptions.map((item) => (
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
      <Button className="w-1/12 text-white text-xl rounded-xl bg-lime-500 shadow" size="lg" onClick={onSubmit}>
        검색
      </Button>
    </form>
  );
}

interface CommunityBoardProps {
  boardList: Array<Board>;
  totalPage: number;
  currentPage: number;
  setCurrentPage: (page: number) => void;
  loading: boolean;
  navigator: NavigateFunction;
}
function CommunityBoardTable({ boardList, totalPage, currentPage, setCurrentPage, loading, navigator }: CommunityBoardProps) {
  return (
    <Table
      aria-label="커뮤니티 게시판"
      selectionMode="single"
      bottomContent={
        totalPage > 0 ? (
          <div className="flex w-full justify-center">
            <Pagination
              isCompact
              showControls
              showShadow
              color="success"
              page={currentPage}
              total={totalPage}
              onChange={(page: number) => setCurrentPage(page)}
            />
          </div>
        ) : null
      }
    >
      <TableHeader>
        <TableColumn key="id">번호</TableColumn>
        <TableColumn key="title">제목</TableColumn>
        <TableColumn key="name">글쓴이</TableColumn>
        <TableColumn key="createdAt">날짜</TableColumn>
        <TableColumn key="hit">조회수</TableColumn>
      </TableHeader>
      <TableBody items={boardList} loadingState={loading ? "loading" : "idle"} loadingContent={<Spinner />}>
        {(board) => (
          <TableRow key={board.id} onClick={() => navigator("/communities/detail/" + board.id)}>
            <TableCell>{board.id}</TableCell>
            <TableCell
              className="cursor-pointer"
              // 테이블 속성에서 너비 조정이 제대로 먹지않아서 inline 속성으로 style 적용함. 추후에 css 파일로 분리 예정
              style={{
                whiteSpace: "nowrap",
                textOverflow: "ellipsis",
                overflow: "hidden",
                maxWidth: "25rem",
              }}
            >
              {board.title}
            </TableCell>
            <TableCell>{board.name}</TableCell>
            <TableCell>{dayjs(board.createdAt).format("YY-MM-DD")}</TableCell>
            <TableCell>{board.hit}</TableCell>
          </TableRow>
        )}
      </TableBody>
    </Table>
  );
}
function Community() {
  const userInfo = useRecoilValue(userState);
  const [searchType, setSearchType] = useState<string>(SearchArticlesOptions[0].value);
  const [searchKeyword, setSearchKeyword] = useState<string>("");
  const [boardList, setBoardList] = useState<Board[]>([]);
  const [currentPage, setCurrentPage] = useState<number>(1);
  const [totalPage, setTotalPage] = useState<number>(1);
  const [loading, setLoading] = useState<boolean>(false);
  const location = useLocation();
  const [typeBoard, setBoardType] = useState<number>(getPathInfo(location.pathname).boardType);
  const [boardCategory, setBoardCategory] = useState(CODE.BOARD_CATEGORY.NOTICE);

  useEffect(() => {
    const { boardType, boardCategory } = getPathInfo(location.pathname);
    setBoardType(boardType);
    setBoardCategory(boardCategory);
    setCurrentPage(1);
  }, [location]);
  const navigator = useNavigate();
  const fetchArticles = useCallback(async () => {
    try {
      setLoading(true);
      const res =
        typeBoard === 0
          ? await searchBoard({
              boardType: BoardCategoriesMap.get(boardCategory) ?? "notice",
              page: currentPage,
            })
          : await searchBoard({
              boardType: ClassCategoriesMap.get(boardCategory) ?? "notice",
              page: currentPage,
              classRoomId: userInfo.classRoomId,
            });
      const data = res.data;
      if (currentPage === res.data.number + 1) {
        setBoardList(data.content);
        setTotalPage(data.totalPages);
      }
    } catch (e) {
      console.error(e);
    } finally {
      setLoading(false);
    }
  }, [boardCategory, currentPage, typeBoard]);

  const searchArticles = async () => {
    try {
      setLoading(true);
      const res =
        typeBoard === 0
          ? await searchBoard({
              searchType: searchType,
              searchValue: searchKeyword,
              boardType: BoardCategoriesMap.get(boardCategory) ?? "notice",
              page: currentPage,
            })
          : await searchBoard({
              searchType: searchType,
              searchValue: searchKeyword,
              boardType: ClassCategoriesMap.get(boardCategory) ?? "notice",
              classRoomId: userInfo.classRoomId,
              page: currentPage,
            });
      const data = res.data;

      if (currentPage === res.data.number + 1) {
        setBoardList(data.content);
        setTotalPage(data.totalPages);
      }
    } catch (e) {
      console.error(e);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchArticles();
  }, [fetchArticles]);

  const onChangeCurrentPage = (newPage: number) => {
    setCurrentPage(newPage);
  };

  const onSearchBoard = async () => {
    setCurrentPage(1);
    await searchArticles();
  };

  const goCreateBoard = (boardType?: number, boardCategory?: number) => {
    navigator("/communities/write/new", {
      state: {
        boardType,
        boardCategory,
      },
    });
  };

  return (
    <div className="flex flex-col w-10/12">
      <CommunityHeader boardCategory={boardCategory} />
      <div className="lg:w-11/12 xl:w-10/12 mx-auto flex flex-col gap-10 py-10 items-center">
        <SearchBar
          searchType={searchType}
          searchKeyword={searchKeyword}
          setSearchKeyword={setSearchKeyword}
          setSearchType={setSearchType}
          onSubmit={onSearchBoard}
        />
        <CommunityBoardTable
          boardList={boardList}
          currentPage={currentPage}
          totalPage={totalPage}
          setCurrentPage={onChangeCurrentPage}
          loading={loading}
          navigator={navigator}
        />
      </div>
      <button
        className="fixed right-10 bottom-10 bg-lime-500 p-3 rounded-full"
        aria-label="게시글 작성"
        onClick={() => goCreateBoard(typeBoard, boardCategory)}
      >
        <EditIcon />
      </button>
    </div>
  );
}

export default Community;
