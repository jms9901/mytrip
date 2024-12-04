package com.lec.spring.mytrip;

import com.lec.spring.mytrip.domain.Flight;
import com.lec.spring.mytrip.repository.FlightRepository;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MytripApplicationTests {

    @Autowired
    private SqlSession sqlSession;

    @Test
    void test1() {
        FlightRepository flightRepository = sqlSession.getMapper(FlightRepository.class);

        flightRepository.getAllAirports().forEach(System.out::println);
    }

}
