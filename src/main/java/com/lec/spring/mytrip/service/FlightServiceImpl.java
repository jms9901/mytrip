package com.lec.spring.mytrip.service;

import com.lec.spring.mytrip.domain.Flight;
import com.lec.spring.mytrip.repository.FlightRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class FlightServiceImpl implements FlightService {
    private final FlightRepository flightRepository;

    public FlightServiceImpl(SqlSession sqlSession) {
        flightRepository = sqlSession.getMapper(FlightRepository.class);
        System.out.println("FlightService() 확인");
    }

    @Override
    public List<Flight> getAllAirports() {
        return flightRepository.getAllAirports();
    }


} // end ServiceImpl class
