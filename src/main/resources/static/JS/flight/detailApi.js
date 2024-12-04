$(document).ready(function () {
    const itineraryId = $("#itinerary-id").val();  // 컨트롤러에서 전달된 itineraryId
    const token = $("#token").val();  // 컨트롤러에서 전달된 token

    if (itineraryId && token) {
        fetchBookingDetail(itineraryId, token);  // 예약 세부 정보 가져오기
    }
});
