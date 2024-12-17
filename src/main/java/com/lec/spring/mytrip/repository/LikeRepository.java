package com.lec.spring.mytrip.repository;

import com.lec.spring.mytrip.domain.Board;
import com.lec.spring.mytrip.domain.City;
import com.lec.spring.mytrip.domain.PackagePost;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface LikeRepository {
    //도시
    //내가 좋아요를 했는가?
    boolean checkIfCityLiked(int userId, int cityId);
    //좋아요 증가
    int incrementCityLike(int userId, int cityId);
    //좋아요 감소
    int decrementCityLike(int userId, int cityId);

    int findAllCityLike(int userId);

    // 특정 사용자의 좋아요 도시
    List<City> getLikedCityByUserId(@Param("userId")Long userId);

    //피드
    //내가 좋아요를 했는가?
    boolean checkIfPeedLiked(int userId, int peedId);
    //좋아요 증가
    int incrementPeedLike(int userId, int peedId);
    //좋아요 감소
    int decrementPeedLike(int userId, int peedId);

    int findAllPeedLike(int userId);

    // 피드 좋아요 리스트 출력
    List<Board> findLikedPostsByUser(int userId);

    //패키지
    //내가 좋아요를 했는가?
    boolean checkIfPackageLiked(int userId, int packageId);
    //좋아요 증가
    int incrementPackageLike(int userId, int packageId);
    //좋아요 감소
    int decrementPackageLike(int userId, int packageId);

    int findAllPackageLike(int userId);

    List<PackagePost> getLikedPackageDetails(int userId);



    // 도시 좋아요 ID 목록 조회
    List<Integer> getLikedCityIds(@Param("userId") int userId);

    // 게시글 좋아요 ID 목록 조회
    List<Integer> getLikedPostIds(@Param("userId") int userId);

    // 패키지 좋아요 ID 목록 조회
    List<Integer> getLikedPackageIds(@Param("userId") int userId);
}
