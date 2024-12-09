package com.lec.spring.mytrip;

import com.lec.spring.mytrip.config.oauth.PrincipalOauth2UserService;
import com.lec.spring.mytrip.domain.Package;
import com.lec.spring.mytrip.service.MainpageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MainpageServiceTest {

    @Autowired
    private MainpageService mainpageService;

    @Test
    public void testGetLatestPackages() {
        List<Package> latestPackages = mainpageService.getLatestPackages();
        assertThat(latestPackages).isNotNull();
        assertThat(latestPackages.size()).isLessThanOrEqualTo(10);
        latestPackages.forEach(pkg -> System.out.println(pkg.getPackageId()));
    }
}
