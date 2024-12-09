package com.lec.spring.mytrip.service;

import com.lec.spring.mytrip.domain.City;

import java.util.List;
import java.util.Map;

public interface MainpageService {
    // 좋아요가 많은 여행지와 이미지 URL을 Repository에게 요청하여 데이터를 가져온다.

    // 투어패키지 이름과 이미지 URL을 Repository에게 요청한다.

    // 도시 이름, 이미지 URL, 언어, 통화를 Repository에게 요청한다.
    List<City> getMostRecommendedCities();




}
