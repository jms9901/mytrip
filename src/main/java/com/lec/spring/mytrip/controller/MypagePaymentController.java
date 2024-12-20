package com.lec.spring.mytrip.controller;

import com.lec.spring.mytrip.domain.Payment;
import com.lec.spring.mytrip.service.PaymentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("bookmain/payment")
public class MypagePaymentController {
    private final PaymentService paymentService;

    public MypagePaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
    // 결제 목록 페이지를 보여주는 메서드
    @GetMapping("/list/{userId}")
    public String showPaymentPage(@PathVariable int userId, Model model) {
        // userId에 해당하는 결제 내역을 가져옴
        List<Payment> payments = paymentService.getPaymentDetails(userId);
        model.addAttribute("payments", payments);

        // 결제 내역을 출력할 뷰를 반환
        return "mypage/myPagePayment";  // 템플릿 경로
    }

    // 결제 목록을 반환하는 API 메서드 (AJAX 요청을 위한 JSON 반환)
    @GetMapping("/list/json/{userId}")
    @ResponseBody
    public List<Payment> getPayments(@PathVariable int userId) {
        // userId에 해당하는 결제 내역을 가져와서 반환
        return paymentService.getPaymentDetails(userId);
    }


}

