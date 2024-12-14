package com.lec.spring.mytrip.config;

import com.lec.spring.mytrip.domain.User;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class UserStatusVoter implements AccessDecisionVoter<Object> {

    private final PrincipalDetailService principalDetailService;

    public UserStatusVoter(PrincipalDetailService principalDetailService) {
        this.principalDetailService = principalDetailService;
    }

    @Override
    public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {
        // Principal이 UserDetails인지 확인
        if (authentication.getPrincipal() instanceof PrincipalDetails) {
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            // User 클래스가 UserDetails를 구현했다고 가정합니다
            User user =  principalDetails.getUser();

            // 사용자 상태에 따른 접근 제한 로직 추가
            if (("ROLE_BUSINESS".equals(user.getAuthorization()) && "대기".equals(user.getStatus())) || ("ROLE_BUSINESS".equals(user.getAuthorization()) && "거절".equals(user.getStatus()))) {
                return ACCESS_DENIED;
            }
        }
        return ACCESS_GRANTED;
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
