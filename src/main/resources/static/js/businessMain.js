document.addEventListener("DOMContentLoaded", () => {
    const packageListContainer = document.querySelector('.package-list');

    // 현재 페이지의 경로에서 userId 추출하기
    const path = window.location.pathname; // 예: "/mypage/business/123"
    const segments = path.split('/'); // 예: ["", "mypage", "business", "123"]

    // userId가 마지막 segment에 있다고 가정
    const userId = segments[segments.length - 1]; // 마지막 세그먼트 추출

    console.log(userId); // 예: userId 확인

    if (!userId || isNaN(userId)) {
        console.error('유효하지 않은 사용자 ID입니다.');
        packageListContainer.innerHTML = '<p>사용자 정보를 불러올 수 없습니다.</p>';
        return;
    }

    fetch(`/mypage/business/js/${userId}`, {
        // 명시적으로 JSON 요청 설정
        headers: {
            'Accept' : 'application/json',
            'Content-Type' : 'application/json'
        }
    })
        .then(response => {
            //응답이 JSON인지 확인
            const contentType = response.headers.get('content-type');
            if(!contentType || !contentType.includes('application/json')) {
                throw new TypeError("서버 응답이 JSON 형식이 아닙니다.")
            }
            if(!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json();
        })

        .then(data => {
            // 데이터 형식 확인
            if(!Array.isArray(data)) {
                console.error('Unexpected data format: ', data);
                packageListContainer.innerHTML = "<p>패키지 데이터를 올바르게 가져오지 못했습니다.</p>"
                return;
            }

            // 패키지 데이터가 비어있는 경우 처리
            if (!data || data.length === 0) {
                packageListContainer.innerHTML = '<p>등록된 패키지가 없습니다.</p>';
                return;
            }

            // 데이터 렌더링
            packageListContainer.innerHTML = data.map(packages => `
                <div class="package-item">
                    <div class="package-title">
                        <div class="package-name">${packages.packageTitle}</div>
                    </div>
                    <div class="package-content">${packages.packageContent}</div>
                    <div class="package-status ${getStatusClass(packages.packageStatus)}">${getStatusText(packages.packageStatus)}</div>
                    <div class="liked">
                        <img src="/img/heart-icon-fill.png" alt="heart-icon-fill" id="liked-heart-icon" type="button">
                        <span class="likedCnt">${packages.likedCount}</span>
                    </div>
                </div>
            `).join('');
        })
        .catch(error => {
            console.error('Error fetching package data:', error);
            packageListContainer.innerHTML = '<p>패키지를 불러오는 중 오류가 발생했습니다.</p>';
        });
});

// 상태에 따른 한국어 텍스트 반환하는 함수 추가 -> 패키지 승인 상태
function getStatusText(status) {
    switch (status) {
        case '승인' :
            return '승인';
        case '대기' :
            return '대기';
        case '미승인' :
            return '미승인';
        default:
            return '';
    }
}

// 상태에 따른 CSS 클래스를 반환하는 함수 추가 -> 패키지 승인 상태
function getStatusClass(status) {
    switch (status) {
        case '승인' :
            return 'status-approved';
        case '대기' :
            return 'status-pending';
        case '미승인' :
            return 'status-rejected';
        default:
            return '';
    }
}

// 결제 버튼 모달 JS
document.addEventListener('DOMContentLoaded', function() {
    var paymentButton = document.getElementById('package-payment-button');
    if (paymentButton) {
        paymentButton.addEventListener('click', function() {
            window.location.href = '/businessPayment.html';
        });
    }
});

// 상태에 따른 한국어 텍스트 반환하는 함수 추가 -> 결제 상태
function getPaymentStatusText(status) {
    switch (status) {
        case '결제 완료' :
            return '결제 완료';
        case '결제 취소' :
            return '결제 취소';
        default:
            return '';
    }
}

// 상태에 따른 CSS 클래스를 반환하는 함수 추가
function getPaymentStatusClass(status) {
    switch (status) {
        case '결제 완료' :
            return 'payment-complete';
        case '결제 취소' :
            return 'payment-cancel';
        default:
            return '';
    }
}

// 개인정보 수정 모달



// 패키지 상세보기 모달



// 패키지 상세보기 -> 수정 모달