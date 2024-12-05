package com.lec.spring.mytrip;

import com.lec.spring.mytrip.domain.User;
import com.lec.spring.mytrip.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class MytripApplicationTests {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private MockMvc mockMvc;

    @Test
    public void testRegister() {
        // 새로운 사용자 객체 생성
        User user = User.builder()
            .email("user4@example.com")
            .password(passwordEncoder.encode("testpassword"))
            .username("user4")
            .name("Test User")
            .phoneNumber("010-1234-5684")
            .birthday("1990-01-11")
            .build();

        // 회원가입
        int result = userService.register(user);
        System.out.println(user);

        // 회원가입이 성공적으로 되었는지 확인
        assertEquals(1, result);

        // 회원가입한 사용자 정보 조회
        User registeredUser = userService.findByUsername("testuser");

        // 사용자 정보가 올바르게 저장되었는지 확인

    }

    @Test
    @WithMockUser(username = "testuser", password = "testpassword", roles = "USER")
    public void testLogin() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        // 로그인 시도
        mockMvc.perform(SecurityMockMvcRequestBuilders.formLogin()
                        .user("user1")
                        .password("testpassword"))
                .andExpect(status().isOk());
    }
}
