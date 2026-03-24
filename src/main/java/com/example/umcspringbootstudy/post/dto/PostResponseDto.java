package com.example.umcspringbootstudy.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostResponseDto {
    private Long postId;
    private Long userId;
    private String title;
    private String content;
}
