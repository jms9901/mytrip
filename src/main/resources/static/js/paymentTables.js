document.addEventListener('DOMContentLoaded', function () {
    console.log("JavaScript Loaded");

    let previouslyFocusedElement = null; // 이전 포커스된 요소 저장

    // 모달 요소 가져오기
    const modal = document.getElementById('exampleModal');

    // 모달 열기 이벤트
    modal.addEventListener('show.bs.modal', () => {
        console.log("Modal is opening...");
        previouslyFocusedElement = document.activeElement; // 현재 포커스된 요소 저장
        modal.removeAttribute('aria-hidden'); // aria-hidden 제거
        modal.removeAttribute('inert'); // inert 제거

        // 모달 내부의 첫 번째 포커스 가능한 요소로 이동
        const focusableElement = modal.querySelector('input, [href], select, textarea, [tabindex]:not([tabindex="-1"])');
        if (focusableElement) {
            focusableElement.focus();
        }
    });

    // 모달 닫기 이벤트
    modal.addEventListener('hide.bs.modal', () => {
        console.log("Modal is closing...");
        modal.setAttribute('aria-hidden', 'true'); // aria-hidden 추가
        modal.setAttribute('inert', ''); // inert 추가

        // 이전 포커스된 요소로 복원
        if (previouslyFocusedElement) {
            previouslyFocusedElement.focus();
        }
    });

    // 테이블 내 버튼 클릭 이벤트 처리
    document.body.addEventListener('click', function (event) {
        if (event.target.classList.contains('payment-details-button')) {
            console.log("Payment details button clicked:", event.target);

            // 데이터 가져오기
            const paymentUsername = event.target.getAttribute('data-paymentusername'); // 로그인 ID
            const packageTitle = event.target.getAttribute('data-packagetitle');
            var paymentRegDate = event.target.getAttribute('data-paymentregdate');
            const paymentCost = event.target.getAttribute('data-packagecost');
            let paymentUserCount = event.target.getAttribute('data-paymentusercount');
            let paymentTotalCost = event.target.getAttribute('data-paymenttotalcost');
            const paymentId = event.target.getAttribute('data-paymentid'); // 숫자형 ID

            paymentRegDate = paymentRegDate ? paymentRegDate.split('T')[0] : '[데이터 없음]';

            console.log("Payment ID:", paymentId); // 서버로 전달할 userId
            console.log("Payment Username:", paymentUsername); // 모달 창에 표시할 username

            // 모달 데이터 업데이트
            document.getElementById('modalUsername').textContent = paymentUsername || '[데이터 없음]';
            document.getElementById('modalPackageTitle').textContent = packageTitle || '[데이터 없음]'; // 모달에 로그인 ID 표시
            document.getElementById('modalPaymentRegDate').textContent = paymentRegDate || '[데이터 없음]';
            document.getElementById('modalPackageCost').textContent = paymentCost + "원"|| '[데이터 없음]';
            document.getElementById('modalPaymentUserCount').textContent = paymentUserCount  || '[데이터 없음]';
            document.getElementById('modalPaymentTotalCost').textContent = paymentTotalCost + "원"|| '[데이터 없음]';

            // 모달에 userId를 숨겨진 데이터로 저장 (삭제 시 사용)
            document.getElementById('modalusername').setAttribute('data-userid', userId);
        }
    });
});
