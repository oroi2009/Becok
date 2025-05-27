package com.likelion.demo.domain.notification.web.controller;

import com.likelion.demo.domain.bookmark.web.dto.BookmarkToggleRes;
import com.likelion.demo.domain.notification.exception.InvalidNotificationTypeException;
import com.likelion.demo.domain.notification.service.ContestNotificationService;
import com.likelion.demo.domain.notification.service.ProgramNotificationService;
import com.likelion.demo.domain.notification.web.dto.NotificationToggleReq;
import com.likelion.demo.global.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationController {
    private final ContestNotificationService contestNotificationService;
    private final ProgramNotificationService programNotificationService;

    @PostMapping("/{memberId}")
    public SuccessResponse<BookmarkToggleRes> toggleNotification(@PathVariable Long memberId,
                                                             @RequestBody NotificationToggleReq request) {
        boolean isOn = switch (request.getType()) {
            case "program" -> programNotificationService.toggleNotific(memberId, request.getContentId());
            case "contest" -> contestNotificationService.toggleNotific(memberId, request.getContentId());
            // 400 : 잘못된 북마크 타입
            default -> throw new InvalidNotificationTypeException();
        };

        BookmarkToggleRes response = new BookmarkToggleRes(isOn ? "on" : "off");
        return SuccessResponse.created(response);
    }
}
