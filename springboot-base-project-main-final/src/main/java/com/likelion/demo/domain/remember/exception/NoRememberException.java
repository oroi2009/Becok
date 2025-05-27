package com.likelion.demo.domain.remember.exception;

import com.likelion.demo.global.exception.BaseException;

public class NoRememberException extends BaseException {
    public NoRememberException() {
        super(RememberErrorCode.NO_REMEMBER_CONTENT_404);
    }
}