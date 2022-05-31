/*
 Navicat Premium Data Transfer

 Source Server         : 本地链接
 Source Server Type    : MySQL
 Source Server Version : 80025
 Source Host           : localhost:3306
 Source Schema         : monkey_house

 Target Server Type    : MySQL
 Target Server Version : 80025
 File Encoding         : 65001

 Date: 31/05/2022 19:23:52
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for haa_org_data
-- ----------------------------
DROP TABLE IF EXISTS `haa_org_data`;
CREATE TABLE `haa_org_data`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `union_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '哈根达斯唯一识别',
  `uid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '通知uid',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `memo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `modify_time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改时间',
  `create_time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `open_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `mobile` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `social_hub_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of haa_org_data
-- ----------------------------
INSERT INTO `haa_org_data` VALUES (1, 'onoRB5tgD5dvz88weMSu4saKwFMU', 'UID_ZtRVPrJYQ9JEgxyMZX1RoP4zCV6z', '聂萌萌', '萌萌', NULL, '2021-06-29 17:42:09', NULL, NULL, NULL);
INSERT INTO `haa_org_data` VALUES (2, 'onoRB5l_PxGubiPtAJ3m8ORaTSTs', 'UID_XpJVE4FfL5KDTbuuIKjsbFXxGddp', '李冰', 'GOD', NULL, '2021-06-29 17:42:09', 'oJlot5Emce9Zv_BXMGO0J-WCnpXE', '15771935223', '2510637');
INSERT INTO `haa_org_data` VALUES (3, 'onoRB5ktJrsDZJfVAZs6p5O_fSAg', 'UID_3m9wuahYhEWZtbBBQMcwSss1GJPm', 'NewWorld', '新号', NULL, '2021-06-29 17:42:09', 'oJlot5HAf9tWbrRjyAEsrWQa45eo', '18192822703', 'IiDqlH9ciAFAQ9Lc');
INSERT INTO `haa_org_data` VALUES (4, 'onoRB5kUifUbiE31G0Cic-2Sx4QQ', 'UID_cfJyQKFQ4ax7sDQwBT7pS08d3UFO', '聂远', '聂远', NULL, '2021-06-29 17:42:09', NULL, NULL, NULL);
INSERT INTO `haa_org_data` VALUES (5, 'onoRB5kR-glM2_PjOhLjnHRsmGMc', 'UID_oikjqhO5sdxbWG4pVYSGdETOUA8W', '萌萌', '青欲得一人心', NULL, '2021-06-29 17:42:09', NULL, NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
