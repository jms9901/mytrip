package com.lec.spring.mytrip.repository;

import com.lec.spring.mytrip.domain.Friendship;
import com.lec.spring.mytrip.domain.FriendshipUserResultMap;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface FriendshipRepository {

    // 친구 요청 보내기
    void sendFriendRequest(Friendship friendship);

    // 친구 요청 수락
    void acceptFriendRequest(int toUserId, int fromUserId);

    // 친구 요청 거절
    void rejectFriendRequest(int toUserId, int fromUserId);

    // 특정 친구 요청 조회
    Optional<Friendship> findFriendRequest(int toUserId, int fromUserId);

    // 친구 수
    int countAcceptedFriends(int toUserId);

    List<FriendshipUserResultMap> AcceptedFriends(Long userId);

}
