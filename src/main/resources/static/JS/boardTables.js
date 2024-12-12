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
        if (event.target.classList.contains('board-details-button')) {
            console.log("board details button clicked:", event.target);

            // 데이터 가져오기
            var regDate = event.target.getAttribute('data-date'); // 숫자형 ID
            const boardSubject = event.target.getAttribute('data-subject'); // 로그인 ID
            const boardContent = event.target.getAttribute('data-content');
            const boardViewCount = event.target.getAttribute('data-viewCount');
            const boardCityId = event.target.getAttribute('data-cityId');
            let boardId = event.target.getAttribute('data-boardId');

            regDate = regDate ? regDate.split('T')[0] : '[데이터 없음]';

            console.log("Board ID:", boardId); // 서버로 전달할 userId
            console.log("Board Subject:", boardSubject); // 모달 창에 표시할 username

            // 모달 데이터 업데이트
            document.getElementById('modalRegDate').textContent = regDate || '[데이터 없음]';
            document.getElementById('modalBoardSubject').textContent = boardSubject || '[데이터 없음]'; // 모달에 로그인 ID 표시
            document.getElementById('modalBoardContent').textContent = boardContent || '[데이터 없음]';
            document.getElementById('modalBoardViewCount').textContent = boardViewCount || '[데이터 없음]';
            document.getElementById('modalBoardCityId').textContent = boardCityId || '[데이터 없음]';
            document.getElementById('modalBoardId').textContent = boardId || '[데이터 없음]';

            // 모달에 userId를 숨겨진 데이터로 저장 (삭제 시 사용)
            document.getElementById('modalBoardId').setAttribute('data-boardId', boardId);
        }
    });



    // 사용자 삭제
    window.deletePost = function () {
        // 삭제를 위한 userId를 가져오기 (숫자형 ID)
        const boardId = document.getElementById('modalBoardId').getAttribute('data-boardId');

        if (!boardId) {
            console.error("Board ID not found.");
            alert("삭제하려는 게시물 ID를 찾을 수 없습니다.");
            return;
        }

        if (confirm('정말로 이 게시물을 삭제하시겠습니까?')) {
            fetch('/admin/deletePost', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: new URLSearchParams({
                    boardId: boardId // 서버로 숫자형 userId 전달
                })
            })
                .then(response => {
                    if (response.ok) {
                        console.log('Board deleted successfully.');
                        const modalInstance = bootstrap.Modal.getInstance(document.getElementById('exampleModal'));
                        if (modalInstance) {
                            modalInstance.hide(); // 모달 닫기
                        }
                        reloadTable(); // 테이블 리로드
                    } else {
                        console.error('Failed to delete board. Server response status:', response.status);
                        alert('게시물 삭제에 실패했습니다. 다시 시도해 주세요.');
                    }
                })
                .catch(error => {
                    console.error('Error deleting user:', error);
                    alert('게시물 삭제 중 오류가 발생했습니다.');
                });
        }
    };


    function reloadTable() {
        location.reload();
    }
});
