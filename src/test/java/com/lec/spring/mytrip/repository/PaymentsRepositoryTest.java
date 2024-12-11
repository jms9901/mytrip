package com.lec.spring.mytrip.repository;

import com.lec.spring.mytrip.domain.Payment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PaymentsRepositoryTest {

    @Autowired
    private PaymentsRepository paymentsRepository;

    @Test
    void paymentSave() {
        int userId = 1;
        int packageId = 4;
        int userCount = 3;
        String status = "결제완료";
        Payment payment = new Payment();
        payment.setUserId(userId);
        payment.setPackageId(packageId);
        payment.setUserCount(userCount);
        payment.setStatus(status);
        paymentsRepository.paymentSave(payment);
    }

    @Test
    void getPaymentByUserId() {
        System.out.println("왜안나와");
        paymentsRepository.getPaymentsByUserId(1).forEach(System.out::println);
    }
}