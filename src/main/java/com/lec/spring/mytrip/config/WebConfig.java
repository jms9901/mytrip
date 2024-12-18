package com.lec.spring.mytrip.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 'uploads/profiles' 폴더에 저장된 파일을 static으로 제공
        registry.addResourceHandler("/uploads/profiles/**")
                .addResourceLocations("file:uploads/profiles/"); // 실제 경로 지정
    }
}