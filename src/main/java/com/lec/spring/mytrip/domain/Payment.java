package com.lec.spring.mytrip.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Payment {
    private String paymentId; //  주문번호
    private int userId; // 구매자 id
    private int packageId; // 상품 ID
    private int  userCount; // 인원 수
    private LocalDateTime Date; // 구매한 날짜
    private String  Status; // 구매한 상태

    private String userName; // 구매자 이름
    private String packageTitle; // 패키지 제목
    private int price; // 패키지 가격

    private int totalPrice; // 패키지 총가격
}
