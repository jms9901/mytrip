package com.lec.spring.mytrip.repository;

import com.lec.spring.mytrip.domain.Feed;

import java.util.List;

public interface FeedRepository {
    // 새 피드 작성 INSERT
    int save(Feed feed);

    // 특정 피드 id 글 내용 읽기
    Feed findById(Long id);

    // 특정 id 글 조회수 증가
    int viewCnt(Long id);

    // 전체 글 목록 : 최신순
    List<Feed> findAll();

    // 가장 최근 게시물 9개 개인정보 오른쪽 페이지에 배치
    int findRecentFeedByUser(Long id);

    // 특정 피드 id 피드 수정 -> 모달에서 처리
    int update(Feed feed);

    // 특정 피드 id 피드 삭제 -> 모달에서 처리
    int delete(Feed feed);


    // 전체 피드 개수
    int feedCountAll();

}
