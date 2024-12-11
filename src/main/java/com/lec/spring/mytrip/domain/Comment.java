package com.lec.spring.mytrip.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {
    private Long commentId;        // 댓글 ID
    private Long boardId;          // 소모임 ID (댓글이 작성된 소모임)
    private Long userId;           // 댓글 작성자 ID
    private String content;        // 댓글 내용
    private LocalDateTime date;    // 댓글 작성 일시
}
