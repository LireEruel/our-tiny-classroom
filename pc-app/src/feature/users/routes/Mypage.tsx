import {
  Button,
  CircularProgress,
  Avatar,
  Image,
  Modal,
  useDisclosure,
  ModalContent,
  ModalHeader,
  ModalBody,
  ModalFooter,
  Input,
} from "@nextui-org/react";
import { useCallback, useEffect, useRef, useState } from "react";
import { MyPageUserInfo } from "../types/myPage";
import { editWeekGoal, getMyPageBaseInfo, updateMyInfo, updateProfileImage } from "../api/myPage";
import { useNavigate } from "react-router-dom";
import Swal from "sweetalert2";

interface UserInfo {
  name: string;
  grade: number;
  classNumber: number;
  profileImage: string;
  point: number;
  attendanceRate: number;
}

interface UserProfileProps {
  userInfo: UserInfo | null;
}
const UserProfile = ({ userInfo }: UserProfileProps) => {
  const format_class_info = userInfo
    ? userInfo.grade < 4
      ? `중등과정 ${userInfo.grade}학년 ${userInfo.classNumber}반`
      : `고등과정 ${userInfo.grade - 3}학년 ${userInfo.classNumber}반`
    : "";

  return (
    <div className="flex w-full items-center min-h-10 ">
      <div>
        <Avatar alt="User Profile Image" src={userInfo?.profileImage} className="w-40 h-40" />
      </div>
      <div className="w-1/2">
        <h2 className="text-xl font-semibold mx-4 py-1 ">{userInfo?.name}</h2>
        <p className="text-sm text-gray-500 mx-4">{format_class_info}</p>
        <div className=" bg-green-100 w-full m-4 rounded-lg flex flex-row p-2">
          <div className="">
            <p className="text-lg font-semibold ">포인트 </p>
          </div>
          <div className=" text-right">
            <p className="text-lg font-bold text-right"> {userInfo?.point}P</p>
          </div>
        </div>
      </div>
    </div>
  );
};

type EditUserInfoModalProp = {
  userInfo: UserInfo | null;
  isOpen: boolean;
  onOpenChange: (isOpen: boolean) => void;
  onSubmitEditUserInfo: (name: string, password: string, newPassword: string, profile: File | null) => void;
  isLoading: boolean;
};
const EditUserInfoModal = ({ userInfo, isOpen, onOpenChange, onSubmitEditUserInfo, isLoading }: EditUserInfoModalProp) => {
  const [name, setName] = useState(userInfo ? userInfo?.name : "");
  const [beforePassword, setBeforePassword] = useState("");
  const [password, setPassword] = useState("");
  const [passwordConfirm, setPasswordConfirm] = useState("");
  const [profile, setProfile] = useState<File | null>(null);
  // input에 연결해주기 위한 useRef 훅 사용
  const imageInputRef = useRef<HTMLInputElement | null>(null);

  const onChangeName = useCallback((e: React.ChangeEvent<HTMLInputElement>) => {
    setName(e.target.value);
  }, []);

  const onChangeBeforePassword = useCallback((e: React.ChangeEvent<HTMLInputElement>) => {
    setBeforePassword(e.target.value);
  }, []);
  const onChangePassword = useCallback((e: React.ChangeEvent<HTMLInputElement>) => {
    setPassword(e.target.value);
  }, []);
  const onChangePasswordConfirm = useCallback((e: React.ChangeEvent<HTMLInputElement>) => {
    setPasswordConfirm(e.target.value);
  }, []);

  const onChangeProfile = (event: React.ChangeEvent<HTMLInputElement>) => {
    // event.target.files가 null이 아님을 확인하고 첫 번째 파일을 가져옵니다.
    if (event.target.files) {
      const file = event.target.files[0];
      setProfile(file);
    }
  };

  const onSubmit = () => {
    if (password === passwordConfirm) {
      onSubmitEditUserInfo(name, beforePassword, password, profile);
    } else {
      Swal.fire("주의!", "비밀번호와 비밀번호 확인의 값이 다릅니다.", "warning");
    }
  };

  // 이미지찾기 버튼 클릭 시 호출하는 함수 (클릭 이벤트)
  const onCickImageUploadHandler = (): void => {
    imageInputRef.current?.click();
  };

  useEffect(() => {
    setName(userInfo ? userInfo.name : "");
    setBeforePassword("");
    setPassword("");
    setPasswordConfirm("");
    setProfile(null);
  }, [userInfo, isOpen]);

  return (
    <Modal isOpen={isOpen} onOpenChange={onOpenChange}>
      <ModalContent>
        {(onClose) => (
          <>
            <ModalHeader className="flex flex-col gap-1">유저 정보 수정</ModalHeader>
            <ModalBody>
              <Input label="이름" value={name} onChange={onChangeName}></Input>
              <Input label="현재 비밀번호" type="password" value={beforePassword} onChange={onChangeBeforePassword}></Input>
              <Input label="새 비밀번호" type="password" value={password} onChange={onChangePassword}></Input>
              <Input label="새 비밀번호 확인" type="password" value={passwordConfirm} onChange={onChangePasswordConfirm}></Input>
              <Button
                color="primary"
                onClick={() => {
                  onCickImageUploadHandler();
                }}
              >
                변경할 프로필 이미지 선택
              </Button>
              <input
                // input의 ref 속성을 이용해 버튼 클릭 이벤트를 input과 연결
                ref={imageInputRef}
                type="file"
                name="file"
                onChange={onChangeProfile}
                className=" hidden"
              />
              {profile && <p>선택된 파일 : {profile.name}</p>}
            </ModalBody>
            <ModalFooter>
              <Button color="danger" variant="light" onPress={onClose}>
                취소
              </Button>
              <Button isLoading={isLoading} color="primary" onPress={onSubmit}>
                확인
              </Button>
            </ModalFooter>
          </>
        )}
      </ModalContent>
    </Modal>
  );
};

type WeekGoalCardProps = {
  thisWeekGoalContent: string;
};

const WeekGoalCard = ({ thisWeekGoalContent }: WeekGoalCardProps) => {
  const [content, setContent] = useState(thisWeekGoalContent);
  const [inputGoal, setInputGoal] = useState(thisWeekGoalContent);
  const [editState, setEditState] = useState(false);

  const onChangeInputGoal = useCallback((e: React.ChangeEvent<HTMLInputElement>) => {
    setInputGoal(e.target.value);
  }, []);
  useEffect(() => {
    setContent(thisWeekGoalContent);
  }, [thisWeekGoalContent]);

  const onSubmitGoal = async () => {
    try {
      await editWeekGoal(inputGoal);
      setContent(inputGoal);
      setInputGoal("");
      setEditState(false);
      // eslint-disable-next-line @typescript-eslint/no-explicit-any
    } catch (e: any) {
      Swal.fire("에러 발생", e.response.data.message, "error");
    }
  };

  return (
    <div className="bg-green-100 rounded-lg p-4">
      <p className="font-bold mb-3 text-green-900"> 이번주 목표</p>
      {editState ? (
        <div>
          <Input className="my-4" value={inputGoal} onChange={onChangeInputGoal}></Input>
          <div className="flex gap-2">
            <Button onPress={() => setEditState(false)} className="bg-red-200">
              취소
            </Button>
            <Button onPress={() => onSubmitGoal()} color="primary">
              확인
            </Button>
          </div>
        </div>
      ) : (
        <div>
          <p className="my-4">{content.length ? content : "설정된 목표가 없습니다. 목표를 설정해주세요."}</p>
          <Button color="primary" onPress={() => setEditState(true)}>
            수정하기
          </Button>
        </div>
      )}
    </div>
  );
};
const Mypage = () => {
  const [userInfo, setUserInfo] = useState<MyPageUserInfo | null>(null);
  const { isOpen, onOpen, onOpenChange, onClose } = useDisclosure();
  const [isLoading, setIsLoading] = useState(false);

  const fetchUserInfo = async () => {
    try {
      const res = await getMyPageBaseInfo();
      console.log(res);
      setUserInfo(res.data);
    } catch (error) {
      console.log(error);
    }
  };

  const onSubmitEditUserInfo = async (name: string, password: string, newPassword: string, profileImage: File | null) => {
    try {
      setIsLoading(true);
      if (name.length || password.length || newPassword.length) {
        await updateMyInfo(name, password, newPassword);
        if (name.length) {
          fetchUserInfo();
        }
      }
      if (profileImage) {
        const formData = new FormData();
        formData.append("image", profileImage);
        await updateProfileImage(formData);
      }
      onClose();
      // eslint-disable-next-line @typescript-eslint/no-explicit-any
    } catch (error: any) {
      Swal.fire("주의", error.response.data.message, "warning");
    } finally {
      setIsLoading(false);
    }
  };

  useEffect(() => {
    fetchUserInfo();
  }, []);

  const navigator = useNavigate();

  const goArticleDetail = (articleId: number) => {
    navigator("/communities/detail/" + articleId);
  };

  const goScheduleDetail = () => {
    navigator("/schedule");
  };
  return (
    <div className="bg-white w-full py-10 px-20 overflow-y-auto h-screen max-w-[1600px] mx-auto">
      <div className="flex w-full min-h-10 gap-10">
        <div className="flex w-1/2">
          <UserProfile userInfo={userInfo} />
        </div>
        <div className=" w-1/2 flex flex-row items-center min-h-10 gap-10">
          <CircularProgress
            value={userInfo ? userInfo.attendanceRate : 100}
            color="success"
            aria-label="출석률"
            showValueLabel={true}
            size="lg"
            classNames={{
              svg: "w-36 h-36 drop-shadow-md",
              value: "text-2xl font-semibold",
            }}
          />
          <div className="bg-green-100 rounded-lg p-4 flex flex-col w-2/3">
            <div className="flex flex-row w-full justify-between">
              <p className="text-sm">총 수업일수: </p>
              <p className="text-sm text-right">
                {userInfo ? userInfo.attend + userInfo.lateOrLeaveFast + userInfo.absent : 0}일
              </p>
            </div>
            <div className="flex flex-row w-full justify-between">
              <div className="flex flex-row items-center gap-1">
                <div className="w-2 h-2 bg-green-500 rounded-full"> </div>
                <p className="text-sm"> 정상 출석:</p>
              </div>
              <p className="text-sm"> {userInfo ? userInfo.attend : 0}일</p>
            </div>
            <div className="flex flex-row w-full justify-between">
              <div className="flex flex-row items-center gap-1">
                <div className="w-2 h-2 bg-yellow-400 rounded-full"> </div>
                <p className="text-sm"> 지각/조퇴:</p>
              </div>
              <p className="text-sm">{userInfo ? userInfo.lateOrLeaveFast : "0"}일</p>
            </div>
            <div className="flex flex-row w-full justify-between">
              <div className="flex flex-row items-center gap-1">
                <div className="w-2 h-2 bg-red-500 rounded-full"> </div>
                <p className="text-sm"> 결석:</p>
              </div>
              <p className="text-sm"> {userInfo ? userInfo.absent : "0"}일</p>
            </div>
          </div>
          <div className="flex flex-col justify-around items-center min-h-10">
            <div className="flex min-h-8 flex-col items-end w-full">
              <Button onPress={onOpen} color="secondary" className=" rounded-lg  mb-2">
                프로필 수정
              </Button>
              <Button onClick={goScheduleDetail} color="primary" className="rounded-lg ">
                내 시간표 보기
              </Button>
            </div>
            <p className="text-lg whitespace-nowrap">
              담임 선생님:
              <span className="mx-2 my-3 font-bold">{userInfo ? userInfo.classRoomTeacher : ""}</span>
            </p>
          </div>
        </div>
      </div>
      <div className="mt-3">
        <WeekGoalCard thisWeekGoalContent={userInfo ? userInfo.thisWeekGoalContent : ""} />
      </div>
      <div className="mt-4 grid grid-cols-2 gap-4">
        <div className="bg-green-100 rounded-lg p-4">
          <h3 className="text-lg font-semibold mb-2 text-green-900">나의 학교 생활</h3>
          <div className="grid grid-cols-2 gap-4">
            <div className="flex flex-col items-center bg-green-400 rounded-xl p-2">
              <span className="text-sm mt-1 text-green-50">총 학습시간</span>
              <span className="font-semibold text-white">{userInfo?.lectureCnt}</span>
            </div>
            <div className="flex flex-col items-center bg-green-400 rounded-xl p-2">
              <span className="text-sm mt-1 text-green-50">학습한 과목 수</span>
              <span className="font-semibold text-white">{userInfo?.totalLectureTime}</span>
            </div>
          </div>
        </div>
        <div className="bg-green-100 rounded-lg p-4">
          <h3 className="text-lg font-semibold mb-2 text-green-900">가장 좋아하는 과목!</h3>
          <ul className="flex flex-col space-y-1">
            <li className="font-semibold text-large">1위 {userInfo ? userInfo.favoriteClassFirst : ""}</li>
            <li className="font-semibold text-large">2위 {userInfo ? userInfo.favoriteClassSecond : ""}</li>
            <li className="font-semibold text-large">3위 {userInfo ? userInfo.favoriteClassThird : ""}</li>
          </ul>
        </div>
      </div>
      <div className="mt-4  gap-4 w-full">
        <div className="bg-green-100 rounded-lg p-4 w-full">
          <h3 className="text-lg font-semibold text-green-900 mb-3"> 뱃지</h3>

          <div className="flex gap-5">
            {userInfo == null
              ? null
              : userInfo.myBadges.map((badge) => (
                  <Image key={badge.badgeId} width={100} height={100} alt="badge" src={badge.badgeUrl} />
                ))}
          </div>
        </div>
      </div>
      <div className="mt-4 gap-4 w-full">
        <div className="bg-green-100 rounded-lg p-4 w-full">
          <h3 className="text-lg font-semibold text-green-900 mb-3">내가 작성한 게시글</h3>
          <ul>
            {userInfo?.myFiveArticles.map((article) => (
              <li key={article.articleId} className=" cursor-pointer text-lg font-semibold ">
                <a className="hover:text-green-500 hover:font-bold" onClick={() => goArticleDetail(article.articleId)}>
                  {article.title}
                </a>
              </li>
            ))}
          </ul>
        </div>
      </div>
      <EditUserInfoModal
        userInfo={userInfo}
        isOpen={isOpen}
        onOpenChange={onOpenChange}
        onSubmitEditUserInfo={onSubmitEditUserInfo}
        isLoading={isLoading}
      />
    </div>
  );
};

export default Mypage;
