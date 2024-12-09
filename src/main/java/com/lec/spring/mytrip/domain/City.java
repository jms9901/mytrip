package com.lec.spring.mytrip.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class City {
    private int cityId;          // 도시 ID
    private String cityName;     // 도시 이름
    private String cityContinent;// 도시 대륙
    private String cityLanguage; // 도시 언어
    private String cityCurrency; // 도시 통화
    private String cityImg;      // 도시 이미지
    private String cityNation;   // 도시 국가
    private String q1Id;         // 첫 번째 질문 답변
    private String q2Id;         // 두 번째 질문 답변
    private String q3Id;         // 세 번째 질문 답변
    private String q4Id;         // 네 번째 질문 답변
    private String q5Id;         // 다섯 번째 질문 답변
}