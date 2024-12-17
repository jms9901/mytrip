$(document).on('click', '.like-button', function (event) {
    event.preventDefault(); // 기본 동작 방지
    const likeButton = $(this); // 현재 클릭된 버튼
    const heartIcon = likeButton.find('i'); // 하트 아이콘 요소
    const postId = likeButton.attr('data-feed-id'); // 게시물 ID

    console.log("좋아요 클릭됨");
    console.log("게시물 ID:", postId);

    $.ajax({
        url: `/likey/post`, // 요청 경로
        method: 'GET',
        data: { postId: postId },
        success: function (response) {
            if (response === 1) {
                heartIcon.removeClass('far').addClass('fas'); // 채워진 하트
                heartIcon.css('color', 'red'); // 좋아요 성공
                console.log(`게시물 ${postId} 좋아요 성공`);
            } else {
                heartIcon.removeClass('fas').addClass('far'); // 빈 하트
                heartIcon.css('color', ''); // 좋아요 취소
                console.log(`게시물 ${postId} 좋아요 취소`);
            }
        },
        error: function () {
            alert("좋아요 처리 중 오류가 발생했습니다. 다시 시도해주세요.");
        }
    });
});
