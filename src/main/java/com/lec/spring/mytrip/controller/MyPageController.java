package com.lec.spring.mytrip.controller;

import ch.qos.logback.classic.Logger;
import com.lec.spring.mytrip.domain.User;
import com.lec.spring.mytrip.repository.UserRepository;
import com.lec.spring.mytrip.service.MyPageService;
import com.lec.spring.mytrip.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/mypage")
public class MyPageController {

    private final MyPageService myPageService;
    private final UserService userService;

    @Autowired
    public MyPageController(MyPageService myPageService, UserService userService) {
        this.myPageService = myPageService;
        this.userService = userService;
    }

    // 사용자 페이지를 보여주는 메소드
    @GetMapping("/{userId}")
    public String getMyPage(@PathVariable("userId") Long userId, Model model) {

        log.info("userId: {}", userId);

        User user = myPageService.getUserById(userId);
        log.info("User Info: {}", user);
        if (user == null) {
            user = new User(); // 빈 User 객체를 생성
            user.setProfile("/img/defaultProfile.jpg"); // 기본 프로필 이미지 경로 설정
        }
        model.addAttribute("user", user);  // user 객체를 모델에 추가
        return "mypage/bookMain"; // 템플릿 이름
    }

    @PostMapping("/update")
    public String updateUser(@RequestParam("userId") Long userId,
                             @RequestParam("newPassword") String newPassword,
                             @RequestParam("introduction") String introduction,
                             @RequestParam(value = "profile", required = false) String profile,
                             RedirectAttributes redirectAttributes) {
        try {
            // 사용자 정보 업데이트
            int result = userService.updateUser(userId, newPassword, introduction, profile);
            if (result > 0) {
                redirectAttributes.addFlashAttribute("success", "사용자 정보가 성공적으로 업데이트되었습니다.");
            } else {
                redirectAttributes.addFlashAttribute("error", "업데이트에 실패했습니다.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "오류가 발생했습니다.");
        }
        return "redirect:/mypage/" + userId; // 페이지 리다이렉트
    }

    // 프로필 이미지 반환 메서드
    @GetMapping("/profile/{userId}")
    @ResponseBody
    public ResponseEntity<Resource> getProfileImage(@PathVariable("userId") Long userId) {
        User user = myPageService.getUserById(userId);
        if (user != null && user.getProfile() != null) {
            Path imagePath = Paths.get("/img/defaultProfile.jpg", user.getProfile()); // 프로필 이미지 경로 설정
            Resource resource = new FileSystemResource(imagePath);
            if (resource.exists()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG) // 이미지 타입 설정
                        .body(resource);
            }
        }
        // 프로필 이미지가 없으면 기본 이미지로 반환
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(new FileSystemResource(Paths.get("/img/defaultProfile.jpg", "defaultProfile.jpg")));
    }

    @GetMapping("/bookMain")
    public String myPage() {
        return "mypage/bookMain";
    }

    @GetMapping("/bookMain/bookGuestBook")
    public String myPageGuestBook() {
        return "mypage/bookGuestBook";
    }

    @RequestMapping("/updatePassword")
    public String updatePassword(@RequestParam("userId") Long userId,
                                 @RequestParam("currentPassword") String currentPassword,
                                 @RequestParam("newPassword") String newPassword) {
        boolean result = myPageService.updatePassword(userId, currentPassword, newPassword);
        return result ? "passwordUpdated" : "passwordUpdateFailed";
    }

    @RequestMapping("/updateIntroduction")
    public String updateIntroduction(@RequestParam("userId") Long userId,
                                     @RequestParam("introduction") String introduction) {
        boolean result = myPageService.updateIntroduction(userId, introduction);
        return result ? "introductionUpdated" : "introductionUpdateFailed";
    }

    @RequestMapping("/updateProfileImage")
    public String updateProfileImage(@RequestParam("userId") Long userId,
                                     @RequestParam("file") MultipartFile file,
                                     RedirectAttributes redirectAttributes) {
        try {
            // 프로필 이미지 업데이트
            boolean profile = myPageService.updateProfileImage(userId, file);
            redirectAttributes.addFlashAttribute("success", "프로필 이미지가 성공적으로 업데이트되었습니다.");
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", "프로필 이미지 업데이트에 실패했습니다.");
        }
        return "redirect:/mypage/" + userId;
    }

}