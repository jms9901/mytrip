package com.lec.spring.mytrip.controller;

import com.lec.spring.mytrip.domain.Payment;
import com.lec.spring.mytrip.domain.payment.Response;
import com.lec.spring.mytrip.service.PaymentService;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String packagePaymentSave(@ModelAttribute Payment payment) {
        System.out.println("수신한 Payment 데이터: " + payment);

        Response response = paymentService.paymentSave(payment);

        // 응답 검증
        if (response == null || response.getResponse() == null) {
            throw new RuntimeException("결제 응답이 null입니다. KakaoPay API 호출 실패");
        }

        // 리다이렉트 URL 확인
        String url = response.getResponse().get("next_redirect_pc_url").toString();
        System.out.println("리다이렉트 URL: " + url);

        return "redirect:" + url;
    }

    //마이페이지 출력
    @PostMapping("")
    public void paymentList(Model model) {
        List<Payment> model_an_ssil_gemen_baggu_yo = paymentService.getPaymentDetails();
        model.addAttribute("payments", model_an_ssil_gemen_baggu_yo);
    }

    //기업페이지 출력

}
