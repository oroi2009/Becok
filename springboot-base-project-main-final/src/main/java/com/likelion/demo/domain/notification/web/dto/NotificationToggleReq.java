package com.likelion.demo.domain.notification.web.dto;

import lombok.Getter;

@Getter
public class NotificationToggleReq {
    private String type; // contest(공모전) or program(비교과)
    private Long contentId; // 해당 공모전/비교과의 고유 id
}
