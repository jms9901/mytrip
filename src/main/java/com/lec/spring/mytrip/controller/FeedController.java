package com.lec.spring.mytrip.controller;

import com.lec.spring.mytrip.service.FeedService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mypage")
public class FeedController {
    //   화면 확인
    @GetMapping("/feedModal")
    public String feedModal() {
        return "mypage/feedModal";
    }

    private final FeedService feedService;

    public FeedController(FeedService feedService) {
        System.out.println("FeedController() 생성 확인");
        this.feedService = feedService;
    }


}
