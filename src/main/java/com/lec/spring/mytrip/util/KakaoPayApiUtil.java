package com.lec.spring.mytrip.util;

import com.lec.spring.mytrip.config.AppConfig;
import com.lec.spring.mytrip.domain.Payment;
import com.lec.spring.mytrip.domain.payment.Response;
import org.hibernate.annotations.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${APP_APIKEY_KAKAOPAY}")
    private static String SECRET_KEY; // 실제 Secret Key 사용
    private static final String READY_URL = "https://open-api.kakaopay.com/online/v1/payment/ready";

    @Autowired
    public KakaoPayApiUtil(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public Response readyToPay(Payment payment) {
        System.out.println("유틸와쓔");
        HttpHeaders headers = createHeaders(); // 헤더 생성
        Map<String, Object> params = createParams(payment); // 파라미터 생성
        Response response = callKakaoPayApi(READY_URL, params, headers, payment);
//        api 호출 결과에 따라 params 에서 필요한 내용들 저장,
        //
        System.out.println("params 레디 : " + params);
        System.out.println("header 레디 : " + headers);


        return response; // API 호출
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "SECRET_KEY " + SECRET_KEY);
        headers.setContentType(MediaType.APPLICATION_JSON);
        System.out.println("header 구성 : " + headers);
        return headers;
    }


    private Map<String, Object> createParams(Payment payment) {
        String timestamp = new java.text.SimpleDateFormat("HHmmss").format(new java.util.Date());
        String order = payment.getUserId() + "_" + payment.getPackageId() + "_" + timestamp;

        payment.setPaymentId(order);
        System.out.println(payment.toString());


        // 요청 파라미터 생성
        Map<String, Object> params = new HashMap<>();
        params.put("cid", CID); // Test 용 가맹점 코드
        params.put("Authorization", "SECRET_KEY " + SECRET_KEY); // 인증 키
        params.put("partner_order_id", order); // 가맹점 주문번호
//        params.put("partner_user_id", payment.getUserId()); // 가맹점 회원 ID
        params.put("partner_user_id", payment.getUserId()); // 가맹점 회원 ID
        params.put("item_name", payment.getPackageTitle()); // 상품명
        params.put("quantity", payment.getUserCount()); // 결제 인원
        params.put("total_amount", payment.getPrice()*payment.getUserCount()); // 상품 총액
        params.put("tax_free_amount", 0); // 비과세 금액 (기본값)
        params.put("approval_url", "http://15.165.181.40:8080/board/city/"
                + payment.getCityId()
                + "/package/detail/"
                + payment.getPackageId() + "?status=success");
        params.put("cancel_url", "http://15.165.181.40:8080/board/city/"
                + payment.getCityId() +
                "/package/detail/" + payment.getPackageId()); // 결제 취소 시 리다이렉트 URL
        params.put("fail_url", "http://15.165.181.40:8080/board/city/"
                + payment.getCityId() +
                "/package/detail/" + payment.getPackageId()); // 결제 실패 시 리다이렉트 URL


        System.out.println("params 구성 : " + params);

        return params;
    }


    private Response callKakaoPayApi(String url, Map<String, Object> params, HttpHeaders headers, Payment payment) {
        System.out.println("api 호출까지");
        System.out.println("params 사용 : " + params);
        System.out.println("header 생성 : " + headers);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(params, headers);
        System.out.println("HttpEntity" + request);
        try {
            ResponseEntity<Map> res = restTemplate.postForEntity(url, request, Map.class);
            // res 확인

            Map<String, Object> responseBody = res.getBody();

            System.out.println(responseBody);

            Response response = new Response();
            response.setResponse(responseBody);
            response.setPayment(payment);
            // Payment 객체에 partner_order_id 설정

            response.getResponse().forEach((k, v) ->
                    System.out.println(k + ":" + v)
            );
            System.out.println(response.getPayment().toString());


            return response;
        } catch (Exception e) {
            throw new RuntimeException("KakaoPay API 호출 중 오류 발생: " + e.getMessage(), e);
        }
    }




    }







