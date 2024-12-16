package com.lec.spring.mytrip.controller;

import com.lec.spring.mytrip.domain.Friendship;
import com.lec.spring.mytrip.domain.FriendshipUserResultMap;
import com.lec.spring.mytrip.service.FriendshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        System.out.println(fromUserId + " " + toUserId);
        return friendshipService.acceptFriendRequest(fromUserId, toUserId);
    }

    // 친구 요청 거절
    @PostMapping("/reject")
    public String rejectFriendRequest(@RequestParam int fromUserId, @RequestParam int toUserId) {
        return friendshipService.rejectFriendRequest(fromUserId, toUserId);
    }

    // 친구 목록 조회 API
    @GetMapping("/list/{userId}")
    @ResponseBody
    public ResponseEntity<List<FriendshipUserResultMap>> AcceptedFriends(@PathVariable("userId") Long userId) {
        List<FriendshipUserResultMap> friends = friendshipService.AcceptedFriends(userId);
        return ResponseEntity.ok(friends);
    }

}
