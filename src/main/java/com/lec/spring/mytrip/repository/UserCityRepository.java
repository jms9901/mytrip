package com.lec.spring.mytrip.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

@Mapper
public interface UserCityRepository {
    int findUserIdByName(@Param("userName") String userName);
    int insertUserCity(@Param("userId") int userId, @Param("cityId") int cityId);




}