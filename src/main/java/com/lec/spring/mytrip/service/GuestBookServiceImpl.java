package com.lec.spring.mytrip.service;

import com.lec.spring.mytrip.domain.GuestBook;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuestBookServiceImpl implements GuestBookService {

    private final SqlSession sqlSession;

    public GuestBookServiceImpl(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public void addGuestBook(GuestBook guestBook) {
        sqlSession.insert("com.lec.spring.mytrip.repository.GuestBookRepository.writeGuestBook", guestBook);
    }

    @Override
    public List<GuestBook> getGuestBooksByUserId(int toUserId) {
        return sqlSession.selectList("com.lec.spring.mytrip.repository.GuestBookRepository.viewGuestBookByUserId", toUserId);
    }

    @Override
    public void removeGuestBook(int guestBookId) {
        sqlSession.delete("com.lec.spring.mytrip.repository.GuestBookRepository.deleteGuestBook", guestBookId);
    }
}
