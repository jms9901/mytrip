package com.lec.spring.mytrip.domain;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PackagePost {
    private int packageId;         // 패키지 ID
    private int cityId;            // 도시 ID
    private User user;            // 작성자
    private String packageStatus;          // 패키지 상태 (승인, 대기, 미승인 등)
    private String packageContent;         // 패키지 내용
    private LocalDateTime packageRegdate ;  // 패키지 등록 날짜
    private String packageTitle;           // 패키지 제목
    private int packageCost;               // 패키지 비용
    private int packageMaxpeople;          // 최대 참여 가능 인원
    private LocalDate packageStartDay; // 패키지 시작 날짜
    private LocalDate packageEndDay;   // 패키지 종료 날짜
    private String packageTitle;        // 패키지 제목
    private String packageAttachmentFile; // 패키지 첨부 파일
}
