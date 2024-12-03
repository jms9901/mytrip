package com.lec.spring.mytrip.repository;

import com.lec.spring.mytrip.domain.Flight;


import java.util.List;


public interface FlightRepository {

    // 모든 공항 데이터를 조회
    List<Flight> getAllAirports();


}
