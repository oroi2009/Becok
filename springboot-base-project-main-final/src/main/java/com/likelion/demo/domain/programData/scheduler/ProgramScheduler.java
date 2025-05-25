//package com.likelion.demo.domain.programData.scheduler;
//
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.likelion.demo.domain.programData.entity.Program;
//import com.likelion.demo.domain.programData.repository.ProgramRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import java.util.List;
//
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//
////비교과 프로그램 크롤러 기능
//@Component
//public class ProgramScheduler {
//    @Autowired
//    private ProgramRepository programRepository;
//
//    @Scheduled(cron = "0 0 2 * * *") // 매일 새벽 2시
//    public void fetchPrograms() {
//        try {
//            String pythonScriptPath = "./scripts/Program-crawl.py";  // scripts 폴더 기준
//            ProcessBuilder pb = new ProcessBuilder("python3", pythonScriptPath);
//            Process process = pb.start();
//
//            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//            StringBuilder jsonOutput = new StringBuilder();
//            String line;
//            while ((line = reader.readLine()) != null) {
//                jsonOutput.append(line);
//            }
//
//            ObjectMapper objectMapper = new ObjectMapper();
//            List<Program> programs = objectMapper.readValue(jsonOutput.toString(), new TypeReference<>() {});
//            programRepository.saveAll(programs);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
