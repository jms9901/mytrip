const scrollIndicator = document.getElementById('scroll-indicator');

// 페이지 높이를 계산
const pageHeight = document.body.scrollHeight - window.innerHeight;

window.addEventListener('scroll', () => {
    const currentScrollY = window.scrollY;

    // 스크롤 위치가 페이지 높이의 10%를 넘으면 숨김
    if (currentScrollY > pageHeight * 0.1) {
        scrollIndicator.classList.add('hidden');
    } else {
        scrollIndicator.classList.remove('hidden');
    }

    // 최상단으로 돌아오면 다시 표시
    if (currentScrollY === 0) {
        scrollIndicator.classList.remove('hidden');
    }
});

document.addEventListener('DOMContentLoaded', () => {
    fetch('/aipage/username')
        .then(response => response.json())
        .then(data => {
            const userNames = document.querySelectorAll('.user-name'); // 클래스명으로 모든 요소 선택
            userNames.forEach((nameElement) => {
                nameElement.textContent = data.name; // 각 요소에 사용자 이름 업데이트
            });
        })
        .catch(error => {
            console.error('에러 발생:', error);
        });
});

