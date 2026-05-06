package com.example.umcspringbootstudy.user.dto;

import lombok.Builder;

public class UserResponseDTO {

    public record SignUpDTO(
            Long id,
            String nickname,
            String email
    ) {}

    @Builder
    public record LoginDTO(
            Long memberId,
            String accessToken
    ){}
}
