package com.lec.spring.mytrip.repository;


import com.lec.spring.mytrip.domain.City;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")  // 'application-test.properties' 사용
class CityRepositoryTest {

    @Autowired
    private SqlSession sqlSession;

    @Test
    void testDatabaseConnection() throws SQLException {
        Connection connection = sqlSession.getConfiguration().getEnvironment().getDataSource().getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT DATABASE()");
        if (resultSet.next()) {
            String result = resultSet.getString(1);
            assertNotNull(result, "데이터베이스 연결이 실패했습니다.");
            System.out.println("현재 연결된 데이터베이스: " + result);
        } else {
            fail("데이터베이스 이름을 가져올 수 없습니다.");
        }
        resultSet.close();
        statement.close();
        connection.close();
    }


    @Test
    void testFindCityByAnswers() {
        CityRepository cityRepository = sqlSession.getMapper(CityRepository.class);

        City city = cityRepository.findCityByAnswers("a", "b", "a", "b", "a");
        assertNotNull(city, "추천된 도시는 null입니다. 입력 데이터와 매칭되는 데이터가 DB에 있는지 확인하세요.");
        System.out.println("추천된 도시: " + city.getCityName());
    }

}