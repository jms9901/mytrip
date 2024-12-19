package com.lec.spring.mytrip.config;

import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomFilter customFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // spring security 에서 보안 필터 체인을 나타내는 인터페이스
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AccessDeniedHandler accessDeniedHandler) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeRequests(auth -> auth
                        .requestMatchers("/main/**","/user/**", "/user/login", "/oauth2/**", "/css/**", "/js/**", "/img/**", "/admin/adminLogin", "admin/login","/admin/auth", "/uploads/**" ).permitAll()
                        .requestMatchers( "/flight/**", "/board/**", "/aipage/**", "/mypage/**").hasAnyAuthority("ROLE_USER", "ROLE_BUSINESS")
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/user/login")
                        .loginProcessingUrl("/user/login")
                        .defaultSuccessUrl("/main/mainpage", true))
                .logout(logout -> logout
                        .logoutUrl("/user/logout")
                        .invalidateHttpSession(true))
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/user/login")
                        .defaultSuccessUrl("/main/mainpage", true));

        // 사용자 상태 확인 필터 추가
        http.addFilterBefore((Filter)customFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
