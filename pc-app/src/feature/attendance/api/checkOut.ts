import { commonAxios } from "@/lib/commonAxios";

const checkOut = () => {
  return commonAxios.patch("/attendances/checkout");
};

export { checkOut };
