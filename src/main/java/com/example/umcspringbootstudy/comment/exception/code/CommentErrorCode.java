package com.example.umcspringbootstudy.comment.exception.code;

import com.example.umcspringbootstudy.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CommentErrorCode implements BaseErrorCode {
    // 400 BAD REQUEST
    COMMENT_CONTENT_BLANK(HttpStatus.BAD_REQUEST, "COMMENT400_1", "댓글 내용은 필수입니다."),
    COMMENT_CONTENT_TOO_LONG(HttpStatus.BAD_REQUEST, "COMMENT400_2", "댓글은 500자 이하여야 합니다."),

    // 403 FORBIDDEN
    COMMENT_UNAUTHORIZED(HttpStatus.FORBIDDEN, "COMMENT403_1", "본인이 작성한 댓글만 수정/삭제할 수 있습니다."),

    // 404 NOT FOUND
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "COMMENT404_1", "댓글을 찾을 수 없습니다."),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "COMMENT404_2", "게시글을 찾을 수 없습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "COMMENT404_3", "사용자를 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
