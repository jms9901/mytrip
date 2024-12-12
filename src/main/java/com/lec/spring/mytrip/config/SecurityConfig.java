package com.lec.spring.mytrip.config;

import com.lec.spring.mytrip.config.oauth.PrincipalOauth2UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private PrincipalOauth2UserService principalOauth2UserService;

    @Autowired
    private Environment env;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        String redirectUri = env.getProperty("spring.security.oauth2.client.registration.kakao.redirect-uri");
        return http
                .csrf(csrf -> csrf.disable()) // CSRF 비활성화
                .authorizeRequests(auth -> auth
                        .requestMatchers("/", "/user/login", "/oauth2/**" ,"/css/**", "/js/**", "/img/**" ).permitAll() // 해당 URL에 대해 모두 접근 허용
                        .requestMatchers("/aipage/**").authenticated()
                        .anyRequest().permitAll()) // 그 밖의 모든 요청은 인증 필요
                .formLogin(form -> form
                        .loginPage("/user/login") // 로그인 페이지 설정
                        .loginProcessingUrl("/user/login") // 로그인 처리 URL
                        .defaultSuccessUrl("/main/mainpage", true)) // 로그인 성공 시 이동할 URL
                .logout(logout -> logout
                        .logoutUrl("/user/logout") // 로그아웃 처리 URL
                        .invalidateHttpSession(true)) // 세션 무효화
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/user/login") // OAuth2 로그인 페이지 설정
                        .defaultSuccessUrl("/main/mainpage", true)
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(principalOauth2UserService))) // OAuth2 UserService 설정
                .build();
    }

}
// git push를 위한 주석 241210 10:45