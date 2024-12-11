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
    private Feed feed;                     // 피드
    private String boardAttachmentFile;    // 저장된 파일명
    private String filepath;               // 파일 경로
//    private Board board;                   // 소모임
}
