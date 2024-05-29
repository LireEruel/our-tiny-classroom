import { Button, Input } from "@nextui-org/react";
import { useCallback, useState } from "react";
import { JoinFormProps } from "../types";
import dayjs from "dayjs";

export default function JoinForm({ onSubmit }: JoinFormProps) {
  const [inputId, setInputId] = useState("");
  const [inputPassword, setInputPassword] = useState("");
  const [inputPasswordConfirm, setInputPasswordConfirm] = useState("");
  const [inputName, setInputName] = useState("");
  const [inputEmail, setInputEmail] = useState("");
  const [inputBirthday, setInputBirthday] = useState("");
  const onChangeInputId = useCallback(
    (e: React.ChangeEvent<HTMLInputElement>) => {
      setInputId(e.target.value);
    },
    []
  );
  const onChangeInputPassword = useCallback(
    (e: React.ChangeEvent<HTMLInputElement>) => {
      setInputPassword(e.target.value);
    },
    []
  );
  const onChangeInputPasswordConfirm = useCallback(
    (e: React.ChangeEvent<HTMLInputElement>) => {
      setInputPasswordConfirm(e.target.value);
    },
    []
  );
  const onChangeInputName = useCallback(
    (e: React.ChangeEvent<HTMLInputElement>) => {
      setInputName(e.target.value);
    },
    []
  );
  const onChangeInputEmail = useCallback(
    (e: React.ChangeEvent<HTMLInputElement>) => {
      setInputEmail(e.target.value);
    },
    []
  );
  const onChangeInputBirthday = useCallback(
    (e: React.ChangeEvent<HTMLInputElement>) => {
      setInputBirthday(e.target.value);
    },
    []
  );
  const onSubmitForm = useCallback(
    (e: React.SyntheticEvent<HTMLFormElement>) => {
      const parsedBirthday = dayjs(inputBirthday).format("YYYY-MM-DD");

      onSubmit(inputId, inputPassword, inputName, inputEmail, parsedBirthday);
      setInputId("");
      setInputPassword("");
      setInputPasswordConfirm("");
      setInputName("");
      setInputEmail("");
      setInputBirthday("");
      e.preventDefault();
    },
    [inputBirthday, inputEmail, inputId, inputName, inputPassword, onSubmit]
  );
  return (
    <form className="mb-6" onSubmit={onSubmitForm} data-testid="JoinForm">
      <div className="mb-8 flex flex-col items-center justify-center gap-4">
        <Input
          id="id"
          placeholder="아이디를 입력해주세요."
          label="아이디"
          labelPlacement="outside"
          value={inputId}
          onChange={onChangeInputId}
          data-testid="JoinFormInputId"
          size="sm"
        />

        <Input
          id="password"
          placeholder="비밀번호를 입력해주세요."
          type="password"
          label="비밀번호"
          labelPlacement="outside"
          value={inputPassword}
          onChange={onChangeInputPassword}
          data-testid="JoinFormInputPassword"
          size="sm"
        />

        <Input
          id="password-confirm"
          placeholder="비밀번호를 다시 입력해주세요."
          type="password"
          label="비밀번호 확인"
          labelPlacement="outside"
          value={inputPasswordConfirm}
          onChange={onChangeInputPasswordConfirm}
          data-testid="JoinFormInputPasswordConfirm"
          size="sm"
          className="mt-1 block w-full"
        />

        <Input
          id="name"
          placeholder="이름을 입력해주세요."
          label="이름"
          labelPlacement="outside"
          value={inputName}
          onChange={onChangeInputName}
          data-testid="JoinFormInputName"
          size="sm"
        />

        <Input
          id="email"
          placeholder="이메일을 입력해주세요."
          label="이메일"
          labelPlacement="outside"
          type="email"
          value={inputEmail}
          onChange={onChangeInputEmail}
          data-testid="JoinFormInputEmail"
          size="sm"
        />
        {/* 날짜 선택기로 바꾸기 */}
        <Input
          id="birthday"
          placeholder="Enter your birthday"
          type="date"
          size="sm"
          className="p-0"
          label="생년월일"
          labelPlacement="outside"
          value={inputBirthday}
          onChange={onChangeInputBirthday}
          data-testid="JoinFormInputBirthday"
        />
      </div>

      <Button
        type="submit"
        className="w-full bg-green-500 hover:bg-green-700 text-white"
      >
        확인
      </Button>
    </form>
  );
}
