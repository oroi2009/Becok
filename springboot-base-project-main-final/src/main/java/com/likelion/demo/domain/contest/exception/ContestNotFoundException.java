package com.likelion.demo.domain.contest.exception;

import com.likelion.demo.global.exception.BaseException;

public class ContestNotFoundException extends BaseException {

    public ContestNotFoundException() {
        super(ContestErrorCode.POPULAR_CONTEST_NOT_FOUND_404);
    }
}
