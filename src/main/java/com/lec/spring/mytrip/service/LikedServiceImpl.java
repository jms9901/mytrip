package com.lec.spring.mytrip.service;

import com.lec.spring.mytrip.domain.Board;
import com.lec.spring.mytrip.domain.City;
import com.lec.spring.mytrip.domain.PackagePost;
import com.lec.spring.mytrip.domain.User;
import com.lec.spring.mytrip.repository.CityRepository;
import com.lec.spring.mytrip.repository.LikeRepository;
import com.lec.spring.mytrip.util.LikeUtil;
import com.lec.spring.mytrip.util.U;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LikedServiceImpl implements LikedService {
  private final  LikeRepository likeRepository;
  private final CityRepository cityRepository;

    @Autowired
    private SqlSession sqlSession;


    public LikedServiceImpl(LikeRepository likeRepository, CityRepository cityRepository) {
        this.likeRepository = likeRepository;
        this.cityRepository = cityRepository;
    }

    @Override
    public int changeLikeStatus(int target, int id) {
        //현재 유저는 누구인가
        int userId = LikeUtil.findUserId();
        if (userId != -1) {

            switch (target) {
                case 1 -> {//이 유저가 이 이곳에 좋아요를 했는가?
                    if (likeRepository.checkIfCityLiked(userId, id)) {
                        //그렇다면 좋아요 삭제
                        likeRepository.decrementCityLike(userId, id);
                        //이 곳의 좋아요 총 숫자 리턴
                        return likeRepository.findAllCityLike(id);
                    } else {
                        //아니라면 좋아요 추가
                        likeRepository.incrementCityLike(userId, id);
                        return likeRepository.findAllCityLike(id);
                    }
                }
                case 2 -> {
                    if (likeRepository.checkIfPeedLiked(userId, id)) {
                        //그렇다면 좋아요 삭제
                        likeRepository.decrementPeedLike(userId, id);
                        //이 곳의 좋아요 총 숫자 리턴
                        return likeRepository.findAllPeedLike(id);
                    } else {
                        //아니라면 좋아요 추가
                        likeRepository.incrementPeedLike(userId, id);
                        return likeRepository.findAllPeedLike(id);
                    }
                }
                case 3 -> {
                    if (likeRepository.checkIfPackageLiked(userId, id)) {
                        //그렇다면 좋아요 삭제
                        likeRepository.decrementPackageLike(userId, id);
                        //이 곳의 좋아요 총 숫자 리턴
                        return likeRepository.findAllPackageLike(id);
                    } else {
                        //아니라면 좋아요 추가
                        likeRepository.incrementPackageLike(userId, id);
                        return likeRepository.findAllPackageLike(id);
                    }
                }
                default -> throw new IllegalStateException("대상이 존재하지 않습니다. 대상 id : " + id);
            }

        } else {
            //로그인 하지 않은 상태
            System.out.println("로그인하라고 알림 떠야댐");
            return -1;
        }

    }

    @Override
    public List<City> getLikedCityByUserId(int userId) {
        return cityRepository.findLikedCitiesByUserId(userId);
    }


    public List<Board> getLikedPosts(int userId) {
        System.out.println(likeRepository.findLikedPostsByUser(userId));
        return likeRepository.findLikedPostsByUser(userId);
    }
    // 좋아요한 패키지 리스트
    public List<PackagePost> getLikedPackageDetails(int userId) {
        return likeRepository.getLikedPackageDetails(userId);
    }


    @Override // 좋아요 증감 감소 및 DB 수정
    public Map<String, Object> checkLiked(int target, int id) {
        int userId = LikeUtil.findUserId(); // 로그인 유저 ID 확인
        Map<String, Object> result = new HashMap<>(); // 결과 저장용 Map

        if (userId != -1) {
            switch (target) {
                case 1 -> { // City 좋아요
                    if (likeRepository.checkIfCityLiked(userId, id)) {
                        likeRepository.decrementCityLike(userId, id); // 좋아요 취소
                        result.put("liked", false);
                    } else {
                        likeRepository.incrementCityLike(userId, id); // 좋아요 추가
                        result.put("liked", true);
                    }
                    int totalLikes = likeRepository.findAllCityLike(id); // 총 좋아요 수 조회
                    result.put("totalLikes", totalLikes);
                }
                case 2 -> { // Post 좋아요
                    if (likeRepository.checkIfPeedLiked(userId, id)) {
                        likeRepository.decrementPeedLike(userId, id);
                        result.put("liked", false);
                    } else {
                        likeRepository.incrementPeedLike(userId, id);
                        result.put("liked", true);
                    }
                    int totalLikes = likeRepository.findAllPeedLike(id);
                    result.put("totalLikes", totalLikes);
                }
                case 3 -> { // Package 좋아요
                    if (likeRepository.checkIfPackageLiked(userId, id)) {
                        likeRepository.decrementPackageLike(userId, id);
                        result.put("liked", false);
                    } else {
                        likeRepository.incrementPackageLike(userId, id);
                        result.put("liked", true);
                    }
                    int totalLikes = likeRepository.findAllPackageLike(id);
                    result.put("totalLikes", totalLikes);
                }
                default -> throw new IllegalStateException("대상이 존재하지 않습니다. 대상 id : " + id);
            }
        } else {
            // 로그인하지 않은 상태 처리
            result.put("liked", false);
            result.put("message", "로그인이 필요합니다.");
        }
        return result;
    }

    @Override
    public Map<String, Object> getLikedItems(int userId) {
        // 기존 사용자 좋아요 항목 리스트
        Map<String, Object> likedItems = new HashMap<>();
        likedItems.put("cities", likeRepository.getLikedCityIds(userId));  // 사용자가 좋아요한 도시 ID 목록
        likedItems.put("posts", likeRepository.getLikedPostIds(userId));  // 사용자가 좋아요한 포스트 ID 목록
        likedItems.put("packages", likeRepository.getLikedPackageIds(userId));  // 사용자가 좋아요한 패키지 ID 목록

        // 각 항목별 총 좋아요 수 추가
        Map<String, Integer> totalLikes = new HashMap<>();
        for (Integer cityId : (List<Integer>) likedItems.get("cities")) {
            totalLikes.put("city_" + cityId, likeRepository.getCityLikeCount(cityId)); // 각 도시의 총 좋아요 수
        }
        for (Integer postId : (List<Integer>) likedItems.get("posts")) {
            totalLikes.put("post_" + postId, likeRepository.getPostLikeCount(postId)); // 각 포스트의 총 좋아요 수
        }
        for (Integer packageId : (List<Integer>) likedItems.get("packages")) {
            totalLikes.put("package_" + packageId, likeRepository.getPackageLikeCount(packageId)); // 각 패키지의 총 좋아요 수
        }

        // 좋아요된 항목과 총 좋아요 수를 합쳐 반환
        likedItems.put("totalLikes", totalLikes);

        return likedItems;
    }

}


