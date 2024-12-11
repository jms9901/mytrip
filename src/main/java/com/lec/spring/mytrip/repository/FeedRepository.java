package com.lec.spring.mytrip.repository;

import com.lec.spring.mytrip.domain.City;
import com.lec.spring.mytrip.domain.Feed;
import com.lec.spring.mytrip.domain.PostAttachment;

import java.util.List;

public interface FeedRepository {
    // 새 피드 작성 INSERT
    int save(Feed feed);

    // 특정 피드 id 글 내용 읽기
    // 상세보기
    Feed findById(Long boardId);

    // 특정 사용자의 피드 읽기
    // 본인이 작성한 피드 리스트 불러오기
    List<Feed> findByUserId(Long userId);

    // 특정 id 글 조회수 증가
    int viewCnt(Long id);

    // 전체 글 목록 : 최신순
//    List<Feed> findAll();

    // 가장 최근 게시물 9개 개인정보 오른쪽 페이지에 배치
    int findRecentFeedByUser(Long id);

    // 특정 피드 id 피드 수정 -> 모달에서 처리
    int update(Feed feed);

    // 특정 피드 id 피드 삭제 -> 모달에서 처리
    int delete(Long id);

    // 전체 피드 개수
    int feedCountAll();

    // 첨부파일 저장
    int saveAttachment(PostAttachment attachment);

    // 특정 피드 ID로 첨부파일 찾기
    List<PostAttachment> findAttachmentByBoardId(Long boardId);

    // 첨부파일 삭제
    int deleteAttachment(Long boardAttachmentId);

    // 특정 피드 ID의 첨부파일 삭제
    int deleteAttachmentByBoardId(Long boardId);

    // 도시
    List<City> findAllCities();

    City findCityById(Long id);

}
