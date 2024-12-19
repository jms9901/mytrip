select * from user;
select * from package;
select * from package_attachment;
select * from package_liked;
select * from payment_info;
select * from board;
select * from board_liked;
select * from board_comment;
select * from board_attachment;
select * from declaration;
select * from city;
select * from city_liked;
select * from user_city;
select * from question_answer;
select * from airport;
select * from search_history;
select * from friendship;
select * from guest_book;
select * from messageroom;
select * from message;

delete from user;
delete from package;
delete from package_attachment;
delete from package_liked;
delete from payment_info;
delete from board;
delete from board_liked;
delete from board_comment;
delete from board_attachment;
delete from declaration;
delete from city;
delete from city_liked;
delete from user_city;
delete from question_answer;
delete from airport;
delete from search_history;
delete from friendship;
delete from guest_book;
delete from messageroom;
delete from message;
SELECT * FROM guest_book WHERE to_user_id = 1;
alter table user
add column user_status varchar(20);

insert into user_city
values(19,3,'2024-12-10'),
      (19,5,'2024-12-10'),
      (19,4,'2024-12-10'),
      (19,11,'2024-12-10'),
      (19,4,'2024-12-10'),
      (19,3,'2024-12-10'),
      (19,3,'2024-12-10'),
      (19,3,'2024-12-10'),
      (19,3,'2024-12-10'),
      (19,3,'2024-12-10'),
      (19,3,'2024-12-10'),
      (19,3,'2024-12-10'),
      (19,3,'2024-12-10'),
      (19,3,'2024-12-10'),
      (19,3,'2024-12-10')
;
update user
set user_password = '$2a$10$y54jausXmoCMgc4wKgAAc.L2VQsLbk0vCDZ33mMKw4UrG/SXSPlxW'
where user_id = 34;


update city set q5_id = 'B' where city_id=12;

SELECT user_username, COUNT(*)
FROM user
GROUP BY user_username
HAVING COUNT(*) > 1;

update user
set user_status = '승인'
where user_id = 25;
ALTER TABLE user MODIFY COLUMN user_authorization VARCHAR(255) DEFAULT 'ROLE_USER';
SELECT user_name, user_username, user_profile FROM user WHERE user_id = 3;
select * from friendship;
SELECT * FROM board WHERE board_id = 121;
ALTER TABLE board AUTO_INCREMENT = 119;


SET foreign_key_checks = 0;
SET foreign_key_checks = 1;




update user
set user_authorization = 'ROLE_ADMIN'
where user_id = 1;

delete from user where user_id = 41;


alter table board modify column board_content LONGTEXT;