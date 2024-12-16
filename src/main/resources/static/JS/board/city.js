document.getElementById("goFeedButton").addEventListener("click", function () {
    // 현재 URL에서 cityId 추출
    const pathParts = window.location.pathname.split("/"); // URL을 '/' 기준으로 나누기
    const cityId = pathParts[3]; // 'board/city/{cityId}'에서 3번째 요소가 cityId

    console.log("cityId: ", cityId); // 디버깅용 로그

    // cityId 값을 사용해 이동 경로 설정
    window.location.href = `/board/city/${cityId}/feeds`;
});