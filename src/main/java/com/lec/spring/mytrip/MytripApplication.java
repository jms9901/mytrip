package com.lec.spring.mytrip;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@MapperScan("com.lec.spring.mytrip.repository")
public class MytripApplication {

    public static void main(String[] args) {
        SpringApplication.run(MytripApplication.class, args);
    }

}
