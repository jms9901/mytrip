package com.lec.spring.mytrip.service;

import com.lec.spring.mytrip.domain.City;
import com.lec.spring.mytrip.repository.AipageRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {

    public final AipageRepository aipageRepository;

    public CityServiceImpl(SqlSession sqlSession) {
        this.aipageRepository = sqlSession.getMapper(AipageRepository.class);
    }

    @Override
    public List<City> findCitiesByContinentOfThisCity(int cityId) {
        return aipageRepository.findContinentByCity(cityId);
    }

    @Override
    public City findCityById(int cityId) {
        return null;
    }
}
