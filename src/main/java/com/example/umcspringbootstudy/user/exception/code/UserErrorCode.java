package com.example.umcspringbootstudy.user.exception.code;

import com.example.umcspringbootstudy.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserErrorCode implements BaseErrorCode {

    // 409 CONFLICT
    USER_ALREADY_EXISTS(HttpStatus.CONFLICT, "USER409_1", "이미 사용 중인 이메일입니다."),

    // 404 NOT FOUND
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER404_1", "사용자를 찾을 수 없습니다."),

    // 401 UNAUTHORIZED
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "USER401_1", "비밀번호가 올바르지 않습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
