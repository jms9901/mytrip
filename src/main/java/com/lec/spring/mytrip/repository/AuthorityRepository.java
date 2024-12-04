package com.lec.spring.mytrip.repository;

import com.lec.spring.mytrip.domain.Authority;
import com.lec.spring.mytrip.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AuthorityRepository {
    // 특정 username 의 권한 정보 가져오기
    Authority findByUsername(String username);

    // 특정 사용자의 권한 리스트 가져오기
    List<Authority> findByUser(User user);

    // 특정 user_id에 권한 주기
    int addAuthority(Authority authority);
}
