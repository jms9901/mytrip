package com.lec.spring.mytrip.domain.lIiked;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardLiked {
    private int userId;           // 사용자 ID
    private int boardId;          // 게시글 ID
    private LocalDateTime likedDate; // 좋아요 누른 날짜
}