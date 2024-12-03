$(document).ready(function () {
    //달력 관련 기능
    // 현재 날짜 기준으로 오늘 이후 날짜만 선택 가능
    const today = new Date();

    // '가는 날' 설정
    const startPicker = flatpickr("#start-date-btn", {
        minDate: today, // 오늘 이후만 선택 가능
        dateFormat: "Y-m-d",
        onChange: function (selectedDates, dateStr) {
            if (selectedDates.length > 0) {
                $("#start-date-btn").text(dateStr); // 버튼 텍스트 업데이트
                $("#start-date-value").val(dateStr);// 인풋 value 업데이트
                endPicker.set("minDate", new Date(selectedDates[0].getTime() + 86400000));
            }
        }
    });

    // '오는 날' 설정
    const endPicker = flatpickr("#end-date-btn", {
        minDate: new Date(today.getTime() + 86400000), // 기본값: 내일부터 가능
        dateFormat: "Y-m-d",
            onChange: function (selectedDates, dateStr) {
            if (selectedDates.length > 0) {
                $("#end-date-btn").text(dateStr); // 버튼 텍스트 업데이트
                $("#end-date-value").val(dateStr);// 인풋 value 업데이트
            }
        }
    });


    // 인원수/좌석 관련
    // 초기화
    const counts = {
        adult: 0,
        child: 0,
        infant: 0
    };

    // 인원수 변경 함수
    function changeCount(type, delta) {
        const countElement = document.getElementById(`${type}-count`);
        counts[type] = Math.max(0, counts[type] + delta); // 최소값 0으로 제한
        countElement.textContent = counts[type];
    }

    // 선택 화면 열기
    document.getElementById("open-selection-btn").addEventListener("click", () => {
        const container = document.getElementById("selection-container");
        container.style.display = container.style.display === "none" ? "block" : "none";
    });
});