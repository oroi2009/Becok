package com.likelion.demo.domain.member.exception;

import com.likelion.demo.global.exception.BaseException;

public class EmailDuplicateException extends BaseException {
    public EmailDuplicateException() {
        super(MemberErrorCode.DUPLICATE_EMAIL);
    }
}
