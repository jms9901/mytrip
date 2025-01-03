package com.lec.spring.mytrip.repository;

import com.lec.spring.mytrip.domain.Payment;

import java.util.List;

public interface PaymentsRepository {
    // 구성된 payment를 저장
    int paymentSave(Payment payment);

    // 해당 유저의 결제목록 출력
    List<Payment> getPaymentsByUserId(int userId);

    // 해당 기업회원이 등록한 모든 패키지에 관한 모든 결제 내역
    List<Payment> getPaymentByCompanyId(int userId);
}
