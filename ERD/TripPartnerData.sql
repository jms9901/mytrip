#  user admin 데이터
insert into user (user_email, user_password, user_username, user_name,  user_birthday, user_profile, user_provider, user_introdution, user_authorization)
values ('fullstackK08@naver.com',
        '1234',
        'admin1234',
        '이경원',
        '2000-04-24',
        '',
        '',
        '',
        'ROLE_ADMIN'
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
         'ROLE_USER'
        ),
        (
         'business1@naver.com',
         '1234',
         'business1',
         'business1',
         '2000-11-11',
         '',
         '',
         '',
         'ROLE_BUSINESS'
       ),
       (
           'business2@naver.com',
           '1234',
           'business2',
           'business2',
           '2000-11-11',
           '',
           '',
           '',
           'ROLE_BUSINESS'
       ),
       (
           'business3@naver.com',
           '1234',
           'business3',
           'business3',
           '2000-11-11',
           '',
           '',
           '',
           'ROLE_BUSINESS'
       ),
       (
           'business4@naver.com',
           '1234',
           'business4',
           'business4',
           '2000-11-11',
           '',
           '',
           '',
           'ROLE_BUSINESS'
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
         '후쿠오카',
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
        );


INSERT INTO user_city (user_id, city_id, created_date)
VALUES
    (1, 5, DATE_SUB(NOW(), INTERVAL 1 DAY)),
    (2, 5, DATE_SUB(NOW(), INTERVAL 1 DAY)),
    (2, 5, DATE_SUB(NOW(), INTERVAL 1 DAY)),
    (1, 7, DATE_SUB(NOW(), INTERVAL 1 DAY)),
    (1, 7, DATE_SUB(NOW(), INTERVAL 1 DAY)),
    (2, 7, DATE_SUB(NOW(), INTERVAL 1 DAY)),
    (2, 9, DATE_SUB(NOW(), INTERVAL 1 DAY)),
    (1, 9, DATE_SUB(NOW(), INTERVAL 1 DAY)),
    (2, 9, DATE_SUB(NOW(), INTERVAL 1 DAY)),
    (2, 9, DATE_SUB(NOW(), INTERVAL 1 DAY)),
    (1, 9, DATE_SUB(NOW(), INTERVAL 1 DAY)),
    (2, 9, DATE_SUB(NOW(), INTERVAL 1 DAY)),
    (2, 9, DATE_SUB(NOW(), INTERVAL 1 DAY));

INSERT INTO package (city_id, user_id, package_status, package_content, package_regdate, package_title, package_cost, package_maxpeople, package_start_day, package_end_day)
VALUES
     (1, 1, '승인', 'This is a sample package content.', NOW(), 'Sample Package 1', 1000, 10, '2023-05-01 00:00:00', '2023-05-10 00:00:00'),
     (2, 1, '대기', 'Another sample package content.', NOW(), 'Sample Package 2', 2000, 20, '2023-06-01 00:00:00', '2023-06-10 00:00:00'),
     (3, 1, '승인', 'Yet another sample package content.', NOW(), 'Sample Package 3', 1500, 15, '2023-07-01 00:00:00', '2023-07-10 00:00:00'),
     (4, 1, '승인', 'Fourth sample package content.', NOW(), 'Sample Package 4', 2500, 25, '2023-08-01 00:00:00', '2023-08-10 00:00:00'),
     (5, 1, '승인', 'Fifth sample package content.', NOW(), 'Sample Package 5', 3000, 30, '2023-09-01 00:00:00', '2023-09-10 00:00:00'),
     (1, 1, '대기', 'Sixth sample package content.', NOW(), 'Sample Package 6', 3500, 35, '2023-10-01 00:00:00', '2023-10-10 00:00:00'),
     (2, 1, '승인', 'Seventh sample package content.', NOW(), 'Sample Package 7', 4000, 40, '2023-11-01 00:00:00', '2023-11-10 00:00:00'),
     (3, 1, '미승인', 'Eighth sample package content.', NOW(), 'Sample Package 8', 4500, 45, '2023-12-01 00:00:00', '2023-12-10 00:00:00'),
     (4, 1, '승인', 'Ninth sample package content.', NOW(), 'Sample Package 9', 5000, 50, '2024-01-01 00:00:00', '2024-01-10 00:00:00'),
     (5, 1, '승인', 'Tenth sample package content.', NOW(), 'Sample Package 10', 5500, 55, '2024-02-01 00:00:00', '2024-02-10 00:00:00'),
     (1, 1, '승인', 'Eleventh sample package content.', NOW(), 'Sample Package 11', 6000, 60, '2024-03-01 00:00:00', '2024-03-10 00:00:00'),
     (2, 1, '승인', 'Twelfth sample package content.', NOW(), 'Sample Package 12', 6500, 65, '2024-04-01 00:00:00', '2024-04-10 00:00:00'),
     (3, 1, '대기', 'Thirteenth sample package content.', NOW(), 'Sample Package 13', 7000, 70, '2024-05-01 00:00:00', '2024-05-10 00:00:00'),
     (4, 1, '승인', 'Fourteenth sample package content.', NOW(), 'Sample Package 14', 7500, 75, '2024-06-01 00:00:00', '2024-06-10 00:00:00'),
     (5, 1, '미승인', 'Fifteenth sample package content.', NOW(), 'Sample Package 15', 8000, 80, '2024-07-01 00:00:00', '2024-07-10 00:00:00'),
     (1, 1, '승인', 'Sixteenth sample package content.', NOW(), 'Sample Package 16', 8500, 85, '2024-08-01 00:00:00', '2024-08-10 00:00:00'),
     (2, 1, '승인', 'Seventeenth sample package content.', NOW(), 'Sample Package 17', 9000, 90, '2024-09-01 00:00:00', '2024-09-10 00:00:00'),
     (3, 1, '대기', 'Eighteenth sample package content.', NOW(), 'Sample Package 18', 9500, 95, '2024-10-01 00:00:00', '2024-10-10 00:00:00'),
     (4, 1, '승인', 'Nineteenth sample package content.', NOW(), 'Sample Package 19', 10000, 100, '2024-11-01 00:00:00', '2024-11-10 00:00:00'),
     (5, 1, '승인', 'Twentieth sample package content.', NOW(), 'Sample Package 20', 10500, 105, '2024-12-01 00:00:00', '2024-12-10 00:00:00');

INSERT INTO package_attachment (package_id, package_attachment_file)
VALUES
     (51, '/img/BailImg.jpg'),
     (52, '/img/BangkokImg.jpg'),
     (53, '/img/BarcelonaImg.jpg'),
     (54, '/img/CebuImg.jpg'),
     (55, '/img/DanangImg.jpg'),
     (56, '/img/DelhiImg.jpg'),
     (57, '/img/DubaiImg.jpg'),
     (58, '/img/FranceImg.jpg'),
     (59, '/img/FukuokaImg.jpg'),
     (60, '/img/GuamImg.jpg');



INSERT INTO payment_info (user_id, package_id, user_count, payment_status)
VALUES
    (1, 21, 2, '결제완료'),
    (3, 22, 1, '결제취소'),
    (3, 23, 3, '결제완료'),
    (3, 24, 4, '결제취소'),
    (2, 25, 2, '결제완료'),
    (2, 26, 1, '결제취소'),
    (2, 27, 3, '결제완료'),
    (1, 28, 4, '결제취소'),
    (1, 29, 2, '결제완료'),
    (4, 30, 1, '결제취소'),
    (4, 31, 3, '결제완료'),
    (4, 32, 4, '결제취소'),
    (4, 33, 2, '결제완료'),
    (4, 34, 1, '결제취소'),
    (1, 35, 3, '결제완료'),
    (1, 36, 4, '결제취소'),
    (1, 37, 2, '결제완료'),
    (1, 38, 1, '결제취소'),
    (2, 39, 3, '결제완료'),
    (2, 40, 4, '결제취소'),
    (2, 21, 2, '결제완료'),
    (1, 22, 1, '결제취소'),
    (1, 23, 3, '결제완료'),
    (1, 24, 4, '결제취소'),
    (1, 25, 2, '결제완료'),
    (2, 26, 1, '결제취소'),
    (7, 27, 3, '결제완료'),
    (7, 28, 4, '결제취소'),
    (7, 29, 2, '결제완료'),
    (7, 30, 1, '결제취소');

INSERT INTO board (user_id, city_id, board_subject, board_content, board_view_count, board_category)
VALUES
    (1, 1, 'Subject 1', 'Content 1', 0, '피드'),
    (3, 2, 'Subject 2', 'Content 2', 0, '소모임'),
    (3, 3, 'Subject 3', 'Content 3', 0, '피드'),
    (3, 4, 'Subject 4', 'Content 4', 0, '소모임'),
    (2, 5, 'Subject 5', 'Content 5', 0, '피드'),
    (2, 6, 'Subject 6', 'Content 6', 0, '소모임'),
    (2, 7, 'Subject 7', 'Content 7', 0, '피드'),
    (1, 8, 'Subject 8', 'Content 8', 0, '소모임'),
    (1, 9, 'Subject 9', 'Content 9', 0, '피드'),
    (4, 10, 'Subject 10', 'Content 10', 0, '소모임'),
    (4, 11, 'Subject 11', 'Content 11', 0, '피드'),
    (4, 12, 'Subject 12', 'Content 12', 0, '소모임'),
    (4, 13, 'Subject 13', 'Content 13', 0, '피드'),
    (4, 14, 'Subject 14', 'Content 14', 0, '소모임'),
    (1, 15, 'Subject 15', 'Content 15', 0, '피드'),
    (1, 16, 'Subject 16', 'Content 16', 0, '소모임'),
    (1, 17, 'Subject 17', 'Content 17', 0, '피드'),
    (1, 18, 'Subject 18', 'Content 18', 0, '소모임'),
    (2, 19, 'Subject 19', 'Content 19', 0, '피드'),
    (2, 20, 'Subject 20', 'Content 20', 0, '소모임'),
    (2, 21, 'Subject 21', 'Content 21', 0, '피드'),
    (1, 22, 'Subject 22', 'Content 22', 0, '소모임'),
    (1, 23, 'Subject 23', 'Content 23', 0, '피드'),
    (1, 24, 'Subject 24', 'Content 24', 0, '소모임'),
    (1, 25, 'Subject 25', 'Content 25', 0, '피드'),
    (2, 26, 'Subject 26', 'Content 26', 0, '소모임'),
    (7, 27, 'Subject 27', 'Content 27', 0, '피드'),
    (7, 28, 'Subject 28', 'Content 28', 0, '소모임'),
    (7, 29, 'Subject 29', 'Content 29', 0, '피드'),
    (7, 30, 'Subject 30', 'Content 30', 0, '소모임');


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


INSERT INTO user_city (user_id, city_id, created_date)
VALUES
    (1, 5, DATE_SUB(NOW(), INTERVAL 1 DAY)),
    (2, 5, DATE_SUB(NOW(), INTERVAL 1 DAY)),
    (2, 5, DATE_SUB(NOW(), INTERVAL 1 DAY)),
    (1, 7, DATE_SUB(NOW(), INTERVAL 1 DAY)),
    (1, 7, DATE_SUB(NOW(), INTERVAL 1 DAY)),
    (2, 7, DATE_SUB(NOW(), INTERVAL 1 DAY)),
    (2, 9, DATE_SUB(NOW(), INTERVAL 1 DAY)),
    (1, 9, DATE_SUB(NOW(), INTERVAL 1 DAY)),
    (2, 9, DATE_SUB(NOW(), INTERVAL 1 DAY)),
    (2, 9, DATE_SUB(NOW(), INTERVAL 1 DAY)),
    (1, 9, DATE_SUB(NOW(), INTERVAL 1 DAY)),
    (2, 9, DATE_SUB(NOW(), INTERVAL 1 DAY)),
    (2, 9, DATE_SUB(NOW(), INTERVAL 1 DAY));

INSERT INTO package (city_id, user_id, package_status, package_content, package_regdate, package_title, package_cost, package_maxpeople, package_start_day, package_end_day)
VALUES
    (1, 1, '승인', 'This is a sample package content.', NOW(), 'Sample Package 1', 1000, 10, '2023-05-01 00:00:00', '2023-05-10 00:00:00'),
    (2, 1, '대기', 'Another sample package content.', NOW(), 'Sample Package 2', 2000, 20, '2023-06-01 00:00:00', '2023-06-10 00:00:00'),
    (3, 1, '승인', 'Yet another sample package content.', NOW(), 'Sample Package 3', 1500, 15, '2023-07-01 00:00:00', '2023-07-10 00:00:00'),
    (4, 1, '승인', 'Fourth sample package content.', NOW(), 'Sample Package 4', 2500, 25, '2023-08-01 00:00:00', '2023-08-10 00:00:00'),
    (5, 1, '승인', 'Fifth sample package content.', NOW(), 'Sample Package 5', 3000, 30, '2023-09-01 00:00:00', '2023-09-10 00:00:00'),
    (1, 1, '대기', 'Sixth sample package content.', NOW(), 'Sample Package 6', 3500, 35, '2023-10-01 00:00:00', '2023-10-10 00:00:00'),
    (2, 1, '승인', 'Seventh sample package content.', NOW(), 'Sample Package 7', 4000, 40, '2023-11-01 00:00:00', '2023-11-10 00:00:00'),
    (3, 1, '미승인', 'Eighth sample package content.', NOW(), 'Sample Package 8', 4500, 45, '2023-12-01 00:00:00', '2023-12-10 00:00:00'),
    (4, 1, '승인', 'Ninth sample package content.', NOW(), 'Sample Package 9', 5000, 50, '2024-01-01 00:00:00', '2024-01-10 00:00:00'),
    (5, 1, '승인', 'Tenth sample package content.', NOW(), 'Sample Package 10', 5500, 55, '2024-02-01 00:00:00', '2024-02-10 00:00:00'),
    (1, 1, '승인', 'Eleventh sample package content.', NOW(), 'Sample Package 11', 6000, 60, '2024-03-01 00:00:00', '2024-03-10 00:00:00'),
    (2, 1, '승인', 'Twelfth sample package content.', NOW(), 'Sample Package 12', 6500, 65, '2024-04-01 00:00:00', '2024-04-10 00:00:00'),
    (3, 1, '대기', 'Thirteenth sample package content.', NOW(), 'Sample Package 13', 7000, 70, '2024-05-01 00:00:00', '2024-05-10 00:00:00'),
    (4, 1, '승인', 'Fourteenth sample package content.', NOW(), 'Sample Package 14', 7500, 75, '2024-06-01 00:00:00', '2024-06-10 00:00:00'),
    (5, 1, '미승인', 'Fifteenth sample package content.', NOW(), 'Sample Package 15', 8000, 80, '2024-07-01 00:00:00', '2024-07-10 00:00:00'),
    (1, 1, '승인', 'Sixteenth sample package content.', NOW(), 'Sample Package 16', 8500, 85, '2024-08-01 00:00:00', '2024-08-10 00:00:00'),
    (2, 1, '승인', 'Seventeenth sample package content.', NOW(), 'Sample Package 17', 9000, 90, '2024-09-01 00:00:00', '2024-09-10 00:00:00'),
    (3, 1, '대기', 'Eighteenth sample package content.', NOW(), 'Sample Package 18', 9500, 95, '2024-10-01 00:00:00', '2024-10-10 00:00:00'),
    (4, 1, '승인', 'Nineteenth sample package content.', NOW(), 'Sample Package 19', 10000, 100, '2024-11-01 00:00:00', '2024-11-10 00:00:00'),
    (5, 1, '승인', 'Twentieth sample package content.', NOW(), 'Sample Package 20', 10500, 105, '2024-12-01 00:00:00', '2024-12-10 00:00:00');

INSERT INTO package_attachment (package_id, package_attachment_file)
VALUES
    (51, '/img/BailImg.jpg'),
    (52, '/img/BangkokImg.jpg'),
    (53, '/img/BarcelonaImg.jpg'),
    (54, '/img/CebuImg.jpg'),
    (55, '/img/DanangImg.jpg'),
    (56, '/img/DelhiImg.jpg'),
    (57, '/img/DubaiImg.jpg'),
    (58, '/img/FranceImg.jpg'),
    (59, '/img/FukuokaImg.jpg'),
    (60, '/img/GuamImg.jpg');

INSERT INTO board_attachment (board_id, board_attachment_file)
VALUES
    (32,'/img/BailImg.jpg');


update user
set user_password = '$2a$10$y54jausXmoCMgc4wKgAAc.L2VQsLbk0vCDZ33mMKw4UrG/SXSPlxW'
where user_id = 1;


insert into user (user_email, user_password, user_username, user_name, user_birthday, user_profile, user_provider, user_introdution, user_authorization)
values
       (
           'jeolmi9@naver.com',
           '1234',
           'jjeol_mii9',
           '절미9',
           '2023-01-16',
           '',
           '',
           '',
           'ROLE_USER'
       );

insert into board_attachment (board_id, board_attachment_file)
values (31, 'BangkokImg.jpg');
