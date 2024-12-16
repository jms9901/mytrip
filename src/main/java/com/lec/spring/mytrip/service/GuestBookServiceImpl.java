package com.lec.spring.mytrip.service;

import com.lec.spring.mytrip.domain.GuestBook;
import com.lec.spring.mytrip.repository.GuestBookRepository;
import jakarta.transaction.Transactional;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuestBookServiceImpl implements GuestBookService {

    private final SqlSession sqlSession;
    private final GuestBookRepository guestBookRepository;

    public GuestBookServiceImpl(SqlSession sqlSession, GuestBookRepository guestBookRepository) {
        this.sqlSession = sqlSession;
        this.guestBookRepository = guestBookRepository;
    }

    @Override
    public void addGuestBook(GuestBook guestBook) {
        sqlSession.insert("com.lec.spring.mytrip.repository.GuestBookRepository.writeGuestBook", guestBook);
    }

    @Transactional
    @Override
    public List<GuestBook> getGuestBooksByUserId(int toUserId) {
        return guestBookRepository.viewGuestBookByUserId(toUserId);
    }

    @Override
    public void removeGuestBook(int guestBookId) {
        sqlSession.delete("com.lec.spring.mytrip.repository.GuestBookRepository.deleteGuestBook", guestBookId);
    }
}
