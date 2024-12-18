package com.lec.spring.mytrip.config;

import com.lec.spring.mytrip.domain.User;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 초기화 작업이 필요하지 않으면 비워둘 수 있습니다.
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        response.setContentType("text/html; charset=UTF-8");   // 콘텐츠 타입과 문자 인코딩 설정
        if (authentication != null && authentication.getPrincipal() instanceof PrincipalDetails) {
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            User user = principalDetails.getUser();

            if ("대기".equals(user.getStatus())) {
                httpResponse.getWriter().write("<script>alert('관리자의 승인이 필요합니다. 관리자에게 문의 바랍니다.'); window.location='/user/logout';</script>");
                httpResponse.getWriter().flush();
                return;
            } else if ("거절".equals(user.getStatus())) {
                httpResponse.getWriter().write("<script>alert('관리자의 승인이 거절되었습니다. 관리자에게 문의 바랍니다.'); window.location='/user/logout';</script>");
                httpResponse.getWriter().flush();
                return;
            } else if ("ROLE_DORMANT".equals(user.getAuthorization())) {
                httpResponse.getWriter().write("<script>alert('ROLE_DORMANT 권한으로 접근할 수 없습니다. 관리자에게 문의 바랍니다.'); window.location='/user/logout';</script>");
                httpResponse.getWriter().flush();
                return;
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // 정리 작업이 필요하지 않으면 비워둘 수 있습니다.
    }
}
