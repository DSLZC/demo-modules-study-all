-- 创建用户并授权
create user mall identified by '123456';
create database mall character set utf8;
grant all privileges on mall.* to 'mall'@'localhost' identified by '123456' with grant option;
grant all privileges on mall.* to 'mall'@'%' identified by '123456' with grant option;
flush privileges;

-- 初始化数据
use mall;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for goods_common_history
-- ----------------------------
DROP TABLE IF EXISTS `goods_common_history`;
CREATE TABLE `goods_common_history` (
  `goods_commonid` int(10) unsigned NOT NULL COMMENT '商品公共表id',
  `goods_name` varchar(1024) DEFAULT NULL,
  `store_id` int(10) unsigned NOT NULL COMMENT '店铺id',
  `goods_state` tinyint(3) unsigned NOT NULL COMMENT '商品状态 0下架，1正常，10违规（禁售）',
  `goods_marketprice` decimal(10,2) NOT NULL COMMENT '市场价',
  `is_own_shop` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '是否为平台自营',
  `origin_company_name` varchar(255) DEFAULT NULL,
  `is_cross_border` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '是否为跨境商品 1是，0否',
  `is_show` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '是否显示给渠道用户 1是，0否'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品公共内容历史表';

-- ----------------------------
-- Records of goods_common_history
-- ----------------------------
INSERT INTO `goods_common_history` VALUES ('100232', '日本熊野马油沐浴露', '20', '10', '69.00', '1', null, '0', '1');
INSERT INTO `goods_common_history` VALUES ('106926', '针线盒套装', '95', '1', '13.00', '0', null, '0', '1');

-- ----------------------------
-- Table structure for goods_history
-- ----------------------------
DROP TABLE IF EXISTS `goods_history`;
CREATE TABLE `goods_history` (
  `goods_id` int(10) unsigned NOT NULL COMMENT '商品id(SKU)',
  `goods_commonid` int(10) unsigned NOT NULL COMMENT '商品公共表id',
  `goods_name` varchar(1024) DEFAULT NULL,
  `store_id` int(10) unsigned NOT NULL COMMENT '店铺id',
  `store_name` varchar(50) NOT NULL COMMENT '店铺名称',
  `goods_price` decimal(10,2) NOT NULL COMMENT '商品价格',
  `goods_state` tinyint(3) unsigned NOT NULL COMMENT '商品状态 0下架，1正常，10违规（禁售）',
  `is_own_shop` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '是否为平台自营'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品历史表';

-- ----------------------------
-- Records of goods_history
-- ----------------------------
INSERT INTO `goods_history` VALUES ('105104', '106029', '2016新款时尚潮人休闲全实木手工偏光太阳镜 男女墨镜开车彩膜 统一规格 斑马木紫片', '43', '心匠旗舰店', '158.00', '1', '0');
INSERT INTO `goods_history` VALUES ('105106', '106030', '新款手工木质潮人偏光太阳镜 男女墨镜开车大框圆脸眼镜包邮 统一规格 Y-1自然竹木框橙色偏光', '43', '心匠旗舰店', '158.00', '1', '0');
INSERT INTO `goods_history` VALUES ('106739', '106926', '针线盒套装', '95', '杂货供应店', '9.90', '1', '0');

