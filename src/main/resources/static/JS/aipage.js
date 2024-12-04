let currentQuestion = 0; // 현재 질문 인덱스
const userAnswers = []; // 사용자의 선택을 저장
let questions = []; // 서버에서 전달된 질문 데이터
const container = document.querySelector(".container");

// 질문 데이터를 서버에서 로드
function loadQuestions() {
    return fetch("/aipage/start", {
        method: "GET",
        headers: {
            "Content-Type": "application/json"
        }
    })
        .then(response => response.json())
        .then(data => {
            questions = data; // 서버에서 전달된 질문 데이터를 저장
            console.log("질문 데이터 로드 성공:", questions);
        })
        .catch(error => console.error("질문 데이터 로드 실패:", error));
}

// 테스트 시작
function startTest() {
    currentQuestion = 0; // 초기화
    userAnswers.length = 0; // 이전 답변 초기화

    document.getElementById("start-page").classList.add("hidden");
    document.getElementById("question-page").classList.remove("hidden");

    showQuestion();
}

// 현재 질문 표시
function showQuestion() {
    if (currentQuestion >= questions.length) {
        sendAnswers(); // 답변을 서버로 전송
        return;
    }

    const current = questions[currentQuestion];
    document.getElementById("question-title").innerText = current.text;
    document.getElementById("option1").innerText = current.A;
    document.getElementById("option2").innerText = current.B;
}

// 답변 선택
function selectOption(optionIndex) {
    userAnswers.push(optionIndex === 0 ? "A" : "B"); // A 또는 B 저장
    currentQuestion++;
    showQuestion();
}

// 서버로 답변 전송
function sendAnswers() {
    fetch("/aipage/submit", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ answers: userAnswers })
    })
        .then(response => response.json())
        .then(data => {
            showResult(data); // 서버에서 반환된 결과로 화면 표시
        })
        .catch(error => console.error("결과 전송 실패:", error));
}

// 결과 표시
function showResult(city) {
    document.getElementById("question-page").classList.add("hidden");
    document.getElementById("result-page").classList.remove("hidden");

    const resultText = `추천 도시: ${city.cityName}\n대륙: ${city.cityContinent}\n언어: ${city.cityLanguage}`;
    document.getElementById("result-text").innerText = resultText;
}

// Home 버튼 클릭 시 메인 페이지로 이동
function goToMain() {
    window.location.href = "/"; // 메인 페이지 경로로 이동
}

// 페이지 로드 시 질문 데이터 로드
window.onload = async function () {
    await loadQuestions();
};