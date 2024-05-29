import { Button, Input } from "@nextui-org/react";
import { ActualFileObject, FilePondFile } from "filepond";
import { useCallback, useState } from "react";
import { FilePond } from "react-filepond";
import Swal from "sweetalert2";

interface MentorDocumentFormProps {
  onSubmit: (formData: FormData) => void;
  goBefore: (current: number) => void;
  loading: boolean;
}

function MentorDocumentForm({
  onSubmit,
  goBefore,
  loading,
}: MentorDocumentFormProps) {
  const [inputBelong, setInputBelong] = useState("");
  const [files, setFiles] = useState<ActualFileObject[]>([]);

  const onChangeInputBelong = useCallback(
    (e: React.ChangeEvent<HTMLInputElement>) => {
      setInputBelong(e.target.value);
    },
    [setInputBelong]
  );

  const onUpdateFiles = (newFiles: FilePondFile[]) => {
    setFiles(newFiles.map((file) => file.file));
  };
  const onClickBeforeBtn = useCallback(() => {
    goBefore(1);
  }, [goBefore]);

  const onSubmitForm = () => {
    if (inputBelong.trim().length && files.length) {
      const formData = new FormData();
      const request = JSON.stringify({ belong: inputBelong });
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

  return (
    <form className="flex flex-col gap-10 border-1 border-lime-400 p-10 rounded-lg 2xl:w-1/3 xl:w-1/2 lg:w-2/3 w-full">
      <Input
        type="text"
        label="소속"
        placeholder="소속을 입력해주세요."
        isClearable={true}
        className=" justify-between "
        labelPlacement="outside"
        required={true}
        value={inputBelong}
        onChange={onChangeInputBelong}
      />
      <p>소속을 확인할 수 있는 서류를 제출해주세요.</p>
      <FilePond
        files={files}
        maxFiles={1}
        name="files"
        onupdatefiles={onUpdateFiles}
        labelIdle=" 끌어놓거나, 클릭해서 찾아보세요."
      />{" "}
      <Button onClick={onClickBeforeBtn} className="text-xl">
        이전
      </Button>
      <Button
        isLoading={loading}
        onClick={onSubmitForm}
        type="submit"
        className="bg-lime-500 text-white text-xl"
      >
        다음
      </Button>
    </form>
  );
}

export default MentorDocumentForm;
