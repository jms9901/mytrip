package com.lec.spring.mytrip.repository;

import com.lec.spring.mytrip.service.AipageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class AipageServiceImplTest {

    @Autowired
    private AipageService aipageService;



    @Test
    void saveUserCityRecord_ShouldInsertDataIntoDatabase() {
        String userName = "user1234";
        int cityId = 4;


        // user_city에 데이터 저장
        aipageService.saveUserCityRecord(userName, cityId);






    }
}
