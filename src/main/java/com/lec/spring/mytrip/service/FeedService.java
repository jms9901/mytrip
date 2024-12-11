package com.lec.spring.mytrip.service;

import com.lec.spring.mytrip.domain.City;
import com.lec.spring.mytrip.domain.Feed;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface FeedService {

    // 피드 작성 >> INSERT
    int write(Feed feed, Map<String, MultipartFile> files);

    // 특정 피드 id의  피드 조회 >> SELECT
    Feed detail(int boardId);

    // 피드 목록 조회 >> SELECT
//    List<Feed> list();

    // 특정 사용자가 작성한 피드 목록 조회 (SELECT)
    // 조회수 증가 없음
    List<Feed> listByUser(int userId);

    // 특정 피드 id의 피드 수정하기 (제목, 내용, 이미지-첨부파일, 도시) >> UPDATE
    boolean update(Feed feed, Map<String, MultipartFile> files, Long[] delfile);

    // 특정 피드 id의 글 삭제하기 >> DELETE
    boolean deleteById(int boardId);

    // 도시 가져오기
    List<City> getAllCities();

}
