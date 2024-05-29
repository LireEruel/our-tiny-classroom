import {
  MainContainer,
  ChatContainer,
  MessageList,
  MessageInput,
  ConversationList,
  Conversation,
  ConversationHeader,
  Message,
  Sidebar,
} from "@chatscope/chat-ui-kit-react";
import "@chatscope/chat-ui-kit-styles/dist/default/styles.min.css";
import "../assets/customChatStyles.scss";
import { useRecoilState, useRecoilValue } from "recoil";
import { chatListAtom, currentRoomAtom, currentRoomIdAtom, roomList } from "@/atoms/chatRoom";
import { ChatRoom } from "../types";
import { useMemo, useState, useEffect } from "react";
import { useStompClient } from "react-stomp-hooks";
import { userState } from "@/atoms/user";
import { createRoom, getChatList } from "../api/chatRoom";
import {
  Button,
  Listbox,
  ListboxItem,
  Modal,
  ModalBody,
  ModalContent,
  ModalFooter,
  ModalHeader,
  Tooltip,
  User,
  useDisclosure,
} from "@nextui-org/react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCommentMedical } from "@fortawesome/free-solid-svg-icons";
import Swal from "sweetalert2";
import { ChatRoomMember, requestGetClassroomMembers } from "@/feature/users/api/members";

type CreateChatRoomModalProps = {
  isOpen: boolean;
  onOpenChange: (isOpen: boolean) => void;
  onSubmit: (memberId: string) => void;
  isLoading: boolean;
};

const CreateChatRoomModal = ({ isOpen, onOpenChange, onSubmit, isLoading }: CreateChatRoomModalProps) => {
  const [selectedKeys, setSelectedKeys] = useState(new Set(["text"]));
  const selectedValue = useMemo(() => Array.from(selectedKeys).join(", "), [selectedKeys]);
  const [friendList, setFriendList] = useState<Array<ChatRoomMember>>([]);
  const userInfo = useRecoilValue(userState);
  const onSelectItem = (key: string) => {
    setSelectedKeys(new Set([key]));
  };

  useEffect(() => {
    const getUserList = async () => {
      const res = await requestGetClassroomMembers();
      setFriendList(res.data.filter((member) => member.memberId !== userInfo.memberId));
    };
    getUserList();
  }, []);

  return (
    <Modal isOpen={isOpen} onOpenChange={onOpenChange}>
      <ModalContent>
        {(onClose) => (
          <>
            <ModalHeader className="flex flex-col gap-1">대화상대 선택</ModalHeader>
            <ModalBody>
              <div className=" h-96 overflow-auto">
                <Listbox
                  aria-label="Single selection example"
                  variant="flat"
                  disallowEmptySelection
                  selectionMode="single"
                  selectedKeys={selectedKeys}
                >
                  {friendList.map((friend) => (
                    <ListboxItem key={friend.memberId} onPress={() => onSelectItem(friend.memberId)}>
                      <User name={friend.name} avatarProps={{ src: friend.profileUrl }} />
                    </ListboxItem>
                  ))}
                </Listbox>
              </div>
            </ModalBody>
            <ModalFooter>
              <Button color="danger" variant="light" onPress={onClose}>
                취소
              </Button>
              <Button isLoading={isLoading} color="success" onPress={() => onSubmit(selectedValue)}>
                생성
              </Button>
            </ModalFooter>
          </>
        )}
      </ModalContent>
    </Modal>
  );
};

const ChatHome = () => {
  const [chatRoomList, setChatRoomList] = useRecoilState(roomList);
  const { isOpen, onOpen, onOpenChange, onClose } = useDisclosure();
  const userInfo = useRecoilValue(userState);
  const stompClient = useStompClient();
  const makeRoomName = (room: ChatRoom | undefined) => {
    if (room) {
      return room.roomMemberList.map((member) => member.name).join(", ");
    } else {
      return "";
    }
  };
  const [currentRoom, setCurrentRoom] = useRecoilState(currentRoomAtom);
  const [currentRoomId, setCurrentRoomId] = useRecoilState(currentRoomIdAtom);
  const [chatList, setChatList] = useRecoilState(chatListAtom);
  const [isCreateChatRoomLoading, setIsCreateChatRoomLoading] = useState(false);

  const onSendMessage = (innerHtml: string) => {
    if (stompClient) {
      stompClient.publish({
        destination: "/ws/pub/message",
        body: JSON.stringify({
          senderId: userInfo.memberId,
          roomId: currentRoom?.roomId,
          message: innerHtml,
        }),
      });
    }
  };

  const onCreateChatRoom = async (memberId: string) => {
    try {
      setIsCreateChatRoomLoading(true);
      const res = await createRoom(memberId);
      setCurrentRoomId(res.data.roomId);
      setChatRoomList((_roomList) => [res.data, ..._roomList]);
      onClose();
    } catch (e) {
      Swal.fire("채팅방 생성 실패", "채팅방 생성이 실패하였습니다. 다시 시도해주세요.", "error");
    } finally {
      setIsCreateChatRoomLoading(false);
    }
  };

  useEffect(() => {
    if (chatRoomList.length) {
      if (!currentRoomId) {
        setCurrentRoom(chatRoomList[0]);
        setCurrentRoomId(chatRoomList[0].roomId);
      }
    }
  }, [chatRoomList]);

  useEffect(() => {
    if (chatRoomList.length && currentRoomId) {
      setCurrentRoom(chatRoomList.find((_room) => _room.roomId === currentRoomId));
    }
  }, [currentRoomId]);

  useEffect(() => {
    return () => {
      setCurrentRoom(undefined);
      setCurrentRoomId(undefined);
    };
  }, []);
  useEffect(() => {
    const fetchChatList = async () => {
      try {
        if (currentRoomId) {
          const res = await getChatList(currentRoomId);
          setChatList(res.data);
        }
      } catch (e) {
        console.error(e);
      }
    };
    fetchChatList();
  }, [currentRoomId, setChatList]);

  return (
    <div className="relative w-full h-full">
      <MainContainer className="h-screen">
        <Sidebar position="left">
          <div className="flex justify-between items-center p-2 w-full">
            <h2 className="text-lg font-semibold ">채팅방 목록 ({chatRoomList ? chatRoomList.length : 0})</h2>
            <Tooltip color="success" content="채팅방 생성">
              <Button isIconOnly onPress={onOpen} className=" bg-lime-300">
                <FontAwesomeIcon size="2xl" icon={faCommentMedical} />
              </Button>
            </Tooltip>
          </div>
          {chatRoomList.length ? (
           <ConversationList className="h">
            {
              chatRoomList.map((room) => (
                <Conversation
                  key={room.roomId}
                  name={makeRoomName(room)}
                  active={currentRoom && currentRoom.roomId === room.roomId ? true : false}
                  onClick={() => setCurrentRoomId(room.roomId)}
                />
              ))}
          </ConversationList>
           ) : (
          <p className=" text-center mt-40 font-semibold text-green-500">채팅방을 추가해주세요.</p>
            )}
        </Sidebar>
        {currentRoom ? (
          <ChatContainer className=" h-[99vh]">
            <ConversationHeader>
              <ConversationHeader.Content userName={makeRoomName(currentRoom)} />
            </ConversationHeader>

            <MessageList>
              {chatList?.map((chat) => (
                <Message
                  model={{
                    message: chat.message,
                    sentTime: "15 mins ago",
                    sender: chat.senderName,
                    direction: chat.senderId === userInfo.memberId ? "outgoing" : "incoming",
                    position: "single",
                  }}
                  key={chat.chatId}
                ></Message>
              ))}
            </MessageList>
            <MessageInput placeholder="Type message here" attachButton={false} onSend={onSendMessage} />
          </ChatContainer>
        ) : null}
      </MainContainer>
      <CreateChatRoomModal
        isOpen={isOpen}
        onOpenChange={onOpenChange}
        isLoading={isCreateChatRoomLoading}
        onSubmit={onCreateChatRoom}
      />
    </div>
  );
};
export default ChatHome;
