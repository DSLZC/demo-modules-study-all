/*
Navicat MySQL Data Transfer

Source Server         : 本机
Source Server Version : 50715
Source Host           : 127.0.0.1:3306
Source Database       : spring_boot_demo

Target Server Type    : MYSQL
Target Server Version : 50715
File Encoding         : 65001

Date: 2016-12-31 15:03:39
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for system_role
-- ----------------------------
DROP TABLE IF EXISTS `system_role`;
CREATE TABLE `system_role` (
  `id` tinyint(3) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `enabled` tinyint(3) unsigned NOT NULL,
  `role_ordinal` tinyint(3) unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_role
-- ----------------------------
INSERT INTO `system_role` VALUES ('1', 'super_manager', '1', '1');
INSERT INTO `system_role` VALUES ('2', 'manager', '1', '2');
INSERT INTO `system_role` VALUES ('3', 'system_user', '1', '3');
INSERT INTO `system_role` VALUES ('4', 'user', '1', '4');
INSERT INTO `system_role` VALUES ('5', 'visitor', '1', '5');

-- ----------------------------
-- Table structure for system_user
-- ----------------------------
DROP TABLE IF EXISTS `system_user`;
CREATE TABLE `system_user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `password` varchar(50) NOT NULL,
  `name` varchar(20) DEFAULT NULL,
  `dept_id` int(11) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_user
-- ----------------------------
INSERT INTO `system_user` VALUES ('1', '13811111111', '111111', '超级管理员', '5');
INSERT INTO `system_user` VALUES ('2', '13822222222', '222222', '管理员', '4');
INSERT INTO `system_user` VALUES ('3', '13833333333', '333333', '系统用户', '1');
INSERT INTO `system_user` VALUES ('4', '13844444444', '444444', '普通用户', '3');
INSERT INTO `system_user` VALUES ('5', '13855555555', '555555', '访客', '2');

-- ----------------------------
-- Table structure for system_user_role
-- ----------------------------
DROP TABLE IF EXISTS `system_user_role`;
CREATE TABLE `system_user_role` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `role_id` tinyint(3) unsigned NOT NULL,
  `system_user_id` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKnp61n3syn415rmbwvhnw87u3a` (`role_id`),
  KEY `FK5soqc4583re86busn1idb194d` (`system_user_id`),
  CONSTRAINT `FK5soqc4583re86busn1idb194d` FOREIGN KEY (`system_user_id`) REFERENCES `system_user` (`id`),
  CONSTRAINT `FKnp61n3syn415rmbwvhnw87u3a` FOREIGN KEY (`role_id`) REFERENCES `system_role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_user_role
-- ----------------------------
INSERT INTO `system_user_role` VALUES ('1', '1', '1');
INSERT INTO `system_user_role` VALUES ('2', '2', '1');
INSERT INTO `system_user_role` VALUES ('3', '2', '2');
INSERT INTO `system_user_role` VALUES ('4', '3', '2');
INSERT INTO `system_user_role` VALUES ('5', '3', '3');
INSERT INTO `system_user_role` VALUES ('6', '4', '4');
INSERT INTO `system_user_role` VALUES ('7', '5', '5');
