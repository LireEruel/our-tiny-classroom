package com.otc.tinyclassroom.community.controller;

import com.otc.tinyclassroom.community.dto.request.ArticleCommentRequestDto;
import com.otc.tinyclassroom.community.dto.request.ArticleCommentUpdateRequestDto;
import com.otc.tinyclassroom.community.service.ArticleCommentService;
import com.otc.tinyclassroom.global.common.model.response.BaseResponse;
import com.otc.tinyclassroom.global.security.auth.PrincipalDetails;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 댓글 controller.
 */
@RequestMapping("/api/community/comments")
@RequiredArgsConstructor
@RestController
public class ArticleCommentController {

    private final ArticleCommentService articleCommentService;

    /**
     * 댓글 작성요청을 처리한다.
     *
     * @param request 댓글 작성정보
     * @return 응답코드, 메세지, 댓글 아이디
     */
    @Operation(summary = "댓글 작성", description = "댓글 정보를 보내면 작성합니다.", tags = { "댓글" })
    @PostMapping()
    public BaseResponse<Long> createArticleComment(@RequestBody ArticleCommentRequestDto request, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Long articleCommentId = articleCommentService.createArticleComment(principalDetails.getMember().getId(), request);
        return BaseResponse.success(HttpStatus.CREATED.value(), "댓글 작성완료", articleCommentId);
    }

    /**
     * 댓글 수정 요청을 처리한다.
     *
     * @param articleCommentId 수정할 댓글 아이디
     * @param request          수정 정보
     * @return 응답코드, 메세지, 댓글 아이디
     */
    @Operation(summary = "댓글 수정", description = "댓글 정보를 보내면 수정합니다.", tags = { "댓글" })
    @PatchMapping("/{articleCommentId}")
    public BaseResponse<Void> updateArticleComment(@PathVariable("articleCommentId") Long articleCommentId, @RequestBody ArticleCommentUpdateRequestDto request,
        @AuthenticationPrincipal PrincipalDetails principalDetails) {
        articleCommentService.validateAuthority(principalDetails.getMember().getId(), articleCommentId);
        articleCommentService.updateArticleComment(articleCommentId, request);
        return BaseResponse.success(HttpStatus.OK.value(), "댓글 수정 성공", null);
    }

    /**
     * 댓글 삭제요청을 처리한다.
     *
     * @param articleCommentId 댓글 아이디
     * @return 응답코드, 메세지
     */
    @Operation(summary = "댓글 삭제", description = "댓글 id를 보내면 삭제합니다.", tags = { "댓글" })
    @DeleteMapping("/{articleCommentId}")
    public BaseResponse<Void> deleteArticleComment(@PathVariable("articleCommentId") Long articleCommentId, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        articleCommentService.validateAuthority(principalDetails.getMember().getId(), articleCommentId);
        articleCommentService.deleteArticleComment(articleCommentId);
        return BaseResponse.success(HttpStatus.NO_CONTENT.value(), "댓글 삭제 성공", null);
    }
}
