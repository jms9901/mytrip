document.addEventListener('DOMContentLoaded', function () {
    console.log("JavaScript Loaded");

    let previouslyFocusedElement = null; // 이전 포커스된 요소 저장

    // 모달 요소 가져오기
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
        if (event.target.classList.contains('user-details-button')) {
            console.log("User details button clicked:", event.target);

            // 데이터 가져오기
            const userId = event.target.getAttribute('data-userid');
            const userUsername = event.target.getAttribute('data-userusername');
            const username = event.target.getAttribute('data-username');
            const userEmail = event.target.getAttribute('data-useremail');
            const userStatus = event.target.getAttribute('data-userstatus');
            const userBirthday = event.target.getAttribute('data-userbirthday');
            let userRegDate = event.target.getAttribute('data-userregdate');

            userRegDate = userRegDate ? userRegDate.split('T')[0] : '[데이터 없음]';

            console.log("User ID:", userId);
            console.log("Username:", userUsername);

            // 모달 데이터 업데이트
            document.getElementById('modalName').textContent = username || '[데이터 없음]';
            document.getElementById('modalusername').textContent = userUsername || '[데이터 없음]';
            document.getElementById('modalEmail').textContent = userEmail || '[데이터 없음]';
            document.getElementById('modalBirthday').textContent = userBirthday || '[데이터 없음]';
            document.getElementById('modalRegDate').textContent = userRegDate || '[데이터 없음]';

            // 모달에 userId를 숨겨진 데이터로 저장 (삭제 시 사용)
            document.getElementById('modalusername').setAttribute('data-userid', userId);

            // 버튼 초기화
            approveButton.style.display = 'none';
            rejectButton.style.display = 'none';
            deleteButton.style.display = 'none';

            // 상태에 따라 버튼 표시
            if (userStatus === '대기') {
                approveButton.style.display = 'inline-block';
                rejectButton.style.display = 'inline-block';
            } else if (userStatus === '승인' || userStatus === '거절') {
                deleteButton.style.display = 'inline-block';
            }

            // 버튼 클릭 이벤트 추가
            approveButton.onclick = function () {
                updateUserStatus(userId, '승인');
            };
            rejectButton.onclick = function () {
                updateUserStatus(userId, '거절');
            };
            deleteButton.onclick = function () {
                deleteUser(userId);
            };
        }
    });

    // 사용자 상태 업데이트 요청
    function updateUserStatus(userId, newStatus) {
        fetch('/admin/updateBusinessStatus', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: new URLSearchParams({ userId: userId, newStatus: newStatus }),
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

    // 사용자 삭제 요청
    function deleteUser(userId) {
        if (confirm('정말로 이 사용자를 삭제하시겠습니까?')) {
            fetch('/admin/deleteuser', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: new URLSearchParams({ userId: userId }),
            })
                .then((response) => response.text())
                .then((data) => {
                    alert(data);
                    location.reload();
                })
                .catch((error) => {
                    console.error('Error deleting user:', error);
                    alert('사용자 삭제 중 오류가 발생했습니다.');
                });
        }
    }

    // 테이블 정렬 함수
    function sortTableByStatus() {
        const rows = Array.from(tableBody.querySelectorAll('tr'));

        // "대기" 상태를 최상단으로 정렬
        rows.sort((rowA, rowB) => {
            const statusA = rowA.querySelector('.user-details-button').getAttribute('data-userstatus') || '';
            const statusB = rowB.querySelector('.user-details-button').getAttribute('data-userstatus') || '';

            if (statusA === '대기' && statusB !== '대기') return -1;
            if (statusA !== '대기' && statusB === '대기') return 1;
            return 0;
        });

        // 정렬된 행을 다시 추가
        rows.forEach(row => tableBody.appendChild(row));
    }

    // 초기 정렬 실행
    sortTableByStatus();

    // 이후 필요 시 테이블 정렬을 다시 호출
    document.body.addEventListener('click', function (event) {
        if (event.target.classList.contains('user-details-button')) {
            sortTableByStatus();
        }
    });
});
