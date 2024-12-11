var current = null;


document.querySelector('#email').addEventListener('focus', function(e) {
  if (current) current.pause();
  current = anime({
    targets: 'path',
    strokeDashoffset: {
      value: 0,
      duration: 700,
      easing: 'easeOutQuart'
    },
    strokeDasharray: {
      value: '240 1386',
      duration: 700,
      easing: 'easeOutQuart'
    }
  });
});


document.querySelector('#password').addEventListener('focus', function(e) {
  if (current) current.pause();
  current = anime({
    targets: 'path',
    strokeDashoffset: {
      value: -336,
      duration: 700,
      easing: 'easeOutQuart'
    },
    strokeDasharray: {
      value: '240 1386',
      duration: 700,
      easing: 'easeOutQuart'
    }
  });
});

document.addEventListener('DOMContentLoaded', () => {
  const logoutLink = document.querySelector('.dropdown-item'); // 클래스 이름 수정
  if (logoutLink) {
    logoutLink.addEventListener('click', (event) => {
      event.preventDefault(); // 기본 동작 방지
      console.log('Logout link clicked!');
      // 추가 동작 수행
    });
  } else {
    console.error('Logout link not found.');
  }
});
