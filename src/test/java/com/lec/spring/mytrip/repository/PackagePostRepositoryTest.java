package com.lec.spring.mytrip.repository;

import com.lec.spring.mytrip.domain.PackagePost;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PackagePostRepositoryTest {
    @Autowired
    private PackagePostRepository packagePostRepository;

    @Test
    void findById() {
        PackagePost packagePost = packagePostRepository.findById(20);
        System.out.println(packagePost.toString());
    }

    @Test
    void findByCityId() {
        List<PackagePost> packagePosts = packagePostRepository.findByCityId(1);
        packagePosts.forEach(System.out::println);
    }

    @Test
    void findByUserId() {
        List<PackagePost> packagePosts = packagePostRepository.findByUserId(3);
        packagePosts.forEach(System.out::println);
    }

    @Test
    void findByStatus() {
        List<PackagePost> packagePosts = packagePostRepository.findByStatus("대기");
        packagePosts.forEach(System.out::println);
    }

    @Test
    void findByCityAndStatus(){
        List<PackagePost> packagePosts = packagePostRepository.findByCityAndStatus(3);
        packagePosts.forEach(System.out::println);
    }

    @Test
    void searchByTitle() {
        List<PackagePost> packagePosts = packagePostRepository.searchByTitle("제에에목");
        packagePosts.forEach(System.out::println);
    }

    @Test
    void save() {
        PackagePost packagePost = PackagePost.builder()
                .packageTitle("뎨목")
                .packageContent("나랏〮말〯ᄊᆞ미〮\n" +
                        "中듀ᇰ國귁〮에〮달아〮\n" +
                        "文문字ᄍᆞᆼ〮와〮로〮서르ᄉᆞᄆᆞᆺ디〮아니〮ᄒᆞᆯᄊᆡ〮\n" +
                        "이〮런젼ᄎᆞ〮로〮어린〮百ᄇᆡᆨ〮姓셔ᇰ〮이〮니르고〮져〮호ᇙ〮배〮이셔〮도〮\n" +
                        "ᄆᆞᄎᆞᆷ〮내〯제ᄠᅳ〮들〮시러〮펴디〮몯〯ᄒᆞᇙ노〮미〮하니〮라〮\n" +
                        "내〮이〮ᄅᆞᆯ〮為윙〮ᄒᆞ〮야〮어〯엿비〮너겨〮\n" +
                        "새〮로〮스〮믈〮여듧〮字ᄍᆞᆼ〮ᄅᆞᆯ〮ᄆᆡᇰᄀᆞ〮노니〮\n" +
                        "사〯ᄅᆞᆷ마〯다〮ᄒᆡ〯ᅇᅧ〮수〯ᄫᅵ〮니겨〮날〮로〮ᄡᅮ〮메〮便뼌安ᅙᅡᆫ킈〮ᄒᆞ고〮져〮ᄒᆞᇙᄯᆞᄅᆞ미〮니라〮。")
                .packageStatus("대기")
                .packageCost(39800)
                .packageMaxpeople(28)
                .packageStartDay(LocalDateTime.parse("2025-01-01T00:00:00"))
                .packageEndDay(LocalDateTime.parse("2025-01-01T00:00:00"))
                .userId(3)
                .cityId(1)
                .build();

        int a = packagePostRepository.save(packagePost);
        System.out.println(a);

        packagePostRepository.findByUserId(3).forEach(System.out::println);
    }

    @Test
    void update() {
        PackagePost packagePost = PackagePost.builder()
                .packageId(20)
                .packageTitle("뎌이목")
                .packageContent("나랏〮말〯ᄊᆞ미〮\n" +
                        "中듀ᇰ國귁〮에〮달아〮\n" +
                        "文문字ᄍᆞᆼ〮와〮로〮서르ᄉᆞᄆᆞᆺ디〮아니〮ᄒᆞᆯᄊᆡ〮\n" +
                        "이〮런젼ᄎᆞ〮로〮어린〮百ᄇᆡᆨ〮姓셔ᇰ〮이〮니르고〮져〮호ᇙ〮배〮이셔〮도〮\n" +
                        "ᄆᆞᄎᆞᆷ〮내〯제ᄠᅳ〮들〮시러〮펴디〮몯〯ᄒᆞᇙ노〮미〮하니〮라〮\n" +
                        "내〮이〮ᄅᆞᆯ〮為윙〮ᄒᆞ〮야〮어〯엿비〮너겨〮\n" +
                        "새〮로〮스〮믈〮여듧〮字ᄍᆞᆼ〮ᄅᆞᆯ〮ᄆᆡᇰᄀᆞ〮노니〮\n" +
                        "사〯ᄅᆞᆷ마〯다〮ᄒᆡ〯ᅇᅧ〮수〯ᄫᅵ〮니겨〮날〮로〮ᄡᅮ〮메〮便뼌安ᅙᅡᆫ킈〮ᄒᆞ고〮져〮ᄒᆞᇙᄯᆞᄅᆞ미〮니라〮。")
                .packageStatus("대기")
                .packageCost(398000)
                .packageMaxpeople(280)
                .packageStartDay(LocalDateTime.parse("2025-01-01T00:00:00"))
                .packageEndDay(LocalDateTime.parse("2025-01-10T00:00:00"))
                .cityId(1)
                .build();

        packagePostRepository.update(packagePost);
        packagePostRepository.findByUserId(3).forEach(System.out::println);

        System.out.println(packagePostRepository.findById(20).toString());
    }

    @Test
    void deleteById() {
        packagePostRepository.deleteById(20);
        packagePostRepository.findByUserId(3).forEach(System.out::println);
    }
}