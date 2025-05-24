package com.likelion.demo.domain.programData.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.likelion.demo.domain.programData.entity.Program;
import com.likelion.demo.domain.programData.repository.ProgramRepository;
import com.likelion.demo.domain.programData.web.dto.ProgramDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProgramServiceImple implements ProgramService {

    private final ProgramRepository programRepository;
    private final ObjectMapper objectMapper; // JSON 파싱용


    @Override
    public void importProgramsFromJson()  {
        programRepository.deleteAll();

        try (InputStream inputStream = getClass().getResourceAsStream("/programs.json")) {
            List<ProgramDto> dtos = objectMapper.readValue(inputStream, new TypeReference<List<ProgramDto>>() {});

            for (ProgramDto dto : dtos) {
                // recruitPeriod -> start_date, end_date 분리
                LocalDate startDate = null;
                LocalDate endDate = null;
                if (dto.getRecruitPeriod() != null && dto.getRecruitPeriod().contains("~")) {
                    String[] parts = dto.getRecruitPeriod().split("~");
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    // 요일 부분 제거하는 정규식: 괄호 안 내용 삭제
                    String startPart = parts[0].replaceAll("\\([^)]*\\)", "").trim();
                    String endPart = parts[1].replaceAll("\\([^)]*\\)", "").trim();

                    startDate = LocalDate.parse(parts[0].trim(), formatter);
                    endDate = LocalDate.parse(parts[1].trim(), formatter);
                }

                // gradeGender -> grade 분리
                String grade = null;
                if (dto.getGradeGender() != null && dto.getGradeGender().contains("/")) {
                    grade = dto.getGradeGender().split("/")[0].trim();
                } else {
                    grade = dto.getGradeGender(); // "/" 없으면 전체 사용
                }

                // competencies 0~5개 값 가져오기 (안전하게 get)
                List<Integer> comps = dto.getCompetencies() != null ? dto.getCompetencies() : Collections.emptyList();

                int creativity = getSafeInt(comps, 0);
                int integration = getSafeInt(comps, 1);
                int management = getSafeInt(comps, 2);
                int interpersonal = getSafeInt(comps, 3);
                int globalCommunication = getSafeInt(comps, 4);
                int globalCitizenship = getSafeInt(comps, 5);

                // tags String → List<String> 변환 (쉼표 기준 가정)
                List<String> tagList = new ArrayList<>();
                if (dto.getTags() != null && !dto.getTags().isEmpty()) {
                    tagList = Arrays.stream(dto.getTags().split(","))
                            .map(String::trim)
                            .collect(Collectors.toList());
                }

                Program program = Program.builder()
                        .title(dto.getTitle())
                        .start_date(startDate)
                        .end_date(endDate)
                        .creativity(creativity)
                        .integration(integration)
                        .management(management)
                        .interpersonal(interpersonal)
                        .globalCommunication(globalCommunication)
                        .globalCitizenship(globalCitizenship)
                        .target(dto.getTarget())
                        .grade(grade)
                        .department(dto.getDepartment())
                        .thumbnail_url(dto.getImageUrl())
                        .Link_url(dto.getDetailUrl())
                        .point(dto.getPoint())
                        .tags(tagList)
                        .hit(extractNumberFromDescription(dto.getDescription()))  // description에서 숫자 추출
                        .build();

                programRepository.save(program);
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to import programs from JSON", e);
        }
    }

    private int getSafeInt(List<Integer> list, int index) {
        if (list.size() > index) {
            Integer val = list.get(index);
            return val != null ? val : 0;
        }
        return 0;
    }

    private int extractNumberFromDescription(String description) {
        if (description == null) return 0;
        // 숫자만 매칭해서 가져오기
        Pattern p = Pattern.compile("(\\d+)");
        Matcher m = p.matcher(description);
        if (m.find()) {
            return Integer.parseInt(m.group(1));
        }
        return 0;
    }
}


