document.addEventListener("DOMContentLoaded", () => {
    const packageListContainer = document.querySelector('.package-list');

    // 로그인한 사용자의 ID를 안전하게 가져오는 방법
    const userElement = document.querySelector('[data-user-id]');
    const userId = userElement ? userElement.getAttribute('data-user-id') : null;

    if (!userId) {
        console.error('사용자 ID를 찾을 수 없습니다.');
        packageListContainer.innerHTML = '<p>사용자 정보를 불러올 수 없습니다.</p>';
        return;
    }


    fetch(`/mypage/business/${userId}`)
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            packageListContainer.innerHTML = data.map(packages => `
                <div class="package-item">
                    <div class="package-header">
                        <div class="package-name">${packages.title}</div>
                    </div>
                    <div class="package-content">${packages.content}</div>
                    <div class="package-status">${packages.status}</div>
                    <div class="liked">
                        <img src="/img/heart-icon-fill.png" alt="heart-icon-fill" id="liked-heart-icon" type="button">
                        <span class="likedCnt">${packages.likedCount}</span>
                    </div>
                </div>
            `).join('');
        })
        .catch(error => {
            console.error('Error fetching package data:', error);
            packageListContainer.innerHTML = '<p>패키지를 불러오는 중 오류가 발생했습니다.</p>';
        });
});