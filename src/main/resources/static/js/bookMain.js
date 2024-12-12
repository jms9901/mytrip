const currentUrl = window.location.pathname;  // 예시: "/mypage/1"
const userId = parseInt(currentUrl.substring(currentUrl.lastIndexOf('/') + 1)); // 문자열을 숫자로 변환

document.addEventListener('DOMContentLoaded', function () {
    const currentUrl = window.location.pathname;
    const userId = parseInt(currentUrl.substring(currentUrl.lastIndexOf('/') + 1));

    const cityLikedContainer = document.querySelector('.mypageCityLiked');
    const slider = cityLikedContainer.querySelector('.slider');
    const prevButton = cityLikedContainer.querySelector('.prev');
    const nextButton = cityLikedContainer.querySelector('.next');

    let currentSlide = 0;

    // 도시 좋아요 목록을 가져오는 API 호출
    fetch(`/likey/likedCity?userId=${userId}`)
        .then(response => response.json())
        .then(data => {
            if (!cityLikedContainer) {
                console.error("City liked container not found.");
                return;
            }

            slider.innerHTML = ''; // 기존 내용 초기화

            if (data.length > 0) {
                data.forEach(city => {
                    const cityName = city.cityName || 'Unknown City';
                    const cityImg = city.cityImg ? `/img/${city.cityImg}` : '/img/defaultCity.jpg';

                    const cityItem = document.createElement('div');
                    cityItem.classList.add('cityLikedItem');

                    const cityTitle = document.createElement('div');
                    cityTitle.classList.add('CityLikedTitle');
                    cityTitle.textContent = cityName;
                    cityItem.appendChild(cityTitle);

                    const cityImgContainer = document.createElement('div');
                    cityImgContainer.classList.add('CityLikedImg');
                    const cityImage = document.createElement('img');
                    cityImage.src = cityImg;
                    cityImage.alt = cityName;
                    cityImgContainer.appendChild(cityImage);
                    cityItem.appendChild(cityImgContainer);

                    slider.appendChild(cityItem);
                });
            } else {
                const noCityMsg = document.createElement('div');
                noCityMsg.textContent = "No cities liked yet.";
                slider.appendChild(noCityMsg);
            }

            function moveSlide() {
                const slideWidth = slider.querySelector('.cityLikedItem').offsetWidth;
                slider.style.transform = `translateX(-${currentSlide * slideWidth}px)`;
            }

            nextButton.addEventListener('click', function() {
                const totalSlides = slider.children.length;
                if (currentSlide < totalSlides - 1) {
                    currentSlide++;
                    moveSlide();
                }
            });

            prevButton.addEventListener('click', function() {
                if (currentSlide > 0) {
                    currentSlide--;
                    moveSlide();
                }
            });
        })
        .catch(error => console.error('Error fetching liked cities:', error));
});

document.getElementById('ConnectionsCnt').addEventListener('click', function () {
    fetch(`/mypage/friendList/${userId}`)
        .then(response => response.json())
        .then(data => {
            const friendListElement = document.getElementById('friendList');
            friendListElement.innerHTML = ''; // 기존 목록 초기화

            if (data.length > 0) {
                data.forEach(friendshipUserResultMap => {
                    const li = document.createElement('li');

                    // 프로필 이미지 경로 설정 (profile이 없으면 defaultProfile.jpg 사용)
                    const profileImage = friendshipUserResultMap.user.profile ? `/uploads/profiles/${friendshipUserResultMap.user.profile}` : '/img/defaultProfile.jpg';

                    // name이 null일 경우 "Unknown"으로 처리
                    const userName = friendshipUserResultMap.user.user_name || 'Unknown';

                    // 프로필 이미지와 유저 이름 표시
                    li.innerHTML = `
                        <img src="${profileImage}" alt="Profile" class="friend-profile-img">
                        <span class="friend-name">${userName}</span>
                    `;
                    friendListElement.appendChild(li);
                });
            } else {
                const noFriendsMsg = document.createElement('li');
                noFriendsMsg.textContent = "No friends found.";
                friendListElement.appendChild(noFriendsMsg);
            }

            // 모달 열기
            document.getElementById('friendModal').style.display = 'block';
        })
        .catch(error => console.error('Error fetching friend list:', error));
});




function closeModal() {
    document.getElementById('friendModal').style.display = 'none'; // 모달 닫기
}

// 소개글 수정
function updateIntroduction() {
    const introElement = document.getElementById("userIntroduction");
    const editInput = document.getElementById("editIntroduction");

    editInput.style.display = "inline";
    introElement.style.display = "none";
    editInput.value = introElement.textContent.trim();

    editInput.addEventListener("blur", () => {
        if (editInput.value.trim()) {
            introElement.textContent = editInput.value.trim();
        }
        editInput.style.display = "none";
        introElement.style.display = "inline";
    }, {once: true});
}

// 비밀번호 수정
function updatePassword(type) {
    const passwordText = document.getElementById(type + "Text");
    const passwordInput = document.getElementById(type);

    passwordText.style.display = "none";
    passwordInput.style.display = "inline";
    passwordInput.focus();

    passwordInput.addEventListener("blur", () => {
        if (passwordInput.value.trim()) {
            passwordText.textContent = "*".repeat(passwordInput.value.length);
        }
        passwordText.style.display = "inline";
        passwordInput.style.display = "none";
    }, {once: true});
}

// 프로필 이미지 미리보기
function previewProfileImage() {
    const fileInput = document.getElementById("profileImageInput");
    const profilePreview = document.getElementById("profilePreview");

    if (fileInput.files && fileInput.files[0]) {
        const reader = new FileReader();
        reader.onload = function (e) {
            profilePreview.src = e.target.result;
            profilePreview.style.display = "block";
        };
        reader.readAsDataURL(fileInput.files[0]);
    }
}

// 변경 사항 제출
function submitUserChanges() {
    const introduction = document.getElementById("userIntroduction").textContent.trim();
    const currentPassword = document.getElementById("currentPassword").value.trim();
    const newPassword = document.getElementById("newPassword").value.trim();
    const profileImage = document.getElementById("profileImageInput").files[0];

    // URL에서 userId 추출
    const currentUrl = window.location.pathname;  // 예시: "/mypage/1"
    const userId = parseInt(currentUrl.substring(currentUrl.lastIndexOf('/') + 1)); // 문자열을 숫자로 변환

    // profileImage가 있을 경우 base64로 변환
    let profileImageBase64 = null;
    if (profileImage) {
        const reader = new FileReader();
        reader.onloadend = function () {
            profileImageBase64 = reader.result.split(',')[1]; // base64 부분만 추출
            // 데이터를 콘솔에 출력
            console.log("User Data to Send:");
            console.log("User ID:", userId);
            console.log("Introduction:", introduction);
            console.log("Current Password:", currentPassword);
            console.log("New Password:", newPassword);
            console.log("Profile Image Base64:", profileImageBase64);
            sendRequest(profileImageBase64);  // base64 변환 후 서버에 요청 보내기
        };
        reader.readAsDataURL(profileImage);  // base64로 변환
    } else {
        sendRequest();  // 프로필 이미지가 없으면 바로 서버로 요청
    }

    // 요청 보내는 함수
    function sendRequest() {
        const formData = {
            userId: userId,
            introduction: introduction,
            currentPassword: currentPassword,
            newPassword: newPassword,
            profileImage: profileImageBase64 // base64로 변환된 프로필 이미지 전달
        };

        // 서버로 POST 요청
        fetch("/mypage/update/" + userId, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(formData)
        })
            .then(response => response.json())
            .then(data => {
                console.log("Success:", data);
            })
            .catch(error => {
                console.error("Error:", error);
            });
    }
}


function loadApp() {

    // Create the flipbook

    $('.flipbook').turn({
        // Width

        width: 922,

        // Height

        height: 600,

        // Elevation

        elevation: 50,

        // Enable gradients

        gradients: true,

        // Auto center this flipbook

        autoCenter: true

    });
    $('.flipbook').bind('turning', function (event, page) {
        if (page === 2) {
            $('.menu1').show(); // Show menu after the first page turn
            $('.menu2').show();
            $('.menu3').show();
            $('.menu4').show();
            $('.menu5').show();
        } else if (page === 1) {
            $('.menu1').hide();
            $('.menu2').hide();
            $('.menu3').hide();
            $('.menu4').hide();
            $('.menu5').hide();
        }
    });
    $('.goChat').hover(function () {
        hoverChat();
    });


    function hoverChat() {
        let angle = 0;
        clearInterval(shakeInterval);
        $('.goChat').css('transform', 'rotateY(0deg)');  // 초기 상태 설정
        let shakeInterval = setInterval(function () {
            angle += 10;  // 한 번에 10도씩 증가
            $('.goChat').css('transform', `rotateY(${angle}deg)`);  // Y축을 중심으로 회전
            if (angle >= 35) {  // 35도까지 회전
                clearInterval(shakeInterval);
                $('.goChat').css('transform', 'rotateY(0deg)');  // 원래 위치로 돌아가게 설정
            }
        }, 100);  // 0.1초마다 회전
    }

}

$('.myPageUpdate').click(function () {
    $('.updateUserModal').show();
});
$('.closeUpdateModal').click(function () {
    $('.updateUserModal').hide();
});


// Load the HTML4 version if there's not CSS transform

function isCSSTransformSupported() {
    var testElement = document.createElement('div');
    var transformProperties = ['transform', 'WebkitTransform', 'MozTransform', 'OTransform', 'msTransform'];

    for (var i = 0; i < transformProperties.length; i++) {
        if (testElement.style[transformProperties[i]] !== undefined) {
            return true; // CSS transform이 지원됩니다.
        }
    }

    return false; // CSS transform이 지원되지 않습니다.
}

if (isCSSTransformSupported()) {
    // transform 지원 시
    $.getScript('../../lib/animationjs/turn.js', function () {
        loadApp();
    });
} else {
    // transform 미지원 시
    $.getScript('../../lib/JQuery/turn.html4.min.js', function () {
        loadApp();
    });
}


function addPage(page, book) {

    var id, pages = book.turn('pages');

    // Create a new element for this page
    var element = $('<div />', {});

    // Add the page to the flipbook
    if (book.turn('addPage', element, page)) {

        // Add the initial HTML
        // It will contain a loader indicator and a gradient
        element.html('<div class="gradient"></div><div class="loader"></div>');

        // Load the page
        loadPage(page, element);
    }

}

function loadPage(page, pageElement) {

    // Create an image element

    var img = $('<img />');

    img.mousedown(function (e) {
        e.preventDefault();
    });

    img.load(function () {

        // Set the size
        $(this).css({width: '100%', height: '100%'});

        // Add the image to the page after loaded

        $(this).appendTo(pageElement);

        // Remove the loader indicator

        pageElement.find('.loader').remove();
    });

    // Load the page

    img.attr('src', 'pages/' + page + '.jpg');

}


function loadLargePage(page, pageElement) {

    var img = $('<img />');

    img.load(function () {

        var prevImg = pageElement.find('img');
        $(this).css({width: '100%', height: '100%'});
        $(this).appendTo(pageElement);
        prevImg.remove();

    });

    // Loadnew page

    img.attr('src', 'pages/' + page + '-large.jpg');
}


function loadSmallPage(page, pageElement) {

    var img = pageElement.find('img');

    img.css({width: '100%', height: '100%'});

    img.unbind('load');
    // Loadnew page

    img.attr('src', 'pages/' + page + '.jpg');
}


// http://code.google.com/p/chromium/issues/detail?id=128488
function isChrome() {

    return navigator.userAgent.indexOf('Chrome') != -1;

}

