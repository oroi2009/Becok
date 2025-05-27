package com.likelion.demo.domain.remember.service;

import com.likelion.demo.domain.bookmark.repository.ContestBookmarkRepository;
import com.likelion.demo.domain.bookmark.repository.ProgramBookmarkRepository;
import com.likelion.demo.domain.contest.entity.Contest;
import com.likelion.demo.domain.contest.repository.ContestRepository;
import com.likelion.demo.domain.notification.repository.ContestNotificationRepository;
import com.likelion.demo.domain.notification.repository.ProgramNotificationRepository;
import com.likelion.demo.domain.programData.entity.Program;
import com.likelion.demo.domain.programData.repository.ProgramRepository;
import com.likelion.demo.domain.remember.exception.NoRememberException;
import com.likelion.demo.domain.remember.web.dto.RememberRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class RememberServiceImpl implements RememberService {
    private final ContestBookmarkRepository cbRepo;
    private final ProgramBookmarkRepository pbRepo;
    private final ContestNotificationRepository cnRepo;
    private final ProgramNotificationRepository pnRepo;
    private final ContestRepository contestRepo;
    private final ProgramRepository programRepo;

    private static final DateTimeFormatter PERIOD = DateTimeFormatter.ofPattern("yyyy.MM.dd(EEE)", Locale.KOREAN);

    @Override
    public List<RememberRes> getRememberList(Long memberId, String view) {
        List<RememberRes> result = new ArrayList<>();

        if ("bookmark".equalsIgnoreCase(view)) {
            cbRepo.findByMember_Id(memberId).forEach(cb -> {
                Contest c = contestRepo.getReferenceById(cb.getContest().getId());
                result.add(toDto(cb.getId(), "contest", c.getId(), c.getName(), c.getStartDate(), c.getEndDate()));
            });
            pbRepo.findByMember_Id(memberId).forEach(pb -> {
                Program p = programRepo.getReferenceById(pb.getProgram().getId());
                result.add(toDto(pb.getId(), "program", p.getId(), p.getTitle(), p.getStart_date(), p.getEnd_date()));
            });
        } else if ("notification".equalsIgnoreCase(view)) {
            cnRepo.findByMember_Id(memberId).forEach(n -> {
                Contest c = contestRepo.getReferenceById(n.getContest().getId());
                result.add(toDto(n.getId(), "contest", c.getId(), c.getName(), c.getStartDate(), c.getEndDate()));
            });
            pnRepo.findByMember_Id(memberId).forEach(n -> {
                Program p = programRepo.getReferenceById(n.getProgram().getId());
                result.add(toDto(n.getId(), "program", p.getId(), p.getTitle(), p.getStart_date(), p.getEnd_date()));
            });
        } else {
            throw new IllegalArgumentException("view 파라미터는 'bookmark' 또는 'notification'이어야 합니다.");
        }

        if (result.isEmpty()) {
            throw new NoRememberException();
        }
        return result;
    }
    private RememberRes toDto(
            Long id, String domain, Long contentId, String title, LocalDate start, LocalDate end
    ) {
        String period = start.format(PERIOD) + " – " + end.format(PERIOD);
        return new RememberRes(id, contentId, domain, title, period);
    }
}
