package com.lec.spring.mytrip.service;

import com.lec.spring.mytrip.domain.Authority;
import com.lec.spring.mytrip.domain.User;

import java.util.List;

public interface UserService {
    //username 으로 해당 user 정보 읽어오기
    User findByUsername(String username);

    // 특정 user가 존재하는지 확인
    boolean isExist(String username);

    // 신규 회원등록
    int register(User user);

    // 특정 user_id의 authorization 리스트로 가져오기
    List<Authority> selectAuthorityById(Long id);

    // 특정 user_id의 status 확인
    // TODO
}
