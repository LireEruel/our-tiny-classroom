import { Button, Input } from "@nextui-org/react";
import { useCallback, useState } from "react";
import { LoginFormProps } from "../types";
// import KakaoLogin from "react-kakao-login";
// import { KAKAO_CLIENT_ID } from "@/config";

// interface KakaoLoginResponse {
// 	token_type: string;
// 	access_token: string;
// 	expires_in: string;
// 	refresh_token: string;
// 	refresh_token_expires_in: number;
// 	scope: string;
// }

export default function LoginForm({ onSubmit }: LoginFormProps) {
  const [inputId, setInputId] = useState("");
  const [inputPassword, setInputPassword] = useState("");

  const onChangeInputId = useCallback((e: React.ChangeEvent<HTMLInputElement>) => {
    setInputId(e.target.value);
  }, []);
  const onChangeInputPassword = useCallback((e: React.ChangeEvent<HTMLInputElement>) => {
    setInputPassword(e.target.value);
  }, []);
  const onSubmitForm = useCallback(
    (e: React.SyntheticEvent<HTMLFormElement>) => {
      onSubmit(inputId, inputPassword);
      setInputId("");
      setInputPassword("");
      e.preventDefault();
    },
    [inputId, inputPassword, onSubmit]
  );

  // const kakaoSuccessHandle = async (res: { response: KakaoLoginResponse }) => {
  //   const accessToken = res.response.access_token;
  //   onKakaoSuccess(accessToken);
  // };

  // const kakaoFailureHandle = (error: unknown) => {
  //   console.log(error);
  // };
  return (
    <form className="space-y-6" data-testid="LoginForm" onSubmit={onSubmitForm}>
      <div>
        <label htmlFor="input-id">아이디</label>
        <Input
          data-testid="LoginFormInputId"
          autoComplete="input-id"
          className="mt-1 block w-full"
          id="input-id"
          name="input-id"
          placeholder="아이디를 입력해주세요."
          required
          value={inputId}
          onChange={onChangeInputId}
        />
      </div>
      <div>
        <label htmlFor="password">비밀번호</label>
        <Input
          value={inputPassword}
          onChange={onChangeInputPassword}
          data-testid="LoginFormInputPassword"
          autoComplete="current-password"
          className="mt-1 block w-full"
          placeholder="비밀번호를 입력해주세요."
          id="password"
          name="password"
          required
          type="password"
        />
      </div>
      <div>
        <Button className="w-full bg-green-500 hover:bg-green-700 text-white" type="submit">
          로그인
        </Button>
      </div>
      {/* <div className="flex items-center justify-between">
        <div className="flex items-center">
          <KakaoLogin token={KAKAO_CLIENT_ID} onSuccess={kakaoSuccessHandle} onFail={kakaoFailureHandle} />
        </div>
      </div> */}
    </form>
  );
}
