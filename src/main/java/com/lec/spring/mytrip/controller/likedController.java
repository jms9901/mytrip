package com.lec.spring.mytrip.controller;

import com.lec.spring.mytrip.domain.City;
import com.lec.spring.mytrip.service.LikedService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/likey")
public class likedController {
    private final LikedService likedService;

    public likedController(LikedService likedService) {
        this.likedService = likedService;
    }

    // 도시 좋아요 증감
    @GetMapping("/city")
    @ResponseBody
    public int changeCityLikeStatus(@RequestParam("cityId") int cityId) {
        int target = 1;
        return likedService.changeLikeStatus(target, cityId);
    }

    // 피드 좋아요 증감
    @GetMapping("/post")
    @ResponseBody
    public int changePeedLikeStatus(@RequestParam("postId") int postId) {
        int target = 2;
        return likedService.changeLikeStatus(target, postId);
    }

    // 훼키지 좋아요 증감
    @GetMapping("/package")
    @ResponseBody
    public int changePackageLikeStatus(@RequestParam("packageId") int packageId) {
        int target = 3;
        return likedService.changeLikeStatus(target, packageId);
    }
    @GetMapping("/likedCity")
    @ResponseBody
    public List<City> getLikedCity(@RequestParam("userId") Long userId) {
        return likedService.getLikedCityByUserId(userId);
    }

}
