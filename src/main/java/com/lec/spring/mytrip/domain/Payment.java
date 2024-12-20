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

//    private int paymentId;              // 결제 정보 ID
//    private LocalDateTime paymentdate;         // 결제 일시
//    private String  paymentStatus;      // 결제 상태 ("결제 완료" / "결제 취소"
//    private int totalCost;              // 결제 관련 인원수 * 일인당 가격 => 총 가격
}
