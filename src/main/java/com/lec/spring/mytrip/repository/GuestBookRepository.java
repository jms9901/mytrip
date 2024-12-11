package com.lec.spring.mytrip.repository;

import com.lec.spring.mytrip.domain.GuestBook;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GuestBookRepository {

    List<GuestBook> findById(int toUserId);

    void writeGuestBook(GuestBook guestBook);

    List<GuestBook> viewGuestBookByUserId(@Param("toUserId") int toUserId);

    void deleteGuestBook(@Param("guestBookId") int guestBookId);


}
