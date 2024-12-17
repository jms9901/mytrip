package com.lec.spring.mytrip.config.oauth;

import com.lec.spring.mytrip.config.PrincipalDetails;
import com.lec.spring.mytrip.config.oauth.provider.GoogleUserInfo;
import com.lec.spring.mytrip.config.oauth.provider.KakaoUserInfo;
import com.lec.spring.mytrip.config.oauth.provider.OAuth2UserInfo;
import com.lec.spring.mytrip.domain.User;
import com.lec.spring.mytrip.repository.UserRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    public PrincipalOauth2UserService(SqlSession sqlSession) {
        this.userRepository = sqlSession.getMapper(UserRepository.class);
    }

    @Value("${app.oauth2.password}")
    private String password;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        OAuth2UserInfo oAuth2UserInfo = getOAuth2UserInfo(userRequest, oAuth2User.getAttributes());

        String email = oAuth2UserInfo.getEmail();
        String provider = oAuth2UserInfo.getProvider();
        String name = oAuth2UserInfo.getName();
        String username = email;
        String profile = oAuth2UserInfo.getProfile();

        User user = userRepository.findByEmail(email);
        if (user == null) {
            String authorization = "ROLE_USER";
            String status = "승인";
            user = User.builder()
                    .username(username)
                    .email(email)
                    .password(password)
                    .name(name)
                    .provider(provider)
                    .authorization(authorization)
                    .status(status)
                    .profile(profile)
                    .build();
            userRepository.save(user);
            user = userRepository.findByEmail(email); // 새로 저장한 사용자 정보를 다시 가져옴

            System.out.println("New user created:");
            System.out.println("ID: " + user.getId());
            System.out.println("Username: " + username);
            System.out.println("Email: " + email);
            System.out.println("Provider: " + provider);
            System.out.println("Name: " + name);
            System.out.println("Profile: " + profile);
        } else {
            System.out.println("Existing user logged in:");
            System.out.println("ID: " + user.getId());
            System.out.println("Username: " + user.getUsername());
            System.out.println("Email: " + user.getEmail());
            System.out.println("Provider: " + user.getProvider());
            System.out.println("Name: " + user.getName());
            System.out.println("Profile: " + profile);
        }

        // 사용자 정보를 principal에 저장할 속성 구조로 변경
        Map<String, Object> userAttributes = new HashMap<>();
        userAttributes.put("id", user.getId());
        userAttributes.put("email", user.getEmail());
        userAttributes.put("password", user.getPassword());
        userAttributes.put("username", user.getUsername());
        userAttributes.put("regDate", user.getRegDate());
        userAttributes.put("provider", user.getProvider());
        userAttributes.put("providerId", user.getProviderId());
        userAttributes.put("profile", user.getProfile());
        userAttributes.put("birthday", user.getBirthday());
        userAttributes.put("introduction", user.getIntroduction());
        userAttributes.put("authorization", user.getAuthorization());
        userAttributes.put("companyNumber", user.getCompanyNumber());
        userAttributes.put("status", user.getStatus());
        userAttributes.put("user_name", name);

        // 추가 속성 설정
        Map<String, Object> additionalAttributes = new HashMap<>(oAuth2User.getAttributes());
        additionalAttributes.put("email", email);
        userAttributes.put("attributes", additionalAttributes);

        Map<String, Object> principalAttributes = new HashMap<>();
        principalAttributes.put("user", userAttributes);
        principalAttributes.put("authorities", Collections.singletonMap("authority", "ROLE_USER"));
        principalAttributes.put("attributes", additionalAttributes);
        principalAttributes.put("name", email);

        DefaultOAuth2User defaultOAuth2User = new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                principalAttributes,
                "name");

        // OAuth2AuthenticationToken 사용
        OAuth2AuthenticationToken authentication = new OAuth2AuthenticationToken(
                defaultOAuth2User,
                defaultOAuth2User.getAuthorities(),
                userRequest.getClientRegistration().getRegistrationId());

        // SecurityContext에 인증 정보 설정
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);

        return defaultOAuth2User;
    }

    private OAuth2UserInfo getOAuth2UserInfo(OAuth2UserRequest userRequest, Map<String, Object> attributes) {
        String provider = userRequest.getClientRegistration().getRegistrationId().toLowerCase();
        return switch(provider) {
            case "google" -> new GoogleUserInfo(attributes);
            case "kakao" -> new KakaoUserInfo(attributes);
            default -> throw new OAuth2AuthenticationException("로그인 provider를 지원하지 않습니다.");
        };
    }
}
