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
    private int paymentId; //
    private int userId; // 구매자 id
    private int packageId; // 상품 ID
    private int  userCount; // 인원 수
    private LocalDateTime Date; // 구매한 날짜
    private String  Status; // 구매한 상태

    private String userName;
    private String packageTitle;
    private int price;

    private int totalPrice;
}
