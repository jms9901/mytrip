document.addEventListener("DOMContentLoaded", function () {
  if(window.location.search.includes('error')){
    alert('로그인에 실패했습니다. 다시 시도해주세요.');
  }
  document.querySelector(".img__btn").addEventListener("click", function () {
    document.querySelector(".cont").classList.toggle("s--signup");
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

  document.getElementById("submit1")?.addEventListener("click", function (event) {
    event.preventDefault();
    handleFormSubmission("loginForm1");
  });

  document.getElementById("submit2")?.addEventListener("click", function (event) {
    event.preventDefault();
    handleFormSubmission("loginForm2");
  });

  document.getElementById("submit3")?.addEventListener("click", function (event) {
    event.preventDefault();
    handleFormSubmission("loginForm3");
  });
});



// <!--// git push를 위한 주석 241210 10:45-->