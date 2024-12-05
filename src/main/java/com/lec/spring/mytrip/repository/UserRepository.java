package com.lec.spring.mytrip.repository;

import com.lec.spring.mytrip.domain.User;
import org.apache.ibatis.annotations.Mapper;


public interface UserRepository {
    //특정 id를 가진 user 리턴
    User findByEmail(String email);

    // 특정  username 의 user 리턴
    User findByUsername(String username);

    // 새로운 user 등록
    int save (User user);

    
}
