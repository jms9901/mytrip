$().ready(function (){
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


})