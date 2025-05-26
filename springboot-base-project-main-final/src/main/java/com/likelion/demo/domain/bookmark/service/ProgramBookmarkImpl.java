package com.likelion.demo.domain.bookmark.service;

import com.likelion.demo.domain.bookmark.entity.ContestBookmark;
import com.likelion.demo.domain.bookmark.entity.ProgramBookmark;
import com.likelion.demo.domain.bookmark.repository.ProgramBookmarkRepository;
import com.likelion.demo.domain.contest.entity.Contest;
import com.likelion.demo.domain.contest.repository.ContestRepository;
import com.likelion.demo.domain.member.entity.Member;
import com.likelion.demo.domain.member.exception.MemberNotFoundException;
import com.likelion.demo.domain.member.repository.MemberRepository;
import com.likelion.demo.domain.programData.entity.Program;
import com.likelion.demo.domain.programData.exception.ProgramNotFoundException;
import com.likelion.demo.domain.programData.repository.ProgramRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProgramBookmarkImpl implements ProgramBookmarkService {
    private final ProgramBookmarkRepository repository;
    private final MemberRepository memberRepository;
    private final ProgramRepository programRepository;


    public boolean toggle(Long memberId, Long programId) {
        boolean exists = repository.existsByMember_IdAndProgram_Id(memberId, programId);
        if (exists) {
            repository.deleteByMember_IdAndProgram_Id(memberId, programId);
            return true;
        }
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        Program program = programRepository.findById(programId).orElseThrow(ProgramNotFoundException::new);
        ProgramBookmark bookmark = ProgramBookmark.builder()
                .member(member)
                .program(program)
                .build();
        repository.save(bookmark);
        return true;
    }
}
