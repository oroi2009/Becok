package com.likelion.demo.domain.recommendation.exception;

import com.likelion.demo.domain.member.exception.MemberErrorCode;
import com.likelion.demo.global.exception.BaseException;

public class RecommendNotFoundException extends BaseException {
    public RecommendNotFoundException() {
        super(RecommendProgramErrorCode.RECOMMEND_PROGRAM_NOT_FOUND_404);
    }
}
