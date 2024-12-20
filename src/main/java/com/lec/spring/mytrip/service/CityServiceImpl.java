package com.lec.spring.mytrip.service;

import com.lec.spring.mytrip.domain.City;
import com.lec.spring.mytrip.repository.CityRepository;
import com.lec.spring.mytrip.repository.AipageRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;
    private final AipageRepository aipageRepository;

    // API 이름 ↔ DB 이름 매핑
    private static final Map<String, String> API_TO_DB_NAME_MAP = new HashMap<>();

    static {
        API_TO_DB_NAME_MAP.put("나트랑 (나짱) 캄란", "나트랑");
        API_TO_DB_NAME_MAP.put("덴파사르", "덴파사");
        API_TO_DB_NAME_MAP.put("타이페이", "타이베이");
        API_TO_DB_NAME_MAP.put("창춘(장춘)", "지린");
        API_TO_DB_NAME_MAP.put("뉴델리", "델리");
        API_TO_DB_NAME_MAP.put("로스앤젤레스", "로스엔젤레스");
        API_TO_DB_NAME_MAP.put("호놀룰루", "하와이");
        API_TO_DB_NAME_MAP.put("타무닝(괌)", "괌");
        API_TO_DB_NAME_MAP.put("밴쿠버", "벤쿠버");
    }

    @Autowired
    public CityServiceImpl(SqlSession sqlSession) {
        this.cityRepository = sqlSession.getMapper(CityRepository.class);
        this.aipageRepository = sqlSession.getMapper(AipageRepository.class);
    }

    // API 도시 이름 → DB 도시 이름 변환
    private String convertApiNameToDbName(String apiName) {
        return API_TO_DB_NAME_MAP.getOrDefault(apiName, apiName); // 매핑되지 않은 경우 원래 이름 반환
    }

    @Override
    public City findCityName(String cityName) {
        if (cityName == null) {
            System.out.println("도시 이름 null");
            return null;
        }
        System.out.println("API 도시 이름 : " + cityName);

        // API 이름을 DB 이름으로 변환
        String dbName = convertApiNameToDbName(cityName);
        System.out.println("DB에서 검색할 도시 이름 : " + dbName);

        // DB에서 도시 정보 조회
        City city = cityRepository.findByCityName(dbName);
        System.out.println("서비스에서 가져온 도시 정보 : " + city);

        return city;
    }
    public List<City> findCitiesByContinentOfThisCity(int cityId) {
        return aipageRepository.findContinentByCity(cityId);
    }

    @Override
    public City findCityById(int cityId) {
        return null;
    }
}
