package com.example.umcspringbootstudy.comment.controller;

import com.example.umcspringbootstudy.comment.dto.CommentRequestDto;
import com.example.umcspringbootstudy.comment.dto.CommentResponseDto;
import com.example.umcspringbootstudy.comment.service.CommentService;
import com.example.umcspringbootstudy.global.apiPayload.ApiResponse;
import com.example.umcspringbootstudy.global.apiPayload.code.GeneralSuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "댓글 API",
        description = "댓글 생성/삭제 API"
)
@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    @Operation(
            summary = "댓글 생성",
            description = "게시글에 댓글을 생성합니다."
    )
    @PostMapping
    public ApiResponse<CommentResponseDto> createComment(@Valid @RequestBody CommentRequestDto request) {
        return ApiResponse.onSuccess(GeneralSuccessCode.CREATED, commentService.createComment(request));
    }

    @Operation(
            summary = "댓글 삭제",
            description = "commentId와 userId를 이용해 댓글을 삭제합니다."
    )
    @DeleteMapping("/{commentId}")
    public ApiResponse<Void> deleteComment(
            @PathVariable Long commentId,
            @RequestParam Long userId) {
        commentService.deleteComment(commentId, userId);
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, null);
    }
}
