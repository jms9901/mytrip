let scrollMenu = document.getElementById("scrollMenu");
let section3 = document.getElementById("section1");

// 통합된 스크롤 이벤트 핸들러
function handleScroll() {
    let section3Top = section3.offsetTop; // section3의 상단 위치
    let scrollPosition = window.scrollY; // 현재 스크롤 위치

    // 메뉴바 표시/숨기기 로직
    if (scrollPosition >= section3Top) {
        scrollMenu.style.display = "block"; // section3 이후에 메뉴바 표시
    } else {
        scrollMenu.style.display = "none"; // section3 이전에는 메뉴바 숨기기
    }
}

// 기존의 `onscroll`에 영향을 주지 않도록 함수로 분리
window.addEventListener("scroll", handleScroll);
