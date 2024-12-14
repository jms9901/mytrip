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

$(document).ready(function  (event) {

        console.log("board details button clicked:", event.target);

        // 데이터 가져오기
        const cityImg = event.target.getAttribute('data-cityImg');

        console.log("Fetching attachments for boardId:", cityImg);

        // 첨부파일 가져오기
        fetch(`/flight/detailImg/{cityName}`)
            .then((response) => response.json())
            .then((cityImg) => {
                const imageContainer = document.getElementById('imageContainer');
                imageContainer.innerHTML = ''; // 기존 이미지 제거

                if (cityImg && cityImg.length > 0) {
                    cityImg.forEach((cityImg) => {
                        const img = document.createElement('img');
                        img.src = `/img/${cityImg.cityName}`; // 서버의 업로드 디렉토리 경로
                        img.alt = '첨부 이미지';
                        imageContainer.appendChild(img);
                    });
                } else {
                    imageContainer.innerHTML = '<p>첨부파일이 없습니다.</p>';
                }
            })
            .catch((error) => {
                console.error("Error fetching attachments:", error);
                alert("첨부파일 정보를 불러오지 못했습니다.");
            });


});
