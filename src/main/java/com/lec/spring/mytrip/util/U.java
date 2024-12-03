package com.lec.spring.mytrip.util;

import com.lec.spring.mytrip.domain.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class U {
    // 현재 request 구하기
    public static HttpServletRequest getRequest(){
        ServletRequestAttributes atts = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return atts.getRequest();
    }

    // 현재 session 구하기
    public static HttpSession getSession(){
        return getRequest().getSession();
    }

    // 현재 로그인한 사용자 user 구하기
//    public static User getLoggedUser(){
//
//    }
}
