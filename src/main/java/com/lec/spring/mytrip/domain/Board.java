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
public class Board {
    private int boardId;        // boardID
    private int userId;         // 작성자 ID
    private int cityId;         // 도시 ID
    private String subject;      // 소모임 제목
    private String content;      // 소모임 내용
    private int viewCount;       // 조회수
    private LocalDateTime date;  // 게시일
    private String category;     // 카테고리 (예: "소모임" / "피드")
    private String userName;
    private String cityName;
}
