package com.lec.spring.mytrip.service;

import com.lec.spring.mytrip.domain.Flight;
import com.lec.spring.mytrip.form.FlightRoundTrip;
import com.lec.spring.mytrip.form.FlightRoundTripResponse;

import java.util.List;

public interface FlightService {
    // 모든 공항 데이터 조회
    List<Flight> getAllAirports();

    FlightRoundTripResponse roundTripApiCall(FlightRoundTrip flightRoundTrip);

    List<Flight> Flightincomplete(String sessionId);
}