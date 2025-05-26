package com.likelion.demo.domain.bookmark.web.controller;

import com.likelion.demo.domain.bookmark.exception.InvalidBookmarkTypeException;
import com.likelion.demo.domain.bookmark.service.ContestBookmarkService;
import com.likelion.demo.domain.bookmark.service.ProgramBookmarkService;
import com.likelion.demo.domain.bookmark.web.dto.BookmarkToggleReq;
import com.likelion.demo.domain.bookmark.web.dto.BookmarkToggleRes;
import com.likelion.demo.global.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookmark")
@RequiredArgsConstructor
public class BookmarkController {

    private final ContestBookmarkService contestBookmarkService;
    private final ProgramBookmarkService programBookmarkService;

    @PostMapping("/{memberId}")
    public SuccessResponse<BookmarkToggleRes> toggleBookmark(@PathVariable Long memberId,
                                                             @RequestBody BookmarkToggleReq request) {
        boolean isOn = switch (request.getType()) {
            case "program" -> programBookmarkService.toggle(request.getContentId(), memberId);
            case "contest" -> contestBookmarkService.toggle(request.getContentId(), memberId);
            // 400 : 잘못된 북마크 타입
            default -> throw new InvalidBookmarkTypeException();
        };

        BookmarkToggleRes response = new BookmarkToggleRes(isOn ? "on" : "off");
        return SuccessResponse.created(response);
    }
}
