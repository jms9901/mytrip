const modal = document.getElementById("feed-modal");
const openModal = document.getElementById("open-modal");
const submitButton = document.getElementById("submit-button");
const fileInput = document.getElementById("file-input");
const imagePreview = document.getElementById("image-preview");
const prevBtn = document.querySelector(".prev-btn");
const nextBtn = document.querySelector(".next-btn");
const cameraIcon = document.getElementById("camera-icon");

let currentImageIndex = 0;
let uploadedImages = [];

// 작성 모달 열기
openModal.addEventListener("click", () => {
    modal.classList.remove("hidden");
});

// 버튼 submit
submitButton.addEventListener("click", () => {

    // 필수 입력값 검증
    // TODO

    alert("피드가 작성되었습니다.");
    modal.classList.add("hidden");
    // uploadedImages 배열 초기화
    uploadedImages = [];
    currentImageIndex = 0;
    updatePreview();
});

// 파일 업로드
fileInput.addEventListener("change", (event) => {
    const files = Array.from(event.target.files);

    // 파일 최대 5장 검증
    if (files.length + uploadedImages.length > 5) {
        alert("이미지는 최대 5장입니다.");
        return;
    }

    uploadedImages = []  // 이미지 리셋
    currentImageIndex = 0;  // 슬라이드 리셋

    // Read and preview each image
    files.forEach((file, index) => {
        const reader = new FileReader();
        reader.onload = (e) => {
            const img = document.createElement("img");
            img.src = e.target.result;

            // 이미지를 넣으면 카메라 아이콘 hidden 처리, 썸네일
            if (index === 0) {
                cameraIcon.style.opacity = "0"; // 카메라 hidden
                uploadedImages.push(img);
                updatePreview(); // 첫 이미지 업데이트
            } else {
                uploadedImages.push(img);
            }
        };
        reader.readAsDataURL(file);
    });

    updatePreview();
});

// 미리보기 사진 업데이트
function updatePreview() {
    imagePreview.innerHTML = "";

    // 미리보기 이미지 추가
    if (uploadedImages.length > 0) {
        const currentImg = uploadedImages[currentImageIndex];
        currentImg.style.width = "100%";
        currentImg.style.height = "100%";
        currentImg.style.objectFit = "cover";
        imagePreview.appendChild(currentImg);

        // prev, next 버튼
        prevBtn.classList.toggle("hidden", currentImageIndex === 0);
        nextBtn.classList.toggle("hidden", currentImageIndex === uploadedImages.length - 1);
    } else {
        // 이미지가 없으면 카메라 아이콘 생성
        cameraIcon.style.opacity = "1";
    }
}

// 미리보기 이미지 슬라이드
prevBtn.addEventListener("click", () => {
    if (currentImageIndex > 0) {
        currentImageIndex--;
        updatePreview();
    }
});

nextBtn.addEventListener("click", () => {
    if (currentImageIndex < uploadedImages.length - 1) {
        currentImageIndex++;
        updatePreview();
    }
});

