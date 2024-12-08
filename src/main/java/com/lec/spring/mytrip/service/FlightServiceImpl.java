package com.lec.spring.mytrip.service;

import com.lec.spring.mytrip.FlightApiCall;
import com.lec.spring.mytrip.domain.Flight;
import com.lec.spring.mytrip.form.flight.*;
import com.lec.spring.mytrip.repository.FlightRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightServiceImpl implements FlightService {
    private final FlightRepository flightRepository;
    private final FlightApiCall flightApiCall;


    public FlightServiceImpl(SqlSession sqlSession, FlightApiCall flightApiCall) {
        flightRepository = sqlSession.getMapper(FlightRepository.class);
        this.flightApiCall = flightApiCall;
        System.out.println("FlightService() 확인");
    }

    @Override
    public List<Flight> getAllAirports() {
        System.out.println("getAllAirports 서비스 단");
        return flightRepository.getAllAirports();
    }

    @Override
    public FlightRoundTripResponse roundTripApiCall(FlightRoundTrip flightRoundTrip) {
        System.out.println("roundTripApiCall() 서비스 진입 확인");
        try {

            //확인용. 차후 수정
            FlightRoundTripResponse f = flightApiCall.fetchFlightData(flightRoundTrip);
            System.out.println("api에서 받아온  값 확인" + f);
            return f;

//            return flightApiCall.fetchFlightData(flightRoundTrip);
        } catch (Exception e) {
            throw new RuntimeException("항공권 데이터 가져오기 중 오류 발생", e);
        }
    }

    @Override
    public FlightRoundTripResponse incompleteCall(String sessionId) {
        System.out.println("안냐쎄요 월리 너의 수의지롱");

        FlightRoundTripResponse f = flightApiCall.fetchincompleteData(sessionId);
        System.out.println("서비스 단 api에서 받아온  값 확인" + f);
        return f;

//        return flightApiCall.fetchincompleteData(sessionId);
    }

    @Override
    public FlightDetailResponse detailCall(String itineraryId, String token) {
        // API 호출로 사이트별 정보 가져오기
        return flightApiCall.fetchFlightDetail(itineraryId, token);
    }




} // end ServiceImpl class
