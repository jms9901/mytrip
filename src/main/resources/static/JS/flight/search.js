$(document).ready(function () {
    // flatpickr 초기화
    const today = new Date();

    flatpickr("#start-date-btn", {
        minDate: today,
        dateFormat: "Y-m-d",
        onChange: function (selectedDates, dateStr) {
            if (selectedDates.length > 0) {
                $("#start-date-btn").text(dateStr).attr("type", "button");
                $("#start-date-value").val(dateStr);
                // endPicker의 minDate를 startPicker의 선택된 날짜 다음날로 설정
                endPicker.set("minDate", new Date(selectedDates[0].getTime() + 86400000));
                // endPicker의 입력을 활성화
                $('#end-date-btn').prop('disabled', false); // endPicker 활성화
                // endPicker 값을 초기화 (초깃값으로 되돌리기)
                $("#end-date-btn").text("Select end date"); // 버튼 텍스트를 초기화
                $("#end-date-value").val(""); // end-date-value 값을 초기화
                endPicker.clear(); // endPicker 날짜 초기화
            }
        }
    });

    // endPicker는 처음에는 비활성화
    const endPicker = flatpickr("#end-date-btn", {
        minDate: new Date(today.getTime() + 86400000),
        dateFormat: "Y-m-d",
        onChange: function (selectedDates, dateStr) {
            if (selectedDates.length > 0) {
                $("#end-date-btn").text(dateStr);
                $("#end-date-value").val(dateStr);
            }
        }
    });

    $("#start-date-btn").attr("type", "button");

    // startPicker를 선택하기 전에는 endPicker를 비활성화
    $('#end-date-btn').prop('disabled', true) // 처음에 endPicker 비활성화
        .attr("type", "button");


    // 인원수 초기화 및 변경 로직
    window.changeCount = function (type, delta) {
        const countElement = document.getElementById(`${type}-count`);
        counts[type] = Math.max(0, counts[type] + delta);
        countElement.textContent = counts[type];
    };

    // 인원 및 좌석 선택 화면 열기/닫기
    $("#open-selection-btn").on("click", function () {
        const container = $("#selection-container");
        container.toggle();
    }).attr("type", "button");

    // 각 그룹의 카운트를 추적하는 객체
    let counts = {
        infant: 0,
        child: 0,
        adult: 0
    };

    // changeCount 함수
    function changeCount(type, delta) {
        const countElement = document.getElementById(`${type}-count`);
        counts[type] = Math.max(0, counts[type] + delta); // 0 이하로 감소하지 않도록 제한
        countElement.textContent = counts[type];
    }

    $('#infant-group .btn-decrease').on('click', function() {
        changeCount('infant', -1);
        $("#infant-count-input").val($("#infant-count").text());
    });

    $('#infant-group .btn-increase').on('click', function() {
        changeCount('infant', 1);
        $("#infant-count-input").val($("#infant-count").text());
    });

    $('#child-group .btn-decrease').on('click', function() {
        changeCount('child', -1);
        $("#child-count-input").val($("#child-count").text());
    });
    $('#child-group .btn-increase').on('click', function() {
        changeCount('child', 1);
        $("#child-count-input").val($("#child-count").text());
    });

    $('#adult-group .btn-decrease').on('click', function() {
        changeCount('adult', -1);
        $("#adult-count-input").val($("#adult-count").text());
    });
    $('#adult-group .btn-increase').on('click', function() {
        changeCount('adult', 1);
        $("#adult-count-input").val($("#adult-count").text());
    });

    $(".btn-increase").attr("type", "button");
    $(".btn-decrease").attr("type", "button");

});
