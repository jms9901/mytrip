document.addEventListener('scroll', () => {
    console.log('Scroll event triggered'); // 스크롤 이벤트 확인
    const scrollIndicator = document.getElementById('scroll-indicator');
    const scrollPosition = window.scrollY;

    if (scrollIndicator) {
        console.log('Scroll indicator found'); // 요소가 선택되었는지 확인
        if (scrollPosition > 20) {
            console.log('Hiding indicator'); // 숨김 상태로 전환
            scrollIndicator.style.display = 'none'; // 스크롤 시 숨김
            scrollIndicator.style.pointerEvents = 'none'; // 클릭 불가능 처리
        } else {
            console.log('Showing indicator'); // 보임 상태로 전환
            scrollIndicator.style.opacity = '1'; // 맨 위로 스크롤 시 보이기
            scrollIndicator.style.pointerEvents = 'auto'; // 클릭 가능 복원
        }
    } else {
        console.error('Scroll indicator not found'); // 요소 선택 실패 시 메시지
    }
});