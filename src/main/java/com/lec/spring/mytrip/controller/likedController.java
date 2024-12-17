package com.lec.spring.mytrip.controller;

import com.lec.spring.mytrip.domain.City;
import com.lec.spring.mytrip.service.LikedService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    @ResponseBody
    public boolean checkCityLikeStatus(@RequestParam("cityId") int cityId) {
        int target = 1;
        System.out.println("컨트롤러는 옴");
        return likedService.checkLiked(target, cityId);
    }

    // 피드 좋아요 증감
    @GetMapping("/board/post")
    @ResponseBody
    public boolean checkPeedLikeStatus(@RequestParam("postId") int postId) {
        int target = 2;
        System.out.println("컨트롤러는 옴, postId = " + postId);
        return likedService.checkLiked(target, postId);
    }

    // 훼키지 좋아요 증감
    @GetMapping("/board/package")
    @ResponseBody
    public boolean checkPackageLikeStatus(@RequestParam("packageId") int packageId) {
        int target = 3;
        System.out.println("컨트롤러는 옴");
        return likedService.checkLiked(target, packageId);
    }
}
