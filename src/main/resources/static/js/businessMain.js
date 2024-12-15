document.addEventListener("DOMContentLoaded", () => {
    // 패키지 리스트 동적 생성 부분
    const packageListContainer = document.querySelector('.package-list');

    // 현재 페이지의 경로에서 userId 추출하기
    const path = window.location.pathname; // 예: "/mypage/business/123"
    const segments = path.split('/'); // 예: ["", "mypage", "business", "123"]

    // userId가 마지막 segment에 있다고 가정
    const userId = segments[segments.length - 1]; // 마지막 세그먼트 추출

    console.log(userId); // 예: userId 확인
    console.log(document.querySelector(".package-list"));

    // 사용자 확인
    if (!userId || isNaN(userId)) {
        console.error('유효하지 않은 사용자 ID입니다.');
        packageListContainer.innerHTML = '<p>사용자 정보를 불러올 수 없습니다.</p>';
        return;
    }
    if(!packageListContainer) {
        console.error("등록한 패키지가 없습니다.");
        packageListContainer.innerHTML = '<p>등록한 패키지가 없습니다.</p>';
        return;
    }

    // 메인 페이지 정보 로드
    fetch(`/mypage/business/js/${userId}`, {
        // 명시적으로 JSON 요청 설정
        headers: {
            'Accept' : 'application/json',
            'Content-Type' : 'application/json'
        }
    })
        .then(response => {
            console.log("메인 서버 응답:", response);
            //응답이 JSON인지 확인
            const contentType = response.headers.get('content-type');
            if(!contentType || !contentType.includes('application/json')) {
                throw new TypeError("기본 정보 데이터 : 서버 응답이 JSON 형식이 아닙니다.")
            }
            if(!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json();
        })

        .then(data => {
            console.log("데이터 확인: ", data);
            // 데이터 형식 확인
            if(!Array.isArray(data)) {
                console.error('Unexpected data format: ', data);
                packageListContainer.innerHTML = "<p>패키지 데이터를 올바르게 가져오지 못했습니다.</p>"
                return;
            }

            // 패키지 데이터가 비어있는 경우 처리
            if (!data || data.length === 0) {
                packageListContainer.innerHTML = '<p>등록된 패키지가 없습니다.</p>';
                return;
            }

            // 패키지 리스트 데이터 렌더링
            packageListContainer.innerHTML = data.map(packages => `
                <div class="package-item" data-package-id="${packages.packageId}">
                    <div class="package-title">
                        <div class="package-name">${packages.packageTitle}</div>
                    </div>
                    <div class="package-content">${packages.packageContent}</div>
                    <div class="package-status ${getStatusClass(packages.packageStatus)}">${getStatusText(packages.packageStatus)}</div>
                    <div class="liked">
                        <img src="/img/heart-icon-fill.png" alt="heart-icon-fill" id="liked-heart-icon" type="button">
                        <span class="likedCnt">${packages.likedCount}</span>
                    </div>
                </div>
            `).join('');

            // 패키지 상세보기 모달 이벤트 리스너
            const packageItems = document.querySelectorAll('.package-item');
            const packageModal = document.getElementById('package-modal');
            const closeButton = packageModal.querySelector('.close-button');

            packageItems.forEach(item => {
                item.addEventListener('click', function() {
                    const packageId = this.dataset.packageId;
                    loadPackageDetails(packageId);
                });
            });

            if (!closeButton) {
                console.error('닫기 버튼을 찾을 수 없습니다.');
                return;
            }

            // 닫기 버튼 이벤트
            closeButton.addEventListener('click', function() {
                packageModal.style.display = 'none';
            });

        })
        .catch(error => {
            console.error('Error fetching package data:', error);
            packageListContainer.innerHTML = '<p>패키지를 불러오는 중 오류가 발생했습니다.</p>';
        });
});

//  패키지 상세보기 모달
document.addEventListener('click', function() {
    const packageDetailData  = document.querySelector('.packageDetail');
    packageModal.querySelector('.package-regdate').textContent = `게시일: ${#temporals.format(new Date(packagePost.packageRegdate), 'yyyy-MM-dd')}`;

    // 패키지 상세보기 모달 데이터 함수
    function loadPackageDetails(packageId) {
        fetch(`/mypage/business/package/${packageId}`, {
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
            .then(packageDetail => {
                console.log("패키지 데이터 확인: ", packageDetail)

                // 패키지 상세보기 데이터 렌더링
                packageDetailData.innerHTML = packageDetail.map(packageInfo => `
                    <div class="modal-content" data-package-id = "${packageInfo.packageId}">
                        <span class="close-button">&times;</span>
                        <div class="package-title">${packageInfo.packageTitle}</div>
                        <div class="package-regdate"></div>
                        
                        <div class="modal-body">
                            <div class="image-section">
                                <div class="slideshow-container">
                                    <div class="slide-fade">
                                    <!--  이미지 첨부파일 동적 생성  -->
                                    </div>
                                </div>
                                
                                <!-- 슬라이드 이전/다음 버튼 -->
                                <a class="prev">&#10094;</a>
                                <a class="next">&#10095;</a>
                            </div>
                        </div>
                        
                        <div class="info-section">
                            <label for="cityName">
                                <span>도시</span>
                                <input type="text" class="cityName" th:value="${packageInfo.cityName}">
                            </label>
                            <label for="packageStartDay" class="packageStartDay">
                                <span>시작일</span>
                                <input type="date" class="packageStartDay" th:value="${#temporals.format(packageInfo.packageStartDay, 'yyyy-MM-dd')}">
                            </label>
                            <label for="packageEndDay">
                                <span>종료일</span>
                                <input type="date" class="packageEndDay" th:value="${#temporals.format(packageInfo.packageEndDay, 'yyyy-MM-dd')}">
                            </label>
                            <label for="packageCost">
                                <span>금액</span>
                                <input type="text" class="packageCost" th:value="${packageInfo.packageCost}">
                            </label>
                            <label for="packageMaxpeople">
                                <span>최대 인원</span>
                                <input type="number" class="packageMaxpeople" th:value="${packageInfo.packageMaxpeople}">
                            </label>
                        </div>
                        
                        <div class="package-details">
                            <label for="packageContent">
                                <span>패키지 내용</span>
                                <textarea class="package-content" th:value="${packageInfo.packageContent}"></textarea>
                            </label>
                        </div>
                        
                        <div class="file-attachments">
                            <!-- 해당 패키지 이미지 첨부파일 리스트 동적 추가 -->
                        </div>
                        <div class="attachmentInfo">해당 첨부파일 클릭 시 삭제됩니다.</div>
                        
                        <div class="button-action" style="border: none;">
                            <button type="button" class="update-button">
                                <img src="/img/myPageUpdate.png" alt="update-button" class="update-button">
                            </button>
                            <button type="submit" class="submit-button">
                                <img src="/img/checkIcon.png" alt="submit-button" class="submit-button">
                            </button>
                            <button type="button" class="delete-button">
                                <img src="/img/mypageDeleteIcon.png" alt="delete-button" class="delete-button">
                            </button>
                        </div>
                    </div>
                `)

                const packageModal = document.getElementById('#modal');
                const updateButton = packageModal.querySelector('#package-update-button');
                const submitButton = packageModal.querySelector('#package-submit-button');
                const updateImage = packageModal.querySelector('.package-image')
                const inputs = packageModal.querySelectorAll('input, textarea');

                // 패키지 상태에 따라 수정 버튼 표시 여부 결정
                if (packageDetail.packageStatus === '승인') {
                    // 승인된 패키지는 수정 불가
                    updateButton.style.display = 'none';
                    submitButton.style.display = 'none';

                    // 모든 입력 필드를 읽기 전용으로 설정
                    inputs.forEach(input => {
                        input.setAttribute('readonly', true);
                    });
                } else {
                    // 대기 또는 미승인 상태일 경우
                    updateButton.style.display = 'block';

                    // 초기에는 모든 입력 필드를 읽기 전용으로 설정
                    inputs.forEach(input => {
                        input.setAttribute('readonly', true);
                    });

                    // 수정 버튼 클릭 시 입력 필드 활성화
                    updateButton.addEventListener('click', function() {
                        inputs.forEach(input => {
                            input.removeAttribute('readonly');
                            input.classList.add('editable');
                        });

                        // 수정 버튼 숨기고 제출 버튼 표시
                        updateButton.style.display = 'none';
                        submitButton.style.display = 'block';
                    });

                    // 제출 버튼 클릭 시 패키지 정보 업데이트
                    submitButton.addEventListener('click', function() {
                        const updatedPackageData = {
                            packageId: packageId,
                            packageTitle: packageModal.querySelector('.packageTitle').value,
                            // packageImage: packageModal.querySelector('.packageImage').value,
                            cityName: packageModal.querySelector('.cityName').value,
                            packageStartDay: packageModal.querySelector('.packageStartDay').value,
                            packageEndDay: packageModal.querySelector('.packageEndDay').value,
                            packageCost: packageModal.querySelector('.packageCost').value,
                            maxPeople: packageModal.querySelector('.packageMaxpeople').value,
                            packageContent: packageModal.querySelector('.packageContent').value
                        };

                        // Date formatting: Ensuring date fields are in the required yyyy-MM-dd format
                        updatedPackageData.packageStartDay = updatedPackageData.packageStartDay.split('T')[0];
                        updatedPackageData.packageEndDay = updatedPackageData.packageEndDay.split('T')[0];

                        fetch(`/mypage/package/update/${packageId}`, {
                            method: 'POST',
                            headers: {
                                'Accept' : 'application/json',
                                'Content-Type': 'application/json'
                            },
                            body: JSON.stringify(updatedPackageData)
                        })
                            .then(response => {
                                console.log("패키지 수정 서버 응답: ",response);
                                if (!response.ok) {
                                    throw new Error('패키지 업데이트 실패');
                                }
                                return response.json();
                            })
                            .then(data => {
                                console.log("패키지 수정 데이터: ", data)
                                // 업데이트 성공 시 패키지 상태를 '대기'로 변경
                                alert('패키지가 성공적으로 업데이트되었습니다. 관리자 승인 대기 상태입니다.');
                                packageModal.querySelector('.package-status').textContent = '대기';
                                packageModal.querySelector('.package-status').className = 'package-status status-pending';

                                // 입력 필드를 다시 읽기 전용으로 설정
                                inputs.forEach(input => {
                                    input.setAttribute('readonly', true);
                                    input.classList.remove('editable');
                                });

                                // 버튼 상태 복원
                                submitButton.style.display = 'none';
                                updateButton.style.display = 'block';
                            })
                            .catch(error => {
                                console.error('패키지 업데이트 오류:', error);
                                alert('패키지 업데이트 중 오류가 발생했습니다.');
                            });
                    });
                }

                // 기존 모달 내용 업데이트 로직 유지
                packageModal.querySelector('.package-title').textContent = packageDetail.packageTitle;
                packageModal.querySelector('.package-regdate').textContent = `게시일: ${#temporals.format(new Date(packagePost.packageRegdate), 'yyyy-MM-dd')}`;

                // 이미지 슬라이드 업데이트
                const slideshowContainer = packageModal.querySelector('.slideshow-container');
                slideshowContainer.innerHTML = packageDetail.images.map((img, index) => `
                <div class="slide fade ${index === 0 ? 'active' : ''}">
                    <img src="${img}" alt="package-image">
                </div>
            `).join('');

                // 슬라이드 컨트롤 표시
                let slideIndex = 1;
                showSlide(slideIndex);

                function showSlide(n) {
                    const slides = packageModal.querySelector('.mySlides');
                    const dots = packageModal.querySelector('.dot');

                    if (n > slides.length) {slideIndex = 1}
                    if (n < 1) {slideIndex = slides.length}

                    slides.forEach((slide, i) => {
                        slide.style.display = i === slideIndex - 1 ? 'block' : 'none';
                    });

                    dots.forEach((dot, i) => {
                        dot.classList.remove('active');
                        if (i === slideIndex - 1) {
                            dot.classList.add('active');
                        }
                    });
                    // 슬라이드 자동 전환
                    setInterval(() => {
                        slideIndex++;
                        showSlides(slideIndex);
                    }, 5000); // 5초마다 슬라이드 전환
                }

                // 입력 필드 값 설정
                packageModal.querySelector('.cityName').value = packageDetail.cityName;
                packageModal.querySelector('.packageStartDay').value = packageDetail.packageStartDay;
                packageModal.querySelector('.packageEndDay').value = packageDetail.packageEndDay;
                packageModal.querySelector('.packageCost').value = packageDetail.packageCost;
                packageModal.querySelector('.packageMaxpeople').value = packageDetail.packageMaxpeople;
                packageModal.querySelector('.packageContent').value = packageDetail.packageContent;

                // 패키지 상태 표시
                const statusElement = packageModal.querySelector('.package-status');
                statusElement.textContent = getStatusText(packageDetail.packageStatus);
                statusElement.className = `package-status ${getStatusClass(packageDetail.packageStatus)}`;

                // 모달 표시
                packageModal.style.display = 'block';
            })
            .catch(error => {
                console.error('패키지 상세 정보 로딩 중 오류 발생: ', error);
                alert('패키지 상세 정보를 불러오는 중 오류가 발생했습니다.');
            });
    }


        // 패키지 아이템들에 이벤트 리스너 추가
        const packageItems = document.querySelectorAll('.package-item');
        packageItems.forEach(item => {
            item.addEventListener('click', function() {
                const packageId = this.dataset.packageId;
                loadPackageDetails(packageId);
            });
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
document.addEventListener('click', function() {
    // 결제 버튼 이벤트 리스너
    var paymentButton = document.getElementById('package-payment-button');
    if (paymentButton) {
        paymentButton.addEventListener('click', function() {
            fetch(`mypage/payments/${userId}`, {
                method: 'GET',
                headers: {
                    'Accept' : 'application/json',
                    'Content-Type': 'application/json'
                }
            })
                .then(response => {
                    console.log("결제 내역 서버 응답: ", response);
                    if(!response.ok) {
                        throw new Error(`HTTP error! status: ${response.status} / 결제 정보를 불러올 수 없습니다.`);
                    }
                    return response.json();
                })
                .then(paymentData => {
                    console.log("결제 내역 데이터: ", paymentData)
                    const paymentModal = document.getElementById('payment-modal');
                    const paymentTable = paymentModal.querySelector('.payment-table tbody');

                    // 결제 데이터로 테이블 업데이트
                    paymentTable.innerHTML = paymentData.map(payment => `
                    <tr>
                        <td>${payment.userName}</td>
                        <td>${payment.packageTitle}</td>
                        <td>${payment.totalPrice}원</td>
                        <td class="pay-status ${getPaymentStatusClass(payment.paymentStatus)}">
                            ${getPaymentStatusText(payment.paymentStatus)}
                        </td>
                    </tr>
                `).join('');

                    // 모달 표시
                    paymentModal.style.display = 'block';
                })
                .catch(error => {
                    console.error("결제 정보 로딩 중 오류 발생", error);
                    packageListContainer.innerHTML = '<p>결제 정보를 불러오는 중 오류가 발생했습니다.</p>';
                });
        });
    }

    // 결제 상태 필터링
    const payStatusSelect = document.querySelector('.pay-status-select');
    if(payStatusSelect) {
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
    switch (status) {
        case '결제 완료' :
            return '결제 완료';
        case '결제 취소' :
            return '결제 취소';
        default:
            return '';
    }
}

// 상태에 따른 CSS 클래스를 반환하는 함수 추가
function getPaymentStatusClass(status) {
    switch (status) {
        case '결제 완료' :
            return 'payment-complete';
        case '결제 취소' :
            return 'payment-cancel';
        default:
            return '';
    }
}

// 개인정보 수정 모달
document.getElementById('profile-icon-Button').addEventListener('click', function() {
    const profileUpdateModal = document.getElementById('modalOverlay');
    const profileUpdateButton = document.getElementById('update-button');
    const profileSubmitButton = profileUpdateModal.querySelector('.submit-button');

    // 프로필 수정 모달 열기
    document.getElementById('profile-icon-Button').addEventListener('click', function() {
        profileUpdateModal.style.display = 'block';
    });

    // 프로필 수정 모달 닫기
    profileUpdateModal.querySelector('.close-button').addEventListener('click', function() {
        profileUpdateModal.style.display = 'none';
    });

    // 프로필 업데이트 버튼 클릭 시
    profileUpdateButton.addEventListener('click', function() {
        // 수정 모드로 전환
        const inputs = profileUpdateModal.querySelectorAll('input');
        inputs.forEach(input => {
            if (input.hasAttribute('readonly')) {
                input.removeAttribute('readonly');
                input.classList.add('editable');
            }
        });
    });
    // 프로필 제출 버튼 클릭 시
    profileSubmitButton.addEventListener('click', function() {
        const formData = {
            companyNumber: profileUpdateModal.querySelector('input[type="text"][placeholder="회사 사업자 번호"]').value,
            password: profileUpdateModal.querySelector('input[type="password"]').value,
            newPassword: profileUpdateModal.querySelector('input[type="re-password"]').value
        };

        fetch(`/mypage/business/profile/{userId}`, {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(formData)
        })
            .then(response => {
                console.log("프로필 수정 서버 응답: ", response);
                if (!response.ok) {
                    throw new Error('프로필 업데이트 실패');
                }
                return response.json();
            })
            .then(data => {
                alert('프로필이 성공적으로 업데이트되었습니다.');
                profileUpdateModal.style.display = 'none';
            })
            .catch(error => {
                console.error('프로필 업데이트 오류:', error);
                alert('프로필 업데이트 중 오류가 발생했습니다.');
            });
    });
});

// 모달 외부 닫기
document.addEventListener('DOMContentLoaded', function () {
    // 모달 외부 닫기 공통
    const modals = document.querySelectorAll('.modal, .businessUpdate-modal, .package-modal');

    window.addEventListener('click', function(event) {
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
        homeLogoButton.addEventListener('click', function() {
            window.location.href = '/'; // 홈페이지로 이동
        });
    }
})

