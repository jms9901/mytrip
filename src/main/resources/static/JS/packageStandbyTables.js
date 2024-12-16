document.addEventListener('DOMContentLoaded', function () {
    console.log("JavaScript Loaded");

    let previouslyFocusedElement = null; // 이전 포커스된 요소 저장

    // 모달 요소 및 버튼 가져오기
    const modal = document.getElementById('exampleModal');
    const approveButton = document.getElementById('approveButton');
    const rejectButton = document.getElementById('rejectButton');
    const deleteButton = document.getElementById('deleteButton');
    const tableBody = document.querySelector('#datatablesSimple tbody');
    const sliderContainer = document.getElementById('sliderContainer');
    const imageContainer = document.getElementById('imageContainer');
    const prevBtn = document.getElementById('prevBtn');
    const nextBtn = document.getElementById('nextBtn');
    let currentIndex = 0;

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

            // 슬라이더 초기화
            fetch(`/admin/packageAttachments/${packageId}`)
                .then(response => response.json())
                .then(attachments => {
                    imageContainer.innerHTML = ''; // 기존 이미지 제거
                    if (attachments && attachments.length > 0) {
                        attachments.forEach(attachment => {
                            const img = document.createElement('img');
                            img.src = `/img/${attachment.fileName.trim()}`; // 서버의 업로드 디렉토리 경로
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
                .catch(error => {
                    console.error('Error fetching attachments:', error);
                    alert('첨부파일 정보를 불러오는 중 오류가 발생했습니다.');
                });

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

    // 패키지 상태 업데이트 요청
    function updatePackageStatus(packageId, newStatus) {
        fetch('/admin/updatePackageStatus', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: new URLSearchParams({ packageId, newStatus }),
        })
            .then(response => response.text())
            .then(data => {
                alert(data);
                location.reload();
            })
            .catch(error => {
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
