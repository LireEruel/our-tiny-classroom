import { Button, Image } from "@nextui-org/react";
import { useState } from "react";
import MentorImg from "../assets/MentorImage.png";
import StudentImg from "../assets/StudentImage.png";
import MentorDocumentForm from "../components/MentorDocumentForm";
import { CODE } from "@/types/Code";
import StudentDocumentForm from "../components/StudentDocumentForm";
import { mentorCertification, studentCertification } from "../api/certification";
import { useMutation } from "@tanstack/react-query";
import Swal from "sweetalert2";
import { AxiosResponse } from "axios";
import { useNavigate } from "react-router-dom";
import { useRecoilValue } from "recoil";
import { userState } from "@/atoms/user";
import { logout } from "../api/logout";

interface StepsProps {
  certificationStep: number;
}
function Steps({ certificationStep }: StepsProps) {
  const getStepStyle = (step: number): string => {
    if (certificationStep == step) {
      return "bg-lime-500 text-white";
    } else {
      return " bg-slate-500 text-black";
    }
  };
  return (
    <div>
      <ul className="flex justify-center items-center gap-7">
        <li className={getStepStyle(0) + " bg-lime-500 text-white font-bold px-5 py-2 rounded-full"}>회원 유형 선택</li>
        <li className={getStepStyle(1) + " bg-lime-500 text-white font-bold px-5 py-2 rounded-full"}>회원 정보 등록</li>
        <li className={getStepStyle(2) + " bg-lime-500 text-white font-bold px-5 py-2 rounded-full"}>회원 승인 대기</li>
      </ul>
    </div>
  );
}
interface SelectRoleProps {
  onSelectRole: (role: number) => void;
}
function SelectRole({ onSelectRole }: SelectRoleProps) {
  return (
    <div className=" max-w-7xl mx-auto">
      <h1 className=" text-center my-14 text-2xl">활동할 역할을 선택해주세요</h1>
      <div className="flex items-center justify-center">
        <ul className="flex gap-20 w-full">
          <li className="flex flex-col items-center justify-center w-full border-3 rounded-lg p-10 hover:border-lime-300 hover:bg-lime-50">
            <Image height={200} width={200} src={StudentImg} radius="full" />
            <h2 className=" my-5 text-3xl font-bold"> 학생</h2>
            <Button
              onClick={() => onSelectRole(CODE.ROLE.STUDENT_SELECT)}
              className="w-full bg-lime-500 text-2xl py-6 text-white"
            >
              가입하기
            </Button>
          </li>
          <li className="flex flex-col items-center justify-center w-full border-3 rounded-lg p-10 hover:border-lime-300 hover:bg-lime-50">
            <Image height={200} width={200} src={MentorImg} radius="full" />
            <h2 className=" my-5 text-3xl font-bold"> 멘토</h2>
            <Button onClick={() => onSelectRole(CODE.ROLE.MENTOR_SELECT)} className="w-full bg-lime-500 text-2xl py-6 text-white">
              가입하기
            </Button>
          </li>
        </ul>
      </div>
    </div>
  );
}

interface SubmitDocumentProps {
  role: number;
  onSubmitStudentDocument: (formData: FormData) => void;
  onSubmitMentorDocument: (formData: FormData) => void;
  goBefore: (current: number) => void;
  loading: boolean;
}
function SubmitDocument({ role, onSubmitStudentDocument, onSubmitMentorDocument, goBefore, loading }: SubmitDocumentProps) {
  return (
    <div className="flex flex-col items-center justify-center">
      <h1 className="my-6 text-xl">서류를 제출해주세요</h1>
      {role === CODE.ROLE.MENTOR_SELECT ? (
        <MentorDocumentForm onSubmit={onSubmitMentorDocument} goBefore={goBefore} loading={loading} />
      ) : (
        <StudentDocumentForm onSubmit={onSubmitStudentDocument} goBefore={goBefore} loading={loading} />
      )}
    </div>
  );
}

function WaitingConfirm() {
  return (
    <div className="flex flex-col items-center justify-center gap-10 my-20">
      <img
        src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/People/Man%20Teacher.png"
        alt="Man Teacher"
        width="400"
        height="400"
      />
      <h2 className=" text-4xl font-bold text-green-500">인증 절차가 정상적으로 완료되었습니다.</h2>
      <p className=" text-2xl font-semibold">관리자의 승인을 기다려주세요.</p>
    </div>
  );
}

function Certification() {
  const userInfo = useRecoilValue(userState);
  const [certificationStep, setCertificationStep] = useState(userInfo.role === "WAITING" ? 2 : 0);
  const [role, setRole] = useState<null | number>(null);
  const [loading, setLoading] = useState(false);

  const onSelectRole = (selectedRole: number) => {
    setRole(selectedRole);
    setCertificationStep((certificationStep) => certificationStep + 1);
  };

  const goBefore = (current: number) => {
    setCertificationStep(current - 1);
  };

  const studentCertificationMutation = useMutation({
    mutationFn: (formData: FormData) => {
      return studentCertification(formData);
    },

    onSuccess: () => {
      // 회원 정보 등록 성공 처리
      Swal.fire("성공!", "회원 정보 등록에 성공하였습니다.", "success");
      setCertificationStep(2);
      setLoading(false);
    },
    onError: (error: AxiosResponse) => {
      // 회원 정보 등록 실패 처리
      Swal.fire("에러 발생", error.data?.message ?? "회원 정보 등록에 실패하였습니다.", "error");
      setLoading(false);
    },
  });

  const mentorCertificationMutation = useMutation({
    mutationFn: (formData: FormData) => {
      return mentorCertification(formData);
    },

    onSuccess: () => {
      // 회원 정보 등록 성공 처리
      Swal.fire("성공!", "회원 정보 등록에 성공하였습니다.", "success");
      setCertificationStep(2);
      setLoading(false);
    },
    onError: (error: AxiosResponse) => {
      // 회원 정보 등록 실패 처리

      Swal.fire("에러 발생", error.data?.message ?? "회원 정보 등록에 실패하였습니다.", "error");
      setLoading(false);
    },
  });

  const onSubmitStudentDocument = (formData: FormData) => {
    setLoading(true);
    studentCertificationMutation.mutate(formData);
  };

  const onSubmitMentorDocument = (formData: FormData) => {
    setLoading(true);
    mentorCertificationMutation.mutate(formData);
  };

  const navigator = useNavigate();
  const onPressLogout = async () => {
    try {
      await logout();
    } finally {
      localStorage.clear();
      navigator("/");
    }
  };

  return (
    <div className=" w-full h-full p-10">
      <Steps certificationStep={certificationStep} />
      {certificationStep === 0 ? <SelectRole onSelectRole={onSelectRole} /> : null}
      {certificationStep === 1 && role !== null ? (
        <SubmitDocument
          onSubmitStudentDocument={onSubmitStudentDocument}
          onSubmitMentorDocument={onSubmitMentorDocument}
          goBefore={goBefore}
          role={role}
          loading={loading}
        />
      ) : null}
      {certificationStep === 2 || userInfo.role == "WAITING" ? <WaitingConfirm /> : null}
      <div className=" flex justify-center items-center mt-10">
        <Button className="text-white" onPress={onPressLogout} color="success">
          다른 계정으로 로그인하기
        </Button>
      </div>
    </div>
  );
}

export default Certification;
