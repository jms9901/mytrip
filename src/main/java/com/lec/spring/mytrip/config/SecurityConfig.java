package com.lec.spring.mytrip.config;

import com.lec.spring.mytrip.config.oauth.PrincipalOauth2UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private PrincipalOauth2UserService principalOauth2UserService;

    @Autowired
    private UserStatusVoter userStatusVoter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AccessDecisionManager accessDecisionManager() {
        return new AffirmativeBased(Arrays.asList(userStatusVoter));
    }

    // spring security 에서 보안 필터 체인을 나타내는 인터페이스
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AccessDeniedHandler accessDeniedHandler) throws Exception {
        http
                // 웹 애플리케이션에서 사용자가 의도치 않게 특정 작업을 수행하도록 하는 공격을 방지하는 보안 기능을 비활성화
                .csrf(csrf -> csrf.disable())
                // HTTP 요청에 대한 접근 권한을 설정
                .authorizeRequests(auth -> auth
                        // 특정 패턴의 URL 요청을 매칭
                        .requestMatchers("/", "/user/login", "/oauth2/**", "/css/**", "/js/**", "/img/**", "/admin/adminLogin", "/admin/auth").permitAll()
                        .requestMatchers("/user/login").hasAnyAuthority("ROLE_USER", "ROLE_DORMANT")
                        .requestMatchers("/user/**").hasAnyAuthority("ROLE_USER", "ROLE_BUSINESS")
                        .anyRequest().permitAll())
                // 폼 기반 로그인 기능을 설정
                .formLogin(form -> form
                        // 사용자 정의 로그인 페이지의 URL 을 지정 , 인증되지 않은 사용자를 해당 URL 로 리다이렉트 시킴
                        .loginPage("/user/login")
                        // 로그인 폼 제출을 처리할 URL , 로그인 폼의 action 속성을 해당 URL 의 POST 방식으로 보내야 함
                        .loginProcessingUrl("/user/login")
                        // 로그인 성공 후 리다다이렉션할 URL
                        .defaultSuccessUrl("/main/mainpage", true))
                // 로그아웃 기능 구현
                .logout(logout -> logout
                        .logoutUrl("/user/logout")
                        .invalidateHttpSession(true))
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/user/login")
                        .defaultSuccessUrl("/main/mainpage", true))
                // authorizeRequests에서 설정한 권한이 없는 유저가 접근할 경우 예외 발생
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(accessDeniedHandler))
                .authorizeRequests()
                .accessDecisionManager(accessDecisionManager()); // 커스텀 AccessDeniedHandler 추가

        return http.build();
    }

}
// git push를 위한 주석 241210 10:45