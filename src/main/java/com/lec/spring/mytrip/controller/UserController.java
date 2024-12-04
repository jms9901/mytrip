package com.lec.spring.mytrip.controller;

import com.lec.spring.mytrip.domain.User;
import com.lec.spring.mytrip.service.UserService;
import com.lec.spring.mytrip.domain.UserValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private UserValidator userValidator;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setUserValidator(UserValidator userValidator) {
        this.userValidator = userValidator;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setValidator(userValidator);
    }

    // 로그인 페이지
    @GetMapping("/login")
    public String login(Model model) {
        return "user/login";
    }

    // 회원가입 페이지
    @GetMapping("/register")
    public String register() {
        return "user/register";
    }

    // 회원가입 처리
    @PostMapping("/register")
    public String registerOk(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("errors", result.getAllErrors());
            return "user/register";
        }

        int cnt = userService.register(user);
        model.addAttribute("result", cnt);
        return "user/registerOk";
    }

    // 로그인 오류 처리
    @PostMapping("/loginError")
    public String loginError() {
        return "user/login";
    }

    // 인증 거부 처리
    @RequestMapping("/rejectAuth")
    public String rejectAuth() {
        return "common/rejectAuth";
    }
}
