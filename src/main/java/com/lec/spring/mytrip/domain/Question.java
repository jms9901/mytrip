package com.lec.spring.mytrip.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Question {
    private String text;     // 질문 내용
    private String a;  // 보기 A
    private String b;  // 보기 B
}