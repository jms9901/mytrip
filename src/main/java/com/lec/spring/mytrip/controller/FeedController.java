package com.lec.spring.mytrip.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mypage")
public class FeedController {

    @GetMapping("/feedModal")
    public String feedModal() {
        return "mypage/feedModal";
    }
}
