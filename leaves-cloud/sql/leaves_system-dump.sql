-- MySQL dump 10.13  Distrib 5.7.42, for osx10.18 (x86_64)
--
-- Host: 127.0.0.1    Database: leaves_system
-- ------------------------------------------------------
-- Server version	5.7.42

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
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
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_dept` (
  `id` varchar(32) NOT NULL COMMENT '部门id',
  `parent_id` varchar(32) DEFAULT '0' COMMENT '父部门id',
  `ancestors` varchar(50) DEFAULT '' COMMENT '祖级列表',
  `dept_name` varchar(30) DEFAULT '' COMMENT '部门名称',
  `order_num` int(4) DEFAULT '0' COMMENT '显示顺序',
  `leader` varchar(20) DEFAULT NULL COMMENT '负责人',
  `phone` varchar(11) DEFAULT NULL COMMENT '联系电话',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '部门状态（1正常 0停用）',
  `del_flag` tinyint(1) NOT NULL DEFAULT '1' COMMENT '删除标志（1代表存在 0代表删除）',
  `create_user` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='部门表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dept`
--

LOCK TABLES `sys_dept` WRITE;
/*!40000 ALTER TABLE `sys_dept` DISABLE KEYS */;
INSERT INTO `sys_dept` VALUES ('100','0','0','若依科技',0,'若依','15888888888','ry@qq.com',0,1,'admin','2022-05-25 09:59:16','',NULL),('101','100','0,100','深圳总公司',1,'若依','15888888888','ry@qq.com',0,1,'admin','2022-05-25 09:59:16','',NULL),('102','100','0,100','长沙分公司',2,'若依','15888888888','ry@qq.com',0,1,'admin','2022-05-25 09:59:16','',NULL),('103','101','0,100,101','研发部门',1,'若依','15888888888','ry@qq.com',0,1,'admin','2022-05-25 09:59:16','',NULL),('104','101','0,100,101','市场部门',2,'若依','15888888888','ry@qq.com',0,1,'admin','2022-05-25 09:59:16','',NULL),('105','101','0,100,101','测试部门',3,'若依','15888888888','ry@qq.com',0,1,'admin','2022-05-25 09:59:16','',NULL),('106','101','0,100,101','财务部门',4,'若依','15888888888','ry@qq.com',0,1,'admin','2022-05-25 09:59:16','',NULL),('107','101','0,100,101','运维部门',5,'若依','15888888888','ry@qq.com',0,1,'admin','2022-05-25 09:59:16','',NULL),('108','102','0,100,102','市场部门',1,'若依','15888888888','ry@qq.com',0,1,'admin','2022-05-25 09:59:16','',NULL),('109','102','0,100,102','财务部门',2,'若依','15888888888','ry@qq.com',0,1,'admin','2022-05-25 09:59:16','',NULL);
/*!40000 ALTER TABLE `sys_dept` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_menu`
--

DROP TABLE IF EXISTS `sys_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_menu` (
  `id` varchar(32) NOT NULL COMMENT '权限ID',
  `parent_id` varchar(32) DEFAULT NULL COMMENT '父菜单ID',
  `type` tinyint(4) DEFAULT NULL COMMENT '菜单类型(1：菜单；2：目录；3：外链；4：按钮)',
  `name` varchar(64) DEFAULT '' COMMENT '菜单名称',
  `path` varchar(128) DEFAULT '' COMMENT '路由路径(浏览器地址栏路径)',
  `component` varchar(128) DEFAULT NULL COMMENT '组件路径(vue页面完整路径，省略.vue后缀)',
  `perm` varchar(50) DEFAULT NULL COMMENT '按钮权限标识',
  `icon` varchar(64) DEFAULT '' COMMENT '菜单图标',
  `sort` int(11) DEFAULT '0' COMMENT '排序',
  `redirect` varchar(128) DEFAULT '' COMMENT '跳转路径',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态（1正常 0停用）',
  `del_flag` tinyint(1) NOT NULL DEFAULT '1' COMMENT '删除标志（1代表存在 0代表删除）',
  `create_user` varchar(32) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` varchar(32) DEFAULT '' COMMENT '更新者',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单管理';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_menu`
--

LOCK TABLES `sys_menu` WRITE;
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_operation_log`
--

DROP TABLE IF EXISTS `sys_operation_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_operation_log` (
  `id` varchar(32) NOT NULL COMMENT '日志主键',
  `title` varchar(50) DEFAULT '' COMMENT '模块标题',
  `method` varchar(100) DEFAULT '' COMMENT '方法名称',
  `request_method` varchar(10) DEFAULT '' COMMENT '请求方式',
  `operation_user` varchar(32) DEFAULT '' COMMENT '操作人员',
  `operation_url` varchar(255) DEFAULT '' COMMENT '请求URL',
  `operation_ip` varchar(50) DEFAULT '' COMMENT '主机地址',
  `operation_location` varchar(255) DEFAULT '' COMMENT '操作地点',
  `operation_param` varchar(2000) DEFAULT '' COMMENT '请求参数',
  `result` varchar(2000) DEFAULT '' COMMENT '返回参数',
  `status` tinyint(1) DEFAULT '1' COMMENT '操作状态（1正常 0异常）',
  `error_msg` varchar(2000) DEFAULT '' COMMENT '错误消息',
  `operation_time` datetime DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='操作日志记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_operation_log`
--

LOCK TABLES `sys_operation_log` WRITE;
/*!40000 ALTER TABLE `sys_operation_log` DISABLE KEYS */;
INSERT INTO `sys_operation_log` VALUES ('1504473156683845633','用户登陆','com.leaves.web.common.LoginController.login()','POST','','/login','0:0:0:0:0:0:0:1','内网IP','{\"password\":\"123456\",\"userName\":\"admin\",\"uuid\":\"\"}','',0,'当前用户已被禁用，请联系管理员解禁','2022-03-17 23:02:07'),('1504473524079722497','用户登陆','com.leaves.web.common.LoginController.login()','POST','','/login','0:0:0:0:0:0:0:1','内网IP','{\"password\":\"123456\",\"userName\":\"admin\",\"uuid\":\"\"}','\"eyJhbGciOiJIUzUxMiJ9.eyJsb2dpbl91c2VyX2tleToiOiJhZG1pbiIsImV4cCI6MTY0NzUzMDMwM30.PD1SYayCma60uZzfJs3QiJo2aHHj9WFHxo6UW89zSWT4g4vyLFbFm_e1IJ76ABKUmnnzFeCtaoT5bZMXe4EB7w\"',0,'','2022-03-17 23:03:35'),('1504473733052530689','test1','com.leaves.web.common.LoginController.test1()','POST','1','/test1','0:0:0:0:0:0:0:1','内网IP','','\"test1\"',0,'','2022-03-17 23:04:24'),('1504474080353484801','hello方法','com.leaves.web.common.LoginController.hello()','POST','1','/hello','0:0:0:0:0:0:0:1','内网IP','','',0,'','2022-03-17 23:05:47'),('1504476021724839938','用户登陆','com.leaves.web.common.LoginController.login()','POST','','/login','0:0:0:0:0:0:0:1','内网IP','{\"password\":\"12345612\",\"userName\":\"admin\",\"uuid\":\"\"}','',0,'Bad credentials','2022-03-17 23:13:30'),('1504476275824164865','用户登陆','com.leaves.web.common.LoginController.login()','POST','','/login','0:0:0:0:0:0:0:1','内网IP','{\"password\":\"12345612\",\"userName\":\"admin\",\"uuid\":\"\"}','',0,'Bad credentials','2022-03-17 23:14:31'),('1504476955502833665','用户登陆','com.leaves.web.common.LoginController.login()','POST','','/login','0:0:0:0:0:0:0:1','内网IP','{\"password\":\"12345612\",\"userName\":\"admin\",\"uuid\":\"\"}','',1,'Bad credentials','2022-03-17 23:17:13'),('1514612549855764481','新增用户','com.leaves.web.system.SysUserController.saveSysUser()','POST','1','/sys-user/save','0:0:0:0:0:0:0:1','内网IP','{\"avatar\":\"\",\"pageNum\":0,\"pageSize\":0,\"phoneNumber\":\"123\",\"roleId\":\"\",\"sex\":\"0\",\"userName\":\"test\"}','true',0,'','2022-04-14 22:32:27'),('1514617446886035457','新增用户','com.leaves.web.system.SysUserController.saveSysUser()','POST','1','/sys-user/save','0:0:0:0:0:0:0:1','内网IP','{\"avatar\":\"\",\"pageNum\":0,\"pageSize\":0,\"phoneNumber\":\"123\",\"roleId\":\"\",\"sex\":\"0\",\"userName\":\"test1\"}','true',0,'','2022-04-14 22:51:54'),('1514618973524627458','查询用户：列表查询/分页查询','com.leaves.web.system.SysUserController.queryUserList()','POST','1','/sys-user/query/list','0:0:0:0:0:0:0:1','内网IP','{\"avatar\":\"\",\"id\":\"\",\"pageNum\":0,\"pageSize\":0,\"phoneNumber\":\"\",\"roleId\":\"\",\"sex\":\"\",\"userName\":\"\"}','{\"endRow\":2,\"hasNextPage\":false,\"hasPreviousPage\":false,\"isFirstPage\":true,\"isLastPage\":true,\"list\":[{\"avatar\":\"\",\"createUser\":\"\",\"delFlag\":\"0\",\"email\":\"\",\"emailFlag\":0,\"id\":\"1\",\"loginIp\":\"\",\"nickName\":\"管理员\",\"password\":\"$2a$10$rMIF.gT726JMzJLhu4Hyb.6vgvuTnZgtBi3HLefAsRP05ths3uW7m\",\"phoneFlag\":0,\"phoneNumber\":\"\",\"salt\":\"\",\"sex\":\"0\",\"status\":\"0\",\"updateUser\":\"\",\"userName\":\"admin\",\"userType\":\"00\"},{\"avatar\":\"\",\"createUser\":\"\",\"delFlag\":\"0\",\"email\":\"\",\"emailFlag\":0,\"id\":\"1514612549704769537\",\"loginIp\":\"\",\"nickName\":\"\",\"password\":\"$2a$10$2uWSO/yAWOfD0.eBcRjpS.fjs1LuCG0wQjpkai/hM/wPvkNBfKcz6\",\"phoneFlag\":0,\"phoneNumber\":\"123\",\"salt\":\"\",\"sex\":\"0\",\"status\":\"0\",\"updateUser\":\"\",\"userName\":\"test\",\"userType\":\"00\"},{\"avatar\":\"\",\"createTime\":\"2022-04-14T22:51:54\",\"createUser\":\"1\",\"delFlag\":\"0\",\"email\":\"\",\"emailFlag\":0,\"id\":\"1514617446730846210\",\"loginIp\":\"\",\"nickName\":\"\",\"password\":\"$2a$10$1hufqRnHsyY6OPwkw3bD8ODcsGEn3RBhsFV8RqqjicqTBSZ31ugia\",\"phoneFlag\":0,\"phoneNumber\":\"123\",\"salt\":\"\",\"sex\":\"0\",\"status\":\"0\",\"updateTime\":\"2022-04-14T22:51:54\",\"updateUser\":\"1\",\"userName\":\"test1\",\"userType\":\"00\"}],\"navigateFirstPage\":1,\"navigateLastPage\":1,\"navigatePages\":8,\"navigatepageNums\":[1],\"nextPage\":0,\"pageNum\":1,\"pageSize\":3,\"pages\":1,\"prePage\":0,\"size\":3,\"startRow\":0,\"total\":3}',0,'','2022-04-14 22:57:56'),('1514619071813947394','查询用户：列表查询/分页查询','com.leaves.web.system.SysUserController.queryUserList()','POST','1','/sys-user/query/list','0:0:0:0:0:0:0:1','内网IP','{\"avatar\":\"\",\"id\":\"\",\"pageNum\":0,\"pageSize\":0,\"phoneNumber\":\"\",\"roleId\":\"\",\"sex\":\"\",\"userName\":\"\"}','{\"endRow\":2,\"hasNextPage\":false,\"hasPreviousPage\":false,\"isFirstPage\":true,\"isLastPage\":true,\"list\":[{\"avatar\":\"\",\"createUser\":\"\",\"delFlag\":\"0\",\"email\":\"\",\"emailFlag\":0,\"id\":\"1\",\"loginIp\":\"\",\"nickName\":\"管理员\",\"password\":\"$2a$10$rMIF.gT726JMzJLhu4Hyb.6vgvuTnZgtBi3HLefAsRP05ths3uW7m\",\"phoneFlag\":0,\"phoneNumber\":\"\",\"salt\":\"\",\"sex\":\"0\",\"status\":\"0\",\"updateUser\":\"\",\"userName\":\"admin\",\"userType\":\"00\"},{\"avatar\":\"\",\"createUser\":\"\",\"delFlag\":\"0\",\"email\":\"\",\"emailFlag\":0,\"id\":\"1514612549704769537\",\"loginIp\":\"\",\"nickName\":\"\",\"password\":\"$2a$10$2uWSO/yAWOfD0.eBcRjpS.fjs1LuCG0wQjpkai/hM/wPvkNBfKcz6\",\"phoneFlag\":0,\"phoneNumber\":\"123\",\"salt\":\"\",\"sex\":\"0\",\"status\":\"0\",\"updateUser\":\"\",\"userName\":\"test\",\"userType\":\"00\"},{\"avatar\":\"\",\"createTime\":\"2022-04-14T22:51:54\",\"createUser\":\"1\",\"delFlag\":\"0\",\"email\":\"\",\"emailFlag\":0,\"id\":\"1514617446730846210\",\"loginIp\":\"\",\"nickName\":\"\",\"password\":\"$2a$10$1hufqRnHsyY6OPwkw3bD8ODcsGEn3RBhsFV8RqqjicqTBSZ31ugia\",\"phoneFlag\":0,\"phoneNumber\":\"123\",\"salt\":\"\",\"sex\":\"0\",\"status\":\"0\",\"updateTime\":\"2022-04-14T22:51:54\",\"updateUser\":\"1\",\"userName\":\"test1\",\"userType\":\"00\"}],\"navigateFirstPage\":1,\"navigateLastPage\":1,\"navigatePages\":8,\"navigatepageNums\":[1],\"nextPage\":0,\"pageNum\":1,\"pageSize\":3,\"pages\":1,\"prePage\":0,\"size\":3,\"startRow\":0,\"total\":3}',0,'','2022-04-14 22:58:22'),('1514619110493818881','查询用户：列表查询/分页查询','com.leaves.web.system.SysUserController.queryUserList()','POST','1','/sys-user/query/list','0:0:0:0:0:0:0:1','内网IP','{\"avatar\":\"\",\"id\":\"\",\"pageNum\":0,\"pageSize\":0,\"phoneNumber\":\"\",\"roleId\":\"\",\"sex\":\"\",\"userName\":\"\"}','{\"endRow\":2,\"hasNextPage\":false,\"hasPreviousPage\":false,\"isFirstPage\":true,\"isLastPage\":true,\"list\":[{\"avatar\":\"\",\"createUser\":\"\",\"delFlag\":\"0\",\"email\":\"\",\"emailFlag\":0,\"id\":\"1\",\"loginIp\":\"\",\"nickName\":\"管理员\",\"password\":\"$2a$10$rMIF.gT726JMzJLhu4Hyb.6vgvuTnZgtBi3HLefAsRP05ths3uW7m\",\"phoneFlag\":0,\"phoneNumber\":\"\",\"salt\":\"\",\"sex\":\"0\",\"status\":\"0\",\"updateUser\":\"\",\"userName\":\"admin\",\"userType\":\"00\"},{\"avatar\":\"\",\"createUser\":\"\",\"delFlag\":\"0\",\"email\":\"\",\"emailFlag\":0,\"id\":\"1514612549704769537\",\"loginIp\":\"\",\"nickName\":\"\",\"password\":\"$2a$10$2uWSO/yAWOfD0.eBcRjpS.fjs1LuCG0wQjpkai/hM/wPvkNBfKcz6\",\"phoneFlag\":0,\"phoneNumber\":\"123\",\"salt\":\"\",\"sex\":\"0\",\"status\":\"0\",\"updateUser\":\"\",\"userName\":\"test\",\"userType\":\"00\"},{\"avatar\":\"\",\"createTime\":\"2022-04-14T22:51:54\",\"createUser\":\"1\",\"delFlag\":\"0\",\"email\":\"\",\"emailFlag\":0,\"id\":\"1514617446730846210\",\"loginIp\":\"\",\"nickName\":\"\",\"password\":\"$2a$10$1hufqRnHsyY6OPwkw3bD8ODcsGEn3RBhsFV8RqqjicqTBSZ31ugia\",\"phoneFlag\":0,\"phoneNumber\":\"123\",\"salt\":\"\",\"sex\":\"0\",\"status\":\"0\",\"updateTime\":\"2022-04-14T22:51:54\",\"updateUser\":\"1\",\"userName\":\"test1\",\"userType\":\"00\"}],\"navigateFirstPage\":1,\"navigateLastPage\":1,\"navigatePages\":8,\"navigatepageNums\":[1],\"nextPage\":0,\"pageNum\":1,\"pageSize\":3,\"pages\":1,\"prePage\":0,\"size\":3,\"startRow\":0,\"total\":3}',0,'','2022-04-14 22:58:31'),('1514619248041824258','查询用户：列表查询/分页查询','com.leaves.web.system.SysUserController.queryUserList()','POST','1','/sys-user/query/list','0:0:0:0:0:0:0:1','内网IP','{\"avatar\":\"\",\"id\":\"\",\"pageNum\":0,\"pageSize\":0,\"phoneNumber\":\"\",\"roleId\":\"\",\"sex\":\"\",\"userName\":\"\"}','{\"endRow\":2,\"hasNextPage\":false,\"hasPreviousPage\":false,\"isFirstPage\":true,\"isLastPage\":true,\"list\":[{\"avatar\":\"\",\"createUser\":\"\",\"delFlag\":\"0\",\"email\":\"\",\"emailFlag\":0,\"id\":\"1\",\"loginIp\":\"\",\"nickName\":\"管理员\",\"password\":\"$2a$10$rMIF.gT726JMzJLhu4Hyb.6vgvuTnZgtBi3HLefAsRP05ths3uW7m\",\"phoneFlag\":0,\"phoneNumber\":\"\",\"salt\":\"\",\"sex\":\"0\",\"status\":\"0\",\"updateUser\":\"\",\"userName\":\"admin\",\"userType\":\"00\"},{\"avatar\":\"\",\"createUser\":\"\",\"delFlag\":\"0\",\"email\":\"\",\"emailFlag\":0,\"id\":\"1514612549704769537\",\"loginIp\":\"\",\"nickName\":\"\",\"password\":\"$2a$10$2uWSO/yAWOfD0.eBcRjpS.fjs1LuCG0wQjpkai/hM/wPvkNBfKcz6\",\"phoneFlag\":0,\"phoneNumber\":\"123\",\"salt\":\"\",\"sex\":\"0\",\"status\":\"0\",\"updateUser\":\"\",\"userName\":\"test\",\"userType\":\"00\"},{\"avatar\":\"\",\"createTime\":\"2022-04-14T22:51:54\",\"createUser\":\"1\",\"delFlag\":\"0\",\"email\":\"\",\"emailFlag\":0,\"id\":\"1514617446730846210\",\"loginIp\":\"\",\"nickName\":\"\",\"password\":\"$2a$10$1hufqRnHsyY6OPwkw3bD8ODcsGEn3RBhsFV8RqqjicqTBSZ31ugia\",\"phoneFlag\":0,\"phoneNumber\":\"123\",\"salt\":\"\",\"sex\":\"0\",\"status\":\"0\",\"updateTime\":\"2022-04-14T22:51:54\",\"updateUser\":\"1\",\"userName\":\"test1\",\"userType\":\"00\"}],\"navigateFirstPage\":1,\"navigateLastPage\":1,\"navigatePages\":8,\"navigatepageNums\":[1],\"nextPage\":0,\"pageNum\":1,\"pageSize\":3,\"pages\":1,\"prePage\":0,\"size\":3,\"startRow\":0,\"total\":3}',0,'','2022-04-14 22:59:04'),('1514619280086306817','新增用户','com.leaves.web.system.SysUserController.saveSysUser()','POST','1','/sys-user/save','0:0:0:0:0:0:0:1','内网IP','{\"avatar\":\"\",\"pageNum\":0,\"pageSize\":0,\"phoneNumber\":\"123\",\"roleId\":\"\",\"sex\":\"0\",\"userName\":\"test12\"}','true',0,'','2022-04-14 22:59:11'),('1514621816600039425','查询用户：列表查询/分页查询','com.leaves.web.system.SysUserController.queryUserList()','POST','1','/sys-user/query/list','0:0:0:0:0:0:0:1','内网IP','{\"avatar\":\"\",\"id\":\"\",\"pageNum\":0,\"pageSize\":0,\"phoneNumber\":\"\",\"roleId\":\"\",\"sex\":\"\",\"userName\":\"\"}','{\"endRow\":3,\"hasNextPage\":false,\"hasPreviousPage\":false,\"isFirstPage\":true,\"isLastPage\":true,\"list\":[{\"avatar\":\"\",\"createUser\":\"\",\"delFlag\":\"0\",\"email\":\"\",\"emailFlag\":0,\"id\":\"1\",\"loginIp\":\"\",\"nickName\":\"管理员\",\"password\":\"$2a$10$rMIF.gT726JMzJLhu4Hyb.6vgvuTnZgtBi3HLefAsRP05ths3uW7m\",\"phoneFlag\":0,\"phoneNumber\":\"\",\"salt\":\"\",\"sex\":\"0\",\"status\":\"0\",\"updateUser\":\"\",\"userName\":\"admin\",\"userType\":\"00\"},{\"avatar\":\"\",\"createUser\":\"\",\"delFlag\":\"0\",\"email\":\"\",\"emailFlag\":0,\"id\":\"1514612549704769537\",\"loginIp\":\"\",\"nickName\":\"\",\"password\":\"$2a$10$2uWSO/yAWOfD0.eBcRjpS.fjs1LuCG0wQjpkai/hM/wPvkNBfKcz6\",\"phoneFlag\":0,\"phoneNumber\":\"123\",\"salt\":\"\",\"sex\":\"0\",\"status\":\"0\",\"updateUser\":\"\",\"userName\":\"test\",\"userType\":\"00\"},{\"avatar\":\"\",\"createTime\":\"2022-04-14T22:51:54\",\"createUser\":\"1\",\"delFlag\":\"0\",\"email\":\"\",\"emailFlag\":0,\"id\":\"1514617446730846210\",\"loginIp\":\"\",\"nickName\":\"\",\"password\":\"$2a$10$1hufqRnHsyY6OPwkw3bD8ODcsGEn3RBhsFV8RqqjicqTBSZ31ugia\",\"phoneFlag\":0,\"phoneNumber\":\"123\",\"salt\":\"\",\"sex\":\"0\",\"status\":\"0\",\"updateTime\":\"2022-04-14T22:51:54\",\"updateUser\":\"1\",\"userName\":\"test1\",\"userType\":\"00\"},{\"avatar\":\"\",\"createTime\":\"2022-04-14T22:59:11\",\"createUser\":\"1\",\"delFlag\":\"0\",\"email\":\"\",\"emailFlag\":0,\"id\":\"1514619280052752385\",\"loginIp\":\"\",\"nickName\":\"\",\"password\":\"$2a$10$9RX4FBKV7ZW5QbNkl1QH1.OWXVercqP2ChW/Gts5Qh.1H6HSPoxlG\",\"phoneFlag\":0,\"phoneNumber\":\"123\",\"salt\":\"\",\"sex\":\"0\",\"status\":\"0\",\"updateTime\":\"2022-04-14T22:59:11\",\"updateUser\":\"1\",\"userName\":\"test12\",\"userType\":\"00\"}],\"navigateFirstPage\":1,\"navigateLastPage\":1,\"navigatePages\":8,\"navigatepageNums\":[1],\"nextPage\":0,\"pageNum\":1,\"pageSize\":4,\"pages\":1,\"prePage\":0,\"size\":4,\"startRow\":0,\"total\":4}',0,'','2022-04-14 23:09:16'),('1514984590341697537','test1','com.leaves.web.common.LoginController.test1()','POST','1','/test1','0:0:0:0:0:0:0:1','内网IP','','{\"code\":\"20000\",\"data\":{},\"msg\":\"操作成功~\"}',0,'','2022-04-15 23:10:48'),('1514985190793953281','test1','com.leaves.web.common.LoginController.test1()','POST','1','/test1','0:0:0:0:0:0:0:1','内网IP','','{\"code\":\"20000\",\"data\":{},\"msg\":\"操作成功~\"}',0,'','2022-04-15 23:13:11'),('1514985521867145217','test1','com.leaves.web.common.LoginController.test1()','POST','1','/test1','0:0:0:0:0:0:0:1','内网IP','','{\"code\":\"20000\",\"data\":{},\"msg\":\"操作成功~\"}',0,'','2022-04-15 23:14:30'),('1514987777094176770','test1','com.leaves.web.common.LoginController.test1()','POST','1','/test1','0:0:0:0:0:0:0:1','内网IP','','{\"code\":\"20000\",\"data\":{},\"msg\":\"操作成功~\"}',0,'','2022-04-15 23:23:28'),('1514988822805467138','test1','com.leaves.web.common.LoginController.test1()','POST','1','/test1','0:0:0:0:0:0:0:1','内网IP','','{\"code\":\"20000\",\"data\":{},\"msg\":\"操作成功~\"}',0,'','2022-04-15 23:27:37'),('1514990376786632705','test1','com.leaves.web.common.LoginController.test1()','POST','1','/test1','0:0:0:0:0:0:0:1','内网IP','','{\"code\":\"20000\",\"data\":{},\"msg\":\"操作成功~\"}',0,'','2022-04-15 23:33:48'),('1539443600168693762','查询部门：列表查询/分页查询','com.leaves.web.system.SysDeptController.queryUserList()','POST','1','/sys-dept/query/list','127.0.0.1','内网IP','{\"deptName\":\"\"}','[{\"ancestors\":\"0\",\"createTime\":\"2022-05-25T09:59:16\",\"createUser\":\"admin\",\"delFlag\":\"0\",\"deptName\":\"若依科技\",\"email\":\"ry@qq.com\",\"id\":\"100\",\"leader\":\"若依\",\"orderNum\":0,\"parentId\":\"0\",\"phone\":\"15888888888\",\"status\":\"0\",\"updateUser\":\"\"},{\"ancestors\":\"0,100\",\"createTime\":\"2022-05-25T09:59:16\",\"createUser\":\"admin\",\"delFlag\":\"0\",\"deptName\":\"深圳总公司\",\"email\":\"ry@qq.com\",\"id\":\"101\",\"leader\":\"若依\",\"orderNum\":1,\"parentId\":\"100\",\"phone\":\"15888888888\",\"status\":\"0\",\"updateUser\":\"\"},{\"ancestors\":\"0,100\",\"createTime\":\"2022-05-25T09:59:16\",\"createUser\":\"admin\",\"delFlag\":\"0\",\"deptName\":\"长沙分公司\",\"email\":\"ry@qq.com\",\"id\":\"102\",\"leader\":\"若依\",\"orderNum\":2,\"parentId\":\"100\",\"phone\":\"15888888888\",\"status\":\"0\",\"updateUser\":\"\"},{\"ancestors\":\"0,100,101\",\"createTime\":\"2022-05-25T09:59:16\",\"createUser\":\"admin\",\"delFlag\":\"0\",\"deptName\":\"研发部门\",\"email\":\"ry@qq.com\",\"id\":\"103\",\"leader\":\"若依\",\"orderNum\":1,\"parentId\":\"101\",\"phone\":\"15888888888\",\"status\":\"0\",\"updateUser\":\"\"},{\"ancestors\":\"0,100,101\",\"createTime\":\"2022-05-25T09:59:16\",\"createUser\":\"admin\",\"delFlag\":\"0\",\"deptName\":\"市场部门\",\"email\":\"ry@qq.com\",\"id\":\"104\",\"leader\":\"若依\",\"orderNum\":2,\"parentId\":\"101\",\"phone\":\"15888888888\",\"status\":\"0\",\"updateUser\":\"\"},{\"ancestors\":\"0,100,101\",\"createTime\":\"2022-05-25T09:59:16\",\"createUser\":\"admin\",\"delFlag\":\"0\",\"deptName\":\"测试部门\",\"email\":\"ry@qq.com\",\"id\":\"105\",\"leader\":\"若依\",\"orderNum\":3,\"parentId\":\"101\",\"phone\":\"15888888888\",\"status\":\"0\",\"updateUser\":\"\"},{\"ancestors\":\"0,100,101\",\"createTime\":\"2022-05-25T09:59:16\",\"createUser\":\"admin\",\"delFlag\":\"0\",\"deptName\":\"财务部门\",\"email\":\"ry@qq.com\",\"id\":\"106\",\"leader\":\"若依\",\"orderNum\":4,\"parentId\":\"101\",\"phone\":\"15888888888\",\"status\":\"0\",\"updateUser\":\"\"},{\"ancestors\":\"0,100,101\",\"createTime\":\"2022-05-25T09:59:16\",\"createUser\":\"admin\",\"delFlag\":\"0\",\"deptName\":\"运维部门\",\"email\":\"ry@qq.com\",\"id\":\"107\",\"leader\":\"若依\",\"orderNum\":5,\"parentId\":\"101\",\"phone\":\"15888888888\",\"status\":\"0\",\"updateUser\":\"\"},{\"ancestors\":\"0,100,102\",\"createTime\":\"2022-05-25T09:59:16\",\"createUser\":\"admin\",\"delFlag\":\"0\",',0,'','2022-06-22 11:02:10');
/*!40000 ALTER TABLE `sys_operation_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_permission`
--

DROP TABLE IF EXISTS `sys_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_permission` (
  `id` varchar(32) NOT NULL COMMENT '权限ID',
  `per_name` varchar(50) NOT NULL COMMENT '权限名称',
  `url` varchar(200) DEFAULT '#' COMMENT '请求地址',
  `per_code` varchar(100) DEFAULT NULL COMMENT '权限标识',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态（1正常 0停用）',
  `del_flag` tinyint(1) NOT NULL DEFAULT '1' COMMENT '删除标志（1代表存在 0代表删除）',
  `create_user` varchar(32) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` varchar(32) DEFAULT '' COMMENT '更新者',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='权限表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_permission`
--

LOCK TABLES `sys_permission` WRITE;
/*!40000 ALTER TABLE `sys_permission` DISABLE KEYS */;
INSERT INTO `sys_permission` VALUES ('1','1','1','sys:user:info',0,0,'',NULL,NULL,'',NULL),('2','2','2','test2',0,0,'',NULL,NULL,'',NULL),('3','3','3','test3',0,0,'',NULL,NULL,'',NULL);
/*!40000 ALTER TABLE `sys_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role` (
  `id` varchar(32) NOT NULL COMMENT '角色ID',
  `role_name` varchar(30) NOT NULL COMMENT '角色名称',
  `role_code` varchar(100) NOT NULL COMMENT '角色编码',
  `data_scope` int(11) DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态（1正常 0停用）',
  `del_flag` tinyint(1) NOT NULL DEFAULT '1' COMMENT '删除标志（1代表存在 0代表删除）',
  `create_user` varchar(32) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(32) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='角色信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` VALUES ('1','管理员','admin',1,0,0,'9527','2022-03-15 10:22:02','9527','2022-03-15 10:22:10',NULL),('2','测试','test',1,0,0,'9527','2022-03-15 10:25:28','9527','2022-03-15 10:25:34',NULL);
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_menu`
--

DROP TABLE IF EXISTS `sys_role_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role_menu` (
  `id` mediumtext,
  `role_id` mediumtext,
  `menu_id` mediumtext,
  `del_flag` tinyint(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色和资源关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_menu`
--

LOCK TABLES `sys_role_menu` WRITE;
/*!40000 ALTER TABLE `sys_role_menu` DISABLE KEYS */;
INSERT INTO `sys_role_menu` VALUES ('1','1','1',1),('2','1','2',1);
/*!40000 ALTER TABLE `sys_role_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_permission`
--

DROP TABLE IF EXISTS `sys_role_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role_permission` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `role_id` varchar(32) NOT NULL COMMENT '角色ID',
  `per_id` varchar(32) NOT NULL COMMENT '权限ID',
  `del_flag` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='角色和权限关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_permission`
--

LOCK TABLES `sys_role_permission` WRITE;
/*!40000 ALTER TABLE `sys_role_permission` DISABLE KEYS */;
INSERT INTO `sys_role_permission` VALUES ('1','1','1',1),('2','1','2',1);
/*!40000 ALTER TABLE `sys_role_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user` (
  `id` varchar(32) NOT NULL COMMENT '用户ID',
  `user_name` varchar(64) NOT NULL COMMENT '登录账号',
  `password` varchar(255) NOT NULL DEFAULT '' COMMENT '密码',
  `nick_name` varchar(255) DEFAULT '' COMMENT '用户昵称',
  `user_type` varchar(2) DEFAULT '00' COMMENT '用户类型（00系统用户 01注册用户）',
  `email` varchar(50) DEFAULT '' COMMENT '用户邮箱',
  `phone_number` varchar(11) DEFAULT '' COMMENT '手机号码',
  `description` varchar(255) DEFAULT NULL COMMENT '个人介绍',
  `email_flag` tinyint(1) DEFAULT '0' COMMENT '邮箱验证标志（1已验证 0未验证）',
  `phone_flag` tinyint(1) DEFAULT '0' COMMENT '手机验证标志（1已验证 0未验证）',
  `sex` int(11) NOT NULL DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
  `avatar` varchar(100) DEFAULT '' COMMENT '头像路径',
  `dept_id` varchar(32) DEFAULT NULL COMMENT '部门id',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态（1正常 0停用）',
  `del_flag` tinyint(1) NOT NULL DEFAULT '1' COMMENT '删除标志（1代表存在 0代表删除）',
  `create_user` varchar(32) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(32) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` VALUES ('1','admin','$2a$10$rMIF.gT726JMzJLhu4Hyb.6vgvuTnZgtBi3HLefAsRP05ths3uW7m','管理员','00','','',NULL,0,0,0,'',NULL,1,1,'',NULL,'',NULL,NULL),('1514612549704769537','test','$2a$10$2uWSO/yAWOfD0.eBcRjpS.fjs1LuCG0wQjpkai/hM/wPvkNBfKcz6','','00','','123',NULL,0,0,0,'',NULL,0,0,'',NULL,'',NULL,NULL);
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_role`
--

DROP TABLE IF EXISTS `sys_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user_role` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `user_id` varchar(32) NOT NULL COMMENT '用户ID',
  `role_id` varchar(32) NOT NULL COMMENT '角色ID',
  `def_flag` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户和角色关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_role`
--

LOCK TABLES `sys_user_role` WRITE;
/*!40000 ALTER TABLE `sys_user_role` DISABLE KEYS */;
INSERT INTO `sys_user_role` VALUES ('1','1','1',1),('2','1501554193507291137','2',1);
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

-- Dump completed on 2023-07-22  9:42:57
