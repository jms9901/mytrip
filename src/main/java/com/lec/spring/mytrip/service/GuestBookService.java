package com.lec.spring.mytrip.service;

import com.lec.spring.mytrip.domain.GuestBook;


import java.util.List;


public interface GuestBookService {

    // 방명록 작성
    void addGuestBook(GuestBook guestBook);

    // 특정 사용자의 방명록 조회
    List<GuestBook> getGuestBooksByUserId(int toUserId);

    // 방명록 삭제
    void removeGuestBook(int guestBookId);

}
