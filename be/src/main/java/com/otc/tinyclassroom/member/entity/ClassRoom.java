package com.otc.tinyclassroom.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.Getter;

/**
 * 반 Entity.
 */
@Getter
@Entity
@Table(name = "classroom")
public class ClassRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int year; // 해당 년도
    @Column(nullable = false)
    private int grade; // 학년
    @Column(nullable = false)
    private int number; // 반
    @Column
    private String roomUrl;

    @OneToMany(mappedBy = "classRoom")
    private final List<MemberClassRoom> members = new ArrayList<>();

    protected ClassRoom() {
    }

    /**
     * 교실 생성자.
     */
    public ClassRoom(int year, int grade, int number, String roomUrl) {
        this.year = year;
        this.grade = grade;
        this.number = number;
        this.roomUrl = roomUrl;
    }

    public static ClassRoom of(int year, int grade, int number) {
        return new ClassRoom(year, grade, number, null);
    }

    public static ClassRoom of(int year, int grade, int number, String roomUrl) {
        return new ClassRoom(year, grade, number, roomUrl);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClassRoom classRoom)) {
            return false;
        }
        return id != null && id.equals(classRoom.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
