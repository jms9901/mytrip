const currentUrl = window.location.pathname;  // 예시: "/mypage/1"
const userId = parseInt(currentUrl.substring(currentUrl.lastIndexOf('/') + 1)); // 문자열을 숫자로 변환
let ownUser =false;
document.addEventListener('DOMContentLoaded', function () {
    const currentUrl = window.location.pathname;
    const userId = parseInt(currentUrl.substring(currentUrl.lastIndexOf('/') + 1));

    const cityLikedContainer = document.querySelector('.mypageCityLiked');
    const slider = cityLikedContainer.querySelector('.slider');
    const prevButton = cityLikedContainer.querySelector('.prev');
    const nextButton = cityLikedContainer.querySelector('.next');

    let currentSlide = 0;
    function goToPage() {
        var url = '/mypage/bookMain/' + userId;
        window.location.href = url;
    }

    let loguser;  // 전역 변수로 선언



    fetch('loginuserid')
        .then(response => response.json())
        .then(data => {
            loguser = data.userId;  // Assign the userId to loguser

            console.log('User ID:', loguser); // Output the loguser value
            let ownUser = (loguser === userId);
            // Add event listener to the form after loguser is set
            document.getElementById("sendFriendRequestForm").addEventListener("submit", (event) => {
                event.preventDefault();
                const params = new URLSearchParams({
                    fromUserId: loguser,  // Use the fetched loguser here
                    toUserId: userId      // Make sure UserId is available in the scope
                });
                ownUser = (loguser===userId);
                console.log(ownUser);
                sendRequest("send", params);
            });

        })
        .catch(error => console.error('Error:', error));


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
// 친구 요청 수락/거절 처리 함수
function handleFriendRequest(action, fromUserId, toUserId, requestItem) {
    const params = new URLSearchParams({
        fromUserId: loguser,  // 로그인 사용자 (fromUserId)
        toUserId: toUserId       // 친구 요청을 받는 사용자 (toUserId)
    });

    fetch(`/friendship/${action}?${params}`, { method: "POST" })
        .then(response => response.text())
        .then(result => {
            alert(result);  // 결과 메시지 출력

            // 요청이 성공하면 해당 항목 삭제
            if (action === "accept" || action === "reject") {
                requestItem.remove();  // 해당 요청 항목 삭제
            }
        })
        .catch(error => console.error(`Error processing ${action} request:`, error));
}




// 데이터 먼저 로드
function loadFriendRequests() {
    fetch(`/mypage/requestList/${userId}`)
        .then(response => response.json())
        .then(data => {
            const requestView = document.getElementById('requestView');
            const friendListElement = document.getElementById('friendList');
            friendListElement.innerHTML = ''; // 기존 목록 초기화

            if (data.length > 0 && ownUser) {
                requestView.style.display = 'flex'; // 데이터가 있으면 표시

                data.forEach(friendshipUserResultMap => {
                    const li = document.createElement('li');

                    // 프로필 이미지 경로 설정
                    const profileImage = friendshipUserResultMap.user.profile
                        ? `/uploads/profiles/${friendshipUserResultMap.user.profile}`
                        : '/img/defaultProfile.jpg';

                    // 이름 설정
                    const userName = friendshipUserResultMap.user.user_name || 'Unknown';

                    // 수락 버튼 생성
                    const acceptButton = document.createElement('button');
                    acceptButton.textContent = '수락';
                    acceptButton.classList.add('accept-btn');
                    acceptButton.onclick = () => {
                        acceptFriendRequest(friendshipUserResultMap.fromUserId, friendshipUserResultMap.toUserId);
                    };

                    // 거절 버튼 생성
                    const rejectButton = document.createElement('button');
                    rejectButton.textContent = '거절';
                    rejectButton.classList.add('reject-btn');
                    rejectButton.onclick = () => {
                        rejectFriendRequest(friendshipUserResultMap.fromUserId, friendshipUserResultMap.toUserId);
                    };

                    // 버튼들을 감싸는 컨테이너 생성
                    const buttonContainer = document.createElement('div');
                    buttonContainer.classList.add('button-container');
                    buttonContainer.appendChild(acceptButton);
                    buttonContainer.appendChild(rejectButton);

                    // 목록 생성
                    li.innerHTML = `
                        <img src="${profileImage}" alt="Profile" class="friend-profile-img">
                        <span class="friend-name">${userName}</span>
                    `;

                    // 수락/거절 버튼을 목록 항목에 추가
                    li.appendChild(buttonContainer);

                    // 버튼 추가 후 확인을 위해 로그 출력
                    console.log("수락 버튼:", acceptButton);
                    console.log("거절 버튼:", rejectButton);

                    friendListElement.appendChild(li);
                });
            } else {
                requestView.style.display = 'none'; // 데이터가 없으면 숨김
            }
        })
        .catch(error => console.error('Error fetching friend list:', error));
}


// 친구 요청 수락
function acceptFriendRequest(fromUserId, toUserId) {
    const params = new URLSearchParams({
        fromUserId: fromUserId, // 요청 보낸 사람
        toUserId: toUserId, // 로그인 사용자
    });

    sendRequest("accept", params);
}

// 친구 요청 거절
function rejectFriendRequest(fromUserId, toUserId) {
    const params = new URLSearchParams({
        fromUserId: fromUserId, // 요청 보낸 사람
        toUserId: toUserId, // 로그인 사용자
    });

    sendRequest("reject", params);
}
// 클릭 이벤트 등록
document.getElementById('requestView').addEventListener('click', function () {
    document.getElementById('friendModal').style.display = 'block';
});

// 페이지 로드 시 데이터 로드
window.onload = loadFriendRequests;


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
            sendRequest(profileImageBase64);  // base64 변환 후 서버에 요청 보내기
        };
        reader.readAsDataURL(profileImage);  // base64로 변환
    } else {
        sendRequest();  // 프로필 이미지가 없으면 바로 서버로 요청
    }

    // 요청 보내는 함수
    function sendRequest(profileImageBase64) {
        const formData = {
            userId: userId,
            introduction: introduction,
            currentPassword: currentPassword,
            newPassword: newPassword,
            profileImage: profileImageBase64 // base64로 변환된 프로필 이미지 전달
        };

        // 서버로 POST 요청
        fetch(`/mypage/update/${userId}`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(formData)  // formData를 JSON으로 전송
        })
            .then(response => response.json())
            .then(data => {
                console.log("Success:", data);
                alert("수정 완료!");
                // 응답 데이터에 따라 추가 작업 수행
            })
            .catch(error => {
                console.error("Error:", error);
                alert("수정 실패! 비밀번호 확인해주세요");
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

    let loguser;  // 전역 변수로 선언
    const currentUrl = window.location.pathname;  // 예시: "/mypage/1"
    const userId = parseInt(currentUrl.substring(currentUrl.lastIndexOf('/') + 1)); // 문자열을 숫자로 변환


    fetch('loginuserid')
        .then(response => response.json())
        .then(data => {
            loguser = data.userId;
            ownUser = (loguser === userId);
            console.log(ownUser);

            $('.flipbook').bind('turning', function (event, page) {
                if (page === 2) {
                    $('.menu2').show();
                    $('.menu3').show();
                    $('.menu4').show();
                    $('.menu5').show();
                    if (ownUser) {
                        $('.menu1').show(); // Show menu after the first page turn
                    }
                } else if (page === 1) {
                    $('.menu1').hide();
                    $('.menu2').hide();
                    $('.menu3').hide();
                    $('.menu4').hide();
                    $('.menu5').hide();
                }
            });

            if(!ownUser){
                $('.requestView').hide();
                $('.myPageUpdate').hide();
            }else{
                $('#freindrequestbtn').hide();
            }
        })
        .catch(error => console.error('Error:', error));
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
// URL에서 toUserId 값 추출하는 함수
function getToUserIdFromUrl() {
    const pathParts = window.location.pathname.split('/');
    const UserId = pathParts[pathParts.length - 1]; // 마지막 부분이 toUserId
    return UserId;
}

// 페이지 로드 시 AJAX 호출
$(document).ready(function() {
    const userId = getToUserIdFromUrl();
    const apiUrl = `/mypage/recentfeed/${userId}`; // API URL 설정

    // 페이지 로드 시 AJAX 호출
    function loadFeeds() {
        $.ajax({
            url: apiUrl,
            type: 'GET',
            dataType: 'json',
            success: function(feeds) {
                console.log("피드 데이터:", feeds);  // 데이터 확인
                loadFeed(feeds);
            },
            error: function(xhr, status, error) {
                console.error("피드 로딩 실패:", error);
            }
        });
    }

    // 피드를 페이지에 추가
    function loadFeed(feeds) {
        if (feeds && feeds.length > 0) {
            let rowHtml = '';
            let count = 0;

            feeds.forEach((feed, index) => {
                let attachmentHtml = '';

                // 첨부파일이 있을 경우 처리
                if (feed.attachmentFiles && feed.attachmentFiles.length > 0) {
                    attachmentHtml = feed.attachmentFiles.map(file => {
                        const filePath = `/uploads/${file}`; // 첨부파일 경로
                        return `<img src="${filePath}" alt="첨부파일" class="feed-attachment">`;
                    }).join('');
                }

                const entryHtml = `
                    <div class="feed-entry" style="background-image: url(/img/postFrame.png)" data-index="${index}">
                        ${attachmentHtml}
                        <div>${feed.boardSubject}</div>
                    </div>
                `;

                // 3개씩 묶어서 하나의 행으로 추가
                rowHtml += entryHtml;
                count++;

                if (count % 3 === 0) {
                    // 3개씩 묶어서 feedEntries div에 추가
                    $('.feedEntries').append(`<div class="row" style="display:flex; gap:10px; justify-content:center;">${rowHtml}</div>`);
                    rowHtml = ''; // rowHtml 초기화
                }
            });

            // 남은 항목이 3개 미만일 경우 처리
            if (rowHtml !== '') {
                $('.feedEntries').append(`<div class="row" style="display:flex; gap:10px; justify-content:center;">${rowHtml}</div>`);
            }
        }
    }

    // 페이지 로드 시 피드 데이터 로드
    loadFeeds();

    $('.myPageGoHome').on('click', function() {

        window.location.href = '/mypage/' + userId;
    });
    $('.menu1').on('click', function() {

        window.location.href = '/bookmain/payment/list/' + userId;
    });
    $('.menu2').on('click', function() {

        window.location.href = '/mypage/feed/list/' + userId;
    });
    $('.menu3').on('click', function() {

        window.location.href = '/mypage/feedliked/' + userId;
    });
    $('.menu4').on('click', function() {

        window.location.href = '/mypage/packageliked/' + userId;
    });
    $('.menu5').on('click', function() {

        window.location.href = '/mypage/bookMain/bookGuestBook/' + userId;
    });
    $('.goChat').on('click', function() {

        window.location.href = '/chat';
    });


});
const apiUrl = "http://localhost:8081/friendship";
const UserId = pathParts[pathParts.length - 1];
// 모달 열기 및 닫기
const modal = document.getElementById("friendshipModal");
document.getElementById("openModalBtn").addEventListener("click", () => {
    modal.style.display = "block";
});
document.getElementById("closeModalBtn").addEventListener("click", () => {
    modal.style.display = "none";
});

// API 요청 함수
async function sendRequest(endpoint, params) {
    const response = await fetch(`${apiUrl}/${endpoint}?${params}`, { method: "POST" });
    const result = await response.text();
    alert(result);
}

// // 친구 요청 보내기
// document.getElementById("sendFriendRequestForm").addEventListener("submit", (event) => {
//     event.preventDefault();
//     const params = new URLSearchParams({
//         fromUserId: loguser,    //로그인한 사용자
//         toUserId: UserId
//     });
//     sendRequest("send", params);
// });

// 친구 요청 수락
document.getElementById("acceptFriendRequestForm").addEventListener("submit", (event) => {
    event.preventDefault();
    const params = new URLSearchParams({
        fromUserId: document.getElementById("acceptFromUserId").value,  //로그인 사용자
        toUserId: UserId
    });
    sendRequest("accept", params);
});

// 친구 요청 거절
document.getElementById("rejectFriendRequestForm").addEventListener("submit", (event) => {
    event.preventDefault();
    const params = new URLSearchParams({
        fromUserId: document.getElementById("rejectFromUserId").value,  //로그인 사용자
        toUserId: UserId
    });
    sendRequest("reject", params);
});
