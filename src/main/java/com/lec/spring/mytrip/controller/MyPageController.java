package com.lec.spring.mytrip.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyPageController {

    @GetMapping("/bookMain")
    public String myPage() {
        return "bookMain";
    }
}
