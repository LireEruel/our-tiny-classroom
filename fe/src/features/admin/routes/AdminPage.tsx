import { useEffect, useState } from "react";
import AdminNavBar from "../components/AdminNavBar";
import AdminAccept from "../components/AdminAccept";
import AdminUserBody from "../components/AdminUserBody";
import { userState } from "@/atoms/user";
import { useRecoilValue } from "recoil";
import { useNavigate } from "react-router-dom";

const AdminPage = () => {
  const [currentPage, setCurrentPage] = useState<string>("user");
  const userInfo = useRecoilValue(userState);
  const navigator = useNavigate();
  const pageHandler = (page: string): void => {
    setCurrentPage(page);
  };

  const renderPage = () => {
    switch (currentPage) {
      case "user":
        return <AdminUserBody />;
      case "accept":
        return <AdminAccept />;
      default:
        return null;
    }
  };

  useEffect(() => {
    if (userInfo?.role !== "ADMIN") {
      navigator("/");
    }
  }, [userInfo]);

  return (
    <>
      <AdminNavBar setpage={pageHandler} />
      {renderPage()}
    </>
  );
};

export default AdminPage;
