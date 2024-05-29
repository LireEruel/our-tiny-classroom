package com.otc.tinyclassroom.mypage.controller;

import com.otc.tinyclassroom.mypage.dto.request.BadgeSaveRequestDto;
import com.otc.tinyclassroom.mypage.service.BadgeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 뱃지 Controller.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/badge")
public class BadgeController {

    private final BadgeService badgeService;

    @Operation(summary = "뱃지 제작", description = "뱃지를 제작하고 저장합니다.", tags = { "뱃지" })
    @PostMapping("/make-badge")
    public void save(@RequestBody BadgeSaveRequestDto dto) {
        badgeService.save(dto);
    }
}
