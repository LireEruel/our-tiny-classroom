package com.otc.tinyclassroom.lecture.repository;

import com.otc.tinyclassroom.lecture.entity.Lecture;
import com.otc.tinyclassroom.lecture.entity.type.LectureApprovalStatusType;
import com.otc.tinyclassroom.lecture.entity.type.LectureCategoryType;
import com.otc.tinyclassroom.lecture.entity.type.LectureType;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 강의 레포지토리.
 */
@Repository
public interface LectureRepository extends JpaRepository<Lecture, Long> {

    List<Lecture> findAll(Specification<Lecture> spec, Sort sort);

    default Specification<Lecture> buildSpecification(
        Long memberId,
        String title,
        String description,
        String dayOfWeek,
        String timeTable,
        LectureType lectureType,
        LectureCategoryType lectureCategoryType,
        LectureApprovalStatusType approved,
        LocalDate date
    ) {
        return (root, query, criteriaBuilder) -> {
            Specification<Lecture> spec = Specification.where(null);

            if (memberId != null) {
                spec = spec.and((root1, query1, criteriaBuilder1) ->
                    criteriaBuilder.equal(root.get("member").get("memberId"), memberId));
            }
            if (title != null && !title.isEmpty()) {
                spec = spec.and((root1, query1, criteriaBuilder1) ->
                    criteriaBuilder.like(root.get("title"), "%" + title + "%"));
            }
            if (description != null && !description.isEmpty()) {
                spec = spec.and((root1, query1, criteriaBuilder1) ->
                    criteriaBuilder.like(root.get("description"), "%" + description + "%"));
            }
            if (dayOfWeek != null && !dayOfWeek.isEmpty()) {
                spec = spec.and((root1, query1, criteriaBuilder1) ->
                    criteriaBuilder.equal(root.get("dayOfWeek"), Integer.valueOf(dayOfWeek)));
            }
            if (timeTable != null && !timeTable.isEmpty()) {
                spec = spec.and((root1, query1, criteriaBuilder1) ->
                    criteriaBuilder.equal(root.get("timeTable"), Integer.valueOf(timeTable)));
            }
            if (lectureType != null) {
                spec = spec.and((root1, query1, criteriaBuilder1) ->
                    criteriaBuilder.equal(root.get("lectureType"), lectureType));
            }
            if (lectureCategoryType != null) {
                spec = spec.and((root1, query1, criteriaBuilder1) ->
                    criteriaBuilder.equal(root.get("lectureCategoryType"), lectureCategoryType));
            }
            if (approved != null) {
                spec = spec.and((root1, query1, criteriaBuilder1) ->
                    criteriaBuilder.equal(root.get("approved"), approved));
            }
            if (date != null) {
                spec = spec.and((root1, query1, criteriaBuilder1) ->
                    criteriaBuilder.equal(root.get("date"), date));
            }
            return spec.toPredicate(root, query, criteriaBuilder);
        };
    }
}