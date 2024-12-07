package com.lec.spring.mytrip.form.flight;

import lombok.Data;

@Data
public class FlightDetailInfo {

    private String siteName;    // 판매 사이트 이름
    private String price;       // 가격
    private String bookingUrl;  // 예약 URL

    @Override
    public String toString() {
        return "FlightDetailInfo{" +
                "siteName='" + siteName + '\'' +
                ", price='" + price + '\'' +
                ", bookingUrl='" + bookingUrl + '\'' +
                '}';
    }

}
