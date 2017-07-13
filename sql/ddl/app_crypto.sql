drop table if exists app_crypto;
create table app_crypto
(
	cpt_id bigint auto_increment comment '主键'
		primary key,
	sub_user_id bigint not null comment '账号ID',
	user_app_desc varchar(128) not null comment '记录描述',
	app_id bigint null comment '应用ID',
	pwd_basic varchar(255) null comment '基础密码',
	pwd_length int null comment '密码长度',
	pwd_complexity tinyint null comment '密码复杂度',
	pwd_time datetime null comment '操作时间',
	remark varchar(255) null comment '备注信息',
	create_by bigint null comment '创建者',
	gmt_create datetime null comment '创建时间',
	modified_by bigint null comment '更新者',
	gmt_modified datetime null comment '更新时间',
	status tinyint null comment '状态',
	constraint uk_uid_desc
	unique (sub_user_id, user_app_desc)
)
	comment '应用加密信息表'
;

