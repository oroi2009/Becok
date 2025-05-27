package com.likelion.demo.domain.notification.service;

import com.likelion.demo.domain.bookmark.entity.ProgramBookmark;
import com.likelion.demo.domain.bookmark.repository.ProgramBookmarkRepository;
import com.likelion.demo.domain.member.exception.MemberNotFoundException;
import com.likelion.demo.domain.member.repository.MemberRepository;
import com.likelion.demo.domain.notification.entity.ProgramNotification;
import com.likelion.demo.domain.notification.repository.ProgramNotificationRepository;
import com.likelion.demo.domain.programData.exception.ProgramNotFoundException;
import com.likelion.demo.domain.programData.repository.ProgramRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProgramNotificationServiceImpl implements ProgramNotificationService {
    private final ProgramNotificationRepository repository;
    private final MemberRepository memberRepository;
    private final ProgramRepository programRepository;

    @Override
    public boolean toggleNotific(Long memberId, Long programId) {
        Optional<ProgramNotification> foundNotification =
                repository.findByMember_IdAndProgram_Id(memberId, programId);

        // 알림 눌려있는 경우
        if (foundNotification.isPresent()) {
            repository.delete(foundNotification.get());
            return false; // 북마크 OFF
        }

        // 알림 안 눌려있는 경우
        var member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new); // 404: 회원 없음

        var program = programRepository.findById(programId)
                .orElseThrow(ProgramNotFoundException::new); // 404: 프로그램 없음

        ProgramNotification notification = ProgramNotification.builder()
                .member(member)
                .program(program)
                .build();

        repository.save(notification);
        return true; // 북마크 ON
    }
}
