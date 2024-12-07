package com.lec.spring.mytrip.form.flight;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class FlightRoundTripInfo {
    //화면에 출력할 값 목록
    String id;          // 항공 id, 디테일에 필요
    String price;       // 가격

    String outDeparture;   // 가는 편 출발일시
    String returnDeparture;   // 오는 편 출발일시
    String outArrival; // 가는 편 도착일시
    String returnArrival; // 오는 편 도착일시

    String outDurationInMinutes; // 가는 편 걸리는 시간
    String returnDurationInMinutes; // 오는 편 걸리는 시간


    String originDisplayCode; // 출발공항코드 ex) ICN
    String originName; // 출발 공항 이름
    String destinationDisplayCode; // 도착공항코드 ex) ICN
    String destinationName; // 도착 공항 이름

    String outAirportName; // 출발 항공사이름
    String returnAirportName; // 도착 항공사이름
    String outLogoUrl;  // 출발 항공사 로고
    String returnLogoUrl;  // 도착 항공사 로고

    String outCity; // 출발 도시
    String outCountry; // 출발 국가
    String returnCity; // 출발 도시
    String returnCountry; // 출발 국가


    String token;       // 디테일 검색에 필요한 토큰 값
    String sessionId;   // 인컴플 호출에 필요한 세션id
    String callStatus ; // 추가 호출 여부
}
