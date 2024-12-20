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

// spring security 에서 권한이 없는 사용자가 접근하려고 할 때 처리하는 handler
// 해당 클래스를 bean 으로 등록 , spring context 에 의해 관리되도록 설정
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(
            HttpServletRequest request,     // 현재 HTTP 요청 객체
            HttpServletResponse response,   // 현재 HTTP 응답 객체
            AccessDeniedException accessDeniedException // 접근 거부 발생 시 발생하는 예외
    ) throws IOException, ServletException {
        response.setContentType("text/html; charset=UTF-8");   // 콘텐츠 타입과 문자 인코딩 설정

        // 사용자 상태 확인
        // 현재 인증된 사용자의 anthentication 객체를 반환하여 spring context holder 에서 현재 인정 정보를 가져온다
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof PrincipalDetails) {
            // PrincipalDetails 객체로 캐스팅하여 사용자의 세부 정보에 접근
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            User user = principalDetails.getUser();
            if ("대기".equals(user.getStatus())) {
                response.getWriter().write("<script>alert('관리자의 승인이 필요합니다. 관리자에게 문의 바랍니다.'); window.location='/user/logout';</script>");
            } else if ("거절".equals(user.getStatus())) {
                response.getWriter().write("<script>alert('관리자의 승인이 거절되었습니다. 관리자에게 문의 바랍니다.'); window.location='/user/logout';</script>");
            }  else if ("ROLE_DORMANT".equals(user.getAuthorization())) {
                response.getWriter().write("<script>alert('ROLE_DORMANT 권한으로 접근할 수 없습니다. 관리자에게 문의 바랍니다.'); window.location='/user/logout';</script>");
            }
            else {
                response.getWriter().write("<script>alert('접근 권한이 없습니다. 로그인 페이지로 이동합니다.'); window.location='/user/logout';</script>");
            }
        } else {
            response.getWriter().write("<script>alert('접근 권한이 없습니다. 로그인 페이지로 이동합니다.'); window.location='/user/logout';</script>");
        }
        // 데이터 전송 후 추가적으로 더 이상 데이터를 전송하지 않음을 보장하기 위해 사용
        response.getWriter().flush();
    }
}
