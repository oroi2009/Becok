package com.likelion.demo.domain.remember.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RememberRes {
    private Long id;           // 북마크/알림 고유 ID
    private Long contentId;    // 실제 공모전/프로그램 ID
    private String domain;     // "contest" or "program"
    private String title;      // 제목
    private String period;     // 접수기간 "2025.05.14(수) – 2025.06.08(토)"
}

