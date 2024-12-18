// Scroll to Top 버튼 표시/숨기기
window.addEventListener("scroll", function () {
    const scrollToTopButton = document.getElementById("scrollToTop");
    if (window.scrollY > 300) {
        scrollToTopButton.style.display = "flex";
    } else {
        scrollToTopButton.style.display = "none";
    }
});

// Scroll to Top 버튼 클릭 이벤트
document.addEventListener("DOMContentLoaded", function () {
    const scrollToTopButton = document.getElementById("scrollToTop");
    if (scrollToTopButton) {
        scrollToTopButton.addEventListener("click", function () {
            window.scrollTo({ top: 0, behavior: "smooth" });
        });
    }

    // 본문 내용 처리 및 "더보기" 버튼 동작
    document.querySelectorAll(".content").forEach((content) => {
        const maxLength = 100; // 표시할 최대 글자 수
        const fullText = content.textContent.trim(); // 전체 본문 내용
        const moreButton = content.nextElementSibling; // 더보기 버튼

        if (fullText.length > maxLength) {
            const shortText = fullText.substring(0, maxLength) + "...";
            content.textContent = shortText; // 일부 내용만 표시

            // 더보기 버튼 클릭 이벤트
            if (moreButton) {
                moreButton.addEventListener("click", () => {
                    content.textContent = fullText; // 전체 내용 표시
                    moreButton.style.display = "none"; // 더보기 버튼 숨기기
                });
            }
        } else {
            if (moreButton) moreButton.style.display = "none"; // 내용이 짧으면 버튼 숨김
        }
    });
});
