package com.lec.spring.mytrip.service;

import com.lec.spring.mytrip.domain.History;
import com.lec.spring.mytrip.repository.HistoryRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
@Profile("production")
// 검색 기록 관련 비즈니스 로직을 처리하는 서비스 구현 클래스
@Service
public class HistoryServiceImpl implements HistoryService {

    private final HistoryRepository historyRepository;

    // SqlSession을 사용해 HistoryRepository를 초기화
    public HistoryServiceImpl(SqlSession sqlSession) {
        historyRepository = sqlSession.getMapper(HistoryRepository.class);
        System.out.println("HistoryService() 확인");
    }

    // 검색 기록을 저장
    @Override
    public void saveSearch(History history) {
        historyRepository.saveSearch(history);
    }

    // 특정 사용자의 최근 검색 기록 조회 (최대 5개)
    @Override
    public List<History> getSearches(int userId) {
        return historyRepository.getSearches(userId);
    }

    // 특정 검색 기록 삭제
    @Override
    public void deleteSearch(int searchHistoryId) {
        historyRepository.deleteSearch(searchHistoryId);
    }

    // 특정 검색 기록 ID를 기준으로 검색 기록 조회
    @Override
    public History getSearchRecordById(int searchHistoryId) {
        return historyRepository.getSearchRecordById(searchHistoryId);
    }
} // end historyServiceImpl class
