create table if not exists world
(
	world_id bigint auto_increment comment '主键'
		primary key,
	name varchar(255) null comment '世界名称',
	parent_world_id bigint null comment '父世界ID',
	creator_id bigint null comment '创造者ID',
	remark varchar(255) null comment '备注信息',
	create_by bigint null comment '创建者',
	gmt_create datetime null comment '创建时间',
	modified_by bigint null comment '更新者',
	gmt_modified datetime null comment '更新时间',
	status tinyint null comment '状态'
)
comment '世界表'
;

