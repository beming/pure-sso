/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2014/8/14 星期四 下午 23:28:35                    */
/*==============================================================*/


drop table if exists tbl_department;

drop table if exists tbl_menu;

drop table if exists tbl_operator_log;

drop table if exists tbl_post;

drop table if exists tbl_role;

drop table if exists tbl_role_menu;

drop table if exists tbl_user;

drop table if exists tbl_user_role;

/*==============================================================*/
/* Table: tbl_department                                        */
/*==============================================================*/
create table tbl_department
(
   dep_id               int not null auto_increment,
   dep_code             varchar(32),
   dep_name             varchar(32) not null,
   dep_status           numeric(2,0) not null,
   dep_linker           varchar(32),
   dep_phone            varchar(32),
   dep_email            varchar(32),
   dep_teacher          varchar(1024),
   dep_remark           varchar(1024),
   primary key (dep_id)
);

/*==============================================================*/
/* Table: tbl_menu                                              */
/*==============================================================*/
create table tbl_menu
(
   menu_id              int not null auto_increment,
   menu_name            varchar(32) not null,
   menu_url             varchar(32),
   navcode              varchar(32),
   menu_img             varchar(64),
   menu_sort            int,
   parent_id            int not null comment '0为模块
            其他为子菜单',
   menu_level           int,
   menu_status          numeric(1,0),
   is_menu              numeric(1,0),
   top_menu_flag        int,
   menu_field           varchar(32),
   menu_remark          varchar(1024),
   primary key (menu_id)
);

/*==============================================================*/
/* Table: tbl_operator_log                                      */
/*==============================================================*/
create table tbl_operator_log
(
   operator_id          int not null auto_increment,
   operator_user        int not null,
   operator_ip          varchar(32) not null,
   operator_level       varchar(32) not null,
   operator_time        datetime not null,
   operator_content     varchar(1024) not null,
   primary key (operator_id)
);

/*==============================================================*/
/* Table: tbl_post                                              */
/*==============================================================*/
create table tbl_post
(
   post_id              int not null auto_increment,
   post_name            varchar(32) not null,
   post_status          numeric(2,0) not null,
   post_remark          varchar(1024),
   primary key (post_id)
);

/*==============================================================*/
/* Table: tbl_role                                              */
/*==============================================================*/
create table tbl_role
(
   role_id              int not null auto_increment,
   role_name            varchar(32) not null,
   role_status          numeric(2,0) not null,
   role_remark          varchar(1024),
   primary key (role_id)
);

/*==============================================================*/
/* Table: tbl_role_menu                                         */
/*==============================================================*/
create table tbl_role_menu
(
   role_menu_id         int not null auto_increment,
   role_id              int,
   menu_id              int,
   r_select             numeric(1,0) not null,
   r_add                numeric(1,0) not null,
   r_update             numeric(1,0) not null,
   r_del                numeric(1,0) not null,
   r_exec               numeric(1,0),
   primary key (role_menu_id)
);

/*==============================================================*/
/* Table: tbl_user                                              */
/*==============================================================*/
create table tbl_user
(
   user_id              int not null auto_increment,
   dep_id               int,
   post_id              int,
   login_name           varchar(32) not null,
   user_name            varchar(32) not null,
   passwd               varchar(32) not null,
   chg_pwd_date         date,
   status               numeric(2,0) not null comment '0、-1 ：无效
            1：有效
            9：被锁',
   id_type              numeric(1,0),
   id_num               varchar(32),
   sexy                 numeric(1,0) comment '0 female
            1male
            3else',
   birthday             date,
   phone                varchar(32),
   mobile               varchar(32),
   addr                 varchar(1024),
   post_code            varchar(32),
   email                varchar(32),
   last_login_time      datetime,
   remark               varchar(1024),
   primary key (user_id)
);

/*==============================================================*/
/* Table: tbl_user_role                                         */
/*==============================================================*/
create table tbl_user_role
(
   user_role_id         int not null auto_increment,
   role_id              int,
   user_id              int,
   primary key (user_role_id)
);



/*==============================================================*/
/* Init the system admin's info，admin:ssoAdmin                 */
/*==============================================================*/
insert into tbl_menu (menu_id, menu_name, menu_url, navcode, menu_img, menu_sort, parent_id, menu_level, menu_status, is_menu, top_menu_flag, menu_field, menu_remark) values (1, '部门管理', '/dept.do?tag=list', 's1', '', 1, 0, 1, 1, '1', 0, '', '');
insert into tbl_menu (menu_id, menu_name, menu_url, navcode, menu_img, menu_sort, parent_id, menu_level, menu_status, is_menu, top_menu_flag, menu_field, menu_remark) values (2, '岗位管理', '/post.do?tag=list', 's2', '', 2, 0, 1, 1, '1', 0, '', '');
insert into tbl_menu (menu_id, menu_name, menu_url, navcode, menu_img, menu_sort, parent_id, menu_level, menu_status, is_menu, top_menu_flag, menu_field, menu_remark) values (3, '菜单管理', '/menu.do?tag=list', 's3', '', 3, 0, 1, 1, '1', 0, '', '');
insert into tbl_menu (menu_id, menu_name, menu_url, navcode, menu_img, menu_sort, parent_id, menu_level, menu_status, is_menu, top_menu_flag, menu_field, menu_remark) values (4, '角色管理', '/role.do?tag=list', 's4', '', 4, 0, 1, 1, '1', 0, '', '');
insert into tbl_menu (menu_id, menu_name, menu_url, navcode, menu_img, menu_sort, parent_id, menu_level, menu_status, is_menu, top_menu_flag, menu_field, menu_remark) values (5, '用户管理', '/user.do?tag=list', 's5', '', 5, 0, 1, 1, '1', 0, '', '');
insert into tbl_menu (menu_id, menu_name, menu_url, navcode, menu_img, menu_sort, parent_id, menu_level, menu_status, is_menu, top_menu_flag, menu_field, menu_remark) values (6, '修改个人密码', '/user.do?tag=chgPwd', 's6', '', 6, 0, 1, 1, '1', 0, '', '');
alter table tbl_menu AUTO_INCREMENT=7;

insert into tbl_role (role_id, role_name, role_status, role_remark) values (1, '超级管理员', 1, '');
alter table tbl_role AUTO_INCREMENT=2;

insert into tbl_role_menu (role_menu_id, role_id, menu_id, r_select, r_add, r_update, r_del, r_exec) values (1, 1, 1, 1, 1, 1, 1, 1);
insert into tbl_role_menu (role_menu_id, role_id, menu_id, r_select, r_add, r_update, r_del, r_exec) values (2, 1, 2, 1, 1, 1, 1, 1);
insert into tbl_role_menu (role_menu_id, role_id, menu_id, r_select, r_add, r_update, r_del, r_exec) values (3, 1, 3, 1, 1, 1, 1, 1);
insert into tbl_role_menu (role_menu_id, role_id, menu_id, r_select, r_add, r_update, r_del, r_exec) values (4, 1, 4, 1, 1, 1, 1, 1);
insert into tbl_role_menu (role_menu_id, role_id, menu_id, r_select, r_add, r_update, r_del, r_exec) values (5, 1, 5, 1, 1, 1, 1, 1);
insert into tbl_role_menu (role_menu_id, role_id, menu_id, r_select, r_add, r_update, r_del, r_exec) values (6, 1, 6, 1, 1, 1, 1, 1);
alter table tbl_role_menu AUTO_INCREMENT=7;

/*===============================================================*/
/*  admin的密码为ssoAdmin                                        */
/*===============================================================*/
insert into tbl_user(user_id,login_name,user_name,passwd,status) values (1,'admin','administrator','A720BD0603C6465ED4F4420DDB0AFC94',1);
alter table tbl_user AUTO_INCREMENT=2;

insert into tbl_user_role (user_role_id, role_id, user_id) values (1, 1, 1);