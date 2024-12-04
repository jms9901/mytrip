package com.lec.spring.mytrip.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class History {

    private User user; // 검색한 사용자 (FK)
    private int searchHistoryId; // 검색 기록 테이블 ID
    private String qryUrl; // 검색 기록 쿼리 URL
    private String startName; // 출발지
    private String endName; // 도착지

    // userId를 직접 가져오기 위한 메서드
//    public int getUserId() {
//        return user.getUserId(); // User 객체의 userId 반환
//    }
}