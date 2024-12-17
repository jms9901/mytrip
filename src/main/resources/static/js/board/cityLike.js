$('.likeButton').off('click').on('click', function (event) {
    event.preventDefault();

    const cityId = $(this).data('city-id');
    const packageId = $(this).data('package-id');
    const postId = $(this).data('group-id');

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

    console.log("Request URL:", url);
    console.log("Request Data:", data);

    $.ajax({
        url: url,
        method: 'GET',
        data: data,
        success: function (response) {
            console.log("Response:", response);
            const icon = $(event.currentTarget).find('i');
            if (response) {
                icon.removeClass('bi-heart').addClass('bi-heart-fill');
            } else {
                icon.removeClass('bi-heart-fill').addClass('bi-heart');
            }
        },
        error: function () {
            alert("오류가 발생했습니다. 다시 시도해주세요.");
        }
    });
});
