DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `stock` mediumint(8) unsigned NOT NULL,
  `price` decimal(6,2) NOT NULL,
  `version` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES ('1', 'iPhone7 plus', '10', '1000.00', '1');
INSERT INTO `goods` VALUES ('2', '小米手环', '10', '99.00', '1');
INSERT INTO `goods` VALUES ('3', '男士白色时髦短袖', '10', '20.00', '1');
INSERT INTO `goods` VALUES ('4', 'spring 框架视频300G', '10', '15.00', '1');
INSERT INTO `goods` VALUES ('5', '美的电风扇 清爽一夏', '10', '45.00', '1');

-- ----------------------------
-- Table structure for order_item
-- ----------------------------
DROP TABLE IF EXISTS `order_item`;
CREATE TABLE `order_item` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `num` smallint(5) unsigned NOT NULL,
  `goods_id` bigint(20) unsigned NOT NULL,
  `user_id` bigint(20) unsigned NOT NULL,
  `state` tinyint(3) unsigned NOT NULL,
  `add_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of order_item
-- ----------------------------

-- ----------------------------
-- Table structure for submit_order_item
-- ----------------------------
DROP TABLE IF EXISTS `submit_order_item`;
CREATE TABLE `submit_order_item` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `num` smallint(5) unsigned NOT NULL,
  `goods_id` bigint(20) unsigned NOT NULL,
  `user_id` bigint(20) unsigned NOT NULL,
  `code` varchar(20) NOT NULL,
  `success` tinyint(1) unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
