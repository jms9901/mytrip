package com.lec.spring.mytrip.repository;

import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LikeRepositoryTest {
    @Autowired
    private SqlSession sqlSession;

    @Test
    void checkIfCityLiked() {
        LikeRepository likeRepository = sqlSession.getMapper(LikeRepository.class);

        int userId = 1;
        int cityId = 2;

        System.out.println("도시 좋아요를 눌렀는가? " + (likeRepository.checkIfCityLiked(userId, cityId)));

    }

    @Test
    void incrementCityLike() {
        LikeRepository likeRepository = sqlSession.getMapper(LikeRepository.class);

        int userId = 1;
        int cityId = 2;

        System.out.println("도시 좋아요 " + (likeRepository.incrementCityLike(userId, cityId)));
    }

    @Test
    void decrementCityLike() {
        LikeRepository likeRepository = sqlSession.getMapper(LikeRepository.class);

        int userId = 1;
        int cityId = 2;

        System.out.println("도시 싫어요 " + (likeRepository.decrementCityLike(userId, cityId)));
    }
}