package com.lec.spring.mytrip.controller;

import com.lec.spring.mytrip.domain.PackagePost;
import com.lec.spring.mytrip.service.PackagePostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/")
public class PackagePostController {

    private final PackagePostService packagePostService;

    public PackagePostController(PackagePostService packagePostService) {
        System.out.println("PackagePostController() 시작");
        this.packagePostService = packagePostService;
    }

    /**
     * 특정 패키지 상세 조회
     *
     * @param packageId 패키지 ID
     * @param model     Model 객체
     * @return 패키지 상세 보기 페이지
     */
    @GetMapping("/{packageId}")
    public String getPackageDetails(@PathVariable int packageId, Model model) {
        PackagePost packagePost = packagePostService.getPackageDetails(packageId);
        model.addAttribute("packagePost", packagePost);
        return "package/details";
    }

    /**
     * 도시별 패키지 목록 조회
     *
     * @param cityId 도시 ID
     * @param model  Model 객체
     * @return 도시별 패키지 목록 페이지
     */
    @GetMapping("/city/{cityId}")
    public String getPackagesByCityId(@PathVariable int cityId, Model model) {
        List<PackagePost> packages = packagePostService.getPackagesByCityId(cityId);
        model.addAttribute("packages", packages);
        return "package/cityPackages";
    }

    /**
     * 사용자별 패키지 목록 조회
     *
     * @param userId 사용자 ID
     * @param model  Model 객체
     * @return 사용자별 패키지 목록 페이지
     */
    @GetMapping("/user/{userId}")
    public String getPackagesByUserId(@PathVariable int userId, Model model) {
        List<PackagePost> packages = packagePostService.getPackagesByUserId(userId);
        model.addAttribute("packages", packages);
        return "package/userPackages";
    }

    /**
     * 패키지 상태별 목록 조회
     *
     * @param status 패키지 상태
     * @param model  Model 객체
     * @return 상태별 패키지 목록 페이지
     */
    @GetMapping("/status/{status}")
    public String getPackagesByStatus(@PathVariable String status, Model model) {
        List<PackagePost> packages = packagePostService.getPackagesByStatus(status);
        model.addAttribute("packages", packages);
        return "package/statusPackages";
    }
//
    /**
     * 패키지 제목 검색
     *
     * @param keyword 검색 키워드
     * @param model   Model 객체
     * @return 검색 결과 페이지
     */
    @GetMapping("/search")
    public String searchPackagesByTitle(@RequestParam String keyword, Model model) {
        List<PackagePost> packages = packagePostService.searchPackagesByTitle(keyword);
        model.addAttribute("packages", packages);
        model.addAttribute("keyword", keyword);
        return "package/searchResults";
    }

    /**
     * 패키지 저장 페이지
     *
     * @param model Model 객체
     * @return 패키지 저장 페이지
     */
    @GetMapping("/new")
    public String createPackageForm(Model model) {
        model.addAttribute("packagePost", new PackagePost());
        return "package/createPackage";
    }

    /**
     * 패키지 저장 처리
     *
     * @param packagePost 저장할 패키지 정보
     * @return 리다이렉트 페이지
     */
    @PostMapping
    public String savePackage(@ModelAttribute PackagePost packagePost) {
        packagePostService.savePackage(packagePost);
        return "redirect:/packages";
    }

    /**
     * 패키지 수정 페이지
     *
     * @param packageId 패키지 ID
     * @param model     Model 객체
     * @return 패키지 수정 페이지
     */
    @GetMapping("/{packageId}/edit")
    public String editPackageForm(@PathVariable int packageId, Model model) {
        PackagePost packagePost = packagePostService.getPackageDetails(packageId);
        model.addAttribute("packagePost", packagePost);
        return "package/editPackage";
    }

    /**
     * 패키지 수정 처리
     *
     * @param packagePost 수정할 패키지 정보
     * @return 리다이렉트 페이지
     */
    @PostMapping("/{packageId}/edit")
    public String updatePackage(@ModelAttribute PackagePost packagePost) {
        packagePostService.updatePackage(packagePost);
        return "redirect:/packages";
    }

    /**
     * 패키지 삭제 처리
     *
     * @param packageId 패키지 ID
     * @return 리다이렉트 페이지
     */
    @PostMapping("/{packageId}/delete")
    public String deletePackage(@PathVariable int packageId, @RequestParam int userId) {
        packagePostService.deletePackage(packageId, userId);
        return "redirect:/packages";
    }

    @Autowired
    PasswordEncoder encoder;

    @RequestMapping("/password")
    @ResponseBody
    public String password() {
        return  encoder.encode("1234");
    }
}
