package com.lec.spring.mytrip.service;

import com.lec.spring.mytrip.util.FlightApiCall;
import com.lec.spring.mytrip.domain.Flight;
import com.lec.spring.mytrip.form.flight.*;
import com.lec.spring.mytrip.repository.FlightRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

            // 전체 데이터 개수 저장
            int totalFlights = f.getFlights() != null ? f.getFlights().size() : 0;

            if (f.getFlights() != null) {
                List<FlightRoundTripInfo> validFlights = f.getFlights().stream()
                        .filter(flight -> flight != null
                                && flight.getOutDeparture() != null && !flight.getOutDeparture().isEmpty()
                                && flight.getReturnDeparture() != null && !flight.getReturnDeparture().isEmpty()
                                && flight.getOriginDisplayCode() != null && !flight.getOriginDisplayCode().isEmpty()
                                && flight.getDestinationDisplayCode() != null && !flight.getDestinationDisplayCode().isEmpty())
                        .toList();

                // 유효한 데이터 설정
                f.setFlights(validFlights);

                // 걸러진 데이터 개수 확인
                int filteredCount = totalFlights - validFlights.size();
                System.out.println("걸러진 데이터 개수: " + filteredCount);

                if (validFlights.isEmpty()) {
                    throw new IllegalArgumentException("모든 항공편 데이터가 유효하지 않습니다.");
                }
            } else {
                f.setFlights(new ArrayList<>()); // flights가 null인 경우 빈 리스트 설정
            }

            return f;

        } catch (Exception e) {
            throw new RuntimeException("항공권 데이터 가져오기 중 오류 발생", e);
        }
    }

    @Override
    public FlightRoundTripResponse incompleteCall(String sessionId) {
        System.out.println("안냐쎄요 월리 너의 수의지롱");
        try {
            FlightRoundTripResponse f = flightApiCall.fetchincompleteData(sessionId);
            System.out.println("서비스 단 api에서 받아온  값 확인" + f);
            // 전체 데이터 개수 저장
            int totalFlights = f.getFlights() != null ? f.getFlights().size() : 0;
            // 데이터 검증 및 필터링
            if (f.getFlights() != null) {
                List<FlightRoundTripInfo> validFlights = f.getFlights().stream()
                        .filter(flight -> flight != null
                                && flight.getOutDeparture() != null && !flight.getOutDeparture().isEmpty()
                                && flight.getReturnDeparture() != null && !flight.getReturnDeparture().isEmpty()
                                && flight.getOriginDisplayCode() != null && !flight.getOriginDisplayCode().isEmpty()
                                && flight.getDestinationDisplayCode() != null && !flight.getDestinationDisplayCode().isEmpty())
                        .toList();

                f.setFlights(validFlights);

                // 걸러진 데이터 개수 확인
                int filteredCount = totalFlights - validFlights.size();
                System.out.println("전체 데이터 수: " + totalFlights);
                System.out.println("유효 데이터 수: " + validFlights.size());
                System.out.println("걸러진 데이터 수: " + filteredCount);

                if (validFlights.isEmpty()) {
                    throw new IllegalArgumentException("모든 항공편 데이터가 유효하지 않습니다.");
                }
            } else {
                f.setFlights(new ArrayList<>()); // flights가 null인 경우 빈 리스트 설정
            }

            return f;

            }catch (Exception e) {
            throw new RuntimeException("불완전 데이터 가져오기 중 오류 발생", e);
        }
    }


    @Override
    public FlightDetailResponse detailCall(String itineraryId, String token) {
        // API 호출로 사이트별 정보 가져오기
        return flightApiCall.fetchFlightDetail(itineraryId, token);
    }




} // end ServiceImpl class
