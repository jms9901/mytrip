document.addEventListener("DOMContentLoaded", function () {
  if(window.location.search.includes('error')){
    alert('존재하지 않는 계정이거나 ID 또는 비밀번호를 잘못입력하셨습니다.');
  }

  document.querySelectorAll(".img__btn").forEach((button) => {
    button.addEventListener("click", function () {
      document.querySelector(".cont").classList.toggle("s--signup");
    });
  });

  function showForm(type) {
    const form2 = document.getElementById("loginForm2");
    const form3 = document.getElementById("loginForm3");

    if (form2) form2.classList.add("hidden");
    if (form3) form3.classList.add("hidden");

    if (type === "member" && form2) {
      form2.classList.remove("hidden");
    } else if (type === "business" && form3) {
      form3.classList.remove("hidden");
    }
  }

  document.getElementById("member")?.addEventListener("click", function () {
    showForm("member");
  });

  document.getElementById("business")?.addEventListener("click", function () {
    showForm("business");
  });

  function handleFormSubmission(formId) {
    const form = document.getElementById(formId);
    if (!form) {
      console.error(`Form with ID ${formId} not found.`);
      return;
    }

    const inputs = form.querySelectorAll("input");
    let isValid = true;

    inputs.forEach((input) => {
      if (!input.value.trim()) {
        isValid = false;
        input.classList.add("error");
      } else {
        input.classList.remove("error");
      }
    });

    if (isValid) {
      console.log("Submitting form:", formId);
      form.submit();
    }
  }

  document.getElementById("submit1").addEventListener("click", function (event) {
    event.preventDefault();
    const form = document.getElementById("loginForm1");
    const inputs = form.querySelectorAll("input");
    form.submit();
  });

  document.getElementById("submit2").addEventListener("click", function (event) {
    event.preventDefault(); // 폼 제출 기본 동작을 막음

    const form = document.getElementById("loginForm2");
    const formData = new FormData(form); // 폼 데이터를 FormData 객체로 가져옴

    fetch('/user/register', {
      method: 'POST',
      body: formData
    })
        .then(response => response.json().then(data => ({ status: response.status, body: data })))
        .then(({ status, body }) => {
          const errorDiv2 = document.getElementById('errorDiv2');
          if (status >= 400 && status < 600) {
            errorDiv2.textContent = body.error || '회원 가입 중 오류가 발생했습니다.'; // 서버에서 받은 에러 메시지를 div에 표시
            alert(body.error || '회원 가입 중 오류가 발생했습니다.');
          } else {
            errorDiv3.textContent = ''; // 성공 시 에러 메시지를 제거
            alert('회원가입이 되었습니다.');
            window.location.href = '/user/login'; // 성공 시 로그인 페이지로 리다이렉트
          }
        })
        .catch(error => {
          console.error('에러 발생:', error);
          alert('회원 가입 중 오류가 발생했습니다.');
        });
  });



  document.getElementById("submit3").addEventListener("click", function (event) {
    event.preventDefault(); // 폼 제출 기본 동작을 막음

    const form = document.getElementById("loginForm3");
    const formData = new FormData(form); // 폼 데이터를 FormData 객체로 가져옴

    fetch('/user/register', {
      method: 'POST',
      body: formData
    })
        .then(response => response.json().then(data => ({ status: response.status, body: data })))
        .then(({ status, body }) => {
          const errorDiv3 = document.getElementById('errorDiv3');
          if (status >= 400 && status < 600) {
            errorDiv3.textContent = body.error || '회원 가입 중 오류가 발생했습니다.'; // 서버에서 받은 에러 메시지를 div에 표시
            alert(body.error || '회원 가입 중 오류가 발생했습니다.');
          } else {
            errorDiv3.textContent = ''; // 성공 시 에러 메시지를 제거
            alert('회원가입이 되었습니다.');
            window.location.href = '/user/login'; // 성공 시 로그인 페이지로 리다이렉트
          }
        })
        .catch(error => {
          console.error('에러 발생:', error);
          alert('회원 가입 중 오류가 발생했습니다.');
        });
  });



  // document.getElementById("submit2").addEventListener("click", function (event) {
  //   event.preventDefault();
  //   const form = document.getElementById("loginForm2");  // ID 수정
  //   const inputs = form.querySelectorAll("input");
  //   let check = true;
  //   if(document.querySelector('.text-danger') === null){
  //     form.submit();
  //     alert(`회원 가입 중 오류가 발생했습니다.`);
  //     href.location='/user/login;'
  //   }
  //   form.submit();
  //   alert('회원가입이 되었습니다.');
  //
  // });
  //
  // document.getElementById("submit3").addEventListener("click", function (event) {
  //   event.preventDefault();
  //   const form = document.getElementById("loginForm3");  // ID 수정
  //   const inputs = form.querySelectorAll("input");
  //   let check = true;
  //   if(document.querySelector('.text-danger') === null){
  //     form.submit();
  //     alert(`회원 가입 중 오류가 발생했습니다.`);
  //     href.location='/user/login;'
  //   }
  //   form.submit();
  //   alert('회원가입이 되었습니다. 관리자의 승인을 기다려주세요');
  //
  // });
});