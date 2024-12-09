package com.lec.spring.mytrip.repository;

import com.lec.spring.mytrip.domain.City;
import java.util.List;
import com.lec.spring.mytrip.domain.Package;

public interface MainRepository {

    // 좋아요가 많은 여행지와 관련 데이터를 데이터베이스에서 조회하여 응답한다.

    // 투어패키지 관련 데이터를 데이터베이스에서 조회하여 응답한다.
    List<Package> findTop10IOrderByDateDesc();

    // 전날 기준으로 가장많이 추천받은 도시와 관련 데이터를 데이터베이스에서 조회하여 응답한다.
    List<City> findMostRecommendedCities();


}
