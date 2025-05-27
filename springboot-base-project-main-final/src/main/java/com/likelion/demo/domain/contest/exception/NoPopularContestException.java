package com.likelion.demo.domain.contest.exception;

import com.likelion.demo.global.exception.BaseException;

public class NoPopularContestException extends BaseException {
    public NoPopularContestException() {
        super(ContestErrorCode.POPULAR_CONTEST_NOT_FOUND_404);
    }
}
