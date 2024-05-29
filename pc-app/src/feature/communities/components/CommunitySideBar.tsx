import { Link, useLocation } from "react-router-dom";
import { CommunityHeaderContents, CommunityHeaderContentsClass } from "../assets/textContent";
import { useEffect, useState } from "react";
import { CODE } from "@/types/Code";
import { getPathInfo } from "../utils/getPathInfo";
import { useRecoilValue } from "recoil";
import { userState } from "@/atoms/user";

function CommunitySideBar() {
  const userInfo = useRecoilValue(userState);
  const location = useLocation();
  const [boardCategory, setBoardCategory] = useState(CODE.BOARD_CATEGORY.NOTICE);
  const [boardType, setBoardType] = useState(CODE.BOARD_TYPE.SCHOOL);

  useEffect(() => {
    const { boardType, boardCategory } = getPathInfo(location.pathname);
    let boardState;

    if (location.state) {
      boardState = location.state;
    } else {
      boardState = null;
    }
    if (boardCategory !== -1) {
      setBoardCategory(boardCategory);
    } else {
      boardState ? setBoardCategory(boardState.boardCategory) : null;
    }
    if (boardType !== -1) {
      setBoardType(boardType);
    } else {
      boardState ? setBoardType(boardState.boardType) : null;
    }
    
  }, [location]);

  const selectionStyle = (_boardType: number, _boardCategory: number): string => {
    if (boardType === _boardType && boardCategory === _boardCategory) {
      return "bg-lime-400";
    } else {
      return "hover:bg-lime-300";
    }
  };

  return (
    <section className=" min-w-56 w-2/12 h-full bg-lime-50 py-3">
      {boardType === 0 ? (
        <>
          <Link to={"/communities"}>
            <p className="text-xl px-3 py-4 font-bold">학교 게시판</p>
          </Link>
          <ul className="text-lg py-3 flex flex-col gap-1 bg-lime-200">
            {CommunityHeaderContents.map((community) => (
              <Link to={"/communities/school" + community.location} key={community.location}>
                <li className={` pl-10 py-3 rounded-2xl mx-2 duration-200 ${selectionStyle(boardType, community.id)}`}>
                  {community.title}
                </li>
              </Link>
            ))}
          </ul>
        </>
      ) : (
        <>
          <Link to={"/communities/class"}>
            <p className="text-xl px-3 py-4 font-bold">{userInfo.class}반 게시판</p>
          </Link>
          <ul className="text-lg py-3 flex flex-col gap-1 bg-lime-200">
            {CommunityHeaderContentsClass.map((community) => (
              <Link to={"/communities/class" + community.location} key={community.location}>
                <li className={` pl-10 py-3 rounded-2xl mx-2 duration-200 ${selectionStyle(boardType, community.id)}`}>
                  {community.title}
                </li>
              </Link>
            ))}
          </ul>
        </>
      )}
    </section>
  );
}

export default CommunitySideBar;
