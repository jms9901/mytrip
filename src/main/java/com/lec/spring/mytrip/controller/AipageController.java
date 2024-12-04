package com.lec.spring.mytrip.controller;

import com.lec.spring.mytrip.domain.City;
import com.lec.spring.mytrip.domain.Question;
import com.lec.spring.mytrip.service.AipageService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/*
@Controller
@RequestMapping("/aipage")
public class AipageController {

    private final AipageService aipageService;

    public AipageController(AipageService aipageService) {
        this.aipageService = aipageService;
    }

    // 시작 페이지
    @GetMapping("/start")
    public String startQuiz(Model model, HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login"; // 로그인 페이지로 리다이렉트
        }

        List<Question> questions = aipageService.getQuestions();
        model.addAttribute("questions", questions);
        return "aipage/start";
    }

    // 질문 제출 처리
    @PostMapping("/submit")
    public String submitAnswers(
            @RequestParam String answer,
            HttpSession session,
            Model model,
            Principal principal
    ) {
        if (principal == null) {
            return "redirect:/login";
        }

        List<String> answers = (List<String>) session.getAttribute("answers");
        if (answers == null) {
            answers = new ArrayList<>();
        }
        answers.add(answer);
        session.setAttribute("answers", answers);

        if (answers.size() == 5) {
            City recommendedCity = aipageService.getRecommendedCity(answers);
            aipageService.saveUserCityRecord(principal.getName(), recommendedCity.getCityId());

            model.addAttribute("city", recommendedCity);
            model.addAttribute("userName", principal.getName());
            return "aipage/result";
        }

        model.addAttribute("questions", aipageService.getQuestions());
        model.addAttribute("currentIndex", answers.size());
        return "aipage/question";
    }

    // 결과 페이지
    @GetMapping("/result")
    public String showResult(Model model, HttpSession session) {
        City city = (City) session.getAttribute("recommendedCity");
        if (city == null) {
            return "redirect:/aipage/start";
        }
        model.addAttribute("city", city);
        return "aipage/result";
    }
}
*/
@Controller
@RequestMapping("/aipage")
public class AipageController {

    private final AipageService aipageService;

    public AipageController(AipageService aipageService) {
        this.aipageService = aipageService;
    }

    // 시작 페이지
    @GetMapping("/start")
    public String startQuiz(Model model, HttpSession session) {
        // 질문 데이터를 서비스에서 가져옴
        List<Question> questions = aipageService.getQuestions();
        model.addAttribute("questions", questions); // 질문 데이터를 뷰로 전달
        return "aipage/start"; // 시작 페이지 뷰 반환
    }

    // 질문 제출 처리
    @PostMapping("/submit")
    public String submitAnswers(
            @RequestParam String answer,
            HttpSession session,
            Model model
    ) {
        // 세션에 답변 리스트 저장
        List<String> answers = (List<String>) session.getAttribute("answers");
        if (answers == null) {
            answers = new ArrayList<>();
        }
        answers.add(answer);
        session.setAttribute("answers", answers);

        // 답변이 5개인 경우 결과 페이지로 이동
        if (answers.size() == 5) {
            City recommendedCity = aipageService.getRecommendedCity(answers);
            model.addAttribute("city", recommendedCity);
            return "aipage/result"; // 결과 페이지 뷰 반환
        }

        // 다음 질문 페이지로 이동
        model.addAttribute("questions", aipageService.getQuestions());
        model.addAttribute("currentIndex", answers.size());
        return "aipage/question"; // 질문 페이지 뷰 반환
    }

    // 결과 페이지
    @GetMapping("/result")
    public String showResult(Model model, HttpSession session) {
        City city = (City) session.getAttribute("recommendedCity");
        if (city == null) {
            return "redirect:/aipage/start"; // 추천된 도시가 없으면 시작 페이지로 리다이렉트
        }
        model.addAttribute("city", city);
        return "aipage/result"; // 결과 페이지 뷰 반환
    }
}