$(document).ready(function () {
    // cityId 받아오는거. 다른 곳에선 따로 쓰도록
    const cityId = $('meta[name="city-id"]').attr('content');

    // 좋아요 버튼 클릭 이벤트
    $('.cityLikeButton').on('click', function () {
        console.log("들어오긴 함?");
        $.ajax({
            url: `/likey/city`,
            method: 'GET',
            data: { cityId: cityId },
            success: function (response) {
                // 이미지 나중에 적당히 변경
                if (response.status === 1) {
                    $('.like-button').text('좋아요 취소');
                } else {
                    $('.like-button').text('좋아요 이미지');
                }
            },
            error: function (xhr, status, error) {
                alert("오류가 발생했습니다. 다시 시도해주세요.");
            }
        });
    });
});