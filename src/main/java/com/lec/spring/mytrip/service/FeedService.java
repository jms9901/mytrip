package com.lec.spring.mytrip.service;

import com.lec.spring.mytrip.domain.City;
import com.lec.spring.mytrip.domain.Feed;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface FeedService {

    // 피드 작성 >> INSERT
    boolean write(Feed feed, Map<String, MultipartFile> files);

    // 특정 피드 id의  피드 조회 >> SELECT
    // 트랜잭션 처리
    @Transactional
    Feed detail(int id);

    // 피드 목록 조회 >> SELECT
    List<Feed> list();

    // 특정 id 의 글 읽어오기 (SELECT)
    // 조회수 증가 없음
    Feed findById(int id);

    // 특정 피드 id의 피드 수정하기 (제목, 내용, 이미지-첨부파일, 도시) >> UPDATE
    boolean update(Feed feed, Map<String, MultipartFile> files, int[] delfile);

    // 특정 피드 id의 글 삭제하기 >> DELETE
    boolean deleteById(int Id);

    // 도시 가져오기
    List<City> getAllCities();

    // 피드 좋아요 처리
//    void likeFeed(Long feedId, String username);

    // 피드 좋아요 취소
//    void unlikeFeed(Long feedId, String username);

    // 특정 사용자가 좋아요한 피드인지 확인
//    boolean isLikedByUser(Long feedId, String username);


    // 도시 및 카테고리 별 불러오기
    List<Feed> findByCityAndCategory(int cityId, String Category);

}
