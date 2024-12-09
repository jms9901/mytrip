package com.lec.spring.mytrip.domain;

import com.lec.spring.mytrip.service.FeedService;
import com.lec.spring.mytrip.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class FeedValidator implements Validator {

    private FeedService feedService;
    private UserService userService;

    @Autowired
    public void setFeedService(FeedService feedService) {
        this.feedService = feedService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        // 검증할 객체의 클래스 타입 확인
        return Feed.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Feed feed = (Feed) target;

        // 현재 로그인한 사용자 확인

        // 피드 작성, 수정, 삭제 권한 검증
        // 피드 제목 -> 필수 입력
        // 피드 내용 -> 필수 입력
        // 도시 선택 -> 필수 선택
        //


        // 피드 작성자 = 현재 로그인 사용자 일치 여부 확인

        // 피드 작성 폼 검증

        // 뷰 레이어에서 사용할 수 있는 권한 체크
        // 페이지 소유자 = 현재 로그인 사용자 경우 피드 작성 버튼 표시


    }
}
