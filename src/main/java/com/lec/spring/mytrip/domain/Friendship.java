package com.lec.spring.mytrip.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Friendship {
    private int requestFriendId;   // PK
    private int toUserId;          // 수신자 ID
    private int fromUserId;        // 발신자 ID
    private FriendshipStatus friendStatus;  // 친구 상태
    private LocalDateTime friendshipDate;   // 친구 관계 형성 일자
}
