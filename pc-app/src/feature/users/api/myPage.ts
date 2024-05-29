import { commonAxios } from "@/lib/commonAxios";
import { fileAxios } from "@/lib/fileAxios";

export const getMyPageBaseInfo = () => {
  return commonAxios.get("/my-page");
};

export const updateMyInfo = (
  name: string,
  beforePassword: string,
  newPassword: string
) => {
  return commonAxios.patch("/my-page/my-info", {
    name,
    beforePassword,
    newPassword,
  });
};

export const updateProfileImage = (formData: FormData) => {
  return fileAxios.post("/my-page/profile-image", formData);
};

export const editWeekGoal = (content: string) => {
  return commonAxios.post("/goal", {
    content,
  });
};
