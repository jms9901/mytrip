$(document).ready(function () {
    $("#search-form").submit(function (e) {
        e.preventDefault();
        const formData = $(this).serialize();
        startSearch(formData);  // startSearch() 호출, formData는 입력된 폼 데이터
    });
});
