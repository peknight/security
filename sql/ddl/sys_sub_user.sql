drop table if exists sys_sub_user;
create table sys_sub_user
(
	sub_user_id bigint auto_increment comment '主键'
		primary key,
	user_id bigint null comment '主账号ID',
	app_id bigint null comment '所属应用ID',
	sub_user_flag tinyint null comment '子账号标志信息',
	sub_user_no varchar(255) null comment '子账号真实ID',
	sub_user_name varchar(255) null comment '子用户名',
	sub_user_email varchar(255) null comment '子账号电子邮箱',
	sub_user_phone varchar(64) null comment '子账号电话',
	sub_user_secret varchar(255) null comment '子账号验证信息',
	parent_suser_id bigint null comment '父账号ID',
	remark varchar(255) null comment '备注信息',
	create_by bigint null comment '创建者',
	gmt_create datetime null comment '创建时间',
	modified_by bigint null comment '更新者',
	gmt_modified datetime null comment '更新时间',
	status tinyint null comment '状态'
)
comment '子账号表'
;

create index idx_email_app
	on sys_sub_user (sub_user_email, app_id, sub_user_flag)
;

create index idx_phone_app
	on sys_sub_user (sub_user_phone, app_id, sub_user_flag)
;

create index idx_user_app
	on sys_sub_user (sub_user_name, app_id, sub_user_flag)
;

