package com.lec.spring.mytrip.service;

import com.lec.spring.mytrip.domain.History;

import java.util.List;

public interface HistoryService {
    // 검색 기록 저장
    void saveSearch(History history);

    // 특정 사용자의 최근 검색 기록 조회
    List<History> getSearches(int userId);

    // 특정 검색 기록 삭제
    void deleteSearch(int searchHistoryId);

    // 특정 검색 기록 조회
    History getSearchRecordById(int searchHistoryId);
}
