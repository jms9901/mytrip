$(document).ready(function () {
    console.log("search js 로드")

    //로딩 처리
    $("#roundTrip").on("submit", function (e) {
        // 로딩 오버레이 표시
        console.log("왜 안대")
        $("#loading-overlay").removeClass("d-none");
    });

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
    $("#openSelectionBtn").on("click", function () {
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

    $('.passenger-item').addClass('col-12 d-flex');
    $('.sungekAge').addClass('col-5');
    $('.sungekCnt').addClass('col-2');
    $('.btn-group').addClass('w-100 d-flex');
    $('.btn-decrease').addClass('flex-fill');
    $('.btn-increase').addClass('flex-fill');


    // 공항 드롭다운 처리 함수
    function setupDropdown(dropdownClass, inputId) {
        const $dropdown = $(`.${dropdownClass}`);
        const $button = $dropdown.closest(".custom-dropdown").find(".dropdown-button");
        const $content = $dropdown.closest(".custom-dropdown").find(".dropdown-content");
        const $input = $(`#${inputId}`);

        // 드롭다운 열기/닫기
        $button.on("click", function () {
            $(".dropdown-content").not($content).removeClass("show"); // 다른 드롭다운 닫기
            $content.toggleClass("show");
        });

        // 항목 클릭 시 선택 처리
        $content.on("click", ".dropdown-item", function () {
            const selectedValue = $(this).attr("value"); // 공항 코드
            const airportName = $(this).find(".airport-name").text();
            const airportCode = $(this).find(".airport-code").text();
            const airportCity = $(this).find(".airport-city").text();
            const airportCountry = $(this).find(".airport-country").text();

            // 입력 필드에 값 설정
            $input.val(selectedValue);

            // 버튼 텍스트를 공항 이름, 코드, 도시, 국가로 설정
            $button.html(`
            <div class="airport-name">${airportName}</div>
            <div class="airport-code">${airportCode}</div>
            <div class="airport-city">${airportCity}</div>
            <div class="airport-country">${airportCountry}</div>
        `);

            // 드롭다운 메뉴 닫기
            $content.removeClass("show");
        });
    }

    // 출발지 드롭다운 설정
    setupDropdown("fromAirport", "fromEntityId");

    // 도착지 드롭다운 설정
    setupDropdown("toAirport", "toEntityId");

    // 문서 클릭 시 드롭다운 닫기
    $(document).on("click", function (event) {
        if (!$(event.target).closest(".custom-dropdown").length) {
            $(".dropdown-content").removeClass("show");
        }
    });
    // 좌석 및 인원 드롭다운
    $('#openSelectionBtn').on('click', function () {
        const $selectionContainer = $('#selectionContainer');
        const $age = $('.sungekAge');

        if ($selectionContainer.is(':visible')) {
            $selectionContainer.addClass('d-none');
        } else {
            // 요소가 보이지 않으면 위치 설정 후 표시
            const buttonOffset = $(this).offset();
            const buttonHeight = $(this).outerHeight();

            $selectionContainer.css({
                top: buttonOffset.top + buttonHeight,
                left: buttonOffset.left,
                display: 'block',  // 표시하도록 설정
            });

            // 부트스트랩 클래스 추가
            $selectionContainer.removeClass('d-none'); // 숨겨진 상태가 아닌지 확인
            $selectionContainer.addClass('d-block');    // 보이도록 설정
            $selectionContainer.addClass('position-absolute'); // 절대 위치
            $selectionContainer.addClass('border');    // 테두리 추가
            $selectionContainer.addClass('p-3');       // 패딩 추가
            $selectionContainer.addClass('bg-light');  // 배경색 추가
        }
    });

    //폼 검증
    $('#roundTrip').on('submit', function(event) {
        event.preventDefault();  // 폼 제출 방지

        // 1. 공항 선택 필드 검증
        const fromAirportId = $('#fromEntityId').val();
        const toAirportId = $('#toEntityId').val();
        if (!fromAirportId || !toAirportId) {
            alert("출발지와 도착지를 선택해주세요.");
            return;  // 검증 실패시 폼 제출 안됨
        }

        // 2. 날짜 선택 필드 검증
        const departDate = $('#start-date-value').val();
        const returnDate = $('#end-date-value').val();
        if (!departDate || !returnDate) {
            alert("가는 날과 오는 날을 선택해주세요.");
            return;
        }

        // 3. 인원 수가 0 이상이어야 함
        const adultCount = parseInt($('#adult-count').text());
        const childCount = parseInt($('#child-count').text());
        const infantCount = parseInt($('#infant-count').text());
        const totalPassengers = adultCount + childCount + infantCount;
        if (totalPassengers <= 0) {
            alert("인원 수는 최소 1명 이상이어야 합니다.");
            return;
        }

        // 4. 폼 제출 (검증이 모두 통과되었을 경우)
        this.submit();
    });


});
