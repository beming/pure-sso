/*==============================================================*/
/* DBMS name:      PostgreSQL 8                                 */
/* Created on:     2013/7/13 星期六 下午 17:27:01                    */
/*==============================================================*/


drop index tbl_department_pk;

drop table tbl_department;

drop index tbl_menu_pk;

drop table tbl_menu;

drop index tbl_operator_log_pk;

drop table tbl_operator_log;

drop index tbl_post_pk;

drop table tbl_post;

drop index tbl_role_pk;

drop table tbl_role;

drop index tbl_role_menu_pk;

drop table tbl_role_menu;

drop index tbl_user_pk;

drop table tbl_user;

drop index tbl_user_role_pk;

drop table tbl_user_role;

/*==============================================================*/
/* Table: tbl_department                                        */
/*==============================================================*/
create table tbl_department (
   dep_id               serial not null,
   dep_code             varchar(32)          null,
   dep_name             varchar(32)          not null,
   dep_status           numeric(2)           not null,
   dep_linker           varchar(32)          null,
   dep_phone            varchar(32)          null,
   dep_email            varchar(32)          null,
   dep_teacher          varchar(1024)        null,
   dep_remark           varchar(1024)        null,
   constraint pk_tbl_department primary key (dep_id)
);

/*==============================================================*/
/* Index: tbl_department_pk                                     */
/*==============================================================*/
create unique index tbl_department_pk on tbl_department (
dep_id
);

/*==============================================================*/
/* Table: tbl_menu                                              */
/*==============================================================*/
create table tbl_menu (
   menu_id              serial not null,
   menu_name            varchar(32)          not null,
   menu_url             varchar(32)          null,
   navcode              varchar(32)          null,
   menu_img             varchar(64)          null,
   menu_sort            int4                 null,
   parent_id            int4                 not null,
   menu_level           int4                 null,
   menu_status          numeric(1)           null,
   is_menu              numeric(1)           null,
   top_menu_flag        int4                 null,
   menu_field           varchar(32)          null,
   menu_remark          varchar(1024)        null,
   constraint pk_tbl_menu primary key (menu_id)
);

comment on column tbl_menu.parent_id is
'0为模块
其他为子菜单';

/*==============================================================*/
/* Index: tbl_menu_pk                                           */
/*==============================================================*/
create unique index tbl_menu_pk on tbl_menu (
menu_id
);

/*==============================================================*/
/* Table: tbl_operator_log                                      */
/*==============================================================*/
create table tbl_operator_log (
   operator_id          serial not null,
   operator_user        int4                 not null,
   operator_ip          varchar(32)          not null,
   operator_level       varchar(32)          not null,
   operator_time        date                 not null,
   operator_content     varchar(1024)        not null,
   constraint pk_tbl_operator_log primary key (operator_id)
);

/*==============================================================*/
/* Index: tbl_operator_log_pk                                   */
/*==============================================================*/
create unique index tbl_operator_log_pk on tbl_operator_log (
operator_id
);

/*==============================================================*/
/* Table: tbl_post                                              */
/*==============================================================*/
create table tbl_post (
   post_id              serial not null,
   post_name            varchar(32)          not null,
   post_status          numeric(2)           not null,
   post_remark          varchar(1024)        null,
   constraint pk_tbl_post primary key (post_id)
);

/*==============================================================*/
/* Index: tbl_post_pk                                           */
/*==============================================================*/
create unique index tbl_post_pk on tbl_post (
post_id
);

/*==============================================================*/
/* Table: tbl_role                                              */
/*==============================================================*/
create table tbl_role (
   role_id              serial not null,
   role_name            varchar(32)          not null,
   role_status          numeric(2)           not null,
   role_remark          varchar(1024)        null,
   constraint pk_tbl_role primary key (role_id)
);

/*==============================================================*/
/* Index: tbl_role_pk                                           */
/*==============================================================*/
create unique index tbl_role_pk on tbl_role (
role_id
);

/*==============================================================*/
/* Table: tbl_role_menu                                         */
/*==============================================================*/
create table tbl_role_menu (
   role_menu_id         serial not null,
   role_id              int4                 not null,
   menu_id              int4                 not null,
   r_select             numeric(1)           not null,
   r_add                numeric(1)           not null,
   r_update             numeric(1)           not null,
   r_del                numeric(1)           not null,
   r_exec               numeric(1)           null,
   constraint pk_tbl_role_menu primary key (role_menu_id)
);

/*==============================================================*/
/* Index: tbl_role_menu_pk                                      */
/*==============================================================*/
create unique index tbl_role_menu_pk on tbl_role_menu (
role_menu_id
);

/*==============================================================*/
/* Table: tbl_user                                              */
/*==============================================================*/
create table tbl_user (
   user_id              serial not null,
   dep_id               int4                 null,
   post_id              int4                 null,
   login_name           varchar(32)          not null,
   user_name            varchar(32)          not null,
   passwd               varchar(32)          not null,
   chg_pwd_date         date                 null,
   status               numeric(2)           not null,
   id_type              numeric(1)           null,
   id_num               varchar(32)          null,
   sexy                 numeric(1)           null,
   birthday             date                 null,
   phone                varchar(32)          null,
   mobile               varchar(32)          null,
   addr                 varchar(1024)        null,
   post_code            varchar(32)          null,
   email                varchar(32)          null,
   last_login_time      date                 null,
   remark               varchar(1024)        null,
   constraint pk_tbl_user primary key (user_id)
);

comment on column tbl_user.status is
'0、-1 ：无效
1：有效
9：被锁';

comment on column tbl_user.sexy is
'0 female
1male
3else';

/*==============================================================*/
/* Index: tbl_user_pk                                           */
/*==============================================================*/
create unique index tbl_user_pk on tbl_user (
user_id
);

/*==============================================================*/
/* Table: tbl_user_role                                         */
/*==============================================================*/
create table tbl_user_role (
   user_role_id         serial not null,
   role_id              int4                 not null,
   user_id              int4                 not null,
   constraint pk_tbl_user_role primary key (user_role_id)
);

/*==============================================================*/
/* Index: tbl_user_role_pk                                      */
/*==============================================================*/
create unique index tbl_user_role_pk on tbl_user_role (
user_role_id
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
SELECT setval('tbl_menu_menu_id_seq', max(menu_id)) FROM tbl_menu;

insert into tbl_role (role_id, role_name, role_status, role_remark) values (1, '超级管理员', 1, '');
SELECT setval('tbl_role_role_id_seq', max(role_id)) FROM tbl_role;

insert into tbl_role_menu (role_menu_id, role_id, menu_id, r_select, r_add, r_update, r_del, r_exec) values (1, 1, 1, 1, 1, 1, 1, 1);
insert into tbl_role_menu (role_menu_id, role_id, menu_id, r_select, r_add, r_update, r_del, r_exec) values (2, 1, 2, 1, 1, 1, 1, 1);
insert into tbl_role_menu (role_menu_id, role_id, menu_id, r_select, r_add, r_update, r_del, r_exec) values (3, 1, 3, 1, 1, 1, 1, 1);
insert into tbl_role_menu (role_menu_id, role_id, menu_id, r_select, r_add, r_update, r_del, r_exec) values (4, 1, 4, 1, 1, 1, 1, 1);
insert into tbl_role_menu (role_menu_id, role_id, menu_id, r_select, r_add, r_update, r_del, r_exec) values (5, 1, 5, 1, 1, 1, 1, 1);
insert into tbl_role_menu (role_menu_id, role_id, menu_id, r_select, r_add, r_update, r_del, r_exec) values (6, 1, 6, 1, 1, 1, 1, 1);
SELECT setval('tbl_role_menu_role_menu_id_seq', max(role_menu_id)) FROM tbl_role_menu;

/*===============================================================*/
/*  admin的密码为ssoAdmin                                        */
/*===============================================================*/
insert into tbl_user(user_id,login_name,user_name,passwd,status) values (1,'admin','administrator','A720BD0603C6465ED4F4420DDB0AFC94',1);
SELECT setval('tbl_user_user_id_seq', max(user_id)) FROM tbl_user;

insert into tbl_user_role (user_role_id, role_id, user_id) values (1, 1, 1);