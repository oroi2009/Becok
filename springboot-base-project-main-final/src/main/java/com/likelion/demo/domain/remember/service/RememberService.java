package com.likelion.demo.domain.remember.service;

import com.likelion.demo.domain.remember.web.dto.RememberRes;

import java.util.List;

public interface RememberService {
    //view = "bookmark" or "notification" id 보관함 전체조회
    List<RememberRes> getRememberList(Long memberId, String view);
}
