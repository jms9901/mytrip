package com.lec.spring.mytrip.controller;

import com.lec.spring.mytrip.domain.FeedValidator;
import com.lec.spring.mytrip.domain.User;
import com.lec.spring.mytrip.domain.UserValidator;
import com.lec.spring.mytrip.service.FeedService;
import com.lec.spring.mytrip.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mypage")
public class MyPageController {

    @Autowired
    private FeedValidator feedValidator;
    private FeedService feedService;
    private UserService userService;

    @Autowired
    public void UserController(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void FeedController (FeedService feedService) {this.feedService = feedService;}

    @Autowired
    public void setFeedValidator(UserValidator userValidator) {
        this.feedValidator = feedValidator;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setValidator(feedValidator);
    }

    // 로그인한 계정 = 마이페이지 소유 계정 확인
    @GetMapping("/mypage/{userId}")
    public String mypageFeed(@PathVariable String userName, Model model) {
        User pageOwner = userService.findByUsername(userName);
        return userName;
    }

    @GetMapping("/mypage/bookMain")
    public String myPage() {
        return "mypage/bookMain";
    }



    @GetMapping("/mypage/bookGuestBook")
    public String myPageGuestBook() {
        return "mypage/bookGuestBook";
    }

}
