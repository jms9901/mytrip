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
    // document.querySelector('.mypage').addEventListener('click', () => {
    //     fetch('../../mypage/loginuserid')
    //         .then(response => response.json())
    //         .then(data => {
    //             const loguser = data.userId;
    //             if (loguser) {
    //                 window.location.href = `/mypage/${loguser}`;
    //             } else {
    //                 alert("로그인된 사용자를 찾을 수 없습니다.");
    //             }
    //         })
    //         .catch(error => {
    //             console.error('에러 발생:', error);
    //             alert("로그인 정보를 가져오는 데 실패했습니다.");
    //         });
    //
    // });
});
