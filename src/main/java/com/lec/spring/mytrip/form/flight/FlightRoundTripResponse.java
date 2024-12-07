package com.lec.spring.mytrip.form.flight;

import lombok.Data;

import java.util.List;

@Data
public class FlightRoundTripResponse {
    private List<FlightRoundTripInfo> flights;
}

