package com.lec.spring.mytrip.form.flight;

import lombok.Data;

@Data
public class FlightRoundTripInfo {
    //화면에 출력할 값 목록
    String id;          // 항공 id, 디테일에 필요
    String price;       // 가격
    String totalResults; // 총 결과 검색 수
    String departure;   // 출발일시
    String durationInMinutes; //  걸리는 시간(분)
    String arrival; // 도착일시
    String originDisplayCode; // 출발공항코드 ex) ICN
    String destinationDisplayCode; // 도착공항코드 ex) ICN
    String airportName; // 항공사이름

    String originName; // 출발 공항 이름
    String destinationName; // 도착 공항 이름
    String city; // 도착 도시
    String country; // 도착 국가




    String token;       // 디테일 검색에 필요한 토큰 값
    String sessionId;   // 인컴플 호출에 필요한 세션id
    String callStatus ; // 추가 호출 여부

    @Override
    public String toString() {
        return "FlightRoundTripInfo{" +
                "id='" + id + '\'' +
                ", price='" + price + '\'' +
                ", departure='" + departure + '\'' +

                ", token='" + token + '\'' +
                ", sessionId='" + sessionId + '\'' +
                ", callStatus ='" + callStatus  + '\'' +
                '}';
    }

}
