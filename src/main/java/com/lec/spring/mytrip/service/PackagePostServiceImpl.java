package com.lec.spring.mytrip.service;

import com.lec.spring.mytrip.domain.PackagePost;
import com.lec.spring.mytrip.domain.User;
import com.lec.spring.mytrip.domain.attachment.BoardAttachment;
import com.lec.spring.mytrip.domain.attachment.PackagePostAndAttachment;
import com.lec.spring.mytrip.domain.attachment.PackagePostAttachment;
import com.lec.spring.mytrip.repository.LikeRepository;
import com.lec.spring.mytrip.repository.PackagePostRepository;
import com.lec.spring.mytrip.util.U;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class PackagePostServiceImpl implements PackagePostService {
    private final PackagePostRepository packagePostRepository;
    private final PackageAttachmentService packageAttachmentService;
    private final LikeRepository likeRepository;

    @Autowired
    public PackagePostServiceImpl(SqlSession sqlSession, PackageAttachmentService packageAttachmentService) {
        this.packagePostRepository = sqlSession.getMapper(PackagePostRepository.class);
        this.packageAttachmentService = packageAttachmentService;
        this.likeRepository = sqlSession.getMapper(LikeRepository.class);
    }

    // 패키지 상세
    @Override
    public PackagePostAndAttachment getPackageDetails(int packageId) {
        PackagePostAndAttachment postAndAttachment = new PackagePostAndAttachment();
        // 패키지 ID 검증
        if (packageId <= 0) {
            throw new IllegalArgumentException("유효하지 않은 패키지 ID 입니다.");
        }
        // 패키지 데이터 조회
        postAndAttachment.setPackagePost(packagePostRepository.findById(packageId));

        if (postAndAttachment.getPackagePost() == null) {
            throw new IllegalArgumentException("ID가 " + packageId + "인 패키지를 찾을 수 없습니다.");
        }

        postAndAttachment.setPackagePostAttachment(packageAttachmentService.getAttachmentsByPackageId(packageId));

        if (postAndAttachment.getPackagePost() == null) {
            throw new IllegalArgumentException("ID가 " + packageId + "인 패키지를 찾을 수 없습니다.");
        }

//        System.out.println(postAndAttachment.toString());


        return postAndAttachment;
    }

    //도시 별 패키지 목록
    @Override
    public List<PackagePost> getPackagesByCityId(int cityId) {
        List<PackagePost> packagePosts = packagePostRepository.findByCityId(cityId);
        packagePosts.forEach(packagePost -> {
            List<PackagePostAttachment> e =
                    packageAttachmentService.getAttachmentsByPackageId(packagePost.getPackageId());
            if(!e.isEmpty()) {
                packagePost.setPackageAttachmentFile(e.get(0).getPackageAttachmentFile());
            }
            packagePost.setPackageLiked(likeRepository.getPackageLikeCount(packagePost.getPackageId()));
        });
        return packagePosts;
    }

    //검색
    @Override
    public List<PackagePost> searchPackages(String keyword) {
        // 검색 키워드 검증
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new IllegalArgumentException("검색 키워드는 null이거나 비어 있을 수 없습니다.");
        }
        return packagePostRepository.searchByTitle(keyword);
    }

    // 패키지 저장
    @Override
    @Transactional  //게시글-이미지 DB 저장-이미지 서버 저장을 트랜잭션으로
    public int savePackage(PackagePost pkg, List<MultipartFile> files) {
        System.out.println("서비스 들어옴");

        User user = U.getLoggedUser();
        pkg.setUser(user);
        pkg.setPackageStatus("대기");
        pkg.setUserId(user.getId());

        // 패키지 데이터 검증. 이거 찐하게 수정해야겠는데
        if (user == null) {
            throw new RuntimeException("로그인 하세요.");
        }
        if (pkg.getCityId() <= 0 || pkg.getUserId() <= 0 || pkg.getPackageTitle() == null || pkg.getPackageTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("유효하지 않은 패키지 데이터입니다. 도시 ID, 사용자 ID, 제목은 필수입니다.");
        }

        // 패키지 저장
        packagePostRepository.save(pkg);
        System.out.println("패키지 저장 완. 이건 이미지 " + files);

        // 첨부파일 저장
        try {
            packageAttachmentService.savePackageAttachments(files, pkg);
        } catch (Exception e) {
            throw new RuntimeException("첨부파일 저장 중 오류가 발생했습니다.", e);
        }

        return pkg.getPackageId();
    }

    //패키지 수정
    @Override
    public int updatePackage(PackagePost pkg, List<MultipartFile> files) {
        // 패키지 데이터 검증
        if (pkg == null || pkg.getPackageId() <= 0) {
            throw new IllegalArgumentException("패키지 또는 패키지 ID가 null일 수 없습니다.");
        }

        // 기존 데이터 조회 및 검증
        PackagePost existingPackage = packagePostRepository.findById(pkg.getPackageId());
        if (existingPackage == null) {
            throw new IllegalArgumentException("ID가 " + pkg.getPackageId() + "인 패키지를 찾을 수 없습니다.");
        }
//        if (existingPackage.getUser().getId() != pkg.getUser().getId()) {
//            throw new SecurityException("이 패키지를 수정할 권한이 없습니다.");
//        }

        List<PackagePostAttachment> attachments = packageAttachmentService.findByPackageId(pkg.getPackageId());
        System.out.println("수정할 글 " + pkg.getPackageId());
        System.out.println("삭제될 첨부파일 " + attachments.toString());
        if(!attachments.isEmpty()){
            attachments.forEach(e ->
                    packageAttachmentService.deletePackageAttachment(e.getPackageAttachmentId())
            );
        }

        try {
            packageAttachmentService.savePackageAttachments(files, pkg);
        } catch (Exception e) {
            throw new RuntimeException("첨부파일 저장 중 오류가 발생했습니다.", e);
        }

        System.out.println(pkg.toString());


        return packagePostRepository.update(pkg);
    }

    // 패키지 삭제
    @Override
    public int deletePackage(int packageId, int userId) {
        // 데이터 검증
        if (packageId <= 0 || userId <= 0) {
            throw new IllegalArgumentException("패키지 ID와 사용자 ID는 null이거나 0 이하일 수 없습니다.");
        }

        // 기존 데이터 조회 및 검증
        PackagePost pkg = packagePostRepository.findById(packageId);
        if (pkg == null) {
            throw new IllegalArgumentException("ID가 " + packageId + "인 패키지를 찾을 수 없습니다.");
        }
        if (pkg.getUser().getId() != userId) {
            throw new SecurityException("이 패키지를 삭제할 권한이 없습니다.");
        }

        List<PackagePostAttachment> attachments = packageAttachmentService.findByPackageId(packageId);
        attachments.forEach(System.out::println);

        if(!attachments.isEmpty()){
            attachments.forEach(e ->
                    packageAttachmentService.deletePackageAttachment(e.getPackageAttachmentId())
            );
        }


        return packagePostRepository.deleteById(packageId);
    }

    @Override
    public List<PackagePost> getPackageDetailsById(int packageId) {
        return packagePostRepository.mypagePackageDetail(packageId);
    }


    // 유저(기업 계정) 별 패키지 목록 > 마이페이지
    @Override
    public List<PackagePost> getPackagesByUserId(int userId) {
        // 사용자 ID 검증
        if (userId <= 0) {
            throw new IllegalArgumentException("유효하지 않은 사용자 ID입니다.");
        }
        // 권한이 기업인지도 추가해야함
        return packagePostRepository.findByUserId(userId);
    }

    // 상태에 따른 조회 > 관리자 페이지?
    @Override
    public List<PackagePost> getPackagesByStatus(String status) {
        // 상태값 검증
        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("상태값은 null이거나 비어 있을 수 없습니다.");
        }
        return packagePostRepository.findByStatus(status);
    }
} // end class

