$(document).ready(function () {
    console.log("search js 로드");

    // flatpickr 초기화
    const today = new Date();

    flatpickr("#start-date-btn", {
        minDate: today,
        dateFormat: "Y-m-d",
        onChange: function (selectedDates, dateStr) {
            if (selectedDates.length > 0) {
                $("#start-date-btn").text(dateStr).attr("type", "button");
                $("#start-date-value").val(dateStr);
                endPicker.set("minDate", new Date(selectedDates[0].getTime() + 86400000));
                $('#end-date-btn').prop('disabled', false); // endPicker 활성화
                $("#end-date-btn").text("오는 날"); // 버튼 텍스트 초기화
                $("#end-date-value").val("");
                endPicker.clear(); // endPicker 날짜 초기화
            }
        }
    });

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
    $('#end-date-btn').prop('disabled', true).attr("type", "button");

// 좌석 및 인원 드롭다운 열기/닫기
    $("#openSelectionBtn").on("click", function (e) {
        e.preventDefault(); // 기본 동작 방지

        const $button = $(this); // 버튼
        const $selectionContainer = $("#selectionContainer"); // 드롭다운

        // 버튼의 위치와 크기 계산
        const buttonOffset = $button.offset();
        const buttonHeight = $button.outerHeight();
        const buttonWidth = $button.outerWidth();

        // 드롭다운 위치 설정
        if ($selectionContainer.hasClass("d-none")) {
            $selectionContainer.css({
                top: buttonOffset.top + buttonHeight + 5, // 버튼 바로 아래
                left: buttonOffset.left, // 버튼 왼쪽 정렬
                minWidth: buttonWidth // 버튼 너비와 동일하게 설정
            }).removeClass("d-none").addClass("d-block");
        } else {
            $selectionContainer.removeClass("d-block").addClass("d-none");
        }
    });

// 문서 외부 클릭 시 드롭다운 닫기
    $(document).on("click", function (event) {
        if (!$(event.target).closest("#selectionContainer").length &&
            !$(event.target).is("#openSelectionBtn")) {
            $("#selectionContainer").removeClass("d-block").addClass("d-none");
        }
    });

    // 인원 증가/감소 버튼 클릭 처리
    $(".btn-sm").on("click", function () {
        const $button = $(this); // 현재 클릭된 버튼
        const $parent = $button.closest(".col-auto"); // 버튼이 위치한 부모 컨테이너
        const $countElement = $parent.find("span"); // 숫자 표시 <span>
        const $inputField = $parent.find("input[type='hidden']"); // 숨겨진 <input>

        let currentCount = parseInt($countElement.text()) || 0; // 현재 값
        const delta = $button.attr("id").includes("increase") ? 1 : -1; // 증가/감소 결정
        const newCount = Math.max(0, currentCount + delta); // 최소값 0 이상

        // 숫자 업데이트
        $countElement.text(newCount);
        $inputField.val(newCount); // 숨겨진 <input> 값 업데이트
    });

    // 좌석 선택 변경 시 숨겨진 input 동기화
    $("#cabin-class-select").on("change", function () {
        const selectedClass = $(this).val();
        console.log(`선택된 좌석: ${selectedClass}`);
        $("#cabin-class-input").val(selectedClass);
    });

    // 드롭다운 처리 함수
    function setupDropdown(dropdownClass, inputId) {
        const $dropdown = $(`.${dropdownClass}`);
        const $button = $dropdown.find(".dropdown-button");
        const $content = $dropdown.find(".dropdown-content");
        const $input = $(`#${inputId}`);

        $button.on("click", function () {
            $(".dropdown-content").not($content).removeClass("show");
            $content.toggleClass("show");
        });

        $content.on("click", ".dropdown-item-btn", function () {
            const selectedValue = $(this).attr("value");
            const airportCode = $(this).find("span").text().trim();
            const airportCity = $(this).find("small").text().trim();

            $input.val(selectedValue);

            // 기존 구조를 유지하면서 내용만 변경
            // `airport-code`와 `airport-city` 텍스트만 업데이트
            $button.find(".airport-code").text(airportCode);
            $button.find(".airport-city").html(`${airportCity} <span class="dropdown-icon">▼</span>`); // 아이콘 유지

            $content.removeClass("show");
        });

        $(document).on("click", function (event) {
            if (!$(event.target).closest(".custom-dropdown").length) {
                $content.removeClass("show");
            }
        });
    }

    // 도착지 드롭다운 설정
    setupDropdown("custom-dropdown", "toEntityId");

    // 출발지 드롭다운 설정
    setupDropdown("fromAirport", "fromEntityId");

    // 폼 검증 및 조회 버튼만 제출 허용
    $('#roundTrip').on('submit', function (event) {
        const clickedButtonId = $(document.activeElement).attr("id");
        if (clickedButtonId !== "api-query") {
            event.preventDefault(); // 조회 버튼이 아닌 경우 제출 방지
            return;
        }

        const fromAirportId = $('#fromEntityId').val();
        const toAirportId = $('#toEntityId').val();
        if (!fromAirportId || !toAirportId) {
            alert("출발지와 도착지를 선택해주세요.");
            event.preventDefault();
            return;
        }

        const departDate = $('#start-date-value').val();
        const returnDate = $('#end-date-value').val();
        if (!departDate || !returnDate) {
            alert("가는 날과 오는 날을 선택해주세요.");
            event.preventDefault();
            return;
        }

        const adultCount = parseInt($('#adult-count').text());
        const childCount = parseInt($('#child-count').text());
        const infantCount = parseInt($('#infant-count').text());
        const totalPassengers = adultCount + childCount + infantCount;
        if (totalPassengers <= 0) {
            alert("인원 수는 최소 1명 이상이어야 합니다.");
            event.preventDefault();
            return;
        }

        $("#loading-overlay").removeClass("d-none");
    });

    // 페이지 로드 후 로딩 오버레이 숨기기
    $(window).on("pageshow", function () {
        $("#loading-overlay").addClass("d-none");
    });

    // 슬라이더 이미지 변경
    const images = document.querySelectorAll("#background-slider .slider-image");
    let currentIndex = 0;

    function changeImage() {
        images[currentIndex].classList.remove("active");
        currentIndex = (currentIndex + 1) % images.length;
        images[currentIndex].classList.add("active");
    }

    setInterval(changeImage, 5000);
    images[currentIndex].classList.add("active");
});
