package com.lec.spring.mytrip.controller;

import com.lec.spring.mytrip.domain.User;
import com.lec.spring.mytrip.service.AdminService;
import com.lec.spring.mytrip.service.UserService;
import com.lec.spring.mytrip.domain.UserValidator;
import com.lec.spring.mytrip.util.U;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final AdminService adminService;
    private UserValidator userValidator;

    @Autowired
    public AdminController(AdminService adminService, UserService userService) {
        this.adminService = adminService;
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

    @GetMapping("/userTables")
    public String userTables(Model model, HttpSession session) {
        User loggedUser = U.getLoggedUser();
        model.addAttribute("user", loggedUser);

        System.out.println("Session ID : " + session.getId());
        System.out.println("Logged User: " + loggedUser);
        return "admin/userTables";
    }

    @GetMapping("/businessTables")
    public String businessTables(Model model, HttpSession session) {
        return "admin/businessTables";
    }

    @GetMapping("/boardTables")
    public String boardTables(Model model, HttpSession session) {
        return "admin/boardTables";
    }

    @GetMapping("/feedTables")
    public String feedTables(Model model, HttpSession session) {
        return "admin/feedTables";
    }

    @GetMapping("/packageAccessTables")
    public String packageAccessTables(Model model, HttpSession session) {
        return "admin/packageAccessTables";
    }

    @GetMapping("/packageStandbyTables")
    public String packageStandbyTables(Model model, HttpSession session) {
        return "admin/packageStandbyTables";
    }

    @GetMapping("/paymentTables")
    public String paymentTables(Model model, HttpSession session) {
        return "admin/paymentTables";
    }

    // 로그인 페이지
    @GetMapping("/adminLogin")
    public String login(Model model) {
        model.addAttribute("user", new User()); // 로그인과 회원가입에서 사용할 빈 유저 객체 추가
        return "admin/adminLogin";
    }

    @RequestMapping("/auth")
    @ResponseBody
    public Authentication auth(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

    // 로그인 오류 처리
    @PostMapping("/loginError")
    public String loginError() {
        System.out.println("로그인 error");
        return "admin/adminLogin";
    }

    // 인증 거부 처리
    @RequestMapping("/rejectAuth")
    public String rejectAuth() {
        return "common/rejectAuth";
    }



}
