package com.example.umcspringbootstudy.user.exception;

import com.example.umcspringbootstudy.global.apiPayload.code.BaseErrorCode;
import com.example.umcspringbootstudy.global.apiPayload.exception.GeneralException;

public class UserException extends GeneralException {
    public UserException(BaseErrorCode code) {
        super(code);
    }
}
