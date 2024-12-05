#  user admin 데이터
insert into user (user_email, user_password, user_username, user_name, user_phonenumber, user_birthday, user_profile, user_provider, user_introdution, user_authorization)
values ('fullstackK08@naver.com',
        '1234',
        'admin1234',
        '이경원',
        '010-8297-0195',
        '2000-04-24',
        '',
        '',
        '',
        'admin'
        ),
        (
         'wonwon123123@naver.com',
         '1234',
         'user1234',
         '이경원',
        '010-1234-1234',
         '2000-02-11',
         '',
         '',
         '',
         'user'
        );

# business 데이터
insert into user (user_email, user_password,user_username,user_name,user_phonenumber,user_authorization,business_companynumber)
 values       (
        'business@naver.com',
         '1234',
         'business1234',
         '이경원',
         '010-1111-1111',
         'business',
         '123-11-12345'
        ) ;

# city data
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
          'A',
          'B'
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
          'A'
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
          'A'
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


# user Data
insert into user (user_email, user_password, user_username, user_name, user_phonenumber, user_birthday, user_profile, user_provider, user_introdution, user_authorization)
values ('fullstackK14@naver.com',
        '4321',
        'admin4321',
        '최시후',
        '010-9162-3183',
        '2001-07-18',
        '',
        '',
        '',
        'admin'
       ),
       (
           'hoohoo4321@naver.com',
           '4321',
           'user4321',
           '최시후',
           '010-4321-4321',
           '2001-07-18',
           '',
           '',
           '',
           'user4321'
       );

#board - feed Data
insert into board (user_id, board_subject, board_content, board_category, city_id)
VALUES (
        '5',
        '룰루랄라 파리 여행',
        '룰랄룰랄루 파리 여행 건물들이 다 이쁘다 히히히',
        '피드',
        '2'
       );

insert into board (user_id, board_subject, board_content, board_category, city_id)
VALUES (
           '2',
           '경원이의 나홀로 여행 시리즈1',
           '우헤헹 나혼자 여행 재미따 키키',
           '피드',
           '8'
       ),(
    '2',
    '경원이의 나홀로 여행 시리즈2',
    '나홀로 여행 다니다보니까 심심하다..',
    '피드',
    '12'
    ),(
    '2',
    '경원이의 나홀로 여행 시리즈3',
    '룰랄루 나홀로 여행 이번엔 여기입니다!! 짜쟌~',
    '피드',
    '20'
    ),(
    '2',
    '경원이의 나홀로 여행 시리즈4',
    '3분 카레 구해서 같이 와야겠다..((외롭))',
    '피드',
    '16'
    );
