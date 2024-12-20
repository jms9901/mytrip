package com.lec.spring.mytrip.util;

import com.lec.spring.mytrip.domain.User;
import com.lec.spring.mytrip.config.PrincipalDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
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
        HttpSession session = getSession();
        User loggedUser = (User) session.getAttribute("loggedInUser");

        if (loggedUser != null) {
            session.setAttribute("loggedInUser", loggedUser);
            System.out.println("로그인한 유저 : " + loggedUser);
            return loggedUser;
        }

        // 세션에 정보가 없는 경우 SecurityContextHolder를 통해 확인
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof PrincipalDetails) {
            PrincipalDetails userDetails = (PrincipalDetails) principal;
            loggedUser = userDetails.getUser();
        } else if (principal instanceof DefaultOAuth2User) {
            DefaultOAuth2User oAuth2User = (DefaultOAuth2User) principal;
            Map<String, Object> attributes = oAuth2User.getAttributes();
            Map<String, Object> user = (Map<String, Object>) attributes.get("user");
            String email = (String) user.get("email");
            String name = (String) user.get("name");
            int id = (int) user.get("id");

            // 사용자 정보를 생성할 때 필요한 추가 속성들을 가져옵니다.
            Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
            if (properties != null && properties.get("nickname") != null) {
                name = (String) properties.get("nickname");
            }

            String profile = null;
            if (properties != null && properties.get("profile_image") != null) {
                profile = (String) properties.get("profile_image");
            }

            // 사용자 ID를 가져옵니다.

            if (attributes.containsKey("user")) {
                Map<String, Object> userMap = (Map<String, Object>) attributes.get("user");
                id = (int) userMap.get("id");
            }

            loggedUser = new User(email, name, id);
        }

        // 세션에 사용자 정보를 저장 (옵션)
        if (loggedUser != null) {
            session.setAttribute("loggedInUser", loggedUser);
        }

        return loggedUser;
    }

}