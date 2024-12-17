package com.lec.spring.mytrip.repository;

import com.lec.spring.mytrip.domain.City;
import com.lec.spring.mytrip.domain.Feed;
import com.lec.spring.mytrip.domain.PostAttachment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FeedRepository {
    // 사용자 ID로 피드 게시글 조회
    List<Feed> findFeedByUserId(@Param("userId") int userId);

    List<Feed> findRecentFeedsByUserId(@Param("userId") int userId);

    // 피드 등록
    void insertFeed(Feed feed);

    // 피드 수정
    void updateFeed(Feed feed);

    // 피드 삭제
    void deleteFeed(@Param("boardId") int boardId, @Param("userId") int userId);

    // 첨부파일 등록
    void insertAttachments(PostAttachment attachment);

    City findCityById(Long id);

    //도시와 카테고리 별 피드
    List<Feed> findByCityAndCategory(int cityId, String category);

    void deleteAttachmentsByBoardId(int boardId);
}
