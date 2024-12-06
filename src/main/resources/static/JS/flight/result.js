$(document).ready(function () {

    const sessionId = $('#incomplete').text();

    $(window).on('scroll', function() {
        var triggerElement = $('#incomplete');
        var windowHeight = $(window).height();
        var elementTop = triggerElement.offset().top;
        var scrollPosition = $(window).scrollTop();

        // #load-more-trigger가 화면에 100% 보일 때 loadMoreData 호출
        if (scrollPosition + windowHeight >= elementTop) {
            loadMoreData();  // 데이터를 더 로드하는 함수 호출
        }
    });

    function loadMoreData(flightId) {
        // 서버에서 api_2의 추가 데이터를 요청
        fetch('/result/incomplete', {
            method: 'POST',  // HTTP 요청 방식 (POST)
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                sessionId: sessionId
                // 더 필요하면 추가
            })
        })
            .then(response => response.json())  // 서버에서 받은 JSON 데이터 처리
            .then(data => {
                if (data.length > 0) {
                    // 데이터를 받아서 HTML에 추가
                    const api2Results = document.getElementById('api2-results');
                    data.forEach(item => {
                        const newItem = document.createElement('div');
                        newItem.innerHTML = `<p>${item.someData}</p>`;  // 적절한 HTML 요소로 변환
                        api2Results.appendChild(newItem);  // HTML에 추가
                    });
                } else {
                    // 더 이상 데이터가 없다면 '더 보기' 버튼을 숨기거나 처리
                    document.getElementById('load-more').style.display = 'none';
                }
            })
            .catch(error => console.error('Error loading more data:', error));  // 에러 처리
    }
});
