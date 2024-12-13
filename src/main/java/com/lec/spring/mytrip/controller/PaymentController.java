package com.lec.spring.mytrip.controller;

import com.lec.spring.mytrip.domain.Payment;
import com.lec.spring.mytrip.domain.payment.Response;
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
    public String packagePaymentSave() {
        Payment payment = Payment.builder()
//                paymentId=null,
                .paymentId(null)
//                userId=0,
                .userId(2)
//                packageId=0,
                .packageId(2)
//                userCount=0,
                .userCount(4)
//                Date=null,
                .Date(null)
//                Status=null,
                .Status(null)
//                userName=null,
                .userName("qjatns777")
//                packageTitle=null,
                .packageTitle("그럼요 당연하죠 네네칰흰")
//                price=0,
                .price(39800)
//                totalPrice=0
                .totalPrice(0)
                .cityId(1)
                .build();

        System.out.println(payment);
        Response response = paymentService.paymentSave(payment);
        String url = response.getResponse().get("next_redirect_pc_url").toString();
        System.out.println(url);
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
