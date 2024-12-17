$(document).ready(function () {
    $('.groupLikeButton').on('click', function (event) {
        event.preventDefault(); // 기본 동작 방지
        const postId = $(this).data('board-id');

        console.log("like 동작");
        console.log(postId);

        $.ajax({
            url: `/likey/post`,
            method: 'GET',
            data: { postId: postId },
            success: function (response) {
                const icon = $(`button[data-package-id="${postId}"] i`);
                if (response === 1) {
                    icon.removeClass('bi-heart').addClass('bi-heart-fill');
                    icon.css('color', 'red'); // 좋아요 클릭
                } else {
                    icon.removeClass('bi-heart-fill').addClass('bi-heart');
                    icon.css('color', ''); // 좋아요 취소
                }
            },
            error: function () {
                alert("오류가 발생했습니다. 다시 시도해주세요.");
            }
        });
    });

    // <i> 태그를 눌렀을 때 이벤트를 부모 버튼으로 전파
    $('.packageLikeButton i').on('click', function (event) {
        event.stopPropagation(); // 부모 요소의 클릭 이벤트 전파를 막기 위함
        $(this).closest('.packageLikeButton').trigger('click'); // 부모 버튼 클릭 이벤트 트리거
    });
});
