package com.lec.spring.mytrip.form.flight;

import lombok.Data;

import java.util.List;
@Data
public class FlightDetailResponse {
    private List<FlightDetailInfo> details;
}
