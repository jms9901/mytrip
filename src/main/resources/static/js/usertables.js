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
        if (event.target.classList.contains('user-details-button')) {
            console.log("User details button clicked:", event.target);

            // 데이터 가져오기
            const userId = event.target.getAttribute('data-userid'); // 숫자형 ID
            const userUsername = event.target.getAttribute('data-userusername'); // 로그인 ID
            const username = event.target.getAttribute('data-username');
            const userEmail = event.target.getAttribute('data-useremail');
            const userBirthday = event.target.getAttribute('data-userbirthday');
            let userRegDate = event.target.getAttribute('data-userregdate');

            userRegDate = userRegDate ? userRegDate.split('T')[0] : '[데이터 없음]';

            console.log("User ID:", userId); // 서버로 전달할 userId
            console.log("Username:", userUsername); // 모달 창에 표시할 username

            // 모달 데이터 업데이트
            document.getElementById('modalName').textContent = username || '[데이터 없음]';
            document.getElementById('modalusername').textContent = userUsername || '[데이터 없음]'; // 모달에 로그인 ID 표시
            document.getElementById('modalEmail').textContent = userEmail || '[데이터 없음]';
            document.getElementById('modalBirthday').textContent = userBirthday || '[데이터 없음]';
            document.getElementById('modalRegDate').textContent = userRegDate || '[데이터 없음]';

            // 모달에 userId를 숨겨진 데이터로 저장 (삭제 시 사용)
            document.getElementById('modalusername').setAttribute('data-userid', userId);
        }
    });



    // 사용자 삭제
    window.deleteUser = function () {
        // 삭제를 위한 userId를 가져오기 (숫자형 ID)
        const userId = document.getElementById('modalusername').getAttribute('data-userid');

        if (!userId) {
            console.error("User ID not found.");
            alert("삭제하려는 사용자 ID를 찾을 수 없습니다.");
            return;
        }

        if (confirm('정말로 이 사용자를 삭제하시겠습니까?')) {
            fetch('/admin/deleteuser', {
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
                        console.log('User deleted successfully.');
                        const modalInstance = bootstrap.Modal.getInstance(document.getElementById('exampleModal'));
                        if (modalInstance) {
                            modalInstance.hide(); // 모달 닫기
                        }
                        reloadTable(); // 테이블 리로드
                    } else {
                        console.error('Failed to delete user. Server response status:', response.status);
                        alert('사용자 삭제에 실패했습니다. 다시 시도해 주세요.');
                    }
                })
                .catch(error => {
                    console.error('Error deleting user:', error);
                    alert('사용자 삭제 중 오류가 발생했습니다.');
                });
        }
    };


    function reloadTable() {
        location.reload();
    }
});
