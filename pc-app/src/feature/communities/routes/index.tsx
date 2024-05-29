import { Route, Routes } from "react-router-dom";
import CreateArticle from "./CreateArticle";
import Community from "./Community";
import DetailArticle from "./DetailArticle";
import CommunityLayout from "../components/CommunityLayout";

export const CommunitiesRoutes = () => {
  return (
    <Routes>
      <Route
        path="write/:articleId"
        element={
          <CommunityLayout>
            <CreateArticle />
          </CommunityLayout>
        }
      />
      <Route
        path="detail/:articleId"
        element={
          <CommunityLayout>
            <DetailArticle />
          </CommunityLayout>
        }
      />
      <Route
        path="/*"
        element={
          <CommunityLayout>
            <Community />
          </CommunityLayout>
        }
      />
    </Routes>
  );
};
