$(document).ready(function () {
    console.log("search js 로드");

    // flatpickr 초기화
    const today = new Date();
    let progress = 0;
    let interval;

    flatpickr("#start-date-btn", {
        minDate: today,
        dateFormat: "Y-m-d",
        onChange: function (selectedDates, dateStr) {
            if (selectedDates.length > 0) {
                $("#start-date-btn").text(dateStr).attr("type", "button");
                $("#start-date-value").val(dateStr);
                endPicker.set("minDate", new Date(selectedDates[0].getTime() + 86400000));
                $('#end-date-btn').prop('disabled', false);
                $("#end-date-btn").text("오는 날");
                $("#end-date-value").val("");
                endPicker.clear();
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
        e.preventDefault();

        const $button = $(this);
        const $selectionContainer = $("#selectionContainer");

        const buttonOffset = $button.offset();
        const buttonHeight = $button.outerHeight();
        const buttonWidth = $button.outerWidth();

        if ($selectionContainer.hasClass("d-none")) {
            $selectionContainer.css({
                top: buttonOffset.top + buttonHeight + 5,
                left: buttonOffset.left,
                minWidth: buttonWidth
            }).removeClass("d-none").addClass("d-block");
        } else {
            $selectionContainer.removeClass("d-block").addClass("d-none");
            updateSelectionButtonText();
        }
    });

    $(document).on("click", function (event) {
        if (!$(event.target).closest("#selectionContainer").length &&
            !$(event.target).is("#openSelectionBtn")) {
            $("#selectionContainer").removeClass("d-block").addClass("d-none");
            updateSelectionButtonText();
        }
    });

    // 인원 증가/감소 버튼 처리
    $(".btn-sm").on("click", function () {
        const $button = $(this);
        const $parent = $button.closest(".col-auto");
        const $countElement = $parent.find("span");
        const $inputField = $parent.find("input[type='hidden']");

        let currentCount = parseInt($countElement.text()) || 0;
        const delta = $button.attr("id").includes("increase") ? 1 : -1;
        const newCount = Math.max(0, currentCount + delta);

        $countElement.text(newCount);
        $inputField.val(newCount);
    });

    // 좌석 선택 처리
    $("#cabin-class-select").on("change", function () {
        const selectedClass = $(this).val();
        $("#cabin-class-input").val(selectedClass);
    });

    function updateSelectionButtonText() {
        const adultCount = parseInt($("#adult-count").text()) || 0;
        const childCount = parseInt($("#child-count").text()) || 0;
        const infantCount = parseInt($("#infant-count").text()) || 0;
        const totalCount = adultCount + childCount + infantCount;
        const cabinClass = $("#cabin-class-select option:selected").text();

        let passengerText = "";

        if (totalCount === 1) {
            if (adultCount === 1) passengerText = "성인 1명";
            else if (childCount === 1) passengerText = "어린이 1명";
            else if (infantCount === 1) passengerText = "아기 1명";
        } else if (totalCount > 1) {
            passengerText = `인원 ${totalCount}명`;
        }

        const buttonText = `${passengerText} ${cabinClass}`;
        $("#openSelectionBtn").text(buttonText);
    }

    updateSelectionButtonText();

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

            $button.find(".airport-code").text(airportCode);
            $button.find(".airport-city").html(`${airportCity} <span class="dropdown-icon">▼</span>`);

            $content.removeClass("show");
        });

        $(document).on("click", function (event) {
            if (!$(event.target).closest(".custom-dropdown").length) {
                $content.removeClass("show");
            }
        });
    }

    setupDropdown("custom-dropdown", "toEntityId");
    setupDropdown("fromAirport", "fromEntityId");

    // 폼 검증 및 로딩 화면 동작
    $('#roundTrip').on('submit', function (event) {
        const clickedButtonId = $(document.activeElement).attr("id");
        if (clickedButtonId !== "api-query") {
            event.preventDefault();
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

        $("#loading-text").html(`${fromAirportId}에서 ${toAirportId}까지<br>왕복 여정을 찾고 있습니다`);
        $("#loading-overlay").removeClass("d-none");

        progress = 0;
        const loadingBar = $(".loading-bar");
        interval = setInterval(() => {
            if (progress < 100) {
                progress += 1;
                loadingBar.css("width", `${progress}%`);
            } else {
                clearInterval(interval);
                setTimeout(() => {
                    $("#loading-overlay").addClass("d-none");
                }, 500);
            }
        }, 50);
    });

    // 페이지 로드 시 로딩 오버레이 초기화
    $(window).on("pageshow", function () {
        $("#loading-overlay").addClass("d-none");
        $(".loading-bar").css("width", "0"); // 로딩 바 초기화
        progress = 0; // 내부 값 초기화
    });

    $(window).on('pagehide', function(){
        clearInterval(interval);
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

