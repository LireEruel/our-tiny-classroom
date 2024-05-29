import { Route, Routes } from "react-router-dom";

import Login from "./Login";
import Join from "./Join";

export const AuthRoutes = () => {
  return (
    <Routes>
      <Route path="join" element={<Join />} />
      <Route path="login" element={<Login />} />
    </Routes>
  );
};
