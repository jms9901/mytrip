$(document).ready(function () {
    const apiUrl = "/mypage"; // API URL 설정
    const currentUrl = window.location.pathname;  // 예시: "/mypage/1"
    const userId = parseInt(currentUrl.substring(currentUrl.lastIndexOf('/') + 1)); // 문자열을 숫자로 변환

    // 피드 페이지 로드 시 피드 목록 불러오기
    function loadFeedList() {
        $.ajax({
            url: `${apiUrl}/{userId}`,
            method: 'GET',
            success: function (feeds) {
                const feedEntries = $('#feed-Entries');
                feedEntries.empty();

                feeds.forEach(feed => {
                    const feedCard = `
                        <div class="feed-card" data-id="${feed.boardId}">
                            <div class="photo-container">
                                ${feed.thumbnail ? `<img src="${feed.thumbnail}" alt="postThumbnail" class="post-thumbnail">` : ''}
                            </div>
                            <div class="feed-title">
                                <h4><b>${feed.boardSubject}</b></h4>
                            </div>
                        </div>
                    `;
                    $('#feed-Entries').append(feedCard);
                });
            },
            error: function () {
                alert("피드를 로딩하는데 실패했습니다.");
            }
        });
    }

    // 피드 작성 모달
    $(document).ready(function() {
        // feedAdd-Button 클릭 시 모달 열기
        $('#feedAdd-Button').click(function() {
            $('#feed-modal').removeClass('hidden'); // 모달을 보이게 설정
            $('body').css('overflow', 'hidden'); // 모달 열릴 때 배경 스크롤 비활성화
        });

        // 모달 밖을 클릭 시 모달 닫기
        $(document).click(function(event) {
            if ($(event.target).closest('#feed-modal').length === 0 && !$(event.target).is('#feedAdd-Button')) {
                $('#feed-modal').addClass('hidden'); // 모달을 숨김
                $('body').css('overflow', 'auto'); // 배경 스크롤 활성화
            }
        });

        // 폼 제출 시 피드 작성 데이터 처리
        $('#feed-modal form').submit(function(event) {
            event.preventDefault(); // 기본 제출 동작 방지

            var formData = new FormData(this);

            $.ajax({
                url: '/mypage/feed/write', // 서버로 폼 데이터 전송
                method: 'POST',
                data: formData,
                contentType: false,
                processData: false,
                success: function(response) {
                    alert("성공적으로 피드를 작성하였습니다!");
                    $('#feed-modal').addClass('hidden');
                    $('body').css('overflow', 'auto');
                    loadFeedList(); // 피드 리스트 갱신
                },
                error: function() {
                    alert("피드를 작성하는데 실패하였습니다.");
                }
            });
        });
    });

// 피드 목록을 로드할 때 피드 카드 클릭 시 상세보기로 이동
    $('#feed-Entries').on('click', '.feed-card', function() {
        const feedId = $(this).data('id'); // feedId를 데이터 속성으로 가져옴
        window.location.href = `/mypage/feedDetail.html?feedId=${feedId}`;
    });

    // 게시판으로 이동
    $('#view-board-button').click(function() {
        window.location.href = '/mypage/feed.html';
    });

    // 수정 버튼 클릭
    $('#edit-button').click(function() {
        window.location.href = `/mypage/feedUpdate.html?feedId=${feed.boardId}`;
    });

    // 삭제 버튼 클릭
    $('#delete-button').click(function() {
        if (confirm("정말 삭제하시겠습니까?")) {
            $.ajax({
                url: `mypage/feed/delete`,
                method: 'POST',
                success: function () {
                    alert("피드가 삭제되었습니다.");
                    $('#feed-detail-modal').addClass('hidden');
                    loadFeedList(); // 목록 갱신
                },
                error: function () {
                    alert("삭제에 실패했습니다.");
                }
            });
        }
    });
    // 피드 목록 불러오기
    loadFeedList();
});






