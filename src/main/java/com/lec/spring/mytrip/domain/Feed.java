package com.lec.spring.mytrip.domain;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Feed {
    private int boardId;                       // 게시물 ID
    private User user;
    private City city;
    private String boardSubject;                // 게시물 제목
    private String boardContent;                // 게시물 내용
    private int boardViewCount;                // 게시물 조회수
    private LocalDateTime boardDate;            // 게시물 작성일.
    private BoardCategory boardCategory;        // 게시물 카테고리 (피드, 소모임 => Enum 타입)
    private List<PostAttachment> attachments;   // 첨부파일

    // 파일 업로드를 위한 임시 필드
    private transient List<MultipartFile> uploadFiles;
    private transient Long[] deleteFileIds;
}
