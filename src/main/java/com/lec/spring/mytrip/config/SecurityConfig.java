package com.lec.spring.mytrip.config;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

//    @Bean
//    @Qualifier("userSecurityFilterChain")
//    public SecurityFilterChain userSecurityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable())
//                .authorizeRequests(auth -> auth
//                        .requestMatchers("/", "/user/login", "/oauth2/**", "/css/**", "/js/**", "/img/**", "admin/adminLogin").permitAll()
//                        .requestMatchers("/user/**").authenticated())
//                .formLogin(form -> form
//                        .loginPage("/user/login")
//                        .loginProcessingUrl("/user/login")
//                        .defaultSuccessUrl("/user/home", true))
//                .logout(logout -> logout
//                        .logoutUrl("/user/logout")
//                        .invalidateHttpSession(true))
//                .oauth2Login(oauth2 -> oauth2
//                        .loginPage("/user/login")
//                        .defaultSuccessUrl("/user/home", true))
//                .exceptionHandling(exception -> exception
//                        .authenticationEntryPoint((request, response, authException) -> {
//                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
//                        }));
//        return http.build();
//    }


    @Bean
    @Qualifier("adminSecurityFilterChain")
    public SecurityFilterChain adminSecurityFilterChain(HttpSecurity http, PrincipalDetailService principalDetailService) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeRequests(auth -> auth
                        .requestMatchers("/", "/user/login", "/oauth2/**","/admin/adminLogin", "/css/**", "/js/**", "/img/**", "/admin/auth").permitAll()
//                        .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                        .anyRequest().permitAll())
                .formLogin(form -> form
                        .loginPage("/admin/adminLogin")
                        .loginProcessingUrl("/admin/login")
                        .defaultSuccessUrl("/admin/userTables", true)
                        .failureUrl("/admin/adminLogin?error=true"))
                .logout(logout -> logout
                        .logoutUrl("/admin/logout")
                        .invalidateHttpSession(true))
                .userDetailsService(principalDetailService)
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                        }));
        return http.build();
    }
}
