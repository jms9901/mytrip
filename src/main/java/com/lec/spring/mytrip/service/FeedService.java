package com.lec.spring.mytrip.service;

import com.lec.spring.mytrip.domain.City;
import com.lec.spring.mytrip.domain.Feed;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface FeedService {

    List<Feed> getFeedByUserId(int userId);


    public List<Feed> findRecentFeedsByUserId(int userId);

    void insertFeed(Feed feed,List<MultipartFile> files) throws IOException;

    void updateFeed(int boardId, int userId, String boardSubject, String boardContent, int cityId, List<MultipartFile> files) throws IOException;

    void deleteFeed(int boardId, int userId);


    // 도시 및 카테고리 별 불러오기
    List<Feed> findByCityAndCategory(int cityId, String Category);

    Feed detail(int id);

    int deleteGroup(int boardId);
}
