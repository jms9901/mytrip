$(document).ready(function () {
    // 현재 URL에서 boardId 추출
    const pathParts = window.location.pathname.split("/");
    const boardId = pathParts[pathParts.length - 1]; // URL의 마지막 값이 boardId라고 가정

    console.log("boardId: ", boardId); // 디버깅용 확인

    // 페이지 로드 시 댓글 불러오기
    fetchComments();

    // 댓글 목록 불러오기
    function fetchComments() {
        $.ajax({
            url: `/comments/${boardId}`, // 서버에서 댓글 + 게시글 작성자 ID + 로그인한 사용자 ID 가져오기
            method: "GET",
            success: function (response) {
                const comments = response.comments; // 댓글 목록
                const boardWriterId = response.boardWriterId; // 게시글 작성자 ID
                const loggedInUserId = response.loggedInUserId; // 로그인된 사용자 ID
                console.log("loggedInUserId:", loggedInUserId, "boardWriterId:", boardWriterId); // 디버깅용

                renderComments(comments, boardWriterId, loggedInUserId); // 댓글 렌더링
            },
            error: function () {
                alert("댓글을 불러오는 데 실패했습니다."); // 서버 요청 실패 처리
            }
        });
    }

    // 댓글 목록을 화면에 표시하는 함수
    function renderComments(comments, boardWriterId, loggedInUserId) {
        const commentsList = $("#commentsList");
        commentsList.empty(); // 기존 댓글 목록 비우기

        if (comments.length === 0) {
            commentsList.html("<p class='text-muted'>등록된 댓글이 없습니다.</p>");
            return;
        }

        comments.forEach(comment => {
            // 댓글 삭제 조건: 로그인한 사용자 === 댓글 작성자 OR 게시글 작성자
            const canDelete = (loggedInUserId === comment.userId) || (loggedInUserId === boardWriterId);
            console.log("CommentId:", comment.commentId, "Can Delete:", canDelete); // 디버깅용 확인

            // KST로 시간 변환
            const rawDate = new Date(comment.date);
            const kstDate = new Date(rawDate.getTime() + 9 * 60 * 60 * 1000); // UTC → KST 변환
            const formattedDate = kstDate.toISOString().slice(0, 16).replace("T", " ");

            // 댓글 HTML 생성
            const commentHtml = `
                <div class="border p-2 mb-2">
                    <div class="d-flex justify-content-between align-items-center">
                        <p class="mb-0">
                            <strong>${comment.userName}</strong>: ${comment.content}
                        </p>
                        <div>
                            <span class="text-muted" style="margin-right: 10px;">${formattedDate}</span>
                            ${canDelete ? `
                                <button class="btn btn-danger btn-sm deleteCommentBtn" data-comment-id="${comment.commentId}">
                                    삭제
                                </button>` : ''}
                        </div>
                    </div>
                </div>
            `;
            commentsList.append(commentHtml);
        });
    }

    // 댓글 등록 버튼 클릭 이벤트
    $("#addCommentBtn").on("click", function () {
        const content = $("#commentInput").val().trim();

        if (!content) {
            alert("댓글 내용을 입력하세요.");
            return;
        }

        $.ajax({
            url: `/comments/write`, // 서버에 댓글 등록 요청
            method: "POST",
            contentType: "application/json",
            data: JSON.stringify({
                boardId: boardId, // 현재 게시글 ID
                content: content // 댓글 내용
            }),
            success: function () {
                $("#commentInput").val(""); // 입력창 비우기
                fetchComments(); // 댓글 목록 다시 불러오기
            },
            error: function () {
                alert("댓글 등록에 실패했습니다.");
            }
        });
    });

    // 댓글 삭제 버튼 클릭 이벤트
    $(document).on("click", ".deleteCommentBtn", function () {
        const commentId = $(this).data("comment-id");

        if (!confirm("정말 삭제하시겠습니까?")) return; // 삭제 확인 경고

        $.ajax({
            url: `/comments/delete/${commentId}`, // 서버에 댓글 삭제 요청
            method: "DELETE",
            success: function () {
                fetchComments(); // 댓글 목록 다시 불러오기
            },
            error: function () {
                alert("댓글 삭제에 실패했습니다.");
            }
        });
    });
});
