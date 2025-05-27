package com.likelion.demo.domain.remember.service;

import com.likelion.demo.domain.bookmark.repository.ContestBookmarkRepository;
import com.likelion.demo.domain.bookmark.repository.ProgramBookmarkRepository;
import com.likelion.demo.domain.contest.entity.Contest;
import com.likelion.demo.domain.contest.repository.ContestRepository;
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
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class RememberServiceImpl implements RememberService {
    private final ContestBookmarkRepository cbRepo;
    private final ProgramBookmarkRepository pbRepo;
//    private final ContestNotificationRepository cnRepo;
//    private final ProgramNotificationRepository pnRepo;
    private final ContestRepository contestRepo;
    private final ProgramRepository programRepo;

    private static final DateTimeFormatter PERIOD = DateTimeFormatter.ofPattern("yyyy.MM.dd(EEE)", Locale.KOREAN);

    @Override
    public List<RememberRes> getRememberList(Long memberId, String view) {
        List<RememberRes> result = new ArrayList<>();

        if ("bookmark".equalsIgnoreCase(view)) {
            Stream<RememberRes> contestStream = cbRepo.findByMember_Id(memberId).stream()
                    .map(cb -> {
                        var c = cb.getContest();
                        return toDto(
                                cb.getId(),
                                "contest",
                                c.getId(),
                                c.getName(),
                                c.getStartDate(),
                                c.getEndDate()
                        );
                    });
            Stream<RememberRes> programStream = pbRepo.findByMember_Id(memberId).stream()
                    .map(pb -> {
                        var p = pb.getProgram();
                        return toDto(
                                pb.getId(),
                                "program",
                                p.getId(),
                                p.getTitle(),
                                p.getStart_date(),
                                p.getEnd_date()
                        );
                    });
            // 3) 두 스트림을 합쳐 리스트로
            List<RememberRes> bookmarks = Stream
                    .concat(contestStream, programStream)
                    .collect(Collectors.toList());

            result.addAll(bookmarks);
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
