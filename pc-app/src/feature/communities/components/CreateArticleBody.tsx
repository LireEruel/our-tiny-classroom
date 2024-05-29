import { Button, Select, SelectItem, Input } from "@nextui-org/react";
import { useState, useRef, useMemo } from "react";
import ReactQuill from "react-quill";
import { API_URL } from "@/config";
import { useNavigate } from "react-router-dom";
import { createBoard } from "../api/createBoard";
import { fileAxios } from "@/lib/fileAxios";

// todo : 카테고리 결정될 경우 아래 데이터 값들 변경해줘야함
const CATEGORYLISTS = [
  {
    category: "공지사항",
    value: "notice",
  },
  {
    category: "자유 게시판",
    value: "free",
  },
  {
    category: "고민 나눔",
    value: "counseling",
  },
  {
    category: "취미 공유",
    value: "hobby",
  },
];

function CreateArticleBody() {
  const navigator = useNavigate();
  const quillRef = useRef<ReactQuill | null>(null);
  const [articleCategory, setArticleCategory] = useState("");
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const selectCategory = (e: React.ChangeEvent<HTMLSelectElement>): void => {
    setArticleCategory(e.target.value);
  };

  const BoardCreate = async () => {
    try {
      await createBoard(title, content, articleCategory);
      navigator("/communities");
    } catch (error) {
      console.error(error);
    }
  };

  // 이 부분 로직 완전히 수정해야함 => 우리 코드에 맞게
  const imageHandler = () => {
    // 1. 이미지를 저장할 input type=file DOM을 만든다.
    const input = document.createElement("input");
    // 속성 써주기
    input.setAttribute("type", "file");
    input.setAttribute("accept", "image/*");
    input.click(); // 에디터 이미지버튼을 클릭하면 이 input이 클릭된다.
    // input이 클릭되면 파일 선택창이 나타난다.

    // input에 변화가 생긴다면 = 이미지를 선택
    input.addEventListener("change", async () => {
      const file = input.files?.[0];
      // multer에 맞는 형식으로 데이터 만들어준다.
      if (file) {
        const formData = new FormData();
        formData.append("image", file); // formData는 키-밸류 구조
        // 백엔드 multer라우터에 이미지를 보낸다.
        try {
          const result = await fileAxios.post(
            // todo : 임시로 영빈이랑 테스트 하기 위해 넣어둔 API 추후에 수정해야함
            `${API_URL}media/images`,
            formData
          );
          const IMG_URL = result.data[0];
          // 이 URL을 img 태그의 src에 넣은 요소를 현재 에디터의 커서에 넣어주면 에디터 내에서 이미지가 나타난다
          // src가 base64가 아닌 짧은 URL이기 때문에 데이터베이스에 에디터의 전체 글 내용을 저장할 수있게된다
          // 이미지는 꼭 로컬 백엔드 uploads 폴더가 아닌 다른 곳에 저장해 URL로 사용하면된다.

          // 이미지 태그를 에디터에 써주기 - 여러 방법이 있다.
          const editor = quillRef.current?.getEditor(); // 에디터 객체 가져오기
          // 1. 에디터 root의 innerHTML을 수정해주기
          // editor의 root는 에디터 컨텐츠들이 담겨있다. 거기에 img태그를 추가해준다.
          // 이미지를 업로드하면 -> 멀터에서 이미지 경로 URL을 받아와 -> 이미지 요소로 만들어 에디터 안에 넣어준다.
          // editor.root.innerHTML =
          //   editor.root.innerHTML + `<img src=${IMG_URL} /><br/>`; // 현재 있는 내용들 뒤에 써줘야한다.

          // 2. 현재 에디터 커서 위치값을 가져온다
          // const range = editor?.getSelection();
          // 가져온 위치에 이미지를 삽입한다

          editor?.insertEmbed(0, "image", IMG_URL);
        } catch (error) {
          console.error(error);
        }
      }
    });
  };

  const modules = useMemo(() => {
    return {
      toolbar: {
        container: [
          ["link", "image", "video"],
          [{ header: [1, 2, 3, false] }],
          ["bold", "italic", "underline", "strike"],
          ["blockquote"],
          [{ list: "ordered" }, { list: "bullet" }],
          [{ color: [] }, { background: [] }],
          [{ align: [] }],
        ],
        handlers: {
          // 이미지 처리는 우리가 직접 imageHandler라는 함수로 처리할 것이다.
          image: imageHandler,
        },
      },
    };
  }, []);

  return (
    <section className="w-10/12 h-full border-l-3 realtive">
      {/* todo : 현 사용단게에서 편의성을 위해서 넣어둔 부분이라 추후에 삭제 예정 */}
      <Button
        className="fixed bottom-5 right-5 text-white text-xl rounded-full  bg-lime-500 shadow"
        onClick={() => {
          navigator("/communities/");
        }}
        size="lg"
      >
        ←
      </Button>
      {/* todo : inline style height 속성 tailwind에서 적당한 height 로 변경하기 */}
      <div className="w-full bg-lime-500 flex items-center justify-center h-20"></div>
      <div className="w-full bg-white" style={{ height: "90%" }}>
        <div className="flex w-full p-5 items-center" style={{ height: "8%" }}>
          <p className="text-2xl w-2/12 f">고민 나눔</p>
          <p className="w-10/12">가슴속에 쌓아둔 이야기를 털어보세요</p>
        </div>
        <hr className="border-b-2" />
        <div className="w-full flex justify-center items-center" style={{ height: "90%" }}>
          <div className="w-5/6 flex flex-col  py-3 items-center" style={{ height: "90%" }}>
            <div className="w-full" style={{ height: "15%" }}>
              <form className="flex justify-between items-center">
                <Select
                  className="bg-white w-1/6 rounded-xl"
                  size="sm"
                  aria-label="어떤 카테고리에 글을 작성할지 선택해주세요. "
                  defaultSelectedKeys={[CATEGORYLISTS[0].value]}
                  onChange={(e) => selectCategory(e)}
                >
                  {CATEGORYLISTS.map(
                    (
                      item: { category: string; value: string }
                      // index: number
                    ) => (
                      <SelectItem key={item.value} value={item.value}>
                        {item.category}
                      </SelectItem>
                    )
                  )}
                </Select>
                <Input
                  className="bg-white w-4/6"
                  type="text"
                  size="sm"
                  placeholder="제목을 입력하세요."
                  onChange={(e) => setTitle(e.target.value)}
                />

                <Button className="w-1/12 text-white text-xl rounded-xl  bg-lime-500 shadow" size="lg" onClick={BoardCreate}>
                  작성
                </Button>
              </form>
            </div>
            <ReactQuill
              ref={quillRef}
              modules={modules}
              style={{ width: "100%", height: "70%" }}
              onChange={(e) => setContent(e)}
            />
          </div>
        </div>
      </div>
    </section>
  );
}

export default CreateArticleBody;
