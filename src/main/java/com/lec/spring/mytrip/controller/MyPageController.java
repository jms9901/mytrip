package com.lec.spring.mytrip.controller;

import com.lec.spring.mytrip.config.PrincipalDetails;
import com.lec.spring.mytrip.domain.City;
import com.lec.spring.mytrip.domain.Feed;
import com.lec.spring.mytrip.domain.FriendshipUserResultMap;
import com.lec.spring.mytrip.domain.User;
import com.lec.spring.mytrip.service.*;
import com.lec.spring.mytrip.util.U;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/mypage")
public class MyPageController {

    private final MyPageService myPageService;
    private final LikedService likeService;
    private final FriendshipService friendshipService;
    private final UserServiceImpl userService;
    private final FeedService feedService;

    @Autowired
    public MyPageController(MyPageService myPageService, LikedService likeService, FriendshipService friendshipService, UserServiceImpl userService, FeedService feedService) {
        this.myPageService = myPageService;
        this.likeService = likeService;
        this.friendshipService = friendshipService;
        this.userService = userService;
        this.feedService = feedService;
    }


    @GetMapping("/loginuserid")
    @ResponseBody
    public Map<String, Integer> loginUserId(HttpSession session) {
        User loggedUser = U.getLoggedUser();  // 사용자 정보 가져오기
        int currentLogger = loggedUser.getId();
        System.out.println(currentLogger);
        // JSON 응답 형식으로 반환
        return Map.of("userId", currentLogger);
    }



    @GetMapping("/{userId}/likedCity")
    public List<City> getLikedCity(@PathVariable("userId") int userId) {
        return likeService.getLikedCityByUserId(userId);
    }

    @GetMapping("/{userId}")
    public String getMyPage(@PathVariable("userId") int userId, Model model) {
        log.info("userId: {}", userId);


        User user = myPageService.getUserById(userId);
        if (user == null) {
            user = new User();
            user.setProfile("/img/defaultProfile.jpg");
        }

        int countAcceptedFriends = friendshipService.countAcceptedFriends(userId);
        model.addAttribute("countAcceptedFriends", countAcceptedFriends);

        List<City> likedCities = likeService.getLikedCityByUserId(userId);
        model.addAttribute("likedCities", likedCities);
        model.addAttribute("user", user);

        return "mypage/bookMain";
    }

    @GetMapping("/recentfeed/{userId}")
    @ResponseBody
    public ResponseEntity<List<Feed>> getRecentFeedsByUserId(@PathVariable int userId) {
        List<Feed> recentFeed = feedService.findRecentFeedsByUserId(userId);
        return ResponseEntity.ok(recentFeed);
    }

    @GetMapping("/friendList/{userId}")
    @ResponseBody
    public ResponseEntity<List<FriendshipUserResultMap>> getFriendList(@PathVariable("userId") Long userId) {
        List<FriendshipUserResultMap> friendList = friendshipService.AcceptedFriends(userId);
        return ResponseEntity.ok(friendList);
    }

    @GetMapping("/requestList/{userId}")
    @ResponseBody
    public ResponseEntity<List<FriendshipUserResultMap>> getrequestist(@PathVariable("userId") Long userId) {
        List<FriendshipUserResultMap> requestList = friendshipService.requestView(userId);
        return ResponseEntity.ok(requestList);
    }



    @PostMapping("/update/{userId}")
    public ResponseEntity<Map<String, String>> updateUser(
            @PathVariable("userId") int userId,
            @RequestBody Map<String, String> updateRequest,
            @RequestParam(required = false) MultipartFile profileImage) {

        Map<String, String> response = new HashMap<>();

        String introduction = updateRequest.get("introduction");
        String currentPassword = updateRequest.get("currentPassword");
        String newPassword = updateRequest.get("newPassword");
        String profileImageBase64 = updateRequest.get("profileImage");

        String savedFileName = null;

        // Process base64 encoded profile image
        if (profileImageBase64 != null && !profileImageBase64.isEmpty()) {
            try {
                byte[] decodedBytes = Base64.getDecoder().decode(profileImageBase64);
                String fileName = userId + "_profileImage.jpg";
                Path filePath = Paths.get("static/uploads/profiles/", fileName);
                Files.write(filePath, decodedBytes);
                savedFileName = fileName;
            } catch (IOException e) {
                log.error("Error saving profile image", e);
                response.put("error", "Error saving profile image.");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        }

        // Process MultipartFile profile image upload
        if (profileImage != null && !profileImage.isEmpty()) {
            try {
                savedFileName = myPageService.saveProfileImage(profileImage);  // Save the image and get file name
            } catch (IOException e) {
                log.error("Error saving profile image", e);
                response.put("error", "Error saving profile image.");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        }

        boolean success = myPageService.updateUser(userId, introduction, currentPassword, newPassword, profileImage, savedFileName);

        if (success) {
            response.put("message", "User information updated successfully.");
            return ResponseEntity.ok(response);
        } else {
            response.put("error", "Failed to update user information.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/profile/{userId}")
    @ResponseBody
    public ResponseEntity<Resource> getProfileImage(@PathVariable("userId") int userId) {
        User user = myPageService.getUserById(userId);
        Path imagePath = (user != null && user.getProfile() != null)
                ? Paths.get("static/uploads/profiles/", user.getProfile())
                : Paths.get("img", "defaultProfile.jpg");

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
    public String myPageGuestBook(@PathVariable("userId") int userId, Model model) {
        User user = myPageService.getUserById(userId);
        model.addAttribute("user", user);
        return "mypage/bookGuestBook";
    }

    @RequestMapping("/updatePassword")
    public String updatePassword(@RequestParam("userId") int userId,
                                 @RequestParam("currentPassword") String currentPassword,
                                 @RequestParam("newPassword") String newPassword) {
        boolean result = myPageService.updatePassword(userId, currentPassword, newPassword);
        return result ? "passwordUpdated" : "passwordUpdateFailed";
    }

    @RequestMapping("/updateIntroduction")
    public String updateIntroduction(@RequestParam("userId") int userId,
                                     @RequestParam("introduction") String introduction) {
        boolean result = myPageService.updateIntroduction(userId, introduction);
        return result ? "introductionUpdated" : "introductionUpdateFailed";
    }

    @RequestMapping("/updateProfileImage")
    public String updateProfileImage(@RequestParam("userId") int userId,
                                     @RequestParam("file") MultipartFile file,
                                     RedirectAttributes redirectAttributes) throws IOException {
        boolean profile = myPageService.updateProfileImage(userId, file);
        redirectAttributes.addFlashAttribute("success", "Profile image updated successfully.");
        return "redirect:/mypage/" + userId;
    }
}
