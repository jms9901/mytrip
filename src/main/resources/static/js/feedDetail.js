document.addEventListener("DOMContentLoaded", () => {
    // 사용자 권한 정보 (예: 서버에서 전달받은 사용자 정보)
    const isCurrentUser = true; // true: 본인, false: 타인 (임시 값, 실제 서버로부터 받아야 함)

    const deleteButton = document.getElementById("delete-button");
    const updateButton = document.getElementById("update-button");
    const goFeedListButton = document.querySelector(".goFeedList");

    // 본인 권한인 경우: 삭제/수정 버튼 보이기
    if (isCurrentUser) {
        deleteButton.style.display = "inline";
        updateButton.style.display = "inline";

        goFeedListButton.style.display = "none";
        goDeclarationButton.style.display = "none";
    } else {
        // 타인 권한인 경우: 피드 리스트/신고 버튼 보이기
        deleteButton.style.display = "none";
        updateButton.style.display = "none";

        goFeedListButton.style.display = "inline";
    }
});