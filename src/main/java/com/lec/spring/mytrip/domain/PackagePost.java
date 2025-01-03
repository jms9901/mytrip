package com.lec.spring.mytrip.domain;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PackagePost {
    private int packageId;         // 패키지 ID
    private int cityId;            // 도시 ID
    private int userId;            // 작성자 ID
    private String packageStatus;          // 패키지 상태 (승인, 대기, 미승인 등)
    private String packageContent;         // 패키지 내용
    private LocalDateTime packageRegdate;  // 패키지 등록 날짜
    private String packageTitle;           // 패키지 제목
    private int packageCost;               // 패키지 비용
    private int packageMaxpeople;          // 최대 참여 가능 인원
//    private LocalDateTime packageStartDay; // 패키지 시작 날짜
//    private LocalDateTime packageEndDay;   // 패키지 종료 날짜
    private int likedCount;         // 패키지 좋아요 수
    private String cityName;        // 도시 이름
    private String packageAttachmentFile;
    private String userName;
    private User user;            // 작성자
    private LocalDate packageStartDay; // 패키지 시작 날짜
    private LocalDate packageEndDay;   // 패키지 종료 날짜
    private int packageLiked;
}
