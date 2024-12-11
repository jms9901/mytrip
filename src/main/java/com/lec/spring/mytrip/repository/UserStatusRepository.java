package com.lec.spring.mytrip.repository;

import com.lec.spring.mytrip.domain.Authority;
import com.lec.spring.mytrip.domain.User;
import com.lec.spring.mytrip.domain.UserStatus;

import java.util.List;

public interface UserStatusRepository {
    UserStatus findByUsername(String username);

    // 특정 사용자의 권한 리스트 가져오기
    List<UserStatus> findByUser(User user);

    // 특정 user_id에 권한 주기
    int addUserStatus(UserStatus userStatus);
}
// git push를 위한 주석 241210 10:45