package com.lec.spring.mytrip;

import com.lec.spring.mytrip.domain.PackagePost;
import com.lec.spring.mytrip.service.MainpageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MainpageServiceTest {

    @Autowired
    private MainpageService mainpageService;

    @Test
    public void testGetLatestPackages() {
        List<PackagePost> latestPackages = mainpageService.getLatestPackages();
        assertThat(latestPackages).isNotNull();
        assertThat(latestPackages.size()).isLessThanOrEqualTo(10);
//        latestPackages.forEach(pkg -> System.out.println(pkg.getPackageId() + " / " + pkg.getCityId() + " / " + pkg.getUserId() + " / " + pkg.getStatus() + " / " + pkg.getContent() + " / " + pkg.getRegDate() + " / " + pkg.getTitle() + " / " + pkg.getCost() + " / " + pkg.getMaxPeople() + " / " + pkg.getStartDay() + " / " + pkg.getEndDay()));
        latestPackages.forEach(System.out::println);
    }
}
