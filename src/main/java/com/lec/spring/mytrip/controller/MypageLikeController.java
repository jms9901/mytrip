package com.lec.spring.mytrip.controller;

import com.lec.spring.mytrip.domain.Board;
import com.lec.spring.mytrip.service.LikedService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/mypage")
public class MypageLikeController {

    private final LikedService likedService;

    public MypageLikeController(LikedService likedService) {
        this.likedService = likedService;
    }

    @GetMapping("/likedposts/{userId}")
    @ResponseBody
    public ResponseEntity<List<Board>> showLikedPosts(@PathVariable Long userId) {
        System.out.println("userId = " + userId);
        List<Board> likedPosts = likedService.getLikedPosts(userId);

        return ResponseEntity.ok(likedPosts);
    }
    @GetMapping("/feedliked/{userId}")
    public String feedLikedview(@PathVariable String userId, Model model){
        return "mypage/MypageLike";
    }
}
