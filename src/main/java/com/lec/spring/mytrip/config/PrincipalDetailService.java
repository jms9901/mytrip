package com.lec.spring.mytrip.config;

import com.lec.spring.mytrip.domain.User;
import com.lec.spring.mytrip.repository.AuthorityRepository;
import com.lec.spring.mytrip.repository.UserRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PrincipalDetailService implements UserDetailsService {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;

    public PrincipalDetailService(SqlSession sqlSession) {
        this.userRepository = sqlSession.getMapper(UserRepository.class);
        this.authorityRepository = sqlSession.getMapper(AuthorityRepository.class);
    }

    // 해당 메서드에 주어진 username 을 기반으로 사용자의 세부 정보를 로드
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("loadUserByUsername (" + username + ") 호출");

        User user = userRepository.findByUsername(username);

        if (user != null) {
            PrincipalDetails userDetails = new PrincipalDetails(user);
            userDetails.setAuthorityRepository(authorityRepository);
            return userDetails;
        }
        throw new UsernameNotFoundException(username);
    }
}
// git push를 위한 주석 241210 10:45