/*
Navicat MySQL Data Transfer

Source Server         : 本机
Source Server Version : 50715
Source Host           : 127.0.0.1:3306
Source Database       : spring_boot_demo1

Target Server Type    : MYSQL
Target Server Version : 50715
File Encoding         : 65001

Date: 2016-12-31 15:04:07
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for system_dept
-- ----------------------------
DROP TABLE IF EXISTS `system_dept`;
CREATE TABLE `system_dept` (
  `id` int(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(10) DEFAULT NULL,
  `create_user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_dept
-- ----------------------------
INSERT INTO `system_dept` VALUES ('1', '开发', '2');
INSERT INTO `system_dept` VALUES ('2', '测试', '2');
INSERT INTO `system_dept` VALUES ('3', '运维', '1');
INSERT INTO `system_dept` VALUES ('4', '市场', '1');
INSERT INTO `system_dept` VALUES ('5', '主管', '1');
