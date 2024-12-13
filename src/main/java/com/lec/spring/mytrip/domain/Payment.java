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
    private int paymentId;              // 결제 정보 ID
    private int userId;                 // 사용자 ID
    private int packageId;              // 패키지 ID
    private int  userCount;             // 결제 관련 인원수
    private LocalDateTime paymentdate;         // 결제 일시
    private String  paymentStatus;      // 결제 상태 ("결제 완료" / "결제 취소"

    private String userName;            // 사용자 이름
    private String packageTitle;        // 패키지 이름
    private int price;                  // 가격

    private int totalCost;              // 결제 관련 인원수 * 일인당 가격 => 총 가격
    private int cityId;                 // 도시 ID
}
