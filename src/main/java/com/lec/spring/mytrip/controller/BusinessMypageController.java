package com.lec.spring.mytrip.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mypage")
public class BusinessMypageController {
    // 기업 개인정보 수정 -> 모달
    // businessMain.html

    // 기업이 등록한 패키지 목록(패키지 제목, 내용, 관리자 승인 상태, 좋아요 수) + 기업 정보 출력 (메인 페이지)
    // businessMain.html

    // 기업이 등록한 모든 패키지에 관한 일반 유저의 결제 내역(사용자, 패키지 상품 제목, 결제 상태)
    // businessPayment.html

    // 등록한 패키지 클릭 시 상세보기 모달
    // businessPackage.html

    // 상세보기에서 수정 가능 -> 상태가 대기/거절일 겨우에만 가능
    // businessPackage.html

    // 상세보기에서 삭제 -> 상태가 대기/거절일 경우에만 가능
    // businessPackage.html

}
