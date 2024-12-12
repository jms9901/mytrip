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
    private int paymentId;
    private int userId;
    private int packageId;
    private int  userCount;
    private LocalDateTime Date;
    private String  Status;        // 결제 상태 ('결제 완료' , '결제 취소')

    private String userName;
    private String packageTitle;
    private int price;

    private int totalPrice;
}
