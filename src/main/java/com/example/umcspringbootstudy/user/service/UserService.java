package com.example.umcspringbootstudy.user.service;

import com.example.umcspringbootstudy.global.apiPayload.exception.GeneralException;
import com.example.umcspringbootstudy.global.auth.CustomUserDetails;
import com.example.umcspringbootstudy.global.auth.enums.Role;
import com.example.umcspringbootstudy.global.auth.jwt.JwtUtil;
import com.example.umcspringbootstudy.user.dto.UserRequestDTO;
import com.example.umcspringbootstudy.user.dto.UserResponseDTO;
import com.example.umcspringbootstudy.user.entity.User;
import com.example.umcspringbootstudy.user.exception.code.UserErrorCode;
import com.example.umcspringbootstudy.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public UserResponseDTO.SignUpDTO signup(UserRequestDTO.SignUpDTO dto) {

        if (userRepository.existsByEmail(dto.email())) {
            throw new GeneralException(UserErrorCode.USER_ALREADY_EXISTS);
        }

        String encodedPassword = passwordEncoder.encode(dto.password());

        User user = User.builder()
                .nickname(dto.nickname())
                .email(dto.email())
                .password(encodedPassword)
                .role(Role.ROLE_USER)
                .gender(dto.gender())
                .phoneNumber(dto.phoneNumber())
                .build();

        User savedUser = userRepository.save(user);

        return new UserResponseDTO.SignUpDTO(
                savedUser.getId(),
                savedUser.getNickname(),
                savedUser.getEmail()
        );
    }

    public UserResponseDTO.LoginDTO login(UserRequestDTO.@Valid LoginDTO dto) {
        // [1] 이메일로 유저 조회 (없으면 예외)
        User user = userRepository.findByEmail(dto.email())
                .orElseThrow(() -> new GeneralException(UserErrorCode.USER_NOT_FOUND));

        // [2] 입력한 비밀번호와 DB의 암호화된 비밀번호 비교
        if (!passwordEncoder.matches(dto.password(), user.getPassword())) {
            throw new GeneralException(UserErrorCode.INVALID_PASSWORD);
        }

        // [3] CustomUserDetails로 감싼 뒤 토큰 발급
        CustomUserDetails userDetails = new CustomUserDetails(user);

        // [4] accessToken 발급
        String accessToken = jwtUtil.createAccessToken(userDetails);

        return UserResponseDTO.LoginDTO.builder()
                .memberId(user.getId())
                .accessToken(accessToken)
                .build();
    }
}
