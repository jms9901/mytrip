$(document).ready(function () {

    // API 엔드포인트 URL 및 설정
    const roundtripUrl = "https://sky-scanner3.p.rapidapi.com/flights/search-roundtrip" +
        "?fromEntityId=${fromAirportId}" +    // 출발 공항
        "&toEntityId=${toAirportId}" +       // 도착 공항
        "&departDate=${departDate}" +        // 출발일
        "&returnDate=${returnDate}" +        // 도착일
        "&stops=direct" +                    // 직항로 고정
        "&currency=KRW" +                    // 금액 표시 단위
        "&adults=${adultsHeadCnt}" +         // 성인 명 수
        "&children=${childrenHeadCnt}" +     // 어린이 명 수
        "&infants=${infantsHeadCnt}" +       // 24개월 미만 영유아 명 수
        "&cabinClass=${cabinClass}";         // 객석 수준

    const incompleteBaseUrl = "https://sky-scanner3.p.rapidapi.com/flights/search-incomplete";
    const detailBaseUrl = "https://sky-scanner3.p.rapidapi.com/flights/detail";

    const headers = {
        "Content-Type": "application/json",
        "X-RapidAPI-Key": "ea7ad1eb80msh82b3ac2477cccb9p10f649jsn801a20d72c31",
        "X-RapidAPI-Host": "sky-scanner3.p.rapidapi.com"
    };

    let sessionId = null; // 세션 ID를 저장
    let itineraryId = null; // 예약 ID 저장
    let token = null; // 토큰 저장

    // `api-query` 버튼 클릭 시 startSearch() 실행
    $('#api-query').click(function () {
        startSearch();
    });

    // `detail` 버튼 클릭 시 fetchBookingDetail() 실행
    $('#detail').click(function () {
        if (itineraryId && token) {
            fetchBookingDetail(itineraryId, token);
        } else {
            console.error("세부 정보를 가져올 수 없습니다. itineraryId 또는 token이 없습니다.");
        }
    });

    // 검색 API 호출
    function startSearch() {
        $.ajax({
            url: roundtripUrl,
            method: "GET",
            headers: headers,
            timeout: 0
        }).done(function (response) {
            console.log("Initial Roundtrip API Response:", response);

            sessionId = response.data.context.sessionId || null;
            if (!sessionId) {
                console.error("세션 ID가 응답에 없습니다.");
                return;
            }

            // 검색 완료 후 페이지 이동
            window.location.href = "/incomplete.html"; // 해당 페이지로 이동
        }).fail(function (jqXHR, textStatus, errorThrown) {
            console.error("Roundtrip API 호출 실패:", textStatus, errorThrown);
        });
    }

    // 페이지 로드 후 `incomplete` div가 있으면 fetchIncompleteData 실행
    if ($("#incomplete").length > 0) {
        const incompleteInterval = setInterval(() => {
            fetchIncompleteData(sessionId, incompleteInterval);
        }, 2000);
    }

    // search-incomplete API 반복 호출
    function fetchIncompleteData(sessionId, intervalId) {
        if (!sessionId) {
            console.error("세션 ID가 설정되지 않았습니다.");
            clearInterval(intervalId); // 반복 호출 중단
            return;
        }

        const urlWithSessionId = `${incompleteBaseUrl}?sessionId=${encodeURIComponent(sessionId)}&stops=direct&currency=KRW&market=KR&locale=ko-KR`;

        $.ajax({
            url: urlWithSessionId,
            method: "GET",
            headers: headers,
            timeout: 0
        }).done(function (response) {
            console.log("Incomplete API Response:", response);

            if (response.data && response.data.context && response.data.context.status === "incomplete") {
                console.log("Incomplete 상태, 데이터를 계속 요청합니다...");
            } else {
                console.log("Complete 상태, 데이터를 저장합니다.");
                saveJsonToFile(response);

                itineraryId = response.data.itinerary && response.data.itinerary.id || null;
                token = response.data.context && response.data.context.token || null;

                if (intervalId) clearInterval(intervalId); // 상태 완료 시 반복 호출 중단
            }
        }).fail(function (jqXHR, textStatus, errorThrown) {
            console.error("Incomplete API 호출 실패:", textStatus, errorThrown);
        });
    }

    // 예약 세부 정보 가져오기
    function fetchBookingDetail(itineraryId, token) {
        const detailUrl = `${detailBaseUrl}?itineraryId=${encodeURIComponent(itineraryId)}&token=${encodeURIComponent(token)}`;

        $.ajax({
            url: detailUrl,
            method: "GET",
            headers: headers,
            timeout: 0
        }).done(function (response) {
            console.log("Detail API Response:", response);

        }).fail(function (jqXHR, textStatus, errorThrown) {
            console.error("Detail API 호출 실패:", textStatus, errorThrown);
        });
    }

});
