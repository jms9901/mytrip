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
    const feed = $('#groups');
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

// DOMContentLoaded 이벤트를 사용하여 DOM이 완전히 로드된 후 실행되도록 설정
document.addEventListener("DOMContentLoaded", function () {
    let scrollMenu = document.getElementById("scrollMenu");
    let section3 = document.querySelector(".container"); // class를 사용하여 선택

    if (!scrollMenu || !section3) {
        console.error("scrollMenu 또는 section3 요소를 찾을 수 없습니다.");
        return;
    }

    // 통합된 스크롤 이벤트 핸들러
    function handleScroll() {
        let section3Top = section3.offsetTop; // section3의 상단 위치
        let scrollPosition = window.scrollY; // 현재 스크롤 위치

        // 메뉴바 표시/숨기기 로직
        if (scrollPosition >= section3Top) {
            scrollMenu.style.display = "block"; // section3 이후에 메뉴바 표시
        } else {
            scrollMenu.style.display = "none"; // section3 이전에는 메뉴바 숨기기
        }
    }

    // 기존의 `onscroll`에 영향을 주지 않도록 함수로 분리
    window.addEventListener("scroll", handleScroll);
});
