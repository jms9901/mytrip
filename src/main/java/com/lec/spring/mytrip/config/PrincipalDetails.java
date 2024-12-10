package com.lec.spring.mytrip.config;

import com.lec.spring.mytrip.domain.Authority;
import com.lec.spring.mytrip.domain.User;
import com.lec.spring.mytrip.repository.AuthorityRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;


public class PrincipalDetails implements UserDetails, OAuth2User {

    private AuthorityRepository authorityRepository;

    public void setAuthorityRepository(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    // 로그인한 사용자 정보
    private User user;
    public User getUser() {return user;}

    // 일반 회원가입
    public PrincipalDetails(User user) {
        this.user = user;
    }

    // 소셜 회원가입
    public PrincipalDetails(User user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }

    // 해당 user의 권한들을 리턴
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        List<Authority> list = authorityRepository.findByUser(user);

        for (Authority auth : list) {
            collect.add(new GrantedAuthority() {
                @Override
                public String getAuthority() {
                    return auth.getAuthority();
                }
            });
        }
        return collect;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return user.getName();
    }

    private Map<String, Object> attributes;

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }
}
