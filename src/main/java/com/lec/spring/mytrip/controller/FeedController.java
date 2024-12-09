package com.lec.spring.mytrip.controller;

import com.lec.spring.mytrip.domain.City;
import com.lec.spring.mytrip.domain.Feed;
import com.lec.spring.mytrip.domain.FeedValidator;
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

    // 피드 작성 폼
    @GetMapping("/feedWrite")
    public String write(Model model) {
        List<City> cities = feedService.getAllCities();
        model.addAttribute("cities", cities);
        model.addAttribute("feed", new Feed());
        return "mypage/feedWrite";
    }

    // 피드 작성 처리
    @PostMapping("/feedWrite")
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

            return "redirect:/mypage/feedWrite";
        }

        boolean result = feedService.write(feed, files);

        if(result) {
            return "mypage/feedWriteOk";
        } else {
            model.addAttribute("message", "피드 작성 실패");
            return "mypage/feedWrite";
        }
    }

    // 피드 상세 보기
    @GetMapping("/feedDetail/{id}")
    public String feedDetail(@PathVariable Long id, Model model) {
        Feed feed = feedService.detail(id);
        model.addAttribute("feed", feed);
        return "mypage/feedDetail";
    }

    // 피드 전체 목록 보기
    @GetMapping("/feedList")
    public String feedList(Model model) {
        model.addAttribute("feeds", feedService.list());
        return "feed";
    }

    // 피드 수정 폼
    @GetMapping("/feedUpdate/{id}")
    public String feedUpdate(@PathVariable Long id, Model model) {
        Feed feed = feedService.detail(id);
        List<City> cities = feedService.getAllCities();

        model.addAttribute("feed", feed);
        model.addAttribute("cities", cities);
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
    @PostMapping("/feedDelete")
    public String feedDeleteOk(Long id, Model model) {
        boolean result = feedService.deleteById(id);
        model.addAttribute("result", result);
        return "mypage/feedDeleteOk";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setValidator(new FeedValidator());
    }
}
