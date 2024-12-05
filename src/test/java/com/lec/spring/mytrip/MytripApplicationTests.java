package com.lec.spring.mytrip;

import com.lec.spring.mytrip.domain.GuestBook;
import com.lec.spring.mytrip.repository.GuestBookRepository;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@MapperScan("com.lec.spring.mytrip.repository") // MyBatis Mapper 스캔
class MytripApplicationTests {

    @Autowired
    private GuestBookRepository guestBookRepository;

    @Test
    void contextLoads() {
        // GuestBook 엔티티를 준비합니다.
        GuestBook guestBook = new GuestBook();
        guestBook.setToUserId(1); // 예시로 toUserId 값을 설정
        guestBook.setFromUserId(2); // 예시로 fromUserId 값을 설정
        guestBook.setGuestBookContent("Test message"); // 예시로 guestBookContent 값을 설정

        // DB에 데이터를 삽입하는 메서드를 호출합니다.
        guestBookRepository.writeGuestBook(guestBook);

        // 삽입 후 데이터를 검증할 수 있는 방법이 필요합니다.
        // 예시로는 DB에서 해당 데이터가 저장되었는지 확인하는 방법이 있을 수 있습니다.

        // 삽입된 데이터를 DB에서 가져오는 메서드를 추가하거나,
        // insert 성공 후 바로 검증할 수 있는 로직을 작성할 수 있습니다.
        assertNotNull(guestBookRepository.findById(guestBook.getToUserId())); // 예시: 데이터를 찾아서 검증
    }
}
