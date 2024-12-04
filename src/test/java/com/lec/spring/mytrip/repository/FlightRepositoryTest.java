package com.lec.spring.mytrip.repository;

import com.lec.spring.mytrip.domain.Flight;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FlightRepositoryTest {

    @Autowired
    private SqlSession sqlSession;

    @Test
    void testSqlSession() {
        assertNotNull(sqlSession, "SqlSession is null! Check your configuration.");
    }

    @Test
    void testFlightRepository() {
        FlightRepository flightRepository = sqlSession.getMapper(FlightRepository.class);
        assertNotNull(flightRepository, "FlightRepository is null! Check your MyBatis mapper configuration.");
    }

    @Test
    void testGetAllAirports() {
        FlightRepository flightRepository = sqlSession.getMapper(FlightRepository.class);
        assertNotNull(flightRepository, "FlightRepository is null!");

        List<Flight> airports = flightRepository.getAllAirports();
        assertNotNull(airports, "Returned airports list is null!");
        assertFalse(airports.isEmpty(), "Returned airports list is empty!");
        airports.forEach(airport -> System.out.println("Airport: " + airport));
    }

    @Test
    void testGetAllAirportsWithExceptionHandling() {
        FlightRepository flightRepository = sqlSession.getMapper(FlightRepository.class);
        assertNotNull(flightRepository, "FlightRepository is null!");

        try {
            List<Flight> airports = flightRepository.getAllAirports();
            assertNotNull(airports, "Returned airports list is null!");
            assertFalse(airports.isEmpty(), "Returned airports list is empty!");
            airports.forEach(airport -> System.out.println("Airport: " + airport));
        } catch (Exception e) {
            fail("An exception occurred while fetching airports: " + e.getMessage());
        }
    }

    @Test
    void testInsertTestData() {
        sqlSession.update("com.lec.spring.mytrip.repository.FlightRepository.insertTestData");
        System.out.println("Test data inserted successfully.");
    }

    @Test
    void testDeleteTestData() {
        sqlSession.update("com.lec.spring.mytrip.repository.FlightRepository.deleteTestData");
        System.out.println("Test data deleted successfully.");
    }

    @Test
    void testDatabaseConnection() {
        try {
            List<String> airports = sqlSession.selectList("com.lec.spring.mytrip.repository.FlightRepository.getAllAirports");
            assertNotNull(airports, "Database connection failed or returned data is null!");
            System.out.println("Database connection is successful. Retrieved airports: " + airports.size());
        } catch (Exception e) {
            fail("Database connection test failed: " + e.getMessage());
        }
    }
}
