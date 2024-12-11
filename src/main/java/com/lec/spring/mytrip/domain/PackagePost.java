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
public class PackagePost {
    private int packageId;         // 패키지 ID
    private int cityId;            // 도시 ID
    private int userId;            // 작성자 ID
    private String status;          // 패키지 상태 (승인, 대기, 미승인 등)
    private String content;         // 패키지 내용
    private LocalDateTime regDate;  // 패키지 등록 날짜
    private String title;           // 패키지 제목
    private int cost;               // 패키지 비용
    private int maxPeople;          // 최대 참여 가능 인원
    private LocalDateTime startDay; // 패키지 시작 날짜
    private LocalDateTime endDay;   // 패키지 종료 날짜
    private String userName;
}
