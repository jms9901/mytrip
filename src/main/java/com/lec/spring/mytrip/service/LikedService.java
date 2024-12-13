package com.lec.spring.mytrip.service;

import com.lec.spring.mytrip.domain.Board;
import com.lec.spring.mytrip.domain.City;

import java.util.List;

public interface LikedService {
    //좋아요 증감
    int changeLikeStatus(int target, int id);

    // 특정 유저의 좋아요한 도시
    List<City> getLikedCityByUserId(Long userId);

    // 좋아요한 피드 리스트
   List<Board> getLikedPosts(Long userId);


}
