package com.lec.spring.mytrip.service;

import com.lec.spring.mytrip.domain.City;
import com.lec.spring.mytrip.repository.MainRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lec.spring.mytrip.domain.PackagePost;

import java.util.List;

@Service
public class MainpageServiceImpl implements MainpageService {

    private final MainRepository mainRepository;

    @Autowired
    public MainpageServiceImpl(SqlSession sqlSession) {
        mainRepository = sqlSession.getMapper(MainRepository.class);
    }

    @Override
    public List<PackagePost> getLatestPackages() {
        return mainRepository.findTop10OrderByDateDesc();
    }

    @Override
    public List<City> getMostRecommendedCities() {
        return mainRepository.findMostRecommendedCities();
    }
}
