package com.lec.spring.mytrip.service;

import com.lec.spring.mytrip.domain.City;

import java.util.List;

public interface CityService {
    // 이 도시와 같은 대륙의 도시 조회
    List<City> findCitiesByContinentOfThisCity(int cityId);

    // 현재 도시의 정보 조회
    City findCityById(int cityId);
    City findCityName(String cityName);
}
