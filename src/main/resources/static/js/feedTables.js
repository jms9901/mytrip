document.addEventListener('DOMContentLoaded', function () {
    console.log("FeedTables JavaScript Loaded");

    let previouslyFocusedElement = null; // 이전 포커스된 요소 저장

    // 모달 요소 가져오기
    const modal = document.getElementById('exampleModal');

    // 모달 열기 이벤트
    modal.addEventListener('show.bs.modal', () => {
        console.log("Modal is opening...");
        previouslyFocusedElement = document.activeElement; // 현재 포커스된 요소 저장
        modal.removeAttribute('aria-hidden'); // aria-hidden 제거
        modal.removeAttribute('inert'); // inert 제거
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
                            img.src = `/uploads/${attachment.fileName.trim()}`; // 서버의 업로드 디렉토리 경로
                            img.alt = '첨부 이미지';
                            img.style.width = '100%';
                            img.style.flexShrink = '0';
                            imageContainer.appendChild(img);
                        });
                        showSlide(0); // 슬라이더 초기화
                    } else {
                        imageContainer.innerHTML = '<p>첨부파일이 없습니다.</p>';
                    }
                })
                .catch((error) => {
                    console.error("Error fetching attachments:", error);
                    alert("첨부파일 정보를 불러오지 못했습니다.");
                });

            // 모달 데이터 업데이트
            const regDate = event.target.getAttribute('data-date')?.split('T')[0] || '[데이터 없음]';
            const boardSubject = event.target.getAttribute('data-subject') || '[데이터 없음]';
            const boardContent = event.target.getAttribute('data-content') || '[데이터 없음]';
            const boardViewCount = event.target.getAttribute('data-viewCount') || '[데이터 없음]';
            const boardCityName = event.target.getAttribute('data-cityName') || '[데이터 없음]';
            const boardUserName = event.target.getAttribute('data-userName') || '[데이터 없음]';

            // HTML 데이터를 DOM으로 변환
            const parser = new DOMParser();
            const doc = parser.parseFromString(boardContent, 'text/html');

            // 텍스트와 이미지 분리
            const textContent = doc.body.textContent.trim(); // 텍스트 추출
            const imageElements = doc.querySelectorAll('img'); // 이미지 요소 추출

            // 이미지 정보를 배열로 저장
            const images = Array.from(imageElements).map(img => ({
                src: img.getAttribute('src'), // 이미지 데이터 (Base64)
                filename: img.getAttribute('data-filename'), // 파일 이름
                style: img.getAttribute('style') // 스타일 정보
            }));

            // 결과 출력
            console.log("텍스트:", textContent);
            console.log("이미지:", images);

            document.getElementById('modalRegDate').textContent = regDate;
            document.getElementById('modalBoardSubject').textContent = boardSubject;
            document.getElementById('modalBoardContent').innerHTML = textContent;
            document.getElementById('modalBoardViewCount').textContent = boardViewCount;
            document.getElementById('modalBoardCityName').textContent = boardCityName;
            document.getElementById('modalBoardUserName').textContent = boardUserName;

            images.forEach(image => {
                const imgElement = document.createElement("img");
                imgElement.src = image.src;
                // imgElement.alt = image.filename; // 이미지 파일 이름
                // imgElement.style = image.style;

                // 이미지 컨테이너에 추가
                imageContainer.appendChild(imgElement.src);
            })
        }
    });

    // 슬라이더 초기화
    const sliderContainer = document.getElementById('sliderContainer');
    const imageContainer = document.getElementById('imageContainer');
    const prevBtn = document.getElementById('prevBtn');
    const nextBtn = document.getElementById('nextBtn');
    let currentIndex = 0;

    function showSlide(index) {
        const images = imageContainer.querySelectorAll('img');
        if (images.length === 0) return;

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

    prevBtn.addEventListener('click', function () {
        showSlide(currentIndex - 1);
    });

    nextBtn.addEventListener('click', function () {
        showSlide(currentIndex + 1);
    });

    // 첫 번째 슬라이드 표시
    showSlide(currentIndex);

    function reloadTable() {
        location.reload();
    }
});
