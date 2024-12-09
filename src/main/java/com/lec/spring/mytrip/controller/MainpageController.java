package com.lec.spring.mytrip.controller;

import com.lec.spring.mytrip.domain.City;
import com.lec.spring.mytrip.service.MainpageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.lec.spring.mytrip.domain.Package;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/main")
public class MainpageController {

    private final MainpageService mainpageService;

    @Autowired
    public MainpageController(MainpageService mainpageService) {
        this.mainpageService = mainpageService;
    }


    // 사용자가 클릭한 값(사진/X)에 따라 다른 Controller에게 요청하거나 팝업을 닫는 동작을 처리한다.
    @GetMapping("/mainpage")
    public String mainpage(Model model) {
        // 서비스 계층에서 상위 2개의 추천 도시 데이터를 가져오기
        List<City> mostRecommendedCities = mainpageService.getMostRecommendedCities();
        model.addAttribute("mostRecommendedCities", mostRecommendedCities);

        // 가장 최신에 등록된 10개의 패키지 게시물 가져오기
        List<Package> latestPackages = mainpageService.getLatestPackages();
        model.addAttribute("latestPackages", latestPackages);

        return "main/mainpage";
    }

    // ai page로 이동
    @GetMapping("/aipage/start")
    public String aipageStart() {
        return "aipage/start";
    }

    // login page로 이동
    @GetMapping("/user/login")
    public void userLogin() {}

    //post page로 이동
    @GetMapping("/post")
    public void post() {}

    // flight page로 이동
    @GetMapping("/flight/search")
    public void flightSearch() {}


    // TODO : 팝업    --> boardpage
    // 좋아요가 가장 많은 여행지 정보를 Service에게 요청한다.
}
