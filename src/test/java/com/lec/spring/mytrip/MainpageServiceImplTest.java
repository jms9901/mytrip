package com.lec.spring.mytrip;

import com.lec.spring.mytrip.domain.City;
import com.lec.spring.mytrip.service.MainpageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MainpageServiceImplTest {

    @Autowired
    private MainpageService mainpageService;

    @Test
    void testGetMostRecommendedCities() {
        List<City> result = mainpageService.getMostRecommendedCities();

        assertNotNull(result, "결과가 null이어서는 안 됩니다.");
        assertFalse(result.isEmpty(), "결과가 비어있어서는 안 됩니다.");
        System.out.println("추천된 도시 리스트: " + result);
    }
}
