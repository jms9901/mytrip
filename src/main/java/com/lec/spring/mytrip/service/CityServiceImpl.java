package com.lec.spring.mytrip.service;

import com.lec.spring.mytrip.domain.City;
import com.lec.spring.mytrip.repository.CityRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    @Autowired
    public CityServiceImpl(SqlSession sqlSession) {
        this.cityRepository = sqlSession.getMapper(CityRepository.class);
    }

    @Override
    public City findCityName(String cityName) {
        if(cityName == null){
            System.out.println("도시 이름 null");
            return null;
        }
        System.out.println("도시 이름 : " + cityName);
        City city = cityRepository.findByCityName(cityName);
        System.out.println("여기는 서비스, 도시 리포 불러왔냐 : " + city);
        return city;
    }
}
