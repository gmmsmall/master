/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80014
 Source Host           : localhost:3306
 Source Schema         : normae_uaa

 Target Server Type    : MySQL
 Target Server Version : 80014
 File Encoding         : 65001

 Date: 10/06/2020 17:18:04
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_authority
-- ----------------------------
DROP TABLE IF EXISTS `t_authority`;
CREATE TABLE `t_authority`  (
  `id` bigint(20) NOT NULL,
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '权限名称',
  `mark` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '权限标识',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '修改时间',
  `create_user_id` bigint(20) NOT NULL COMMENT '创建人',
  `update_user_id` bigint(20) NOT NULL COMMENT '修改人',
  `disabled` int(1) NOT NULL COMMENT '是否禁用   1:是  0:否',
  `deleted` int(1) NOT NULL COMMENT '是否删除  1:是  0:否',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_mark`(`mark`) USING BTREE COMMENT '权限标识唯一索引'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_authority
-- ----------------------------
INSERT INTO `t_authority` VALUES (1, '1', '1', '2020-05-21 15:07:16', '2020-05-21 15:07:20', 1, 1, 0, 0);

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role`  (
  `id` bigint(20) NOT NULL,
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色名称',
  `mark` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色标识',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '修改时间',
  `create_user_id` bigint(20) NOT NULL COMMENT '创建人',
  `update_user_id` bigint(20) NOT NULL COMMENT '修改人',
  `disabled` int(1) NOT NULL COMMENT '是否禁用   1:是  0:否',
  `deleted` int(1) NOT NULL COMMENT '是否删除  1:是  0:否',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_mark`(`mark`) USING BTREE COMMENT '角色标识唯一索引'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES (1111, 'aaa', 'ROLE_EMPLOYEE', '2020-05-20 18:29:45', '2020-05-20 18:29:48', 0, 0, 0, 0);
INSERT INTO `t_role` VALUES (1263031841413808130, '超级管理员', 'ROLE_ADMINISTRATOR', '2020-05-20 17:00:31', '2020-05-20 17:00:31', 0, 0, 0, 0);

-- ----------------------------
-- Table structure for t_role_authority
-- ----------------------------
DROP TABLE IF EXISTS `t_role_authority`;
CREATE TABLE `t_role_authority`  (
  `id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL COMMENT '角色 id',
  `authority_id` bigint(20) NOT NULL COMMENT '权限 id',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_roleid_authorityid`(`role_id`, `authority_id`) USING BTREE COMMENT '角色id和权限id唯一索引'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色权限关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_role_authority
-- ----------------------------
INSERT INTO `t_role_authority` VALUES (1, 1263031841413808130, 1);

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` bigint(20) NOT NULL,
  `username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `password` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '修改时间',
  `create_user_id` bigint(20) NOT NULL COMMENT '创建人 id',
  `update_user_id` bigint(20) NOT NULL COMMENT '修改人 id',
  `disabled` int(1) NOT NULL COMMENT '是否禁用 1:是  0:否',
  `deleted` int(1) NOT NULL COMMENT '是否删除  1:是  0:否',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_username`(`username`) USING BTREE COMMENT '用户名唯一索引'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES (1263031841128595458, 'admin', 'admin', '2020-05-20 17:00:31', '2020-05-20 17:00:31', 0, 0, 0, 0);

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role`  (
  `id` bigint(20) NOT NULL,
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户 id',
  `role_id` bigint(20) NOT NULL COMMENT '角色 id',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_userid_roleid`(`user_id`, `role_id`) USING BTREE COMMENT '用户id和角色id唯一索引'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user_role
-- ----------------------------
INSERT INTO `t_user_role` VALUES (1, 1263031841128595458, 1111);
INSERT INTO `t_user_role` VALUES (1263031841426391041, 1263031841128595458, 1263031841413808130);

SET FOREIGN_KEY_CHECKS = 1;
