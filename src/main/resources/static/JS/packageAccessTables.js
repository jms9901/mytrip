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
            const packageStatus = event.target.getAttribute('data-packagestatus')

            console.log("Package ID:", packageId);
            console.log("User Name:", userName);
            console.log("Package Title:", packageTitle);

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

            // 패키지 ID를 숨겨진 데이터로 저장 (삭제 시 사용)
            document.getElementById('modalPackageId').setAttribute('data-packageid', packageId);

            // 버튼 초기화
            approveButton.style.display = 'none';
            rejectButton.style.display = 'none';
            deleteButton.style.display = 'none';

            // 상태에 따라 버튼 표시
            if (userStatus === '대기') {
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
                deleteUser(packageId);
            };
        }
    });

    // 패키지  상태 업데이트 요청
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

    // 패키지 삭제
    window.deletePackage = function () {
        // 삭제를 위한 packageId를 가져오기
        const packageId = document.getElementById('modalPackageId').getAttribute('data-packageid');

        if (!packageId) {
            console.error("Package ID not found.");
            alert("삭제하려는 패키지 ID를 찾을 수 없습니다.");
            return;
        }

        if (confirm('정말로 이 패키지를 삭제하시겠습니까?')) {
            fetch('/admin/deletePackage', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: new URLSearchParams({
                    packageId: packageId, // 서버로 전달할 packageId
                }),
            })
                .then((response) => {
                    if (response.ok) {
                        return response.text();
                    }
                    throw new Error('Failed to delete package.');
                })
                .then((data) => {
                    alert(data);
                    const modalInstance = bootstrap.Modal.getInstance(document.getElementById('exampleModal'));
                    if (modalInstance) {
                        modalInstance.hide(); // 모달 닫기
                    }
                    reloadTable(); // 테이블 리로드
                })
                .catch((error) => {
                    console.error('Error deleting package:', error);
                    alert('패키지 삭제 중 오류가 발생했습니다.');
                });
        }
    };

    // 테이블 리로드
    function reloadTable() {
        location.reload();
    }
});
