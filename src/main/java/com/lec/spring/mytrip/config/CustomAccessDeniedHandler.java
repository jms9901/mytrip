package com.lec.spring.mytrip.config;

import com.lec.spring.mytrip.domain.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        // 사용자 상태 확인
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof PrincipalDetails) {
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            User user = principalDetails.getUser();
            if ("대기".equals(user.getStatus())) {
                response.getWriter().write("<script>alert('관리자의 승인이 필요합니다. 관리자에게 문의 바랍니다.'); window.location='/user/logout';</script>");
            } else if ("거절 ".equals(user.getStatus())) {
                response.getWriter().write("<script>alert('관리자의 승인이 거절되었습니다. 관리자에게 문의 바랍니다.'); window.location='/user/logout';</script>");
            }
            else {
                response.getWriter().write("<script>alert('접근 권한이 없습니다. 로그인 페이지로 이동합니다.'); window.location='/user/logout';</script>");
            }
        } else {
            response.getWriter().write("<script>alert('접근 권한이 없습니다. 로그인 페이지로 이동합니다.'); window.location='/user/logout';</script>");
        }

        response.getWriter().flush();
    }
}
