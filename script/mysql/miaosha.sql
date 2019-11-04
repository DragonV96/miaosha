DROP DATABASE  IF EXISTS miaosha;
CREATE DATABASE miaosha;
USE miaosha;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品ID',
  `goods_name` varchar(16) NOT NULL COMMENT '商品名称',
  `goods_title` varchar(64) NOT NULL COMMENT '商品标题',
  `goods_img` varchar(64) NOT NULL COMMENT '商品图片',
  `goods_detail` longtext COMMENT '商品的详情介绍',
  `goods_price` decimal(10,2) DEFAULT '0.00' COMMENT '商品单价',
  `goods_stock` int(11) DEFAULT '0' COMMENT '商品库存，-1表示没有限制',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='普通商品表';

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES ('1', 'iphoneX', 'Apple iPhone X (A1865) 64GB 深空灰色 移动联通电信4G手机', '/img/iphonex.png', 'Apple iPhone X (A1865) 64GB 深空灰色 移动联通电信4G手机', '8765.00', '10000');
INSERT INTO `goods` VALUES ('2', '华为P30', '华为 HUAWEI P30 Pro 超感光徕卡四摄10倍混合变焦麒麟980芯片屏内指纹 8GB+256GB', '/img/p30.png', '华为 HUAWEI P30 Pro 超感光徕卡四摄10倍混合变焦麒麟980芯片屏内指纹 8GB+256GB极光色全网通版双4G手机', '5988.00', '-1');

-- ----------------------------
-- Table structure for ms_goods
-- ----------------------------
DROP TABLE IF EXISTS `ms_goods`;
CREATE TABLE `ms_goods` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '秒杀的商品表',
  `goods_id` bigint(20) NOT NULL COMMENT '商品id',
  `ms_price` decimal(10,0) DEFAULT '0' COMMENT '秒杀价',
  `stock_count` int(11) DEFAULT NULL COMMENT '库存数量',
  `start_date` datetime DEFAULT NULL COMMENT '秒杀开始时间',
  `end_date` datetime DEFAULT NULL COMMENT '秒杀结束时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='秒杀商品表';

-- ----------------------------
-- Records of ms_goods
-- ----------------------------
INSERT INTO `ms_goods` VALUES ('1', '1', '0', '10', '2019-06-02 21:49:22', '2019-07-01 11:08:02');
INSERT INTO `ms_goods` VALUES ('2', '2', '0', '9', '2019-06-02 21:49:36', '2019-07-01 00:58:44');

-- ----------------------------
-- Table structure for ms_order
-- ----------------------------
DROP TABLE IF EXISTS `ms_order`;
CREATE TABLE `ms_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `order_id` bigint(20) DEFAULT NULL COMMENT '订单id',
  `goods_id` bigint(20) DEFAULT NULL COMMENT '商品id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `u_uid_gid` (`user_id`,`goods_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COMMENT='秒杀订单表';

-- ----------------------------
-- Records of ms_order
-- ----------------------------
INSERT INTO `ms_order` VALUES ('11', '12345678910', '12', '2');

-- ----------------------------
-- Table structure for ms_user
-- ----------------------------
DROP TABLE IF EXISTS `ms_user`;
CREATE TABLE `ms_user` (
  `id` bigint(20) NOT NULL COMMENT '用户id，手机号',
  `nickname` varchar(255) NOT NULL COMMENT 'MD5(MD5(passw明文+固定salt)+salt)',
  `password` varchar(32) DEFAULT NULL,
  `salt` varchar(10) DEFAULT NULL,
  `head` varchar(128) DEFAULT NULL COMMENT '头像，云存储的ID',
  `register_date` datetime DEFAULT NULL COMMENT '注册时间',
  `last_login_date` datetime DEFAULT NULL COMMENT '上次登录时间',
  `login_count` int(11) DEFAULT NULL COMMENT '登录次数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='秒杀用户表';

-- ----------------------------
-- Records of ms_user
-- ----------------------------
INSERT INTO `ms_user` VALUES ('12345678910', 'glw', '555148fc0e07b46c88269cf4d6390418', 'f56wet15', null, '2019-05-07 17:25:22', '2019-05-07 17:25:28', '1');

-- ----------------------------
-- Table structure for order_info
-- ----------------------------
DROP TABLE IF EXISTS `order_info`;
CREATE TABLE `order_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `goods_id` bigint(20) NOT NULL COMMENT '商品id',
  `delivery_addr_id` bigint(20) NOT NULL COMMENT '收货地址id',
  `goods_name` varchar(16) NOT NULL COMMENT '冗余过来的商品名称',
  `goods_count` int(11) DEFAULT '0' COMMENT '商品数量',
  `goods_price` decimal(10,0) DEFAULT '0' COMMENT '商品单价',
  `order_channel` tinyint(4) DEFAULT '0' COMMENT '1-pc,2-android,3-ios',
  `status` tinyint(4) DEFAULT '0' COMMENT '1-已支付，2-已发货，3-已收货，4-已退款，5-已完成',
  `create_time` datetime DEFAULT NULL COMMENT '订单创建的时间',
  `pay_date` datetime DEFAULT NULL COMMENT '支付时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='订单信息表';

-- ----------------------------
-- Records of order_info
-- ----------------------------
INSERT INTO `order_info` VALUES ('12', '12345678910', '2', '0', '华为P30', '1', '5988', '1', '0', '2019-06-02 22:39:35', null);
