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


    @Override
    public boolean checkLiked(int target, int id) {
        //현재 유저는 누구인가
        int userId = LikeUtil.findUserId();
        if (userId != -1) {

            switch (target) {
                case 1 -> {//이 유저가 이 이곳에 좋아요를 했는가?
                    if (likeRepository.checkIfCityLiked(userId, id)) {
                        //그렇다면 좋아요 삭제
                        likeRepository.decrementCityLike(userId, id);
                        //이 곳의 좋아요 총 숫자 리턴
                        return false;
                    } else {
                        //아니라면 좋아요 추가
                        likeRepository.incrementCityLike(userId, id);
                        return true;
                    }
                }
                case 2 -> {
                    if (likeRepository.checkIfPeedLiked(userId, id)) {
                        //그렇다면 좋아요 삭제
                        likeRepository.decrementPeedLike(userId, id);
                        //이 곳의 좋아요 총 숫자 리턴
                        return false;
                    } else {
                        //아니라면 좋아요 추가
                        likeRepository.incrementPeedLike(userId, id);
                        return true;
                    }
                }
                case 3 -> {
                    if (likeRepository.checkIfPackageLiked(userId, id)) {
                        //그렇다면 좋아요 삭제
                        likeRepository.decrementPackageLike(userId, id);
                        //이 곳의 좋아요 총 숫자 리턴
                        return false;
                    } else {
                        //아니라면 좋아요 추가
                        likeRepository.incrementPackageLike(userId, id);
                        return true;
                    }
                }
                default -> throw new IllegalStateException("대상이 존재하지 않습니다. 대상 id : " + id);
            }

        } else {
            //로그인 하지 않은 상태
            System.out.println("로그인하라고 알림 떠야댐");
            return false;
        }

    }

}


