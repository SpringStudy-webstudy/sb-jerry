package com.example.umcspringbootstudy.post.exception;

import com.example.umcspringbootstudy.global.apiPayload.code.BaseErrorCode;
import com.example.umcspringbootstudy.global.apiPayload.exception.GeneralException;

public class PostException extends GeneralException {
    public PostException(BaseErrorCode code) {
      super(code);
    }
}
