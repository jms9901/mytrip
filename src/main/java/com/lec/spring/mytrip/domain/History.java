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



    private User user; // 검색한자(FK)

    private int search_history_id; // 검색 기록 테이블 ID
    private String qry_url; // 검색 기록 쿼리
    private String start_name; // 출발지
    private String end_name; // 도착지

} // end History class
