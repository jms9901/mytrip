package com.lec.spring.mytrip.domain;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Feed {
    private Long id;
    private String subject;
    private String content;
    private Long viewCount;
    private LocalDateTime regDate;
    private String category;

    private User user;
    private City city;

    // 피드 : 첨부파일 1 : N
    @ToString.Exclude
    @Builder.Default
    private List<PostAttachment> fileList = new ArrayList<>();

}
