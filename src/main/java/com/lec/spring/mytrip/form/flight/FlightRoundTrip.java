package com.lec.spring.mytrip.form.flight;

import lombok.Data;

@Data
public class FlightRoundTrip {
    private String fromAirportId;
    private String toAirportId;
    private String departDate;
    private String returnDate;
    private int adultsHeadCnt;
    private int childrenHeadCnt;
    private int infantsHeadCnt;
    private String cabinClass;
}
