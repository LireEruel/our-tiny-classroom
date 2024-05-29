package com.otc.tinyclassroom.lecture.entity.type;

import lombok.Getter;

/**
 * 강의 승인 여부를 위한 enum class 정의.
 */
@Getter
public enum LectureApprovalStatusType {
    APPROVED, WAITING, REJECTED
}
