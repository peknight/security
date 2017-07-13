drop table if exists application;
create table application
(
	app_id bigint auto_increment comment '主键'
		primary key,
	app_name varchar(255) null comment '应用名称',
	org_id bigint null comment '应用所属组织ID',
	app_type bigint null comment '应用类别',
	parent_app_id bigint null comment '父应用ID',
	remark varchar(255) null comment '备注信息',
	create_by bigint null comment '创建者',
	gmt_create datetime null comment '创建时间',
	modified_by bigint null comment '更新者',
	gmt_modified datetime null comment '更新时间',
	status tinyint null comment '状态'
)
comment '应用表'
;

