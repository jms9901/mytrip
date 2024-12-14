package com.lec.spring.mytrip.controller;

import ch.qos.logback.classic.Logger;
import com.lec.spring.mytrip.domain.City;
import com.lec.spring.mytrip.domain.Feed;
import com.lec.spring.mytrip.domain.FriendshipUserResultMap;
import com.lec.spring.mytrip.domain.User;
import com.lec.spring.mytrip.repository.UserRepository;
import com.lec.spring.mytrip.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.*;

@Slf4j
@Controller
@RequestMapping("/mypage")
public class MyPageController {

    private final MyPageService myPageService;
    private final LikedService likeService;

    @Autowired
    private FriendshipService friendshipService;

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private FeedService feedService;

    @Autowired
    public MyPageController(MyPageService myPageService, LikedService likeService, FriendshipService friendshipService, UserServiceImpl userService) {
        this.myPageService = myPageService;
        this.likeService = likeService;
        this.userService=userService;
        this.friendshipService=friendshipService;
    }

    @GetMapping("/{userId}/likedCity")
    public List<City> getLikedCity(@PathVariable("userId") Long userId) {
        return likeService.getLikedCityByUserId(userId);
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
        int countAcceptedFriends=friendshipService.countAcceptedFriends(userId.intValue());
        model.addAttribute("countAcceptedFriends", countAcceptedFriends);

        // 좋아요한 도시 목록 가져오기
        List<City> likedCities = likeService.getLikedCityByUserId(userId);
        model.addAttribute("likedCities", likedCities);  // 모델에 추가

        model.addAttribute("user", user);  // user 객체를 모델에 추가
        return "mypage/bookMain"; // 템플릿 이름
    }

    @GetMapping("/recentfeed/{userId}")
    @ResponseBody
    public ResponseEntity<List<Feed>> getFeedfindRecentFeedsByUserId(@PathVariable int userId){
        List<Feed> recentfeed = feedService.findRecentFeedsByUserId(userId);
        return ResponseEntity.ok(recentfeed);
    }

    // 친구 목록조회
    @GetMapping("/friendList/{userId}")
    @ResponseBody
    public ResponseEntity<List<FriendshipUserResultMap>> getFriendList(@PathVariable("userId") Long userId) {
        List<FriendshipUserResultMap> friendList = friendshipService.AcceptedFriends(userId);
        return ResponseEntity.ok(friendList);
    }

    @PostMapping("/update/{userId}")
    public ResponseEntity<Map<String, String>> updateUser(
            @PathVariable("userId") Long userId,
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
        String profileImageBase64 = updateRequest.get("profileImage");  // base64로 받은 프로필 이미지

        String uploadDirectory = "uploads/profiles/";
        String savedFileName = null;
        System.out.println(uploadDirectory.toString());
        // base64로 받은 프로필 이미지가 있을 경우, 디코딩해서 저장
        if (profileImageBase64 != null && !profileImageBase64.isEmpty()) {
            try {
                byte[] decodedBytes = Base64.getDecoder().decode(profileImageBase64);
                String fileName = userId + "_profileImage.jpg";
                Path filePath = Paths.get(uploadDirectory, fileName);
                Files.write(filePath, decodedBytes); // 이미지 파일 저장
                savedFileName = fileName;
                log.info("Profile image saved as: {}", savedFileName);
            } catch (IOException e) {
                log.error("프로필 이미지 저장 오류", e);
                response.put("error", "프로필 이미지 저장 중 오류가 발생했습니다.");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        } else {
            log.info("No profile image received.");
        }

        // 사용자 업데이트 호출
        boolean success = myPageService.updateUser(userId, introduction, currentPassword, newPassword, profileImage, savedFileName);

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
        Path imagePath;

        if (user != null && user.getProfile() != null) {
            imagePath = Paths.get("uploads/profiles/", user.getProfile());
        } else {
            imagePath = Paths.get("img", "defaultProfile.jpg"); // 기본 프로필 이미지 경로
        }

        Resource resource = new FileSystemResource(imagePath);
        if (resource.exists()) {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(resource);
        }
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/bookMain")
    public String myPage() {
        return "mypage/bookMain";
    }

    @GetMapping("/bookMain/bookGuestBook/{userId}")
    public String myPageGuestBook(@PathVariable("userId") Long userId, Model model) {
        // userId에 해당하는 사용자 정보를 모델에 추가
        User user = myPageService.getUserById(userId);
        model.addAttribute("user", user);

        // GuestBook 페이지로 이동
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