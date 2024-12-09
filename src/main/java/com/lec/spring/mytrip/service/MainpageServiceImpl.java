package com.lec.spring.mytrip.service;

import com.lec.spring.mytrip.repository.MainRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MainpageServiceImpl implements MainpageService {

    private final MainRepository mainRepository;

    @Autowired
    public MainpageServiceImpl(SqlSession sqlSession) {
        mainRepository = sqlSession.getMapper(MainRepository.class);
    }

    @Override
    public Map<String, Object> getMostRecommendedCity() {
        // Repository 호출하여 데이터베이스에서 가장 추천받은 도시 조회
        return mainRepository.findMostRecommendedCity();
    }
}