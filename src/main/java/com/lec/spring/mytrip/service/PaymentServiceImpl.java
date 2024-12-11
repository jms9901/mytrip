package com.lec.spring.mytrip.service;

import com.lec.spring.mytrip.domain.Payment;
import com.lec.spring.mytrip.repository.PackagePostRepository;
import com.lec.spring.mytrip.repository.PaymentsRepository;
import com.lec.spring.mytrip.repository.UserRepository;
import com.lec.spring.mytrip.util.U;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentsRepository paymentsRepository;

    @Autowired
    public PaymentServiceImpl(SqlSession sqlSession) {
        this.paymentsRepository = sqlSession.getMapper(PaymentsRepository.class);
    }


    //결제 저장
    @Override
    public int paymentSave(Payment payment) {
        paymentsRepository.paymentSave(payment);
        return 0;
    }

    //마이/기업페이지 결제 출력
    @Override
    public List<Payment> getPaymentDetails() {
//      현재 접속한 유저 확인. 없으면 nullpointE 던짐
        int userId = Objects.requireNonNull(U.getLoggedUser()).getId();
        List<Payment> paypay = paymentsRepository.getPaymentsByUserId(userId);

        paypay.forEach(e -> {
            e.setTotalPrice(e.getPrice() * e.getUserCount());
            System.out.println(e.getUserCount());
            System.out.println(e.getPrice());
            System.out.println(e.getTotalPrice());
        });

        return paypay;
    }

}
