package com.lec.spring.mytrip.controller;

import com.lec.spring.mytrip.domain.Feed;
import com.lec.spring.mytrip.domain.FeedValidaor;
import com.lec.spring.mytrip.service.FeedService;
import com.lec.spring.mytrip.service.PostAttachmentService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

@Controller
@RequestMapping("/mypage")
public class FeedController {

    private final FeedService feedService;
    private final PostAttachmentService postAttachmentService;

    private static final String UPLOAD_DIR = "uploads";

    public FeedController(FeedService feedService, PostAttachmentService postAttachmentService) {
        this.feedService = feedService;
        this.postAttachmentService = postAttachmentService;

        // 업로드 디렉토리 생성
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
    }

    // 피드 작성 폼
    @GetMapping("/feedWrite")
    public String write() {
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
            redirectAttributes.addFlashAttribute("subject", feed.getSubject());
            redirectAttributes.addFlashAttribute("content", feed.getContent());

            bindingResult.getFieldErrors().forEach(error ->
                    redirectAttributes.addFlashAttribute("error_" + error.getField(), error.getDefaultMessage()));

            return "redirect:/mypage/feedWrite";
        }

        try {
            for (MultipartFile file : files.values()) {
                if (!file.isEmpty()) {
                    String fileName = feed.getCategory() + feed.getUser().getUsername() + "_" + file.getOriginalFilename();
                    String folderName = UPLOAD_DIR + File.separator + feed.getCategory();

                    File folder = new File(folderName);
                    if (!folder.exists()) folder.mkdirs();

                    Path filePath = Paths.get(folderName, fileName);
                    Files.copy(file.getInputStream(), filePath);
                }
            }
        } catch (IOException e) {
            model.addAttribute("message", "파일 업로드 실패: " + e.getMessage());
            return "mypage/feedWrite";
        }

        model.addAttribute("result", feedService.write(feed, files));
        return "mypage/feedWriteOk";
    }

    // 피드 상세 보기
    @GetMapping("/feedDetail/{id}")
    public String feedDetail(@PathVariable Long id, Model model) {
        model.addAttribute("feed", feedService.detail(id));
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
        model.addAttribute("feed", feedService.findById(id));
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
            redirectAttributes.addFlashAttribute("subject", feed.getSubject());
            redirectAttributes.addFlashAttribute("content", feed.getContent());

            bindingResult.getFieldErrors().forEach(error ->
                    redirectAttributes.addFlashAttribute("error_" + error.getField(), error.getDefaultMessage()));

            return "redirect:/mypage/feedUpdate/" + feed.getId();
        }

        try {
            for (MultipartFile file : files.values()) {
                if (!file.isEmpty()) {
                    String fileName = "feed" + feed.getUser().getUsername() + "_" + file.getOriginalFilename();
                    String folderName = UPLOAD_DIR + File.separator + feed.getCategory();

                    File folder = new File(folderName);
                    if (!folder.exists()) folder.mkdirs();

                    Path filePath = Paths.get(folderName, fileName);
                    Files.copy(file.getInputStream(), filePath);
                }
            }
        } catch (IOException e) {
            model.addAttribute("message", "파일 처리 중 오류 발생: " + e.getMessage());
            return "mypage/feedUpdate";
        }

        model.addAttribute("result", feedService.update(feed, files, delfile));
        return "mypage/feedUpdateOk";
    }

    // 피드 삭제 처리
    @PostMapping("/feedDelete")
    public String feedDeleteOk(Long id, Model model) {
        model.addAttribute("result", feedService.deleteById(id));
        return "mypage/feedDeleteOk";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setValidator(new FeedValidaor());
    }
}
