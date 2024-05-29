import {
  Button,
  Image,
  Input,
  Modal,
  ModalBody,
  ModalContent,
  ModalFooter,
  ModalHeader,
  Switch,
  Table,
  TableBody,
  TableCell,
  TableColumn,
  TableHeader,
  TableRow,
  Textarea,
  useDisclosure,
} from "@nextui-org/react";
import { useEffect, useState } from "react";
import {
  requestGetMentorCertDetail,
  requestGetMentorCertList,
  requestGetStudentCertDetail,
  requestGetStudentCertList,
  requestMentorCertChange,
  requestStudentCertChange,
} from "../api/users";
import { MentorCertDetail, StudentCertDetail } from "../types";

type StudentDetailProps = {
  isOpen: boolean;
  onOpenChange: () => void;
  articleId: number | undefined;
  onClose: () => void;
  loadData: () => void;
};
const StudentDetailModal = ({
  isOpen,
  onOpenChange,
  articleId,
  onClose,
  loadData,
}: StudentDetailProps) => {
  const [student, setStudent] = useState<StudentCertDetail>();
  const [grade, setGrade] = useState<string>("");
  useEffect(() => {
    if (isOpen && articleId) {
      const getDetail = async () => {
        const res = await requestGetStudentCertDetail(articleId);
        setStudent(res.data);
      };
      getDetail();
    }
  }, [isOpen, articleId]);

  const sendResult = async (isApprove: boolean) => {
    if (articleId) {
      try {
        await requestStudentCertChange(articleId, isApprove, +grade);
      } catch (error) {
        console.error(error);
      } finally {
        loadData();
        onClose();
      }
    }
  };

  const schoolTypeMap = () => {
    if (student) {
      switch (student.beforeSchoolType) {
        case 0:
          return "초등학교";
        case 1:
          return "중학교";
        case 2:
          return "고등학교";
        case 3:
          return "기타";
      }
    }
  };

  return (
    <Modal isOpen={isOpen} onOpenChange={onOpenChange}>
      <ModalContent>
        <ModalHeader className="flex flex-col gap-1">
          학생 신청 상세 조회
        </ModalHeader>
        <ModalBody>
          <div className="h-max-[70vh] overflow-y-auto flex flex-col gap-5">
            <Input label="이름" value={student?.memberName} size="sm" />
            <Input
              label="생년월일"
              value={student?.birthday ?? ""}
              readOnly></Input>
            <Input label="이전 학교 구분" value={schoolTypeMap()} readOnly />
            <Textarea label="자퇴 사유" value={student?.quitReason} readOnly />
            <Input label="이전 학교" value={student?.beforeSchool} />
            <Input
              label="임시 학년"
              value={grade}
              onChange={(e) => setGrade(e.target.value)}
            />
            <Image
              alt="자퇴 사유 파일"
              src={student?.quitConfirmationPaths[0] ?? ""}
            />
          </div>
        </ModalBody>
        <ModalFooter>
          <Button color="danger" onPress={() => sendResult(false)}>
            거절
          </Button>
          <Button color="primary" onPress={() => sendResult(true)}>
            승인
          </Button>
        </ModalFooter>
      </ModalContent>
    </Modal>
  );
};

const MentorDetailModal = ({
  isOpen,
  onOpenChange,
  articleId,
  onClose,
}: StudentDetailProps) => {
  const [mentor, setMentor] = useState<MentorCertDetail>();
  useEffect(() => {
    if (isOpen && articleId) {
      const getDetail = async () => {
        const res = await requestGetMentorCertDetail(articleId);
        setMentor(res.data);
      };
      getDetail();
    }
  }, [isOpen, articleId]);

  const sendResult = async (isApprove: boolean) => {
    if (articleId) {
      try {
        await requestMentorCertChange(articleId, isApprove);
      } catch (error) {
        console.error(error);
      } finally {
        onClose();
      }
    }
  };

  return (
    <Modal isOpen={isOpen} onOpenChange={onOpenChange}>
      <ModalContent>
        <ModalHeader className="flex flex-col gap-1">
          학생 신청 상세 조회
        </ModalHeader>
        <ModalBody>
          <div className="h-max-[70vh] overflow-y-auto flex flex-col gap-5">
            <Input label="이름" value={mentor?.memberName} size="sm" />
            <Input
              label="생년월일"
              value={mentor?.birthday ?? ""}
              readOnly></Input>
            <Textarea label="소속" value={mentor?.belong} readOnly />
            <Image
              alt="소속 관련 서류"
              src={mentor?.belongDocumentPaths[0] ?? ""}
            />
          </div>
        </ModalBody>
        <ModalFooter>
          <Button color="danger" onPress={() => sendResult(false)}>
            거절
          </Button>
          <Button color="primary" onPress={() => sendResult(true)}>
            승인
          </Button>
        </ModalFooter>
      </ModalContent>
    </Modal>
  );
};

const AdminAccept = () => {
  const [isStudent, setIsStudent] = useState(true);
  const [certDataList, setCertDataList] = useState(
    Array<{ articleId: number; memberName: string; birthday: string }>
  );
  const [articleId, setArticleId] = useState(-1);
  const { isOpen, onOpen, onOpenChange, onClose } = useDisclosure();
  const getStudentCertList = async () => {
    const res = await requestGetStudentCertList();
    setCertDataList(res.data);
  };

  const getMentorCertList = async () => {
    const res = await requestGetMentorCertList();
    setCertDataList(res.data);
  };

  const onOpenModal = (id: number) => {
    setArticleId(id);
    onOpen();
  };

  const loadData = () => {
    if (isStudent) {
      getStudentCertList();
    } else {
      getMentorCertList();
    }
  };

  useEffect(() => {
    loadData();
  }, [isStudent]);

  return (
    <section className="w-10/12 flex flex-col mx-auto py-10">
      <p className="my-5 text-2xl">등업 요청 목록</p>

      <Switch isSelected={isStudent} onValueChange={setIsStudent}>
        {isStudent ? "학생" : "멘토"}
      </Switch>

      <Table
        selectionMode="single"
        aria-label="등업 요청 관리 게시판"
        className="mt-10 max-h-[60vh]"
        isHeaderSticky>
        <TableHeader>
          {/* <TableColumn key="userId">계정</TableColumn> */}
          <TableColumn key="articleId">계정</TableColumn>
          <TableColumn key="memberName">이름</TableColumn>
          <TableColumn key="birthday">학년</TableColumn>
        </TableHeader>
        <TableBody items={certDataList}>
          {(article) => (
            <TableRow
              onClick={() => onOpenModal(article.articleId)}
              key={article.articleId}>
              <TableCell>{article.articleId}</TableCell>
              <TableCell>{article.memberName}</TableCell>
              <TableCell>{article.birthday}</TableCell>
            </TableRow>
          )}
        </TableBody>
      </Table>
      <StudentDetailModal
        isOpen={isStudent && isOpen}
        onOpenChange={onOpenChange}
        loadData={loadData}
        onClose={onClose}
        articleId={articleId}
      />
      <MentorDetailModal
        isOpen={!isStudent && isOpen}
        onOpenChange={onOpenChange}
        loadData={loadData}
        onClose={onClose}
        articleId={articleId}
      />
    </section>
  );
};

export default AdminAccept;
