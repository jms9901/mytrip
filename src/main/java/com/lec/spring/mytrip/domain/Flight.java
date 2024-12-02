package com.lec.spring.mytrip.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Flight { //클래스 명을 airport로 바꾸는건 어떨까요?
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

    /*
    private int search_history_id; // 검색 기록 테이블 ID
    private String search_history_qry_url; // 검색 기록 쿼리
    private String search_history_start_name; // 출발지
    private String search_history_end_name; // 도착지

    이런 식으로 통일감을 주거나

    변수명은 밑에처럼 언더바 빼고 대문자 표기가 좀 더 자바스럽긴 해요

    private int searchHistoryId; // 검색 기록 테이블 ID
    private String searchHistoryQryUrl; // 검색 기록 쿼리
    private String searchHistoryStartName; // 출발지
    private String searchHistoryEndName; // 도착지

    */

    private User user; // 검색한자(FK)


} // FLight class end
