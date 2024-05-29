package com.otc.tinyclassroom.member.entity;

import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

/**
 * member ClassRomm 맵핑 entity.
 */
@Getter
@Entity
public class MemberClassRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    private Member member;

    @Setter
    @ManyToOne(fetch = LAZY)
    private ClassRoom classRoom;

    protected MemberClassRoom() {
    }

    private MemberClassRoom(Member member, ClassRoom classRoom) {
        this.member = member;
        this.classRoom = classRoom;
    }

    public static MemberClassRoom of(Member member, ClassRoom classRoom) {
        return new MemberClassRoom(member, classRoom);
    }
}
