package com.lec.spring.mytrip.controller;

import com.lec.spring.mytrip.domain.Flight;
import com.lec.spring.mytrip.domain.History;
import com.lec.spring.mytrip.form.flight.FlightRoundTrip;
import com.lec.spring.mytrip.form.flight.FlightRoundTripResponse;
import com.lec.spring.mytrip.service.FlightService;
import com.lec.spring.mytrip.service.HistoryService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

// 공항 데이터와 검색 기록을 관리하는 통합 컨트롤러
@Controller
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
    public void search(Model model) {
        // 검색 페이지 처리 로직 추가 가능

        System.out.println("search 컨트롤러");
        List<Flight> airports = flightService.getAllAirports();
        model.addAttribute("airports", airports);
    }

//    // 모든 공항 데이터를 조회하는 엔드포인트
//    @GetMapping("/airports")
//    public String getAllAirports(Model model) {
//        List<Flight> airports = flightService.getAllAirports();
//        model.addAttribute("airports", airports);
//        return "/search";
//    }

    // 검색 결과 페이지를 렌더링하는 엔드포인트
    @PostMapping("/result")
    public void result(@ModelAttribute FlightRoundTrip flightRoundTrip, Model model) {
        // 검색 결과 페이지 처리 로직 추가 가능
        System.out.println("result 컨트롤러");
        List<Flight> airports = flightService.getAllAirports();
        model.addAttribute("airports", airports);

        try {
            FlightRoundTripResponse flightApiResponse = flightService.roundTripApiCall(flightRoundTrip);

            System.out.println("flightApiResponse 생겨먹은거  " + flightApiResponse + "\n");
            System.out.println("flightApiResponse.getFlights() 생겨먹은거  " + flightApiResponse.getFlights() + "\n");

            model.addAttribute("flights", flightApiResponse.getFlights());
            System.out.println("flights 모달 보냄");
        } catch (Exception e) {
            model.addAttribute("error", "항공편 조회 중 오류가 발생했습니다.");
        }
    }

    //incomplete api 추가 요청
    @PostMapping("/result/incomplete")
    public ResponseEntity<FlightRoundTripResponse> incomplete(@RequestBody Map<String, String> sessionId) {
        System.out.println("힘세고 강한 컨트롤러");
        System.out.println(sessionId.get("sessionId"));

        FlightRoundTripResponse incomplete = flightService.Flightincomplete(sessionId.get("sessionId"));
        // api_2 데이터 반환
        System.out.println("오늘도 평화로운 컨트롤러, " + incomplete);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(incomplete);
    }

    // 상세 보기 페이지를 렌더링하는 엔드포인트
    @GetMapping("/detail")
    public String detail() {
        return "flight/detail";
    }

    // 검색 기록을 저장하는 엔드포인트
    @PostMapping("/history/save")
    public void saveSearch(@RequestBody History history) {
        historyService.saveSearch(history);
    }
//    @PostMapping("/history/save")
//    public void saveSearchHistory(@RequestBody History history) {
//        historyService.saveSearch(history);
//
//     // 5개 초과 시 가장 오래된 기록 삭제
//     List<History> userHistory = historyService.getSearches(history.getUserId());
//      if (userHistory.size() > 5) {
//           historyService.deleteSearch(userHistory.get(0).getId());
//       }
//    }

    // 특정 사용자의 최근 검색 기록을 조회하는 엔드포인트 (최대 5개) 띵킹필
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

    @PostMapping("/search/startSearch")
    public String startSearch(@RequestParam String sessionId, Model model) {
        model.addAttribute("sessionId", sessionId);
        return "result"; // result.html 페이지로 이동
    }

    @PostMapping("/result/completeSearch")
    public String completeSearch(@RequestBody Map<String, Object> data, Model model) {
        String itineraryId = (String) data.get("itineraryId");
        String token = (String) data.get("token");
        model.addAttribute("itineraryId", itineraryId);
        model.addAttribute("token", token);
        return "detail"; // detail.html 페이지로 이동
    }





} // end controller
