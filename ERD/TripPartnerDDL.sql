CREATE TABLE airport
(
    airport_id      INT          NOT NULL AUTO_INCREMENT COMMENT '공항 테이블 ID',
    airport_code    VARCHAR(100) NOT NULL COMMENT '공항 코드',
    airport_name    VARCHAR(100) NOT NULL COMMENT '공항의 이름',
    airport_city    VARCHAR(100) NOT NULL COMMENT '공항의 도시',
    airport_country VARCHAR(100) NOT NULL COMMENT '공항의 나라',
    PRIMARY KEY (airport_id)
) COMMENT '공항';

CREATE TABLE board_comment
(
    board_comment_id INT          NOT NULL AUTO_INCREMENT COMMENT '피드 댓글 테이블 ID',
    user_id          INT          NOT NULL COMMENT '사용자 ID',
    board_id        INT          NOT NULL COMMENT '피드, 소모임 ID',
    comment_content  VARCHAR(100) NOT NULL COMMENT '댓글 내용',
    comment_date     DATETIME     NOT NULL DEFAULT NOW() COMMENT '댓글 작성 일자',
    PRIMARY KEY (board_comment_id)
) COMMENT ' 피드 댓글';


CREATE TABLE board
(
    board_id         INT           NOT NULL AUTO_INCREMENT COMMENT '피드, 소모임 테이블 ID',
    user_id          INT           NOT NULL COMMENT '사용자 ID',
    city_id          INT           NOT NULL COMMENT '도시 ID',
    board_subject    VARCHAR(100)  NOT NULL COMMENT '피드 제목',
    board_content    VARCHAR(1000) NOT NULL COMMENT '파드 내용',
    board_view_count INT           NOT NULL DEFAULT 0 COMMENT '피드 조회수',
    board_date       DATETIME      NOT NULL DEFAULT NOW() COMMENT '피드 게시일',
    board_category   VARCHAR(100)  NOT NULL COMMENT '피드 카테고리',
    PRIMARY KEY (board_id)
) COMMENT '피드, 소모임';

ALTER TABLE board
    ADD CONSTRAINT chk_board_category
        CHECK (board_category IN ('피드', '소모임'));

CREATE TABLE board_attachment
(
    board_attachment_id   INT           NOT NULL AUTO_INCREMENT COMMENT '피드 게시물 테이블 ID',
    board_id              INT           NOT NULL COMMENT '피드. 소모임 ID',
    board_attachment_file VARCHAR(1000) NOT NULL COMMENT '피드 첨부파일 명',
    PRIMARY KEY (board_attachment_id)
) COMMENT '피드 첨부파일';

CREATE TABLE board_liked
(
    user_id          INT      NOT NULL COMMENT '사용자 ID',
    board_id         INT      NOT NULL COMMENT '피드. 소모임 ID',
    board_liked_date DATETIME NOT NULL COMMENT '피드 좋아요한 날짜',
    PRIMARY KEY (user_id, board_id)
) COMMENT '피드 좋아요';

CREATE TABLE city
(
    city_id       INT           NOT NULL AUTO_INCREMENT COMMENT '도시 ID',
    city_name     VARCHAR(20)   NOT NULL UNIQUE COMMENT '도시 이름',
    city_continent VARCHAR(50)   NOT NULL COMMENT '도시의 대륙',
    city_language VARCHAR(20)   NOT NULL COMMENT '도시의 언어',
    city_currency VARCHAR(20)   NOT NULL COMMENT '도시의 통화',
    city_img      VARCHAR(1000) NOT NULL COMMENT '도시의 이미지',
    city_nation   VARCHAR(20)   NOT NULL COMMENT '도시의 국가',
    q1_id         VARCHAR(20)   NOT NULL COMMENT '첫번째 질문에 대한 답',
    q2_id         VARCHAR(20)   NOT NULL COMMENT '두번째 질문에 대한 답',
    q3_id         VARCHAR(20)   NOT NULL COMMENT '세번째 질문에 대한 답',
    q4_id         VARCHAR(20)   NOT NULL COMMENT '네번째 질문에 대한 답',
    q5_id         VARCHAR(20)   NOT NULL COMMENT '다섯번째 질문에 대한 답',
    PRIMARY KEY (city_id)
) COMMENT '도시';

ALTER TABLE city
    ADD CONSTRAINT UQ_city_name UNIQUE (city_name);


CREATE TABLE city_liked
(
    user_id         INT      NOT NULL COMMENT '사용자 ID',
    city_id         INT      NOT NULL COMMENT '도시 ID',
    city_liked_date DATETIME NOT NULL DEFAULT NOW() COMMENT '도시 좋아요 누른 날짜',
    PRIMARY KEY (user_id, city_id)
) COMMENT '도시 좋아요';

CREATE TABLE declaration
(
    declaration_id       INT          NOT NULL AUTO_INCREMENT COMMENT '게시물 신고 테이블 ID',
    board_id            INT          NOT NULL COMMENT '피드, 소모임 ID',
    user_id              INT          NOT NULL COMMENT '신고자 ID',
    declaration_content  VARCHAR(200) NOT NULL COMMENT '신고 내용',
    declaration_category VARCHAR(100) NOT NULL COMMENT '신고 게시물의 종류',
    PRIMARY KEY (declaration_id)
) COMMENT '게시물 신고';

CREATE TABLE friendship
(
    request_friend_id INT         NOT NULL AUTO_INCREMENT COMMENT '친구 관계 테이블 ID',
    to_user_id        INT         NOT NULL COMMENT '친구 요청된 사용자 ID',
    from_user_id      INT         NOT NULL COMMENT '친구 요청한 사용자 ID',
    friend_status     ENUM('수락','대기','거절') NOT NULL default '대기' COMMENT '친구 상태',
    friendship_date   DATETIME    NULL     DEFAULT NOW() COMMENT '친구 수락 일자',
    PRIMARY KEY (request_friend_id)
) COMMENT '친구 관계';

ALTER TABLE friendship
    ADD CONSTRAINT chk_friend_status
        CHECK (friend_status IN ('수락', '대기', '거절'));

CREATE TABLE guest_book
(
    guest_book_id      INT          NOT NULL AUTO_INCREMENT COMMENT '방명록 테이블 ID',
    to_user_id         INT          NOT NULL COMMENT '작성된 사용자 ID',
    from_user_id       INT          NOT NULL COMMENT '작성 사용자ID',
    guest_book_content VARCHAR(100) NOT NULL COMMENT '방명록 내용',
    guest_book_date    DATETIME     NOT NULL DEFAULT NOW() COMMENT '방명록 작성 일시',
    PRIMARY KEY (guest_book_id)
) COMMENT '방명록';

CREATE TABLE message
(
    message_id        INT          NOT NULL AUTO_INCREMENT COMMENT '메시지 테이블 ID',
    messageroom_id    INT          NOT NULL COMMENT '채팅방 ID',
    from_user_id      INT          NOT NULL COMMENT '메세지를 보낸 사용자 ID',
    to_user_id        INT          NOT NULL COMMENT '메세지를 받은  사용자 ID',
    message_content   VARCHAR(100) NOT NULL COMMENT '메시지 내용',
    message_send_date DATETIME     NOT NULL DEFAULT NOW() COMMENT '메시지 보낸 일자',
    message_check     VARCHAR(20)  NOT NULL DEFAULT '미확인' COMMENT '메시지 확인 여부',
    PRIMARY KEY (message_id)
) COMMENT '메시지';

ALTER TABLE message
    ADD CONSTRAINT chk_message_check
        CHECK (message_check IN('미확인', '확인'));

CREATE TABLE messageroom
(
    messageroom_id INT NOT NULL AUTO_INCREMENT COMMENT '채팅방 테이블 ID',
    to_user_id     INT NOT NULL COMMENT '대화 상대자의 ID',
    from_user_id   INT NOT NULL COMMENT '개설한 사용자의 ID',
    PRIMARY KEY (messageroom_id)
) COMMENT '채팅방';

CREATE TABLE package
(
    package_id        INT           NOT NULL AUTO_INCREMENT COMMENT '패키지 테이블 ID',
    city_id           INT           NOT NULL COMMENT '도시 ID',
    user_id           INT           NOT NULL COMMENT '사용자 테이블 ID',
    package_status    VARCHAR(100)  NOT NULL COMMENT '패키지 허가 여부',
    package_content   VARCHAR(1000) NOT NULL COMMENT '패키지 내용',
    package_regdate   DATETIME      NOT NULL DEFAULT NOW() COMMENT '패키지 게시일자',
    package_title     VARCHAR(100)  NOT NULL COMMENT '패키지 제목',
    package_cost      INT           NOT NULL COMMENT '패키지 비용',
    package_maxpeople INT           NOT NULL COMMENT '패키지 최대 인원',
    package_start_day DATETIME      NOT NULL COMMENT '패키지 시작 일자',
    package_end_day   DATETIME      NOT NULL COMMENT '패키지 종료 일자',
    PRIMARY KEY (package_id)
) COMMENT '패키지';

 ALTER TABLE package
    ADD CONSTRAINT chk_package_status
        CHECK (package_status IN('승인', '대기', '미승인'));

CREATE TABLE package_attachment
(
    package_attachment_id   INT           NOT NULL AUTO_INCREMENT COMMENT '패키지 첨부파일 테이블 ID',
    package_id              INT           NOT NULL COMMENT '패키지 ID',
    package_attachment_file VARCHAR(1000) NOT NULL COMMENT '패키지 첨부파일 명',
    PRIMARY KEY (package_attachment_id)
) COMMENT '패키지 첨부파일';

CREATE TABLE package_liked
(
    user_id            INT      NOT NULL COMMENT '사용자 ID',
    package_id         INT      NOT NULL COMMENT '패키지 ID',
    package_liked_date DATETIME NOT NULL COMMENT '패키지 좋아요한 날짜',
    PRIMARY KEY (user_id, package_id)
) COMMENT '패키지 좋아요';

CREATE TABLE payment_info
(
    payment_id      INT         NOT NULL AUTO_INCREMENT COMMENT '결제 정보 ID',
    user_id         INT         NOT NULL COMMENT '사용자 ID',
    package_id      INT         NOT NULL COMMENT '패키지 ID',
    user_count      INT         NOT NULL COMMENT '결제에 관련된 인원수',
    payment_date    DATETIME    NOT NULL DEFAULT NOW() COMMENT '결제 일시',
    payment_status VARCHAR(20) NOT NULL COMMENT '결제 상태',
    PRIMARY KEY (payment_id)
) COMMENT '결제 정보';

ALTER TABLE payment_info
    ADD CONSTRAINT chk_payment_status
        CHECK(payment_status IN('결제완료', '결제취소'));

CREATE TABLE question_answer
(
    question_answer_id INT         NOT NULL AUTO_INCREMENT COMMENT '질문에 대한 보기 테이블 ID',
    question_id        VARCHAR(20) NOT NULL COMMENT '질문 ID',
    answerA            VARCHAR(20) NOT NULL COMMENT '보기A',
    answerB            VARCHAR(20) NOT NULL COMMENT '보기B',
    PRIMARY KEY (question_answer_id)
) COMMENT '질문에 대한 보기';

CREATE TABLE search_history
(
    search_history_id INT           NOT NULL AUTO_INCREMENT COMMENT '검색 기록 테이블 ID',
    user_id           INT           NOT NULL COMMENT '사용자 ID',
    qry_url           VARCHAR(1000) NOT NULL COMMENT '검색 기록 쿼리',
    start_name        VARCHAR(100)  NOT NULL COMMENT '출발지',
    end_name          VARCHAR(100)  NOT NULL COMMENT '도착지',
    PRIMARY KEY (search_history_id)
) COMMENT '검색 기록';

CREATE TABLE user
(
    user_id                INT           NOT NULL AUTO_INCREMENT COMMENT '사용자 테이블 ID',
    user_email             VARCHAR(100)  NOT NULL COMMENT '사용자 이메일',
    user_password          VARCHAR(100)  NOT NULL COMMENT '사용자 비밀번호',
    user_username          VARCHAR(20)   NOT NULL COMMENT '사용자 ID',
    user_name              VARCHAR(20)   NOT NULL COMMENT '사용자 실제 이름',
    user_phonenumber       VARCHAR(20)   NOT NULL COMMENT '사용자 핸드폰 번호',
    user_regdate           DATETIME      NOT NULL DEFAULT NOW() COMMENT '사용자 계정 생성 일자',
    user_birthday          VARCHAR(20)   NULL     COMMENT '사용자 생년월일',
    user_profile           VARCHAR(1000) NULL     COMMENT '사용자 프로필 사진',
    user_provider          VARCHAR(100)  NULL     COMMENT '사용자 소셜 로그인',
    user_introdution       VARCHAR(100)  NULL     COMMENT '사용자 프로필 자기소개 ',
    user_authorization     VARCHAR(20)   NOT NULL COMMENT '사용자 권한',
    business_companynumber VARCHAR(100)  NULL     COMMENT '기업 사업자 번호',
    user_status            VARCHAR(100)  NULL     COMMENT '사용자 승인 상태',
    PRIMARY KEY (user_id)
) COMMENT '사용자 계정';

ALTER TABLE user
    ADD CONSTRAINT UQ_user_email UNIQUE (user_email);

ALTER TABLE user
    ADD CONSTRAINT UQ_user_username UNIQUE (user_username);

ALTER TABLE user
    ADD CONSTRAINT UQ_user_phonenumber UNIQUE (user_phonenumber);

ALTER TABLE user
    ADD CONSTRAINT UQ_business_companynumber UNIQUE (business_companynumber);

CREATE TABLE user_city
(
    user_id      INT      NOT NULL COMMENT '사용자 ID',
    city_id      INT      NOT NULL COMMENT '도시 ID',
    created_date DATETIME NOT NULL DEFAULT NOW() COMMENT '유저가 선택한 시간'
) COMMENT '유저가 선택한 도시';

ALTER TABLE city_liked
    ADD CONSTRAINT FK_user_TO_city_liked
        FOREIGN KEY (user_id)
            REFERENCES user (user_id);

ALTER TABLE city_liked
    ADD CONSTRAINT FK_city_TO_city_liked
        FOREIGN KEY (city_id)
            REFERENCES city (city_id);

ALTER TABLE user_city
    ADD CONSTRAINT FK_user_TO_user_city
        FOREIGN KEY (user_id)
            REFERENCES user (user_id);

ALTER TABLE package
    ADD CONSTRAINT FK_city_TO_package
        FOREIGN KEY (city_id)
            REFERENCES city (city_id);

ALTER TABLE package_liked
    ADD CONSTRAINT FK_user_TO_package_liked
        FOREIGN KEY (user_id)
            REFERENCES user (user_id);

ALTER TABLE package_liked
    ADD CONSTRAINT FK_package_TO_package_liked
        FOREIGN KEY (package_id)
            REFERENCES package (package_id);

ALTER TABLE payment_info
    ADD CONSTRAINT FK_user_TO_payment_info
        FOREIGN KEY (user_id)
            REFERENCES user (user_id);

ALTER TABLE payment_info
    ADD CONSTRAINT FK_package_TO_payment_info
        FOREIGN KEY (package_id)
            REFERENCES package (package_id);

ALTER TABLE package_attachment
    ADD CONSTRAINT FK_package_TO_package_attachment
        FOREIGN KEY (package_id)
            REFERENCES package (package_id);

ALTER TABLE board_attachment
    ADD CONSTRAINT FK_board_TO_board_attachment
        FOREIGN KEY (board_id)
            REFERENCES board (board_id);

ALTER TABLE declaration
    ADD CONSTRAINT FK_user_TO_declaration
        FOREIGN KEY (user_id)
            REFERENCES user (user_id);

ALTER TABLE board_comment
    ADD CONSTRAINT FK_board_TO_board_comment
        FOREIGN KEY (board_id)
            REFERENCES board (board_id);

ALTER TABLE board_comment
    ADD CONSTRAINT FK_user_TO_board_comment
        FOREIGN KEY (user_id)
            REFERENCES user (user_id);

ALTER TABLE search_history
    ADD CONSTRAINT FK_user_TO_search_history
        FOREIGN KEY (user_id)
            REFERENCES user (user_id);

ALTER TABLE guest_book
    ADD CONSTRAINT FK_user_TO_guest_book
        FOREIGN KEY (to_user_id)
            REFERENCES user (user_id);

ALTER TABLE guest_book
    ADD CONSTRAINT FK_user_TO_guest_book1
        FOREIGN KEY (from_user_id)
            REFERENCES user (user_id);

ALTER TABLE friendship
    ADD CONSTRAINT FK_user_TO_friendship
        FOREIGN KEY (to_user_id)
            REFERENCES user (user_id);

ALTER TABLE friendship
    ADD CONSTRAINT FK_user_TO_friendship1
        FOREIGN KEY (from_user_id)
            REFERENCES user (user_id);

ALTER TABLE board_liked
    ADD CONSTRAINT FK_user_TO_board_liked
        FOREIGN KEY (user_id)
            REFERENCES user (user_id);

ALTER TABLE board_liked
    ADD CONSTRAINT FK_board_TO_board_liked
        FOREIGN KEY (board_id)
            REFERENCES board (board_id);

ALTER TABLE board
    ADD CONSTRAINT FK_user_TO_board
        FOREIGN KEY (user_id)
            REFERENCES user (user_id);

ALTER TABLE declaration
    ADD CONSTRAINT FK_board_TO_declaration
        FOREIGN KEY (board_id)
            REFERENCES board (board_id);

ALTER TABLE board
    ADD CONSTRAINT FK_city_TO_board
        FOREIGN KEY (city_id)
            REFERENCES city (city_id);

ALTER TABLE user_city
    ADD CONSTRAINT FK_city_TO_user_city
        FOREIGN KEY (city_id)
            REFERENCES city (city_id);

ALTER TABLE messageroom
    ADD CONSTRAINT FK_user_TO_messageroom
        FOREIGN KEY (to_user_id)
            REFERENCES user (user_id);

ALTER TABLE messageroom
    ADD CONSTRAINT FK_user_TO_messageroom1
        FOREIGN KEY (from_user_id)
            REFERENCES user (user_id);

ALTER TABLE message
    ADD CONSTRAINT FK_messageroom_TO_message
        FOREIGN KEY (messageroom_id)
            REFERENCES messageroom (messageroom_id);

ALTER TABLE message
    ADD CONSTRAINT FK_user_TO_message
        FOREIGN KEY (from_user_id)
            REFERENCES user (user_id);

ALTER TABLE message
    ADD CONSTRAINT FK_user_TO_message1
        FOREIGN KEY (to_user_id)
            REFERENCES user (user_id);

ALTER TABLE package
    ADD CONSTRAINT FK_user_TO_package
        FOREIGN KEY (user_id)
            REFERENCES user (user_id);

ALTER TABLE user MODIFY user_profile BLOB;