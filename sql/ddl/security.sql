-- ----------------------------
-- Table structure for application
-- ----------------------------
DROP TABLE IF EXISTS `application`;
CREATE TABLE `application` (
  `app_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `app_name` varchar(128) DEFAULT NULL COMMENT '应用名称',
  `app_org_id` bigint(20) DEFAULT NULL COMMENT '应用所属组织ID',
  `app_type` int(11) DEFAULT NULL COMMENT '应用类别',
  `parent_app_id` int(11) DEFAULT NULL COMMENT '父应用ID',
  `app_info` varchar(255) DEFAULT NULL COMMENT '其它信息',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `add_time` datetime DEFAULT NULL COMMENT '添加时间',
  `mod_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`app_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for character
-- ----------------------------
DROP TABLE IF EXISTS `character`;
CREATE TABLE `character` (
  `c_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `c_world` int(4) DEFAULT NULL COMMENT '所属世界',
  `c_type` int(11) DEFAULT NULL COMMENT '类型',
  `c_birthday` varchar(255) DEFAULT NULL COMMENT '出生时间',
  `c_deathday` varchar(255) DEFAULT NULL COMMENT '死亡时间',
  `c_mother_id` bigint(20) DEFAULT NULL COMMENT '母亲ID',
  `c_father_id` bigint(20) DEFAULT NULL COMMENT '父亲ID',
  `c_info` varchar(255) DEFAULT NULL COMMENT '其它信息',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `add_time` datetime DEFAULT NULL COMMENT '添加时间',
  `mod_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for password
-- ----------------------------
DROP TABLE IF EXISTS `password`;
CREATE TABLE `password` (
  `pwd_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `sub_user_id` int(11) NOT NULL COMMENT '账号ID',
  `user_app_desc` varchar(128) NOT NULL COMMENT '记录描述',
  `app_id` int(11) NOT NULL COMMENT '应用ID',
  `pwd_basic` varchar(255) DEFAULT NULL COMMENT '基础密码',
  `app_user_id` varchar(128) DEFAULT NULL COMMENT '应用内用户ID',
  `app_user_name` varchar(128) DEFAULT NULL COMMENT '应用内用户名',
  `app_user_email` varchar(128) DEFAULT NULL COMMENT '应用内电子邮箱',
  `app_user_phone` varchar(32) DEFAULT NULL COMMENT '应用内电话',
  `pwd_length` int(11) DEFAULT NULL COMMENT '密码长度',
  `pwd_complexity` tinyint(4) DEFAULT NULL COMMENT '密码复杂度',
  `pwd_time` datetime DEFAULT NULL COMMENT '操作时间',
  `pwd_info` varchar(255) DEFAULT NULL COMMENT '其它信息',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `add_time` datetime DEFAULT NULL COMMENT '添加时间',
  `mod_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`pwd_id`),
  UNIQUE KEY `uid_desc_index` (`sub_user_id`,`user_app_desc`) USING BTREE COMMENT '用户ID&应用描述联合唯一索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for sub_user
-- ----------------------------
DROP TABLE IF EXISTS `sub_user`;
CREATE TABLE `sub_user` (
  `sub_user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) DEFAULT NULL COMMENT '主账号ID',
  `app_id` int(11) DEFAULT NULL COMMENT '所属应用ID',
  `sub_user_flag` tinyint(4) DEFAULT NULL COMMENT '子账号标志信息',
  `sub_user_name` varchar(32) DEFAULT NULL COMMENT '子用户名',
  `sub_user_email` varchar(255) DEFAULT NULL COMMENT '子账号电子邮箱',
  `sub_user_phone` varchar(32) DEFAULT NULL COMMENT '子账号电话',
  `sub_user_secret` varchar(255) DEFAULT NULL COMMENT '子账号验证信息',
  `sub_user_info` varchar(255) DEFAULT NULL COMMENT '其它信息',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `add_time` datetime DEFAULT NULL COMMENT '添加时间',
  `mod_time` datetime DEFAULT NULL COMMENT '更新时间',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`sub_user_id`),
  UNIQUE KEY `suname_app_index` (`sub_user_name`,`app_id`,`sub_user_flag`) USING BTREE,
  UNIQUE KEY `suemail_app_index` (`sub_user_email`,`app_id`,`sub_user_flag`) USING BTREE,
  UNIQUE KEY `suphone_app_index` (`sub_user_phone`,`app_id`,`sub_user_flag`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for user_p
-- ----------------------------
DROP TABLE IF EXISTS `user_p`;
CREATE TABLE `user_p` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `c_id` bigint(20) DEFAULT NULL COMMENT 'Character ID',
  `app_id` int(11) DEFAULT NULL COMMENT '所属应用ID',
  `user_name` varchar(32) DEFAULT NULL COMMENT '用户名',
  `user_email` varchar(255) DEFAULT NULL COMMENT '账号电子邮箱',
  `user_phone` varchar(32) DEFAULT NULL COMMENT '账号电话',
  `user_secret` varchar(255) DEFAULT NULL COMMENT '账号验证信息',
  `user_info` varchar(255) DEFAULT NULL COMMENT '其它信息',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `add_time` datetime DEFAULT NULL COMMENT '添加时间',
  `mod_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `name_app_index` (`user_name`,`app_id`) USING HASH,
  UNIQUE KEY `email_app_index` (`user_email`,`app_id`) USING HASH,
  UNIQUE KEY `phone_app_index` (`user_phone`,`app_id`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
