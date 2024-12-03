package com.lec.spring.mytrip.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// 소모임 + 피드 첨부파일 domain
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostAttachment {
    private Long id;
    private String fileName;
}
