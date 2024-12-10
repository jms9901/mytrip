package com.lec.spring.mytrip.service;

public interface LikedService {
    //좋아요 증감
    int changeLikeStatus(int target, int id);
}
