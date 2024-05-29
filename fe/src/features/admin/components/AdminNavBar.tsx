import {
  Navbar,
  NavbarBrand,
  NavbarContent,
  NavbarItem,
} from "@nextui-org/react";
import { useNavigate } from "react-router-dom";

interface AdminNavBarProps {
  setpage: (page: string) => void;
}

const AdminNavBar = ({ setpage }: AdminNavBarProps) => {
  const navigator = useNavigate();

  return (
    <Navbar className=" bg-primary" maxWidth="2xl">
      <NavbarBrand>
        <p
          onClick={() => navigator("/")}
          className="font-bold text-inherit text-white cursor-pointer">
          우리들의 작은 교실
        </p>
        <span className=" text-gray-100 mx-4">관리자 페이지</span>
      </NavbarBrand>
      <NavbarContent className="hidden sm:flex gap-4" justify="center">
        <NavbarItem onClick={() => setpage("user")}>
          <p className="text-white cursor-pointer">유저 관리</p>
        </NavbarItem>
        <NavbarItem onClick={() => setpage("accept")}>
          <p className="text-white cursor-pointer">등업 관리</p>
        </NavbarItem>
      </NavbarContent>
    </Navbar>
  );
};

export default AdminNavBar;
