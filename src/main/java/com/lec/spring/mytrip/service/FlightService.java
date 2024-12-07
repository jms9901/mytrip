package com.lec.spring.mytrip.service;

import com.lec.spring.mytrip.domain.Flight;
import com.lec.spring.mytrip.form.flight.FlightDetailResponse;
import com.lec.spring.mytrip.form.flight.FlightRoundTrip;
import com.lec.spring.mytrip.form.flight.FlightRoundTripResponse;

import java.util.List;

public interface FlightService {
    // 모든 공항 데이터 조회
    List<Flight> getAllAirports();

    FlightRoundTripResponse roundTripApiCall(FlightRoundTrip flightRoundTrip);

    FlightRoundTripResponse Flightincomplete(String sessionId);

    FlightDetailResponse fetchFlightDetail(String itineraryId, String token);



}