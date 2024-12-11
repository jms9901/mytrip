package com.lec.spring.mytrip.util;

import com.lec.spring.mytrip.domain.User;
import com.lec.spring.mytrip.config.PrincipalDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Map;

public class U {

    // 현재 request 구하기
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attrs.getRequest();
    }

    // 현재 session 구하기
    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    // 현재 로그인 한 사용자 User 구하기
    public static User getLoggedUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof PrincipalDetails) {
            // 일반 로그인 사용자
            PrincipalDetails userDetails = (PrincipalDetails) principal;
            return userDetails.getUser();
        } else if (principal instanceof DefaultOAuth2User) {
            // OAuth2 로그인 사용자
            DefaultOAuth2User oAuth2User = (DefaultOAuth2User) principal;
            Map<String, Object> attributes = oAuth2User.getAttributes();

            // 필요한 속성을 가져와 User 객체를 생성합니다.
            String email = (String) attributes.get("email");
            String name = (String) attributes.get("name");

            // kakao로 로그인한 유저는 properties안에 nickname 이 있기 때문에 name을 재정의 해야 함
            Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
            if (properties != null && properties.get("nickname") != null) {
                name = (String) properties.get("nickname");
            }

            return new User(email, name); // 적절한 User 생성자를 호출하여 반환
        } else {
            return null;
        }
    }

}
// git push를 위한 주석 241210 10:45