create table if not exists sys_user
(
	user_id bigint auto_increment comment '主键'
		primary key,
	c_id bigint null comment 'Character ID',
	user_name varchar(255) null comment '用户名',
	user_email varchar(255) null comment '账号电子邮箱',
	user_phone varchar(64) null comment '账号电话',
	user_secret varchar(255) null comment '账号验证信息',
	remark varchar(255) null comment '备注信息',
	create_by bigint null comment '创建者',
	gmt_create datetime null comment '创建时间',
	modified_by bigint null comment '更新者',
	gmt_modified datetime null comment '更新时间',
	status tinyint null comment '状态'
)
comment '账号表'
;

create index idx_user_email
	on sys_user (user_email)
;

create index idx_user_name
	on sys_user (user_name)
;

create index idx_user_phone
	on sys_user (user_phone)
;

