package com.lec.spring.mytrip.config;

import com.lec.spring.mytrip.config.PrincipalDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws jakarta.servlet.ServletException, java.io.IOException {
        PrincipalDetails userDetails = (PrincipalDetails) authentication.getPrincipal();
        HttpSession session = request.getSession();
        session.setAttribute("loggedInUser", userDetails.getUser());

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
// git push를 위한 주석 241210 10:45