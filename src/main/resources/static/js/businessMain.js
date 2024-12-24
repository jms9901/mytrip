// 현재 페이지의 경로에서 userId 추출하기
const path = window.location.pathname; // 예: "/mypage/business/123"
const segments = path.split('/'); // 예: ["", "mypage", "business", "123"]

// userId가 마지막 segment에 있다고 가정
let userId = null;
if (segments.length > 2) {
    userId = segments[segments.length - 1];
}

document.addEventListener("DOMContentLoaded", function () {
    // 패키지 리스트 동적 생성 부분
    const packageListContainer = document.querySelector('.package-list');
    if(!packageListContainer) {
        console.error(".packgage-list 컨테이너가 존재하지 않습니다.");
        return;
    }

    console.log("추출된 userId: ",userId); // 예: userId 확인

    // 메인 페이지 정보 로드
    fetch(`/mypage/business/js/${userId}`, {
    })
        .then(response => {
            console.log("응답 상태 코드:", response.status);
            if(!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            const contentType = response.headers.get('content-type');
            if(!contentType || !contentType.includes('application/json')) {
                throw new TypeError("기본 정보 데이터 : 서버 응답이 JSON 형식이 아닙니다.")
            }
            return response.json();
        })
        .then(data => {
            console.log("api로부터 받은 데이터: ", data);

            // `packages` 배열 존재 확인
            const packages = data.packages || [];

            // 데이터 형식 확인, 패키지 확인
            if(!Array.isArray(packages) || packages.length === 0) {
                packageListContainer.innerHTML = '<p>등록된 패키지가 없습니다.</p>';
                return;
            }

            // 패키지 리스트 데이터 렌더링
            packageListContainer.innerHTML = packages.map(package => `
                <div class="package-item" id="package-item" data-package-id="${package.packageId}">
                    <div class="package-title">
                        <div class="package-name">${truncateContent(package.packageTitle, 7)}</div>
                    </div>
                    <div class="package-content">${truncateContent(extractTextFromHTML(package.packageContent), 10)}</div>
                    <div class="package-status ${getStatusClass(package.packageStatus)}">${getStatusText(package.packageStatus)}</div>
                    <div class="liked">
                        <img src="/img/heart-icon-fill.png" alt="heart-icon-fill" id="liked-heart-icon" type="button">
                        <span class="likedCnt">${package.likedCount}</span>
                    </div>
                </div>
            `).join('');

        })
        .catch(error => {
            console.error('Error fetching package data:', error);
            packageListContainer.innerHTML = '<p>패키지를 불러오는 중 오류가 발생했습니다.</p>';
        });

    // HTML 태그 제거 함수
    function extractTextFromHTML(html) {
        const tempDiv = document.createElement("div");
        tempDiv.innerHTML = html;
        return tempDiv.textContent || tempDiv.innerText || ""; // HTML 제거 후 순수 텍스트 반환
    }

    // 텍스트 길이 제한 함수
    function truncateContent(text, maxLength) {
        if (text.length > maxLength) {
            return text.slice(0, maxLength) + "...";
        }
        return text;
    }
}); // 페이지 로드 괄호 닫기

// 패키지 상세보기
document.addEventListener("DOMContentLoaded", function () {
    const packageDetailContainer = document.querySelector('.packageDetail');
    let selectedPackageId = null;   // 선택된 패키지 ID 저장

    // ISO 8601 형식의 날짜를 yyyy-MM-dd 형식으로 변환하는 함수
    function formatDate(dateString) {
        if (!dateString) return ''; // 날짜가 없으면 빈 문자열 반환
        const date = new Date(dateString);
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, '0'); // 월을 두 자리로 변환
        const day = String(date.getDate()).padStart(2, '0'); // 일을 두 자리로 변환
        return `${year}-${month}-${day}`;
    }

    // 이벤트 위임으로 동적 요소 처리
    document.body.addEventListener('click', function (event) {
        const packageDetailButton = event.target.closest('#package-item');
        if (packageDetailButton) {
            // 패키지 ID 가져오기
            selectedPackageId = packageDetailButton.dataset.packageId;

            if (!selectedPackageId) {
                console.error('패키지 ID를 가져올 수 없습니다.');
                return;
            }

            // 패키지 상세보기 요청
            console.log("패키지 상세보기 요청: ", selectedPackageId);

            fetch(`/mypage/business/package/${selectedPackageId}`, {
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                }
            })
                .then(response => {
                    console.log("패키지 서버 응답: ", response)
                    const contentType = response.headers.get('content-type');
                    if (!contentType || !contentType.includes('application/json')) {
                        throw new TypeError("패키지 상세보기 데이터 : 서버 응답이 JSON 형식이 아닙니다.");
                    }
                    if (!response.ok) {
                        throw new Error(`HTTP error! status: ${response.status}`);
                    }
                    return response.json();
                })
                .then(packages => {
                    console.log("패키지 데이터 확인: ", packages);

                    // 리스트 초기화
                    packageDetailContainer.innerHTML = '';

                    // 선택된 패키지 정보만 찾기
                    const selectedPackage =  packages.find(pkg => pkg.packageId === Number(selectedPackageId));

                    if (!selectedPackage) {
                        alert('선택된 패키지 정보를 찾을 수 없습니다.');
                        return;
                    }

                    // 금액 포맷팅 함수
                    function formatCurrency(amount) {
                        if (!amount) return '0'; // 금액이 없으면 기본값 반환
                        return Number(amount).toLocaleString('ko-KR');
                    }

                    const cityId = selectedPackage.cityId;
                    console.log("도시 ID: ", cityId);

                    // 패키지 상세보기 데이터 렌더링
                    packageDetailContainer.innerHTML = `
                        <div class="modal-content" data-package-id="${selectedPackage.selectedPackageId}">
                            <span class="close-button">&times;</span>
                            <div class="package-title">${selectedPackage.packageTitle}</div>
                            <div class="package-regdate">${formatDate(selectedPackage.packageRegdate)}</div>

                            <div class="modal-body">
                                <div class="image-section">
                                    <div class="slideshow-container">
                                        <div class="slide-fade">
                                        
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="info-section">
                                <div class="cityName" id="cityName"><strong>도시:</strong>  ${selectedPackage.cityName}</div>
                                <div class="packageStartDay" id="packageStartDay"><strong>시작일:</strong>   ${formatDate(selectedPackage.packageStartDay)}</div>
                                <div class="packageEndDay" id="packageEndDay"><strong>종료일:</strong>   ${formatDate(selectedPackage.packageEndDay)}</div>
                                <div class="packagCost" id="packageCost"><strong>금액:</strong>   ${formatCurrency(selectedPackage.packageCost)}원</div>
                                <div class="packageMaxpeople" id="packageMaxpeople"><strong>최대 인원:</strong>   ${selectedPackage.packageMaxpeople}명</div>
                                <div class="package-details">
                                    <strong>패키지 내용:</strong>
                                    <div class="package-content" id="packageContent">${selectedPackage.packageContent}</div>
                                </div>
                            </div>
                            <button class="packageMoveButton">
                                <img src="/img/mypageFeedList.png" alt="moveButton" id="move-button" style="cursor: pointer">
                            </button>
                            
                            <form class="button-action" style="border: none;">
                                <input type="hidden" name="cityId" value="${selectedPackage.cityId}">
                                <input type="hidden" name="packageId" value="${selectedPackage.packageId}">
                            </form>
                        </div>
                    `;

                    const packageCloseButton = packageDetailContainer.querySelector('.close-button');

                    // 모달 표시
                    packageDetailContainer.style.display = 'block';

                    // 패키지 모달 닫기
                    packageCloseButton.addEventListener('click', function() {
                        packageDetailContainer.innerHTML = '';  // 모달 닫기 시 초기화
                        packageDetailContainer.style.display = 'none';
                    });

                    // 게시판으로 이동
                    // 이벤트 위임 방식 사용 (동적으로 생성된 요소도 포함)
                    document.body.addEventListener('click', function (event) {

                        // 클릭된 요소가 이미지 버튼인지 확인
                        if (event.target.id === 'move-button') {
                            // 페이지 이동
                            window.location.href = `/board/city/${cityId}`;
                        }
                    });

                })
                .catch(error => {
                    console.error('패키지 상세 정보 로딩 중 오류 발생: ', error);
                    alert('패키지 상세 정보를 불러오는 중 오류가 발생했습니다.');
                });
        }
    });
});

// 기존 getStatusText, getStatusClass 함수 유지
function getStatusText(status) {
    switch (status) {
        case '승인':
            return '승인';
        case '대기':
            return '대기';
        case '미승인':
            return '미승인';
        default:
            return '';
    }
}

function getStatusClass(status) {
    switch (status) {

        case '승인':
            return 'status-approved';
        case '대기':
            return 'status-pending';
        case '미승인':
            return 'status-rejected';
        default:
            return '';
    }
}

// 결제 버튼 모달
document.addEventListener('DOMContentLoaded', function() {
    const paymentButton = document.getElementById('package-payment-button');
    const payments = document.querySelector('.paymentList');

    if (paymentButton) {
        paymentButton.addEventListener('click', function () {
            fetch(`/mypage/business/payments/${userId}`, {
                method: 'GET',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                }
            })
                .then(response => {
                    console.log("결제 내역 서버 응답: ", response);
                    if (!response.ok) {
                        throw new Error(`HTTP error! status: ${response.status} / 결제 정보를 불러올 수 없습니다.`);
                    }
                    return response.json();
                })
                .then(paymentData => {
                    console.log("결제 내역 데이터: ", paymentData);
                    const paymentModal = document.getElementById('payment-modal');

                    // 항상 테이블을 초기화하고 기본 구조를 유지
                    payments.innerHTML = `
                        <div class="modal hidden" id="payment-modal">
                            <div class="modal-payment">
                                <div class="modal-header">
                                    <span class="closebutton">&times;</span>
                                    <div class="modal-title">결제 정보</div>

                                    <label for="payment-select" id="payment-status">
                                        <select name="payment-select" id="payment-status" class="pay-status-select">
                                            <option value="" selected>전체</option>
                                            <option value="결제 완료">결제 완료</option>
                                            <option value="결제 취소">결제 취소</option>
                                        </select>
                                    </label>

                                    <table class="payment-table">
                                        <thead>
                                            <tr class="table-header">
                                                <th>사용자명</th>
                                                <th>패키지명</th>
                                                <th>결제 금액</th>
                                                <th>결제 상태</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            ${paymentData.length === 0
                        ? `<tr><td colspan="4" style="text-align:center;">결제 내역이 없습니다.</td></tr>`
                        : paymentData.map(payments => `
                                                    <tr>
                                                        <td>${payments.userName}</td>
                                                        <td>${payments.packageTitle}</td>
                                                        <td>${payments.totalPrice}</td>
                                                        <td class="pay-status ${getPaymentStatusClass(payments.status)}">
                                                            ${getPaymentStatusText(payments.status)}
                                                        </td>
                                                    </tr>
                                                `).join('')}
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    `;

                    payments.style.display = 'block';

                    const paymentModalButton = payments.querySelector('.closebutton');

                    // 모달 표시


                    // 패키지 모달 닫기
                    paymentModalButton.addEventListener('click', function() {
                        payments.innerHTML = '';  // 모달 닫기 시 초기화
                        payments.style.display = 'none';
                    });
                    // 상태 필터링 이벤트 등록
                    const payStatusSelect = document.getElementById('pay-status-select');
                    payStatusSelect.addEventListener('change', function () {
                        const selectedStatus = this.value.trim(); // 선택된 상태 값
                        const paymentRows = document.querySelectorAll('.payment-table tbody tr');

                        paymentRows.forEach(row => {
                            const statusCell = row.querySelector('.pay-status');
                            const rowClass = statusCell.classList.contains(selectedStatus);

                            // 전체 선택 시 모든 행 표시, 특정 상태 선택 시 필터링
                            if (selectedStatus === '' || rowClass) {
                                row.style.display = '';
                            } else {
                                row.style.display = 'none';
                            }
                        });
                    });

                })
                .catch(error => {
                    console.error("결제 정보 로딩 중 오류 발생", error);
                    if (!payments.innerHTML) {
                        payments.innerHTML = '<p>결제 정보를 불러오는 중 오류가 발생했습니다.</p>';
                    }
                });
        });
    }

    // 결제 상태 필터링
    const payStatusSelect = document.querySelector('.pay-status-select');
    if (payStatusSelect) {
        payStatusSelect.addEventListener('change', function () {
            const selectedStatus = this.value;
            const paymentRows = document.querySelectorAll('.payment-table tbody tr');

            paymentRows.forEach(row => {
                const statusCell = row.querySelector('.pay-status');
                if (selectedStatus === '' || statusCell.textContent.trim() === selectedStatus) {
                    row.style.display = '';
                } else {
                    row.style.display = 'none';
                }
            });
        });
    }
});

// 상태에 따른 한국어 텍스트 반환하는 함수 추가 -> 결제 상태
        function getPaymentStatusText(status) {
            switch (status.trim()) {
                case '결제완료' :
                    return '결제완료';
                case '결제취소' :
                    return '결제취소';
                default:
                    return '';
            }
        }

// 상태에 따른 CSS 클래스를 반환하는 함수 추가
        function getPaymentStatusClass(status) {
            switch (status.trim()) {
                case '결제완료' :
                    return 'payment-complete';
                case '결제취소' :
                    return 'payment-cancel';
                default:
                    return '';
            }
        }

// 개인 정보 조회 및 수정
document.addEventListener('DOMContentLoaded', function () {
    const userProfile = document.getElementById('profile-icon-Button');
    const userInfo = document.querySelector('.userInfo');

    console.log("userId 추출: ", userId);

    // 날짜 포맷팅 함수
    function formatDate(dateString) {
        const date = new Date(dateString);
        return date.toLocaleString('ko-KR', {
            year: 'numeric',
            month: '2-digit',
            day: '2-digit',
        });
    }

    // 비밀번호 유효성 검사
    function validatePasswords(currentPassword, newPassword) {
        if (!currentPassword.trim()) {
            alert('기존 비밀번호를 입력해주세요.');
            return false;
        }

        if (!newPassword.trim()) {
            alert('새 비밀번호를 입력해주세요.');
            return false;
        }

        if (newPassword.length < 8) {
            alert('새 비밀번호는 최소 8자 이상이어야 합니다.');
            return false;
        }

        if (currentPassword === newPassword) {
            alert('새 비밀번호는 기존 비밀번호와 다르게 입력해주세요.');
            return false;
        }

        return true;
    }

    // 프로필 수정 모달 열기
    userProfile.addEventListener('click', function () {
        fetch(`/mypage/business/profile/${userId}`, {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status} / 프로필 개인 정보를 불러올 수 없습니다.`);
                }
                return response.json();
            })
            .then(user => {
                userInfo.innerHTML = `
                <div class="modal hidden" id="businessUpdate-modal">
                    <form id="profile-update-form">
                        <div class="header">
                            <span class="close-button">&times;</span>
                            <h2 class="businessName">${user.username}</h2>
                            <span class="joinDate">가입일 :  ${formatDate(user.regDate)}</span>
                        </div>
                        <div class="profile">
                            <img alt="프로필" class="profile-img" id="profileImage" src="/img/defaultProfile.jpg">
                        </div>
                        <div class="businessInfo">
                            <label for="businessId">
                                <span>기업 ID</span>
                                <input type="text" id="businessId" value="${user.username}" readonly>
                            </label>
                        </div>
                        <div class="businessInfo">
                            <label for="businessEmail">
                                <span>기업 이메일</span>
                                <input type="email" id="businessEmail" value="${user.email}" readonly>
                            </label>
                        </div>
                        <div class="businessInfo">
                            <label for="businessName">
                                <span>기업 이름</span>
                                <input type="email" id="businessName" value="${user.user_name}" readonly>
                            </label>
                        </div>
                        <div class="businessInfo">
                            <label for="password">
                                <span>기존 비밀번호</span>
                                <input type="password" id="password" placeholder="기존 비밀번호를 입력하세요.">
                            </label>
                        </div>
                        <div class="businessInfo">
                            <label for="new-password">
                                <span>새 비밀번호</span>
                                <input type="password" id="new-password" placeholder="새 비밀번호를 입력하세요.">
                            </label>
                        </div>
                        <div class="businessInfo">
                            <label for="businessNumber">
                                <span>사업자 번호</span>
                                <input type="text" id="businessNumber" value="${user.companyNumber}" readonly>
                            </label>
                        </div>
                        
                        <button type="submit" class="submit-button">
                            <img src="/img/checkIcon.png" alt="submit-button" class="submit-button">
                        </button>
                    
                    </form>
                </div>
                `;

                setTimeout(() => {
                    const profileUpdateModal = document.getElementById('businessUpdate-modal');
                    if (profileUpdateModal) {
                        profileUpdateModal.classList.remove('hidden');
                        profileUpdateModal.style.display = 'block';

                        const closeButton = profileUpdateModal.querySelector('.close-button');
                        closeButton.addEventListener('click', function () {
                            profileUpdateModal.classList.add('hidden');
                            profileUpdateModal.style.display = 'none';
                        });

                        const profileForm = document.getElementById('profile-update-form');
                        profileForm.addEventListener('submit', function (event) {
                            event.preventDefault();

                            const currentPassword = document.getElementById("password").value.trim();
                            const newPassword = document.getElementById("new-password").value.trim();

                            if (!validatePasswords(currentPassword, newPassword)) {
                                return;
                            }

                            const formData = {
                                userId: userId,
                                currentPassword: currentPassword,
                                newPassword: newPassword,
                            };

                                fetch(`/mypage/business/update/${userId}`, {
                                    method: "POST",
                                    headers: {
                                        "Content-Type": "application/json"
                                    },
                                    body: JSON.stringify(formData)
                                })
                                    .then(response => response.json())
                                    .then(data => {
                                        console.log("Success:", data);
                                        alert(data.message || "프로필이 성공적으로 업데이트되었습니다.");

                                        // 모달 숨기기
                                        profileUpdateModal.classList.add('hidden');
                                        profileUpdateModal.style.display = 'none';
                                    })
                                    .catch(error => {
                                        console.error("Error:", error);
                                        alert("프로필 업데이트 중 오류가 발생했습니다.");
                                    });
                        });
                    } else {
                        console.error("모달 요소를 찾을 수 없습니다.");
                    }
                }, 0);
            })
            .catch(error => {
                console.error('프로필 정보 로드 오류:', error);
                alert('프로필 정보를 불러올 수 없습니다.');
            });
    });
});



// 모달 외부 닫기
document.addEventListener('DOMContentLoaded', function () {
    // 모달 외부 닫기 공통
    const modals = document.querySelectorAll('.modal, .businessUpdate-modal, .package-modal');

    window.addEventListener('click', function (event) {
        modals.forEach(modal => {
            if (event.target === modal) {
                modal.style.display = 'none';
            }
        });
    });
})

// 홈버튼 클릭 시 이동
document.addEventListener('DOMContentLoaded', function () {
    // 홈버튼 클릭 시 해당 홈으로 이동 url
    const homeLogoButton = document.querySelector('.homeLogo-Button');
    if (homeLogoButton) {
        homeLogoButton.addEventListener('click', function () {
            window.location.href = '/main/mainpage'; // 홈페이지로 이동
        });
    }
})


