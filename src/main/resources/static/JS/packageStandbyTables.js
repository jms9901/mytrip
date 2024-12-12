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
            console.log("User details button clicked:", event.target);

            // 데이터 가져오기
            const packageId = event.target.getAttribute('data-packageid'); // 숫자형 ID
            const userName = event.target.getAttribute('data-username'); // 로그인 ID
            const packageTitle = event.target.getAttribute('data-packagetitle');
            const packageContent = event.target.getAttribute('data-packagecontent');
            const packageCost = event.target.getAttribute('data-packagecost');
            const packageMaxPeople = event.target.getAttribute('data-packagemaxpeople');
            const packageStartDay = event.target.getAttribute('data-packagestartday');
            const packageEndDay = event.target.getAttribute('data-packageendday');
            let packageRegDate = event.target.getAttribute('data-packageregdate');

            packageRegDate = packageRegDate ? packageRegDate.split('T')[0] : '[데이터 없음]';

            console.log("Package ID:", packageId); // 서버로 전달할 userId
            console.log("Package Title:", packageTitle); // 모달 창에 표시할 username

            // 모달 데이터 업데이트
            document.getElementById('modalUsername').textContent = userName || '[데이터 없음]';
            document.getElementById('modalPackageTitle').textContent = packageTitle || '[데이터 없음]'; // 모달에 로그인 ID 표시
            document.getElementById('modalPackageContent').textContent = packageContent || '[데이터 없음]';
            document.getElementById('modalPackageCost').textContent = packageCost || '[데이터 없음]';
            document.getElementById('modalPackageMaxPeople').textContent = packageMaxPeople || '[데이터 없음]';
            document.getElementById('modalPackageStartDay').textContent = packageStartDay || '[데이터 없음]';
            document.getElementById('modalPackageEndDay').textContent = packageEndDay || '[데이터 없음]';
            document.getElementById('modalPackageRegDate').textContent = packageRegDate || '[데이터 없음]';
            document.getElementById('modalPackageId').textContent = packageId || '[데이터 없음]';

            // 모달에 userId를 숨겨진 데이터로 저장 (삭제 시 사용)
            document.getElementById('modalPackageId').setAttribute('data-packageid', packageId);
        }
    });



    // 사용자 삭제
    window.deletePackage = function () {
        // 삭제를 위한 userId를 가져오기 (숫자형 ID)
        const userId = document.getElementById('modalPackageId').getAttribute('data-packageId');

        if (!userId) {
            console.error("Package ID not found.");
            alert("삭제하려는 패키지 ID를 찾을 수 없습니다.");
            return;
        }

        if (confirm('정말로 이 패키지를 삭제하시겠습니까?')) {
            fetch('/admin/deletePackage', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: new URLSearchParams({
                    userId: userId // 서버로 숫자형 userId 전달
                })
            })
                .then(response => {
                    if (response.ok) {
                        console.log('Package deleted successfully.');
                        const modalInstance = bootstrap.Modal.getInstance(document.getElementById('exampleModal'));
                        if (modalInstance) {
                            modalInstance.hide(); // 모달 닫기
                        }
                        reloadTable(); // 테이블 리로드
                    } else {
                        console.error('Failed to delete package. Server response status:', response.status);
                        alert('패키지 삭제에 실패했습니다. 다시 시도해 주세요.');
                    }
                })
                .catch(error => {
                    console.error('Error deleting package:', error);
                    alert('패키지 삭제 중 오류가 발생했습니다.');
                });
        }
    };


    function reloadTable() {
        location.reload();
    }
});
