package com.lec.spring.mytrip.service;

import com.lec.spring.mytrip.domain.Payment;

import java.util.List;

public interface PaymentService {
    //결제 저장
    //구성된 정보를 받아 성공 실패 여부 리턴
    int paymentSave(Payment payment);

    // 마이/기업페이지 출력
    // 유저 정보를 받아 payment 정보 리턴
    List<Payment> getPaymentDetails();

    // kakaoPay
    String readyToPay();
}
