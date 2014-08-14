create user whdb identified by '123456@TA';

create database whdb default character set utf8;

grant all on whdb.* to whdb@"%";
