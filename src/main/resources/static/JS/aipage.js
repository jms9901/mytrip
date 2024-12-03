let currentQuestion = 0;
const questions = [
    {
        question: "내가 고르고 싶은 분위기는?",
        options: ["조용하고 한적한 자연", "사람들이 붐비는 활기찬 도시"],
        background: "url('/img/section2img1.jpg')"
    },
    {
        question: "나는 주로 어떤 일을 할 때 행복한가?",
        options: ["혼자 시간을 보내기", "다른 사람들과 어울리기"],
        background: "url('/img/section2img2.jpg')"
    },
    {
        question: "나는 어떤 결정을 내릴 때 중요한가?",
        options: ["논리와 분석", "감정과 직관"],
        background: "url('/img/section2img6.jpg')"
    },
    {
        question: "나의 생각을 표현할 때 어떤 방식이 더 좋은가?",
        options: ["글로 작성하기", "말로 표현하기"],
        background: "url('/img/section2img7.jpg')"
    },
    {
        question: "내가 스트레스를 받을 때 어떻게 대처하는가?",
        options: ["휴식을 취하며 혼자만의 시간을 갖기", "친구들과 이야기를 나누며 해결책 찾기"],
        background: "url('/img/section2img9.jpg')"
    }
];

const startBackground = "url('/img/section2img10.jpg')";
const resultBackground = "url('/img/section2img10.jpg')";
const container = document.querySelector(".container");

window.onload = function(){
    container.style.backgroundImage = startBackground;
}
function startTest() {
    container.style.backgroundImage = questions[0].background;
    document.getElementById("start-page").classList.add("hidden");
    document.getElementById("question-page").classList.remove("hidden");
    showQuestion();
}

function showQuestion() {
    if (currentQuestion >= questions.length) {
        showResult();
        return;
    }
    container.style.backgroundImage = questions[currentQuestion].background;
    document.getElementById("question-title").innerText = questions[currentQuestion].question;
    document.getElementById("option1").innerText = questions[currentQuestion].options[0];
    document.getElementById("option2").innerText = questions[currentQuestion].options[1];
}

function selectOption(option) {
    currentQuestion++;
    showQuestion();
}

function showResult() {
    container.style.backgroundImage = resultBackground;
    document.getElementById("question-page").classList.add("hidden");
    document.getElementById("result-page").classList.remove("hidden");
    document.getElementById("result-text").innerText = "여기에 결과를 입력하세요."; // 결과 로직을 추가하세요.
}

function restartTest() {
    currentQuestion = 0;
    container.style.backgroundImage = startBackground;
    document.getElementById("result-page").classList.add("hidden");
    document.getElementById("start-page").classList.remove("hidden");
}
