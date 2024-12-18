document.addEventListener("DOMContentLoaded", () => {
    // 사용자 권한 정보 (예: 서버에서 전달받은 사용자 정보)
    const isCurrentUser = true; // true: 본인, false: 타인 (임시 값, 실제 서버로부터 받아야 함)

    const deleteButton = document.getElementById("delete-button");
    const updateButton = document.getElementById("update-button");
    const goFeedListButton = document.querySelector(".goFeedList");

    // 본인 권한인 경우: 삭제/수정 버튼 보이기
    if (isCurrentUser) {
        deleteButton.style.display = "inline";
        updateButton.style.display = "inline";

        goFeedListButton.style.display = "none";
        goDeclarationButton.style.display = "none";
    } else {
        // 타인 권한인 경우: 피드 리스트/신고 버튼 보이기
        deleteButton.style.display = "none";
        updateButton.style.display = "none";

        goFeedListButton.style.display = "inline";
    }
});

$(document).ready(function () {
    const apiUrl = "/mypage/feed";
    const urlParams = new URLSearchParams(window.location.search);
    const feedId = urlParams.get('feedId');

    if (!feedId) {
        alert("Invalid feed ID.");
        window.location.href = '/mypage/feed.html'; // 목록 페이지로 리다이렉트
        return;
    }

    // 피드 상세 데이터 로딩
    function loadFeedDetail(feedId) {
        $.ajax({
            url: `${apiUrl}/detail/${feedId}`,
            method: 'GET',
            success: function (feed) {
                $('#feed-title').text(feed.boardSubject);
                $('#feed-content').text(feed.boardContent);
                $('#city-name').text(feed.cityName);

                // 이미지 로딩
                const imagePreview = $('#image-preview');
                feed.images.forEach(image => {
                    imagePreview.append(`<img src="${image}" alt="feed image" class="slider-image">`);
                });

                // 좋아요 상태 설정
                $('.feed-liked').toggleClass('liked', feed.isLiked);
                $('.feed-liked-count').text(feed.likeCount);
            },
            error: function () {
                alert("Failed to load feed details.");
                window.location.href = '/mypage/feed.html'; // 목록 페이지로 리다이렉트
            }
        });
    }

    loadFeedDetail(feedId);
});