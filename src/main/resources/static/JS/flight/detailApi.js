$(document).ready(function () {
    // // URL에서 itineraryId 추출
    // const params = new URLSearchParams(window.location.search);
    // const itineraryId = params.get('itineraryId');
    //
    // if (!itineraryId) {
    //     alert('Itinerary ID가 없습니다.');
    //     return;
    // }

    // detail API 호출
    // const detailUrl = `https://sky-scanner3.p.rapidapi.com/flights/detail?itineraryId=${itineraryId}?&token=${token}&stops=direct&market=KR&currency=KRW&locale=ko-KR\`;`;
    const detailUrl = `https://sky-scanner3.p.rapidapi.com/flights/detail?itineraryId=12409-2412101040--32128-0-13554-2412101630|13554-2412300845--32672-1-12409-2412311030&token=eyJhIjoxLCJjIjowLCJpIjowLCJjYyI6ImVjb25vbXkiLCJvIjoiSUNOIiwiZCI6IkxIUiIsImQxIjoiMjAyNC0xMi0xMCIsImQyIjoiMjAyNC0xMi0zMCJ9&stops=direct&market=KR&currency=KRW&locale=ko-KR`;
    const headers = {
        "Content-Type": "application/json",
        "X-RapidAPI-Key": "6400a15222msh8627a40b3bd3531p1bdef5jsnaa784d38dcf3",
        "X-RapidAPI-Host": "sky-scanner3.p.rapidapi.com"
    };

    fetch(detailUrl, { method: "GET", headers })
        .then(response => response.json())
        .then(data => {
            // Pricing Options 배열 가져오기
            const pricingOptions = data.data.itinerary.pricingOptions;

            if (!pricingOptions || pricingOptions.length === 0) {
                alert("사이트 정보를 찾을 수 없습니다.");
                return;
            }

            // 가격 섹션 컨테이너
            const priceContainer = $('.price-container');

            // 모든 옵션 순회
            pricingOptions.forEach((option, index) => {
                // 각 옵션의 agents 배열 처리
                option.agents.forEach(agent => {
                    const priceRow = `
                    <div class="price-row">
                        <span class="site-name">${agent.name}</span>
                        <span class="site-price">₩${agent.price.toLocaleString()}</span>
                        <button class="select-button" data-url="${agent.url}">선택</button>
                    </div>
                `;
                    // 컨테이너에 추가
                    priceContainer.append(priceRow);
                });
            });

            // 선택 버튼 클릭 이벤트
            $('.select-button').on('click', function () {
                alert("항공권 예약 페이지로 이동합니다!");
                const url = $(this).data('url');
                window.open(url, '_blank');
            });
        })
        .catch(error => {
            console.error('Detail API 호출 오류:', error);
            alert('데이터를 불러오는 중 오류가 발생했습니다.');
        });
});
