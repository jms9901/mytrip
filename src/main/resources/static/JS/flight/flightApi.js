// 공통 API 호출 및 데이터 처리 함수들

function startSearch(formData) {
    $.ajax({
        url: "https://sky-scanner3.p.rapidapi.com/flights/search-roundtrip",
        method: "GET",
        headers: {
            "Content-Type": "application/json",
            "X-RapidAPI-Key": "API_KEY",
            "X-RapidAPI-Host": "sky-scanner3.p.rapidapi.com"
        },
        data: formData,
        timeout: 0
    }).done(function (response) {
        const sessionId = response.data.context.sessionId;
        if (!sessionId) {
            alert("세션 생성 실패!");
            return;
        }

        // 세션 ID를 컨트롤러로 전달
        $.post("/controller/startSearch", { sessionId: sessionId }, function () {
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
            $.post("/controller/completeSearch", { data: response.data }, function () {
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
        // 예약 세부 정보 처리
        saveJsonToFile(response);
    }).fail(function (jqXHR, textStatus, errorThrown) {
        console.error("Detail API 호출 실패:", textStatus, errorThrown);
    });
}

function saveJsonToFile(jsonData) {
    const blob = new Blob([JSON.stringify(jsonData)], { type: "application/json" });
    const url = URL.createObjectURL(blob);
    const a = document.createElement("a");
    a.href = url;
    a.download = "data.json";
    a.click();
    URL.revokeObjectURL(url);
}
