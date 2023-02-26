/*
 Navicat Premium Data Transfer

 Source Server         : mallcloud
 Source Server Type    : MySQL
 Source Server Version : 80031
 Source Host           : 192.168.10.10:31324
 Source Schema         : mall_sys

 Target Server Type    : MySQL
 Target Server Version : 80031
 File Encoding         : 65001

 Date: 27/02/2023 00:00:31
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for back_log
-- ----------------------------
DROP TABLE IF EXISTS `back_log`;
CREATE TABLE `back_log`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `code` int(0) NULL DEFAULT NULL COMMENT '编码',
  `data` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '数据',
  `created_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'System' COMMENT '创建者',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of back_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `pid` bigint(0) NULL DEFAULT NULL COMMENT '上级部门',
  `sub_count` int(0) NULL DEFAULT 0 COMMENT '子部门数目',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '名称',
  `dept_sort` int(0) NULL DEFAULT 999 COMMENT '排序',
  `enabled` tinyint(1) NOT NULL DEFAULT 1 COMMENT '状态(1 启用 0禁用)',
  `created_by` bigint(0) NOT NULL COMMENT '创建者',
  `updated_by` bigint(0) NULL DEFAULT NULL COMMENT '修改者',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_name_pid`(`name`, `pid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '部门' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (2, 7, 2, '研发部', 3, 1, 1, 1, '2019-03-25 09:15:32', '2023-01-28 19:22:15');
INSERT INTO `sys_dept` VALUES (5, 7, 0, '运维部', 5, 1, 1, 1, '2019-03-25 09:20:44', '2023-01-28 17:40:13');
INSERT INTO `sys_dept` VALUES (6, 8, 0, '测试部', 6, 1, 1, 1, '2019-03-25 09:52:18', '2023-01-28 17:40:23');
INSERT INTO `sys_dept` VALUES (7, NULL, 2, '华南分部', 1, 1, 1, 1, '2019-03-25 11:04:50', '2023-01-28 19:37:13');
INSERT INTO `sys_dept` VALUES (8, NULL, 2, '华北分部', 2, 1, 1, 1, '2019-03-25 11:04:53', '2023-01-28 17:11:23');
INSERT INTO `sys_dept` VALUES (15, 8, 0, 'UI部门', 7, 1, 1, 1, '2020-05-13 22:56:53', '2023-01-28 17:40:46');
INSERT INTO `sys_dept` VALUES (17, 2, 0, '研发一组', 4, 1, 1, 1, '2020-08-02 14:49:07', '2023-01-28 20:54:30');

-- ----------------------------
-- Table structure for sys_job
-- ----------------------------
DROP TABLE IF EXISTS `sys_job`;
CREATE TABLE `sys_job`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '岗位名称',
  `enabled` tinyint(1) NOT NULL COMMENT '岗位状态',
  `job_sort` int(0) NULL DEFAULT NULL COMMENT '排序',
  `created_by` bigint(0) NOT NULL COMMENT '创建者',
  `updated_by` bigint(0) NULL DEFAULT NULL COMMENT '修改者',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '岗位' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_job
-- ----------------------------
INSERT INTO `sys_job` VALUES (8, '人事专员', 1, 0, 1, 1, '2019-03-29 14:52:28', '2023-01-28 14:40:32');
INSERT INTO `sys_job` VALUES (10, '产品经理', 1, 4, 1, 1, '2019-03-29 14:55:51', '2023-01-28 14:40:31');
INSERT INTO `sys_job` VALUES (11, '全栈开发', 1, 2, 1, 1, '2019-03-31 13:39:30', '2023-01-27 18:01:23');
INSERT INTO `sys_job` VALUES (12, '软件测试', 1, 5, 1, 1, '2019-03-31 13:39:43', '2023-01-27 18:01:25');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `pid` bigint(0) NULL DEFAULT NULL COMMENT '上级菜单ID',
  `sub_count` int(0) NULL DEFAULT 0 COMMENT '子菜单数目',
  `type` tinyint(0) NOT NULL DEFAULT 1 COMMENT '菜单类型 1菜单 2按钮',
  `title` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '菜单标题',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '组件名称',
  `component` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '组件',
  `menu_sort` int(0) NULL DEFAULT 0 COMMENT '排序',
  `icon` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '图标',
  `path` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '链接地址',
  `target` tinyint(1) NULL DEFAULT NULL COMMENT '0 内部路由 1 内部外链 2 跳转外联',
  `enabled` tinyint(1) NULL DEFAULT 1 COMMENT '0禁用 1启用',
  `keep_alive` tinyint(1) NULL DEFAULT NULL COMMENT '缓存 0 不缓存 1缓存',
  `hidden` tinyint(1) NULL DEFAULT NULL COMMENT '隐藏 0 不隐藏 1隐藏',
  `permission` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限',
  `created_by` bigint(0) NOT NULL COMMENT '创建者',
  `updated_by` bigint(0) NULL DEFAULT NULL COMMENT '修改者',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 226 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统菜单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (159, NULL, 5, 1, '系统管理', 'system', 'RouteView', 1, 'setting', NULL, 0, 1, 1, 0, NULL, 1, 1, '2023-01-29 18:36:43', '2023-02-12 14:30:25');
INSERT INTO `sys_menu` VALUES (160, 159, 4, 1, '用户管理', 'user', 'system/user', 1, NULL, NULL, 0, 1, 1, 0, NULL, 1, 1, '2023-01-29 18:37:26', '2023-01-30 00:17:06');
INSERT INTO `sys_menu` VALUES (161, 159, 4, 1, '岗位管理', 'job', 'system/job', 2, NULL, NULL, 0, 1, 1, 0, NULL, 1, 1, '2023-01-29 18:37:51', '2023-01-30 00:17:16');
INSERT INTO `sys_menu` VALUES (162, 159, 4, 1, '部门管理', 'department', 'system/department', 3, NULL, NULL, 0, 1, 1, 0, NULL, 1, 1, '2023-01-29 18:38:11', '2023-01-29 18:50:36');
INSERT INTO `sys_menu` VALUES (163, 159, 4, 1, '菜单管理', 'menu', 'system/menu', 5, NULL, NULL, 0, 1, 1, 0, NULL, 1, 1, '2023-01-29 18:38:35', '2023-01-30 00:17:37');
INSERT INTO `sys_menu` VALUES (164, 159, 4, 1, '角色管理', 'role', 'system/role', 4, NULL, NULL, 0, 1, 1, 0, NULL, 1, 1, '2023-01-29 18:38:55', '2023-01-30 00:17:29');
INSERT INTO `sys_menu` VALUES (165, 160, 0, 2, '添加', NULL, NULL, 0, NULL, NULL, 0, 1, 1, 0, 'user:add', 1, NULL, '2023-01-29 18:40:25', NULL);
INSERT INTO `sys_menu` VALUES (166, 160, 0, 2, '修改', NULL, NULL, 0, NULL, NULL, 0, 1, 1, 0, 'user:update', 1, NULL, '2023-01-29 18:41:34', NULL);
INSERT INTO `sys_menu` VALUES (167, 160, 0, 2, '删除', NULL, NULL, 0, NULL, NULL, 0, 1, 1, 0, 'user:delete', 1, NULL, '2023-01-29 18:41:55', NULL);
INSERT INTO `sys_menu` VALUES (168, 160, 0, 2, '查询', NULL, NULL, 0, NULL, NULL, 0, 1, 1, 0, 'user:select', 1, NULL, '2023-01-29 18:42:29', NULL);
INSERT INTO `sys_menu` VALUES (169, 161, 0, 2, '查询', NULL, NULL, 0, NULL, NULL, 0, 1, 1, 0, 'job:select', 1, 1, '2023-01-29 18:44:40', '2023-01-30 00:15:58');
INSERT INTO `sys_menu` VALUES (170, 161, 0, 2, '添加', NULL, NULL, 0, NULL, NULL, 0, 1, 1, 0, 'job:add', 1, NULL, '2023-01-29 18:44:56', NULL);
INSERT INTO `sys_menu` VALUES (171, 161, 0, 2, '更新', NULL, NULL, 0, NULL, NULL, 0, 1, 1, 0, 'job:update', 1, NULL, '2023-01-29 18:45:13', NULL);
INSERT INTO `sys_menu` VALUES (172, 161, 0, 2, '删除', NULL, NULL, 0, NULL, NULL, 0, 1, 1, 0, 'job:delete', 1, NULL, '2023-01-29 18:45:27', NULL);
INSERT INTO `sys_menu` VALUES (173, 162, 0, 2, '添加', NULL, NULL, 0, NULL, NULL, 0, 1, 1, 0, 'dept:add', 1, NULL, '2023-01-29 18:45:48', NULL);
INSERT INTO `sys_menu` VALUES (174, 162, 0, 2, '更新', NULL, NULL, 0, NULL, NULL, 0, 1, 1, 0, 'dept:update', 1, NULL, '2023-01-29 18:46:07', NULL);
INSERT INTO `sys_menu` VALUES (175, 162, 0, 2, '删除', NULL, NULL, 0, NULL, NULL, 0, 1, 1, 0, 'dept:delete', 1, NULL, '2023-01-29 18:46:25', NULL);
INSERT INTO `sys_menu` VALUES (176, 162, 0, 2, '查询', NULL, NULL, 0, NULL, NULL, 0, 1, 1, 0, 'dept:select', 1, NULL, '2023-01-29 18:46:42', NULL);
INSERT INTO `sys_menu` VALUES (177, 163, 0, 2, '添加', NULL, NULL, 0, NULL, NULL, 0, 1, 1, 0, 'menu:add', 1, NULL, '2023-01-29 18:47:09', NULL);
INSERT INTO `sys_menu` VALUES (178, 163, 0, 2, '更新', NULL, NULL, 0, NULL, NULL, 0, 1, 1, 0, 'menu:update', 1, NULL, '2023-01-29 18:47:22', NULL);
INSERT INTO `sys_menu` VALUES (179, 163, 0, 2, '删除', NULL, NULL, 0, NULL, NULL, 0, 1, 1, 0, 'menu:delete', 1, NULL, '2023-01-29 18:47:50', NULL);
INSERT INTO `sys_menu` VALUES (180, 163, 0, 2, '查询', NULL, NULL, 0, NULL, NULL, 0, 1, 1, 0, 'menu:select', 1, NULL, '2023-01-29 18:48:06', NULL);
INSERT INTO `sys_menu` VALUES (181, 164, 0, 2, '添加', NULL, NULL, 1, NULL, NULL, 0, 1, 1, 0, 'role:add', 1, 1, '2023-01-29 18:48:28', '2023-01-29 19:03:30');
INSERT INTO `sys_menu` VALUES (182, 164, 0, 2, '更新', NULL, NULL, 0, NULL, NULL, 0, 1, 1, 0, 'role:update', 1, NULL, '2023-01-29 18:48:47', NULL);
INSERT INTO `sys_menu` VALUES (183, 164, 0, 2, '删除', NULL, NULL, 0, NULL, NULL, 0, 1, 1, 0, 'role:delete', 1, NULL, '2023-01-29 18:49:02', NULL);
INSERT INTO `sys_menu` VALUES (184, 164, 0, 2, '查询', NULL, NULL, 0, NULL, NULL, 0, 1, 1, 0, 'role:select', 1, NULL, '2023-01-29 18:49:26', NULL);
INSERT INTO `sys_menu` VALUES (185, NULL, 13, 1, '商品管理', 'pms', 'RouteView', 2, 'product', NULL, 0, 1, 1, 0, NULL, 1, 1, '2023-02-02 00:19:59', '2023-02-12 14:30:16');
INSERT INTO `sys_menu` VALUES (186, 185, 0, 1, '商品列表', 'product', 'pms/product', 0, NULL, NULL, 0, 1, 1, 0, NULL, 1, 1, '2023-02-02 00:24:45', '2023-02-02 01:05:47');
INSERT INTO `sys_menu` VALUES (187, 185, 0, 1, '添加商品', 'addProduct', 'pms/product/addOrUpdate', 1, NULL, 'addProduct', 0, 1, 1, 0, NULL, 1, 1, '2023-02-02 00:26:32', '2023-02-02 01:02:12');
INSERT INTO `sys_menu` VALUES (188, 185, 0, 1, '修改商品', 'updateProduct', 'pms/product/addOrUpdate', 2, NULL, 'updateProduct', 0, 1, 1, 1, NULL, 1, 1, '2023-02-02 00:30:05', '2023-02-02 01:02:15');
INSERT INTO `sys_menu` VALUES (189, 185, 0, 1, '商品分类', 'productCategory', 'pms/productCategory', 3, NULL, 'productCategory', 0, 1, 1, 0, NULL, 1, 1, '2023-02-02 00:33:06', '2023-02-02 01:02:18');
INSERT INTO `sys_menu` VALUES (190, 185, 0, 1, '添加商品分类', 'addProductCategory', 'pms/productCategory/addOrUpdate', 4, NULL, 'addProductCategory', 0, 1, 1, 1, NULL, 1, 1, '2023-02-02 00:35:58', '2023-02-02 01:02:20');
INSERT INTO `sys_menu` VALUES (191, 185, 0, 1, '修改商品分类', 'updateProductCategory', 'pms/productCategory/addOrUpdate', 5, NULL, 'updateProductCategory', 0, 1, 1, 1, NULL, 1, 1, '2023-02-02 00:37:46', '2023-02-02 01:02:23');
INSERT INTO `sys_menu` VALUES (192, 185, 0, 1, '属性类型', 'productAttr', 'pms/productAttr', 6, NULL, 'productAttr', 0, 1, 1, 0, NULL, 1, 1, '2023-02-02 00:39:28', '2023-02-09 14:10:21');
INSERT INTO `sys_menu` VALUES (193, 185, 0, 1, '商品属性列表', 'productAttrList', 'pms/productAttr/productAttrList', 7, NULL, 'productAttrList', 0, 1, 1, 1, NULL, 1, 1, '2023-02-02 00:42:23', '2023-02-02 01:02:30');
INSERT INTO `sys_menu` VALUES (194, 185, 0, 1, '添加商品属性', 'addProductAttr', 'pms/productAttr/andOrUpdateAttr', 8, NULL, 'addProductAttr', 0, 1, 1, 1, NULL, 1, 1, '2023-02-02 00:49:58', '2023-02-09 15:25:41');
INSERT INTO `sys_menu` VALUES (195, 185, 0, 1, '修改商品属性', 'updateProductAttr', 'pms/productAttr/andOrUpdateAttr', 9, NULL, 'updateProductAttr', 0, 1, 1, 1, NULL, 1, 1, '2023-02-02 00:50:41', '2023-02-09 15:25:55');
INSERT INTO `sys_menu` VALUES (196, 185, 0, 1, '品牌管理', 'brand', 'pms/brand', 10, NULL, 'brand', 0, 1, 1, 0, NULL, 1, 1, '2023-02-02 00:51:11', '2023-02-02 01:02:39');
INSERT INTO `sys_menu` VALUES (197, 185, 0, 1, '添加品牌', 'addBrand', 'pms/brand/addOrUpdate', 11, NULL, 'addBrand', 0, 1, 1, 1, NULL, 1, 1, '2023-02-02 00:52:03', '2023-02-02 01:02:44');
INSERT INTO `sys_menu` VALUES (198, 185, 0, 1, '编辑品牌', 'updateBrand', 'pms/brand/addOrUpdate', 12, NULL, 'updateBrand', 0, 1, 1, 1, NULL, 1, 1, '2023-02-02 00:52:35', '2023-02-02 01:02:46');
INSERT INTO `sys_menu` VALUES (199, NULL, 8, 1, '订单管理', 'oms', 'RouteView', 3, 'order', NULL, 0, 1, 1, 0, NULL, 1, 1, '2023-02-09 23:16:39', '2023-02-12 14:30:21');
INSERT INTO `sys_menu` VALUES (200, 199, 0, 1, '订单列表', 'order', 'oms/order', 0, NULL, NULL, 0, 1, 1, 0, NULL, 1, 1, '2023-02-09 23:17:37', '2023-02-09 23:17:51');
INSERT INTO `sys_menu` VALUES (201, 199, 0, 1, '订单详情', 'orderDetail', 'oms/order/orderDetail', 1, NULL, NULL, 0, 1, 1, 1, NULL, 1, 1, '2023-02-09 23:18:43', '2023-02-09 23:20:35');
INSERT INTO `sys_menu` VALUES (202, 199, 0, 1, '发货列表', 'deliverOrderList', 'oms/order/deliverOrderList', 2, NULL, NULL, 0, 1, 1, 1, NULL, 1, 1, '2023-02-09 23:19:44', '2023-02-11 01:03:56');
INSERT INTO `sys_menu` VALUES (203, 199, 0, 1, '订单设置', 'orderSetting', 'oms/order/setting', 3, NULL, NULL, 0, 1, 1, 0, NULL, 1, 1, '2023-02-09 23:21:13', '2023-02-11 01:04:00');
INSERT INTO `sys_menu` VALUES (204, 199, 0, 1, '退货申请处理', 'returnApply', 'oms/apply', 4, NULL, NULL, 0, 1, 1, 0, NULL, 1, 1, '2023-02-09 23:21:50', '2023-02-11 01:03:49');
INSERT INTO `sys_menu` VALUES (205, 199, 0, 1, '退货原因设置', 'returnReason', 'oms/apply/reason', 5, NULL, NULL, 0, 1, 1, 0, NULL, 1, NULL, '2023-02-09 23:22:40', NULL);
INSERT INTO `sys_menu` VALUES (206, 199, 0, 1, '退货原因详情', 'returnApplyDetail', 'oms/apply/applyDetail', 6, NULL, NULL, 0, 1, 1, 1, NULL, 1, NULL, '2023-02-09 23:23:21', NULL);
INSERT INTO `sys_menu` VALUES (207, NULL, 4, 1, '营销管理', 'sms', 'RouteView', 4, 'sms', NULL, 0, 1, 1, 0, NULL, 1, 1, '2023-02-11 01:26:10', '2023-02-26 19:20:59');
INSERT INTO `sys_menu` VALUES (218, 207, 0, 1, '新品推荐', 'new', 'sms/indexConfig', 9, NULL, NULL, 0, 1, 1, 0, NULL, 1, 1, '2023-02-11 01:34:52', '2023-02-26 14:29:31');
INSERT INTO `sys_menu` VALUES (219, 207, 0, 1, '人气推荐', 'hot', 'sms/indexConfig', 10, NULL, NULL, 0, 1, 1, 0, NULL, 1, 1, '2023-02-11 01:35:25', '2023-02-26 15:26:28');
INSERT INTO `sys_menu` VALUES (220, 207, 0, 1, '商品推荐', 'recommend', 'sms/indexConfig', 11, NULL, NULL, 0, 1, 1, 0, NULL, 1, 1, '2023-02-11 01:35:50', '2023-02-26 15:29:08');
INSERT INTO `sys_menu` VALUES (224, NULL, 0, 1, '首页', 'home', 'home', 0, 'home', '', 0, 1, 1, 0, NULL, 1, 1, '2023-02-12 14:28:02', '2023-02-12 14:49:13');
INSERT INTO `sys_menu` VALUES (225, 207, 0, 1, '轮播图配置', 'carousel', 'sms/carousel', 11, NULL, NULL, 0, 1, 1, 0, NULL, 1, 1, '2023-02-26 15:32:22', '2023-02-26 15:34:17');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '名称',
  `code` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '编码',
  `level` int(0) NULL DEFAULT NULL COMMENT '角色级别',
  `enabled` tinyint(1) NOT NULL DEFAULT 1 COMMENT '状态 1 开启 0 禁用',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '描述',
  `created_by` bigint(0) NOT NULL COMMENT '创建者',
  `updated_by` bigint(0) NULL DEFAULT NULL COMMENT '修改者',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `ux_code`(`code`) USING BTREE COMMENT '标识唯一索引'
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级管理员', 'ROLE_ADMIN', 1, 1, '超级管理员', 1, 1, '2023-01-15 23:08:04', '2023-01-29 16:25:34');
INSERT INTO `sys_role` VALUES (3, '管理员', 'ADM', NULL, 1, '管理员管理一切', 1, 1, '2023-01-29 16:25:56', '2023-01-29 19:08:59');

-- ----------------------------
-- Table structure for sys_role_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_dept`;
CREATE TABLE `sys_role_dept`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `role_id` bigint(0) NOT NULL,
  `dept_id` bigint(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unique_role_dept`(`dept_id`, `role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色部门关联' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_dept
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `menu_id` bigint(0) NOT NULL COMMENT '菜单ID',
  `role_id` bigint(0) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unique_role_menu`(`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 253 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色菜单关联' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (209, 159, 1);
INSERT INTO `sys_role_menu` VALUES (179, 160, 1);
INSERT INTO `sys_role_menu` VALUES (182, 161, 1);
INSERT INTO `sys_role_menu` VALUES (204, 162, 1);
INSERT INTO `sys_role_menu` VALUES (210, 163, 1);
INSERT INTO `sys_role_menu` VALUES (174, 164, 1);
INSERT INTO `sys_role_menu` VALUES (196, 165, 1);
INSERT INTO `sys_role_menu` VALUES (197, 166, 1);
INSERT INTO `sys_role_menu` VALUES (193, 167, 1);
INSERT INTO `sys_role_menu` VALUES (189, 168, 1);
INSERT INTO `sys_role_menu` VALUES (192, 169, 1);
INSERT INTO `sys_role_menu` VALUES (186, 170, 1);
INSERT INTO `sys_role_menu` VALUES (188, 171, 1);
INSERT INTO `sys_role_menu` VALUES (185, 172, 1);
INSERT INTO `sys_role_menu` VALUES (202, 173, 1);
INSERT INTO `sys_role_menu` VALUES (203, 174, 1);
INSERT INTO `sys_role_menu` VALUES (205, 175, 1);
INSERT INTO `sys_role_menu` VALUES (173, 176, 1);
INSERT INTO `sys_role_menu` VALUES (207, 177, 1);
INSERT INTO `sys_role_menu` VALUES (208, 178, 1);
INSERT INTO `sys_role_menu` VALUES (211, 179, 1);
INSERT INTO `sys_role_menu` VALUES (194, 180, 1);
INSERT INTO `sys_role_menu` VALUES (190, 181, 1);
INSERT INTO `sys_role_menu` VALUES (191, 182, 1);
INSERT INTO `sys_role_menu` VALUES (187, 183, 1);
INSERT INTO `sys_role_menu` VALUES (184, 184, 1);
INSERT INTO `sys_role_menu` VALUES (225, 185, 1);
INSERT INTO `sys_role_menu` VALUES (212, 186, 1);
INSERT INTO `sys_role_menu` VALUES (220, 187, 1);
INSERT INTO `sys_role_menu` VALUES (218, 188, 1);
INSERT INTO `sys_role_menu` VALUES (213, 189, 1);
INSERT INTO `sys_role_menu` VALUES (214, 190, 1);
INSERT INTO `sys_role_menu` VALUES (222, 191, 1);
INSERT INTO `sys_role_menu` VALUES (219, 192, 1);
INSERT INTO `sys_role_menu` VALUES (221, 193, 1);
INSERT INTO `sys_role_menu` VALUES (216, 194, 1);
INSERT INTO `sys_role_menu` VALUES (217, 195, 1);
INSERT INTO `sys_role_menu` VALUES (215, 196, 1);
INSERT INTO `sys_role_menu` VALUES (223, 197, 1);
INSERT INTO `sys_role_menu` VALUES (224, 198, 1);
INSERT INTO `sys_role_menu` VALUES (227, 199, 1);
INSERT INTO `sys_role_menu` VALUES (231, 200, 1);
INSERT INTO `sys_role_menu` VALUES (233, 201, 1);
INSERT INTO `sys_role_menu` VALUES (226, 202, 1);
INSERT INTO `sys_role_menu` VALUES (230, 203, 1);
INSERT INTO `sys_role_menu` VALUES (232, 204, 1);
INSERT INTO `sys_role_menu` VALUES (228, 205, 1);
INSERT INTO `sys_role_menu` VALUES (229, 206, 1);
INSERT INTO `sys_role_menu` VALUES (235, 208, 1);
INSERT INTO `sys_role_menu` VALUES (238, 209, 1);
INSERT INTO `sys_role_menu` VALUES (246, 210, 1);
INSERT INTO `sys_role_menu` VALUES (249, 211, 1);
INSERT INTO `sys_role_menu` VALUES (245, 212, 1);
INSERT INTO `sys_role_menu` VALUES (240, 213, 1);
INSERT INTO `sys_role_menu` VALUES (241, 214, 1);
INSERT INTO `sys_role_menu` VALUES (248, 216, 1);
INSERT INTO `sys_role_menu` VALUES (250, 217, 1);
INSERT INTO `sys_role_menu` VALUES (243, 218, 1);
INSERT INTO `sys_role_menu` VALUES (244, 219, 1);
INSERT INTO `sys_role_menu` VALUES (242, 220, 1);
INSERT INTO `sys_role_menu` VALUES (236, 221, 1);
INSERT INTO `sys_role_menu` VALUES (237, 222, 1);
INSERT INTO `sys_role_menu` VALUES (251, 224, 1);
INSERT INTO `sys_role_menu` VALUES (252, 225, 1);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `dept_id` bigint(0) NULL DEFAULT NULL COMMENT '部门名称',
  `job_id` bigint(0) NOT NULL COMMENT '岗位id',
  `username` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `real_name` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `nick_name` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '昵称',
  `gender` tinyint(1) NOT NULL COMMENT '性别 1男 0女',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '手机号码',
  `email` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
  `avatar` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '头像地址',
  `password` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `can_deleted` tinyint(1) NULL DEFAULT 1 COMMENT '是否可以删除',
  `enabled` tinyint(1) NOT NULL DEFAULT 1 COMMENT '状态：1启用、0禁用',
  `must_reset_pwd` tinyint(1) NULL DEFAULT 0 COMMENT '是否必须修改密码：1是、0否',
  `pwd_fails_count` int(0) NOT NULL DEFAULT 0 COMMENT '密码连续错误次数',
  `fail_lock_time` datetime(0) NULL DEFAULT NULL COMMENT '密码错误锁定时间',
  `pwd_reset_time` datetime(0) NULL DEFAULT NULL COMMENT '修改密码的时间',
  `last_login_time` datetime(0) NULL DEFAULT NULL COMMENT '最后一次登陆时间',
  `created_by` bigint(0) NULL DEFAULT NULL COMMENT '创建者',
  `updated_by` bigint(0) NULL DEFAULT NULL COMMENT '修改者',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uniq_username`(`username`) USING BTREE,
  UNIQUE INDEX `uniq_email`(`email`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 7, 11, 'admin', '宋啊', '手动阀手动阀', 1, NULL, NULL, NULL, 'e10adc3949ba59abbe56e057f20f883e', 0, 1, 0, 0, '2023-01-19 19:12:57', '2023-01-19 19:13:04', '2023-02-17 23:48:50', 1, 1, '2023-01-15 22:57:05', '2023-02-17 23:46:21');
INSERT INTO `sys_user` VALUES (6, 2, 11, 'sdfsdfsdf3', '宋啊', 'eternal', 1, '18683446912', 'leellun@sina.cn', NULL, 'e10adc3949ba59abbe56e057f20f883e', 1, 0, 1, 0, NULL, '2023-01-28 01:58:03', NULL, 1, 1, '2023-01-28 00:19:57', '2023-01-28 14:55:02');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` bigint(0) NOT NULL COMMENT '用户ID',
  `role_id` bigint(0) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unique_user_role`(`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户角色关联' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1, 1);
INSERT INTO `sys_user_role` VALUES (2, 4, 1);
INSERT INTO `sys_user_role` VALUES (19, 5, 1);
INSERT INTO `sys_user_role` VALUES (20, 6, 1);

SET FOREIGN_KEY_CHECKS = 1;
