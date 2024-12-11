package com.lec.spring.mytrip.controller;

import com.lec.spring.mytrip.domain.City;
import com.lec.spring.mytrip.domain.Feed;
import com.lec.spring.mytrip.domain.FeedValidator;
import com.lec.spring.mytrip.domain.User;
import com.lec.spring.mytrip.service.FeedService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/mypage")
public class FeedController {

    private final FeedService feedService;

    public FeedController(FeedService feedService) {
        System.out.println("FeedController() 생성");
        this.feedService = feedService;
    }

    // 피드 전체 목록 보기
    @GetMapping("/feedList/{userId}")
    @ResponseBody
    public List<Feed> feedList(@PathVariable int userId) {
        return feedService.listByUser(userId);
    }

    @GetMapping("/feed/{userId}")
    public String feedList(@PathVariable int userId, Model model) {
        List<Feed> feeds = feedService.listByUser(userId);
        model.addAttribute("feeds", feeds);
        return "mypage/feed";
    }

    // 피드 작성 폼
    @GetMapping("/feed/write/{userId}")
    public String write(Model model, @PathVariable int userId) {
        List<City> cities = feedService.getAllCities();
        List<Feed> feedList = feedService.listByUser(userId);
        model.addAttribute("cities", cities);
        model.addAttribute("feedList", feedList);
        model.addAttribute("feed", new Feed());  // 초기 피드 객체를 생성해서 전달
        return "mypage/feed";
    }

    // 피드 작성 처리
    @PostMapping("/feed/write")
    public String feedWriteOk(
            @RequestParam Map<String, MultipartFile> files,
            @Valid Feed feed,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("subject", feed.getBoardSubject());
            redirectAttributes.addFlashAttribute("content", feed.getBoardContent());

            bindingResult.getFieldErrors().forEach(error ->
                    redirectAttributes.addFlashAttribute("error_" + error.getField(), error.getDefaultMessage()));

            return "redirect:/mypage/feed";
        }

        model.addAttribute("result", feedService.write(feed, files));
        return "redirect:/mypage/feed/" + feed.getUser().getId();
    }

    // 피드 상세 보기
    @GetMapping("/feedDetail/{boardId}")
    public String feedDetail(@PathVariable int boardId, Model model) {
        Feed feed = feedService.detail(boardId);
        model.addAttribute("feed", feed);
        return "mypage/feedDetail";  // 상세보기 페이지
    }


    // 피드 수정 폼
    @GetMapping("/feedUpdate/{boardId}")
    public String feedUpdate(@PathVariable int boardId, Model model) {
        Feed feed = feedService.detail(boardId);
        List<City> cities = feedService.getAllCities();

        model.addAttribute("feed", feed);
        model.addAttribute("cityList", cities);
        return "mypage/feedUpdate";
    }

    // 피드 수정 처리
    @PostMapping("/feedUpdate")
    public String feedUpdateOk(
            @RequestParam Map<String, MultipartFile> files,
            @RequestParam(required = false) Long[] delfile,
            @Valid Feed feed,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("subject", feed.getBoardSubject());
            redirectAttributes.addFlashAttribute("content", feed.getBoardContent());

            bindingResult.getFieldErrors().forEach(error ->
                    redirectAttributes.addFlashAttribute("error_" + error.getField(), error.getDefaultMessage()));

            return "redirect:/mypage/feedUpdate/" + feed.getBoardId();
        }

        boolean result = feedService.update(feed, files, delfile);

        if (result) {
            return "mypage/feedUpdateOk";
        } else {
            model.addAttribute("message", "피드 수정 실패");
            return "mypage/feedUpdate";
        }
    }

    // 피드 삭제 처리
    @PostMapping("/feed/delete")
    public String feedDeleteOk(int id, Model model) {
        boolean result = feedService.deleteById(id);
        model.addAttribute("result", result);
        return "mypage/feed";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setValidator(new FeedValidator());
    }
}
