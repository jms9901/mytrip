package com.lec.spring.mytrip.controller;

import com.lec.spring.mytrip.domain.Board;
import com.lec.spring.mytrip.domain.PackagePost;
import com.lec.spring.mytrip.domain.Payment;
import com.lec.spring.mytrip.domain.User;
import com.lec.spring.mytrip.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/adminLogin")
    public String login() {
        return "admin/adminLogin";
    }

    @GetMapping("/userTables")
    public String userTables(Model model) {
        List<User> users = adminService.findByAuthorityRoleUser("ROLE_USER");
        model.addAttribute("users", users);
        return "admin/userTables";
    }

    @GetMapping("/businessTables")
    public String businessTables(Model model) {
        List<User> businessUsers = adminService.findByAuthorityRoleBusiness("ROLE_BUSINESS");
        model.addAttribute("businessUsers", businessUsers);
        return "admin/businessTables";
    }

    @GetMapping("/boardTables")
    public String boardTables(Model model) {
        List<Board> boards = adminService.findByBoardCategory("소모임");
        model.addAttribute("boards", boards);
        return "admin/boardTables";
    }

    @GetMapping("/feedTables")
    public String feedTables(Model model) {
        List<Board> feeds = adminService.findByFeedCategory("피드");
        model.addAttribute("feeds", feeds);
        return "admin/feedTables";
    }

    @GetMapping("/packageAccessTables")
    public String packageAccessTables(Model model) {
        List<PackagePost> approvedPackages = adminService.findByAccessPackage("승인");
        model.addAttribute("approvedPackages", approvedPackages);
        return "admin/packageAccessTables";
    }

    @GetMapping("/packageStandbyTables")
    public String packageStandbyTables(Model model) {
        List<PackagePost> standbyPackages = adminService.findByStandByPackage("대기");
        model.addAttribute("standbyPackages", standbyPackages);
        return "admin/packageStandbyTables";
    }

    @GetMapping("/paymentTables")
    public String paymentTables(Model model) {
        List<Payment> payments = adminService.findByPayment(); // 0은 예시 값으로, 실제로는 조건에 맞게 수정해야 합니다.
        model.addAttribute("payments", payments);
        return "admin/paymentTables";
    }

    @GetMapping("/charts")
    public String charts() {
        return "admin/charts";
    }

    // 유저 삭제 (사용 예시)
    @PostMapping("/userTables/delete")
    public String deleteUser(@RequestParam int userId) {
        adminService.deleteUser(userId);
        return "redirect:/admin/userTables";
    }

    // business 유저 승인 상태로 변경 (사용 예시)
    @PostMapping("/businessTables/approve")
    public String updateBusinessUserStatus(@RequestParam int userId, @RequestParam String status) {
        adminService.updateBusinessUserStatus(userId, status);
        return "redirect:/admin/businessTables";
    }

    // 소모임/피드 삭제 (사용 예시)
    @PostMapping("/boardTables/delete")
    public String deleteBoard(@RequestParam int boardId) {
        adminService.deleteBoard(boardId);
        return "redirect:/admin/boardTables";
    }

    // 패키지 승인 상태 변경 (사용 예시)
    @PostMapping("/packageAccessTables/updateStatus")
    public String updatePackageStatus(@RequestParam int packageId, @RequestParam String status) {
        adminService.updatePackageStatus(packageId, status);
        return "redirect:/admin/packageAccessTables";
    }
}
