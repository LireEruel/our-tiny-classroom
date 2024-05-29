import { Button, Input, Select, SelectItem } from "@nextui-org/react";
import { ActualFileObject, FilePondFile } from "filepond";
import { useCallback, useState } from "react";
import { FilePond } from "react-filepond";
import Swal from "sweetalert2";

interface StudentDocumentFormProps {
  onSubmit: (formData: FormData) => void;
  goBefore: (current: number) => void;
  loading: boolean;
}

const SchoolTypes = [
  {
    value: 0,
    label: "초등학교",
  },
  {
    value: 1,
    label: "중학교",
  },
  {
    value: 2,
    label: "고등학교",
  },
  {
    value: 3,
    label: "기타",
  },
];
function StudentDocumentForm({
  onSubmit,
  goBefore,
  loading,
}: StudentDocumentFormProps) {
  const [inputQuitReason, setInputQuitReason] = useState("");
  const [inputBeforeSchool, setInputBeforeSchool] = useState("");
  const [files, setFiles] = useState<ActualFileObject[]>([]);
  const [selectedSchoolType, setSelectedSchoolType] = useState("");

  const onChangeInputQuitReason = useCallback(
    (e: React.ChangeEvent<HTMLInputElement>) => {
      setInputQuitReason(e.target.value);
    },
    [setInputQuitReason]
  );

  const onChangeInputBeforeSchool = useCallback(
    (e: React.ChangeEvent<HTMLInputElement>) => {
      setInputBeforeSchool(e.target.value);
    },
    [setInputBeforeSchool]
  );

  const onUpdateFiles = (newFiles: FilePondFile[]) => {
    setFiles(newFiles.map((file) => file.file));
  };

  const onClickBeforeBtn = useCallback(() => {
    goBefore(1);
  }, [goBefore]);

  const onSubmitForm = () => {
    if (
      inputQuitReason.trim().length > 0 &&
      inputBeforeSchool.trim().length > 0 &&
      files.length &&
      files[0] &&
      selectedSchoolType.length
    ) {
      const formData = new FormData();
      const request = JSON.stringify({
        quitReason: inputQuitReason,
        beforeSchool: inputBeforeSchool,
        beforeSchoolType: selectedSchoolType,
      });
      formData.append(
        "request",
        new Blob([request], { type: "application/json" })
      );
      formData.append("image", files[0]);
      onSubmit(formData);
    } else {
      Swal.fire("실패", "양식에 값을 모두 채워주세요", "warning");
    }
  };

  const handleBeforeSchoolTypeSelectionChange = (
    e: React.ChangeEvent<HTMLSelectElement>
  ) => {
    setSelectedSchoolType(e.target.value);
  };

  return (
    <form className="flex flex-col gap-5 border-1 border-lime-400 p-5 rounded-lg 2xl:w-1/3 xl:w-1/2 lg:w-2/3 w-full">
      <Select
        label="기존 학교 종류를 선택해주세요."
        value={selectedSchoolType}
        onChange={handleBeforeSchoolTypeSelectionChange}
        isRequired={true}
        size="sm"
      >
        {SchoolTypes.map((school) => (
          <SelectItem key={school.value} value={school.value}>
            {school.label}
          </SelectItem>
        ))}
      </Select>
      <Input
        type="text"
        label="자퇴 사유"
        placeholder="자퇴 사유를 입력해주세요."
        isClearable={true}
        required={true}
        value={inputQuitReason}
        onChange={onChangeInputQuitReason}
        size="sm"
      />
      <Input
        type="text"
        label="기존 소속 학교"
        placeholder="기존 소속 학교를 입력해주세요."
        isClearable={true}
        required={true}
        value={inputBeforeSchool}
        onChange={onChangeInputBeforeSchool}
        size="sm"
      />
      <div className="flex flex-col gap-2">
        <label htmlFor="files"> 자퇴 확인서를 제출해주세요.</label>
        <FilePond
          files={files}
          name="files"
          onupdatefiles={onUpdateFiles}
          labelIdle=" 끌어놓거나, 클릭해서 찾아보세요."
        />
      </div>

      <Button onClick={onClickBeforeBtn} className="text-xl">
        이전
      </Button>
      <Button
        isLoading={loading}
        onClick={onSubmitForm}
        className="bg-lime-500 text-white text-xl"
      >
        다음
      </Button>
    </form>
  );
}

export default StudentDocumentForm;
