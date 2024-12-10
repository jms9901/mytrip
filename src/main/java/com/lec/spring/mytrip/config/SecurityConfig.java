package com.lec.spring.mytrip.config;

import com.lec.spring.mytrip.config.oauth.PrincipalOauth2UserService;
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

    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

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
                        .anyRequest().authenticated()) // 그 밖의 모든 요청은 인증 필요
                .formLogin(form -> form
                        .loginPage("/user/login") // 로그인 페이지 설정
                        .loginProcessingUrl("/user/login") // 로그인 처리 URL
                        .successHandler(customAuthenticationSuccessHandler)
                        .defaultSuccessUrl("/user/home", true)) // 로그인 성공 시 이동할 URL
                .logout(logout -> logout
                        .logoutUrl("/user/logout") // 로그아웃 처리 URL
                        .invalidateHttpSession(true)) // 세션 무효화
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/user/login") // OAuth2 로그인 페이지 설정
                        .defaultSuccessUrl("/user/home", true)
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(principalOauth2UserService))) // OAuth2 UserService 설정
                .build();
    }

    // 권한 관련 주석
    /*
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, PrincipalDetailService principalDetailService) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeRequests(auth -> auth
                        .requestMatchers("/", "/user/login", "/oauth2/**").permitAll()
                        // 해당 url로 들어오는 요청은 인증만 필요
                        // .requestMatchers("").authenticated()
                        // 해당 url로 들어오는 요청은 인증 뿐아니라 권한도 필요
                        // .requestMatchers("").hasAnyRole("ROLE_USER")
                        // .requestMatchers().hasAnyRole("ROLE_BUSINESS")
                        // .requestMatchers().hasAnyRole("ROLE_ADMIN")
                        .anyRequest().permitAll())
                .formLogin(form -> form
                        .loginPage("/user/login")
                        .loginProcessingUrl("/user/login")
                        .defaultSuccessUrl("/user/home", true))
                .logout(logout -> logout
                        .logoutUrl("/user/logout")
                        .invalidateHttpSession(false))
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/user/login")
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(principalOauth2UserService)))
                .build();
    }
    */
}
