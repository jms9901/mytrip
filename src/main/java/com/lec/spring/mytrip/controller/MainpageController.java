package com.lec.spring.mytrip.controller;

import com.lec.spring.mytrip.service.MainpageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Controller
public class MainpageController {

    private final MainpageService mainpageService;

    @Autowired
    public MainpageController(MainpageService mainpageService) {
        this.mainpageService = mainpageService;
    }

    // TODO : 팝업    --> boardpage
    // 좋아요가 가장 많은 여행지 정보를 Service에게 요청한다.

    // 사용자가 클릭한 값(사진/X)에 따라 다른 Controller에게 요청하거나 팝업을 닫는 동작을 처리한다.

    // TODO : 투어패키지     --> boardpage
    // 사용자가 투어패키지 정보를 볼 수 있게 Service에게 요청한다.
    // 사용자가 투어패키지를 클릭하면, BoardController에게 해당 투어 페이지로 이동하도록 요청을 전달한다.

    // TODO : AI 추천여행지   --> aipage
    @PostMapping("/ai-recommendations")
    public String showRecommendedCity(Model model) {
        // 서비스 계층에서 가장 추천받은 도시 데이터 가져오기
        Map<String, Object> mostRecommendedCity = mainpageService.getMostRecommendedCity();
        model.addAttribute("mostRecommendedCity", mostRecommendedCity);
        return "ai-recommendations"; // View 이름
    }


}
