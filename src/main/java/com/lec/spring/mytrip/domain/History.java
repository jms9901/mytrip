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

    private int searchHistoryId; // 검색 기록 테이블 ID
    private String qryUrl; // 검색 기록 쿼리
    private String startName; // 출발지
    private String endName; // 도착지

} // end History class
