let currentIndex = {
    packages: 0,
    groups: 0
};

function moveSlide(sliderId, direction) {
    const slider = document.getElementById(sliderId);
    const items = slider.getElementsByClassName('slider-item');
    const itemCount = items.length;
    const itemsVisible = 4; // 화면에 보일 항목 수

    currentIndex[sliderId] += direction;

    if (currentIndex[sliderId] < 0) {
        currentIndex[sliderId] = 0;
    } else if (currentIndex[sliderId] > itemCount - itemsVisible) {
        currentIndex[sliderId] = itemCount - itemsVisible;
    }

    const offset = currentIndex[sliderId] * -25; // 25%씩 이동
    slider.style.transform = `translateX(${offset}%)`;
}

function goToFeed(){
    href.location='피드게시판'
}
