package com.lec.spring.mytrip.service;

import com.lec.spring.mytrip.domain.Friendship;
import com.lec.spring.mytrip.domain.FriendshipStatus;
import com.lec.spring.mytrip.domain.FriendshipUserResultMap;
import com.lec.spring.mytrip.repository.FriendshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FriendshipService {
    @Autowired
    private FriendshipRepository friendshipRepository;

    //친구 요청 보내기
    public String sendFriendRequest(int fromUserId, int toUserId){
        Optional<Friendship> existingRequest = friendshipRepository.findFriendRequest(toUserId, fromUserId);

        if(existingRequest.isPresent()){
            return "요청 수락 대기중";
        }

        Friendship friendship = new Friendship();
        friendship.setFromUserId(fromUserId);
        friendship.setToUserId(toUserId);
        friendship.setFriendStatus(FriendshipStatus.대기);
        friendship.setFriendshipDate(LocalDateTime.now());

        friendshipRepository.sendFriendRequest(friendship);
        return "친구 요청 완료";
    }

    // 요청 수락
    public String acceptFriendRequest(int fromUserId, int toUserId){
        Optional<Friendship> existingRequest = friendshipRepository.findFriendRequest(toUserId, fromUserId);

    if(existingRequest.isEmpty()){
        return "요청이 존재하지 않습니다";
    }
    Friendship friendship = existingRequest.get();
    friendship.setFriendStatus(FriendshipStatus.수락);
    friendship.setFriendshipDate(LocalDateTime.now());

   friendshipRepository.acceptFriendRequest(toUserId, fromUserId);
   return "수락 완료";
    }

    //요청 거절
    public String rejectFriendRequest(int fromUserId, int toUserId){
        Optional<Friendship> existingRequest = friendshipRepository.findFriendRequest(toUserId, fromUserId);

        if(existingRequest.isEmpty()){
            return "요청이 존재하지 않습니다";
        }

        Friendship friendship = existingRequest.get();
        friendship.setFriendStatus(FriendshipStatus.거절);
        friendshipRepository.rejectFriendRequest(toUserId, fromUserId);
        return "거절되었습니다";
    }

    // 친구 수 조회
    public int countAcceptedFriends(int userId){
        return  friendshipRepository.countAcceptedFriends(userId);
    }

    //친구 목록 조회
    public List<FriendshipUserResultMap> AcceptedFriends(Long userId){
        return friendshipRepository.AcceptedFriends(userId);
    }

    //친구 목록 조회
    public List<FriendshipUserResultMap> requestView(Long userId){
        return friendshipRepository.requestView(userId);
    }


}
