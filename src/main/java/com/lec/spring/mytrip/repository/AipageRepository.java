package com.lec.spring.mytrip.repository;

import com.lec.spring.mytrip.domain.City;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


public interface AipageRepository {


    // 추천 도시 조회
    City findCityByAnswers(
            @Param("q1Id") String q1Id,
            @Param("q2Id") String q2Id,
            @Param("q3Id") String q3Id,
            @Param("q4Id") String q4Id,
            @Param("q5Id") String q5Id
    );

    //도시별 정보 조회, 그런데 질문값을 빠트린
    City findCityById(int cityId);

}
