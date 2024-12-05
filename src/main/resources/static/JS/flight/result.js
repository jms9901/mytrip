$(document).ready(function () {

    console.log("작동한다어ㅏ아ㅣㅏ")
    let roundtripUrl = null;

    fetch('/result')  // 컨트롤러에서 JSON을 반환하는 엔드포인트
        .then(response => response.json())
        .then(data => {
            console.log(data);  // flightRoundTrip 객체를 사용할 수 있습니다.
            roundtripUrl = `https://sky-scanner3.p.rapidapi.com/flights/search-roundtrip` +
                `?fromEntityId=${data.fromAirportId}` +  // 출발 공항
                `&toEntityId=${data.toAirportId}` +      // 도착 공항
                `&departDate=${data.departDate}` +       // 출발일
                `&returnDate=${data.returnDate}` +       // 도착일
                `&stops=direct` +                                       // 직항로 고정
                `&currency=KRW` +                                       // 금액 표시 단위
                `&adults=${data.adultsHeadCnt}` +        // 성인 명 수
                `&children=${data.childrenHeadCnt}` +    // 어린이 명 수
                `&infants=${data.infantsHeadCnt}` +      // 유아 명 수
                `&cabinClass=${data.cabinClass}`;        // 객석 수준
        })
        .catch(error => console.error('Error fetching data:', error));


    console.log(roundtripUrl); // 객체 출력
});
