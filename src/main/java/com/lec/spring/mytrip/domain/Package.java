package com.lec.spring.mytrip.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Package {
    private Long packageId;          // 패키지 ID
    private Long cityId;             // 도시 ID
    private Long userId;             // 작성자 ID
    private String status;           // 패키지 상태 (예: "승인", "대기", "미승인")
    private String content;          // 패키지 내용
    private LocalDateTime regDate;   // 패키지 등록일
    private String title;            // 패키지 제목
    private int cost;                // 패키지 비용
    private int maxPeople;           // 최대 인원
    private LocalDateTime startDay;  // 시작 일자
    private LocalDateTime endDay;    // 종료 일자
}
