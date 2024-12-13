let currentQuestion = 0; // 현재 질문 인덱스
const userAnswers = []; // 사용자의 선택을 저장
let questions = []; // 서버에서 전달된 질문 데이터
const container = document.querySelector(".container");

// 페이지 로드 시 사용자 이름 가져오기
let userName = "Anonymous"; // 기본값
async function loadUserName() {
    try {
        const response = await fetch("/aipage/username", { // 서버에서 사용자 이름을 가져오는 API
            method: "GET",
            headers: {
                "Content-Type": "application/json"
            }
        });
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        const data = await response.json();
        userName = data.name || "Anonymous"; // 사용자 이름을 변수에 저장
        console.log(`사용자 이름 로드 성공: ${userName}`);
    } catch (error) {
        console.error("사용자 이름 로드 실패:", error);
    }
}

// 질문 데이터를 서버에서 로드
async function loadQuestions() {
    try {
        const response = await fetch("/aipage/start", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            }
        });
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        questions = await response.json(); // 서버에서 전달된 질문 데이터를 저장
        console.log("질문 데이터 로드 성공:", questions);
    } catch (error) {
        console.error("질문 데이터 로드 실패:", error);
    }
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
    container.style.backgroundImage = "url('/img/AiQuestionImg.jpg')";
    document.getElementById("question-title").innerText = current.text;
    document.getElementById("option1").innerText = current.a;
    document.getElementById("option2").innerText = current.b;
}

// 답변 선택
function selectOption(optionIndex) {
    userAnswers.push(optionIndex === 0 ? "a" : "b"); // A 또는 B 저장
    currentQuestion++;
    showQuestion();
}

// 서버로 답변 전송
async function sendAnswers() {
    try {
        const response = await fetch("/aipage/submit", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(userAnswers)
        });
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        const data = await response.json();
        showResult(data); // 서버에서 반환된 결과로 화면 표시
    } catch (error) {
        console.error("결과 전송 실패:", error);
    }
}

// 결과 표시
function showResult(data) {
    document.getElementById("question-page").classList.add("hidden");
    document.getElementById("result-page").classList.remove("hidden");

    const city = data.city || {};
    const username = data.name || userName || "Anonymous";

    if (city.cityName && city.cityNation && city.cityImg) {
        container.style.backgroundImage = `url('/img/${city.cityImg}')`;
        const resultText = `${username}님의 추천여행지로는 \n ${city.cityNation}의\u00A0 ${city.cityName}을(를) 추천합니다.\n`;
        document.getElementById("result-text").innerText = resultText;
    } else {
        document.getElementById("result-text").innerText = `${username}님의 추천 여행지를 찾을 수 없습니다.`;
    }
}

// Home 버튼 클릭 시 메인 페이지로 이동
function goToMain() {
    window.location.href = "/aipage/aipage"; // 메인 페이지 경로로 이동
}

// 페이지 로드 시 질문 데이터와 사용자 이름 로드
window.onload = async function () {
    await loadUserName();
    await loadQuestions();
    container.style.backgroundImage = "url('/img/RomeImg.jpg')";
};
