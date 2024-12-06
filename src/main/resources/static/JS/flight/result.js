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
    // 서버에서 api_2의 추가 데이터를 요청
    fetch('http://localhost:8082/flight/result/incomplete', {
        method: 'POST',  // HTTP 요청 방식 (POST)
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            sessionId: sessionId
            // 더 필요하면 추가
        })
    })
        // .then(response => response.json())  // 서버에서 받은 JSON 데이터 처리
        // .then(data => {
        //     console.log("일단 이거 실행은 되냐")
        //     console.log(data.length)
        //     if (data.length > 0) {
        //         console.log("data")
        //         const tableBody = document.querySelector('#flights-table tbody');  // 기존 테이블의 tbody 선택
        //
        //         data.forEach(flight => {
        //             const newRow = document.createElement('tr');  // 새로운 행 생성
        //             const priceCell = document.createElement('td');
        //             priceCell.textContent = flight.price;  // 가격 데이터 추가
        //             const departureCell = document.createElement('td');
        //             departureCell.textContent = flight.departure;  // 출발지 데이터 추가
        //
        //             newRow.appendChild(priceCell);  // 새 행에 가격 셀 추가
        //             newRow.appendChild(departureCell);  // 새 행에 출발 셀 추가
        //             tableBody.appendChild(newRow);  // 새 행을 테이블에 추가
        //             console.log("한 놈 추가")
        //         });
        //     }
        // })
        // .catch(error => console.error('Error loading more flights:', error));
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return response.text();  // 텍스트로 응답을 먼저 확인
        })
        .then(text => {
            console.log("Received response text:", text);  // 응답 텍스트 출력
            try {
                const data = JSON.parse(text);  // JSON 파싱 시도
                console.log("Received data:", data);
                console.log("Received flights array:", data.flights);
                if (data && data.flights && data.flights.length > 0) {
                    const tableBody = document.querySelector('#flights-table tbody');  // 기존 테이블의 tbody 선택
                    data.forEach(flight => {
                        const newRow = document.createElement('tr');  // 새로운 행 생성
                        const priceCell = document.createElement('td');
                        priceCell.textContent = flight.price;  // 가격 데이터 추가
                        const departureCell = document.createElement('td');
                        departureCell.textContent = flight.departure;  // 출발지 데이터 추가

                        newRow.appendChild(priceCell);  // 새 행에 가격 셀 추가
                        newRow.appendChild(departureCell);  // 새 행에 출발 셀 추가
                        tableBody.appendChild(newRow);  // 새 행을 테이블에 추가
                        console.log("한 놈 추가")
                    });
                } else {
                    console.log("No data received.");
                }
            } catch (error) {
                console.error("Error parsing JSON:", error);
            }
        })
        .catch(error => {
            console.error('Error loading data:', error);
        });
}
