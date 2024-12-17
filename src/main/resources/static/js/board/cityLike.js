$(document).ready(function () {
    // cityId를 메타 태그에서 받아옴
    const cityId = $('meta[name="city-id"]').attr('content');

    // 좋아요 버튼 클릭 이벤트
    $('.cityLikeButton').on('click', function () {
        const likeButton = $(this); // 현재 버튼 요소
        const heartIcon = likeButton.find('i'); // 하트 아이콘 요소

        $.ajax({
            url: `/likey/city`, // 서버 요청 URL
            method: 'GET',      // GET 요청
            data: { cityId: cityId }, // 요청 데이터
            success: function (response) {
                // 서버 응답에 따라 좋아요 상태를 토글
                if (response.status === 1) {
                    // 좋아요 성공: 하트 채우기
                    heartIcon.removeClass('bi-heart').addClass('bi-heart-fill');
                    heartIcon.css('color', 'red');
                    console.log("좋아요 성공!");
                } else {
                    // 좋아요 취소: 하트 비우기
                    heartIcon.removeClass('bi-heart-fill').addClass('bi-heart');
                    heartIcon.css('color', '');
                    console.log("좋아요 취소!");
                }
            },
            error: function (xhr, status, error) {
                alert("오류가 발생했습니다. 다시 시도해주세요.");
            }
        });
    });
});
