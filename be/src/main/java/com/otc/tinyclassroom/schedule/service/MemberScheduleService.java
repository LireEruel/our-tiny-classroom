package com.otc.tinyclassroom.schedule.service;

import com.otc.tinyclassroom.lecture.entity.type.LectureCategoryType;
import com.otc.tinyclassroom.schedule.repository.MemberScheduleRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Member와 Schedul의 매핑테이블을 다루는 서비스.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class MemberScheduleService {

    private final MemberScheduleRepository memberScheduleRepository;

    public List<LectureCategoryType> findDistinctLectureCategoryTypeByMemberIdOrderByLectureCategoryTypeDesc(Long memberId) {
        return memberScheduleRepository.findDistinctLectureCategoryTypeByMemberIdOrderByLectureCategoryTypeDesc(memberId);
    }

    public Long countByMemberId(Long memberId){
        return memberScheduleRepository.countByMemberId(memberId);
    }
}
