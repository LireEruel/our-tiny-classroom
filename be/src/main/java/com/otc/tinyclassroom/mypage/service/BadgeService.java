package com.otc.tinyclassroom.mypage.service;

import com.otc.tinyclassroom.mypage.dto.request.BadgeSaveRequestDto;
import com.otc.tinyclassroom.mypage.dto.response.MemberBadgeResponseDto;
import com.otc.tinyclassroom.mypage.entity.Badge;
import com.otc.tinyclassroom.mypage.repository.BadgeRepository;
import com.otc.tinyclassroom.mypage.repository.MemberBadgeRepository;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 뱃지 관련 서비스.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class BadgeService {

    private final MemberBadgeRepository memberBadgeRepository;
    private final BadgeRepository badgeRepository;

    /**
     * 가진 뱃지를 모두 가져온다.
     */
    public List<MemberBadgeResponseDto> getOnesBadge(Long memberId) {
        return memberBadgeRepository.findByMemberId(memberId).stream()
            .map(memberBadge -> MemberBadgeResponseDto.of(memberBadge.getBadge().getId(), memberBadge.getBadge().getImageUrl()))
            .collect(Collectors.toList());
    }

    public void save(BadgeSaveRequestDto dto) {
        Badge entity = new Badge(dto.image(), new HashSet<>());
        badgeRepository.save(entity);
    }
}
