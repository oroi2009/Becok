package com.likelion.demo.domain.member.exception;

import com.likelion.demo.global.exception.BaseException;

public class PasswordMismatchException extends BaseException {
    public PasswordMismatchException() {
        super(MemberErrorCode.PASSWORD_NOT_MATCH);
    }
}
