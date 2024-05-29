import { commonAxios } from "@/lib/commonAxios";
import { BaseResponse } from "@/types/BaseResponse";

export interface ChatRoomMember {
  memberId: string;
  name: string;
  birthday: string;
  role: string;
  profileUrl: string;
}

interface RequestGetClassroomMembersProps extends BaseResponse {
  data: ChatRoomMember[];
}

export const requestGetClassroomMembers = (): Promise<RequestGetClassroomMembersProps> => {
  return commonAxios.get(`/members/myClass`);
};
