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
 * Mentor ROLE 업데이트 요청 엔티티.
 */
@Getter
@Table(name = "mentor_role_update_request")
@Entity
public class MentorRoleUpdateRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Member member;

    // Mentor 의 소속
    private String belong;

    @Convert(converter = StringListConverter.class)
    private List<String> originalFileNames;

    @Convert(converter = StringListConverter.class)
    private List<String> belongDocumentPaths; // 소속 증명 이미지 파일을 저장하는 리스트

    private MentorRoleUpdateRequest(Member member, String belong, List<String> originalFileNames, List<String> belongDocumentPaths) {
        this.member = member;
        this.belong = belong;
        this.originalFileNames = originalFileNames;
        this.belongDocumentPaths = belongDocumentPaths;
    }

    protected MentorRoleUpdateRequest() {
    }

    /**
     * 팩토리 메서드.
     *
     * @param member 멤버
     * @param belong 소속
     * @param originalFileNames 원본파일명
     * @param belongDocumentPaths 저장파일명
     * @return MentorRoleUpdateRequest
     */
    public static MentorRoleUpdateRequest of(Member member, String belong, List<String> originalFileNames, List<String> belongDocumentPaths) {
        return new MentorRoleUpdateRequest(member, belong, originalFileNames, belongDocumentPaths);
    }
}
