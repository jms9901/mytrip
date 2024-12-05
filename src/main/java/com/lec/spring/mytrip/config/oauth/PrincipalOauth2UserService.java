package com.lec.spring.mytrip.config.oauth;

import com.lec.spring.mytrip.config.PrincipalDetails;
import com.lec.spring.mytrip.config.oauth.provider.GoogleUserInfo;
import com.lec.spring.mytrip.config.oauth.provider.OAuth2UserInfo;
import com.lec.spring.mytrip.domain.Authority;
import com.lec.spring.mytrip.domain.User;
import com.lec.spring.mytrip.repository.AuthorityRepository;
import com.lec.spring.mytrip.repository.UserRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
    // 인증 후 후처리 과정

    // 회원 조회, 가입 처리를 위한 주입
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;

    public PrincipalOauth2UserService(SqlSession sqlSession) {
        this.userRepository = sqlSession.getMapper(UserRepository.class);
        this.authorityRepository = sqlSession.getMapper(AuthorityRepository.class);
    }

    @Value("${app.oauth2.password}")
    private String oauth2Password;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String provider = userRequest.getClientRegistration().getRegistrationId();

        OAuth2UserInfo oAuth2UserInfo = switch (provider.toLowerCase()) {
            case "google" -> new GoogleUserInfo(oAuth2User.getAttributes());

            default -> null;
        };

        String providerId = oAuth2UserInfo.getProviderId();
        String username = provider + "_" + providerId;
        String password = oauth2Password;
        String email = oAuth2UserInfo.getEmail();
        String name = oAuth2UserInfo.getName();

        User user = userRepository.findByUsername(username);
        if(user == null) {
            User newUser =User.builder()
                    .username(username)
                    .name(name)
                    .email(email)
                    .password(password)
                    .provider(provider)
                    .build();
            int cnt = userRepository.save(newUser);
            Authority auth = authorityRepository.findByUsername("ROLE_USER");
            authorityRepository.addAuthority(auth);

            if(cnt >0) {
                user = userRepository.findByUsername(username);
            }
        }

        PrincipalDetails principalDetails = new PrincipalDetails(user, oAuth2User.getAttributes());

        principalDetails.setAuthorityRepository(authorityRepository);

        return principalDetails;
    }
}

