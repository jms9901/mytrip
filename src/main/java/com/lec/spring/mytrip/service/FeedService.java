package com.lec.spring.mytrip.service;

import com.lec.spring.mytrip.domain.Feed;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface FeedService {

    // 피드 작성 >> INSERT
    int write(Feed feed, Map<String, MultipartFile> files);

    // 특정 피드 id의  피드 조회 >> SELECT
    // 트랜잭션 처리
    @Transactional
    Feed detail(Long id);

    // 피드 목록 조회 >> SELECT
    List<Feed> list();

    // 특정 id 의 글 읽어오기 (SELECT)
    // 조회수 증가 없음
    Feed findById(Long id);

    // 특정 피드 id의 피드 수정하기 (제목, 내용, 이미지-첨부파일, 도시) >> UPDATE
    int update(Feed feed, Map<String, MultipartFile> files, Long[] delfile);

    // 특정 피드 id의 글 삭제하기 >> DELETE
    int deleteById(Long id);


}
