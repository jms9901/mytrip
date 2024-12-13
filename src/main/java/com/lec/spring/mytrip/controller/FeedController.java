package com.lec.spring.mytrip.controller;

import com.lec.spring.mytrip.domain.City;
import com.lec.spring.mytrip.domain.Feed;
//import com.lec.spring.mytrip.domain.FeedValidator;
import com.lec.spring.mytrip.service.FeedService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/mypage/feed")
public class FeedController {

    private final FeedService feedService;

    @Autowired
    public FeedController(FeedService feedService) {
        System.out.println("FeedController() 생성");
        this.feedService = feedService;
    }
    // 피드 메인 뷰
@GetMapping("/list/{userId}")
    public String feedListView(@PathVariable String userId, Model model) {
        return "mypage/feed";
}

    // 작성 폼 매핑 -> html 수정 할 가능성 있어서 타입, 반환 수정
@GetMapping("/new/{userId}")
   public void newFeed(){}

    // 수정 폼 매핑 -> html 수정 할 가능성 있어서 타입, 반환 수정
    @GetMapping("/edit/{userId}")
    public void editFeed(){}

    @GetMapping("/data/{userId}")
    @ResponseBody
    public ResponseEntity<List<Feed>> getFeedsByUserId(@PathVariable int userId){
        List<Feed> feeds = feedService.getFeedByUserId(userId);
        return ResponseEntity.ok(feeds);
    }

    // 피드 등록
    @PostMapping("/create/{userId}")
    @ResponseBody
    public ResponseEntity<String> createFeed(@PathVariable int userId, @ModelAttribute Feed feed, @RequestParam("files") List<MultipartFile> files) throws IOException {
        feed.setUserId(userId);
        feed.setBoardCategory("피드");
        feedService.insertFeed(feed,files);
        return ResponseEntity.ok("success");
    }

    // 피드 수정 (AJAX 요청)
    @PutMapping("/update/{userId}/{boardId}")
    @ResponseBody
    public ResponseEntity<String> updateFeed(@PathVariable int userId, @PathVariable int boardId,
                                             @RequestParam String boardSubject, @RequestParam String boardContent,
                                             @RequestParam int cityId, @RequestParam(value = "attachmentFiles", required = false) List<MultipartFile> files) throws IOException {
        System.out.println("Received files: " + files);
        if (files != null) {
            for (MultipartFile file : files) {
                System.out.println("File name: " + file.getOriginalFilename());
            }
        } else {
            System.out.println("No files received");
        }
        // Update feed with new information
        feedService.updateFeed(boardId, userId, boardSubject, boardContent, cityId, files);
        return ResponseEntity.ok("피드가 성공적으로 수정되었습니다.");
    }


    // 피드 삭제 (AJAX 요청)
    @DeleteMapping("/delete/{userId}/{boardId}")
    @ResponseBody
    public ResponseEntity<String> deleteFeed(@PathVariable int userId, @RequestParam("boardId") int boardId) {
        feedService.deleteFeed(boardId, userId);
        return ResponseEntity.ok("피드가 성공적으로 삭제되었습니다.");
    }
}