package com.lec.spring.mytrip.repository;

import com.lec.spring.mytrip.domain.Board;
import com.lec.spring.mytrip.domain.PackagePost;
import com.lec.spring.mytrip.domain.Payment;
import com.lec.spring.mytrip.domain.User;
import com.lec.spring.mytrip.service.AdminService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("dev")  // test 환경 설정
public class AdminServiceTest {

    @Autowired
    private AdminService adminService;

    @Test
    public void testFindByAuthorityRoleUser() {
        List<User> users = adminService.findByAuthorityRoleUser("ROLE_USER");
        assertNotNull(users);

        // 콘솔에 users 리스트의 내용을 출력
        users.forEach(user -> System.out.println(user));
    }

    @Test
    public void testFindByAuthorityRoleBusiness() {
        List<User> users = adminService.findByAuthorityRoleBusiness("ROLE_BUSINESS");
        assertNotNull(users);
        users.forEach(user -> System.out.println(user));
    }

    @Test
    public void findByBoardCategory(){
        List<Board> boards = adminService.findByBoardCategory("소모임");
        assertNotNull(boards);
        boards.forEach(board -> System.out.println(board));
    }

    @Test
    public void findByFeedCategory(){
        List<Board> feeds = adminService.findByFeedCategory("피드");
        assertNotNull(feeds);
        feeds.forEach(feed -> System.out.println(feed));
    }

    @Test
    public void findByAccessPackage(){
        List<PackagePost> access = adminService.findByAccessPackage("승인");
        assertNotNull(access);
        access.forEach(packagePost -> System.out.println(packagePost));
    }

    @Test
    public void findByStandByPackage(){
        List<PackagePost> standBy = adminService.findByStandByPackage("대기");
        assertNotNull(standBy);
        standBy.forEach(packagePost -> System.out.println(packagePost));
    }

    @Test
    public void findByPayment(){
        List<Payment> payments = adminService.findByPayment();
        assertNotNull(payments);
        payments.forEach(payment -> System.out.println(payment));
    }

    // 다른 메서드들에 대한 테스트도 동일한 방식으로 추가할 수 있습니다.
}
