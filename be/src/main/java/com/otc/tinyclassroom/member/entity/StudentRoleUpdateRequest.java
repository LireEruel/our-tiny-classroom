package com.otc.tinyclassroom.member.entity;

import com.otc.tinyclassroom.global.common.StringListConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Getter;

/**
 * Student ROLE 업데이트 요청 엔티티.
 */
@Getter
@Table(name = "student_role_update_request")
@Entity
public class StudentRoleUpdateRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Member member;

    // 학생의 이전 학교 분류
    private int beforeSchoolType;

    private String quitReason;

    // 학생의 이전 학교 이름
    private String beforeSchool;

    @Convert(converter = StringListConverter.class)
    private List<String> originalFileNames;

    @Convert(converter = StringListConverter.class)
    private List<String> quitConfirmationPaths; // 파일 경로들을 저장하는 리스트

    private StudentRoleUpdateRequest(Member member, int beforeSchoolType, String quitReason, String beforeSchool, List<String> originalFileNames, List<String>  quitConfirmationPaths) {
        this.member = member;
        this.beforeSchoolType = beforeSchoolType;
        this.quitReason = quitReason;
        this.beforeSchool = beforeSchool;
        this.originalFileNames = originalFileNames;
        this.quitConfirmationPaths = quitConfirmationPaths;
    }

    protected StudentRoleUpdateRequest() {
    }

    public static StudentRoleUpdateRequest of(Member member, int beforeSchoolType, String quitReason, String beforeSchool, List<String> originalFileNames, List<String> quitConfirmationPaths) {
        return new StudentRoleUpdateRequest(member, beforeSchoolType, quitReason, beforeSchool, originalFileNames, quitConfirmationPaths);
    }
}
