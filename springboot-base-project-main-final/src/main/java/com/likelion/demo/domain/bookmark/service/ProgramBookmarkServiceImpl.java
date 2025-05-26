package com.likelion.demo.domain.bookmark.service;

import com.likelion.demo.domain.bookmark.entity.ProgramBookmark;
import com.likelion.demo.domain.bookmark.repository.ProgramBookmarkRepository;
import com.likelion.demo.domain.member.exception.MemberNotFoundException;
import com.likelion.demo.domain.member.repository.MemberRepository;
import com.likelion.demo.domain.programData.exception.ProgramNotFoundException;
import com.likelion.demo.domain.programData.repository.ProgramRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProgramBookmarkServiceImpl implements ProgramBookmarkService {

    private final ProgramBookmarkRepository repository;
    private final MemberRepository memberRepository;
    private final ProgramRepository programRepository;

    @Override
    public boolean toggle(Long memberId, Long programId) {
        Optional<ProgramBookmark> foundBookmark =
                repository.findByMember_IdAndProgram_Id(memberId, programId);

        // 북마크가 눌려있는 경우
        if (foundBookmark.isPresent()) {
            repository.delete(foundBookmark.get());
            return false; // 북마크 OFF
        }

        // 북마크가 안 눌려있는 경우
        var member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new); // 404: 회원 없음

        var program = programRepository.findById(programId)
                .orElseThrow(ProgramNotFoundException::new); // 404: 프로그램 없음

        ProgramBookmark bookmark = ProgramBookmark.builder()
                .member(member)
                .program(program)
                .build();

        repository.save(bookmark);
        return true; // 북마크 ON
    }
}