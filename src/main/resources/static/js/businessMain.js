document.addEventListener("DOMContentLoaded", () => {
    const packageListContainer = document.querySelector('.package-list');

    fetch('/mypage/business/packageList')
        .then(response => response.json())
        .then(data => {
            packageListContainer.innerHTML = data.map(package => `
                <div class="package-item">
                    <div class="package-header">
                        <div class="package-name">${package.packageTitle}</div>
                    </div>
                    <div class="package-content">${package.packageContent}</div>
                    <div class="package-status">${package.packageStatus}</div>
                    <div class="liked">
                        <img src="/static/img/heart-icon-fill.png" alt="heart-icon-fill" id="liked-heart-icon" type="button">
                        <span class="likedCnt">${package.likedCount}</span>
                    </div>
                </div>
            `).join('');
        })
        .catch(error => console.error('Error fetching package data:', error));
});