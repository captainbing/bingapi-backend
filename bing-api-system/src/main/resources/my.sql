DROP TABLE IF EXISTS `address`;
CREATE TABLE `address`  (
                            `id` int(0) NOT NULL AUTO_INCREMENT,
                            `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                            `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                            `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                            `uid` int(0) NULL DEFAULT NULL,
                            PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 42 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of address
-- ----------------------------
INSERT INTO `address` VALUES (24, '李振鹏', '15929789876', '陕西省宝鸡市岐山县', 32);
INSERT INTO `address` VALUES (25, '巨瑞华', '18842697892', '陕西省宝鸡市岐山县', 32);
INSERT INTO `address` VALUES (29, '巨瑞华', '18842687923', '陕西省宝鸡市', 39);
INSERT INTO `address` VALUES (31, '巨瑞华', '18842697892', '陕西省宝鸡市岐山县', 40);
INSERT INTO `address` VALUES (33, '小飞侠', '18876890987', '陕西省西安市雁塔区', 31);
INSERT INTO `address` VALUES (36, '小巨要做一个很棒的人', '111', '三生三世', NULL);
INSERT INTO `address` VALUES (38, '拜托虾滑超好吃的', '123', '北京市市辖区东城区动物园', NULL);
INSERT INTO `address` VALUES (39, '小巨要做一个很棒的人', '1', '天津市市辖区河东区三生三世', NULL);
INSERT INTO `address` VALUES (40, '小巨要做一个很棒的人', '1', '河北省唐山市古冶区陕西省西安市/雁塔区/大雁塔', NULL);

-- ----------------------------
-- Table structure for adminuser
-- ----------------------------
DROP TABLE IF EXISTS `adminuser`;
CREATE TABLE `adminuser`  (
                              `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                              `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dingdan
-- ----------------------------
DROP TABLE IF EXISTS `dingdan`;
CREATE TABLE `dingdan`  (
                            `id` int(0) NOT NULL AUTO_INCREMENT,
                            `sjrname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                            `sjrphone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                            `sjraddress` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                            `yjtime` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                            `ddname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                            `ddmessage` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                            `uid` int(0) NULL DEFAULT NULL,
                            `uname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                            `number` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                            PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 157 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dingdan
-- ----------------------------
INSERT INTO `dingdan` VALUES (1, '巨瑞华', '18842697892', '陕西省宝鸡市岐山县', '2022-05-21T16:00:00.000Z', '白玫瑰啊', '祝你幸福', 44, '巨瑞华', '20220522170350292853');
INSERT INTO `dingdan` VALUES (150, '巨瑞华', '18842697892', '陕西省宝鸡市岐山县', '', '粉玫瑰', '', 44, NULL, 'Wed Sep 06 2023 11:59:48 GMT+0800 (中国标准时间)322942');
INSERT INTO `dingdan` VALUES (151, '巨瑞华', '18842697892', '陕西省宝鸡市岐山县', '', '蓝色勿忘我', '', 44, NULL, 'Wed Sep 06 2023 14:39:19 GMT+0800 (中国标准时间)749411');
INSERT INTO `dingdan` VALUES (152, '巨瑞华', '18842697892', '陕西省宝鸡市岐山县', '', '红玫瑰', '', 44, NULL, 'Wed Sep 06 2023 16:34:12 GMT+0800 (中国标准时间)991546');
INSERT INTO `dingdan` VALUES (153, '巨瑞华', '18842687923', '陕西省宝鸡市', '2023-09-13T16:00:00.000Z', '白玫瑰啊', '三生三世水水水水', 44, NULL, 'Thu Sep 07 2023 15:47:53 GMT+0800 (中国标准时间)128217');
INSERT INTO `dingdan` VALUES (154, '巨瑞华', '18842687923', '陕西省宝鸡市', '2023-09-13T16:00:00.000Z', '粉玫瑰', '三生三世水水水水', 44, NULL, 'Thu Sep 07 2023 15:47:53 GMT+0800 (中国标准时间)128217');
INSERT INTO `dingdan` VALUES (155, '巨瑞华', '18842687923', '陕西省宝鸡市', '2023-09-13T16:00:00.000Z', '粉玫瑰', '三生三世水水水水', 44, NULL, 'Thu Sep 07 2023 15:47:53 GMT+0800 (中国标准时间)128217');
INSERT INTO `dingdan` VALUES (156, '巨瑞华', '18842687923', '陕西省宝鸡市', '2023-09-13T16:00:00.000Z', '蓝色勿忘我', '三生三世水水水水', 44, NULL, 'Thu Sep 07 2023 15:47:53 GMT+0800 (中国标准时间)128217');

-- ----------------------------
-- Table structure for flower
-- ----------------------------
DROP TABLE IF EXISTS `flower`;
CREATE TABLE `flower`  (
                           `fid` int(0) NOT NULL AUTO_INCREMENT,
                           `fname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                           `mprice` double(10, 2) NULL DEFAULT NULL,
  `sprice` double(10, 2) NULL DEFAULT NULL,
  `img` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `fdesc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `tid` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `obj` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`fid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 39 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of flower
-- ----------------------------
INSERT INTO `flower` VALUES (1, '白玫瑰', 22.00, 33.00, 'img11.jpg', '纯洁、高贵、天真和纯纯的爱。', '1', '送恋人');
INSERT INTO `flower` VALUES (5, '红玫瑰', 12.00, 33.00, 'img1.jpg', '热恋，我爱你', '1', '送恋人');
INSERT INTO `flower` VALUES (17, '合欢花', 1.00, 2.00, 'img5.jpg', '永远恩爱、两两相对。', '1', '送长辈');
INSERT INTO `flower` VALUES (35, '栀子花', 1.00, 1.00, 'img13.jpg', '坚强、永恒的爱', '1', '送恋人');
INSERT INTO `flower` VALUES (36, '郁金香', 11.00, 21.00, 'img10.jpg', '博爱体贴、永远的爱', '1', '送恋人');
INSERT INTO `flower` VALUES (37, '含羞花', 1.00, 1.00, 'img12.jpg', '热烈的爱', '1', '送长辈');
INSERT INTO `flower` VALUES (38, '玫瑰', 2.00, 2.00, 'img08.jpg', '热烈的爱', '1', '送长辈');

-- ----------------------------
-- Table structure for gwc
-- ----------------------------
DROP TABLE IF EXISTS `gwc`;
CREATE TABLE `gwc`  (
                        `id` int(0) NOT NULL AUTO_INCREMENT,
                        `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                        `descs` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                        `price` decimal(10, 2) NULL DEFAULT NULL,
                        `uid` int(0) NULL DEFAULT NULL,
                        `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                        PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 64 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of gwc
-- ----------------------------
INSERT INTO `gwc` VALUES (1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `gwc` VALUES (42, '粉玫瑰', '初恋，灿烂的笑容', 3.00, 44, 'img3.jpg');
INSERT INTO `gwc` VALUES (51, '白玫瑰啊', '纯洁、高贵、天真和纯纯的爱。', 33.00, 44, 'img11.jpg');
INSERT INTO `gwc` VALUES (52, '红玫瑰', '热恋，我爱你', 33.00, 4, 'img1.jpg');
INSERT INTO `gwc` VALUES (53, '粉玫瑰', '初恋，灿烂的笑容', 2.00, 44, 'img3.jpg');
INSERT INTO `gwc` VALUES (54, '蓝色勿忘我', '永恒不变的爱，深情，不要忘记我、真实的爱。', 2.00, 44, 'img4.jpg');
INSERT INTO `gwc` VALUES (60, '白玫瑰', '纯洁、高贵、天真和纯纯的爱。', 33.00, NULL, 'img11.jpg');
INSERT INTO `gwc` VALUES (61, '白玫瑰', '纯洁、高贵、天真和纯纯的爱。', 33.00, NULL, 'img11.jpg');
INSERT INTO `gwc` VALUES (62, '白玫瑰', '纯洁、高贵、天真和纯纯的爱。', 33.00, NULL, 'img11.jpg');
INSERT INTO `gwc` VALUES (63, '红玫瑰', '热恋，我爱你', 33.00, NULL, 'img1.jpg');

-- ----------------------------
-- Table structure for inventory
-- ----------------------------
DROP TABLE IF EXISTS `inventory`;
CREATE TABLE `inventory`  (
                              `iid` int(0) NOT NULL,
                              `fid` int(0) NULL DEFAULT NULL,
                              `fname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                              `count` int(0) NULL DEFAULT NULL,
                              PRIMARY KEY (`iid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
                           `oid` int(0) NOT NULL,
                           `amount` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                           `ordertime` datetime(0) NULL DEFAULT NULL,
                           `state` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                           `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                           `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                           `addr` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                           `number` int(0) NULL DEFAULT NULL,
                           `uid` int(0) NOT NULL,
                           PRIMARY KEY (`oid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES (1, '22', '2022-04-08 16:13:36', '1', '一', '1111111111111', '少时诵诗书', 121212, 32);
INSERT INTO `orders` VALUES (2, '33', '2022-04-22 16:14:12', '1', '二', '2222222', '三生三世水水水水', 222, 2);

-- ----------------------------
-- Table structure for sc
-- ----------------------------
DROP TABLE IF EXISTS `sc`;
CREATE TABLE `sc`  (
                       `id` int(0) NOT NULL AUTO_INCREMENT,
                       `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                       `descs` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                       `price` decimal(10, 2) NULL DEFAULT NULL,
                       `uid` int(0) NULL DEFAULT NULL,
                       `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                       PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 90 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sc
-- ----------------------------
INSERT INTO `sc` VALUES (1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sc` VALUES (43, '红玫瑰', '热恋，我爱你', 33.00, 44, 'img1.jpg');
INSERT INTO `sc` VALUES (51, '白玫瑰啊', '纯洁、高贵、天真和纯纯的爱。', 33.00, 44, 'img11.jpg');
INSERT INTO `sc` VALUES (52, '红玫瑰', '热恋，我爱你', 33.00, 4, 'img1.jpg');
INSERT INTO `sc` VALUES (53, '粉玫瑰', '初恋，灿烂的笑容', 2.00, 44, 'img3.jpg');
INSERT INTO `sc` VALUES (80, '', NULL, NULL, NULL, NULL);
INSERT INTO `sc` VALUES (81, '', NULL, NULL, NULL, NULL);
INSERT INTO `sc` VALUES (82, '', NULL, NULL, NULL, NULL);
INSERT INTO `sc` VALUES (83, '', NULL, NULL, NULL, NULL);
INSERT INTO `sc` VALUES (84, '', NULL, NULL, NULL, NULL);
INSERT INTO `sc` VALUES (85, '', NULL, NULL, NULL, NULL);
INSERT INTO `sc` VALUES (86, '白玫瑰', '纯洁、高贵、天真和纯纯的爱。', 33.00, NULL, 'img11.jpg');
INSERT INTO `sc` VALUES (87, '白玫瑰', '纯洁、高贵、天真和纯纯的爱。', 33.00, NULL, 'img11.jpg');
INSERT INTO `sc` VALUES (88, '白玫瑰', '纯洁、高贵、天真和纯纯的爱。', 33.00, NULL, 'img11.jpg');
INSERT INTO `sc` VALUES (89, '红玫瑰', '热恋，我爱你', 33.00, NULL, 'img1.jpg');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
                             `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
                             `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '名称',
                             `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '描述',
                             `flag` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '唯一标识',
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '管理员', '管理员', 'ROLE_ADMIN');
INSERT INTO `sys_role` VALUES (2, '用户', '用户', 'ROLE_USER');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
                             `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
                             `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户名',
                             `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '密码',
                             `nickname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '昵称',
                             `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '邮箱',
                             `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '电话',
                             `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '地址',
                             `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
                             `avatar_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '头像',
                             `role` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '角色',
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 46 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, '巨瑞华', '111', '巨瑞华', '1612549920@qq.com', '18842682407', '陕西宝鸡', '2022-04-07 22:04:21', NULL, 'ROLE_USER');
INSERT INTO `sys_user` VALUES (3, '李振鹏', '调查方法', '阿散发出', '1212549920@qq.com', '18843790897', '陕西西安', '2022-03-30 22:04:50', NULL, 'ROLE_USER');
INSERT INTO `sys_user` VALUES (31, 'admin', 'admin', '巨瑞华', '1622549920@qq.com', '18842687893', '辽宁大连', '2022-04-01 16:01:41', NULL, 'ROLE_ADMIN');
INSERT INTO `sys_user` VALUES (32, 'user', 'user', '哈哈', '1312549920@qq.com', '15191960616', '陕西宝及', '2022-04-02 11:24:44', NULL, 'ROLE_USER');
INSERT INTO `sys_user` VALUES (35, 'wee', 'wee', 'wee', '1232549920@qq.com', '18843432345', '江苏南京', '2022-05-06 18:14:11', NULL, 'ROLE_USER');
INSERT INTO `sys_user` VALUES (36, 'lzp', 'lzp', '李振鹏', '1612549920@qq.com', '15929605605', '浙江杭州', '2022-05-11 16:32:52', NULL, 'ROLE_USER');
INSERT INTO `sys_user` VALUES (37, 'userw', 'userw', 'jujuju', NULL, NULL, NULL, '2022-05-19 17:08:30', NULL, 'ROLE_USER');
INSERT INTO `sys_user` VALUES (38, 'uuu', 'uuu', '小肉丸', '1612549920@qq.com', '18842697892', 'dd', '2022-05-20 18:47:19', NULL, 'ROLE_USER');
INSERT INTO `sys_user` VALUES (39, 'addw', 'addw', '小肉丸', '1612549920@qq.com', '18842697892', '陕西西安', '2022-05-20 18:54:08', NULL, 'ROLE_USER');
INSERT INTO `sys_user` VALUES (40, 'addwq', '1234', '小飞侠', '1612549920@qq.com', '18842697892', '陕西宝鸡', '2022-05-20 18:58:07', NULL, 'ROLE_USER');
INSERT INTO `sys_user` VALUES (41, '111', '1', NULL, NULL, NULL, NULL, '2023-08-31 14:35:23', NULL, 'ROLE_USER');
INSERT INTO `sys_user` VALUES (42, '1', '1', NULL, NULL, NULL, NULL, '2023-08-31 14:39:44', NULL, 'ROLE_USER');
INSERT INTO `sys_user` VALUES (43, '111', '11', NULL, NULL, NULL, NULL, '2023-08-31 14:40:35', NULL, 'ROLE_USER');
INSERT INTO `sys_user` VALUES (44, '111', '111', NULL, NULL, NULL, NULL, '2023-08-31 14:41:23', NULL, 'ROLE_USER');
INSERT INTO `sys_user` VALUES (45, '1111', '111', NULL, NULL, NULL, NULL, '2023-08-31 15:01:07', NULL, 'ROLE_USER');

-- ----------------------------
-- Table structure for type
-- ----------------------------
DROP TABLE IF EXISTS `type`;
CREATE TABLE `type`  (
                         `tid` int(0) NOT NULL AUTO_INCREMENT,
                         `tname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                         `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                         `num` int(0) NULL DEFAULT NULL,
                         PRIMARY KEY (`tid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of type
-- ----------------------------
INSERT INTO `type` VALUES (1, '白玫瑰', '玫瑰', 4);
INSERT INTO `type` VALUES (2, '郁金香', '郁金', 4);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
                         `uid` int(0) NOT NULL,
                         `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                         `password` int(0) NULL DEFAULT NULL,
                         `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                         `phone` int(0) NULL DEFAULT NULL,
                         `addr` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                         `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                         PRIMARY KEY (`uid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '聚聚我', 123, '巨瑞华', 123232323, '陕西省宝鸡市岐山县', '2345');
