package com.likelion.demo.domain.member.exception;

import com.likelion.demo.global.exception.BaseException;

public class LoginFailException extends BaseException {
    public LoginFailException() {
        super(MemberErrorCode.LOGIN_FAIL);
    }
}
