$(document).ready(function () {
    // $(window).on('scroll', function() {
    //     var triggerElement = $('#incomplete');
    //     var windowHeight = $(window).height();
    //     var elementTop = triggerElement.offset().top;
    //     var scrollPosition = $(window).scrollTop();
    //
    //     // #load-more-trigger가 화면에 100% 보일 때 loadMoreData 호출
    //     if (scrollPosition + windowHeight >= elementTop) {
    //         loadMoreData();  // 데이터를 더 로드하는 함수 호출
    //     }
    // });

    $('#loadMoreButton').on('click', function () {
        const sessionId = $('#incomplete').data('session-id');
        console.log(sessionId);
        loadMoreData(sessionId);
    });

});


function loadMoreData(sessionId) {
    console.log("불러오기 더 정보")
    // 서버에서 incomplete의 추가 데이터를 요청

    //complete일 경우 더 이상 쿼리를 진행하디 않음

    $.ajax({
        url: 'http://localhost:8082/flight/result/incomplete', // API 엔드포인트
        method: 'POST', // HTTP 요청 방식
        contentType: 'application/json', // Content-Type 설정
        data: JSON.stringify({
            sessionId: sessionId // 필요한 데이터 전달
            // 추가 데이터가 있다면 여기에 포함
        }),
        success: function(data) {
            console.log("Received data:", data);
            if (data && data.flights && data.flights.length > 0) {

                const $tableBody = $('#apiResult #incompleteResult'); // 테이블의 tbody 선택

                $.each(data.flights, function(index, flight) { // flights 배열 순회

                    const $newRow = $('<tr>'); // 새로운 행 생성
                    const $priceCell = $('<td>').text(flight.price); // 가격 셀 생성 및 값 추가
                    const $departureCell = $('<td>').text(flight.departure); // 출발 셀 생성 및 값 추가

                    $newRow.append($priceCell, $departureCell); // 행에 셀 추가
                    $tableBody.append($newRow); // 테이블에 새 행 추가

                });
            } else {
                console.log("추가 데이타가 읍스요.");
            }
        },
        error: function(xhr, status, error) {
            console.error('비사아아앙:', status, error);
        }
    });

}
