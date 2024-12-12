package com.lec.spring.mytrip.controller;

import com.lec.spring.mytrip.domain.*;
import com.lec.spring.mytrip.service.AdminService;
import com.lec.spring.mytrip.service.UserService;
import com.lec.spring.mytrip.util.U;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        model.addAttribute("adminUser", loggedUser);

        System.out.println("Session ID : " + session.getId());
        System.out.println("Logged User: " + loggedUser);

        List<User> users = adminService.findByAuthorityRoleUser("ROLE_USER");
        model.addAttribute("users", users);

        return "admin/userTables";
    }

    @PostMapping("/login")
    public void adminLogin(){}

    @PostMapping("/deleteuser")
    @ResponseBody
    public ResponseEntity<String> deleteUser(@RequestParam("userId") int userId) {
        try {
            adminService.deleteUser(userId); // 서비스 메서드 호출
            System.out.println("User with ID " + userId + " deleted successfully.");
            return ResponseEntity.ok("User deleted successfully!");
        } catch (Exception e) {
            System.err.println("Error deleting user: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete user.");
        }
    }

    @GetMapping("/businessTables")
    public String businessTables(Model model, HttpSession session) {
        List<User> businessUsers = adminService.findByAuthorityRoleBusiness("ROLE_BUSINESS");
        model.addAttribute("businessUsers", businessUsers);
        return "admin/businessTables";
    }

    @GetMapping("/boardTables")
    public String boardTables(Model model, HttpSession session) {
        List<Board> boards = adminService.findByBoardCategory("소모임");
        model.addAttribute("boards", boards);
        return "admin/boardTables";
    }

    @GetMapping("/feedTables")
    public String feedTables(Model model, HttpSession session) {
        List<Board> feeds = adminService.findByFeedCategory("피드");
        model.addAttribute("feeds", feeds);
        return "admin/feedTables";
    }

    @PostMapping("/deletePost")
    @ResponseBody
    public ResponseEntity<String> deletePost(@RequestParam("boardId") int boardId) {
        try {
            adminService.deleteBoard(boardId); // 서비스 메서드 호출
            System.out.println("Board with ID " + boardId + " deleted successfully.");
            return ResponseEntity.ok("Board deleted successfully!");
        } catch (Exception e) {
            System.err.println("Error deleting board: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete board.");
        }
    }

    @GetMapping("/packageAccessTables")
    public String packageAccessTables(Model model, HttpSession session) {
        List<PackagePost> AccessPackages = adminService.findByAccessPackage("승인");
        model.addAttribute("AccessPackages", AccessPackages);
        return "admin/packageAccessTables";
    }

    @GetMapping("/packageStandbyTables")
    public String packageStandbyTables(Model model, HttpSession session) {
        List<PackagePost> standByPackages = adminService.findByStandByPackage("미승인", "대기");
        model.addAttribute("standByPackages", standByPackages);
        return "admin/packageStandbyTables";
    }

    @GetMapping("/paymentTables")
    public String paymentTables(Model model, HttpSession session) {
        List<Payment> payments = adminService.findByPayment();
        model.addAttribute("payments", payments);
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
