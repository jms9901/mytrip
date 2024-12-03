package com.lec.spring.mytrip.controller;

import com.lec.spring.mytrip.domain.Flight;
import com.lec.spring.mytrip.domain.History;
import com.lec.spring.mytrip.service.FlightService;
import com.lec.spring.mytrip.service.HistoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// 공항 데이터와 검색 기록을 관리하는 통합 컨트롤러
@RestController
@RequestMapping("/flight")
public class FlightController {

    private final FlightService flightService;
    private final HistoryService historyService;

    public FlightController(FlightService flightService, HistoryService historyService) {
        this.flightService = flightService;
        this.historyService = historyService;
    }

    // 검색 페이지를 렌더링하는 엔드포인트
    @GetMapping("/search")
    public void search() {
        // 검색 페이지 처리 로직 추가 가능
    }

    // 모든 공항 데이터를 조회하는 엔드포인트
    @GetMapping("/airports")
    public List<Flight> getAllAirports() {
        return flightService.getAllAirports();
    }

    // 검색 결과 페이지를 렌더링하는 엔드포인트
    @GetMapping("/result")
    public void result() {
        // 검색 결과 페이지 처리 로직 추가 가능
    }

    // 상세 보기 페이지를 렌더링하는 엔드포인트
    @GetMapping("/detail")
    public void detail() {
        // 상세 보기 페이지 처리 로직 추가 가능
    }

    // 검색 기록을 저장하는 엔드포인트
    @PostMapping("/history")
    public void saveSearch(@RequestBody History history) {
        historyService.saveSearch(history);
    }

    // 특정 사용자의 최근 검색 기록을 조회하는 엔드포인트 (최대 5개)
    @GetMapping("/history/user/{userId}")
    public List<History> getSearches(@PathVariable int userId) {
        return historyService.getSearches(userId);
    }

    // 특정 검색 기록을 삭제하는 엔드포인트
    @DeleteMapping("/history/{searchHistoryId}")
    public void deleteSearch(@PathVariable int searchHistoryId) {
        historyService.deleteSearch(searchHistoryId);
    }

    // 특정 검색 기록 ID를 기준으로 검색 기록을 조회하는 엔드포인트
    @GetMapping("/history/{searchHistoryId}")
    public History getSearchRecordById(@PathVariable int searchHistoryId) {
        return historyService.getSearchRecordById(searchHistoryId);
    }
} // end controller
