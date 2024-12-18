$(document).ready(function () {
    // 페이지 로드 시, 좋아요 상태와 총 좋아요 수 가져오기
    $.ajax({
        url: "/likey/liked-items", // 좋아요 상태 및 총 좋아요 수를 불러오는 컨트롤러
        method: "GET",
        success: function (response) {
            console.log("좋아요 데이터 불러오기 성공:", response);

            // 좋아요 상태와 총 좋아요 수 업데이트
            // 도시 좋아요 상태 처리
            if (response.likedCities) {
                response.likedCities.forEach(cityId => {
                    const button = $(`button[data-city-id='${cityId}']`);
                    const likeCountElement = button.siblings('.likeCount');
                    button.find('i').removeClass('bi-heart').addClass('bi-heart-fill'); // 좋아요 상태 업데이트
                });
            }

            // 패키지 좋아요 상태 처리
            if (response.likedPackages) {
                response.likedPackages.forEach(packageId => {
                    const button = $(`button[data-package-id='${packageId}']`);
                    const likeCountElement = button.siblings('.likeCount');
                    button.find('i').removeClass('bi-heart').addClass('bi-heart-fill'); // 좋아요 상태 업데이트
                });
            }

            // 그룹(게시물) 좋아요 상태 처리
            if (response.likedPosts) {
                response.likedPosts.forEach(postId => {
                    const button = $(`button[data-group-id='${postId}']`);
                    const likeCountElement = button.siblings('.likeCount');
                    button.find('i').removeClass('bi-heart').addClass('bi-heart-fill'); // 좋아요 상태 업데이트
                });
            }

            // 총 좋아요 수 업데이트
            if (response.totalLikes) {
                Object.keys(response.totalLikes).forEach(key => {
                    const [type, id] = key.split('_'); // key 예: "city_1", "post_10"
                    const likeCount = response.totalLikes[key]; // 총 좋아요 수

                    let button;
                    if (type === 'city') {
                        button = $(`button[data-city-id='${id}']`);
                    } else if (type === 'post') {
                        button = $(`button[data-group-id='${id}']`);
                    } else if (type === 'package') {
                        button = $(`button[data-package-id='${id}']`);
                    }

                    if (button) {
                        const likeCountElement = button.siblings('.likeCount');
                        likeCountElement.text(likeCount); // 총 좋아요 수 업데이트
                    }
                });
            }

            // 비로그인 메시지 처리
            if (response.message) {
                console.warn(response.message); // 콘솔에 메시지 출력 (필요 시 팝업 처리 가능)
            }
        },
        error: function () {
            console.error("좋아요 데이터를 불러오지 못했습니다.");
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
                // 로그인 필요 메시지 확인
                if (response.message) {
                    alert(response.message); // 로그인 필요시 팝업 표시
                    return;
                }

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
