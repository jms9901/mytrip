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

alter table user
add column user_status varchar(20);

ALTER TABLE user MODIFY COLUMN user_authorization VARCHAR(255) DEFAULT 'ROLE_USER';
