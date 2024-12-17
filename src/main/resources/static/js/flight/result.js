$(document).ready(function () {
    const observer = new IntersectionObserver((entries, observer) => {
        entries.forEach(entry => {
            if (entry.isIntersecting) { // div가 화면에 보이면
                const sessionId = $('#incomplete').data('session-id');
                console.log(sessionId);
                loadMoreData(sessionId);  // 자동으로 데이터 로드
                observer.unobserve(entry.target);  // 한 번만 실행하도록 감시 중지
            }
        });
    }, { threshold: 1.0 });  // 100% 보일 때 트리거

// #incomplete 요소가 화면에 보이는지 감지
    observer.observe(document.getElementById('incomplete'));

    // $('#loadMoreButton').on('click', function () {
    //     const sessionId = $('#incomplete').data('session-id');
    //     console.log(sessionId);
    //     loadMoreData(sessionId);
    // });

    // 버튼 클릭 시 상단으로 스크롤 이동
    $('#scrollTopBtn').click(function() {
        $('html, body').animate({ scrollTop: 0 }, 'smooth'); // 부드럽게 상단으로 이동
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
        beforeSend: function() {
            // 요청 시작 전에 로딩 메시지 표시
            $('#loadingMessage').show();
        },
        success: function(data) {
            console.log("Received data:", data);
            if (data && data.flights && data.flights.length > 0) {

                const $tableBody = $('#apiResult #incompleteResult'); // 테이블의 tbody 선택

                $.each(data.flights, function(index, flight) { // flights 배열 순회

                    const newRowHtml = `
    <tr>
      <form class="detailApiCall" action="/flight/detail" method="post">
        <!-- 기타 필요 정보들을 hidden으로 전송 -->
        <input type="hidden" name="itineraryId" value="${flight.id}">
        <input type="hidden" name="token" class="tokenInput">

        <!-- 항공사 정보 -->
        <td>
          <div class="div_1">
            <div class="log">
              <img src="${flight.outLogoUrl}" alt="가는편 항공사 로고">
            </div>
            <div>${flight.outAirportName}</div>
          </div>
          <div class="div_2">
            <div class="log">
              <img src="${flight.returnLogoUrl}" alt="오는편 항공사 로고">
            </div>
            <div>${flight.returnAirportName}</div>
          </div>
        </td>

        <!-- 출발 공항 2개 -->
        <td>
          <div class="div_1">
            <span>${flight.originDisplayCode}</span>
            <span>${flight.originName}</span>
            <span>${flight.outDeparture}</span>
          </div>
          <div class="div_2">
            <span>${flight.destinationDisplayCode}</span>
            <span>${flight.destinationName}</span>
            <span>${flight.returnDeparture}</span>
          </div>
        </td>

        <!-- 가는 시간 2개 -->
        <td>
          <div class="div_1">
            <span>${flight.outDurationInMinutes}</span>
          </div>
          <div class="div_2">
            <span>${flight.returnDurationInMinutes}</span>
          </div>
        </td>

        <!-- 도착 공항 2개 -->
        <td>
          <div class="div_1">
            <span>${flight.destinationDisplayCode}</span>
            <span>${flight.destinationName}</span>
            <span>${flight.outArrival}</span>
          </div>
          <div class="div_2">
            <span>${flight.originDisplayCode}</span>
            <span>${flight.originName}</span>
            <span>${flight.returnArrival}</span>
          </div>
        </td>

        <!-- 가격 버튼-->
        <td>
          <button type="submit" name="submit_${flight.id}" class="btn btn-primary detailBtn">${flight.price}</button>
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
        },
        complete: function() {
            // 요청 완료 후 로딩 메시지 숨기기
            $('#loadingMessage').hide();
        }
    });

}
