package com.lec.spring.mytrip.controller;

import com.lec.spring.mytrip.domain.Payment;
import com.lec.spring.mytrip.service.PaymentService;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

// 결제와 관련된 기능은 여기서 처리

@Controller
@RequestMapping("/")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    //결제 저장
    @PostMapping("board/package/payment")
    public String packagePaymentSave(Payment payment) {
        if(1 == paymentService.paymentSave(payment))
            return "/board/payment/success"; //결제 성공 팝업 후 어디로 갈까요
        else return "redirect:/board/payment"; //결제페이지 재이동
    }

    //마이페이지 출력
    @PostMapping("")
    public void paymentList(Model model) {
        List<Payment> model_an_ssil_gemen_baggu_yo = paymentService.getPaymentDetails();
        model.addAttribute("payments", model_an_ssil_gemen_baggu_yo);
    }

    //기업페이지 출력

}
