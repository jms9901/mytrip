package com.lec.spring.mytrip.form.flight;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class FlightDetailTicketInfo {
    String outAirport;  // 출발 공항 id
    String outCity;     // 출발 도시
    String outCountry;  // 출발 국가

    String returnAirport;  // 도착 공항 id
    String returnCity;     // 도착 도시
    String returnCountry;  // 도착 국가

    String outDate;// 가는 편 날짜
    String outTime;// 가는 편 시간
    String outCarrier;// 가는 편 항공사

    String returnDate;// 오는 편 날짜
    String returnTime;// 오는 편 시간
    String returnCarrier;// 오는 편 항공사
}
