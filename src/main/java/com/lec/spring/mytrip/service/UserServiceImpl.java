package com.lec.spring.mytrip.service;

import com.lec.spring.mytrip.domain.Authority;
import com.lec.spring.mytrip.domain.User;
import com.lec.spring.mytrip.repository.AuthorityRepository;
import com.lec.spring.mytrip.repository.UserRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(SqlSession sqlSession, PasswordEncoder passwordEncoder) {
        this.userRepository = sqlSession.getMapper(UserRepository.class);
        this.authorityRepository = sqlSession.getMapper(AuthorityRepository.class);
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username.toLowerCase());
    }

    @Override
    public boolean isExist(String username) {
        User user = findByUsername(username.toLowerCase());
        return (user != null);
    }

    @Override
    public int register(User user) {
        user.setUsername(user.getUsername().toLowerCase());
        user.setPassword(passwordEncoder.encode(user.getPassword()));  // 비밀번호 해싱
        userRepository.save(user);

        // 권한 부여 변수 선언
        String authorization;

        // user 회원가입 시 companynumber가 없다면 일반 사용자로 권한 부여 (ROLE_USER)
        if (user.getCompanyNumber() == null) {
            authorization = "ROLE_USER";
        }
        // user 회원가입 시 companynumber가 있다면 기업 사용자로 권한 부여 (ROLE_BUSINESS)
        else {
            authorization = "ROLE_BUSINESS";
        }

        // 사용자 객체에 권한 설정
        user.setAuthorization(authorization);

        // 권한 객체 생성 및 저장
        Authority auth = new Authority();
        auth.setUsername(user.getUsername());
        auth.setAuthority(authorization);
        authorityRepository.addAuthority(auth);

        return 1;
    }

    @Override
    public List<Authority> selectAuthorityById(Long id) {
        User user = userRepository.findById(id);
        if (user == null) return null;

        return authorityRepository.findByUser(user);
    }
}
