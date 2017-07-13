drop table if exists sys_character;
create table sys_character
(
	c_id bigint auto_increment comment '主键'
		primary key,
	c_world bigint(4) unsigned null comment '所属世界',
	c_species bigint null comment '类型',
	c_name varchar(255) null comment '名字',
	c_birthday varchar(255) null comment '诞生时间',
	c_deathday varchar(255) null comment '死亡时间',
	c_father_id bigint null comment '父亲ID',
	c_mother_id bigint null comment '母亲ID',
	remark varchar(255) null comment '备注信息',
	create_by bigint null comment '创建者',
	gmt_create datetime null comment '创建时间',
	modified_by bigint null comment '更新者',
	gmt_modified datetime null comment '更新时间',
	status tinyint null comment '状态'
)
comment '生物表'
;

