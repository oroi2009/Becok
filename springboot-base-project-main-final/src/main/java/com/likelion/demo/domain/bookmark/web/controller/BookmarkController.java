package com.likelion.demo.domain.bookmark.web.controller;

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
            default -> throw new IllegalArgumentException("Invalid bookmark type: " + request.getType());
        };

        BookmarkToggleRes response = new BookmarkToggleRes(isOn ? "on" : "off");
        return SuccessResponse.created(response);
    }
}
