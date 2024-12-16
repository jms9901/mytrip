package com.lec.spring.mytrip.controller;

import com.lec.spring.mytrip.domain.User;
import com.lec.spring.mytrip.service.UserService;
import com.lec.spring.mytrip.domain.UserValidator;
import com.lec.spring.mytrip.util.U;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String register(@Valid User user, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
            //검증동작
            if(bindingResult.hasErrors()){
                redirectAttributes.addFlashAttribute("username", user.getUsername());
                redirectAttributes.addFlashAttribute("name", user.getName());
                redirectAttributes.addFlashAttribute("email", user.getEmail());

                List<FieldError> errorList = bindingResult.getFieldErrors();
                for(FieldError error : errorList){
                    // 가장 처음에 발견된 에러만 담아서 보낸다
                    redirectAttributes.addFlashAttribute("error", error.getDefaultMessage());
                    break;
                }
                if(errorList.isEmpty()){
                    return "redirect:/user/login";
                }
        }

        // 사용자명 중복 확인
        if (userService.findByUsername(user.getUsername()) != null) {
            redirectAttributes.addFlashAttribute("error", "이미 존재하는 아이디(username) 입니다.");
            redirectAttributes.addFlashAttribute("user", user);
            return "redirect:/user/login";
//            return null;
        }

//        String page = "/user/registerOk";
        int cnt = userService.register(user);
        model.addAttribute("result", cnt);
        return "redirect:/user/login"; // 회원가입 성공 후 로그인 페이지로 리다이렉트
    }

//    @PostMapping("/register")
//    @ResponseBody
//    public Map<String, String> register(@Valid User user, BindingResult bindingResult) {
//        Map<String, String> response = new HashMap<>();
//
//        // 검증 동작
//        if (bindingResult.hasErrors()) {
//            List<FieldError> errorList = bindingResult.getFieldErrors();
//            for (FieldError error : errorList) {
//                // 가장 처음에 발견된 에러만 담아서 보낸다
//                response.put("error", error.getDefaultMessage());
//                break;
//            }
//            return response;
//        }
//
//        // 사용자명 중복 확인
//        if (userService.findByUsername(user.getUsername()) != null) {
//            response.put("error", "이미 존재하는 아이디(username) 입니다.");
//            return response;
//        }
//
//        // 사용자 등록 로직
//        int cnt = userService.register(user);
//        response.put("result", String.valueOf(cnt));
//        return response;
//    }



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
// git push를 위한 주석 241210 10:45