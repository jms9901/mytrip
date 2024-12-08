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
        if(username == null){
            return null;
        }

        return userRepository.findByUsername(username.toLowerCase());
    }

    @Override
    public User findByEmail(String email) {
        if(email == null){
            return null;
        }

        return userRepository.findByEmail(email.toLowerCase());
    }

//    @Override
//    public boolean isExist(String username) {
//        User user = findByUsername(username.toLowerCase());
//        return (user != null);
//    }

    @Override
    public int register(User user) {
        user.setUsername(user.getUsername().toLowerCase());
        user.setPassword(passwordEncoder.encode(user.getPassword()));  // 비밀번호 해싱

        // 권한 설정
        String authorization = (user.getCompanyNumber() == null || user.getCompanyNumber().isEmpty()) ? "ROLE_USER" : "ROLE_BUSINESS";
        user.setAuthorization(authorization);  // 권한 설정

        String status = (user.getAuthorization().equalsIgnoreCase( "ROLE_BUSINESS")) ? "대기" : "승인";
        user.setStatus(status);

        userRepository.save(user);
        System.out.println(user);
        // 권한 객체 생성 및 저장
//        Authority auth = new Authority();
//        auth.setUsername(user.getUsername());
//        auth.setAuthority(authorization);
//        authorityRepository.addAuthority(auth);


        // 회원가입 성공 로그
        System.out.println("회원가입 성공: " + user);

        return 1;
    }

    @Override
    public int updateUser(Long userId, String newPassword, String introduction, String profile) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return 0;  // 사용자 없음
        }

        // 비밀번호 업데이트
        if (newPassword != null && !newPassword.isEmpty()) {
            user.setPassword(passwordEncoder.encode(newPassword));  // 비밀번호 해싱
        }

        // 자기소개 업데이트
        if (introduction != null) {
            user.setIntroduction(introduction);
        }

        // 프로필 이미지 업데이트
        if (profile != null) {
            user.setProfile(profile);
        }

        // 업데이트된 사용자 정보 저장
        return userRepository.updateUser(userId, user.getPassword(), user.getIntroduction(), user.getProfile());
    }

//    @Override
//    public List<Authority> selectAuthorityById(Long id) {
//        User user = userRepository.findById(id);
//        if (user == null) return null;
//
//        return authorityRepository.findByUser(user);
//    }
}

