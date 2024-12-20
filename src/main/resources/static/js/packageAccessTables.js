document.addEventListener('DOMContentLoaded', function () {
    console.log("PackageAccessTables JavaScript Loaded");

    const modal = document.getElementById('exampleModal');
    const sliderContainer = document.getElementById('sliderContainer');
    const imageContainer = document.getElementById('imageContainer');
    const prevBtn = document.getElementById('prevBtn');
    const nextBtn = document.getElementById('nextBtn');
    const deleteButton = document.getElementById('deleteButton'); // 삭제 버튼
    let currentIndex = 0;

    let modalPackageId = null; // 패키지 ID 저장
    let previouslyFocusedElement = null; // 이전 포커스된 요소 저장

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
        imageContainer.innerHTML = ''; // 슬라이더 이미지 초기화

        if (previouslyFocusedElement) {
            previouslyFocusedElement.focus();
        }

        modalPackageId = null; // 모달이 닫힐 때 ID 초기화
    });

    // 테이블 내 버튼 클릭 이벤트 처리
    document.body.addEventListener('click', function (event) {
        if (event.target.classList.contains('package-details-button')) {
            console.log("Package details button clicked:", event.target);

            const modalPackageId = event.target.getAttribute('data-packageid');

            console.log("Package ID:", modalPackageId);


            // 데이터 가져오기
            const userName = event.target.getAttribute('data-username') || '[데이터 없음]';
            const packageTitle = event.target.getAttribute('data-packagetitle') || '[데이터 없음]';
            const packageContent = event.target.getAttribute('data-packagecontent') || '[데이터 없음]';
            const packageCost = event.target.getAttribute('data-packagecost') || '[데이터 없음]';
            const packageMaxPeople = event.target.getAttribute('data-packagemaxpeople') || '[데이터 없음]';
            const packageStartDay = event.target.getAttribute('data-packagestartday') || '[데이터 없음]';
            const packageEndDay = event.target.getAttribute('data-packageendday') || '[데이터 없음]';
            const packageRegDate = event.target.getAttribute('data-packageregdate') || '[데이터 없음]';

            // HTML 데이터를 DOM으로 변환
            const parser = new DOMParser();
            const doc = parser.parseFromString(packageContent, 'text/html');

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


            // 모달 데이터 업데이트
            document.getElementById('modalUsername').textContent = userName;
            document.getElementById('modalPackageTitle').textContent = packageTitle;
            document.getElementById('modalPackageContent').innerHTML = textContent;
            document.getElementById('modalPackageCost').textContent = packageCost;
            document.getElementById('modalPackageMaxPeople').textContent = packageMaxPeople;
            document.getElementById('modalPackageStartDay').textContent = packageStartDay;
            document.getElementById('modalPackageEndDay').textContent = packageEndDay;
            document.getElementById('modalPackageRegDate').textContent = packageRegDate;


            images.forEach(image => {
                const imgElement = document.createElement("img");
                imgElement.src = image.src;
                imgElement.alt = image.filename; // 이미지 파일 이름
                imgElement.style.height = '200px';
                imgElement.style.width = 'auto';
                imgElement.style.objectFit = 'contain';

                // 이미지 컨테이너에 추가
                imageContainer.appendChild(imgElement);
            })

            deleteButton.onclick = function () {
                deletePackage(modalPackageId);
            };
        }
    });

    // 패키지 삭제 요청
    function deletePackage(packageId) {
        if (confirm('정말로 이 패키지를 삭제하시겠습니까?')) {
            fetch('/admin/deletePackage', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: new URLSearchParams({ packageId }),
            })
                .then(response => response.text())
                .then(data => {
                    alert(data);
                    location.reload();
                })
                .catch(error => {
                    console.error('Error deleting package:', error);
                    alert('패키지 삭제 중 오류가 발생했습니다.');
                });
        }
    }



    // 슬라이더 표시 함수
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
});