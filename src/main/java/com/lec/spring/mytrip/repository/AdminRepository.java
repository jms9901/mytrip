package com.lec.spring.mytrip.repository;

import com.lec.spring.mytrip.domain.User;

import java.util.List;

public interface AdminRepository {
    // ROLE_USER 리스트 가져오기
    List<User> findByAuthorityRoleUser(String authority);

    // ROLE_BUSINESS 리스트 가져오기
    List<User> findByAuthorityRoleBusiness(String authority);

    List<Board> findByCategory(String boardCategory);


}
