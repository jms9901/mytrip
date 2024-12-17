package com.lec.spring.mytrip.controller;

import com.lec.spring.mytrip.domain.City;
import com.lec.spring.mytrip.domain.Feed;
import com.lec.spring.mytrip.domain.PackagePost;
import com.lec.spring.mytrip.domain.User;
import com.lec.spring.mytrip.domain.attachment.PackagePostAndAttachment;
import com.lec.spring.mytrip.domain.attachment.PackagePostAttachment;
import com.lec.spring.mytrip.service.CityService;
import com.lec.spring.mytrip.service.PackageAttachmentService;
import com.lec.spring.mytrip.service.FeedService;
import com.lec.spring.mytrip.service.PackagePostService;
import com.lec.spring.mytrip.util.U;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/board/city")
public class PackagePostController {

    private final PackagePostService packagePostService;
    private final CityService cityService;
    private final FeedService feedService;

    public PackagePostController(PackagePostService packagePostService, CityService cityService, FeedService feedService) {
        System.out.println("PackagePostController() 시작");
        this.cityService = cityService;
        this.feedService = feedService;
        this.packagePostService = packagePostService;
    }

    @ModelAttribute("cityId")
    public Integer addCityId(@PathVariable(required = false) Integer cityId) {
        return cityId;
    }

    // 도시 별 페이지 이동
    // board.city.html
    @GetMapping("/{cityId}")
    public String getPackagesByCityId(@PathVariable int cityId,
                                    Model model) {

        //메인 페이지 사이드 도시 목록 출력용
        List<City> sideCities = cityService.findCitiesByContinentOfThisCity(cityId);
        model.addAttribute("cities", sideCities);
//        System.out.println("도시 목록"  + sideCities.toString());

        // 이 도시의 패키지 목록
        List<PackagePost> packages = packagePostService.getPackagesByCityId(cityId);
        packages.forEach(System.out::println);
        model.addAttribute("packages", packages);
//        System.out.println("패키지 목록" + packages.toString());

        // 이 도시의 소모임 목록
        List<Feed> feeds = feedService.findByCityAndCategory(cityId, "소모임");
        model.addAttribute("feeds", feeds);
//        System.out.println("소모임 목록" + feeds.toString());


        return "/board/city";
    }

    // 검색 결과 페이지 이동 -> 당장 안쓸듯?
    // board.search.html
    @GetMapping("{cityId}/package/search")
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
    public String getPackageDetails(@PathVariable int cityId,
                                  @PathVariable int packageId,
                                  Model model) {
        PackagePostAndAttachment packagePostAndAttachment = packagePostService.getPackageDetails(packageId);

        model.addAttribute("packagePost", packagePostAndAttachment.getPackagePost());
        model.addAttribute("packageId", packageId);
        model.addAttribute("attachments", packagePostAndAttachment.getPackagePostAttachment());
//        packagePostAndAttachment.getPackagePostAttachment().forEach(System.out::println);

        return "board/city/package/detail";
    }

    // 패키지 글쓰기 페이지 이동
    // board.package.write.html
    @GetMapping("{cityId}/package/write")
    public String writePackagePage(@PathVariable int cityId) {
        return "board/city/package/write";
    }

    // 패키지 저장
    @PostMapping("{cityId}/package/save")
    public String savePackage(@PathVariable int cityId,
                              @RequestParam("files") List<MultipartFile> files,
                              @ModelAttribute PackagePost packagePost){
        System.out.println("컨트롤러 들어옴");

        files.forEach(System.out::println);

        System.out.println("저장할 파일" + packagePost);

        // 패키지 저장 처리
        // 패키지 저장 처리. 패키지 저장에서 첨부파일 서비스를 호출
        int packageId = packagePostService.savePackage(packagePost, files);

        // 저장 후 상세 페이지로 리다이렉트
        return "redirect:/board/city/" + cityId + "/package/detail/" + packageId;
    }

    // 패키지 수정 페이지 이동 안써
    // board.package.edit
    @GetMapping("{cityId}/package/edit/{packageId}")
    public String editPackagePage(@PathVariable int cityId,
                                  @PathVariable int packageId,
                                  Model model) {
        // 패키지 수정 페이지로 이동
        model.addAttribute("packagePost", packagePostService.getPackageDetails(packageId));
        return "/board/city/package/edit";
    }

    // 패키지 수정 저장 안써
    @PostMapping("{cityId}/package/update/{packageId}")
    public String updatePackage(@PathVariable int cityId,
                                @PathVariable int packageId,
                                @RequestParam("files") List<MultipartFile> files,
                                @ModelAttribute PackagePost packagePost) {
        // 패키지 수정 저장 처리
        // 패키지 저장 처리 후 저장된 ID 반환
        int id = packagePostService.updatePackage(packagePost, files);

        // 저장 후 상세 페이지로 리다이렉트
        return "redirect:/board/city/" + cityId + "/package/detail/" + packageId;
    }

    // 패키지 삭제
    @GetMapping("{cityId}/package/delete/{packageId}")
    public String deletePackage(@PathVariable int cityId,
                                @PathVariable int packageId) {
        int userId = packagePostService.getPackageDetails(packageId).getPackagePost().getUser().getId();
        // 패키지 삭제 처리
        System.out.println("삭제 컨트롤러 진입");
        packagePostService.deletePackage(packageId, userId);
        return "redirect:/board/city/" + cityId;
    }

    // 결제 페이지 이동


    // 서비스 단 이하는 차후
    // 소모임 상세 이동
    // board.group.detail.html
    @GetMapping("{cityId}/group/detail/{groupId}")
    public String getGroupDetails(@PathVariable int cityId,
                                @PathVariable int groupId,
                                Model model) {
        // 소모임 상세 페이지로 이동
        Feed feed =  feedService.detail(groupId);

        model.addAttribute("feed", feed);
        model.addAttribute("cityId", cityId);
        model.addAttribute("groupId", groupId);

        return "board/city/group/detail";
    }

    // 소모임 글쓰기 페이지 이동
    // board.group.write.html
    @GetMapping("{cityId}/group/write")
    public String writeGroupPage(@PathVariable int cityId) {
        // 소모임 글쓰기 페이지로 이동
        return "board/city/group/write";
    }

    // 소모임 저장
    @PostMapping("{cityId}/group/save")
    public String saveGroup(@PathVariable int cityId,
                            @ModelAttribute Feed feed,
                            @RequestParam List<MultipartFile> files) {

        feed.setUserId(U.getLoggedUser().getId());
        System.out.println(feed.getUserId());

        try {
            feed.setBoardCategory("소모임");
            feedService.insertFeed(feed, files);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return "redirect:/board/city/" + cityId + "/group/detail/" + feed.getBoardId();
    }

    // 소모임 수정 페이지 이동
    // board.group.edit.html
    @GetMapping("{cityId}/group/edit/{groupId}")
    public String editGroupPage(@PathVariable int cityId,
                                @PathVariable int groupId,
                                Model model) {
        // 소모임 수정 페이지로 이동
        Feed feed = feedService.detail(groupId);
        model.addAttribute("feed", feed);
        model.addAttribute("cityId", cityId);
        return "board/city/group/edit";
    }

    // 소모임 수정 저장
    @PostMapping("{cityId}/group/update")
    public String updateGroup(@PathVariable int cityId,
                              @ModelAttribute Feed feed,
                              @RequestParam List<MultipartFile> files) {
        System.out.println(feed.toString());

        try {
            feedService.updateFeed(feed.getBoardId(), feed.getUserId()
                    , feed.getBoardSubject()
                    , feed.getBoardContent()
                    , feed.getCityId()
                    , files);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return "redirect:/board/city/" + cityId + "/group/detail/" + feed.getBoardId();
    }

    // 소모임 삭제
    @GetMapping("{cityId}/group/delete/{groupId}")
    public String deleteGroup(@PathVariable int cityId,
                              @PathVariable int groupId) {
        System.out.println("컨트롤라 : " + U.getLoggedUser().getId());

        feedService. deleteFeed(groupId , U.getLoggedUser().getId());

        return "redirect:/board/city/" + cityId;
    }


    // 도시별 피드 모음 페이지 이동
    // board.feeds.html
    @GetMapping("{cityId}/feeds")
    public String cityFeedsPage(
            @PathVariable int cityId,
            @RequestParam(required = false) Integer groupId,
            @RequestParam(required = false) String category,
            Model model) {
        // 도시와 카테고리에 따른 피드 가져오기
        List<Feed> feeds = feedService.findByCityAndCategory(cityId, "피드");
        System.out.println("feeds " + feeds);
        // 그룹 ID 관련 데이터 추가 로직 필요시 구현
        if (groupId != null) {
            model.addAttribute("groupId", groupId);
        }


        List<City> sideCities = cityService.findCitiesByContinentOfThisCity(cityId);
        model.addAttribute("cities", sideCities);


        // 모델에 데이터 추가
        model.addAttribute("feeds", feeds); // 피드 목록
        model.addAttribute("cityId", cityId); // 현재 도시 ID
        model.addAttribute("category", category); // 현재 카테고리

        // "board/feeds"는 templates/board/feeds.html을 가리킴
        return "board/city/feeds";
    }

    //상대방 마이페이지로 이동
}
