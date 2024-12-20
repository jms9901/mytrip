$().ready(function (){
    // flatpickr 초기화
    const today = new Date();
    console.log("아아아아ㅏ아아ㅏㅇ")

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


    document.getElementById("form-submit-btn").addEventListener("click", function (event) {
        // 숨겨진 입력 필드 값 가져오기
        const startDate = document.getElementById("start-date-value").value;
        const endDate = document.getElementById("end-date-value").value;

        // 날짜 값이 비어 있는지 확인
        if (!startDate || !endDate) {
            event.preventDefault(); // 폼 전송 막기
            alert("날짜를 입력하세요."); // 팝업 메시지
        }
    });


    //웹에디터
    $('#summernote').summernote({
        toolbar: [
            ['style', ['bold', 'italic', 'underline']],
            ['font', ['strikethrough', 'superscript', 'subscript']],
            ['color', ['forecolor', 'backcolor']],
            ['para', ['ul', 'ol', 'paragraph']],
            ['insert', ['link', 'picture']]
        ],
        height: 300
    });

    document.getElementById('packageCost').addEventListener('input', function () {
        if (this.value < 0) {
            this.value = 0;
            alert('Package Cost는 0 이상의 값만 입력할 수 있습니다.');
        }
    });

    document.getElementById('packageMaxpeople').addEventListener('input', function () {
        if (this.value < 0) {
            this.value = 0;
            alert('Max Participants는 0 이상의 값만 입력할 수 있습니다.');
        }
    });
})