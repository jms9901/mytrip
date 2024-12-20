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
    private int boardAttachmentId;        // 첨부파일 ID
    private int boardId;
    private Feed feed;                     // 피드
    private String fileName;    // 저장된 파일명
    private String filePath;               // 파일 경로
}
