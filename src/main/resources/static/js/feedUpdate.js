$(document).ready(function () {
    const apiUrl = "/mypage/feed";
    const urlParams = new URLSearchParams(window.location.search);
    const feedId = urlParams.get('feedId');

    if (!feedId) {
        alert("Invalid feed ID.");
        window.location.href = '/mypage/feed.html'; // 목록 페이지로 리다이렉트
        return;
    }

    // 수정 데이터 로딩
    function loadFeedUpdate(feedId) {
        $.ajax({
            url: `${apiUrl}/detail/${feedId}`,
            method: 'GET',
            success: function (feed) {
                $('#feed-title').val(feed.boardSubject);
                $('#feed-content').val(feed.boardContent);
                $('#city-name').val(feed.cityId);
            },
            error: function () {
                alert("Failed to load feed details.");
                window.location.href = '/mypage/feed.html'; // 목록 페이지로 리다이렉트
            }
        });
    }

    // 수정 요청
    function updateFeed(feedId) {
        const updatedFeed = {
            boardSubject: $('#feed-title').val(),
            boardContent: $('#feed-content').val(),
            cityId: $('#city-name').val(),
        };

        $.ajax({
            url: `${apiUrl}/update/${feedId}`,
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(updatedFeed),
            success: function () {
                alert("Feed updated successfully.");
                window.location.href = `/mypage/feedDetail.html?feedId=${feedId}`;
            },
            error: function () {
                alert("Failed to update feed.");
            }
        });
    }

    $('#save-button').click(function () {
        updateFeed(feedId);
    });

    loadFeedUpdate(feedId);
});