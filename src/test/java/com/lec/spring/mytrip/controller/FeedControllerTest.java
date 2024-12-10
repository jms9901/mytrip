package com.lec.spring.mytrip.controller;

import com.lec.spring.mytrip.domain.City;
import com.lec.spring.mytrip.domain.Feed;
import com.lec.spring.mytrip.domain.PostAttachment;
import com.lec.spring.mytrip.domain.User;
import com.lec.spring.mytrip.repository.FeedRepository;
import com.lec.spring.mytrip.repository.UserRepository;
import com.lec.spring.mytrip.service.FeedService;
import com.lec.spring.mytrip.service.FeedServiceImpl;
import com.lec.spring.mytrip.util.U;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@ActiveProfiles("test")
class FeedControllerTest {
    @Autowired
    private FeedService feedService;

    private Feed testFeed;
    private User testUser;
    private City testCity;

    @BeforeEach
    public void setup() {
        // 테스트에 사용될 기본 객체 초기화
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testuser");

        testCity = new City();
        testCity.setCityId(1);
        testCity.setCityName("Test City");

        testFeed = new Feed();
        testFeed.setBoardId(1L);
        testFeed.setBoardSubject("Test Feed Subject");
        testFeed.setBoardContent("Test Feed Content");
        testFeed.setUser(testUser);
        testFeed.setCity(testCity);
    }

    @Test
    @DisplayName("피드 작성 페이지 접근 테스트")
    public void testFeedWritePage() throws Exception {
        // 모든 도시 목록 모킹
        List<City> cities = Arrays.asList(testCity);
        when(feedService.getAllCities()).thenReturn(cities);

//        mockMvc.perform(MockMvcRequestBuilders.get("/mypage/feed"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.model().attributeExists("cities"))
//                .andExpect(MockMvcResultMatchers.model().attributeExists("feed"))
//                .andExpect(MockMvcResultMatchers.view().name("mypage/feed"))
//                .andDo(print());
    }

    @Test
    @DisplayName("피드 작성 성공 테스트")
    public void testFeedWriteSuccess() throws Exception {
        // 파일 모의 객체 생성
        MockMultipartFile file1 = new MockMultipartFile(
                "files",
                "test-image.jpg",
                "image/jpeg",
                "test image content".getBytes()
        );

        // 파일 맵 생성
        Map<String, MockMultipartFile> fileMap = new HashMap<>();
        fileMap.put("file1", file1);

        // 피드 작성 서비스
//        when(feedService.write(any(Feed.class), any())).thenReturn(true);

//        mockMvc.perform(MockMvcRequestBuilders.multipart("/mypage/feed/write")
//                        .file(file1)
//                        .param("boardSubject", testFeed.getBoardSubject())
//                        .param("boardContent", testFeed.getBoardContent())
//                        .flashAttr("feed", testFeed))
//                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
//                .andDo(print());

        // 서비스 메서드 호출 확인
        verify(feedService, times(1)).write(any(Feed.class), any());
    }

    @Test
    @DisplayName("피드 상세 조회 테스트")
    public void testFeedDetail() throws Exception {
        // 피드 상세 조회 모킹
        when(feedService.detail(1L)).thenReturn(testFeed);

//        mockMvc.perform(MockMvcRequestBuilders.get("/mypage/feedDetail/1"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.model().attributeExists("feed"))
//                .andExpect(MockMvcResultMatchers.model().attributeExists("isOwner"))
//                .andExpect(MockMvcResultMatchers.view().name("mypage/feedDetail"))
//                .andDo(print());
    }

    @Test
    @DisplayName("사용자별 피드 목록 조회 테스트")
    public void testFeedListByUser() throws Exception {
        // 사용자별 피드 목록 모킹
        List<Feed> userFeeds = Arrays.asList(testFeed);
        when(feedService.listByUser(1L)).thenReturn(userFeeds);

//        mockMvc.perform(MockMvcRequestBuilders.get("/mypage/feed/1"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.model().attributeExists("feeds"))
//                .andExpect(MockMvcResultMatchers.view().name("mypage/feed"))
//                .andDo(print());
    }

    @Test
    @DisplayName("피드 삭제 테스트")
    public void testFeedDelete() throws Exception {
        // 피드 삭제 모킹
        when(feedService.deleteById(1L)).thenReturn(true);

//        mockMvc.perform(MockMvcRequestBuilders.post("/mypage/feed/delete")
//                        .param("id", "1"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.view().name("mypage/feed"))
//                .andDo(print());
    }

}