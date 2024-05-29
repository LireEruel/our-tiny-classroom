import { Route, Routes } from "react-router-dom";
import { SpecialLectureCreate } from "./SpecialLectureCreate";
import { SpecialLecture } from "./SpecialLecture";
import { SpecialLectureEdit } from "./SpecialLectureEdit";

export const LecturesRoutes = () => {
  return (
    <Routes>
      <Route path="" element={<SpecialLecture />} />
      <Route path="create" element={<SpecialLectureCreate />} />
      <Route path="edit/:lectureId" element={<SpecialLectureEdit />} />
    </Routes>
  );
};
