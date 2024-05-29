import { fileAxios } from "@/lib/fileAxios";

export const studentCertification = (formData: FormData) => {
  return fileAxios.post("/members/certification/student", formData);
};

export const mentorCertification = (formData: FormData) => {
  return fileAxios.post("/members/certification/mentor", formData);
};
