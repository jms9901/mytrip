package com.lec.spring.mytrip.controller;

import com.lec.spring.mytrip.domain.City;
import com.lec.spring.mytrip.repository.LikeRepository;
import com.lec.spring.mytrip.service.CityService;
import com.lec.spring.mytrip.service.LikedService;
import com.lec.spring.mytrip.util.LikeUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/likey")
public class likedController {
    private final LikedService likedService;
    private final LikeRepository likeRepository;

    public likedController(LikedService likedService, LikeRepository likeRepository) {
        this.likedService = likedService;
        this.likeRepository = likeRepository;
    }

    // 도시 좋아요 증감
    @GetMapping("/city")
    @ResponseBody
    public int changeCityLikeStatus(@RequestParam("cityId") int cityId) {
        int target = 1;
        System.out.println("컨트롤러는 옴");
        return likedService.changeLikeStatus(target, cityId);
    }

    // 피드 좋아요 증감
    @GetMapping("/post")
    @ResponseBody
    public int changePeedLikeStatus(@RequestParam("postId") int postId) {
        int target = 2;
        System.out.println("컨트롤러는 옴, postId = " + postId);
        return likedService.changeLikeStatus(target, postId);
    }

    // 훼키지 좋아요 증감
    @GetMapping("/package")
    @ResponseBody
    public int changePackageLikeStatus(@RequestParam("packageId") int packageId) {
        int target = 3;
        System.out.println("컨트롤러는 옴");
        return likedService.changeLikeStatus(target, packageId);
    }

    @GetMapping("/likedCity")
    @ResponseBody
    public List<City> getLikedCity(@RequestParam("userId") int userId) {
        return likedService.getLikedCityByUserId(userId);
    }


    //이하 게시판에서 사용

    // 도시 좋아요 증감
    @GetMapping("/board/city")
    @ResponseBody // JSON 형식으로 반환
    public Map<String, Object> checkCityLikeStatus(@RequestParam("cityId") int cityId) {
        int target = 1;
        System.out.println("도시 좋아요 요청, cityId = " + cityId);
        return likedService.checkLiked(target, cityId);
    }

    // 피드 좋아요 증감
    @GetMapping("/board/post")
    @ResponseBody // JSON 형식으로 반환
    public Map<String, Object> checkPostLikeStatus(@RequestParam("postId") int postId) {
        int target = 2;
        System.out.println("게시글 좋아요 요청, postId = " + postId);
        return likedService.checkLiked(target, postId);
    }

    // 패키지 좋아요 증감
    @GetMapping("/board/package")
    @ResponseBody // JSON 형식으로 반환
    public Map<String, Object> checkPackageLikeStatus(@RequestParam("packageId") int packageId) {
        int target = 3;
        System.out.println("패키지 좋아요 요청, packageId = " + packageId);
        return likedService.checkLiked(target, packageId);
    }



    // 사용자가 좋아요 누른 Id 상태 및 각각의 Id의 총 좋아요 갯수
    @GetMapping("/liked-items")
    @ResponseBody
    public Map<String, Object> getLikedItems(@RequestParam(required = false) Integer cityId,
                                             @RequestParam(required = false) Integer postId,
                                             @RequestParam(required = false) Integer packageId) {

        Map<String, Object> response = new HashMap<>();
        int userId = LikeUtil.findUserId(); // 로그인 유저 ID 확인

        // 각 항목별 총 좋아요 수는 로그인 여부와 관계없이 반환
        if (cityId != null) {
            response.put("cityLikeCount", likeRepository.getCityLikeCount(cityId));
        }
        if (postId != null) {
            response.put("postLikeCount", likeRepository.getPostLikeCount(postId));
        }
        if (packageId != null) {
            response.put("packageLikeCount", likeRepository.getPackageLikeCount(packageId));
        }

        // 로그인된 경우, 사용자가 누른 좋아요 항목 추가 반환
        if (userId != -1) {
            response.put("likedCities", likeRepository.getLikedCityIds(userId));
            response.put("likedPosts", likeRepository.getLikedPostIds(userId));
            response.put("likedPackages", likeRepository.getLikedPackageIds(userId));
        } else {
            response.put("message", "로그인이 필요합니다."); // 비로그인 상태
        }

        return response;
    }
}// end class
