package com.lec.spring.mytrip.service;

import com.lec.spring.mytrip.domain.Payment;
import com.lec.spring.mytrip.domain.User;
import com.lec.spring.mytrip.domain.payment.Response;
import com.lec.spring.mytrip.repository.PackagePostRepository;
import com.lec.spring.mytrip.repository.PaymentsRepository;
import com.lec.spring.mytrip.repository.UserRepository;
import com.lec.spring.mytrip.util.KakaoPayApiUtil;
import com.lec.spring.mytrip.util.U;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentsRepository paymentsRepository;
    private final KakaoPayApiUtil kakaoPayApiUtil;


    @Autowired
    public PaymentServiceImpl(SqlSession sqlSession, KakaoPayApiUtil kakaoPayApiUtil) {
        this.paymentsRepository = sqlSession.getMapper(PaymentsRepository.class);
        this.kakaoPayApiUtil = kakaoPayApiUtil;
    }


    //결제 저장
    @Override
    public Response paymentSave(Payment payment) {
        System.out.println("싸-비쓰 왔슈");
        try {
//            payment.setUserId(U.getLoggedUser().getId());
            User user = User.builder()
                    .id(1)
                    .name("이경원")
                    .email("wonwon123123@naver.com")
                    .build();
            payment.setUserId(user.getId());
            // KakaoPay API 호출
            if (payment == null) {
                System.out.println("Payment 객체가 null입니다.");
                return null;
            }
            System.out.println("Payment 객체 디버깅: " + payment);
            Response response = kakaoPayApiUtil.readyToPay(payment);
            if(response.getResponse().get("tid") != null){
                int i = paymentsRepository.paymentSave(response.getPayment());
            };

            return response;
        } catch (Exception e) {
            e.printStackTrace();
            // 실패 시 0 반환
            return null;
        }
    }
    //마이/기업페이지 결제 출력
    @Override
    public List<Payment> getPaymentDetails(int userId) {
        System.out.println("결제정보"+paymentsRepository.getPaymentsByUserId(userId));

        return paymentsRepository.getPaymentsByUserId(userId);
    }





}
