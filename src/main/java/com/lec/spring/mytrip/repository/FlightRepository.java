package com.lec.spring.mytrip.repository;

import com.lec.spring.mytrip.domain.Flight;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FlightRepository {

    // 모든 공항 데이터를 조회
    List<Flight> getAllAirports();

    // 특정 공항 데이터를 조회
    Flight getAirportById(int airportId);
}
