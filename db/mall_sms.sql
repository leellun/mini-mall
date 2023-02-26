/*
 Navicat Premium Data Transfer

 Source Server         : mallcloud
 Source Server Type    : MySQL
 Source Server Version : 80031
 Source Host           : 192.168.10.10:31324
 Source Schema         : mall_sms

 Target Server Type    : MySQL
 Target Server Version : 80031
 File Encoding         : 65001

 Date: 27/02/2023 00:00:23
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sms_carousel
-- ----------------------------
DROP TABLE IF EXISTS `sms_carousel`;
CREATE TABLE `sms_carousel`  (
  `carousel_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '首页轮播图主键id',
  `carousel_url` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '' COMMENT '轮播图',
  `redirect_url` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '\'##\'' COMMENT '点击后的跳转地址(默认不跳转)',
  `carousel_rank` int(0) NOT NULL DEFAULT 0 COMMENT '排序值(字段越大越靠前)',
  `is_deleted` tinyint(0) NOT NULL DEFAULT 0 COMMENT '删除标识字段(0-未删除 1-已删除)',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `create_user` int(0) NOT NULL DEFAULT 0 COMMENT '创建者id',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `update_user` int(0) NOT NULL DEFAULT 0 COMMENT '修改者id',
  PRIMARY KEY (`carousel_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sms_carousel
-- ----------------------------
INSERT INTO `sms_carousel` VALUES (1, 'https://newbee-mall.oss-cn-beijing.aliyuncs.com/images/banner2.jpg', '##', 200, 1, '2021-08-23 17:50:45', 0, '2021-11-10 00:23:01', 0);
INSERT INTO `sms_carousel` VALUES (2, 'https://newbee-mall.oss-cn-beijing.aliyuncs.com/images/banner1.png', 'https://juejin.cn/book/7085254558678515742', 13, 0, '2021-11-29 00:00:00', 0, '2023-02-26 16:46:43', 0);
INSERT INTO `sms_carousel` VALUES (3, 'https://newbee-mall.oss-cn-beijing.aliyuncs.com/images/banner3.jpg', '##', 0, 1, '2021-09-18 18:26:38', 0, '2021-11-10 00:23:01', 0);
INSERT INTO `sms_carousel` VALUES (5, 'https://newbee-mall.oss-cn-beijing.aliyuncs.com/images/banner2.png', 'https://juejin.cn/book/7085254558678515742', 0, 0, '2021-11-29 00:00:00', 0, '2021-11-29 00:00:00', 0);
INSERT INTO `sms_carousel` VALUES (6, 'https://newbee-mall.oss-cn-beijing.aliyuncs.com/images/banner1.png', '##', 101, 1, '2021-09-19 23:37:40', 0, '2021-11-07 00:15:52', 0);
INSERT INTO `sms_carousel` VALUES (7, 'https://newbee-mall.oss-cn-beijing.aliyuncs.com/images/banner2.png', '##', 99, 1, '2021-09-19 23:37:58', 0, '2021-10-22 00:15:01', 0);
INSERT INTO `sms_carousel` VALUES (8, 'http://192.168.100.100:31582/test/2023-02-26/164304676785900.jpg', 'http://localhost:8081/sms/carousel', 1, 0, '2023-02-26 16:45:17', 0, '2023-02-26 16:45:17', 0);

-- ----------------------------
-- Table structure for sms_index_config
-- ----------------------------
DROP TABLE IF EXISTS `sms_index_config`;
CREATE TABLE `sms_index_config`  (
  `config_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '首页配置项主键id',
  `config_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '' COMMENT '显示字符(配置搜索时不可为空，其他可为空)',
  `config_type` tinyint(0) NOT NULL DEFAULT 0 COMMENT '1-搜索框热搜 2-搜索下拉框热搜 3-(首页)热销商品 4-(首页)新品上线 5-(首页)为你推荐',
  `goods_id` bigint(0) NOT NULL DEFAULT 0 COMMENT '商品id 默认为0',
  `redirect_url` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '##' COMMENT '点击后的跳转地址(默认不跳转)',
  `config_rank` int(0) NOT NULL DEFAULT 0 COMMENT '排序值(字段越大越靠前)',
  `is_deleted` tinyint(0) NOT NULL DEFAULT 0 COMMENT '删除标识字段(0-未删除 1-已删除)',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `create_user` int(0) NOT NULL DEFAULT 0 COMMENT '创建者id',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '最新修改时间',
  `update_user` int(0) NULL DEFAULT 0 COMMENT '修改者id',
  PRIMARY KEY (`config_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sms_index_config
-- ----------------------------
INSERT INTO `sms_index_config` VALUES (1, '热销商品 iPhone 12', 3, 10906, '##', 201, 0, '2021-03-08 18:55:49', 0, '2021-03-08 18:55:49', 0);
INSERT INTO `sms_index_config` VALUES (2, '热销商品 华为Mate40 Pro', 3, 10908, '##', 300, 0, '2021-03-08 18:55:49', 0, '2021-03-08 18:55:49', 0);
INSERT INTO `sms_index_config` VALUES (3, '新品上线 MackBook2021', 4, 10920, '##', 180, 0, '2021-03-08 18:55:49', 0, '2021-03-08 18:55:49', 0);
INSERT INTO `sms_index_config` VALUES (4, '新品上线 华为 P50 Pro', 4, 10921, '##', 160, 0, '2021-03-08 18:55:49', 0, '2021-03-08 18:55:49', 0);
INSERT INTO `sms_index_config` VALUES (5, '新品上线 Apple Watch', 4, 10919, '##', 101, 0, '2021-03-08 18:55:49', 0, '2021-03-08 18:55:49', 0);
INSERT INTO `sms_index_config` VALUES (6, '纪梵希高定香榭天鹅绒唇膏', 5, 10233, '##', 80, 0, '2021-03-08 18:55:49', 0, '2021-03-08 18:55:49', 0);
INSERT INTO `sms_index_config` VALUES (7, 'P50 白色', 5, 10922, '##', 102, 0, '2021-03-08 18:55:49', 0, '2023-02-26 15:29:30', 0);
INSERT INTO `sms_index_config` VALUES (8, 'free buds pro', 5, 10930, '##', 102, 0, '2021-03-08 18:55:49', 0, '2021-03-08 18:55:49', 0);
INSERT INTO `sms_index_config` VALUES (9, 'iPhone 13', 5, 10916, '##', 101, 0, '2021-03-08 18:55:49', 0, '2021-03-08 18:55:49', 0);
INSERT INTO `sms_index_config` VALUES (10, '华为Mate40 Pro', 5, 10907, '##', 80, 0, '2021-03-08 18:55:49', 0, '2021-03-08 18:55:49', 0);
INSERT INTO `sms_index_config` VALUES (11, 'MacBook Pro 2021', 5, 10920, '##', 100, 0, '2021-03-08 18:55:49', 0, '2021-03-08 18:55:49', 0);
INSERT INTO `sms_index_config` VALUES (12, 'WATCH 3 Pro', 5, 10928, '##', 99, 0, '2021-03-08 18:55:49', 0, '2021-03-08 18:55:49', 0);
INSERT INTO `sms_index_config` VALUES (13, '塑料浴室座椅', 5, 10154, '##', 80, 0, '2021-03-08 18:55:49', 0, '2021-03-08 18:55:49', 0);
INSERT INTO `sms_index_config` VALUES (14, '华为 soundx', 5, 10929, '##', 100, 0, '2021-03-08 18:55:49', 0, '2021-03-08 18:55:49', 0);
INSERT INTO `sms_index_config` VALUES (15, 'matepad pro', 5, 10906, '##', 0, 0, '2021-03-08 18:55:49', 0, '2021-03-08 18:55:49', 0);
INSERT INTO `sms_index_config` VALUES (16, '热销商品 P40', 3, 10902, '##', 200, 0, '2021-03-08 18:55:49', 0, '2021-03-08 18:55:49', 0);
INSERT INTO `sms_index_config` VALUES (17, '新品上线 华为 P50 Pocket', 4, 10925, '##', 200, 0, '2021-03-08 18:55:49', 0, '2021-03-08 18:55:49', 0);
INSERT INTO `sms_index_config` VALUES (18, '新品上线 华为Mate X Pro', 4, 10926, '##', 200, 0, '2021-03-08 18:55:49', 0, '2023-02-26 15:18:46', 0);
INSERT INTO `sms_index_config` VALUES (19, '华为 Mate 30 Pro', 5, 10927, '##', 101, 0, '2021-03-08 18:55:49', 0, '2021-03-08 18:55:49', 0);
INSERT INTO `sms_index_config` VALUES (20, '新品上线 iPhone13', 4, 10915, '##', 190, 0, '2021-03-08 18:55:49', 0, '2021-03-08 18:55:49', 0);
INSERT INTO `sms_index_config` VALUES (21, 'Air Pods 第三代', 3, 10918, '##', 301, 0, '2021-03-08 18:55:49', 0, '2021-03-08 18:55:49', 0);
INSERT INTO `sms_index_config` VALUES (22, '234234', 4, 10003, '##', 1, 1, '2023-02-26 15:11:06', 0, '2023-02-26 15:22:47', 0);
INSERT INTO `sms_index_config` VALUES (23, '423435', 4, 10003, 'sfr', 1, 1, '2023-02-26 15:23:43', 0, '2023-02-26 15:23:48', 0);

SET FOREIGN_KEY_CHECKS = 1;
