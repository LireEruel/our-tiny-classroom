/**
 * v0 by Vercel.
 * @see https://v0.dev/t/9nKwojc9Maz
 */

import { Button } from "@nextui-org/react";
import JoinForm from "../components/JoinForm";
import { useMutation } from "@tanstack/react-query";
import { join } from "../api/join";
import Swal from "sweetalert2";
import { useNavigate } from "react-router-dom";
import { useCallback } from "react";

export default function Join() {
  const navigate = useNavigate();

  const mutation = useMutation({
    mutationFn: (loginData: {
      memberId: string;
      password: string;
      name: string;
      email: string;
      birthday: string;
    }) => {
      return join(
        loginData.memberId,
        loginData.password,
        loginData.name,
        loginData.email,
        loginData.birthday
      );
    },

    onSuccess: () => {
      // 회원가입 성공 처리
      Swal.fire("성공!", "회원가입에 성공하였습니다.", "success").then(() => {
        navigate("/auth/login");
      });
    },
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    onError: (error: any) => {
      // 회원가입 실패 처리
      Swal.fire(
        "에러 발생",
        error?.response.data?.message ?? "회원가입에 실패하였습니다.",
        "error"
      );
    },
  });

  const onSubmit = useCallback(
    (
      memberId: string,
      password: string,
      name: string,
      email: string,
      birthday: string
    ) => {
      mutation.mutate({ memberId, password, name, email, birthday });
    },
    [mutation]
  );

  const goLogin = useCallback(() => {
    navigate("/auth/login");
  }, [navigate]);

  return (
    <div className="min-h-screen bg-yellow-100 flex flex-col justify-center py-12 sm:px-6 lg:px-8">
      <div className="sm:mx-auto sm:w-full">
        {/* <img
          alt="School Logo"
          className="mx-auto h-12 w-auto"
          src="/placeholder.svg"
        /> */}
        <h2 className="mt-6 text-center text-3xl font-extrabold text-gray-900">
          나만의 작은 교실에 오신 것을 환영합니다.
        </h2>
        <p className="mt-4 text-center text-sm text-gray-600">
          서비스를 이용하기 위해 회원가입을 진행하여주세요.
        </p>
      </div>
      <div className="mt-8 sm:mx-auto sm:w-full sm:max-w-md">
        <div className="bg-white py-8 px-4 shadow sm:rounded-lg sm:px-10">
          <JoinForm onSubmit={onSubmit} />
          <div className="mt-6">
            <div className="relative">
              <div className="absolute inset-0 flex items-center">
                <div className="w-full border-t border-gray-300" />
              </div>
              <div className="relative flex justify-center text-sm">
                <span className="px-2 bg-white text-gray-500">또는</span>
              </div>
            </div>
            <Button
              className="w-full bg-green-500 hover:bg-green-700 text-white mt-7"
              type="submit"
              onClick={goLogin}
            >
              로그인
            </Button>
          </div>
        </div>
      </div>
    </div>
  );
}
