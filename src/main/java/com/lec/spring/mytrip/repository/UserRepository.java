package com.lec.spring.mytrip.repository;

import com.lec.spring.mytrip.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface UserRepository {
    //특정 id를 가진 user 리턴
    User findByEmail(String email);

    // 특정  username 의 user 리턴
    User findByUsername(String username);

    //id로 유저찾기
    User findById(int id);

    // 새로운 user 등록
    int save (User user);

    // 특정 고유번호 유저 리턴
    Optional<User> findListById(int userId);

    // user 정보를 업데이트 (비밀번호, 자기소개, 프로필 이미지)
    int updateUser(int userId, String password, String introduction, String profile);
}