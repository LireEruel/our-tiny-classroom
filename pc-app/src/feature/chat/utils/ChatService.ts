import {
  ChatEvent,
  ChatEventHandler,
  ChatEventType,
  IChatService,
  IStorage,
  SendMessageServiceParams,
  SendTypingServiceParams,
  UpdateState,
} from "@chatscope/use-chat";

export class ChatService implements IChatService {
  storage: IStorage;
  updateState: UpdateState;
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  private eventHandlers = new Map<ChatEventType, ChatEventHandler<any, any>>();
  constructor(storage: IStorage, updateState: UpdateState) {
    this.storage = storage;
    this.updateState = updateState;
  }

  on<T extends ChatEventType, H extends ChatEvent<T>>(
    evtType: T,
    evtHandler: ChatEventHandler<T, H>
  ): void {
    this.eventHandlers.set(evtType, evtHandler);
  }

  off<T extends ChatEventType, H extends ChatEvent<T>>(
    evtType: T,
    evtHandler: ChatEventHandler<T, H>
  ): void {
    this.eventHandlers.delete(evtType);
    console.log(evtHandler);
  }

  sendMessage(params: SendMessageServiceParams): void {
    // 메시지 전송 로직 구현
    console.log("Sending message:", params);
    // 예를 들어 WebSocket을 사용하여 서버에 메시지를 전송할 수 있습니다.
  }

  sendTyping(params: SendTypingServiceParams): void {
    // 타이핑 인디케이터 전송 로직 구현
    console.log("Sending typing indicator:", params);
    // 예를 들어 WebSocket을 사용하여 서버에 타이핑 상태를 전송할 수 있습니다.
  }
}
