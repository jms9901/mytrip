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

import java.util.List;

@Controller
@RequestMapping("/aipage")
public class AipageController {

//    private final AipageService aipageService;
//
//    public AipageController(AipageService aipageService) {
//        System.out.println("AipageController() 생성");
//        this.aipageService = aipageService;
//    }
//
//
//    // TODO 1: 사용자가 시작 버튼을 누르면 질문과 보기 데이터를 요청
//    @GetMapping("/start")
//    public String startQuiz(Model model, HttpSession session) {
//        if (session.getAttribute("user") == null) {
//            return "redirect:/login";
//        }
//
//        List<Question> questions = aipageService.getQuestions(); // 질문과 보기를 서비스에서 가져옴
//        model.addAttribute("questions", questions); // 뷰에 질문 데이터를 전달
//        return "quiz/start"; // 질문 페이지 뷰 반환
//    }
//
//    // TODO 2: 사용자가 선택한 보기 값을 Service에 전달하여 저장 요청
//    @PostMapping("/submit")
//    public String submitAnswers(@RequestParam List<String> answers, HttpSession session) {
//        if (session.getAttribute("user") == null) {
//            return "redirect:/login";
//        }
//        aipageService.saveAnswers(answers); // 사용자가 제출한 답변 리스트를 서비스로 전달
//        return "/aipage/result";
//    }
//
//
//    // TODO 3: city_name과 user_name을 사용자에게 보여주기
//    public String showResult(Model model, HttpSession session) {
//        if (session.getAttribute("user") == null) {
//            return "redirect:/login";
//        }
//        City recommendedCity = aipageService.getRecommendedCity();
//        model.addAttribute("city", recommendedCity);
//        return "quiz/result";
//    }
//
//
//    // TODO 4: Home 버튼을 누르면 메인 페이지로 리다이렉트
//    @GetMapping("/home")
//    public String redirectTohome() {
//        return "redirect:/main";
//    }
}
