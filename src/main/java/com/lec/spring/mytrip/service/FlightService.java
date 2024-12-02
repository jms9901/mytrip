package com.lec.spring.mytrip.service;

import com.lec.spring.mytrip.domain.Flight;

import java.util.List;

public interface FlightService {
    // 모든 공항 데이터 조회
    List<Flight> getAllAirports();


    // 특정 공항 데이터 조회
    Flight getAirportById(int airportId);
}