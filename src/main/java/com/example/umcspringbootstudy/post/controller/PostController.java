package com.example.umcspringbootstudy.post.controller;

import com.example.umcspringbootstudy.global.apiPayload.ApiResponse;
import com.example.umcspringbootstudy.global.apiPayload.code.GeneralSuccessCode;
import com.example.umcspringbootstudy.post.dto.*;
import com.example.umcspringbootstudy.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Tag(
        name = "게시판 API",
        description = "게시판 생성/수정/삭제/조회 API"
)
@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    @Operation(
            summary = "게시글 생성",
            description = "제목, 내용 기반 게시글을 생성합니다."
    )
    @PostMapping
    public ApiResponse<PostResponseDto> createPost(@Valid @RequestBody PostRequestDto request) {
        return ApiResponse.onSuccess(GeneralSuccessCode.CREATED, postService.createPost(request));
    }

    @Operation(
            summary = "게시글 상세 조회",
            description = "postId, 저자, 제목, 내용, 생성일을 반환합니다."
    )
    @GetMapping("/{postId}")
    public ApiResponse<PostDetailResponseDto> getPost(@PathVariable Long postId){
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, postService.getPost(postId));
    }

    @Operation(
            summary = "게시글 삭제",
            description = "postId와 userId를 이용해 게시글을 삭제합니다."
    )
    @DeleteMapping("/{postId}")
    public ApiResponse<Void> deletePost(@PathVariable Long postId, @RequestParam Long userId) {
        postService.deletePost(postId, userId);
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, null);
    }

    @Operation(
            summary = "게시글 수정",
            description = "제목과 내용을 수정합니다. 작성자만 가능합니다."
    )
    @PutMapping("/{postId}")
    public ApiResponse<PostResponseDto> updatePost(
            @PathVariable Long postId,
            @RequestParam Long userId,
            @Valid @RequestBody PostUpdateRequestDto request) {
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, postService.updatePost(postId, userId, request));
    }

    @Operation(summary = "게시글 목록 조회", description = "페이징/정렬을 지원합니다.")
    @GetMapping("/lists")
    public ApiResponse<PostListResponseDto> getPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) LocalDateTime startDate,
            @RequestParam(required = false) LocalDateTime endDate
    ) {
        return ApiResponse.onSuccess(GeneralSuccessCode.OK,
                postService.getPosts(page, size, sortBy, keyword, startDate, endDate));
    }

    @Operation(summary = "게시글 목록 조회 (QueryDSL)", description = "QueryDSL 방식으로 검색/페이징을 지원합니다.")
    @GetMapping("/lists/querydsl")
    public ApiResponse<PostListResponseDto> getPostsQueryDsl(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) LocalDateTime startDate,
            @RequestParam(required = false) LocalDateTime endDate
    ) {
        return ApiResponse.onSuccess(GeneralSuccessCode.OK,
                postService.getPostsQueryDsl(page, size, sortBy, keyword, startDate, endDate));
    }

    @Operation(summary = "게시글 조회 EXPLAIN", description = "created_at 인덱스 사용 여부를 EXPLAIN으로 확인합니다.")
    @GetMapping("/explain")
    public ApiResponse<List<Map<String, Object>>> explainPosts() {
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, postService.explainPosts());
    }
}
