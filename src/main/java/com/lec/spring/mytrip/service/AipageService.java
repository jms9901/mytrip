package com.lec.spring.mytrip.service;

import com.lec.spring.mytrip.domain.City;
import com.lec.spring.mytrip.domain.Question;

import java.util.List;

public interface AipageService {

    // 질문 데이터 제공
    List<Question> getQuestions();

    // 추천 도시 반환
    City getRecommendedCity(List<String> answers);

    // 사용자 답변 저장
    void saveUserCityRecord(String userName, int cityId);

}