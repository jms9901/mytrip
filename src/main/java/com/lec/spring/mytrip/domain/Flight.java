package com.lec.spring.mytrip.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Flight {
    private int airport_id; // 공항 테이블 ID
    private String airport_code; // 공항 코드
    private String airport_name; // 공항의 이름
    private String airport_city; // 공항의 도시
    private String airport_country; // 공항의 나라


} // FLight class end
