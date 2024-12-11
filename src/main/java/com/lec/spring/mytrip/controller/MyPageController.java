package com.lec.spring.mytrip.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyPageController {

    @GetMapping("/mypage/bookMain")
    public String myPage() {
        return "mypage/bookMain";
    }



    @GetMapping("/mypage/bookGuestBook")
    public String myPageGuestBook() {
        return "mypage/bookGuestBook";
    }

}
