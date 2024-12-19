function increaseCount() {
    const countInput = document.getElementById('userCount'); // 메인 화면의 인원 수 입력 필드
    const maxPeople = parseInt(countInput.getAttribute('max')); // 최대 인원 수

    if (parseInt(countInput.value) < maxPeople) {
        countInput.value = parseInt(countInput.value) + 1; // 인원 증가
        syncModalValues(); // 값 동기화
    } else {
        alert("최대 인원 수를 초과할 수 없습니다."); // 경고 메시지
    }
}

function decreaseCount() {
    const countInput = document.getElementById('userCount'); // 메인 화면의 인원 수 입력 필드

    if (parseInt(countInput.value) > 1) {
        countInput.value = parseInt(countInput.value) - 1; // 인원 감소
        syncModalValues(); // 값 동기화
    } else {
        alert("최소 인원은 1명이어야 합니다."); // 경고 메시지
    }
}

function syncModalValues() {
    const userCount = parseInt(document.getElementById('userCount').value); // 메인 화면의 인원 수
    const pricePerPerson = parseInt(document.getElementById('totalPrice').getAttribute('data-price-per-person')); // 1인당 가격

    if (!isNaN(userCount) && !isNaN(pricePerPerson)) {
        const totalPrice = userCount * pricePerPerson; // 총 금액 계산

        // 메인 화면 금액 업데이트
        document.getElementById('totalPrice').innerText = totalPrice.toLocaleString() + '원';

        // 모달 금액 업데이트
        document.getElementById('modalPeopleCount').value = userCount; // 모달 창의 인원 수
        document.getElementById('modalTotalPrice').value = totalPrice.toLocaleString() + '원'; // 모달 창의 총 금액

        // 숨겨진 필드에 값 반영
        document.getElementById('hiddenPeopleCount').value = userCount; // 서버로 전달할 인원 수
        document.getElementById('sanitizedTotalPrice').value = totalPrice; // 서버로 전달할 총 금액
    } else {
        console.error("총 금액 계산 오류: 유효하지 않은 데이터입니다.");
    }
}

// 모달 열릴 때 메인 화면 값 동기화
document.getElementById('openModalBtn').addEventListener('click', function () {
    const reservationModal = new bootstrap.Modal(document.getElementById('reservationModal'));
    syncModalValues(); // 모달 열기 전에 값 동기화
    reservationModal.show();
});

// 결제 버튼 클릭 시 처리
document.getElementById('submitBtn').addEventListener('click', function () {
    const userCount = document.getElementById('hiddenPeopleCount').value;
    const totalPrice = document.getElementById('sanitizedTotalPrice').value;

    // 디버깅용 데이터 확인
    console.log('전송 데이터:', { userCount, totalPrice });

    if (!userCount || parseInt(userCount) <= 0 || !totalPrice || parseInt(totalPrice) <= 0) {
        alert("값이 올바르지 않습니다. 다시 확인해주세요.");
        return;
    }

    // 폼 제출
    document.getElementById('reservationForm').submit();
});

// 현재 URL에서 cityId 추출
const currentUrl = window.location.href;
const cityIdMatch = currentUrl.match(/\/city\/(\d+)/); // '/city/{cityId}' 추출

if (cityIdMatch) {
    const cityId = cityIdMatch[1];
    const backUrl = `/board/city/${cityId}`;
    document.getElementById('backToList').href = backUrl; // "목록" 버튼 URL 설정
} else {
    console.error('cityId를 URL에서 찾을 수 없습니다.');
}

document.addEventListener('DOMContentLoaded', () => {
    // URL 쿼리 파라미터 확인
    const urlParams = new URLSearchParams(window.location.search);
    const status = urlParams.get('status');

    if (status === 'success') {
        alert('결제가 성공적으로 완료되었습니다!');
    } else if (status === 'pending') {
        alert('관리자 승인 후 패키지가 등록됩니다.');
    }
});