package com.likelion.demo.domain.remember.web.controller;

import com.likelion.demo.domain.contest.web.dto.ContestDetailRes;
import com.likelion.demo.domain.remember.service.RememberService;
import com.likelion.demo.domain.remember.web.dto.RememberRes;
import com.likelion.demo.global.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/remember")
@RequiredArgsConstructor
public class RememberController {
    private final RememberService rememberService;

    @GetMapping
    public ResponseEntity<SuccessResponse<List<RememberRes>>> getRemember(@RequestParam Long memberId, @RequestParam String view) {
        List<RememberRes> res = rememberService.getRememberList(memberId, view);
        return ResponseEntity.status(HttpStatus.OK).body(SuccessResponse.created(res));
    }
}