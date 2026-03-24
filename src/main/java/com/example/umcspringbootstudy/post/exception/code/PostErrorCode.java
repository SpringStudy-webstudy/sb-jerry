package com.example.umcspringbootstudy.post.exception.code;

import com.example.umcspringbootstudy.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum PostErrorCode implements BaseErrorCode {
     // 400 BAD REQUEST
    POST_TITLE_BLANK(HttpStatus.BAD_REQUEST, "POST400_1", "제목은 필수입니다."),
    POST_CONTENT_BLANK(HttpStatus.BAD_REQUEST, "POST400_2", "내용은 필수입니다."),
    POST_TITLE_TOO_LONG(HttpStatus.BAD_REQUEST, "POST400_3", "제목은 50자 이하여야 합니다."),
    POST_CONTENT_TOO_LONG(HttpStatus.BAD_REQUEST, "POST400_4", "내용은 1000자 이하여야 합니다."),

    // 403 FORBIDDEN
    POST_UNAUTHORIZED(HttpStatus.FORBIDDEN, "POST403_1", "본인이 작성한 게시글만 수정/삭제할 수 있습니다."),

    // 404 NOT FOUND
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "POST404_1", "게시글을 찾을 수 없습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "POST404_2", "사용자를 찾을 수 없습니다.");


    private final HttpStatus status;
    private final String code;
    private final String message;
}
