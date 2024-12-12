#  user admin 데이터
insert into user (user_email, user_password, user_username, user_name, user_birthday, user_profile, user_provider, user_introdution, user_authorization)
values ('fullstackK08@naver.com',
        '1234',
        'admin1234',
        '이경원',
        '2000-04-24',
        '',
        '',
        '나는 이경원이다',
        'admin'
        ),
        (
         'wonwon123123@naver.com',
         '1234',
         'user1234',
         '이경원',
         '2000-02-11',
         '',
         '',
         '',
         'user'
        );

# business 데이터
insert into user (user_email, user_password,user_username,user_name,user_authorization,business_companynumber)
 values       (
        'business@naver.com',
         '1234',
         'business1234',
         '이경원',
         'business',
         '123-11-12345'
        ) ;

insert into user (user_email, user_password,user_username,user_name,user_authorization,business_companynumber)
values       (
                 'business2@gmail.com',
                 '102972',
                 'business10',
                 '하나투어',
                 'business',
                 '122-11-01739'
             ),
    (
        'business3@gmail.com',
        '10292',
        'business15',
        '투어쓰',
        'business',
        '332-11-00739'
    )
    ;

# city data
insert into city (city_name, city_continent, city_language, city_currency, city_img, city_nation, q1_id,q2_id,q3_id,q4_id,q5_id)
values(
       '괌',
       '남태평양',
       '영어',
       '달러',
       'GuamImg.jpg',
       '미국',
       'A',
       'A',
       'A',
       'A',
       'A'),
        (
         '마닐라',
         '동남아시아',
         '타갈로그어',
         '페소',
         'ManilaImg.jpg',
         '필리핀',
         'A',
         'A',
         'A',
         'A',
         'B'
        ),
        (
         '나트랑',
         '동남아시아',
         '베트남어',
         '동',
         'NahTrangImg.jpg',
         '베트남',
         'A',
         'A',
         'A',
         'B',
         'A'
        ),
        (
         '세부',
         '동남아시아',
         '타갈로그어',
         '페소',
         'CebuImg.jpg',
         '필리핀',
         'A',
         'A',
         'A',
         'B',
         'B'
        ),
        (
         '방콕',
         '동남아시아',
         '태국어',
         '바트',
         'BangkokImg.jpg',
         '태국',
         'A',
         'A',
         'B',
         'A',
         'A'
        ),
        (
         '파리',
         '유럽',
         '프랑스어',
         '유로',
         'FranceImg.jpg',
         '프랑스',
         'A',
         'A',
         'B',
         'A',
         'B'
        ),
        (
         '홍콩',
         '아시아',
         '중국어',
         '홍콩 달러',
         'HongkongImg.jpg',
         '홍콩',
         'A',
         'A',
         'B',
         'B',
         'A'
        ),
        (
         '상하이',
         '아시아',
         '중국어',
         '위안',
         'SanghaiImg.jpeg',
         '중국',
         'A',
         'A',
         'B',
         'B',
         'B'
        ),
        (
        '하와이',
         '미국',
         '영어',
         '달러',
         'HawaiiImg.jpg',
         '미국',
        'A',
        'B',
        'A',
        'A',
        'A'
        ),
        (
         '다낭',
         '동남아시아',
         '베트남어',
         '동',
         'DanangImg.jpg',
         '베트남',
         'A',
         'B',
         'A',
         'A',
         'B'
        ),
        (
        '울란바토르',
         '아시아',
         '몽골어',
         '투그릭',
         'UlaanbaatarImg.jpg',
         '몽골',
         'A',
         'B',
         'A',
         'B',
         'A'
        ),
        (
        '발리',
         '동남아시아',
         '인도네시아어',
         '루피아',
         'BailImg.jpg',
         '인도네시아',
         'A',
         'B',
         'A',
         'B',
         'B'
        ),
        (
         '로스엔젤레스',
         '미국',
         '영어',
         '달러',
         'LosAngelesImg.jpg',
         '미국',
         'A',
         'B',
         'B',
         'A',
         'A'
        ),
        (
         '두바이',
         '아시아',
         '아랍어',
         '디르함',
         'DubaiImg.jpg',
         '아랍에미리트',
         'A',
         'B',
         'B',
         'A',
         'B'
        ),
        (
         '도쿄',
         '일본',
         '일본어',
         '엔',
         'TokyoImg.jpg',
         '일본',
         'A',
         'B',
         'B',
         'B',
         'A'
        ),
        (
         '런던',
         '유럽',
         '영어',
         '파운드',
         'LondonImg.jpg',
         '영국',
         'A',
         'B',
         'B',
         'B',
         'B'
        ),
        (
         '삿포로',
         '일본',
         '일본어',
         '엔',
         'SapporoImg.jpg',
         '일본',
         'B',
         'A',
         'A',
         'A',
         'A'
        ),
        (
        '사이판',
         '남태평양',
         '영어',
         '달러',
         'SaipanImg.jpg',
         '미국',
         'B',
         'A',
         'A',
         'A',
         'B'
        ),
        (
         '이스탄불',
         '유럽',
         '튀르키예어',
         '리라',
         'IstanbulImg.jpg',
         '튀르키예',
         'B',
         'A',
         'A',
         'B',
         'A'
        ),
        (
         '로마',
         '유럽',
         '이탈리아어',
         '유로',
         'RomeImg.jpg',
         '이탈리아',
         'B',
         'A',
         'A',
         'B',
         'B'
        ),
        (
        '뉴욕',
        '미국',
        '영어',
        '달러',
        'NewyorkImg.jpg',
        '미국',
        'B',
        'A',
        'B',
        'A',
        'A'
        ),
        (
        '하얼빈',
        '아시아',
        '중국어',
        '위안',
        'HarbinImg.jpg',
        '중국',
        'B',
        'A',
        'B',
        'A',
        'B'
        ),
        (
        '오사카',
         '일본',
         '일본어',
         '엔',
         'OsakaImg.jpg',
         '일본',
        'B',
        'A',
        'B',
        'B',
        'A'
        ),
        (
        '마카오',
         '아시아',
        '중국어',
         '위안',
         'MacauImg.jpg',
         '중국',
        'B',
        'A',
        'B',
        'B',
        'B'
        ),
        (
         '델리',
         '아시아',
         '힌디어',
         '루피',
         'DelhiImg.jpg',
         '인도',
         'B',
         'B',
         'A',
         'A',
         'A'
        ),
        (
        '프랑크푸르트',
         '유럽',
         '독일어',
         '유로',
         'FrankfurtImg.jpg',
         '독일',
        'B',
        'B',
        'A',
        'A',
        'B'
        ),
        (
         '시드니',
         '남태평양',
         '영어',
         '오스트레일리아 달러',
         'SydneyImg.jpg',
         '호주',
         'B',
         'B',
         'A',
         'B',
         'A'
        ),
        (
         '호쿠오카',
         '일본',
         '일본어',
         '엔',
         'FukuokaImg.jpg',
         '일본',
         'B',
         'B',
         'A',
         'B',
         'B'
        ),
        (
        '오키나와',
         '일본',
         '일본어',
         '엔',
         'OkinawaImg.jpg',
         '일본',
        'B',
        'B',
        'B',
        'A',
        'A'
        ),
        (
        '타이베이',
         '아시아',
         '중국어',
         '타이완 달러',
         'TaipeiImg.jpg',
         '대만',
        'B',
        'B',
        'B',
        'A',
        'B'
        ),
        (
        '벤쿠버',
         '미국',
         '영어',
         '달러',
         'VancouverImg.jpg',
         '미국',
        'B',
        'B',
        'B',
        'B',
        'A'
        ),
        (
        '지린',
         '아시아',
         '중국어',
         '위안',
         'JilinImg.jpg',
         '중국',
        'B',
        'B',
        'B',
        'B',
        'B'
        )
;

# 공항 정보 테이블
INSERT INTO airport (airport_code, airport_name, airport_city, airport_country)
VALUES
    ('ICN', '인천 국제 공항', '서울', '한국'),
    ('HND', '도교 국제 공항', '도쿄', '일본'),
    ('KIX', '간사이 국제 공항', '오사카', '일본'),
    ('FUK', '후쿠오카 국제 공항', '후쿠오카', '일본'),
    ('CTS', '신 치토세 공항', '삿포로', '일본'),
    ('OKA', '나하 공항', '오키나와', '일본'),
    ('CXR', '깜라인 국제 공항', '나트랑', '베트남'),
    ('DAD', '다낭 국제 공항', '다낭', '베트남'),
    ('BKK', '수완나품 국제 공항', '방콕', '태국'),
    ('MNL', '니노이 아키노 국제 공항', '마닐라', '필리핀'),
    ('CEB', '막탄 세부 국제 공항', '세부', '필리핀'),
    ('DPS', '응우라 라이 국제 공항', '발리', '인도네시아'),
    ('CDG', '파리 샤를 드골 공항', '파리', '프랑스'),
    ('LHR', '히스로 공항', '런던', '영국'),
    ('FCO', '레오나르도 다 빈치 국제 공항', '로마', '이탈리아'),
    ('FRA', '프랑크푸르트 공항', '프랑크푸르트', '독일'),
    ('IST', '이스탄불 공항', '이스탄불', '튀르키예'),
    ('TPE', '타이완 타오위엔 공항', '타이베이', '대만'),
    ('HKG', '홍콩 국제공항', '홍콩', '중국'),
    ('MFM', '마카오 국제공항', '마카오', '중국'),
    ('PVG', '상하이 푸둥 국제 공항', '상하이', '중국'),
    ('CGQ', '창춘 룽자 국제 공항', '지린', '중국'),
    ('HRB', '하얼빈 타이핑 국제 공항', '하얼빈', '중국'),
    ('DEL', '인디라 간디 국제 공항', '델리', '인도'),
    ('DXB', '두바이 국제 공항', '두바이', '아랍에미리트'),
    ('UBN', '칭기즈 칸 국제 공항', '올란바토르', '몽골'),
    ('LAX', '로스앤젤레스 국제 공항', '로스엔젤레스', '미국'),
    ('HNL', '호놀룰루 공항', '하와이', '미국'),
    ('JFK', '존 F. 케네디 국제 공항', '뉴욕', '미국'),
    ('YVR', '밴쿠버 국제공항', '밴쿠버', '캐나다'),
    ('GUM', '안토니오 B. 원 팻 국제 공항', '괌', '미국'),
    ('SPN', '사이판 국제공항', '사이판', '미국'),
    ('SYD', '시드니 공항', '시드니', '호주')
;

# 피드 더미
INSERT INTO board (user_id, city_id, board_subject, board_content, board_category)
VALUES
    (7, 2, '제목', '내애용', '피드'),
    (7, 5, '제목', '내애용', '피드'),
    (7, 10, '제목', '내애용', '피드'),
    (7, 23, '제목', '내애용', '소모임'),
    (7, 2, '제목', '내애용', '소모임'),
    (8, 2, '제목', '내애용', '소모임'),
    (8, 22, '제목', '내애용', '피드'),
    (8, 5, '제목', '내애용', '피드'),
    (8, 10, '제목', '내애용', '피드'),
    (8, 23, '제목', '내애용', '소모임'),
    (8, 2, '제목', '내애용', '피드'),
    (8, 16, '제목', '내애용', '소모임'),
    (8, 30, '제목', '내애용', '소모임'),
    (8, 5, '제목', '내애용', '소모임'),
    (8, 10, '제목', '내애용', '피드'),
    (7, 19, '제목', '내애용', '소모임'),
    (7, 2, '제목', '내애용', '소모임'),
    (7, 26, '제목', '내애용', '피드'),
    (7, 30, '제목', '내애용', '피드'),
    (7, 5, '제목', '내애용', '피드'),
    (7, 10, '제목', '내애용', '소모임'),
    (7, 23, '제목', '내애용', '피드'),
    (8, 29, '제목', '내애용', '피드'),
    (8, 2, '제목', '내애용', '피드'),
    (8, 30, '제목', '내애용', '피드'),
    (8, 31, '제목', '내애용', '소모임'),
    (8, 23, '제목', '내애용', '피드'),
    (8, 4, '제목', '내애용', '피드')
;

INSERT INTO package (city_id, user_id, package_content, package_status, package_title, package_cost, package_maxpeople, package_start_day, package_end_day)
VALUES
    (1, 10, '보온무운', '대기', '제에에ㅔ목', 123456, 10, '2024-12-01', '2024-12-12'),
    (1, 10, '보온무운', '대기', '제에에ㅔ목', 123456, 10, '2024-12-02', '2024-12-12'),
    (1, 10, '보온무운', '대기', '제에에ㅔ목', 6578657, 10, '2024-12-03', '2024-12-12'),
    (1, 10, '보온무운', '대기', '제에에ㅔ목', 435376, 10, '2024-12-04', '2024-12-12'),
    (1, 10, '보온무운', '대기', '제에에ㅔ목', 2345755, 10, '2024-12-05', '2024-12-12'),
    (1, 10, '보온무운', '대기', '제에에ㅔ목', 3246, 10, '2024-12-06', '2024-12-12'),
    (1, 10, '보온무운', '대기', '제에에ㅔ목', 34226, 10, '2024-12-07', '2024-12-12'),
    (1, 10, '보온무운', '대기', '제에에ㅔ목', 34225, 10, '2024-12-08', '2024-12-12'),
    (1, 10, '보온무운', '대기', '제에에ㅔ목', 123456, 10, '2024-12-09', '2024-12-12'),
    (1, 10, '보온무운', '대기', '제에에ㅔ목', 123456, 10, '2024-12-10', '2024-12-12'),
    (1, 10, '보온무운', '대기', '제에에ㅔ목', 123456, 10, '2024-12-11', '2025-01-12'),
    (1, 10, '보온무운', '대기', '제에에ㅔ목', 213424, 10, '2024-04-12', '2024-12-12'),
    (1, 10, '보온무운', '대기', '제에에ㅔ목', 1234544356, 10, '2024-12-13', '2024-12-12'),
    (1, 10, '보온무운', '대기', '제에에ㅔ목', 12531234, 10, '2024-12-14', '2024-12-12'),
    (1, 10, '보온무운', '대기', '제에에ㅔ목', 123456, 10, '2024-12-15', '2024-12-12'),
    (1, 10, '보온무운', '대기', '제에에ㅔ목', 123456, 10, '2024-12-16', '2024-12-12'),
    (1, 10, '보온무운', '대기', '제에에ㅔ목', 123456, 10, '2024-12-17', '2024-12-12'),
    (1, 10, '보온무운', '대기', '제에에ㅔ목', 123456, 10, '2024-12-18', '2024-12-12')
;

insert into city_liked (user_id,city_id)
values(7,1);
insert into city_liked (user_id,city_id)
values(8,2);
insert into city_liked (user_id,city_id)
values(7,3);
insert into payment_info (user_id, package_id, user_count, payment_status)
values(8,1,1,'결제완료');
insert into payment_info (user_id, package_id, user_count, payment_status)
values(8,2,1,'결제완료');
insert into payment_info (user_id, package_id, user_count, payment_status)
values(8,3,1,'결제완료');
insert into payment_info (user_id, package_id, user_count, payment_status)
values(7,4,1,'결제완료');
insert into payment_info (user_id, package_id, user_count, payment_status)
values(7,5,1,'결제완료');
insert into payment_info (user_id, package_id, user_count, payment_status)
                     values(7,6,1,'결제완료');
insert into payment_info (user_id, package_id, user_count, payment_status)
                                          values(7,7,1,'결제완료');

SELECT * FROM user WHERE user_id = 9;
SELECT * FROM user WHERE user_id = 10;
SELECT * FROM user WHERE user_id = 11;
UPDATE user
SET user_status = '승인'
WHERE user_id = 9;
UPDATE user
SET user_status = '승인'
WHERE user_id = 10;
UPDATE user
SET user_status = '승인'
WHERE user_id = 11;
SELECT * FROM user WHERE user_id = 10;
SELECT * FROM user WHERE user_id = 11;;