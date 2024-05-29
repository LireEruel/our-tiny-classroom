import { CODE } from "@/types/Code";

interface CommunityPathInfo {
  boardType: number;
  boardCategory: number;
}
export const getPathInfo = (pathname: string): CommunityPathInfo => {
  const pathParts = pathname.split("/");
  const pathInfo = {
    boardType: CODE.BOARD_TYPE.SCHOOL,
    boardCategory: CODE.BOARD_CATEGORY.NOTICE,
  };

  if (pathParts[2]) {
    switch (pathParts[2]) {
      case "school":
        pathInfo.boardType = CODE.BOARD_TYPE.SCHOOL;
        break;
      case "class":
        pathInfo.boardType = CODE.BOARD_TYPE.CLASS;
        break;
      default:
        pathInfo.boardType = -1;
        pathInfo.boardCategory = -1;
    }
  }

  if (pathParts[3]) {
    switch (pathParts[3]) {
      case "notice":
        pathInfo.boardCategory = CODE.BOARD_CATEGORY.NOTICE;
        break;
      case "free":
        pathInfo.boardCategory = CODE.BOARD_CATEGORY.FREE;
        break;
      case "counseling":
        pathInfo.boardCategory = CODE.BOARD_CATEGORY.COUNSELING;
        break;
      case "hobby":
        pathInfo.boardCategory = CODE.BOARD_CATEGORY.HOBBY;
        break;
    }
  }
  return pathInfo;
};
