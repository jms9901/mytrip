$(document).ready(function () {
    // 로그인한 사용자의 좋아요 상태 가져오기
    $.ajax({
        url: "/likey/liked-items", // 좋아요 상태를 불러오는 컨트롤러
        method: "GET",
        success: function (response) {
            console.log("좋아요 상태 불러오기 성공:", response);

            // 도시 좋아요 상태 처리
            if (response.cities) {
                response.cities.forEach(cityId => {
                    $(`button[data-city-id='${cityId}'] i`).removeClass('bi-heart').addClass('bi-heart-fill');
                });
            }

            // 패키지 좋아요 상태 처리
            if (response.packages) {
                response.packages.forEach(packageId => {
                    $(`button[data-package-id='${packageId}'] i`).removeClass('bi-heart').addClass('bi-heart-fill');
                });
            }

            // 그룹(게시물) 좋아요 상태 처리
            if (response.posts) {
                response.posts.forEach(postId => {
                    $(`button[data-group-id='${postId}'] i`).removeClass('bi-heart').addClass('bi-heart-fill');
                });
            }
        },
        error: function () {
            console.error("좋아요 상태를 불러오지 못했습니다.");
        }
    });

    // 좋아요 버튼 클릭 이벤트
    $('.likeButton').on('click', function (event) {
        event.preventDefault();

        const button = $(this);
        const likeCountElement = button.siblings('.likeCount'); // 좋아요 수 표시 요소

        const cityId = button.data('city-id');
        const packageId = button.data('package-id');
        const postId = button.data('group-id');

        let url = '';
        let data = {};

        if (cityId) {
            url = '/likey/board/city';
            data.cityId = cityId;
        } else if (packageId) {
            url = '/likey/board/package';
            data.packageId = packageId;
        } else if (postId) {
            url = '/likey/board/post';
            data.postId = postId;
        }

        $.ajax({
            url: url,
            method: 'GET',
            data: data,
            success: function (response) {
                console.log("Response:", response);

                const icon = button.find('i');
                if (response.liked) {
                    icon.removeClass('bi-heart').addClass('bi-heart-fill');
                } else {
                    icon.removeClass('bi-heart-fill').addClass('bi-heart');
                }

                likeCountElement.text(response.totalLikes); // 좋아요 개수 업데이트
            },
            error: function () {
                alert("오류가 발생했습니다. 다시 시도해주세요.");
            }
        });
    });
});
