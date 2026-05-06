package com.example.umcspringbootstudy.user.dto;

import com.example.umcspringbootstudy.user.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserRequestDTO {

    public record SignUpDTO(
            @NotBlank
            String nickname,
            @NotBlank
            @Email
            String email,
            @NotBlank
            String password,
            Gender gender,
            String phoneNumber
    ) {}

    public record LoginDTO(
            @NotBlank
            String email,
            @NotBlank
            String password
    ){}
}
