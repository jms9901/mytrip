// 공통 API 호출 및 데이터 처리 함수들

function startSearch(formData) {

    const API_KEY = 'ea7ad1eb80msh82b3ac2477cccb9p10f649jsn801a20d72c31'

    // 각 항목을 변수로 할당
    let fromAirportId = "";
    let toAirportId = "";
    let departDate = "";
    let returnDate = "";
    let adultsHeadCnt = 0;
    let childrenHeadCnt = 0;
    let infantsHeadCnt = 0;
    let cabinClass = "";

    formData.forEach(function(item) {
        if (item.name === "fromAirportId") {
            fromAirportId = item.value;
        }
        if (item.name === "toAirportId") {
            toAirportId = item.value;
        }
        if (item.name === "departDate") {
            departDate = item.value;
        }
        if (item.name === "returnDate") {
            returnDate = item.value;
        }
        if (item.name === "adultsHeadCnt") {
            adultsHeadCnt = item.value;
        }
        if (item.name === "childrenHeadCnt") {
            childrenHeadCnt = item.value;
        }
        if (item.name === "infantsHeadCnt") {
            infantsHeadCnt = item.value;
        }
        if (item.name === "cabinClass") {
            cabinClass = item.value;
        }
    });

    // URL 구성
    const roundtripUrl = `https://sky-scanner3.p.rapidapi.com/flights/search-roundtrip` +
        `?fromEntityId=${encodeURIComponent(fromAirportId)}` +  // 출발 공항
        `&toEntityId=${encodeURIComponent(toAirportId)}` +      // 도착 공항
        `&departDate=${encodeURIComponent(departDate)}` +       // 출발일
        `&returnDate=${encodeURIComponent(returnDate)}` +       // 도착일
        `&stops=direct` +                                       // 직항로 고정
        `&currency=KRW` +                                       // 금액 표시 단위
        `&adults=${encodeURIComponent(adultsHeadCnt)}` +        // 성인 명 수
        `&children=${encodeURIComponent(childrenHeadCnt)}` +    // 어린이 명 수
        `&infants=${encodeURIComponent(infantsHeadCnt)}` +      // 유아 명 수
        `&cabinClass=${encodeURIComponent(cabinClass)}`;        // 객석 수준

    $.ajax({
        url: roundtripUrl,
        method: "GET",
        headers: {
            "Content-Type": "application/json",
            "X-RapidAPI-Key": API_KEY,
            "X-RapidAPI-Host": "sky-scanner3.p.rapidapi.com"
        },
    }).done(function (response) {
        const sessionId = response.data.context.sessionId;
        if (!sessionId) {
            alert("세션 생성 실패!");
            return;
        }

        // 세션 ID를 컨트롤러로 전달
        $.post("/search/startSearch", { sessionId: sessionId }, function () {
            console.log("새로운 세션 ID 컨트롤러에 전달 완료");
        });
    }).fail(function (jqXHR, textStatus, errorThrown) {
        console.error("API 호출 실패:", textStatus, errorThrown);
    });
}

function fetchIncompleteData(sessionId, intervalId) {
    $.ajax({
        url: `https://sky-scanner3.p.rapidapi.com/flights/search-incomplete?sessionId=${sessionId}`,
        method: "GET",
        headers: {
            "Content-Type": "application/json",
            "X-RapidAPI-Key": "API_KEY",
            "X-RapidAPI-Host": "sky-scanner3.p.rapidapi.com"
        },
        timeout: 0
    }).done(function (response) {
        if (response.data.context.status === "complete") {
            clearInterval(intervalId);
            $.post("/result/completeSearch", { data: response.data }, function () {
                console.log("완료된 데이터 컨트롤러에 전달 완료");
            });
        }
    }).fail(function (jqXHR, textStatus, errorThrown) {
        console.error("Incomplete API 호출 실패:", textStatus, errorThrown);
    });
}

function fetchBookingDetail(itineraryId, token) {
    $.ajax({
        url: `https://sky-scanner3.p.rapidapi.com/flights/detail?itineraryId=${itineraryId}&token=${token}`,
        method: "GET",
        headers: {
            "Content-Type": "application/json",
            "X-RapidAPI-Key": "API_KEY",
            "X-RapidAPI-Host": "sky-scanner3.p.rapidapi.com"
        },
        timeout: 0
    }).done(function (response) {
        console.log("Detail API Response:", response);
        //화면에 출력
    }).fail(function (jqXHR, textStatus, errorThrown) {
        console.error("Detail API 호출 실패:", textStatus, errorThrown);
    });
}
