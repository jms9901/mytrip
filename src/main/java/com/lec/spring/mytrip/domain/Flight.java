package com.lec.spring.mytrip.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Flight {
    private int airport_id; // 공항 테이블 ID
    private String airport_code; // 공항 코드
    private String airport_name; // 공항의 이름
    private String airport_city; // 공항의 도시
    private String airport_country; // 공항의 나라
    // 분리하기

    private int search_history_id; // 검색 기록 테이블 ID
    private String qry_url; // 검색 기록 쿼리
    private String start_name; // 출발지
    private String end_name; // 도착지

    private User user; // 검색한자(FK)


} // FLight class end
