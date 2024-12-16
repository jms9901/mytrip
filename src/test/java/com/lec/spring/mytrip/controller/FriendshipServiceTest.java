package com.lec.spring.mytrip.controller;

import com.lec.spring.mytrip.domain.Friendship;
import com.lec.spring.mytrip.domain.FriendshipStatus;
import com.lec.spring.mytrip.repository.FriendshipRepository;
import com.lec.spring.mytrip.service.FriendshipService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FriendshipServiceTest {

    @Autowired
    private FriendshipService friendshipService;

    @Autowired
    private FriendshipRepository friendshipRepository;

    @Test
    void testSendFriendRequest() {
        String result = friendshipService.sendFriendRequest(1, 2);
        assertEquals("친구 요청이 성공적으로 전송되었습니다.", result);

        Optional<Friendship> found = friendshipRepository.findFriendRequest(2, 1);
        assertTrue(found.isPresent());
        assertEquals(FriendshipStatus.대기, found.get().getFriendStatus());
    }

    @Test
    void testAcceptFriendRequest() {
        friendshipService.sendFriendRequest(1, 2);

        String result = friendshipService.acceptFriendRequest(1, 2);
        assertEquals("친구 요청이 수락되었습니다.", result);

        Optional<Friendship> found = friendshipRepository.findFriendRequest(2, 1);
        assertTrue(found.isPresent());
        assertEquals(FriendshipStatus.수락, found.get().getFriendStatus());
    }

    @Test
    void testRejectFriendRequest() {
        friendshipService.sendFriendRequest(1, 2);

        String result = friendshipService.rejectFriendRequest(1, 2);
        assertEquals("친구 요청이 거절되었습니다.", result);

        Optional<Friendship> found = friendshipRepository.findFriendRequest(2, 1);
        assertTrue(found.isPresent());
        assertEquals(FriendshipStatus.거절, found.get().getFriendStatus());
    }
}
