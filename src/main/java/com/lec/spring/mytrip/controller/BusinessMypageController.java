package com.lec.spring.mytrip.controller;

import com.lec.spring.mytrip.domain.PackagePost;
import com.lec.spring.mytrip.domain.Payment;
import com.lec.spring.mytrip.domain.User;
import com.lec.spring.mytrip.repository.PackagePostRepository;
import com.lec.spring.mytrip.service.PackagePostServiceImpl;
import com.lec.spring.mytrip.service.UserServiceImpl;
import com.lec.spring.mytrip.service.businessMypageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.View;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/mypage")
public class BusinessMypageController {

    private final businessMypageService businessMypageService;
    private final UserServiceImpl userService;
    @Autowired
    private View error;
    @Autowired
    private PackagePostRepository packagePostRepository;
    @Autowired
    private PackagePostServiceImpl packagePostServiceImpl;

    public BusinessMypageController(com.lec.spring.mytrip.service.businessMypageService businessMypageService, UserServiceImpl userService) {
        this.businessMypageService = businessMypageService;
        this.userService = userService;
    }


    // 기업 개인정보 수정 -> 모달
    // businessMain.html
    @PostMapping("/business/profile")
    public ResponseEntity<?> updateCompanyProfile(
            @AuthenticationPrincipal User user,
            @RequestParam(required = false) String currentPassword,
            @RequestParam(required = false) String newPassword,
            @RequestParam(required = false)MultipartFile profileImage
            ) {
         try {
             String profileImageFilename = profileImage != null ? profileImage.getOriginalFilename() : null;
            User result = businessMypageService.updateCompany(
                    user.getId(),
                    currentPassword,
                    newPassword,
                    profileImage,
                    profileImageFilename
            );
            return result != null ? ResponseEntity.ok().build(): ResponseEntity.badRequest().body("프로필 수정 실패");
        } catch (Exception e) {
             log.error("프로필 수정 중 예외 발생: ", e);
             return ResponseEntity.status(500).body("서버 오류");
         }
    }

    // 기업 정보 출력 -> 메인 페이지 상단
    // businessMain.html
    @GetMapping("/business/{userId}")
    public String businessMypage(@PathVariable("userId") int userId, Model model) {
        try {
            // 유저 정보 로드
            User user = businessMypageService.getUserById(userId);

            if(user == null) {
                model.addAttribute("errorMessage", "해당 유저를 찾을 수 없습니다.");
                return "error";
            }

            // 승인된 유저만 접근 가능
            if (!"승인".equals(user.getStatus())) {
                model.addAttribute("errorMessage", "접근 권한이 없습니다.");
                return "error";
            }


            List<PackagePost> packages = businessMypageService.likeCntByPackage(userId);
            model.addAttribute("packages", packages);
            model.addAttribute("user", user);

            log.info("Business data: {}, Packages: {}", user, packages);
            return "mypage/businessMain";

        } catch (Exception e) {
//            log.error("마이페이지 로드 중 오류 발생", e);
//            model.addAttribute("errorMessage", "서버 오류가 발생했습니다.");
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("패키지 조회 중 오류가 발생했습니다: " + e.getMessage());
            return "error";
        }

    }


    // 기업이 등록한 패키지 리스트 (패키지 제목, 패키지 내용, 패키지, 패키지 승인 상태, 좋아요 수) -> 메인 페이지 하단
//    @GetMapping("/")
//    public ResponseEntity<List<PackagePost>> getBusinessPackages(@AuthenticationPrincipal User user) {
//        List<PackagePost> packages = businessMypageService.likeCntByPackage(user.getId());
//        return ResponseEntity.ok(packages);
//    }

    // 기업이 등록한 모든 패키지에 관한 일반 유저의 결제 내역(사용자, 패키지 상품 제목, 결제 상태) -> 모달
    @GetMapping("/payments")
    public ResponseEntity<List<Payment>> getBusinessPayments(@AuthenticationPrincipal User user) {
        List<Payment> payments = businessMypageService.getPaymentByCompanyId(user.getId());
        return ResponseEntity.ok(payments);
    }

    // 등록한 패키지 클릭 시 상세보기 -> 모달
    @GetMapping("/package/{packageId}")
    public ResponseEntity<PackagePost> getPackageDetails(@PathVariable("packageId") int packageId) {
        PackagePost packagePost = packagePostRepository.findById(packageId);
        return packagePost != null ? ResponseEntity.ok(packagePost) : ResponseEntity.notFound().build();
    }

    // 상세보기에서 수정 가능 -> 상태가 대기/거절일 경우에만 가능
    @PutMapping("/package/detail")
    public ResponseEntity<?> updatePackage(@RequestBody PackagePost packagePost) {
        int result = packagePostServiceImpl.updatePackage(packagePost);
        return result > 0 ? ResponseEntity.ok().build() : ResponseEntity.badRequest().body("패키지 수정 실패");
    }

    // 상세보기에서 삭제 -> 상태가 대기/거절일 경우에만 가능
    @DeleteMapping("/package/delete/{packageId}")
    public ResponseEntity<?> deletePackage(@PathVariable("packageId") int packageId, int userId) {
       try {
           int result = packagePostServiceImpl.deletePackage(packageId, userId);
           return result > 0 ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
       } catch (IllegalArgumentException | SecurityException e) {
           return ResponseEntity.badRequest().body(e.getMessage());
       }
    }
}
