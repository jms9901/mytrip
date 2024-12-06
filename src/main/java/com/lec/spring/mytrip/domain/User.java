package com.lec.spring.mytrip.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private Long id;  // 사용자 ID

    private String email;  // 사용자 이메일

    private String password;  // 사용자 비밀번호

    @ToString.Exclude
    @JsonIgnore
    private String re_password;  // 비밀번호 확인 입력

    private String username;  // 사용자 아이디

    private String name;  // 사용자 이름

    private LocalDateTime regDate;  // 회원 가입일

    private String provider;  // 소셜 로그인 제공자 (예: Google, Facebook)

    private String providerId;  // 소셜 로그인 제공자 ID

    private String profile;  // 사용자 프로필 이미지 경로

    private String birthday;  // 생년월일

    private String introduction;  // 사용자 자기소개

    private String authorization;  // 사용자 권한

    private String companyNumber;  // 기업 사업자 번호

    private String status;

}
