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

    $('.detailApiCall').on('submit', function(event) {
        event.preventDefault(); // 폼 제출을 잠시 멈춤
        var token = $('div[data-token]').attr('data-token'); // div에서 token 값 가져오기
        $('.tokenInput').val(token); // hidden input에 값 설정하기
        this.submit(); // 폼 제출
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

                    const newRowHtml = `
                    <tr>
                        <form id="form-flight-${flight.id}" class="flight-form" action="/flight/detail" method="post">
                            <input type="hidden" class="flight-form" name="flightId" value="${flight.id}">
                            <td class="departure-cell" >${flight.outDeparture}</td>
                            <td class="button-cell">
                                <button class="detailApiBtn" type="submit">${flight.price}</button>
                            </td>
                        </form>
                    </tr>
                    `;

                    // 테이블 본문에 추가
                    $('#incompleteResult').append(newRowHtml);
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
