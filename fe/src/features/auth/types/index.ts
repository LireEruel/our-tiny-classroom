import { BaseResponse } from "@/types/BaseResponse";

export interface JoinFormProps {
  onSubmit: (
    id: string,
    password: string,
    name: string,
    email: string,
    birthday: string
  ) => void;
}

export interface JoinResponse extends BaseResponse {
  data:
    | {
        memberId: string;
        name: string;
      }
    | undefined; // data 는 성공했을 때에만 있으며 실패했을 경우에는 undefined
}

export interface LoginFormProps {
  onSubmit: (id: string, password: string) => void;
  onKakaoSuccess: (accessToken: string) => void;
}

export interface LoginResponse extends BaseResponse {
  data: {
    name: string;
    role: string;
    memberId: string;
    point: number;
    refreshToken: string;
  };
}
