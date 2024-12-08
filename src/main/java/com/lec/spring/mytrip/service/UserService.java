package com.lec.spring.mytrip.service;

import com.lec.spring.mytrip.domain.Authority;
import com.lec.spring.mytrip.domain.User;

import java.util.List;

public interface UserService {
    //username 으로 해당 user 정보 읽어오기
    User findByUsername(String username);

    // email로 해당 user 정보 읽어오기
    User findByEmail(String email);

    // 신규 회원등록
    int register(User user);

    int updateUser(Long userId, String newPassword, String introduction ,String Profile);
    // TODO 특정 user_id의 status 확인

}
