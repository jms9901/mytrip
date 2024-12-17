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
            const boardId = event.target.getAttribute('data-boardId');

            console.log("Fetching attachments for boardId:", boardId);

            // 첨부파일 가져오기
            fetch(`/admin/boardAttachments/${boardId}`)
                .then((response) => response.json())
                .then((attachments) => {
                    const imageContainer = document.getElementById('imageContainer');
                    imageContainer.innerHTML = ''; // 기존 이미지 제거

                    if (attachments && attachments.length > 0) {
                        attachments.forEach((attachment) => {
                            const img = document.createElement('img');
                            img.src = `/img/${attachment.fileName.trim()}`; // 서버의 업로드 디렉토리 경로
                            img.alt = '첨부 이미지';
                            img.style.width = '100px';
                            img.style.margin = '5px';
                            imageContainer.appendChild(img);
                        });
                    } else {
                        imageContainer.innerHTML = '<p>첨부파일이 없습니다.</p>';
                    }
                })
                .catch((error) => {
                    console.error("Error fetching attachments:", error);
                    alert("첨부파일 정보를 불러오지 못했습니다.");
                });

            // 기타 모달 데이터 설정
            const regDate = event.target.getAttribute('data-date')?.split('T')[0] || '[데이터 없음]';
            const boardSubject = event.target.getAttribute('data-subject') || '[데이터 없음]';
            const boardContent = event.target.getAttribute('data-content') || '[데이터 없음]';
            const boardViewCount = event.target.getAttribute('data-viewCount') || '[데이터 없음]';
            const boardCityName = event.target.getAttribute('data-cityName') || '[데이터 없음]';
            const boardUserName = event.target.getAttribute('data-userName') || '[데이터 없음]';

            document.getElementById('modalRegDate').textContent = regDate;
            document.getElementById('modalBoardSubject').textContent = boardSubject;
            document.getElementById('modalBoardContent').textContent = boardContent;
            document.getElementById('modalBoardViewCount').textContent = boardViewCount;
            document.getElementById('modalBoardCityName').textContent = boardCityName;
            document.getElementById('modalBoardUserName').textContent = boardUserName;
        }
    });


    // 사용자 삭제
    window.deletePost = function () {
        // 삭제를 위한 boardId 가져오기
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
                    boardId: boardId // 서버로 숫자형 boardId 전달
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
                    console.error('Error deleting board:', error);
                    alert('게시물 삭제 중 오류가 발생했습니다.');
                });
        }
    };

        const sliderContainer = document.getElementById('sliderContainer');
        const imageContainer = document.getElementById('imageContainer');
        const prevBtn = document.getElementById('prevBtn');
        const nextBtn = document.getElementById('nextBtn');
        let currentIndex = 0;

        // 슬라이드 표시 함수
        function showSlide(index) {
            const images = imageContainer.querySelectorAll('img');

            if (index < 0) {
                currentIndex = images.length - 1;
            } else if (index >= images.length) {
                currentIndex = 0;
            } else {
                currentIndex = index;
            }

            const offset = -currentIndex * sliderContainer.clientWidth;
            imageContainer.style.transform = `translateX(${offset}px)`;
        }

        // 이전 버튼 클릭 이벤트
        prevBtn.addEventListener('click', function () {
            showSlide(currentIndex - 1);
        });

        // 다음 버튼 클릭 이벤트
        nextBtn.addEventListener('click', function () {
            showSlide(currentIndex + 1);
        });

        // 첫 번째 슬라이드 표시
        showSlide(currentIndex);

    function reloadTable() {
        location.reload();
    }
});
