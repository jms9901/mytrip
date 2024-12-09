package com.lec.spring.mytrip.controller;

import ch.qos.logback.classic.Logger;
import com.lec.spring.mytrip.domain.User;
import com.lec.spring.mytrip.repository.UserRepository;
import com.lec.spring.mytrip.service.MyPageService;
import com.lec.spring.mytrip.service.UserService;
import com.lec.spring.mytrip.service.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/mypage")
public class MyPageController {

    private final MyPageService myPageService;


    @Autowired
    private UserServiceImpl userService;

    @Autowired
    public MyPageController(MyPageService myPageService, UserService userService) {
        this.myPageService = myPageService;

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
    @PostMapping("/update/{userId}")
    public ResponseEntity<Map<String, String>> updateUser(
            @PathVariable Long userId,
            @RequestBody Map<String, String> updateRequest,  // 모든 데이터를 Map으로 받습니다.
            @RequestParam(required = false) MultipartFile profileImage)  {

        Map<String, String> response = new HashMap<>();

        // 전달받은 정보 출력
        log.info("Received request to update user {}", userId);
        log.info("Received data: {}", updateRequest);  // updateRequest에 포함된 모든 값 출력
        if (profileImage != null) {
            log.info("Received profile image: {} (size: {} bytes)", profileImage.getOriginalFilename(), profileImage.getSize());
        } else {
            log.info("No profile image received.");
        }
        // 전달받은 정보
        String introduction = updateRequest.get("introduction");
        String currentPassword = updateRequest.get("currentPassword");
        String newPassword = updateRequest.get("newPassword");
        String profileImageBase64 = null;  // base64로 받은 프로필 이미지



        // MultipartFile이 있을 경우, base64로 변환
        if (profileImage != null && !profileImage.isEmpty()) {
            try {
                profileImageBase64 = encodeToBase64(profileImage);  // MultipartFile을 base64로 변환
                log.info("Base64 profileImage: {}", profileImageBase64); // Base64 로그 출력
            } catch (IOException e) {
                log.error("프로필 이미지 처리 중 오류 발생", e);
                response.put("error", "프로필 이미지 처리 중 오류가 발생했습니다.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        }

        // 사용자 업데이트 호출
        boolean success = myPageService.updateUser(userId, introduction, currentPassword, newPassword, profileImage);

        if (success) {
            response.put("message", "정보가 성공적으로 업데이트되었습니다.");
            return ResponseEntity.ok(response);
        } else {
            response.put("error", "정보 업데이트에 실패했습니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    // MultipartFile을 Base64로 변환하는 메서드
    private String encodeToBase64(MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();
        return "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(bytes);
    }


    // 프로필 이미지 반환 메서드
    @GetMapping("/profile/{userId}")
    @ResponseBody
    public ResponseEntity<Resource> getProfileImage(@PathVariable("userId") Long userId) {
        User user = myPageService.getUserById(userId);
        if (user != null && user.getProfile() != null) {
            Path imagePath = Paths.get("/static/uploads/profiles", user.getProfile()); // 프로필 이미지 경로 설정
            Resource resource = new FileSystemResource(imagePath);
            if (resource.exists()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG) // 이미지 타입 설정
                        .body(new FileSystemResource(Paths.get("img","defaultProfile.jpg")));
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
                                     RedirectAttributes redirectAttributes) throws IOException {
        // 프로필 이미지 업데이트
        boolean profile = myPageService.updateProfileImage(userId, file);
        redirectAttributes.addFlashAttribute("success", "프로필 이미지가 성공적으로 업데이트되었습니다.");
        return "redirect:/mypage/" + userId;
    }

}