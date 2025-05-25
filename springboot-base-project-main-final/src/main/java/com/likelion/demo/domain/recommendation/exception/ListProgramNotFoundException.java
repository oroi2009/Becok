package com.likelion.demo.domain.recommendation.exception;

import com.likelion.demo.global.exception.BaseException;

public class ListProgramNotFoundException extends BaseException {
    public ListProgramNotFoundException() {
        super(RecommendProgramErrorCode.LIST_PROGRAM_NOT_FOUND_404);
    }
}
