package com.lec.spring.mytrip.service;

import com.lec.spring.mytrip.domain.City;
import com.lec.spring.mytrip.domain.Feed;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface FeedService {

    List<Feed> getFeedByUserId(int userId);

    void insertFeed(Feed feed);

    void updateFeed(Feed feed);

    void deleteFeed(int boardId, int userId);

}
