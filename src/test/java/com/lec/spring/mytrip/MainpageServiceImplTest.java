package com.lec.spring.mytrip;

import com.lec.spring.mytrip.service.MainpageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class MainpageServiceImplTest {

    @Autowired
    private MainpageService mainpageService;

    @Test
    void testGetMostRecommendedCity() {
        // WHEN: 서비스 메서드 호출
        Map<String, Object> result = mainpageService.getMostRecommendedCity();

        // THEN: 결과 검증
        assertNotNull(result, "결과가 null이어서는 안 됩니다.");
        System.out.println("추천받은 도시: " + result);
    }
}
