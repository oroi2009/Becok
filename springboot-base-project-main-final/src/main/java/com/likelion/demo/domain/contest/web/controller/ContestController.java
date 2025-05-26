package com.likelion.demo.domain.contest.web.controller;

import com.likelion.demo.domain.contest.service.ContestService;
import com.likelion.demo.domain.contest.web.dto.ContestDetailRes;
import com.likelion.demo.domain.contest.web.dto.ContestRes;
import com.likelion.demo.global.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ContestController {

    private final ContestService contestService;

    @GetMapping("/contests/popular")
    public ResponseEntity<SuccessResponse<List<ContestRes>>> getPopularContests() {
        List<ContestRes> contests = contestService.getPopularContests();

        return ResponseEntity.status(HttpStatus.OK).body(SuccessResponse.ok(contests));
    }

    @GetMapping("contests/{contestId}")
    public ResponseEntity<SuccessResponse<ContestDetailRes>> getContestDetail(@PathVariable Long contestId) {
        ContestDetailRes detail = contestService.getContestDetail(contestId);
        return ResponseEntity.status(HttpStatus.OK).body(SuccessResponse.ok(detail));
    }
}
