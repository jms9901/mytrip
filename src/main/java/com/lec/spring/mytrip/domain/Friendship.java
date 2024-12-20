package com.lec.spring.mytrip.domain;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Friendship {

    @Setter
    @Getter
    private User fromUser;  // 발신자 (User 객체)

    @Setter
    @Getter
    private User toUser;    // 수신자 (User 객체)

    private int requestFriendId;   // PK
    private int toUserId;          // 수신자 ID
    private int fromUserId;        // 발신자 ID
    private FriendshipStatus friendStatus;  // 친구 상태
    private LocalDateTime friendshipDate;   // 친구 관계 형성 일자
    private String fromUserName;  // 발신자 이름
    private String toUserName;    // 수신자 이름
}
