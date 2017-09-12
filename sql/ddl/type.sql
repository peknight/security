create table if not exists type
(
	type_id bigint auto_increment comment '主键'
		primary key,
	world bigint null comment '所属世界',
	org_id bigint null comment '所属组织',
	name varchar(255) null comment '类型名称',
  level varchar(255) null comment '类型级别',
	parent_type_id bigint null comment '父类型ID',
	remark varchar(255) null comment '备注信息',
	create_by bigint null comment '创建者',
	gmt_create datetime null comment '创建时间',
	modified_by bigint null comment '更新者',
	gmt_modified datetime null comment '更新时间',
	status tinyint null comment '状态'
)
comment '类型表'
;

