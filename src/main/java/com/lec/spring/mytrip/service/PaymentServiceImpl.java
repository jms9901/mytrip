package com.lec.spring.mytrip.service;

import com.lec.spring.mytrip.domain.Payment;

public class PaymentServiceImpl implements PaymentService {

    //결제 저장
    @Override
    public int paymentSave(Payment payment) {
        return 0;
    }

    //마이/기업페이지 결제 출력
    @Override
    public Payment getPaymentDetails(int userId) {
        return null;
    }
}
