create table if not exists organization
(
	org_id bigint auto_increment comment '主键'
		primary key,
	org_world bigint null comment '所属世界',
	org_type bigint null comment '组织类别',
	org_name varchar(255) null comment '组织名称',
	org_birthday varchar(255) null comment '组织建立时间',
	org_deathday varchar(255) null comment '组织死亡时间',
	parent_org_id bigint null comment '上级组织ID',
	remark varchar(255) null comment '备注信息',
	create_by bigint null comment '创建者',
	gmt_create datetime null comment '创建时间',
	modified_by bigint null comment '更新者',
	gmt_modified datetime null comment '更新时间',
	status tinyint null comment '状态'
)
comment '组织表'
;

