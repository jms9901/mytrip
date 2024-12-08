document.addEventListener("DOMContentLoaded", function() {
    const slider = document.querySelector('.slider');
    const slides = document.querySelectorAll('.slide');

    function stopSlide() {
        slider.classList.add('paused'); // 슬라이더 멈춤
    }

    function startSlide() {
        slider.classList.remove('paused'); // 슬라이더 재개
    }

    slides.forEach(slide => {
        slide.addEventListener('mouseover', stopSlide); // 마우스 오버 시 슬라이드 멈춤
        slide.addEventListener('mouseout', startSlide); // 마우스 아웃 시 슬라이드 재개
    });
});
