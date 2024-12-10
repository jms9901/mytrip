package com.lec.spring.mytrip.controller;

import com.lec.spring.mytrip.domain.PackagePost;
import com.lec.spring.mytrip.service.PackagePostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/board")
public class PackagePostController {

    private final PackagePostService packagePostService;

    public PackagePostController(PackagePostService packagePostService) {
        System.out.println("PackagePostController() 시작");
        this.packagePostService = packagePostService;
    }

    // 도시 별 페이지 이동
    // board.city.html
    @GetMapping("/city/{cityId}")
    public void getPackagesByCityId(@PathVariable int cityId,
                                    Model model) {
        List<PackagePost> packages = packagePostService.getPackagesByCityId(cityId);
        model.addAttribute("packages", packages);

        // 소모임 목록도 가져와야함
    }

    // 검색 결과 페이지 이동
    // board.search.html
    @GetMapping("{cityId}/search")
    public void searchPackagesByTitle(@PathVariable int cityId,
                                      @RequestParam String keyword,
                                      Model model) {
        List<PackagePost> packages = packagePostService.searchPackages(keyword);
        model.addAttribute("packages", packages);
        model.addAttribute("keyword", keyword);
    }

    // 패키지 상세 이동
    // board.package.detail.html
    @GetMapping("{cityId}/package/detail/{packageId}")
    public void getPackageDetails(@PathVariable int cityId,
                                  @PathVariable int packageId,
                                  Model model) {
        PackagePost packagePost = packagePostService.getPackageDetails(packageId);
        model.addAttribute("packagePost", packagePost);
    }

    // 패키지 글쓰기 페이지 이동
    // board.package.write.html
    @GetMapping("{cityId}/package/write")
    public void writePackagePage(@PathVariable int cityId,
                                 Model model) {
        // 패키지 글쓰기 페이지로 이동
    }

    // 패키지 저장
    @PostMapping("{cityId}/package/save")
    public String savePackage(@PathVariable int cityId,
                              @ModelAttribute PackagePost packagePost) {
        // 패키지 저장 처리
        // 패키지 저장 처리 후 저장된 ID 반환
        int id = packagePostService.savePackage(packagePost);

        // 저장 후 상세 페이지로 리다이렉트
        return "redirect:/board/package/details/" + id;
    }

    // 패키지 수정 페이지 이동
    // board.package.edit
    @GetMapping("{cityId}/package/edit/{packageId}")
    public String editPackagePage(@PathVariable int cityId,
                                  @PathVariable int packageId,
                                  Model model) {
        // 패키지 수정 페이지로 이동
        return "package/edit";
    }

    // 패키지 수정 저장
    @PostMapping("{cityId}/package/update")
    public String updatePackage(@PathVariable int cityId,
                                @ModelAttribute PackagePost packagePost) {
        // 패키지 수정 저장 처리
        // 패키지 저장 처리 후 저장된 ID 반환
        int id = packagePostService.savePackage(packagePost);

        // 저장 후 상세 페이지로 리다이렉트
        return "redirect:/board/package/details/" + id;
    }

    // 패키지 삭제
    @DeleteMapping("{cityId}/package/delete/{packageId}")
    public String deletePackage(@PathVariable int cityId,
                                @PathVariable int packageId) {
        // 패키지 삭제 처리
        int id = 0; //도시 id
        return "redirect:/board/city/" + id;
    }


    // 서비스 단 이하는 차후
    // 소모임 상세 이동
    // board.group.detail.html
    @GetMapping("{cityId}/group/detail/{groupId}")
    public void getGroupDetails(@PathVariable int cityId,
                                @PathVariable int groupId,
                                Model model) {
        // 소모임 상세 페이지로 이동
    }

    // 소모임 글쓰기 페이지 이동
    // board.group.write.html
    @GetMapping("{cityId}/group/write")
    public void writeGroupPage(@PathVariable int cityId) {
        // 소모임 글쓰기 페이지로 이동
    }

    // 소모임 저장
    @PostMapping("{cityId}/group/save")
    public String saveGroup(@PathVariable int cityId,
                            @ModelAttribute PackagePost groupPost) {
        // 소모임 저장 처리
        return "redirect:/board"; // 저장 후 리스트 페이지로 이동
    }

    // 소모임 수정 페이지 이동
    // board.group.edit.html
    @GetMapping("{cityId}/group/edit/{groupId}")
    public String editGroupPage(@PathVariable int cityId,
                                @PathVariable int groupId,
                                Model model) {
        // 소모임 수정 페이지로 이동
        return "group/edit";
    }

    // 소모임 수정 저장
    @PostMapping("{cityId}/group/update")
    public String updateGroup(@PathVariable int cityId,
                              @ModelAttribute PackagePost groupPost) {
        // 소모임 수정 저장 처리
        return "redirect:/board"; // 수정 후 리스트 페이지로 이동
    }

    // 소모임 삭제
    @DeleteMapping("{cityId}/group/delete/{groupId}")
    public String deleteGroup(@PathVariable int cityId,
                              @PathVariable int groupId) {
        // 소모임 삭제 처리
        return "redirect:/board"; // 삭제 후 리스트 페이지로 이동
    }

    // 도시별 피드 모음 페이지 이동
    // board.feeds.html
    @GetMapping("{cityId}/feeds")
    public void cityFeedsPage(@PathVariable int cityId,
                              Model model) {
        // 도시별 피드 모음 페이지로 이동
    }
}
