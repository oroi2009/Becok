package com.likelion.demo.domain.recommendation.engin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class GptClient {
    //외부 api요청에 사용하는 HTTP 클라이언트
    private final RestTemplate restTemplate;

    private static final String API_URL = "https://api.openai.com/v1/chat/completions";
    private static final String API_KEY = "OPENAI_API_KEY";
    public String requestRecommendation(String prompt) {
        //HTTP 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", API_KEY);

        Map<String, Object> body = Map.of(
                "model", "gpt-3.5-turbo",
                "messages", List.of(
                        Map.of("role", "user", "content", prompt)
                ), //대화 메시지 형식
                "temperature", 0.7 //창의성 조절
        );
        //headers와 body를 하나의 HTTP 요청으로 병합
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
        //호출할 주소, Post방식, 앞서 만든 요청, 응답 String(json) 받는다.
        ResponseEntity<String> response = restTemplate.exchange(API_URL, HttpMethod.POST, entity, String.class);
        return response.getBody();
    }
}
