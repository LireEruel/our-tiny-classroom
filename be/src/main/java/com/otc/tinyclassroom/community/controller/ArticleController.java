package com.otc.tinyclassroom.community.controller;

import com.otc.tinyclassroom.community.dto.request.ArticleCreateRequestDto;
import com.otc.tinyclassroom.community.dto.request.ArticleUpdateRequestDto;
import com.otc.tinyclassroom.community.dto.response.ArticleResponseDto;
import com.otc.tinyclassroom.community.dto.response.ArticleWithCommentsResponseDto;
import com.otc.tinyclassroom.community.entity.type.ArticleType;
import com.otc.tinyclassroom.community.entity.type.SearchType;
import com.otc.tinyclassroom.community.service.ArticleService;
import com.otc.tinyclassroom.global.common.model.response.BaseResponse;
import com.otc.tinyclassroom.global.security.auth.PrincipalDetails;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 게시글 controller dto.
 */
@RestController
@RequestMapping("/api/community")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    /**
     * 지정 커뮤니티의 게시글 목록 조회.
     */
    @Operation(summary = "게시글 조회", description = "parameter를 보내면 해당 커뮤니티의 게시글 목록을 조회합니다.", tags = { "게시글" })
    @GetMapping("/articles")
    public BaseResponse<?> getArticleList(
        @RequestParam(required = false) String boardType,
        @RequestParam(required = false) String searchType,
        @RequestParam(required = false) String searchValue,
        @PageableDefault(size = 10, sort = "createdAt", direction = Direction.DESC) Pageable pageable
    ) {
        if (boardType != null) {
            // 특정 커뮤니티의 게시글 목록을 처리합니다.
            return BaseResponse.success(HttpStatus.OK.value(), "게시글 목록 반환 성공",
                articleService.searchArticles(null, ArticleType.fromString(boardType), SearchType.fromString(searchType), searchValue, pageable).map(ArticleResponseDto::from));
        } else {
            // 모든 게시물을 처리합니다.
            return BaseResponse.success(HttpStatus.OK.value(), "전체 게시물을 조회했습니다.", articleService.getArticleList());
        }
    }

    /**
     * 게시글 생성.
     */
    @Operation(summary = "게시글 작성", description = "커뮤니티에 게시글을 작성합니다.", tags = { "게시글" })
    @PostMapping("/articles")
    public BaseResponse<Void> createArticle(@RequestBody ArticleCreateRequestDto articleCreateRequestDto,
        @AuthenticationPrincipal PrincipalDetails principal) {
        articleService.createArticle(principal.getMember().getId(), articleCreateRequestDto);
        return BaseResponse.success(HttpStatus.CREATED.value(), "게시글 작성 완료", null);
    }

    /**
     * 게시글 삭제.
     */
    @Operation(summary = "게시글 삭제", description = "권한 있는 글에 대해 삭제 요청을 보내면 게시글을 삭제합니다.", tags = { "게시글" })
    @DeleteMapping("/articles/{articleId}")
    public BaseResponse<Void> removeArticle(@PathVariable("articleId") Long articleId, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        articleService.validateAuthority(principalDetails.getMember().getId(), articleId);
        articleService.deleteArticle(articleId);
        return BaseResponse.success(HttpStatus.OK.value(), "게시글 삭제 성공", null);
    }

    /**
     * 게시글 업데이트.
     */
    @Operation(summary = "게시글 수정", description = "권한 있는 글에 대해 수정 요청을 보내면 게시글을 수정합니다.", tags = { "게시글" })
    @PatchMapping("/articles/{articleId}")
    public BaseResponse<Void> updateArticle(@PathVariable("articleId") Long articleId, @RequestBody ArticleUpdateRequestDto articleUpdateRequestDto,
        @AuthenticationPrincipal PrincipalDetails principalDetails) {
        articleService.validateAuthority(principalDetails.getMember().getId(), articleId);
        articleService.updateArticle(articleId, articleUpdateRequestDto);
        return BaseResponse.success(HttpStatus.OK.value(), "게시글 수정 성공", null);
    }

    /**
     * 게시글 상세보기.
     */
    @Operation(summary = "게시글 상세보기", description = "게시글에 대한 상세한 정보를 조회합니다.", tags = { "게시글" })
    @GetMapping("/articles/{articleId}")
    public BaseResponse<ArticleWithCommentsResponseDto> getArticleDetail(@PathVariable("articleId") Long articleId) {
        ArticleWithCommentsResponseDto article = ArticleWithCommentsResponseDto.from(articleService.getArticle(articleId));
        return BaseResponse.success(HttpStatus.OK.value(), "게시글 반환성공", article);
    }

    /**
     * 커뮤니티의 반별 게시판 조회.
     */
    @Operation(summary = "반별 게시판 조회", description = "특정 반 만의 게시판을 조회합니다.", tags = { "커뮤니티" })
    @GetMapping("/classRoom/{classRoomId}/articles")
    public BaseResponse<Page<ArticleResponseDto>> getArticleListForClassRoom(
        @PathVariable("classRoomId") Long classRoomId,
        @RequestParam(required = true) String boardType,
        @RequestParam(required = false) String searchType,
        @RequestParam(required = false) String searchValue,
        @PageableDefault(size = 10, sort = "createdAt", direction = Direction.DESC) Pageable pageable
    ) {
        return BaseResponse.success(HttpStatus.OK.value(), "게시글 목록 반환 성공",
            articleService.searchArticles(classRoomId, ArticleType.fromString(boardType), SearchType.fromString(searchType), searchValue, pageable).map(ArticleResponseDto::from));
    }

    /**
     * 반별 게시판의 게시글 생성.
     */
    @Operation(summary = "반별 게시판 게시글 생성", description = "특정 반의 게시글을 생성합니다.", tags = { "커뮤니티" })
    @PostMapping("/classRoom/{classRoomId}/articles")
    public BaseResponse<Void> createArticleForClassRoom(@RequestBody ArticleCreateRequestDto articleCreateRequestDto) {
        articleService.createArticle(articleService.getCurrentMemberId(), articleCreateRequestDto);
        return BaseResponse.success(HttpStatus.CREATED.value(), "게시글 작성 완료", null);
    }

    /**
     * 반별 게시판의 게시글 삭제.
     */
    @Operation(summary = "반별 게시판 게시글 삭제", description = "내가 작성한 글인 경우, 특정 반의 게시글을 삭제합니다.", tags = { "커뮤니티" })
    @DeleteMapping("/classRoom/{classRoomId}/articles/{articleId}")
    public BaseResponse<Void> removeArticleForClassRoom(@PathVariable("articleId") Long articleId, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        articleService.validateAuthority(principalDetails.getMember().getId(), articleId);
        articleService.deleteArticle(articleId);
        return BaseResponse.success(HttpStatus.OK.value(), "게시글 삭제 성공", null);
    }

    /**
     * 반별 게시판 내 게시글 업데이트.
     */
    @Operation(summary = "반별 게시판 게시글 수정", description = "내가 작성한 글인 경우, 특정 반의 게시글을 수정합니다.", tags = { "커뮤니티" })
    @PatchMapping("/classRoom/{classRoomId}/articles/{articleId}")
    public BaseResponse<Void> updateArticleForClassRoom(@PathVariable("articleId") Long articleId, @RequestBody ArticleUpdateRequestDto articleUpdateRequestDto,
        @AuthenticationPrincipal PrincipalDetails principalDetails) {
        articleService.validateAuthority(principalDetails.getMember().getId(), articleId);
        articleService.updateArticle(articleId, articleUpdateRequestDto);
        return BaseResponse.success(HttpStatus.OK.value(), "게시글 삭제 성공", null);
    }

    /**
     * 반별 게시판 내 게시글 상세보기.
     */
    @Operation(summary = "반별 게시판 게시글 조회", description = "특정 반의 게시글을 상세조회합니다.", tags = { "커뮤니티" })
    @GetMapping("/classRoom/{classRoomId}/articles/{articleId}")
    public BaseResponse<ArticleWithCommentsResponseDto> getArticleDetailForClassRoom(@PathVariable("articleId") Long articleId) {
        ArticleWithCommentsResponseDto article = ArticleWithCommentsResponseDto.from(articleService.getArticle(articleId));
        return BaseResponse.success(HttpStatus.OK.value(), "게시글 반환성공", article);
    }

}
