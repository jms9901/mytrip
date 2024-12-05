package com.lec.spring.mytrip.controller;

import com.lec.spring.mytrip.domain.User;
import com.lec.spring.mytrip.service.UserService;
import com.lec.spring.mytrip.domain.UserValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

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
    @GetMapping("/home")
    public String home(Model model) {
        return "user/home";
    }
    // 로그인 페이지
    @GetMapping("/login")
    public String login(Model model) {
//        model.addAttribute("user", new User()); // 로그인과 회원가입에서 사용할 빈 유저 객체 추가
        return "user/login";
    }

    // 로그인 처리
    @PostMapping("/login")
    public String loginPost(@Valid User user, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("errors", result.getAllErrors());
            return "user/login";
        }

        // 로그인 로직 구현
        // 예: 사용자가 입력한 이메일과 비밀번호로 인증을 시도합니다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // TODO: 여기에 로그인 성공/실패 처리 로직 추가
        return "redirect:user/home"; // 로그인 성공 시 홈으로 리다이렉트
    }

    // 회원가입 처리
    @PostMapping("/register")
    public String register(@Valid User user, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("errors", result.getAllErrors());

            // 전체 오류 메시지를 flash attribute에 추가
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.user", result);
            redirectAttributes.addFlashAttribute("user", user);

            List<FieldError> errorList = result.getFieldErrors();
            for (FieldError error : errorList) {
                // 가장 처음에 발견된 에러만 담아서 보낸다
                redirectAttributes.addFlashAttribute("error", error.getDefaultMessage());
                break;
            }
            if(userService.findByUsername(user.getUsername()) != null){
                redirectAttributes.addFlashAttribute("error", "이미 존재하는 아이디입니다.");
                redirectAttributes.addFlashAttribute("user", user);
                return "redirect:/user/login";
            }
            if (userService.findByUsername(user.getEmail()) != null) {
                redirectAttributes.addFlashAttribute("error", "이미 존재하는 이메일입니다.");
                redirectAttributes.addFlashAttribute("user", user);
                return "redirect:/user/login";
            }
            return "redirect:/user/login";
        }

        // 사용자명 중복 확인
        if (userService.findByUsername(user.getUsername()) != null) {
            redirectAttributes.addFlashAttribute("error", "이미 존재하는 아이디(username) 입니다.");
            redirectAttributes.addFlashAttribute("user", user);
            return "redirect:/user/login";
        }

        String page = "/user/registerOk";
        int cnt = userService.register(user);
        model.addAttribute("result", cnt);
        return page; // 회원가입 성공 후 로그인 페이지로 리다이렉트
    }


    // 로그인 오류 처리
    @PostMapping("/loginError")
    public String loginError() {
        System.out.println("로그인 error");
        return "user/login";
    }

    // 인증 거부 처리
    @RequestMapping("/rejectAuth")
    public String rejectAuth() {
        return "common/rejectAuth";
    }
}
