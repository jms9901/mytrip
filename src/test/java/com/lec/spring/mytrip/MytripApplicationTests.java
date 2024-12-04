package com.lec.spring.mytrip;

import com.lec.spring.mytrip.domain.User;
import com.lec.spring.mytrip.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MytripApplicationTests {

    @Autowired
    private UserService userService;

    @Test
    @Transactional
    public void testRegister() {
        // 새로운 사용자 객체 생성
        User user = User.builder()
                .email("testuser@example.com")
                .password("testpassword")
                .username("testuser")
                .name("Test User")
                .phoneNumber("010-1234-5678")
                .birthday("1990-01-01")
                .build();

        // 회원가입
        int result = userService.register(user);

        // 회원가입이 성공적으로 되었는지 확인
        assertEquals(1, result);

        // 회원가입한 사용자 정보 조회
        User registeredUser = userService.findByUsername("testuser");

        // 사용자 정보가 올바르게 저장되었는지 확인
        assertNotNull(registeredUser);
        assertEquals("testuser", registeredUser.getUsername());
        assertEquals("testuser@example.com", registeredUser.getEmail());
    }
}

