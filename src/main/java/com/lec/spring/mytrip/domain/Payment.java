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
    private int userId;
    private int packageId;
    private int userCount;
    private LocalDateTime Date;
    private String  Status;
    private String userName;
    private String packageTitle;
    private int packageCost;
    private int price;
    private int totalPrice;

    private String paymentId; //  주문번호
    private int cityId; //도시 id. 결제 후 이동 페이지에 필요
}
