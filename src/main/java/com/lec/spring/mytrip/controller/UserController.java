package com.lec.spring.mytrip.controller;

import com.lec.spring.mytrip.domain.User;
import com.lec.spring.mytrip.service.UserService;
import com.lec.spring.mytrip.domain.UserValidator;
import com.lec.spring.mytrip.util.U;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public String home(Model model, HttpSession session) {
        User loggedUser = U.getLoggedUser();
        model.addAttribute("user", loggedUser);

        System.out.println("Session ID : " + session.getId());
        System.out.println("Logged User: " + loggedUser);

        // 세션에서 사용자 ID 가져오기
        if (loggedUser != null) {
            System.out.println("Logged User ID: " + loggedUser.getId());
            System.out.println("Logged User Name: " + loggedUser.getName());
        } else {
            System.out.println("No logged user found in session.");
        }

        return "user/home";
    }



    // 로그인 페이지
    @GetMapping("/login")
    public String login(Model model, @RequestParam(value = "username", required = false) String username,
                        @RequestParam(value = "password", required = false) String password) {
        model.addAttribute("user", new User()); // 로그인과 회원가입에서 사용할 빈 유저 객체 추가
        // 사용자 이름 검증
        return "user/login";
    }


    @RequestMapping("/auth")
    @ResponseBody
    public Authentication auth(){
        return SecurityContextHolder.getContext().getAuthentication();
    }



    // 회원가입 처리
    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<Map<String, String>> register(@Valid User user, BindingResult bindingResult) {
        Map<String, String> response = new HashMap<>();

        if (bindingResult.hasErrors()) {
            List<FieldError> errorList = bindingResult.getFieldErrors();
            for (FieldError error : errorList) {
                response.put("error", error.getDefaultMessage());
                return ResponseEntity.badRequest().body(response);
            }
        }

        if (userService.findByUsername(user.getUsername()) != null) {
            response.put("error", "이미 존재하는 ID 입니다.");
            return ResponseEntity.badRequest().body(response);
        }

        int cnt = userService.register(user);
        response.put("result", "success");
        return ResponseEntity.ok(response); // 회원가입 성공 시 성공 응답 반환
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

    // 회원정보 수정
    // 사용자 정보 수정 페이지 요청
    @GetMapping("/editUser")
    public String editUser(Model model, Authentication authentication) {

        // 현재 로그인한 사용자 정보 찾기
        String username = authentication.getName();
        User user = userService.findByUsername(username);

        if (user == null) {
            model.addAttribute("error", "사용자를 찾을 수 없습니다.");
            return "redirect:/mypage/bookMain"; // 사용자 정보를 찾을 수 없으면 마이페이지 메인으로
        }

        model.addAttribute("user", user); // 사용자 정보를 모델에 추가
        return "user/editUser"; // 사용자 정보 수정 페이지 반환
    }




    }



