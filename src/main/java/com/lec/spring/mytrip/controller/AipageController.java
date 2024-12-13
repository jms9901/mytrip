package com.lec.spring.mytrip.controller;

import com.lec.spring.mytrip.domain.City;
import com.lec.spring.mytrip.domain.Question;
import com.lec.spring.mytrip.service.AipageService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/aipage")
public class AipageController {

    private final AipageService aipageService;

    public AipageController(AipageService aipageService) {
        this.aipageService = aipageService;
    }

    @GetMapping("/aipage")
    public String aipage() {
        return "aipage/aipage";
    }


    // 시작 페이지
    @GetMapping("/start")
    public String startQuiz(Model model, HttpSession session, Principal principal) {
        // Principal에서 사용자 로그인 ID(username) 가져오기
        String username = (principal != null) ? principal.getName() : "Anonymous";

        // 디버깅 로그
        System.out.println("Principal username: " + username);

        // 사용자 이름(name) 조회
        String name = null;
        try {
            name = aipageService.findNameByUsername(username);
        } catch (Exception e) {
            System.out.println("Error fetching name for username: " + username);
            e.printStackTrace();
        }

        // 디버깅 로그
        System.out.println("Fetched name: " + name);

        // 사용자 이름과 로그인 ID를 세션에 저장
        session.setAttribute("username", username);
        session.setAttribute("name", name);

        // 질문 데이터를 서비스에서 가져옴
        List<Question> questions = aipageService.getQuestions();
        model.addAttribute("questions", questions); // 질문 데이터를 뷰로 전달

        // 사용자 이름을 모델에 추가 (뷰에서 표시 가능)
        model.addAttribute("username", username);
        model.addAttribute("name", name != null ? name : "Guest");

        return "aipage/aipage"; // 시작 페이지 뷰 반환
    }

    @GetMapping("/username")
    @ResponseBody
    public Map<String, String> getUserName(Principal principal) {
        String username = (principal != null) ? principal.getName() : "Anonymous"; // 로그인 ID(user_username)

        // 로그인 ID로 사용자 이름(user_name) 조회
        String name = aipageService.findNameByUsername(username);

        // 응답 데이터 준비
        Map<String, String> response = new HashMap<>();
        response.put("username", username); // 로그인 ID
        response.put("name", name);         // 사용자 이름
        return response; // JSON 형식으로 반환
    }


    // 질문 데이터를 JSON으로 반환
    @PostMapping("/start")
    @ResponseBody
    public List<Question> getQuestions() {
        return aipageService.getQuestions();
    }

    // 답변 제출 처리
    @PostMapping("/submit")
    @ResponseBody
    public Map<String, Object> submitAnswers(@RequestBody List<String> answers, HttpSession session) {
        session.setAttribute("answers", answers);

        City recommendedCity = aipageService.getRecommendedCity(answers);

        // 사용자 이름 조회
        String username = (String) session.getAttribute("username");
        if (username != null && recommendedCity != null) {
            aipageService.saveUserCityRecord(username, recommendedCity.getCityId());
        }

        // 로그 추가
        System.out.println("Username: " + username);
        System.out.println("Recommended City: " + recommendedCity);

        // 반환할 데이터 구성
        Map<String, Object> response = new HashMap<>();
        response.put("username", username);
        response.put("city", recommendedCity);

        return response;
    }


    // 결과 페이지
    @GetMapping("/result")
    public String showResult(Model model, HttpSession session) {
        City city = (City) session.getAttribute("recommendedCity");
        if (city == null) {
            return "redirect:/aipage/start"; // 추천된 도시가 없으면 시작 페이지로 리다이렉트
        }

        String name = (String) session.getAttribute("name");
        model.addAttribute("city", city);
        model.addAttribute("name", name);
        return "ai/aipage"; // 결과 페이지 뷰 반환
    }
}