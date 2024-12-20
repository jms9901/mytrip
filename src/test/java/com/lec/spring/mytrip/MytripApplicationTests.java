package com.lec.spring.mytrip;

import com.lec.spring.mytrip.config.oauth.PrincipalOauth2UserService;
import com.lec.spring.mytrip.domain.City;
import com.lec.spring.mytrip.domain.User;
import com.lec.spring.mytrip.repository.UserRepository;
import com.lec.spring.mytrip.service.CityService;
import com.lec.spring.mytrip.service.UserService;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class MytripApplicationTests {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserService userService;

    @Autowired
    private CityService cityService;

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

    @Mock
    private SqlSession sqlSession;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ClientRegistrationRepository clientRegistrationRepository;

    @InjectMocks
    private PrincipalOauth2UserService principalOauth2UserService;

    private OAuth2UserRequest getOAuth2UserRequest() {
        ClientRegistration clientRegistration = ClientRegistration.withRegistrationId("google")
                .clientId("client-id")
                .clientSecret("client-secret")
                .scope("email", "profile")
                .authorizationUri("https://accounts.google.com/o/oauth2/auth")
                .tokenUri("https://oauth2.googleapis.com/token")
                .userInfoUri("https://www.googleapis.com/oauth2/v3/userinfo")
                .userNameAttributeName("sub")
                .clientName("Google")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUri("{baseUrl}/login/oauth2/code/{registrationId}")
                .build();

        OAuth2AccessToken accessToken = new OAuth2AccessToken(
                OAuth2AccessToken.TokenType.BEARER,
                "access-token",
                null,
                null
        );

        return new OAuth2UserRequest(clientRegistration, accessToken);
    }

    @BeforeEach
    public void setup() {
        when(sqlSession.getMapper(UserRepository.class)).thenReturn(userRepository);
    }

    @Test
    public void testLoadUser() throws OAuth2AuthenticationException {
        // Given
        OAuth2UserRequest userRequest = getOAuth2UserRequest();

        Map<String, Object> attributes = Map.of(
                "sub", "123456789",
                "email", "test_user@example.com",
                "name", "Test User"
        );

        OAuth2User oAuth2User = new DefaultOAuth2User(
                Collections.singleton(new OAuth2UserAuthority(attributes)),
                attributes,
                "email"
        );

        DefaultOAuth2UserService defaultOAuth2UserService = mock(DefaultOAuth2UserService.class);
        when(defaultOAuth2UserService.loadUser(any(OAuth2UserRequest.class))).thenReturn(oAuth2User); // When

        // When
        OAuth2User user = principalOauth2UserService.loadUser(userRequest);

        // Then
        assertNotNull(user);
        assertNotNull(user.getAttributes().get("email"));
        assertNotNull(user.getAttributes().get("name"));
    }

    @Test
    public void findCity(){
        // Given
        String cityName = "발리";

        // When
        City city = cityService.findCityName(cityName);

        // Then
        assertNotNull(city, "도시 정보를 찾을 수 없습니다.");

        System.out.println("도시 이미지 URL: " + city.getCityImg());
    }

}
