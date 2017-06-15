-- ----------------------------
-- Table structure for app_crypto
-- ----------------------------
DROP TABLE IF EXISTS `app_crypto`;
CREATE TABLE `app_crypto` (
  `cpt_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `sub_user_id` bigint(20) NOT NULL COMMENT '账号ID',
  `user_app_desc` varchar(128) NOT NULL COMMENT '记录描述',
  `app_id` bigint(20) DEFAULT NULL COMMENT '应用ID',
  `pwd_basic` varchar(255) DEFAULT NULL COMMENT '基础密码',
  `pwd_length` int(11) DEFAULT NULL COMMENT '密码长度',
  `pwd_complexity` tinyint(4) DEFAULT NULL COMMENT '密码复杂度',
  `pwd_time` datetime DEFAULT NULL COMMENT '操作时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `add_time` datetime DEFAULT NULL COMMENT '添加时间',
  `mod_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`cpt_id`),
  UNIQUE KEY `index_uid_desc` (`sub_user_id`,`user_app_desc`) USING BTREE COMMENT '用户ID&应用描述联合唯一索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='应用加密信息表';

-- ----------------------------
-- Table structure for sys_application
-- ----------------------------
DROP TABLE IF EXISTS `sys_application`;
CREATE TABLE `sys_application` (
  `app_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `app_name` varchar(255) DEFAULT NULL COMMENT '应用名称',
  `app_org_id` bigint(20) DEFAULT NULL COMMENT '应用所属组织ID',
  `app_type` bigint(20) DEFAULT NULL COMMENT '应用类别',
  `parent_app_id` bigint(20) DEFAULT NULL COMMENT '父应用ID',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `add_time` datetime DEFAULT NULL COMMENT '添加时间',
  `mod_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`app_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='应用表';

-- ----------------------------
-- Table structure for sys_character
-- ----------------------------
DROP TABLE IF EXISTS `sys_character`;
CREATE TABLE `sys_character` (
  `c_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `c_world` bigint(4) DEFAULT NULL COMMENT '所属世界',
  `c_species` bigint(20) DEFAULT NULL COMMENT '类型',
  `c_name` varchar(255) DEFAULT NULL COMMENT '名字',
  `c_birthday` varchar(255) DEFAULT NULL COMMENT '出生时间',
  `c_deathday` varchar(255) DEFAULT NULL COMMENT '死亡时间',
  `c_father_id` bigint(20) DEFAULT NULL COMMENT '父亲ID',
  `c_mother_id` bigint(20) DEFAULT NULL COMMENT '母亲ID',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `add_time` datetime DEFAULT NULL COMMENT '添加时间',
  `mod_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='生物表';

-- ----------------------------
-- Table structure for sys_organization
-- ----------------------------
DROP TABLE IF EXISTS `sys_organization`;
CREATE TABLE `sys_organization` (
  `org_id` bigint(20) NOT NULL COMMENT '主键',
  `org_world` bigint(20) DEFAULT NULL COMMENT '所属世界',
  `org_type` bigint(20) DEFAULT NULL COMMENT '组织类别',
  `org_name` varchar(255) DEFAULT NULL COMMENT '组织名称',
  `org_birthday` varchar(255) DEFAULT NULL COMMENT '组织建立时间',
  `org_deathday` varchar(255) DEFAULT NULL COMMENT '组织死亡时间',
  `parent_org_id` bigint(20) DEFAULT NULL COMMENT '上级组织ID',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `add_time` datetime DEFAULT NULL COMMENT '添加时间',
  `mod_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`org_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='组织表';

-- ----------------------------
-- Table structure for sys_sub_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_sub_user`;
CREATE TABLE `sys_sub_user` (
  `sub_user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(20) DEFAULT NULL COMMENT '主账号ID',
  `app_id` bigint(20) DEFAULT NULL COMMENT '所属应用ID',
  `sub_user_flag` tinyint(4) DEFAULT NULL COMMENT '子账号标志信息',
  `sub_user_no` varchar(255) DEFAULT NULL COMMENT '子账号真实ID',
  `sub_user_name` varchar(255) DEFAULT NULL COMMENT '子用户名',
  `sub_user_email` varchar(255) DEFAULT NULL COMMENT '子账号电子邮箱',
  `sub_user_phone` varchar(64) DEFAULT NULL COMMENT '子账号电话',
  `sub_user_secret` varchar(255) DEFAULT NULL COMMENT '子账号验证信息',
  `parent_suser_id` bigint(20) DEFAULT NULL COMMENT '父账号ID',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `add_time` datetime DEFAULT NULL COMMENT '添加时间',
  `mod_time` datetime DEFAULT NULL COMMENT '更新时间',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`sub_user_id`),
  KEY `index_user_app` (`sub_user_name`,`app_id`,`sub_user_flag`) USING BTREE,
  KEY `index_email_app` (`sub_user_email`,`app_id`,`sub_user_flag`) USING BTREE,
  KEY `index_phone_app` (`sub_user_phone`,`app_id`,`sub_user_flag`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='子账号表';

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `c_id` bigint(20) DEFAULT NULL COMMENT 'Character ID',
  `user_name` varchar(255) DEFAULT NULL COMMENT '用户名',
  `user_email` varchar(255) DEFAULT NULL COMMENT '账号电子邮箱',
  `user_phone` varchar(64) DEFAULT NULL COMMENT '账号电话',
  `user_secret` varchar(255) DEFAULT NULL COMMENT '账号验证信息',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `add_time` datetime DEFAULT NULL COMMENT '添加时间',
  `mod_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`user_id`),
  KEY `index_user_name` (`user_name`) USING BTREE,
  KEY `index_user_email` (`user_email`) USING BTREE,
  KEY `index_user_phone` (`user_phone`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='账号表';
