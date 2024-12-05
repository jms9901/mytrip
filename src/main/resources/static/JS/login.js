document.addEventListener("DOMContentLoaded", function () {
  document.querySelector(".img__btn").addEventListener("click", function () {
    document.querySelector(".cont").classList.toggle("s--signup");
  });

  function showForm(type) {
    document.getElementById('loginForm2').classList.add('hidden');
    document.getElementById('loginForm3').classList.add('hidden');

    if (type === 'member') {
      document.getElementById('loginForm2').classList.remove('hidden');
    } else if (type === 'business') {
      document.getElementById('loginForm3').classList.remove('hidden');
      document.getElementById('business').classList.remove('hidden');
    }
  }

  document.getElementById("member").addEventListener("click", function () {
    showForm('member');
  });

  document.getElementById("business").addEventListener("click", function () {
    showForm('business');
  });

  document.getElementById("submit1").addEventListener("click", function (event) {
    event.preventDefault();
    const form = document.getElementById("loginForm1");
    const inputs = form.querySelectorAll("input");
    let check = true;

    inputs.forEach((element) => {
      if (element.value.trim() === "") {
        check = false;
      }
    });

    if (check) {
      form.submit();
    }
  });

  document.getElementById("submit2").addEventListener("click", function (event) {
    event.preventDefault();
    const form = document.getElementById("loginForm2");  // ID 수정
    const inputs = form.querySelectorAll("input");
    let check = true;

    inputs.forEach((element) => {
      if (element.value.trim() === "") {
        check = false;
      }
    });

    if (check) {
      form.submit();
    }
  });

  document.getElementById("submit3").addEventListener("click", function (event) {
    event.preventDefault();
    const form = document.getElementById("loginForm3");  // ID 수정
    const inputs = form.querySelectorAll("input");
    let check = true;

    inputs.forEach((element) => {
      if (element.value.trim() === "") {
        check = false;
      }
    });

    if (check) {
      form.submit();
    }
  });
});
