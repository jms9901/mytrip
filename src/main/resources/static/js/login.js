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
    event.preventDefault();
    const form = document.getElementById("loginForm2");  // ID 수정
    const inputs = form.querySelectorAll("input");
    let check = true;
    if(document.querySelector('.text-danger') !== null){
      form.submit();
      alert(`회원 가입 중 오류가 발생했습니다.`);
      href.location='/user/login;'
    }
    form.submit();
    alert('회원가입이 되었습니다.');

  });

  document.getElementById("submit3").addEventListener("click", function (event) {
    event.preventDefault();
    const form = document.getElementById("loginForm3");  // ID 수정
    const inputs = form.querySelectorAll("input");
    let check = true;
    if(document.querySelector('.text-danger') !== null){
      form.submit();
      alert(`회원 가입 중 오류가 발생했습니다.`);
      href.location='/user/login;'
    }
    form.submit();
    alert('회원가입이 되었습니다.');

  });
});