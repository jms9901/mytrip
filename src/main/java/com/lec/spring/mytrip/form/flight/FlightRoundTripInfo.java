package com.lec.spring.mytrip.form.flight;

import lombok.Data;

@Data
public class FlightRoundTripInfo {
    //화면에 출력할 값 목록
    String id;          // 항공 id, 디테일에 필요
    String price;       // 가격
    String departure;   // 출발일시

    String token;       // 디테일 검색에 필요한 토큰 값
    String sessionId;   // 인컴플 호출에 필요한 세션id

    @Override
    public String toString() {
        return "FlightRoundTripInfo{" +
                "id='" + id + '\'' +
                ", price='" + price + '\'' +
                ", departure='" + departure + '\'' +

                ", token='" + token + '\'' +
                ", sessionId='" + sessionId + '\'' +
                '}';
    }
}
