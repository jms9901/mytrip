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

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AccessDeniedHandler accessDeniedHandler) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeRequests(auth -> auth
                        .requestMatchers("/", "/user/login", "/oauth2/**", "/css/**", "/js/**", "/img/**", "/admin/adminLogin", "/admin/auth").permitAll()
                        .requestMatchers("/user/home", "/user/login").hasAnyAuthority("ROLE_USER", "ROLE_DORMANT")
                        .requestMatchers("/user/**").hasAnyAuthority("ROLE_USER", "ROLE_BUSINESS")
                        .anyRequest().permitAll())
                .formLogin(form -> form
                        .loginPage("/user/login")
                        .loginProcessingUrl("/user/login")
                        .defaultSuccessUrl("/main/mainpage", true))
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