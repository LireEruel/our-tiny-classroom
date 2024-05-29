package com.otc.tinyclassroom.member.repository;

import com.otc.tinyclassroom.member.entity.ClassRoom;
import com.otc.tinyclassroom.member.entity.QClassRoom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * custom Repository 구현.
 */
@Repository
@AllArgsConstructor
@SuppressWarnings("checkstyle:LocalVariableName")
public class ClassRoomRepositoryCustomImpl implements ClassRoomRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ClassRoom> findClassRoomByGradeAndYearWithNonZeroNumber(int grade, int year) {
        QClassRoom classRoom = QClassRoom.classRoom;
        return queryFactory.selectFrom(classRoom)
            .where(classRoom.grade.eq(grade)
                    .and(classRoom.year.eq(year))
                    .and(classRoom.number.gt(0)))
            .fetch();
    }

    @Override
    public Optional<ClassRoom> findTempClassRoomByGrade(int grade) {
        QClassRoom classRoom = QClassRoom.classRoom;
        ClassRoom result = queryFactory.selectFrom(classRoom)
            .where(classRoom.grade.eq(grade)
                .and(classRoom.year.eq(LocalDateTime.now().getYear()))
                .and(classRoom.number.eq(0)))
            .fetchOne();
        return Optional.ofNullable(result);
    }
}
