document.getElementById("goFeedButton").addEventListener("click", function () {
    // 현재 URL에서 cityId 추출
    const pathParts = window.location.pathname.split("/"); // URL을 '/' 기준으로 나누기
    const cityId = pathParts[3]; // 'board/city/{cityId}'에서 3번째 요소가 cityId

    console.log("cityId: ", cityId); // 디버깅용 로그

    // cityId 값을 사용해 이동 경로 설정
    window.location.href = `/board/city/${cityId}/feeds`;
});

$().ready(function() {
    const packages = $('#packages');
    const feed = $('#feed');
    const items = $('.slider-item');
    const itemWidth = items.first().outerWidth();
    const totalWidth = items.length * itemWidth;
    const packagesWidth = packages.width();
    const feedWidth = feed.width();
    let currentPosition = 0;

    //패키지 슬라이드
    $('.package-prev-btn').click(function() {
        if (currentPosition < 0) {  // 첫 번째 슬라이드로 돌아갈 수 있으면
            currentPosition += itemWidth;  // X 좌표를 오른쪽으로 이동
            packages.css('transform', 'translateX(' + currentPosition + 'px)');
        }
    });
    $('.package-next-btn').click(function() {
        // 마지막 슬라이드로 가면 더 이상 이동하지 않도록
        if (currentPosition > -(totalWidth - packagesWidth)) {
            currentPosition -= itemWidth;  // X 좌표를 왼쪽으로 이동
            packages.css('transform', 'translateX(' + currentPosition + 'px)');
        }
    });

    //소모임 슬라이드
    $('.group-prev-btn').click(function() {
        if (currentPosition < 0) {  // 첫 번째 슬라이드로 돌아갈 수 있으면
            currentPosition += itemWidth;  // X 좌표를 오른쪽으로 이동
            feed.css('transform', 'translateX(' + currentPosition + 'px)');
        }
    });
    $('.group-next-btn').click(function() {
        // 마지막 슬라이드로 가면 더 이상 이동하지 않도록
        if (currentPosition > -(totalWidth - feedWidth)) {
            currentPosition -= itemWidth;  // X 좌표를 왼쪽으로 이동
            feed.css('transform', 'translateX(' + currentPosition + 'px)');
        }
    });
});