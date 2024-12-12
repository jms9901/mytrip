package com.lec.spring.mytrip.repository;

import com.lec.spring.mytrip.domain.Payment;

import java.util.List;
import java.util.Map;

public interface PaymentsRepository {
    // 구성된 payment를 저장
    int paymentSave(Payment payment);

    // 해당 유저의 결제목록 출력
    List<Payment> getPaymentsByUserId(int userId);



}
