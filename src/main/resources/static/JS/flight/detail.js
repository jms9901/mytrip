document.addEventListener('scroll', () => {
    const scrollIndicator = document.querySelector('.scroll-indicator');
    const scrollPosition = window.scrollY;

    if (scrollIndicator) {
        if (scrollPosition > 0) {
            scrollIndicator.style.opacity = '0'; // 스크롤 시 숨김
            scrollIndicator.style.pointerEvents = 'none'; // 클릭 불가능 처리
        } else {
            scrollIndicator.style.opacity = '1'; // 맨 위로 스크롤 시 보이기
            scrollIndicator.style.pointerEvents = 'auto'; // 클릭 가능 복원
        }
    }
});

