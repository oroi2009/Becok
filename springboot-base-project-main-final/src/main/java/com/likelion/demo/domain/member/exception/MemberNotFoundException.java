package com.likelion.demo.domain.member.exception;

import com.likelion.demo.global.exception.BaseException;

public class MemberNotFoundException extends BaseException {
    public MemberNotFoundException() {
        super(MemberErrorCode.MEMBER_NOT_FOUND_404);
    }
}
