$(document).ready(function () {

    $('.packageLikeButton').on('click', function () {
        const packageId = $(this).data('package-id');

        console.log("like 동작");
        console.log(packageId);

        $.ajax({
            url: `/likey/package`,
            method: 'GET',
            data: { packageId: packageId },
            success: function (response) {
                // 이미지 변경은 좀 다름
                if (response === 1) {
                    $(`button[data-package-id="${packageId}"]`).text('좋아요 취소');
                } else {
                    $(`button[data-package-id="${packageId}"]`).text('좋아요 이미지');
                }
            },
            error: function () {
                alert("오류가 발생했습니다. 다시 시도해주세요.");
            }
        });
    });
});