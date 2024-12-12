package com.lec.spring.mytrip.util;

import com.lec.spring.mytrip.config.AppConfig;
import com.lec.spring.mytrip.domain.Payment;
import org.hibernate.annotations.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
public class KakaoPayApiUtil {
    private final RestTemplate restTemplate;


    private static final String CID = "TC0ONETIME"; // 테스트용 가맹점 코드
    private static final String SECRET_KEY = "YDEV2E00C3E59BCFC94808617B157D8DF64003292"; // 실제 Secret Key 사용
    private static final String READY_URL = "https://open-api.kakaopay.com/online/v1/payment/ready";

    @Autowired
    public KakaoPayApiUtil(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public Payment readyToPay(Payment payment) {
        HttpHeaders headers = createHeaders(); // 헤더 생성
        Map<String, Object> params = createParams(payment); // 파라미터 생성
        Payment payment1 = callKakaoPayApi(READY_URL, params, headers, payment);
//        api 호출 결과에 따라 params 에서 필요한 내용들 저장,
        //

        return payment1; // API 호출
    }


    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "SECRET_KEY " + SECRET_KEY);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }


    private Map<String, Object> createParams(Payment payment) {
        String timestamp = new java.text.SimpleDateFormat("HHmmss").format(new java.util.Date());
        String order = payment.getUserId() + "_" + payment.getPackageId() + "_" + timestamp;

        payment.setPaymentId(order);



        // 요청 파라미터 생성
        Map<String, Object> params = new HashMap<>();
        params.put("cid", CID); // Test 용 가맹점 코드
        params.put("Authorization", "SECRET_KEY " + SECRET_KEY); // 인증 키
        params.put("partner_order_id", order); // 가맹점 주문번호
        params.put("partner_user_id", payment.getUserId()); // 가맹점 회원 ID
        params.put("item_name", payment.getPackageTitle()); // 상품명
        params.put("quantity", payment.getUserCount()); // 결제 인원
        params.put("total_amount", payment.getPrice()*payment.getUserCount()); // 상품 총액
        params.put("tax_free_amount", 0); // 비과세 금액 (기본값)
        params.put("approval_url", "http://localhost:8081/payment/approve"); // 결제 성공 시 리다이렉트 URL
        params.put("cancel_url", "http://localhost:8081/payment/cancel"); // 결제 취소 시 리다이렉트 URL
        params.put("fail_url", "http://localhost:8081/payment/fail"); // 결제 실패 시 리다이렉트 URL

        return params;
    }


    private Payment callKakaoPayApi(String url, Map<String, Object> params, HttpHeaders headers, Payment payment) {
        try {
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(params, headers);
            ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);
            // res 확인

            // parms값을 빼서 payment에 set으로 뽑아서 저장
            // Payment 객체에 partner_order_id 설정


            return payment;
        } catch (Exception e) {
            throw new RuntimeException("KakaoPay API 호출 중 오류 발생: " + e.getMessage(), e);
        }
    }




    }






