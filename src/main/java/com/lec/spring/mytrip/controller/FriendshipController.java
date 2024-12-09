package com.lec.spring.mytrip.controller;

import com.lec.spring.mytrip.service.FriendshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/friendship")
public class FriendshipController {

    @Autowired
    private FriendshipService friendshipService;

    // 친구 요청 보내기
    @PostMapping("/send")
    public String sendFriendRequest(@RequestParam int fromUserId, @RequestParam int toUserId) {
        return friendshipService.sendFriendRequest(fromUserId, toUserId);
    }

    // 친구 요청 수락
    @PostMapping("/accept")
    public String acceptFriendRequest(@RequestParam int fromUserId, @RequestParam int toUserId) {
        return friendshipService.acceptFriendRequest(fromUserId, toUserId);
    }

    // 친구 요청 거절
    @PostMapping("/reject")
    public String rejectFriendRequest(@RequestParam int fromUserId, @RequestParam int toUserId) {
        return friendshipService.rejectFriendRequest(fromUserId, toUserId);
    }
}
