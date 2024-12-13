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
    private int airportId; // 공항 테이블 ID
    private String airportCode; // 공항 코드
    private String airportName; // 공항의 이름
    private String airportCity; // 공항의 도시
    private String airportCountry; // 공항의 나라


} // FLight class end
