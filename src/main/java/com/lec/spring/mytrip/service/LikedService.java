package com.lec.spring.mytrip.service;

import com.lec.spring.mytrip.domain.Board;
import com.lec.spring.mytrip.domain.City;
import com.lec.spring.mytrip.domain.PackagePost;

import java.util.List;
import java.util.Map;

public interface LikedService {
    //좋아요 증감
    int changeLikeStatus(int target, int id);

    // 특정 유저의 좋아요한 도시
    List<City> getLikedCityByUserId(int userId);

    // 좋아요한 피드 리스트
   List<Board> getLikedPosts(int userId);

    List<PackagePost> getLikedPackageDetails(int userId);

    Map<String, Object> checkLiked(int target, int id);
    // 사용자가 좋아요 누른 상태 로딩 때 표시랑 각각의 id들의 좋아요 갯수
    Map<String, Object> getLikedItems(int userId); // 반환 타입 수정
}
