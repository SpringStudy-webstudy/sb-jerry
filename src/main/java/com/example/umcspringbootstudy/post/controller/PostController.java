package com.example.umcspringbootstudy.post.controller;

import com.example.umcspringbootstudy.global.apiPayload.ApiResponse;
import com.example.umcspringbootstudy.global.apiPayload.code.GeneralSuccessCode;
import com.example.umcspringbootstudy.post.dto.PostDetailResponseDto;
import com.example.umcspringbootstudy.post.dto.PostRequestDto;
import com.example.umcspringbootstudy.post.dto.PostResponseDto;
import com.example.umcspringbootstudy.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    @PostMapping
    public ApiResponse<PostResponseDto> createPost(@Valid @RequestBody PostRequestDto request) {
        return ApiResponse.onSuccess(GeneralSuccessCode.CREATED, postService.createPost(request));
    }

    @GetMapping("/{postId}")
    public ApiResponse<PostDetailResponseDto> getPost(@PathVariable Long postId){
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, postService.getPost(postId));
    }
}
