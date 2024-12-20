let mybutton = document.getElementById("myBtn");

// 통합된 스크롤 이벤트 핸들러
function handleScrollForButton() {
    let scrollPosition = window.scrollY;

    // scrollDown 버튼 표시/숨기기 로직
    if (scrollPosition > 20) {
        mybutton.style.display = "block";
    } else {
        mybutton.style.display = "none";
    }
}

// 기존의 `onscroll`에 영향을 주지 않도록 함수로 분리
window.addEventListener("scroll", handleScrollForButton);

// When the user clicks on the button, scroll to the top of the document
function topFunction() {
    document.body.scrollTop = 0; // Safari
    document.documentElement.scrollTop = 0; // Chrome, Firefox, IE, Opera
}
