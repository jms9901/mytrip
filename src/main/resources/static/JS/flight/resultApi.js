$(document).ready(function () {
    const sessionId = $("#session-id").val(); // 컨트롤러에서 전달된 기존 세션 ID

    if (sessionId) {
        startIncompleteDataFetch(sessionId); // 기존 세션 ID로 데이터를 가져옴
    }

    // 새 검색 폼 제출 처리
    $("#search-form").submit(function (e) {
        e.preventDefault();
        const formData = $(this).serialize();

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
            const newSessionId = response.data.context.sessionId;
            if (!newSessionId) {
                alert("세션 생성 실패!");
                return;
            }

            // 새로운 세션 ID를 컨트롤러로 전달
            $.post("/controller/startSearch", { sessionId: newSessionId }, function () {
                console.log("새로운 세션 ID 컨트롤러에 전달 완료");
                location.reload();  // 페이지 새로고침하여 새로운 세션 데이터 표시
            });
        }).fail(function (jqXHR, textStatus, errorThrown) {
            console.error("API 호출 실패:", textStatus, errorThrown);
        });
    });

    // 기존 세션의 데이터 가져오기
    function startIncompleteDataFetch(sessionId) {
        const intervalId = setInterval(() => {
            fetchIncompleteData(sessionId, intervalId);
        }, 2000);
    }
});
