package com.lec.spring.mytrip.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PaymentServiceImplTest {
    @Autowired
    private PaymentService paymentService;

    @Test
    void paymentSave() {
    }

    @Test
    void getPaymentDetails() {
        paymentService.getPaymentDetails();
    }
}