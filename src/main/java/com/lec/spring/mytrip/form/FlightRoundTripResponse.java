package com.lec.spring.mytrip.form;

import lombok.Data;

import java.util.List;

@Data
public class FlightRoundTripResponse {
    private List<FlightRoundTripInfo> flights;
}

