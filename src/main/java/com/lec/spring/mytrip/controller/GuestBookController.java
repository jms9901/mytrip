package com.lec.spring.mytrip.controller;

import com.lec.spring.mytrip.domain.GuestBook;
import com.lec.spring.mytrip.service.GuestBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("bookMain/bookGuestBook")
public class GuestBookController {

    private final GuestBookService guestBookService;

    public GuestBookController(GuestBookService guestBookService) {
        this.guestBookService = guestBookService;
    }


    // 방명록 추가
    @PostMapping("/add")
    public Map<String, Object> addGuestBook(@RequestBody GuestBook guestBook) {
        guestBookService.addGuestBook(guestBook);
        Map<String,Object> response = new HashMap<>();
        response.put("message", "success");
        return response;
    }

    // 방명록 조회
    @GetMapping("/list/{toUserId}")
    public List<GuestBook> getGuestBooks(@PathVariable int toUserId){
        return guestBookService.getGuestBooksByUserId(toUserId);
    }

    // 방명록 삭제
    @DeleteMapping("/delete/{guestBookId}")
    public Map<String, Object> deleteGuestBook(@PathVariable int guestBookId) {
        guestBookService.removeGuestBook(guestBookId);
        Map<String,Object> response = new HashMap<>();
        response.put("message", "success");
        return response;
    }

}
