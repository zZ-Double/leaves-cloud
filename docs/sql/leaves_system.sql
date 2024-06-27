-- MySQL dump 10.13  Distrib 8.3.0, for macos14.2 (arm64)
--
-- Host: 127.0.0.1    Database: leaves_system
-- ------------------------------------------------------
-- Server version	8.3.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `sys_dept`
--

DROP TABLE IF EXISTS `sys_dept`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_dept` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '部门id',
  `parent_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '0' COMMENT '父部门id',
  `ancestors` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '祖级列表',
  `dept_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '部门名称',
  `order_num` int DEFAULT '0' COMMENT '显示顺序',
  `leader` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '负责人',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '联系电话',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '邮箱',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '部门状态（1正常 0停用）',
  `del_flag` tinyint(1) NOT NULL DEFAULT '1' COMMENT '删除标志（1代表存在 0代表删除）',
  `create_user` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='部门表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dept`
--

LOCK TABLES `sys_dept` WRITE;
/*!40000 ALTER TABLE `sys_dept` DISABLE KEYS */;
INSERT INTO `sys_dept` VALUES ('1','0','0','XXX有限公司',0,'leaves','136xxxxxxxx','',1,1,'1','2023-08-21 14:26:26','1','2023-08-21 14:26:26'),('1806143314407911425','2','0,1,2','Java开发',0,'Java开发','','',1,1,'1','2024-06-27 09:51:37','1','2024-06-27 09:51:37'),('1806143387896311809','2','0,1,2','python开发',1,'python开发',NULL,NULL,1,1,'1','2024-06-27 09:51:55','1','2024-06-27 09:51:55'),('2','1','0,1','技术部',1,'xxx','xxxxxxxxxxx',NULL,1,1,'1','2023-08-21 14:27:01','1','2023-08-21 14:27:01'),('3','1','0,1','行政部',2,'行政负责人','13612345671','',1,1,'1','2024-06-27 09:38:57','1','2024-06-27 09:39:09');
/*!40000 ALTER TABLE `sys_dept` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_menu`
--

DROP TABLE IF EXISTS `sys_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_menu` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '权限ID',
  `parent_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '父菜单ID',
  `type` tinyint(1) DEFAULT NULL COMMENT '菜单类型(1：菜单；2：目录；3：外链；4：按钮)',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '菜单名称',
  `path` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '路由路径(浏览器地址栏路径)',
  `component` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '组件路径(vue页面完整路径，省略.vue后缀)',
  `perm` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '按钮权限标识',
  `icon` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '菜单图标',
  `sort` int DEFAULT '0' COMMENT '排序',
  `redirect` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '跳转路径',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态（1正常 0停用）',
  `del_flag` tinyint(1) NOT NULL DEFAULT '1' COMMENT '删除标志（1代表存在 0代表删除）',
  `create_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '更新者',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='菜单管理';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_menu`
--

LOCK TABLES `sys_menu` WRITE;
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;
INSERT INTO `sys_menu` VALUES ('1','0',1,'系统设置','/sys','Layout','','system',1,'/sys/user',1,0,'1','2023-08-16 14:50:11','2023-08-16 15:58:39','1',NULL),('101','1',2,'角色管理','role','sys/role',NULL,'role',2,'',1,0,'1','2023-08-16 14:50:11','2023-08-16 14:50:12','1',NULL),('1011','101',3,'角色新增','',NULL,'sys:role:save','user',1,'',1,0,'1','2023-08-16 14:50:11','2023-08-16 14:50:12','1',NULL),('1012','101',3,'角色编辑','',NULL,'sys:role:update','user',2,'',1,0,'1','2023-08-16 14:50:11','2023-08-16 14:50:12','1',NULL),('1013','101',3,'角色删除','',NULL,'sys:role:remove','user',3,'',1,0,'1','2023-08-16 14:50:11','2023-08-16 14:50:12','1',NULL),('1014','101',3,'角色查询','',NULL,'sys:role:page','user',4,'',1,0,'1','2023-08-16 14:50:11','2023-08-16 14:50:12','1',NULL),('1015','101',3,'角色详情','',NULL,'sys:role:query','user',5,'',1,0,'1','2023-08-16 14:50:11','2023-08-16 14:50:12','1',NULL),('1016','101',3,'角色菜单列表','','','sys:role:menus:query','',7,'',1,0,'1','2023-08-25 11:07:00','2023-08-25 11:07:00','1',NULL),('1017','101',3,'分配角色菜单资源','',NULL,'sys:role:menus:save','',8,'',1,0,'1','2023-08-25 11:07:36','2023-08-25 11:07:36','1',NULL),('102','1',2,'用户管理','user','sys/user',NULL,'user',1,'',1,0,'1','2023-08-16 14:50:11','2023-08-16 14:50:12','1',NULL),('1021','102',3,'用户新增','',NULL,'sys:user:save','user',1,'',1,0,'1','2023-08-16 14:50:11','2023-08-16 14:50:12','1',NULL),('1022','102',3,'用户编辑','',NULL,'sys:user:update','user',2,'',1,0,'1','2023-08-16 14:50:11','2023-08-16 14:50:12','1',NULL),('1023','102',3,'用户删除','',NULL,'sys:user:remove','user',3,'',1,0,'1','2023-08-16 14:50:11','2023-08-25 10:53:37','1',NULL),('1024','102',3,'用户查询','',NULL,'sys:user:page','user',4,'',1,0,'1','2023-08-16 14:50:11','2023-08-25 10:55:15','1',NULL),('1025','102',3,'用户详情','',NULL,'sys:user:query','user',5,'',1,0,'1','2023-08-16 14:50:11','2023-08-16 14:50:12','1',NULL),('103','1',2,'部门管理','dept','sys/dept','','tree',4,'',1,0,'1','2023-08-17 15:40:14','2023-08-24 16:44:37','1',NULL),('1031','103',3,'部门新增','',NULL,'sys:dept:save','',1,'',1,0,'1','2023-08-16 14:50:11','2023-08-16 14:50:12','1',NULL),('1032','103',3,'部门编辑','',NULL,'sys:dept:update','',2,'',1,0,'1','2023-08-16 14:50:11','2023-08-16 14:50:12','1',NULL),('1033','103',3,'部门删除','',NULL,'sys:dept:remove','',3,'',1,0,'1','2023-08-16 14:50:11','2023-08-16 14:50:12','1',NULL),('1034','103',3,'部门查询','',NULL,'sys:dept:list','',4,'',1,0,'1','2023-08-16 14:50:11','2023-08-16 14:50:12','1',NULL),('1035','103',3,'部门详情','',NULL,'sys:dept:query','',5,'',1,0,'1','2023-08-16 14:50:11','2023-08-16 14:50:12','1',NULL),('104','1',2,'菜单管理','menu','sys/menu',NULL,'menu',3,'',1,0,'1','2023-08-16 14:50:11','2023-08-16 14:50:12','1',NULL),('1041','104',3,'菜单新增','',NULL,'sys:menu:save','',1,'',1,0,'1','2023-08-16 14:50:11','2023-08-25 10:59:32','1',NULL),('1042','104',3,'菜单编辑','',NULL,'sys:menu:update','',2,'',1,0,'1','2023-08-16 14:50:11','2023-08-16 14:50:12','1',NULL),('1043','104',3,'菜单删除','',NULL,'sys:menu:remove','',3,'',1,0,'1','2023-08-16 14:50:11','2023-08-16 14:50:12','1',NULL),('1044','104',3,'菜单查询','',NULL,'sys:menu:list','',4,'',1,0,'1','2023-08-16 14:50:11','2023-08-16 14:50:12','1',NULL),('1045','104',3,'菜单详情','',NULL,'sys:menu:query','',4,'',1,0,'1','2023-08-16 14:50:11','2023-08-16 14:50:12','1',NULL),('1046','104',3,'菜单下拉','',NULL,'sys:menu:options','',6,'',1,0,'1','2023-08-16 14:50:11','2023-08-16 14:50:12','1',NULL),('105','1',3,'角色下拉','',NULL,'sys:role:options','',6,'',1,0,'1','2023-08-16 14:50:11','2024-06-27 10:09:27','1',NULL),('106','1',3,'部门下拉','',NULL,'sys:dept:options','',6,'',1,0,'1','2023-08-16 14:50:11','2024-06-27 10:09:53','1',NULL),('2','0',1,'测试目录','/test','Layout','','advert copy',2,'/test/test1',1,0,'1','2023-08-17 14:00:23','2023-08-17 14:00:23','1',NULL),('201','2',2,'测试菜单','test1','test/test1',NULL,'data-scope',1,'',1,0,'1','2023-08-17 14:01:12','2023-08-17 14:01:12','1',NULL);
/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_operation_log`
--

DROP TABLE IF EXISTS `sys_operation_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_operation_log` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '日志主键',
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '模块标题',
  `method` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '方法名称',
  `request_method` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '请求方式',
  `operation_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '操作人员',
  `operation_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '请求URL',
  `operation_ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '主机地址',
  `operation_location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '操作地点',
  `operation_param` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '请求参数',
  `result` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '返回参数',
  `status` tinyint(1) DEFAULT '1' COMMENT '操作状态（1正常 0异常）',
  `error_msg` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '错误消息',
  `operation_time` datetime DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='操作日志记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_operation_log`
--

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色ID',
  `role_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色名称',
  `role_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色编码',
  `data_scope` tinyint(1) DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态（1正常 0停用）',
  `del_flag` tinyint(1) NOT NULL DEFAULT '1' COMMENT '删除标志（1代表存在 0代表删除）',
  `create_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='角色信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` VALUES ('1','超级管理员','ROOT',1,1,0,'1','2023-08-11 14:01:55','1',NULL,NULL),('1806143612329324545','测试角色','TEST',2,1,0,'1','2024-06-27 09:52:48','1','2024-06-27 09:52:48',NULL);
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_menu`
--

DROP TABLE IF EXISTS `sys_role_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role_menu` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `role_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `menu_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `del_flag` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色和资源关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_menu`
--

LOCK TABLES `sys_role_menu` WRITE;
/*!40000 ALTER TABLE `sys_role_menu` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_role_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID',
  `username` varchar(64) NOT NULL COMMENT '登录账号',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '密码',
  `nick_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '用户昵称',
  `user_type` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '00' COMMENT '用户类型（00系统用户 01注册用户）',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '用户邮箱',
  `phone_number` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '手机号码',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '个人介绍',
  `email_flag` tinyint(1) DEFAULT '0' COMMENT '邮箱验证标志（1已验证 0未验证）',
  `phone_flag` tinyint(1) DEFAULT '0' COMMENT '手机验证标志（1已验证 0未验证）',
  `sex` int NOT NULL DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
  `avatar` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '头像路径',
  `dept_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '部门id',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态（1正常 0停用）',
  `del_flag` tinyint(1) NOT NULL DEFAULT '1' COMMENT '删除标志（1代表存在 0代表删除）',
  `create_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '备注',
  `data_scope` int DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='用户信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` VALUES ('1','admin','$2a$10$rMIF.gT726JMzJLhu4Hyb.6vgvuTnZgtBi3HLefAsRP05ths3uW7m','管理员','00','','',NULL,0,0,0,'https://avatars.githubusercontent.com/u/48982260?v=4',NULL,1,0,'',NULL,'',NULL,NULL,NULL),('1806143858329448449','Test','$2a$10$7DWObbPMZ3Bzho/uzJn42.kGceQZqFUgtxbJgl4GabAnowJXEtC3K','Test','00','','13612345671',NULL,0,0,1,'','2',1,0,'1','2024-06-27 09:53:47','1','2024-06-27 09:53:47',NULL,1),('1806143997014110209','Test1','$2a$10$0Of9vc7bqGnZbFbRW6f7xOPFXmJF.oJzbY.hJZgVxBwTf3.f6P6Q.','Test1','00','','13612345672',NULL,0,0,1,'','1806143314407911425',1,0,'1','2024-06-27 09:54:20','1','2024-06-27 09:54:20',NULL,2),('1806146223958814721','Test2','$2a$10$W63d6wMN2BXaUdirjnd20eoAzBWzF9KCiFLGUD4zB.TS4G.okA1ra','Test2','00','','13612345673',NULL,0,0,1,'','1806143387896311809',1,0,'1','2024-06-27 10:03:11','1','2024-06-27 10:03:11',NULL,3);
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_role`
--

DROP TABLE IF EXISTS `sys_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user_role` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主键',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID',
  `role_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色ID',
  `del_flag` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='用户和角色关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_role`
--

LOCK TABLES `sys_user_role` WRITE;
/*!40000 ALTER TABLE `sys_user_role` DISABLE KEYS */;
INSERT INTO `sys_user_role` VALUES ('1','1','1',0),('1806143858396557313','1806143858329448449','1806143612329324545',0),('1806143997043470337','1806143997014110209','1806143612329324545',0),('1806146224025923586','1806146223958814721','1806143612329324545',0);
/*!40000 ALTER TABLE `sys_user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-06-27 10:45:21
