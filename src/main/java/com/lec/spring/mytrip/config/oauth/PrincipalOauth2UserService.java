package com.lec.spring.mytrip.config.oauth;

import com.lec.spring.mytrip.config.oauth.provider.GoogleUserInfo;
import com.lec.spring.mytrip.config.oauth.provider.KakaoUserInfo;
import com.lec.spring.mytrip.config.oauth.provider.OAuth2UserInfo;
import com.lec.spring.mytrip.domain.User;
import com.lec.spring.mytrip.repository.UserRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
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
        String username = provider + "_" + email.replace("@", "_").replace(".", "_");
        String profile = oAuth2UserInfo.getProfile();
        // 이메일에서 username 생성 ('_'를 기준으로)

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

            // 새 사용자 정보 콘솔에 출력
            System.out.println("New user created:");
            System.out.println("Username: " + username);
            System.out.println("Email: " + email);
            System.out.println("Provider: " + provider);
            System.out.println("Name: " + name);
            System.out.println("Profile: " + profile);
        } else {
            // 기존 사용자 정보 콘솔에 출력
            System.out.println("Existing user logged in:");
            System.out.println("Username: " + user.getUsername());
            System.out.println("Email: " + user.getEmail());
            System.out.println("Provider: " + user.getProvider());
            System.out.println("Name: " + user.getName());
            System.out.println("Profile: " + profile);
        }

        Map<String, Object> attributes = new HashMap<>(oAuth2User.getAttributes());
        attributes.put("email", email);


        return new DefaultOAuth2User(
                Collections.singleton(new OAuth2UserAuthority(attributes)),attributes,"email");
    }

    private OAuth2UserInfo getOAuth2UserInfo(OAuth2UserRequest userRequest, Map<String, Object> attributes) {
        String provider = userRequest.getClientRegistration().getRegistrationId().toLowerCase();
        return switch(provider) {
            case "google" ->new GoogleUserInfo(attributes);
            case "kakao" ->new KakaoUserInfo(attributes);
            default -> throw new OAuth2AuthenticationException("로그인 provider를 지원하지 않습니다.");
        };

    }
}
