drop table if exists sys_object;
create table sys_object
(
	obj_id bigint auto_increment comment '主键'
		primary key,
	obj_world bigint null comment '所属世界',
	obj_type bigint null comment '对象类别',
	obj_name varchar(255) null comment '对象名称',
	obj_birthday varchar(255) null comment '诞生时间',
	obj_deathday varchar(255) null comment '销亡时间',
	creator_id bigint null comment '创造者ID',
	remark varchar(255) null comment '备注信息',
	create_by bigint null comment '创建者',
	gmt_create datetime null comment '创建时间',
	modified_by bigint null comment '更新者',
	gmt_modified datetime null comment '更新时间',
	status tinyint null comment '状态'
)
comment '对象表'
;

