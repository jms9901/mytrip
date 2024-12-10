package com.lec.spring.mytrip.repository;

import com.lec.spring.mytrip.domain.City;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AipageRepositoryTest {

    @Autowired
    private SqlSession sqlSession;

    @Test
    void findCityById() {
        AipageRepository aipageRepository = sqlSession.getMapper(AipageRepository.class);

        City city = aipageRepository.findCityById(1);

        // 반환된 city 값을 로그로 출력
        System.out.println("City: " + city);
    }
}