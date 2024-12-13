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

import java.util.Collections;
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

    // 기업 마이페이지 메인
    @GetMapping("/business/{userId}")
    public String businessMypage(@PathVariable("userId") int userId, Model model) {
        try {
            User user = businessMypageService.getUserById(userId);

            if(user == null) {
                model.addAttribute("error", "해당 유저를 찾을 수 없습니다.");
                return "error";
            }

            if(!"승인".equals(user.getStatus())) {
                model.addAttribute("error", "접근 권한이 없습니다.");
                return "error";
            }

            model.addAttribute("user", user);
            return "mypage/businessMain";
        } catch (Exception e) {
            log.error("페이지 로드 중 오류 발생", e);
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    // 기업 정보 출력 -> 메인 페이지
    // 기업이 등록한 패키지 리스트 (패키지 제목, 패키지 내용, 패키지, 패키지 승인 상태, 좋아요 수) -> 메인 페이지
    // JSON API 엔드포인트(js요청)
    // businessMain.html
    @GetMapping("/business/js/{userId}")
    @ResponseBody
    public ResponseEntity<?> businessMypage(@PathVariable("userId") int userId) {
        try {
            // 유저 정보 로드
            User user = businessMypageService.getUserById(userId);

            // 디버깅용 로그
            log.info("user found: {}", user);
            log.info("userID: {}", userId);

            if(user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("해당 유저를 찾을 수 없습니다.");
            }

            // 승인된 유저만 접근 가능
            if (!"승인".equals(user.getStatus())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("접근 권한이 없습니다.");
            }

            // 패키지 목록 로드
            List<PackagePost> packages = businessMypageService.likeCntByPackage(userId);

            // 패키지 상세정보 로드
            List<PackagePost> packageDetail = packagePostRepository.findByUserId(userId);

            // 결제 내역 로드
            List<Payment> payments = businessMypageService.getPaymentByCompanyId(userId);

            log.info("Business data: {}, Packages: {}", user, packages);

            // 패키지가 없는 경우 빈 배열 반환
            if (packages == null || packages.isEmpty()) {
                return ResponseEntity.ok(Collections.emptyList());
            }

            return ResponseEntity.ok(packages);

        } catch (Exception e) {
            log.error("패키지 조회 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("패키지 조회 중 오류가 발생했습니다: " + e.getMessage());
        }

    }

    // 기업 개인정보 수정 -> 모달
    // businessMain.html
    @GetMapping("/business/profile/{userId}")
    @ResponseBody
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


    // 기업이 등록한 모든 패키지에 관한 일반 유저의 결제 내역(사용자, 패키지 상품 제목, 결제 상태) -> 모달
    // 결제 리스트 데이터 반환
    @GetMapping("/payments")
    @ResponseBody
    public ResponseEntity<List<Payment>> getBusinessPayments(@AuthenticationPrincipal User user) {
        try {
            List<Payment> payments = businessMypageService.getPaymentByCompanyId(user.getId());
            return ResponseEntity.ok(payments);
        } catch (Exception e) {
            log.error("결제 정보 조회 중 오류 발생", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    // 등록한 패키지 클릭 시 상세보기 -> 모달
    // 패키지 상세 정보 반환
    @GetMapping("/package/{packageId}")
    @ResponseBody
    public ResponseEntity<?> getPackageDetails(@PathVariable("packageId") int packageId) {
        try {
            PackagePost packagePost = packagePostRepository.findById(packageId);

            if(packagePost == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(packagePost);
        } catch (Exception e) {
            log.error("패키지 상세 조회 중 오류 발생", e);
            return ResponseEntity.internalServerError().body("패키지 상세 조회 중 오류가 발생해습니다.");
        }
    }

    // 상세보기에서 삭제 -> 상태가 대기/거절일 경우에만 가능
    @DeleteMapping("/package/delete/{packageId}")
    public ResponseEntity<?> deletePackage(@PathVariable("packageId") int packageId, @AuthenticationPrincipal User user) {
       try {
           int result = packagePostServiceImpl.deletePackage(packageId, user.getId());
           return result > 0 ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
       } catch (IllegalArgumentException | SecurityException e) {
           return ResponseEntity.badRequest().body(e.getMessage());
       }
    }
}
