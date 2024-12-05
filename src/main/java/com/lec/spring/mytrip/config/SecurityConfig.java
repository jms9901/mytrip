package com.lec.spring.mytrip.config;

import com.lec.spring.mytrip.config.oauth.PrincipalOauth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // oauth 로그인
    // AuthenticationManager 빈 생성
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // OAuth2 Client
    private PrincipalOauth2UserService principalOauth2UserService;

    @Autowired
    public void setPrincipalOauth2UserService(PrincipalOauth2UserService principalOauth2UserService) {
        this.principalOauth2UserService = principalOauth2UserService;
    }

    // SecurityFilterChain을 bean으로 등록해서 설정
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, PrincipalDetailService principalDetailService) throws Exception{
        return http
                // csrf 비활성화
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // 해당 url로 들어오는 요청은 인증만 필요
//                        .requestMatchers().authenticated()
//                        // 해당 url로 들어오는 요청은 인증 뿐아니라 권한도 필요
//                        .requestMatchers("").hasAnyRole("ROLE_USER")
//                        .requestMatchers().hasAnyRole("ROLE_BUSINESS")
//                        .requestMatchers().hasAnyRole("ROLE_ADMIN")
                        // 그 밖의 요청들은 모두 허용
                        .anyRequest().permitAll())
                .formLogin(form -> form
                        .loginPage("/user/login")
                        .loginProcessingUrl("/user/login")
                        .defaultSuccessUrl("/home", true))
                .logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer
                        .logoutUrl("/user/logout")
                        .invalidateHttpSession(false))
//                .exceptionHandling(httpSecurityExceptionHandlingConfigurer -> httpSecurityExceptionHandlingConfigurer
//                        .accessDeniedHandler())
                .oauth2Login(httpSecurityOAuth2LoginConfigurer -> httpSecurityOAuth2LoginConfigurer
                        .loginPage("/user/login")
                        .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig
                            .userService(principalOauth2UserService)))
                .build();

    }
}
