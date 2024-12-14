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

        // 현재 로그인한 사용자 정보 확인
        User currentUser = userService.findByUsername(feed.getUser().getUsername());
        if (currentUser == null) {
            errors.reject("user.notLoggedIn", "로그인 상태가 아닙니다.");
            return;
        }

        // 피드 제목 필수 검증
        if (feed.getBoardSubject() == null || feed.getBoardSubject().isEmpty()) {
            errors.rejectValue("title", "title.empty", "제목은 필수 입력 항목입니다.");
        }

        // 피드 내용 필수 검증
        if (feed.getBoardContent() == null || feed.getBoardContent().isEmpty()) {
            errors.rejectValue("content", "content.empty", "내용은 필수 입력 항목입니다.");
        }

        // 도시 필수 선택 검증
        if (feed.getCity() == null) {
            errors.rejectValue("cityId", "city.empty", "도시는 필수 선택 항목입니다.");
        }

        // 피드 작성자와 현재 로그인 사용자 일치 여부 확인
        if (!feed.getUser().getUsername().equals(currentUser.getUsername())) {
            errors.reject("user.notAuthorized", "권한이 없습니다.");
        }

        // 피드 작성 폼 검증
        if (feed.getBoardId() != 0) { // 수정의 경우
            Feed existingFeed = feedService.findById(feed.getBoardId());
            if (existingFeed == null) {
                errors.reject("feed.notFound", "존재하지 않는 피드입니다.");
            } else if (!existingFeed.getUser().getUsername().equals(currentUser.getUsername())) {
                errors.reject("user.notAuthorized", "피드를 수정할 권한이 없습니다.");
            }
        }
    }
}

        // 좋아요 Validator에 넣어야 할 거.
        // 이미 쓴 피드일 경우, 현재 좋아요 갯수를 보여줌. 본인거 좋아요 누를 수 없음.

        // 타인 페이지의 방문할 경우
        // 마이페이지 -> 피드 작성 버튼 사라짐
        // 피드 상세보기 시, 피드 게시판으로 이동하는 버튼 나타나고 이동 버튼 클릭 시 해당 url로 이동
        // 타인 피드 상세보기에 있는 좋아요를 누를 수 있고, 이미 눌렀다면 취소할 수 있다.

        // 좋아요 누른 거 : 빨간 색상이 채워져 있는 하트
        // 좋아요 누르지 않은 거 : 빨간 색상의 선만 있는 하트 (채워져있지 않음)
        // 해당 하트 클릭 시, 색상이 채워지고 안채워지고로 바뀜.
