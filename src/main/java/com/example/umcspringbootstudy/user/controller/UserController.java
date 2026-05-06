package com.example.umcspringbootstudy.user.controller;

import com.example.umcspringbootstudy.global.apiPayload.ApiResponse;
import com.example.umcspringbootstudy.global.apiPayload.code.GeneralSuccessCode;
import com.example.umcspringbootstudy.user.dto.UserRequestDTO;
import com.example.umcspringbootstudy.user.dto.UserResponseDTO;
import com.example.umcspringbootstudy.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "유저 API", description = "회원가입/로그인 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Operation(summary = "회원가입", description = "이메일, 비밀번호, 닉네임으로 회원가입합니다.")
    @PostMapping("/signup")
    public ApiResponse<UserResponseDTO.SignUpDTO> signup(@Valid @RequestBody UserRequestDTO.SignUpDTO dto) {
        return ApiResponse.onSuccess(GeneralSuccessCode.CREATED, userService.signup(dto));
    }

    @PostMapping("/login")
    public ApiResponse<UserResponseDTO.LoginDTO> login(@Valid @RequestBody UserRequestDTO.LoginDTO dto){
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, userService.login(dto));
    }
}
