// 로그인 유저 권한 확인 (일반유저, 비즈니스)
document.querySelector('.mypage').addEventListener('click', () => {
    fetch('/mypage/loginuserauthority')
        .then(response => response.json())
        .then(data => {
            const redirectUrl = data.redirectUrl;
            if (redirectUrl) {
                window.location.href = redirectUrl;
            } else {
                alert("권한 확인에 실패했습니다.");
            }
        })
        .catch(error => {
            console.error('에러 발생:', error);
            alert("로그인 정보를 가져오는 데 실패했습니다.");
        });
});