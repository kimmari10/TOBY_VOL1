create table users (
	id varchar(10) primary key,
	name varchar(20) not null,
	password varchar(20) not null
)

select * from users;
desc users;
alter table users drop column test ;