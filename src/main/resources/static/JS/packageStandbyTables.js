document.addEventListener('DOMContentLoaded', function () {
    console.log("JavaScript Loaded");

    let previouslyFocusedElement = null; // 이전 포커스된 요소 저장

    // 모달 요소 및 버튼 가져오기
    const modal = document.getElementById('exampleModal');
    const approveButton = document.getElementById('approveButton');
    const rejectButton = document.getElementById('rejectButton');
    const deleteButton = document.getElementById('deleteButton');
    const tableBody = document.querySelector('#datatablesSimple tbody');

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
        if (event.target.classList.contains('package-details-button')) {
            console.log("Package details button clicked:", event.target);

            // 데이터 가져오기
            const packageId = event.target.getAttribute('data-packageid') || '[데이터 없음]';
            const userName = event.target.getAttribute('data-username') || '[데이터 없음]';
            const packageTitle = event.target.getAttribute('data-packagetitle') || '[데이터 없음]';
            const packageContent = event.target.getAttribute('data-packagecontent') || '[데이터 없음]';
            const packageCost = event.target.getAttribute('data-packagecost') || '[데이터 없음]';
            const packageMaxPeople = event.target.getAttribute('data-packagemaxpeople') || '[데이터 없음]';
            const packageStartDay = event.target.getAttribute('data-packagestartday') || '[데이터 없음]';
            const packageEndDay = event.target.getAttribute('data-packageendday') || '[데이터 없음]';
            const packageRegDate = event.target.getAttribute('data-packageregdate') || '[데이터 없음]';
            const packageStatus = event.target.getAttribute('data-packagestatus') || '';

            // 모달 데이터 업데이트
            document.getElementById('modalUsername').textContent = userName;
            document.getElementById('modalPackageTitle').textContent = packageTitle;
            document.getElementById('modalPackageContent').textContent = packageContent;
            document.getElementById('modalPackageCost').textContent = packageCost;
            document.getElementById('modalPackageMaxPeople').textContent = packageMaxPeople;
            document.getElementById('modalPackageStartDay').textContent = packageStartDay;
            document.getElementById('modalPackageEndDay').textContent = packageEndDay;
            document.getElementById('modalPackageRegDate').textContent = packageRegDate;
            document.getElementById('modalPackageId').textContent = packageId;

            // 버튼 초기화
            approveButton.style.display = 'none';
            rejectButton.style.display = 'none';
            deleteButton.style.display = 'none';

            // 상태에 따라 버튼 표시
            if (packageStatus === '대기') {
                approveButton.style.display = 'inline-block';
                rejectButton.style.display = 'inline-block';
            } else if (packageStatus === '승인' || packageStatus === '미승인') {
                deleteButton.style.display = 'inline-block';
            }

            // 버튼 클릭 이벤트 추가
            approveButton.onclick = function () {
                updatePackageStatus(packageId, '승인');
            };
            rejectButton.onclick = function () {
                updatePackageStatus(packageId, '미승인');
            };
            deleteButton.onclick = function () {
                deletePackage(packageId);
            };
        }
    });

    // 패키지 상태 업데이트 요청
    function updatePackageStatus(packageId, newStatus) {
        fetch('/admin/updatePackageStatus', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: new URLSearchParams({ packageId: packageId, newStatus: newStatus }),
        })
            .then((response) => response.text())
            .then((data) => {
                alert(data);
                location.reload();
            })
            .catch((error) => {
                console.error('Error updating status:', error);
                alert('상태 변경 중 오류가 발생했습니다.');
            });
    }

    // 패키지 삭제 요청
    function deletePackage(packageId) {
        if (confirm('정말로 이 패키지를 삭제하시겠습니까?')) {
            fetch('/admin/deletePackage', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: new URLSearchParams({ packageId: packageId }),
            })
                .then((response) => response.text())
                .then((data) => {
                    alert(data);
                    location.reload();
                })
                .catch((error) => {
                    console.error('Error deleting package:', error);
                    alert('패키지 삭제 중 오류가 발생했습니다.');
                });
        }
    }

    // 테이블 정렬 함수
    function sortTableByStatus() {
        const rows = Array.from(tableBody.querySelectorAll('tr'));

        // "대기" 상태를 최상단으로 정렬
        rows.sort((rowA, rowB) => {
            const statusA = rowA.querySelector('.package-details-button').getAttribute('data-packagestatus') || '';
            const statusB = rowB.querySelector('.package-details-button').getAttribute('data-packagestatus') || '';

            if (statusA === '대기' && statusB !== '대기') return -1;
            if (statusA !== '대기' && statusB === '대기') return 1;
            return 0;
        });

        // 정렬된 행을 다시 추가
        rows.forEach(row => tableBody.appendChild(row));
    }

    // 초기 정렬 실행
    sortTableByStatus();
});
