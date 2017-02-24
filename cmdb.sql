set global max_allowed_packet=1000000000;
create database cmdb;
use cmdb;
--
-- Table structure for table `authority_log`
--

DROP TABLE IF EXISTS `authority_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `authority_log` (
  `log_id` int(11) NOT NULL AUTO_INCREMENT,
  `ip` varchar(32) DEFAULT NULL,
  `user` varchar(32) DEFAULT NULL,
  `time` varchar(32) DEFAULT NULL,
  `info` text,
  PRIMARY KEY (`log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authority_log`
--

LOCK TABLES `authority_log` WRITE;
/*!40000 ALTER TABLE `authority_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `authority_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `authority_pages`
--

DROP TABLE IF EXISTS `authority_pages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `authority_pages` (
  `pages_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `pages_url` varchar(200) DEFAULT NULL COMMENT '访问页面URL',
  `pages_name` varchar(100) DEFAULT NULL COMMENT '菜单名字',
  `last_modify_user` varchar(4000) DEFAULT NULL COMMENT '最近修改用户',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `last_modify_time` bigint(20) DEFAULT NULL COMMENT '最后修改时间',
  `is_valid` int(11) DEFAULT NULL COMMENT '是否有效',
  `model_name` varchar(100) DEFAULT NULL COMMENT '所属模块',
  PRIMARY KEY (`pages_id`),
  UNIQUE KEY `uidx_pages_url` (`pages_url`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authority_pages`
--

LOCK TABLES `authority_pages` WRITE;
/*!40000 ALTER TABLE `authority_pages` DISABLE KEYS */;
/*!40000 ALTER TABLE `authority_pages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `authority_permission`
--

DROP TABLE IF EXISTS `authority_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `authority_permission` (
  `permission_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `pages_id` int(11) DEFAULT NULL COMMENT '页面id参考pages',
  `last_modify_user` varchar(32) DEFAULT NULL COMMENT '最近修改时间',
  `last_modify_time` varchar(32) DEFAULT NULL,
  `is_valid` int(11) DEFAULT NULL COMMENT '是否有效',
  `user` varchar(50) DEFAULT NULL COMMENT '用户名',
  PRIMARY KEY (`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authority_permission`
--

LOCK TABLES `authority_permission` WRITE;
/*!40000 ALTER TABLE `authority_permission` DISABLE KEYS */;
/*!40000 ALTER TABLE `authority_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `authority_user`
--

DROP TABLE IF EXISTS `authority_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `authority_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(32) DEFAULT NULL COMMENT '用户名',
  `password` varchar(32) DEFAULT NULL COMMENT '密码',
  `last_login` varchar(32) DEFAULT NULL COMMENT '最近登录时间',
  `login_faild_count` int(11) DEFAULT NULL COMMENT '登录失败次数',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `uidx_auth_user_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authority_user`
--

LOCK TABLES `authority_user` WRITE;
/*!40000 ALTER TABLE `authority_user` DISABLE KEYS */;
INSERT INTO `authority_user` VALUES (3,'admin','21232f297a57a5a743894a0e4a801fc3',NULL,NULL);
/*!40000 ALTER TABLE `authority_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cmdb_quartz`
--

DROP TABLE IF EXISTS `cmdb_quartz`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cmdb_quartz` (
  `quartz_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `quartz_name` varchar(32) DEFAULT NULL COMMENT '任务名称',
  `quartz_url` varchar(300) DEFAULT NULL COMMENT '任务接口地址',
  `quartz_time` int(11) DEFAULT NULL COMMENT '任务执行时间, 秒',
  `description` varchar(200) DEFAULT NULL COMMENT '描述信息',
  `last_modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近修改时间',
  `last_modify_user` varchar(32) DEFAULT NULL COMMENT '最近修改用户',
  PRIMARY KEY (`quartz_id`),
  UNIQUE KEY `uidx_cmdb_quartz_quartz_name` (`quartz_name`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cmdb_quartz`
--

LOCK TABLES `cmdb_quartz` WRITE;
/*!40000 ALTER TABLE `cmdb_quartz` DISABLE KEYS */;
INSERT INTO `cmdb_quartz` VALUES (13,'ping','http://v.asura.com/resource/configure/server/checkActive',300,'资源服务器存活检查','2016-08-10 12:54:09',''),(15,'pingAddress','http://v.asura.com/resource/configure/network/ping',300,'ping网段中的地址','2016-08-16 01:21:26','');
/*!40000 ALTER TABLE `cmdb_quartz` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cmdb_resource_cabinet`
--

DROP TABLE IF EXISTS `cmdb_resource_cabinet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cmdb_resource_cabinet` (
  `cabinet_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '机柜编号',
  `cabinet_name` varchar(200) DEFAULT NULL COMMENT '机柜名称',
  `floor_id` int(11) DEFAULT NULL,
  `last_modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `last_modify_user` varchar(32) DEFAULT NULL COMMENT '最近修改用户',
  `create_time` bigint(20) DEFAULT NULL,
  `number` int(11) DEFAULT NULL COMMENT '最多存放设备',
  `cabinet_scope` varchar(100) DEFAULT NULL COMMENT '机柜所属区域',
  PRIMARY KEY (`cabinet_id`),
  UNIQUE KEY `uidx_cabinet_name` (`cabinet_name`)
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cmdb_resource_cabinet`
--

LOCK TABLES `cmdb_resource_cabinet` WRITE;
/*!40000 ALTER TABLE `cmdb_resource_cabinet` DISABLE KEYS */;
INSERT INTO `cmdb_resource_cabinet` VALUES (1,NULL,NULL,'2016-07-31 02:12:11',NULL,NULL,NULL,12,NULL),(3,'I01',3,'2016-07-31 02:59:49','','',1469764453,12,'I排');
/*!40000 ALTER TABLE `cmdb_resource_cabinet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cmdb_resource_entname`
--

DROP TABLE IF EXISTS `cmdb_resource_entname`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cmdb_resource_entname` (
  `ent_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '环境id',
  `ent_name` varchar(132) DEFAULT NULL COMMENT '环境名称',
  `last_modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `last_modify_user` varchar(32) DEFAULT NULL COMMENT '最近修改 用户',
  `create_time` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ent_id`),
  UNIQUE KEY `uid_centname` (`ent_name`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cmdb_resource_entname`
--

LOCK TABLES `cmdb_resource_entname` WRITE;
/*!40000 ALTER TABLE `cmdb_resource_entname` DISABLE KEYS */;
INSERT INTO `cmdb_resource_entname` VALUES (2,NULL,'2016-07-29 02:01:26',NULL,NULL,NULL),(3,'生产环境','2016-07-29 06:10:00','','',NULL),(5,'测试环境','2016-07-29 06:06:48','','',1469772409),(7,'开发环境','2016-07-29 06:06:52','','',1469772413),(9,'准生产环境','2016-07-29 06:06:57','','',1469772418);
/*!40000 ALTER TABLE `cmdb_resource_entname` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cmdb_resource_floor`
--

DROP TABLE IF EXISTS `cmdb_resource_floor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cmdb_resource_floor` (
  `floor_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '机房编号',
  `floor_name` varchar(1000) DEFAULT NULL COMMENT '机房楼层名称',
  `floor_address` varchar(1000) DEFAULT NULL COMMENT '机房楼层地址',
  `last_modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `last_modify_user` varchar(32) DEFAULT NULL COMMENT '最近修改用户',
  `create_time` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`floor_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cmdb_resource_floor`
--

LOCK TABLES `cmdb_resource_floor` WRITE;
/*!40000 ALTER TABLE `cmdb_resource_floor` DISABLE KEYS */;
INSERT INTO `cmdb_resource_floor` VALUES (3,'酒仙桥机房4层','酒仙桥','2016-07-29 03:52:47','','',NULL);
/*!40000 ALTER TABLE `cmdb_resource_floor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cmdb_resource_groups`
--

DROP TABLE IF EXISTS `cmdb_resource_groups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cmdb_resource_groups` (
  `groups_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '业务组ID',
  `groups_name` varchar(250) DEFAULT NULL,
  `last_modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `last_modify_user` varchar(32) DEFAULT NULL COMMENT '最近修改 用户',
  `create_time` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`groups_id`),
  UNIQUE KEY `uidx_groups_groups_name` (`groups_name`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cmdb_resource_groups`
--

LOCK TABLES `cmdb_resource_groups` WRITE;
/*!40000 ALTER TABLE `cmdb_resource_groups` DISABLE KEYS */;
/*!40000 ALTER TABLE `cmdb_resource_groups` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cmdb_resource_inventory`
--

DROP TABLE IF EXISTS `cmdb_resource_inventory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cmdb_resource_inventory` (
  `inventory_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id,主键',
  `title` varchar(100) DEFAULT NULL COMMENT '中心标志',
  `groups_id` varchar(200) DEFAULT NULL COMMENT '中心拥有的组id',
  `last_modify_time` varchar(32) DEFAULT NULL COMMENT '最近想修改时间',
  `inventory_number` int(11) DEFAULT NULL COMMENT '库存数量',
  `inventory_totle` int(11) DEFAULT NULL COMMENT '库存总量',
  `last_modify_user` varchar(32) DEFAULT NULL COMMENT '最近修改用户',
  `update_time` varchar(32) DEFAULT NULL COMMENT '数据更新时间',
  `inventory_used` int(11) DEFAULT NULL COMMENT '已使用数量',
  PRIMARY KEY (`inventory_id`),
  UNIQUE KEY `uidx_inventory_title` (`title`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cmdb_resource_inventory`
--

LOCK TABLES `cmdb_resource_inventory` WRITE;
/*!40000 ALTER TABLE `cmdb_resource_inventory` DISABLE KEYS */;
INSERT INTO `cmdb_resource_inventory` VALUES (1,'租住','3,5,7,9,28',NULL,-20,0,'zhaozq14','1484557695',265),(2,'服务','11,17,30,31,32',NULL,23,30,'zhaozq14','1484710237',220),(3,'民宿','1',NULL,20,20,'zhaozq14','1484278784',67),(4,'家修','15',NULL,67,68,'zhaozq14','1484710237',48),(5,'阿修罗寓','27',NULL,20,20,'zhaozq14','1484278864',40);
/*!40000 ALTER TABLE `cmdb_resource_inventory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cmdb_resource_network`
--

DROP TABLE IF EXISTS `cmdb_resource_network`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cmdb_resource_network` (
  `network_id` int(11) NOT NULL AUTO_INCREMENT,
  `network_prefix` varchar(100) DEFAULT NULL COMMENT '前缀',
  `last_modify_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `create_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `last_modify_user` varchar(32) DEFAULT NULL COMMENT '最近修改 用户',
  `create_time` bigint(20) DEFAULT NULL,
  `network_suffix` int(11) DEFAULT NULL COMMENT '后缀',
  `description` varchar(100) DEFAULT NULL COMMENT '描述信息',
  PRIMARY KEY (`network_id`),
  UNIQUE KEY `idx_network_prefix` (`network_prefix`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cmdb_resource_network`
--

LOCK TABLES `cmdb_resource_network` WRITE;
/*!40000 ALTER TABLE `cmdb_resource_network` DISABLE KEYS */;
INSERT INTO `cmdb_resource_network` VALUES (3,'10.16.25','2016-08-15 13:53:47','','',NULL,24,'25段生产环境');
/*!40000 ALTER TABLE `cmdb_resource_network` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cmdb_resource_network_address`
--

DROP TABLE IF EXISTS `cmdb_resource_network_address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cmdb_resource_network_address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ip_prefix_id` int(11) DEFAULT NULL COMMENT '参考网络的id',
  `ip_subffix` int(11) DEFAULT NULL COMMENT 'ip地址后缀',
  `status` int(11) DEFAULT NULL COMMENT '使用状态',
  `last_modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_ip_prefix_id_ip_subffix` (`ip_prefix_id`,`ip_subffix`)
) ENGINE=InnoDB AUTO_INCREMENT=4825 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cmdb_resource_network_address`
--

LOCK TABLES `cmdb_resource_network_address` WRITE;
/*!40000 ALTER TABLE `cmdb_resource_network_address` DISABLE KEYS */;
INSERT INTO `cmdb_resource_network_address` VALUES (1,13,11,1,'2017-01-30 03:08:57'),(3,11,53,0,'2017-01-30 03:09:16'),(5,13,179,0,'2017-01-30 03:09:21'),(7,15,219,0,'2017-01-30 03:10:35'),(9,9,162,0,'2017-01-30 03:10:24'),(11,13,153,1,'2017-01-30 03:09:16'),(13,15,74,0,'2017-01-30 03:09:37'),(15,15,218,0,'2017-01-30 03:10:34'),(17,9,47,0,'2017-01-30 03:09:17'),(19,7,28,1,'2017-01-30 03:08:15'),(21,13,228,0,'2017-01-30 03:10:12'),(23,9,38,0,'2017-01-30 03:09:08'),(25,3,102,1,'2017-01-30 03:08:04'),(27,13,218,0,'2017-01-30 03:09:57'),(29,5,224,1,'2017-01-30 03:08:37'),(31,5,241,1,'2017-01-30 03:08:37'),(33,7,117,1,'2017-01-30 03:08:57'),(35,15,239,0,'2017-01-30 03:10:55'),(37,11,71,1,'2017-01-30 03:09:23'),(39,15,145,1,'2017-01-30 03:09:49'),(41,15,150,1,'2017-01-30 03:09:49'),(43,5,104,1,'2017-01-30 03:08:25'),(45,7,33,1,'2017-01-30 03:08:15'),(47,5,38,1,'2017-01-30 03:08:16'),(49,13,196,0,'2017-01-30 03:09:37'),(51,11,47,0,'2017-01-30 03:09:13'),(53,3,179,0,'2017-01-30 03:08:39'),(55,15,186,0,'2017-01-30 03:09:57'),(57,11,150,0,'2017-01-30 03:10:12'),(59,11,119,0,'2017-01-30 03:09:45'),(61,9,214,0,'2017-01-30 03:11:18'),(63,13,152,1,'2017-01-30 03:09:16'),(65,9,121,1,'2017-01-30 03:09:56'),(67,11,43,0,'2017-01-30 03:09:11'),(69,11,195,0,'2017-01-30 03:10:54'),(71,11,94,1,'2017-01-30 03:09:33'),(73,5,172,1,'2017-01-30 03:08:33'),(75,11,107,0,'2017-01-30 03:09:42'),(77,15,207,0,'2017-01-30 03:10:22'),(79,15,137,1,'2017-01-30 03:09:49'),(81,15,104,1,'2017-01-30 03:09:48'),(83,3,147,1,'2017-01-30 03:08:20'),(85,15,50,1,'2017-01-30 03:09:12'),(87,7,106,1,'2017-01-30 03:08:57'),(89,7,250,0,'2017-01-30 03:09:16'),(91,13,223,0,'2017-01-30 03:10:03'),(93,9,241,0,'2017-01-30 03:11:46'),(95,9,68,0,'2017-01-30 03:09:40'),(97,13,165,1,'2017-01-30 03:09:17'),(99,9,212,0,'2017-01-30 03:11:16');
/*!40000 ALTER TABLE `cmdb_resource_network_address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cmdb_resource_os_type`
--

DROP TABLE IF EXISTS `cmdb_resource_os_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cmdb_resource_os_type` (
  `os_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '操作系统id',
  `os_name` varchar(132) DEFAULT NULL COMMENT '操作系统名称',
  `last_modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `last_modify_user` varchar(32) DEFAULT NULL COMMENT '最近修改 用户',
  `create_time` bigint(20) DEFAULT NULL,
  `image_path` varchar(200) DEFAULT NULL COMMENT '图片路径',
  PRIMARY KEY (`os_id`),
  UNIQUE KEY `uidx_os_name` (`os_name`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cmdb_resource_os_type`
--

LOCK TABLES `cmdb_resource_os_type` WRITE;
/*!40000 ALTER TABLE `cmdb_resource_os_type` DISABLE KEYS */;
INSERT INTO `cmdb_resource_os_type` VALUES (1,'windows_2012','2016-07-29 08:14:29','','',NULL,''),(3,'centos','2016-07-29 08:13:26','','',1469780008,''),(5,'centos6.5','2016-07-29 08:14:52','','',1469780093,''),(7,'centos6.6','2016-07-29 08:14:55','','',1469780096,''),(9,'ubantu','2016-07-29 08:15:16','','',1469780117,'');
/*!40000 ALTER TABLE `cmdb_resource_os_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cmdb_resource_server`
--

DROP TABLE IF EXISTS `cmdb_resource_server`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cmdb_resource_server` (
  `server_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '服务器ID',
  `groups_id` int(11) DEFAULT NULL COMMENT '业务线组',
  `type_id` int(11) DEFAULT NULL COMMENT '服务器类型',
  `os_id` int(11) DEFAULT NULL COMMENT '参考操作 系统类型',
  `cabinet_id` int(11) DEFAULT NULL COMMENT '机柜ID',
  `user_id` varchar(100) DEFAULT NULL COMMENT '参考用户id,管理员',
  `service_id` varchar(100) DEFAULT NULL COMMENT '参考服务类型ID,逗号分隔',
  `memory` varchar(32) DEFAULT NULL COMMENT '内存大小',
  `cpu` varchar(32) DEFAULT NULL COMMENT 'cpu个数',
  `host_id` int(11) DEFAULT NULL COMMENT '参考宿主机ID,自己的表,类型为宿主机的',
  `manager_ip` varchar(32) DEFAULT NULL COMMENT '远程管理卡IP',
  `domain_name` varchar(300) DEFAULT NULL COMMENT '域名',
  `ent_id` int(11) DEFAULT NULL COMMENT '参考环境管理,所属环境',
  `disk_size` varchar(100) DEFAULT NULL COMMENT '硬盘大小',
  `ip_address` varchar(250) DEFAULT NULL,
  `open_port` varchar(200) DEFAULT NULL COMMENT '开放端口',
  `last_modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `last_modify_user` varchar(32) DEFAULT NULL COMMENT '最近修改 用户',
  `cabinet_level` int(11) DEFAULT NULL COMMENT '所在机柜的位置',
  `create_time` bigint(20) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL COMMENT '设备描述信息',
  `expire` varchar(32) DEFAULT NULL COMMENT '过期时间',
  `asset_coding` varchar(132) DEFAULT NULL COMMENT '资产编码',
  `status` int(11) DEFAULT NULL COMMENT '简单测试连通性,ping，检查是否活着',
  PRIMARY KEY (`server_id`),
  UNIQUE KEY `uid_ip_address` (`ip_address`),
  UNIQUE KEY `uidx_cabinet_levelcabinet_id` (`cabinet_id`,`cabinet_level`),
  KEY `uidx_server_id` (`server_id`),
  KEY `uidx_manager_ip` (`manager_ip`)
) ENGINE=InnoDB AUTO_INCREMENT=2293 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cmdb_resource_server`
--

LOCK TABLES `cmdb_resource_server` WRITE;
/*!40000 ALTER TABLE `cmdb_resource_server` DISABLE KEYS */;
/*!40000 ALTER TABLE `cmdb_resource_server` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cmdb_resource_server_type`
--

DROP TABLE IF EXISTS `cmdb_resource_server_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cmdb_resource_server_type` (
  `type_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '服务类型ID',
  `type_name` varchar(32) DEFAULT NULL,
  `last_modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `last_modify_user` varchar(32) DEFAULT NULL COMMENT '最近修改 用户',
  `create_time` bigint(20) DEFAULT NULL,
  `image_path` varchar(200) DEFAULT NULL COMMENT '图片路径',
  `depend` varchar(5) DEFAULT NULL COMMENT '参考的类型',
  PRIMARY KEY (`type_id`),
  UNIQUE KEY `uidx_service_type` (`type_name`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cmdb_resource_server_type`
--

LOCK TABLES `cmdb_resource_server_type` WRITE;
/*!40000 ALTER TABLE `cmdb_resource_server_type` DISABLE KEYS */;
INSERT INTO `cmdb_resource_server_type` VALUES (1,'KVM虚拟机','2016-07-29 23:15:20','','',NULL,'','3'),(3,'KVM宿主机','2016-07-29 08:10:29','','',NULL,'',NULL),(5,'数据库物理机','2016-07-29 08:11:04','','',1469779866,'',NULL),(7,'应用物理机','2016-07-29 08:11:12','','',1469779873,'',NULL),(9,'二层交换机','2016-07-29 08:11:22','','',1469779883,'',NULL),(11,'三层交换机','2016-07-29 08:11:34','','',1469779895,'',NULL),(13,'防火墙','2016-07-29 08:11:39','','',1469779901,'',NULL),(15,'路由器','2016-07-29 08:11:44','','',1469779905,'',NULL),(17,'虚拟机','2016-07-29 08:11:59','','',1469779921,'',NULL),(19,'物理机','2016-07-29 08:12:05','','',1469779927,'',NULL),(21,'存储设备','2016-07-29 08:21:22','','',1469780483,'',NULL),(23,'光纤交换机','2016-07-29 08:21:40','','',1469780501,'',NULL),(24,'VMware虚拟机','2016-08-30 08:02:57','','',NULL,'','25'),(25,'VMware物理机','2016-08-30 08:02:42','','',1472543929,'','19');
/*!40000 ALTER TABLE `cmdb_resource_server_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cmdb_resource_service`
--

DROP TABLE IF EXISTS `cmdb_resource_service`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cmdb_resource_service` (
  `service_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '服务类型id',
  `service_name` varchar(132) DEFAULT NULL COMMENT '服务类型名称',
  `last_modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `last_modify_user` varchar(32) DEFAULT NULL COMMENT '最近修改 用户',
  `create_time` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`service_id`),
  UNIQUE KEY `uidx_service` (`service_name`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cmdb_resource_service`
--

LOCK TABLES `cmdb_resource_service` WRITE;
/*!40000 ALTER TABLE `cmdb_resource_service` DISABLE KEYS */;
INSERT INTO `cmdb_resource_service` VALUES (3,'nginx','2016-07-30 08:18:19','','',1469866701),(5,'jdk.1.7_tomcat_7.0.56','2016-08-02 05:43:31','','',NULL),(7,'MySQL5.6','2016-11-09 02:50:18','','',NULL),(9,'kvm','2016-07-30 10:04:56','','',1469873098),(11,'nginx_php_5.4.22','2016-08-02 05:42:34','','',1470116554),(12,'zookeeper','2016-08-30 02:22:33','','',1472523520),(13,'nginx_php-5.6.25_50G','2016-09-09 06:37:42','','',NULL),(14,'nodejs','2016-09-10 08:28:10','','',1473495858),(15,'dubbo','2016-10-11 06:02:07','','',1476165726),(16,'hadoop','2016-11-08 00:32:20','','',1478565140),(17,'redis','2016-11-08 00:38:36','','',1478565516),(18,'alibaba-rocketmq','2016-11-08 01:24:35','','',1478568275),(19,'Oracle11.2.0.1','2016-11-09 02:49:51','','',1478659791),(20,'MySQL5.5','2016-11-09 02:50:34','','',1478659834),(21,'MySQL5.7','2016-11-09 02:50:51','','',1478659851),(25,'jdk.1.8_tomcat_7.0.56','2016-12-02 10:28:25','admin','admin',1480674505);
/*!40000 ALTER TABLE `cmdb_resource_service` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cmdb_resource_user`
--

DROP TABLE IF EXISTS `cmdb_resource_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cmdb_resource_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `user_name` varchar(100) DEFAULT NULL COMMENT '用户名称',
  `groups_id` int(11) DEFAULT NULL COMMENT '业务线组',
  `last_modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `last_modify_user` varchar(32) DEFAULT NULL COMMENT '最近修改 用户',
  `create_time` bigint(20) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `mobile` varchar(11) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  KEY `uidx_user_name` (`user_name`,`mobile`,`email`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cmdb_resource_user`
--

LOCK TABLES `cmdb_resource_user` WRITE;
/*!40000 ALTER TABLE `cmdb_resource_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `cmdb_resource_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `monitor_configure`
--

DROP TABLE IF EXISTS `monitor_configure`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `monitor_configure` (
  `configure_id` int(11) NOT NULL AUTO_INCREMENT,
  `host_id` int(11) DEFAULT NULL COMMENT '主机,参考资源server_id',
  `description` varchar(200) DEFAULT NULL COMMENT '描述信息',
  `monitor_time` varchar(25) DEFAULT '7x24',
  `alarm_count` int(11) DEFAULT NULL COMMENT '报警次数',
  `alarm_interval` int(11) DEFAULT NULL COMMENT '报警间隔',
  `script_id` int(11) DEFAULT NULL COMMENT '脚本名，参考脚本id',
  `is_valid` int(11) DEFAULT NULL COMMENT '是否有效',
  `last_modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近修改时间',
  `last_modify_user` varchar(20) DEFAULT NULL COMMENT '最近修改人',
  `template_id` varchar(1000) DEFAULT NULL COMMENT '使用模板,可以使用多个用逗号分隔',
  `groups_id` varchar(1000) DEFAULT NULL COMMENT '使用组,多个用逗号分隔',
  `retry` int(11) DEFAULT NULL COMMENT '失败重试次数',
  `monitor_configure_tp` varchar(20) DEFAULT NULL,
  `monitor_hosts_tp` varchar(20) DEFAULT NULL,
  `hosts` varchar(2000) DEFAULT NULL COMMENT '监控服务器IP地址，参考cmdb的server_id',
  `arg1` varchar(1000) DEFAULT NULL COMMENT '参数1',
  `arg2` varchar(1000) DEFAULT NULL COMMENT '参数2',
  `arg3` varchar(1000) DEFAULT NULL COMMENT '参数3',
  `arg4` varchar(1000) DEFAULT NULL COMMENT '参数4',
  `arg5` varchar(1000) DEFAULT NULL COMMENT '参数5',
  `arg6` varchar(1000) DEFAULT NULL COMMENT '参数6',
  `arg7` varchar(1000) DEFAULT NULL COMMENT '参数7',
  `arg8` varchar(1000) DEFAULT NULL COMMENT '参数8',
  `check_interval` int(11) DEFAULT NULL COMMENT '脚本检查间隔',
  `is_mobile` int(11) DEFAULT NULL COMMENT '报警发送给手机,1有效，0无效',
  `is_email` int(11) DEFAULT NULL COMMENT '报警发送给手机,1有效，0无效',
  `is_ding` int(11) DEFAULT NULL COMMENT '报警发送给钉钉,1有效，0无效',
  `is_weixin` int(11) DEFAULT NULL COMMENT '报警发送给微信,1有效，0无效',
  `weixin_groups` varchar(1000) DEFAULT NULL,
  `ding_groups` varchar(1000) DEFAULT NULL,
  `mobile_groups` varchar(1000) DEFAULT NULL,
  `email_groups` varchar(1000) DEFAULT NULL,
  `all_groups` varchar(1000) DEFAULT NULL,
  `item_id` varchar(12) DEFAULT NULL,
  `gname` varchar(11) DEFAULT NULL,
  PRIMARY KEY (`configure_id`),
  UNIQUE KEY `uidx_description` (`description`),
  KEY `idx_configure_hid_gid_sid` (`host_id`,`groups_id`(255),`script_id`,`is_valid`,`template_id`(255))
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `monitor_configure`
--

LOCK TABLES `monitor_configure` WRITE;
/*!40000 ALTER TABLE `monitor_configure` DISABLE KEYS */;
/*!40000 ALTER TABLE `monitor_configure` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `monitor_contact_group`
--

DROP TABLE IF EXISTS `monitor_contact_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `monitor_contact_group` (
  `group_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `group_name` varchar(50) DEFAULT NULL COMMENT '组名字',
  `description` varchar(50) DEFAULT NULL COMMENT '描述信息',
  `member` varchar(100) DEFAULT NULL COMMENT '组成员',
  `ismail` int(11) DEFAULT NULL COMMENT '是否发送邮件',
  `ismobile` int(11) DEFAULT NULL COMMENT '是否发送邮件',
  `status` int(11) DEFAULT NULL COMMENT '状态',
  `last_modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近修改时间',
  `last_modify_user` varchar(32) DEFAULT NULL COMMENT '最近修改用户',
  `is_admin` int(11) DEFAULT NULL COMMENT '是否是管理员组,所有报警都发送到管理员组',
  PRIMARY KEY (`group_id`),
  UNIQUE KEY `uidx_group_name` (`group_name`)
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `monitor_contact_group`
--

LOCK TABLES `monitor_contact_group` WRITE;
/*!40000 ALTER TABLE `monitor_contact_group` DISABLE KEYS */;
/*!40000 ALTER TABLE `monitor_contact_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `monitor_contacts`
--

DROP TABLE IF EXISTS `monitor_contacts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `monitor_contacts` (
  `contacts_id` int(11) NOT NULL AUTO_INCREMENT,
  `member_name` varchar(30) DEFAULT NULL,
  `mobile` varchar(11) DEFAULT NULL,
  `mail` varchar(50) DEFAULT NULL,
  `no` varchar(13) DEFAULT NULL COMMENT '工号',
  `last_modify_user` varchar(100) DEFAULT NULL COMMENT '最近修改人',
  `last_modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近修改时间',
  PRIMARY KEY (`contacts_id`),
  UNIQUE KEY `idx_contacts_member_name` (`member_name`,`mobile`,`mail`)
) ENGINE=InnoDB AUTO_INCREMENT=444 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `monitor_contacts`
--

LOCK TABLES `monitor_contacts` WRITE;
/*!40000 ALTER TABLE `monitor_contacts` DISABLE KEYS */;
/*!40000 ALTER TABLE `monitor_contacts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `monitor_groups`
--

DROP TABLE IF EXISTS `monitor_groups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `monitor_groups` (
  `groups_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ä¸»é”®',
  `groups_name` varchar(200) DEFAULT NULL COMMENT 'ç»„åç§°',
  `last_modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'æœ€è¿‘ä¿®æ”¹æ—¶é—´',
  `last_modify_user` varchar(32) DEFAULT NULL COMMENT 'æœ€è¿‘ä¿®æ”¹ç”¨æˆ·',
  `is_valid` int(11) DEFAULT NULL COMMENT 'æ˜¯å¦æœ‰æ•ˆ',
  `hosts` text COMMENT 'æ‹¥æœ‰çš„ä¸»æœº',
  `other_name` varchar(100) DEFAULT NULL COMMENT 'ç»„åˆ«å',
  PRIMARY KEY (`groups_id`),
  UNIQUE KEY `uidx_monitor_groups_groups_name` (`groups_name`,`other_name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `monitor_groups`
--

LOCK TABLES `monitor_groups` WRITE;
/*!40000 ALTER TABLE `monitor_groups` DISABLE KEYS */;
INSERT INTO `monitor_groups` VALUES (1,'3','2016-09-11 23:07:27','',1,'282,286,304,306,308,461',''),(2,'1','2016-09-11 23:51:20','',1,'995,997,1017','日志组');
/*!40000 ALTER TABLE `monitor_groups` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `monitor_index_alarm`
--

DROP TABLE IF EXISTS `monitor_index_alarm`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `monitor_index_alarm` (
  `index_id` int(11) DEFAULT NULL COMMENT '参考指标表id',
  `server_id` int(11) DEFAULT NULL COMMENT '参考cmdb的表server_id',
  `gt_value` varchar(30) DEFAULT NULL,
  `lt_value` varchar(30) DEFAULT NULL,
  `eq_value` varchar(30) DEFAULT NULL,
  `last_modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近修改时间',
  `last_modify_user` varchar(32) DEFAULT NULL COMMENT '最近修改用户',
  `status_id` int(11) DEFAULT NULL COMMENT '条件达成后报警级别',
  `weixin_groups` varchar(1000) DEFAULT NULL COMMENT '微信组',
  `ding_groups` varchar(1000) DEFAULT NULL,
  `mobile_groups` varchar(1000) DEFAULT NULL,
  `email_groups` varchar(1000) DEFAULT NULL,
  `alarm_id` int(11) NOT NULL AUTO_INCREMENT,
  `is_mobile` int(11) DEFAULT NULL,
  `is_ding` int(11) DEFAULT NULL,
  `is_weixin` int(11) DEFAULT NULL,
  `is_email` int(11) DEFAULT NULL,
  `is_alarm` int(11) DEFAULT NULL,
  `not_eq_value` varchar(30) DEFAULT NULL,
  `all_groups` varchar(1000) DEFAULT NULL COMMENT '所有报警组',
  `alarm_count` varchar(3) DEFAULT NULL COMMENT '报警次数',
  `alarm_interval` varchar(3) DEFAULT NULL COMMENT '报警间隔',
  PRIMARY KEY (`alarm_id`),
  UNIQUE KEY `uidx_sid_indexid` (`server_id`,`index_id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `monitor_index_alarm`
--

LOCK TABLES `monitor_index_alarm` WRITE;
/*!40000 ALTER TABLE `monitor_index_alarm` DISABLE KEYS */;
/*!40000 ALTER TABLE `monitor_index_alarm` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `monitor_index_from_scripts`
--

DROP TABLE IF EXISTS `monitor_index_from_scripts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `monitor_index_from_scripts` (
  `index_id` int(11) NOT NULL AUTO_INCREMENT,
  `index_name` varchar(100) DEFAULT NULL COMMENT '指标名称',
  `scripts_id` int(11) DEFAULT NULL COMMENT '指标属于的脚本',
  `last_modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近修改时间',
  `last_modify_user` varchar(100) DEFAULT NULL COMMENT '最近修改人',
  `description` varchar(30) DEFAULT NULL COMMENT ' 描述信息',
  `unit` varchar(10) DEFAULT NULL COMMENT 'æŒ‡æ ‡å•ä½',
  PRIMARY KEY (`index_id`),
  UNIQUE KEY `uidx_index_name` (`index_name`)
) ENGINE=InnoDB AUTO_INCREMENT=618 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `monitor_index_from_scripts`
--

LOCK TABLES `monitor_index_from_scripts` WRITE;
/*!40000 ALTER TABLE `monitor_index_from_scripts` DISABLE KEYS */;
INSERT INTO `monitor_index_from_scripts` VALUES (1,'loadavg.system.load.1',12,'2016-11-03 07:57:34','','1分钟负载',''),(3,'loadavg.system.load.5',12,'2016-11-03 07:57:44','','5分钟负载',''),(5,'loadavg.system.load.15',12,'2016-11-03 07:57:22','','15分钟负载',''),(7,'Mysql.Questions',20,'2016-11-14 10:12:08','','查询数量',''),(9,'Mysql.Com_select',20,'2016-10-31 01:08:34','','无缓冲查询数量',NULL),(11,'Mysql.Com_delete',20,'2016-10-31 01:11:34','','删除数量',NULL),(13,'Mysql.Com_update',20,'2016-10-31 01:11:44','','更新数量',NULL),(15,'Mysql.Qcache_hits',20,'2016-10-31 01:19:18','','Qcache命中',NULL),(17,'Mysql.Threads_connected',20,'2016-10-31 01:19:44','','打开链接数',NULL),(19,'Mysql.Hit',20,'2016-10-31 01:19:58','','命中率',NULL),(21,'Mysql.tps',20,'2016-10-31 01:20:14','','Tps',NULL),(32,'cpu.system.cpu.nice',7,'2016-11-03 07:42:30','','cpu使用率nice','%'),(33,'cpu.system.cpu.steal',7,'2016-11-03 07:42:56','','cpu使用率steal','%'),(34,'cpu.system.cpu.iowait',7,'2016-11-03 07:44:22','','cpu使用率iowait越大表示IO延迟越多','%'),(35,'cpu.system.cpu.idle',7,'2016-11-03 07:56:40','','越大表示cpu越空闲','%'),(37,'IO.system.io.r_s.vda',18,'2016-11-03 08:00:45','','设备每秒读的次数',''),(38,'IO.system.io.r_s.sda',18,'2016-11-03 08:00:50','','设备每秒读的次数',''),(39,'IO.system.io.w_s.sda',18,'2016-11-03 08:01:03','','设备每秒写的次数',''),(40,'IO.system.io.w_s.vda',18,'2016-11-03 08:01:08','','设备每秒写的次数',''),(41,'IO.system.io.Blk_read.vda',18,'2016-11-03 08:02:16','','设备每秒读的KB数',''),(42,'IO.system.io.Blk_read.sda',18,'2016-11-03 08:02:36','','设备每秒读的KB数',''),(43,'IO.system.io.Blk_wrtn.sda',18,'2016-11-03 08:02:57','','设备每秒写的KB数',''),(44,'IO.system.io.Blk_wrtn.vda',18,'2016-11-03 08:03:01','','设备每秒写的KB数',''),(45,'memory.system.mem.used.percent',5,'2016-11-03 08:07:46','','内存使用率百分比','%'),(46,'memory.system.mem.totle',5,'2016-11-03 08:08:09','','内存大小','KB'),(48,'memory.system.mem.free',5,'2016-11-03 08:09:59','','内存空闲','KB'),(49,'memory.system.mem.used',5,'2016-11-03 08:09:50','','内存使用大小','KB'),(50,'memory.system.mem.cache',5,'2016-11-03 08:09:06','','内存使用cache大小','KB'),(51,'memory.system.mem.buffer',5,'2016-11-03 08:09:12','','内存使用buffer大小','KB'),(52,'memory.system.swap.used',5,'2016-11-03 08:09:31','','swap使用大小','KB'),(53,'memory.system.swap.free',5,'2016-11-03 08:09:40','','swap空闲大小','KB'),(54,'PHP-FPM.php_fpm.listen_queue.max_size',21,'2016-11-04 04:49:26','','请求等待队列最高的数量',''),(55,'PHP-FPM.php_fpm.listen_queue.size',21,'2016-11-04 04:50:05','','请求等待队列，如果这个值不为0，那么要增加FPM的进程数量',''),(56,'PHP-FPM.php_fpm.processes.active',21,'2016-11-04 04:50:36','','活跃进程数量',''),(57,'PHP-FPM.php_fpm.processes.idle',21,'2016-11-04 04:50:53','','空闲进程数量',''),(58,'PHP-FPM.php_fpm.processes.totle',21,'2016-11-04 04:51:16','','总进程数量',''),(59,'PHP-FPM.php_fpm.processes.max_active',21,'2016-11-04 04:51:44','','最大的活跃进程数量（FPM启动开始算）',''),(60,'PHP_FPM.php_fpm.processes.max_reached',21,'2016-11-14 10:23:18','','进程最大数量限制的次数，如果不为0，说明最大进程数量太小了',''),(61,'PHP_FPM.php_fpm.requests.accepted_conn',21,'2016-11-04 04:54:22','','当前池子接受的请求数',''),(62,'PHP_FPM.php_fpm.requests.slow',21,'2016-11-04 04:54:38','','启用了php-fpm slow-log，缓慢请求的数量',''),(337,'cpu.system.cpu.user',7,'2016-11-04 08:07:53',NULL,NULL,NULL),(338,'cpu.system.cpu.system',7,'2016-11-04 08:07:53',NULL,NULL,NULL),(339,'traffic.system.net.bytes.rcvd.eth0',17,'2016-11-04 08:07:54',NULL,NULL,NULL),(340,'traffic.system.net.packets_in.error.eth0',17,'2016-11-04 08:07:54',NULL,NULL,NULL),(341,'traffic.system.net.packets_out.count.eth0',17,'2016-11-04 08:07:54',NULL,NULL,NULL),(342,'traffic.system.net.bytes.send.eth0',17,'2016-11-04 08:07:54',NULL,NULL,NULL),(343,'traffic.system.net.packets_in.count.eth0',17,'2016-11-04 08:07:54',NULL,NULL,NULL),(344,'traffic.system.net.packets_out.error.eth0',17,'2016-11-04 08:07:54',NULL,NULL,NULL),(345,'IO.system.io.util.vda',18,'2016-11-04 08:07:54',NULL,NULL,NULL),(346,'IO.system.io.await.vda',18,'2016-11-04 08:07:54',NULL,NULL,NULL),(347,'Nginx.nginx.net.request_per_s',21,'2016-11-04 08:07:54',NULL,NULL,NULL),(348,'Nginx.nginx.net.conn_opened_per_s',21,'2016-11-04 08:07:54',NULL,NULL,NULL),(349,'Nginx.nginx.net.conn_dropped_per_s',21,'2016-11-04 08:07:54',NULL,NULL,NULL),(350,'Nginx.nginx.net.connections',21,'2016-11-04 08:07:54',NULL,NULL,NULL),(351,'Nginx.nginx.net.reading',21,'2016-11-04 08:07:54',NULL,NULL,NULL),(352,'Nginx.nginx.net.writing',21,'2016-11-04 08:07:54',NULL,NULL,NULL),(353,'Nginx.nginx.net.waiting',21,'2016-11-04 08:07:54',NULL,NULL,NULL),(354,'PHP_FPM.php_fpm.listen_queue.max_size',21,'2016-11-04 08:07:54',NULL,NULL,NULL),(355,'PHP_FPM.php_fpm.listen_queue.size',21,'2016-11-04 08:07:54',NULL,NULL,NULL),(356,'PHP_FPM.php_fpm.processes.active',21,'2016-11-04 08:07:54',NULL,NULL,NULL),(357,'PHP_FPM.php_fpm.processes.idle',21,'2016-11-14 10:13:52','','php进程空闲数量',''),(358,'PHP_FPM.php_fpm.processes.totle',21,'2016-11-04 08:07:54',NULL,NULL,NULL),(359,'PHP_FPM.php_fpm.processes.max_active',21,'2016-11-04 08:07:54',NULL,NULL,NULL),(360,'traffic.system.net.packets_in.count.eth1',17,'2016-11-04 08:07:54',NULL,NULL,NULL),(361,'traffic.system.net.bytes.send.eth1',17,'2016-11-04 08:07:54',NULL,NULL,NULL),(362,'traffic.system.net.bytes.rcvd.eth1',17,'2016-11-04 08:07:54',NULL,NULL,NULL),(363,'traffic.system.net.packets_in.error.eth1',17,'2016-11-04 08:07:54',NULL,NULL,NULL),(364,'traffic.system.net.packets_out.count.eth1',17,'2016-11-04 08:07:54',NULL,NULL,NULL),(365,'traffic.system.net.packets_out.error.eth1',17,'2016-11-04 08:07:54',NULL,NULL,NULL),(366,'disk.system.disk.used.percent.SLASH',16,'2016-11-04 08:07:54',NULL,NULL,NULL),(367,'disk.system.disk.used.percent.SLASHboot',16,'2016-11-04 08:07:54',NULL,NULL,NULL),(368,'disk.system.disk.totle.SLASH',16,'2016-11-04 08:07:54',NULL,NULL,NULL),(369,'disk.system.disk.in_use.SLASH',16,'2016-11-04 08:07:54',NULL,NULL,NULL),(370,'disk.system.disk.free.SLASH',16,'2016-11-04 08:07:55',NULL,NULL,NULL),(371,'disk.system.disk.totle.SLASHboot',16,'2016-11-04 08:07:55',NULL,NULL,NULL),(372,'disk.system.disk.in_use.SLASHboot',16,'2016-11-04 08:07:55',NULL,NULL,NULL),(373,'disk.system.disk.free.SLASHboot',16,'2016-11-04 08:07:55',NULL,NULL,NULL),(374,'disk.system.fs.inodes.use.percent.SLASH',16,'2016-11-04 08:07:55',NULL,NULL,NULL),(375,'disk.system.fs.inodes.totle.SLASH',16,'2016-11-04 08:07:55',NULL,NULL,NULL),(376,'disk.system.fs.inodes.in_use.SLASH',16,'2016-11-04 08:07:55',NULL,NULL,NULL),(377,'disk.system.fs.inodes.free.SLASH',16,'2016-11-04 08:07:55',NULL,NULL,NULL),(378,'disk.system.fs.inodes.use.percent.SLASHboot',16,'2016-11-04 08:07:55',NULL,NULL,NULL),(379,'disk.system.fs.inodes.totle.SLASHboot',16,'2016-11-04 08:07:55',NULL,NULL,NULL),(380,'disk.system.fs.inodes.in_use.SLASHboot',16,'2016-11-04 08:07:55',NULL,NULL,NULL),(381,'disk.system.fs.inodes.free.SLASHboot',16,'2016-11-04 08:07:55',NULL,NULL,NULL),(382,'IO.system.io.w_s.vdb',18,'2016-11-04 08:07:55',NULL,NULL,NULL),(383,'IO.system.io.await.sda',18,'2016-11-04 08:07:55',NULL,NULL,NULL),(384,'IO.system.io.util.sda',18,'2016-11-04 08:07:55',NULL,NULL,NULL),(385,'IO.system.io.Blk_read.vdb',18,'2016-11-04 08:07:55',NULL,NULL,NULL),(386,'IO.system.io.Blk_wrtn.vdb',18,'2016-11-04 08:07:55',NULL,NULL,NULL),(387,'IO.system.io.await.vdb',18,'2016-11-04 08:07:55',NULL,NULL,NULL),(388,'IO.system.io.util.vdb',18,'2016-11-04 08:07:55',NULL,NULL,NULL),(389,'IO.system.io.r_s.vdc',18,'2016-11-04 08:07:55',NULL,NULL,NULL),(390,'IO.system.io.r_s.vdb',18,'2016-11-04 08:07:55',NULL,NULL,NULL),(391,'IO.system.io.w_s.vdc',18,'2016-11-04 08:07:55',NULL,NULL,NULL),(392,'IO.system.io.Blk_read.vdc',18,'2016-11-04 08:07:55',NULL,NULL,NULL),(393,'IO.system.io.Blk_wrtn.vdc',18,'2016-11-04 08:07:55',NULL,NULL,NULL),(394,'IO.system.io.util.vdc',18,'2016-11-04 08:07:55',NULL,NULL,NULL),(395,'IO.system.io.await.vdc',18,'2016-11-04 08:07:55',NULL,NULL,NULL),(396,'IO.system.io.await.sdc',18,'2016-11-04 08:07:55',NULL,NULL,NULL),(397,'IO.system.io.await.sdb',18,'2016-11-04 08:07:55',NULL,NULL,NULL),(398,'IO.system.io.Blk_read.sdb',18,'2016-11-04 08:07:56',NULL,NULL,NULL),(399,'IO.system.io.w_s.sdb',18,'2016-11-04 08:07:56',NULL,NULL,NULL),(400,'IO.system.io.r_s.sdc',18,'2016-11-04 08:07:56',NULL,NULL,NULL),(401,'IO.system.io.w_s.sdc',18,'2016-11-04 08:07:56',NULL,NULL,NULL),(402,'IO.system.io.r_s.sdb',18,'2016-11-04 08:07:56',NULL,NULL,NULL),(403,'IO.system.io.Blk_wrtn.sdc',18,'2016-11-04 08:07:56',NULL,NULL,NULL),(404,'IO.system.io.util.sdc',18,'2016-11-04 08:07:56',NULL,NULL,NULL),(405,'IO.system.io.Blk_wrtn.sdb',18,'2016-11-04 08:07:56',NULL,NULL,NULL),(406,'IO.system.io.Blk_read.sdc',18,'2016-11-04 08:07:56',NULL,NULL,NULL),(407,'IO.system.io.util.sdb',18,'2016-11-04 08:07:56',NULL,NULL,NULL),(408,'Mysql.Com_insert',20,'2016-11-04 08:07:56',NULL,NULL,NULL),(409,'Mysql.Innodb_row_lock_waits',20,'2016-11-04 08:07:56',NULL,NULL,NULL),(410,'Mysql.Slow_queries',20,'2016-11-04 08:07:56',NULL,NULL,NULL),(411,'port.8081',19,'2016-11-04 08:07:56',NULL,NULL,NULL),(412,'disk.system.disk.free.percent.SLASH',16,'2016-11-04 08:07:56',NULL,NULL,NULL),(413,'disk.system.disk.free.percent.SLASHboot',16,'2016-11-04 08:07:56',NULL,NULL,NULL),(414,'disk.system.disk.used.percent.SLASHhome',16,'2016-11-04 08:07:56',NULL,NULL,NULL),(415,'disk.system.disk.totle.SLASHhome',16,'2016-11-04 08:07:56',NULL,NULL,NULL),(416,'disk.system.disk.in_use.SLASHhome',16,'2016-11-04 08:07:56',NULL,NULL,NULL),(417,'disk.system.disk.free.SLASHhome',16,'2016-11-04 08:07:56',NULL,NULL,NULL),(418,'disk.system.fs.inodes.use.percent.SLASHhome',16,'2016-11-04 08:07:56',NULL,NULL,NULL),(419,'disk.system.fs.inodes.totle.SLASHhome',16,'2016-11-04 08:07:56',NULL,NULL,NULL),(420,'disk.system.fs.inodes.in_use.SLASHhome',16,'2016-11-04 08:07:56',NULL,NULL,NULL),(421,'disk.system.fs.inodes.free.SLASHhome',16,'2016-11-04 08:07:56',NULL,NULL,NULL),(422,'ping.ping',10,'2016-11-14 10:22:31',NULL,NULL,NULL),(423,'disk.system.disk.used.percent.SLASHhomeSLASHmvn',16,'2016-11-14 10:22:31',NULL,NULL,NULL),(424,'disk.system.disk.totle.SLASHhomeSLASHmvn',16,'2016-11-14 10:22:31',NULL,NULL,NULL),(425,'disk.system.disk.in_use.SLASHhomeSLASHmvn',16,'2016-11-14 10:22:31',NULL,NULL,NULL),(426,'disk.system.disk.free.SLASHhomeSLASHmvn',16,'2016-11-14 10:22:31',NULL,NULL,NULL),(427,'disk.system.fs.inodes.use.percent.SLASHhomeSLASHmvn',16,'2016-11-14 10:22:31',NULL,NULL,NULL),(428,'disk.system.fs.inodes.totle.SLASHhomeSLASHmvn',16,'2016-11-14 10:22:31',NULL,NULL,NULL),(429,'disk.system.fs.inodes.in_use.SLASHhomeSLASHmvn',16,'2016-11-14 10:22:31',NULL,NULL,NULL),(430,'disk.system.fs.inodes.free.SLASHhomeSLASHmvn',16,'2016-11-14 10:22:31',NULL,NULL,NULL),(431,'disk.system.disk.used.percent.SLASHusrSLASHlocalSLASHmysql',16,'2016-11-14 10:22:31',NULL,NULL,NULL),(432,'disk.system.disk.used.percent.SLASHtmp',16,'2016-11-14 10:22:31',NULL,NULL,NULL),(433,'disk.system.disk.totle.SLASHusrSLASHlocalSLASHmysql',16,'2016-11-14 10:22:31',NULL,NULL,NULL),(434,'disk.system.disk.in_use.SLASHusrSLASHlocalSLASHmysql',16,'2016-11-14 10:22:31',NULL,NULL,NULL),(435,'disk.system.disk.free.SLASHusrSLASHlocalSLASHmysql',16,'2016-11-14 10:22:32',NULL,NULL,NULL),(436,'disk.system.fs.inodes.use.percent.SLASHusrSLASHlocalSLASHmysql',16,'2016-11-14 10:22:32',NULL,NULL,NULL),(437,'disk.system.fs.inodes.totle.SLASHusrSLASHlocalSLASHmysql',16,'2016-11-14 10:22:32',NULL,NULL,NULL),(438,'disk.system.fs.inodes.in_use.SLASHusrSLASHlocalSLASHmysql',16,'2016-11-14 10:22:32',NULL,NULL,NULL),(439,'disk.system.fs.inodes.free.SLASHusrSLASHlocalSLASHmysql',16,'2016-11-14 10:22:32',NULL,NULL,NULL),(440,'disk.system.disk.used.percent.SLASHusrSLASHlocalSLASHmysqlSLASHdata',16,'2016-11-14 10:22:32',NULL,NULL,NULL),(441,'disk.system.disk.totle.SLASHusrSLASHlocalSLASHmysqlSLASHdata',16,'2016-11-14 10:22:32',NULL,NULL,NULL),(442,'disk.system.disk.in_use.SLASHusrSLASHlocalSLASHmysqlSLASHdata',16,'2016-11-14 10:22:32',NULL,NULL,NULL),(443,'disk.system.disk.free.SLASHusrSLASHlocalSLASHmysqlSLASHdata',16,'2016-11-14 10:22:32',NULL,NULL,NULL),(444,'disk.system.fs.inodes.use.percent.SLASHusrSLASHlocalSLASHmysqlSLASHdata',16,'2016-11-14 10:22:32',NULL,NULL,NULL),(445,'disk.system.fs.inodes.totle.SLASHusrSLASHlocalSLASHmysqlSLASHdata',16,'2016-11-14 10:22:32',NULL,NULL,NULL),(446,'disk.system.fs.inodes.in_use.SLASHusrSLASHlocalSLASHmysqlSLASHdata',16,'2016-11-14 10:22:32',NULL,NULL,NULL),(447,'disk.system.fs.inodes.free.SLASHusrSLASHlocalSLASHmysqlSLASHdata',16,'2016-11-14 10:22:32',NULL,NULL,NULL),(448,'disk.system.disk.used.percent.SLASHusr',16,'2016-11-14 10:22:32',NULL,NULL,NULL),(449,'disk.system.disk.used.percent.SLASHvar',16,'2016-11-14 10:22:32',NULL,NULL,NULL),(450,'disk.system.disk.totle.SLASHtmp',16,'2016-11-14 10:22:32',NULL,NULL,NULL),(451,'disk.system.disk.in_use.SLASHtmp',16,'2016-11-14 10:22:32',NULL,NULL,NULL),(452,'disk.system.disk.free.SLASHtmp',16,'2016-11-14 10:22:32',NULL,NULL,NULL),(453,'disk.system.disk.totle.SLASHusr',16,'2016-11-14 10:22:32',NULL,NULL,NULL),(454,'disk.system.disk.in_use.SLASHusr',16,'2016-11-14 10:22:32',NULL,NULL,NULL),(455,'disk.system.disk.free.SLASHusr',16,'2016-11-14 10:22:32',NULL,NULL,NULL),(456,'disk.system.disk.totle.SLASHvar',16,'2016-11-14 10:22:32',NULL,NULL,NULL),(457,'disk.system.disk.in_use.SLASHvar',16,'2016-11-14 10:22:32',NULL,NULL,NULL),(458,'disk.system.disk.free.SLASHvar',16,'2016-11-14 10:22:32',NULL,NULL,NULL),(459,'disk.system.fs.inodes.use.percent.SLASHtmp',16,'2016-11-14 10:22:32',NULL,NULL,NULL),(460,'disk.system.fs.inodes.totle.SLASHtmp',16,'2016-11-14 10:22:32',NULL,NULL,NULL),(461,'disk.system.fs.inodes.in_use.SLASHtmp',16,'2016-11-14 10:22:32',NULL,NULL,NULL),(462,'disk.system.fs.inodes.free.SLASHtmp',16,'2016-11-14 10:22:32',NULL,NULL,NULL),(463,'disk.system.fs.inodes.use.percent.SLASHusr',16,'2016-11-14 10:22:32',NULL,NULL,NULL),(464,'disk.system.fs.inodes.totle.SLASHusr',16,'2016-11-14 10:22:32',NULL,NULL,NULL),(465,'disk.system.fs.inodes.in_use.SLASHusr',16,'2016-11-14 10:22:32',NULL,NULL,NULL),(466,'disk.system.fs.inodes.free.SLASHusr',16,'2016-11-14 10:22:32',NULL,NULL,NULL),(467,'disk.system.fs.inodes.use.percent.SLASHvar',16,'2016-11-14 10:22:32',NULL,NULL,NULL),(468,'disk.system.fs.inodes.totle.SLASHvar',16,'2016-11-14 10:22:32',NULL,NULL,NULL),(469,'disk.system.fs.inodes.in_use.SLASHvar',16,'2016-11-14 10:22:32',NULL,NULL,NULL),(470,'disk.system.fs.inodes.free.SLASHvar',16,'2016-11-14 10:22:32',NULL,NULL,NULL),(471,'traffic.system.net.bytes.rcvd.em1',17,'2016-11-14 10:22:32',NULL,NULL,NULL),(472,'traffic.system.net.bytes.send.em1',17,'2016-11-14 10:22:32',NULL,NULL,NULL),(473,'traffic.system.net.packets_in.count.em1',17,'2016-11-14 10:22:33',NULL,NULL,NULL),(474,'traffic.system.net.packets_in.error.em1',17,'2016-11-14 10:22:33',NULL,NULL,NULL),(475,'traffic.system.net.packets_out.count.em1',17,'2016-11-14 10:22:33',NULL,NULL,NULL),(476,'traffic.system.net.packets_out.error.em1',17,'2016-11-14 10:22:33',NULL,NULL,NULL),(477,'traffic.system.net.packets_in.count.em2',17,'2016-11-14 10:22:33',NULL,NULL,NULL),(478,'traffic.system.net.packets_in.error.em2',17,'2016-11-14 10:22:33',NULL,NULL,NULL),(479,'traffic.system.net.bytes.rcvd.em2',17,'2016-11-14 10:22:33',NULL,NULL,NULL),(480,'traffic.system.net.packets_out.count.em4',17,'2016-11-14 10:22:33',NULL,NULL,NULL),(481,'traffic.system.net.packets_in.count.em3',17,'2016-11-14 10:22:33',NULL,NULL,NULL),(482,'traffic.system.net.packets_in.error.em4',17,'2016-11-14 10:22:33',NULL,NULL,NULL),(483,'traffic.system.net.bytes.send.em3',17,'2016-11-14 10:22:33',NULL,NULL,NULL),(484,'traffic.system.net.packets_out.error.em2',17,'2016-11-14 10:22:33',NULL,NULL,NULL),(485,'traffic.system.net.packets_out.error.em4',17,'2016-11-14 10:22:33',NULL,NULL,NULL),(486,'traffic.system.net.bytes.send.em4',17,'2016-11-14 10:22:33',NULL,NULL,NULL),(487,'traffic.system.net.packets_in.error.em3',17,'2016-11-14 10:22:33',NULL,NULL,NULL),(488,'traffic.system.net.packets_out.error.em3',17,'2016-11-14 10:22:33',NULL,NULL,NULL),(489,'traffic.system.net.packets_out.count.em2',17,'2016-11-14 10:22:33',NULL,NULL,NULL),(490,'traffic.system.net.bytes.rcvd.em4',17,'2016-11-14 10:22:33',NULL,NULL,NULL),(491,'traffic.system.net.packets_out.count.em3',17,'2016-11-14 10:22:33',NULL,NULL,NULL),(492,'traffic.system.net.bytes.rcvd.em3',17,'2016-11-14 10:22:33',NULL,NULL,NULL),(493,'traffic.system.net.packets_in.count.em4',17,'2016-11-14 10:22:33',NULL,NULL,NULL),(494,'traffic.system.net.bytes.send.em2',17,'2016-11-14 10:22:33',NULL,NULL,NULL),(495,'disk.system.disk.used.percent.SLASHbootSLASHefi',16,'2016-11-14 10:22:33',NULL,NULL,NULL),(496,'disk.system.disk.totle.SLASHbootSLASHefi',16,'2016-11-14 10:22:33',NULL,NULL,NULL),(497,'disk.system.disk.in_use.SLASHbootSLASHefi',16,'2016-11-14 10:22:33',NULL,NULL,NULL),(498,'disk.system.disk.free.SLASHbootSLASHefi',16,'2016-11-14 10:22:33',NULL,NULL,NULL),(499,'disk.system.disk.used.percent.SLASHhomeSLASHdata',16,'2016-11-14 10:22:33',NULL,NULL,NULL),(500,'disk.system.disk.totle.SLASHhomeSLASHdata',16,'2016-11-14 10:22:33',NULL,NULL,NULL),(501,'disk.system.disk.in_use.SLASHhomeSLASHdata',16,'2016-11-14 10:22:33',NULL,NULL,NULL),(502,'disk.system.disk.free.SLASHhomeSLASHdata',16,'2016-11-14 10:22:33',NULL,NULL,NULL),(503,'disk.system.fs.inodes.use.percent.SLASHhomeSLASHdata',16,'2016-11-14 10:22:33',NULL,NULL,NULL),(504,'disk.system.fs.inodes.totle.SLASHhomeSLASHdata',16,'2016-11-14 10:22:33',NULL,NULL,NULL),(505,'disk.system.fs.inodes.in_use.SLASHhomeSLASHdata',16,'2016-11-14 10:22:33',NULL,NULL,NULL),(506,'disk.system.fs.inodes.free.SLASHhomeSLASHdata',16,'2016-11-14 10:22:33',NULL,NULL,NULL),(507,'disk.system.disk.used.percent.SLASHoptSLASHappSLASHoradata',16,'2016-11-14 10:22:33',NULL,NULL,NULL),(508,'disk.system.disk.totle.SLASHoptSLASHappSLASHoradata',16,'2016-11-14 10:22:33',NULL,NULL,NULL),(509,'disk.system.disk.in_use.SLASHoptSLASHappSLASHoradata',16,'2016-11-14 10:22:33',NULL,NULL,NULL),(510,'disk.system.disk.free.SLASHoptSLASHappSLASHoradata',16,'2016-11-14 10:22:33',NULL,NULL,NULL),(511,'disk.system.fs.inodes.use.percent.SLASHoptSLASHappSLASHoradata',16,'2016-11-14 10:22:33',NULL,NULL,NULL),(512,'disk.system.fs.inodes.totle.SLASHoptSLASHappSLASHoradata',16,'2016-11-14 10:22:33',NULL,NULL,NULL),(513,'disk.system.fs.inodes.in_use.SLASHoptSLASHappSLASHoradata',16,'2016-11-14 10:22:34',NULL,NULL,NULL),(514,'disk.system.fs.inodes.free.SLASHoptSLASHappSLASHoradata',16,'2016-11-14 10:22:34',NULL,NULL,NULL),(515,'url.access',11,'2016-12-23 07:51:42',NULL,NULL,NULL),(516,'log.scripts.output.error',22,'2016-12-23 07:51:42',NULL,NULL,NULL),(517,'loadavg.loadavg.system.load.5',1,'2016-12-23 07:51:42',NULL,NULL,NULL),(518,'process.SLASHvarSLASHhollycrmSLASHtomcatSLASHtomcat_ccSLASH',23,'2016-12-23 07:51:42',NULL,NULL,NULL),(519,'process.SLASHvarSLASHhollycrmSLASHtomcatSLASHtomcat_monitorSLASHendorsed',23,'2016-12-23 07:51:42',NULL,NULL,NULL),(520,'process.SLASHvarSLASHhollycrmSLASHmongodbSLASHdata',23,'2016-12-23 07:51:42',NULL,NULL,NULL),(521,'process.SLASHusrSLASHsrcSLASHHollyIPCCSLASHHollyIVRSLASHBusiProxy.exe',23,'2016-12-23 07:51:42',NULL,NULL,NULL),(522,'process.SLASHusrSLASHsrcSLASHHollyIPCCSLASHHollyIVRSLASHGRSService.exe',23,'2016-12-23 07:51:42',NULL,NULL,NULL),(523,'process.SLASHusrSLASHsrcSLASHHollyIPCCSLASHHollyIVRSLASHMCSService.exe',23,'2016-12-23 07:51:42',NULL,NULL,NULL),(524,'process.SLASHusrSLASHsrcSLASHHollyIPCCSLASHHollyIVRSLASHVPSService.exe',23,'2016-12-23 07:51:42',NULL,NULL,NULL),(525,'process.SLASHusrSLASHsrcSLASHHollyIPCCSLASHHollyContactSLASHls.exe',23,'2016-12-23 07:51:42',NULL,NULL,NULL),(526,'process.SLASHusrSLASHsrcSLASHHollyIPCCSLASHHollyContactSLASHeds.exe',23,'2016-12-23 07:51:42',NULL,NULL,NULL),(527,'process.SLASHusrSLASHsrcSLASHHollyIPCCSLASHHollyContactSLASHContactService.exe',23,'2016-12-23 07:51:42',NULL,NULL,NULL),(528,'process.SLASHusrSLASHsrcSLASHHollyIPCCSLASHHollyContactSLASHStatService.exe',23,'2016-12-23 07:51:43',NULL,NULL,NULL),(529,'process.SLASHusrSLASHsrcSLASHHollyIPCCSLASHHollyContactSLASHBeeDBService.exe',23,'2016-12-23 07:51:43',NULL,NULL,NULL),(530,'process.SLASHusrSLASHsrcSLASHHollyIPCCSLASHHollyContactSLASHTelService_Asterisk.exe',23,'2016-12-23 07:51:43',NULL,NULL,NULL),(531,'process.SLASHusrSLASHsrcSLASHHollyIPCCSLASHHollyContactSLASHCMUService.exe',23,'2016-12-23 07:51:43',NULL,NULL,NULL),(532,'process.SLASHusrSLASHsrcSLASHHollyIPCCSLASHHollyContactSLASHJPProxyService.exe',23,'2016-12-23 07:51:43',NULL,NULL,NULL),(533,'process.SLASHusrSLASHsrcSLASHHollyIPCCSLASHHollyContactSLASHIPProxyService.exe',23,'2016-12-23 07:51:43',NULL,NULL,NULL),(534,'process.SLASHusrSLASHbinSLASHnode',23,'2016-12-23 07:51:43',NULL,NULL,NULL),(535,'process.SLASHusrSLASHsrcSLASHHollyIPCCSLASHHollyContactSLASHAgentService.exe',23,'2016-12-23 07:51:43',NULL,NULL,NULL),(536,'process.SLASHusrSLASHsrcSLASHHollyIPCCSLASHHollyContactSLASHGlobalService.exe',23,'2016-12-23 07:51:43',NULL,NULL,NULL),(537,'process.SLASHbinSLASHsh',23,'2016-12-23 07:51:43',NULL,NULL,NULL),(538,'process.SLASHusrSLASHsrcSLASHHollyIPCCSLASHHollyContactSLASHGMSServiceEx.exe',23,'2016-12-23 07:51:43',NULL,NULL,NULL),(539,'disk.system.disk.used.percent.SLASHu02',16,'2016-12-23 07:51:43',NULL,NULL,NULL),(540,'disk.system.disk.used.percent.SLASHu01',16,'2016-12-23 07:51:43',NULL,NULL,NULL),(541,'disk.system.disk.used.percent.SLASHora_bak',16,'2016-12-23 07:51:43',NULL,NULL,NULL),(542,'disk.system.disk.totle.SLASHu02',16,'2016-12-23 07:51:43',NULL,NULL,NULL),(543,'disk.system.disk.in_use.SLASHu02',16,'2016-12-23 07:51:43',NULL,NULL,NULL),(544,'disk.system.disk.free.SLASHu02',16,'2016-12-23 07:51:43',NULL,NULL,NULL),(545,'disk.system.disk.totle.SLASHu01',16,'2016-12-23 07:51:43',NULL,NULL,NULL),(546,'disk.system.disk.in_use.SLASHu01',16,'2016-12-23 07:51:43',NULL,NULL,NULL),(547,'disk.system.disk.free.SLASHu01',16,'2016-12-23 07:51:43',NULL,NULL,NULL),(548,'disk.system.disk.totle.SLASHora_bak',16,'2016-12-23 07:51:43',NULL,NULL,NULL),(549,'disk.system.disk.in_use.SLASHora_bak',16,'2016-12-23 07:51:43',NULL,NULL,NULL),(550,'disk.system.disk.free.SLASHora_bak',16,'2016-12-23 07:51:43',NULL,NULL,NULL),(551,'disk.system.fs.inodes.use.percent.SLASHu02',16,'2016-12-23 07:51:43',NULL,NULL,NULL),(552,'disk.system.fs.inodes.totle.SLASHu02',16,'2016-12-23 07:51:43',NULL,NULL,NULL),(553,'disk.system.fs.inodes.in_use.SLASHu02',16,'2016-12-23 07:51:43',NULL,NULL,NULL),(554,'disk.system.fs.inodes.free.SLASHu02',16,'2016-12-23 07:51:43',NULL,NULL,NULL),(555,'disk.system.fs.inodes.use.percent.SLASHu01',16,'2016-12-23 07:51:43',NULL,NULL,NULL),(556,'disk.system.fs.inodes.totle.SLASHu01',16,'2016-12-23 07:51:43',NULL,NULL,NULL),(557,'disk.system.fs.inodes.in_use.SLASHu01',16,'2016-12-23 07:51:43',NULL,NULL,NULL),(558,'disk.system.fs.inodes.free.SLASHu01',16,'2016-12-23 07:51:43',NULL,NULL,NULL),(559,'disk.system.fs.inodes.use.percent.SLASHora_bak',16,'2016-12-23 07:51:43',NULL,NULL,NULL),(560,'disk.system.fs.inodes.totle.SLASHora_bak',16,'2016-12-23 07:51:43',NULL,NULL,NULL),(561,'disk.system.fs.inodes.in_use.SLASHora_bak',16,'2016-12-23 07:51:43',NULL,NULL,NULL),(562,'disk.system.fs.inodes.free.SLASHora_bak',16,'2016-12-23 07:51:44',NULL,NULL,NULL),(563,'disk.system.disk.used.percent.SLASHmnt',16,'2016-12-23 07:51:44',NULL,NULL,NULL),(564,'disk.system.disk.totle.SLASHmnt',16,'2016-12-23 07:51:44',NULL,NULL,NULL),(565,'disk.system.disk.in_use.SLASHmnt',16,'2016-12-23 07:51:44',NULL,NULL,NULL),(566,'disk.system.disk.free.SLASHmnt',16,'2016-12-23 07:51:44',NULL,NULL,NULL),(567,'port.1521',19,'2016-12-23 07:51:44',NULL,NULL,NULL),(568,'logMonitor.error',1,'2016-12-23 07:51:44',NULL,NULL,NULL),(569,'traffic.system.net.packets_in.error.eth0.0081',17,'2016-12-23 07:51:44',NULL,NULL,NULL),(570,'traffic.system.net.packets_in.count.eth0.0081',17,'2016-12-23 07:51:44',NULL,NULL,NULL),(571,'traffic.system.net.bytes.send.eth0.0081',17,'2016-12-23 07:51:44',NULL,NULL,NULL),(572,'traffic.system.net.bytes.rcvd.eth0.0081',17,'2016-12-23 07:51:44',NULL,NULL,NULL),(573,'traffic.system.net.packets_out.error.eth0.0081',17,'2016-12-23 07:51:44',NULL,NULL,NULL),(574,'traffic.system.net.packets_out.count.eth0.0081',17,'2016-12-23 07:51:44',NULL,NULL,NULL),(575,'traffic.system.net.bytes.send.eth1.25',17,'2016-12-23 07:51:44',NULL,NULL,NULL),(576,'traffic.system.net.packets_in.error.eth1.25',17,'2016-12-23 07:51:44',NULL,NULL,NULL),(577,'traffic.system.net.packets_out.error.eth1.25',17,'2016-12-23 07:51:44',NULL,NULL,NULL),(578,'traffic.system.net.bytes.rcvd.eth1.25',17,'2016-12-23 07:51:44',NULL,NULL,NULL),(579,'traffic.system.net.packets_in.count.eth1.25',17,'2016-12-23 07:51:44',NULL,NULL,NULL),(580,'traffic.system.net.packets_out.count.eth1.25',17,'2016-12-23 07:51:44',NULL,NULL,NULL),(581,'traffic.system.net.packets_out.count.eth0.25',17,'2016-12-23 07:51:44',NULL,NULL,NULL),(582,'traffic.system.net.packets_in.count.eth0.25',17,'2016-12-23 07:51:44',NULL,NULL,NULL),(583,'traffic.system.net.packets_out.error.eth0.25',17,'2016-12-23 07:51:44',NULL,NULL,NULL),(584,'traffic.system.net.bytes.send.eth0.25',17,'2016-12-23 07:51:44',NULL,NULL,NULL),(585,'traffic.system.net.packets_out.error.eth0.55',17,'2016-12-23 07:51:45',NULL,NULL,NULL),(586,'traffic.system.net.bytes.send.eth0.55',17,'2016-12-23 07:51:45',NULL,NULL,NULL),(587,'traffic.system.net.packets_in.count.eth0.100',17,'2016-12-23 07:51:45',NULL,NULL,NULL),(588,'traffic.system.net.packets_out.error.eth0.100',17,'2016-12-23 07:51:45',NULL,NULL,NULL),(589,'traffic.system.net.bytes.rcvd.eth0.55',17,'2016-12-23 07:51:45',NULL,NULL,NULL),(590,'traffic.system.net.bytes.rcvd.eth0.100',17,'2016-12-23 07:51:45',NULL,NULL,NULL),(591,'traffic.system.net.packets_in.count.eth0.55',17,'2016-12-23 07:51:45',NULL,NULL,NULL),(592,'traffic.system.net.packets_in.error.eth0.25',17,'2016-12-23 07:51:45',NULL,NULL,NULL),(593,'traffic.system.net.packets_in.error.eth0.100',17,'2016-12-23 07:51:45',NULL,NULL,NULL),(594,'traffic.system.net.bytes.rcvd.eth0.25',17,'2016-12-23 07:51:45',NULL,NULL,NULL),(595,'traffic.system.net.packets_out.count.eth0.55',17,'2016-12-23 07:51:45',NULL,NULL,NULL),(596,'traffic.system.net.packets_in.error.eth0.55',17,'2016-12-23 07:51:45',NULL,NULL,NULL),(597,'traffic.system.net.bytes.send.eth0.100',17,'2016-12-23 07:51:45',NULL,NULL,NULL),(598,'traffic.system.net.packets_out.count.eth0.100',17,'2016-12-23 07:51:45',NULL,NULL,NULL),(599,'traffic.system.net.packets_in.count.eth0.15',17,'2016-12-23 07:51:45',NULL,NULL,NULL),(600,'traffic.system.net.packets_out.count.eth0.15',17,'2016-12-23 07:51:45',NULL,NULL,NULL),(601,'traffic.system.net.bytes.rcvd.eth0.15',17,'2016-12-23 07:51:45',NULL,NULL,NULL),(602,'traffic.system.net.packets_in.error.eth0.15',17,'2016-12-23 07:51:45',NULL,NULL,NULL),(603,'traffic.system.net.bytes.send.eth0.15',17,'2016-12-23 07:51:45',NULL,NULL,NULL),(604,'traffic.system.net.packets_out.error.eth0.15',17,'2016-12-23 07:51:45',NULL,NULL,NULL),(605,'traffic.system.net.packets_in.error.eth0.15.br',17,'2016-12-23 07:51:46',NULL,NULL,NULL),(606,'traffic.system.net.packets_in.count.eth0.15.br',17,'2016-12-23 07:51:46',NULL,NULL,NULL),(607,'traffic.system.net.bytes.send.eth0.15.br',17,'2016-12-23 07:51:46',NULL,NULL,NULL),(608,'traffic.system.net.packets_out.error.eth0.15.br',17,'2016-12-23 07:51:46',NULL,NULL,NULL),(609,'traffic.system.net.packets_out.count.eth0.15.br',17,'2016-12-23 07:51:46',NULL,NULL,NULL),(610,'traffic.system.net.bytes.rcvd.eth0.15.br',17,'2016-12-23 07:51:46',NULL,NULL,NULL),(611,'traffic.system.net.packets_in.count.eth0.55.br',17,'2016-12-23 07:51:46',NULL,NULL,NULL),(612,'traffic.system.net.packets_out.count.eth0.55.br',17,'2016-12-23 07:51:46',NULL,NULL,NULL),(613,'traffic.system.net.packets_in.error.eth0.55.br',17,'2016-12-23 07:51:46',NULL,NULL,NULL),(614,'traffic.system.net.bytes.send.eth0.55.br',17,'2016-12-23 07:51:46',NULL,NULL,NULL),(615,'traffic.system.net.bytes.rcvd.eth0.55.br',17,'2016-12-23 07:51:46',NULL,NULL,NULL),(616,'traffic.system.net.packets_out.error.eth0.55.br',17,'2016-12-23 07:51:46',NULL,NULL,NULL),(617,'soa.dubbo.status',9,'2016-12-23 07:51:46',NULL,NULL,NULL);
/*!40000 ALTER TABLE `monitor_index_from_scripts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `monitor_information`
--

DROP TABLE IF EXISTS `monitor_information`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `monitor_information` (
  `information_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `server_id` int(11) DEFAULT NULL COMMENT '主机ip，参考资源的server_id',
  `script_id` int(11) DEFAULT NULL COMMENT '脚本id，参考脚本id',
  `next_time` int(11) DEFAULT NULL COMMENT '下次检查时间',
  `severity_id` int(11) DEFAULT NULL COMMENT '报警状态，参考serverity_id',
  `info` varchar(3000) DEFAULT NULL,
  `notice_number` int(11) DEFAULT NULL COMMENT '监控报警次数',
  `groups_id` int(11) DEFAULT NULL COMMENT '业务线id，参考cmdb的业务线id',
  PRIMARY KEY (`information_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `monitor_information`
--

LOCK TABLES `monitor_information` WRITE;
/*!40000 ALTER TABLE `monitor_information` DISABLE KEYS */;
/*!40000 ALTER TABLE `monitor_information` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `monitor_item`
--

DROP TABLE IF EXISTS `monitor_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `monitor_item` (
  `item_id` int(11) NOT NULL AUTO_INCREMENT,
  `item_name` varchar(200) NOT NULL,
  `description` varchar(200) NOT NULL,
  `monitor_time` varchar(25) DEFAULT '7x24',
  `check_interval` int(11) DEFAULT '5',
  `alarm_count` int(11) DEFAULT '3',
  `alarm_interval` int(11) DEFAULT '15',
  `script_id` int(11) NOT NULL,
  `arg1` varchar(2000) DEFAULT NULL,
  `arg2` varchar(2000) DEFAULT NULL,
  `arg3` varchar(2000) DEFAULT NULL,
  `arg4` varchar(2000) DEFAULT NULL,
  `is_valid` int(11) DEFAULT NULL COMMENT '是否有效',
  `last_modify_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '最近修改时间',
  `last_modify_user` varchar(20) DEFAULT NULL COMMENT '最近修改人',
  `template_id` varchar(1000) DEFAULT NULL COMMENT '使用模板,可以使用多个用逗号分隔',
  `arg5` varchar(2000) DEFAULT NULL COMMENT '第5个参数',
  `arg6` varchar(2000) DEFAULT NULL COMMENT '第5个参数',
  `arg7` varchar(2000) DEFAULT NULL COMMENT '第5个参数',
  `arg8` varchar(2000) DEFAULT NULL COMMENT '第8个参数',
  `retry` int(11) DEFAULT '5',
  `is_recover` int(11) DEFAULT '1',
  `alarm_messages` varchar(800) DEFAULT NULL COMMENT '报警信息',
  `recover_messages` varchar(800) DEFAULT NULL COMMENT '恢复信息',
  `resend` int(11) DEFAULT '24' COMMENT '重新发送时间',
  `is_merge` int(11) DEFAULT NULL COMMENT '是否合并报警信息',
  `is_admin` int(11) DEFAULT NULL COMMENT '是否是管理员组,所有报警都发送到管理员组',
  `is_default` int(11) DEFAULT NULL COMMENT '1为开启，开启后agent将自动监控该项目',
  PRIMARY KEY (`item_id`),
  KEY `idx_montor_item_tid` (`template_id`(255),`script_id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `monitor_item`
--

LOCK TABLES `monitor_item` WRITE;
/*!40000 ALTER TABLE `monitor_item` DISABLE KEYS */;
INSERT INTO `monitor_item` VALUES (1,'监控rabbitmq的队列数','监控rabbitmq的队列数',NULL,30,3,15,4,'10.10.25.1','15672','sms_rab','sms_rab',1,'2016-10-03 01:40:12','',NULL,NULL,NULL,NULL,NULL,6,1,'RabbitMq队列数异常:\n${message}\n${groups}\n服务器地址:${server} ${time}','RabbitMq队列数恢复:\n${message}\n${groups}\n服务器地址:${server} ${time}                              ',0,0,NULL,NULL),(2,'监控内存','监控内存',NULL,30,3,15,5,'90',NULL,NULL,NULL,1,'2016-10-19 06:04:15','',NULL,NULL,NULL,NULL,NULL,6,1,'内存监控报警:\n${message}\n服务器地址:${server} ${time}','内存监控报警恢复:\n${message}\n服务器地址:${server} ${time}',0,0,1,1),(3,'监控CPU使用率','监控cpu恢复',NULL,120,3,15,7,'20','40',NULL,NULL,1,'2016-10-31 04:41:42','',NULL,NULL,NULL,NULL,NULL,6,0,'cpu','cpu',0,0,1,1),(5,'ping服务器','监控服务器是否能ping通 ',NULL,120,3,15,10,'10.16.35.193,10.16.35.197',NULL,NULL,NULL,0,'2016-10-09 06:06:59','',NULL,NULL,NULL,NULL,NULL,6,0,'ping失败:\n${message}\n服务器地址:${server} ${time}','ping恢复:\n${message}\n服务器地址:${server} ${time}',0,0,NULL,NULL),(6,'监控硬盘使用率','硬盘监控',NULL,600,3,30,16,'90','80',NULL,NULL,1,'2016-10-26 12:56:43','',NULL,NULL,NULL,NULL,NULL,6,1,'硬盘使用监控报警:\n${message}\n服务器地址:${server} ${time}','硬盘使用恢复:\n${message}\n服务器地址:${server} ${time}',0,0,1,1),(7,'监控系统负载','系统负载监控',NULL,300,3,30,12,'50,20,15','25,20,18',NULL,NULL,1,'2016-10-19 05:54:59','',NULL,NULL,NULL,NULL,NULL,6,1,'系统负载监控报警:\n${message}\n服务器地址:${server} ${time}','系统负载恢复:\n${message}\n服务器地址:${server} ${time}',0,0,1,1),(8,'监控网卡流量','监控服务器网卡流量',NULL,120,3,15,17,NULL,NULL,NULL,NULL,1,'2016-10-19 06:00:55','',NULL,NULL,NULL,NULL,NULL,6,1,'网卡流量异常:\n${message}\n服务器地址:${server} ${time}','网卡流量恢复:\n${message}\n服务器地址:${server} ${time}',0,0,1,1),(9,'监控磁盘IO性能','监控磁盘IO',NULL,60,3,15,18,NULL,NULL,NULL,NULL,1,'2016-10-19 05:55:07','',NULL,NULL,NULL,NULL,NULL,6,1,'磁盘IO异常报警:\n${message}\n服务器地址:${server} ${time}','磁盘IO异常恢复:\n${message}\n服务器地址:${server} ${time}',0,0,1,1),(11,'日志监控','日志输出监控',NULL,300,0,0,1,'Error,error','/home/asura/runtime/tomcat_8081/logs/catalina.out',NULL,NULL,0,'2016-10-11 06:16:45','',NULL,NULL,NULL,NULL,NULL,0,1,'日志监控报警:\n${message}\n服务器地址:${server} ${time}','日志监控恢复:\n${message}\n服务器地址:${server} ${time}',0,0,NULL,NULL),(12,'监控端口连通性','监控服务器端口连通',NULL,60,3,15,19,'22',NULL,NULL,NULL,0,'2016-10-12 06:11:39','',NULL,NULL,NULL,NULL,NULL,8,0,'端口监控异常报警:\n${message}\n服务器地址:${server} ${time}','端口监控报警恢复:\n${message}\n服务器地址:${server} ${time}',0,0,NULL,NULL),(13,'mysql性能指标监控','监控mysql指标只画图',NULL,30,3,15,20,NULL,NULL,NULL,NULL,0,'2016-10-31 04:45:28','',NULL,NULL,NULL,NULL,NULL,6,1,'','',0,0,0,0),(14,'监控nginx和php-fpm的性能','监控nginx和phpfpm默认监控',NULL,60,3,15,21,NULL,NULL,NULL,NULL,0,'2016-11-04 02:19:07','',NULL,NULL,NULL,NULL,NULL,6,1,'','',0,0,0,1),(15,'监控URL是否能访问','监控url访问',NULL,120,3,15,11,'http://www.xx.com/index.php','ok','5',NULL,0,'2016-11-17 04:55:52','',NULL,NULL,NULL,NULL,NULL,6,0,'URL访问异常报警:\n${message}\n服务器地址:${server} ${time}','URL访问恢复:\n${message}\n服务器地址:${server} ${time}',0,0,1,0),(16,'监控脚本数据','脚本输出监控',NULL,120,3,15,22,'ls',NULL,NULL,NULL,1,'2016-11-21 07:03:51','',NULL,NULL,NULL,NULL,NULL,6,1,'${message}\n服务器地址:${server} ${time}','锁表报警恢复:\n${message}\n服务器地址:${server} ${time}',0,0,1,0),(17,'进程存在检查','进程检查监控',NULL,120,3,15,23,'sshd',NULL,NULL,NULL,0,'2016-11-23 08:05:20','',NULL,NULL,NULL,NULL,NULL,6,0,'进程检查异常报警:\n${message}\n服务器地址:${server} ${time}','进程检查恢复:\n${message}\n服务器地址:${server} ${time}',0,0,1,0),(18,'soa服务监控','soa服务监控',NULL,120,3,15,24,'20880','127.0.0.1',NULL,NULL,1,'2017-01-20 23:53:47','zhaozq14',NULL,NULL,NULL,NULL,NULL,14,1,'dubbo异常报警:\n${message}\n服务器地址:${server} ${time}','dubbo报警恢复:\n${message}\n服务器地址:${server} ${time}',0,0,1,0),(19,'监控redis读写','监控redis是否可以写入数据',NULL,60,3,15,8,'127.0.0.1:6379','set a a','OK',NULL,0,'2017-01-22 09:39:22','zhaozq14',NULL,NULL,NULL,NULL,NULL,6,0,'redis 监控报警:\n${message}\n服务器地址:${server} ${time}','redis监控报警恢复:\n${message}\n服务器地址:${server} ${time}',0,0,1,0);
/*!40000 ALTER TABLE `monitor_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `monitor_message_channel`
--

DROP TABLE IF EXISTS `monitor_message_channel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `monitor_message_channel` (
  `channel_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `channel_tp` varchar(20) DEFAULT NULL,
  `channel_script` text COMMENT '发送脚本',
  `last_modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近修改时间',
  `last_modify_user` varchar(32) DEFAULT NULL COMMENT '最近修改用户',
  `is_valid` int(11) DEFAULT NULL COMMENT '是否有效',
  `smtp_server` varchar(60) DEFAULT NULL COMMENT '发送有服务器地址',
  `smtp_user` varchar(30) DEFAULT NULL COMMENT '发送有服务器用户名',
  `smtp_pass` varchar(30) DEFAULT NULL COMMENT '发送有服务器用户名密码',
  `smtp_auth` varchar(30) DEFAULT NULL COMMENT '发送服务器是否使用用户名密码',
  `mail_template` text COMMENT '邮件模板',
  `smtp_sender` varchar(50) DEFAULT NULL COMMENT '向用户显示的邮件地址',
  `ding_corp_id` varchar(200) DEFAULT NULL COMMENT '钉钉的CorpID',
  `ding_secret_id` varchar(200) DEFAULT NULL COMMENT '钉钉的SecretId',
  `ding_agent_id` bigint(20) DEFAULT NULL COMMENT '钉钉的agentid',
  `weixin_corp_id` varchar(200) DEFAULT NULL,
  `weixin_secret_id` varchar(200) DEFAULT NULL COMMENT '微信SecretId',
  `weixin_encoding_AESKey` varchar(200) DEFAULT NULL COMMENT '微信企业号的EncodingAESKey',
  `weixin_wechat_token` varchar(200) DEFAULT NULL COMMENT '微信订阅号token',
  `weixin_app_secret` varchar(200) DEFAULT NULL COMMENT '微信订阅号secret',
  `weixin_app_id` varchar(200) DEFAULT NULL COMMENT '微信订阅号app_id',
  PRIMARY KEY (`channel_id`),
  UNIQUE KEY `uidx_channel_tp` (`channel_tp`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `monitor_message_channel`
--

LOCK TABLES `monitor_message_channel` WRITE;
/*!40000 ALTER TABLE `monitor_message_channel` DISABLE KEYS */;
INSERT INTO `monitor_message_channel` VALUES (3,'email','脚本使用环境变量接收参数\nUSERNAMES CONTENT\nUSERNAMES 使用逗号分隔: 如 13101321011,13201210011,15810243121\nCONTENT 为发送的消息体\n如 shell 接收要发送的对象 to_user=\"$USERNAMES\"\n                                    脚本使用环境变量接收参数\nUSERNAMES CONTENT\nUSERNAMES 使用逗号分隔: 如 13101321011,13201210011,15810243121\nCONTENT 为发送的消息体\n如 shell 接收要发送的对象 to_user=\"$USERNAMES\"\n                                    脚本使用环境变量接收参数\nUSERNAMES CONTENT\nUSERNAMES 使用逗号分隔: 如 13101321011,13201210011,15810243121\nCONTENT 为发送的消息体\n如 shell 接收要发送的对象 to_user=\"$USERNAMES\"\n                                    脚本使用环境变量接收参数\nUSERNAMES CONTENT\nUSERNAMES 使用逗号分隔: 如 13101321011,13201210011,15810243121\nCONTENT 为发送的消息体\n如 shell 接收要发送的对象 to_user=\"$USERNAMES\"\n                                    脚本使用环境变量接收参数\nUSERNAMES CONTENT\nUSERNAMES 使用逗号分隔: 如 13101321011,13201210011,15810243121\nCONTENT 为发送的消息体\n如 shell 接收要发送的对象 to_user=\"$USERNAMES\"\n                                    脚本使用环境变量接收参数\nUSERNAMES CONTENT\nUSERNAMES 使用逗号分隔: 如 13101321011,13201210011,15810243121\nCONTENT 为发送的消息体\n如 shell 接收要发送的对象 to_user=\"$USERNAMES\"\n                                    脚本使用环境变量接收参数\nUSERNAMES CONTENT\nUSERNAMES 使用逗号分隔: 如 13101321011,13201210011,15810243121\nCONTENT 为发送的消息体\n如 shell 接收要发送的对象 to_user=\"$USERNAMES\"\n                                    脚本使用环境变量接收参数\nUSERNAMES CONTENT\nUSERNAMES 使用逗号分隔: 如 13101321011,13201210011,15810243121\nCONTENT 为发送的消息体\n如 shell 接收要发送的对象 to_user=\"$USERNAMES\"\n                                    脚本使用环境变量接收参数\nUSERNAMES CONTENT\nUSERNAMES 使用逗号分隔: 如 13101321011,13201210011,15810243121\nCONTENT 为发送的消息体\n如 shell 接收要发送的对象 to_user=\"$USERNAMES\"\n                                    \n                                                                                                        \n                                                                    \n                                                                    \n                                                                    \n                                                                    \n                                                                    \n                                                                    \n                                                                    \n                                                                    \n                                \n                                \n                                \n                                \n                                \n                                \n                                ','2017-01-22 00:10:45','zhaozq14',NULL,'smtp.asura.com.cn','','',' 0 ','<style>\n    .ibox {\n        clear: both;\n        margin-bottom: 45px;\n        margin-top: 0;\n        padding: 0;\n    }\n    .ibox-content {\n        background-color: #ffffff;\n        color: inherit;\n        padding: 15px 20px 20px 20px;\n        border-color: #e7eaec;\n        -webkit-border-image: none;\n        -o-border-image: none;\n        border-image: none;\n        border-style: solid solid none;\n        border-width: 1px 0px;\n        border: 1px solid #f0f0f0;\n    }\n    .table {\n        width: 100%;\n        max-width: 100%;\n        margin-bottom: 20px;\n    }\n    table {\n        background-color: transparent;\n    }\n    table {\n        border-spacing: 0;\n        border-collapse: collapse;\n    }\n    .table > thead > tr > th, .table > tbody > tr > th, .table > tfoot > tr > th, .table > thead > tr > td, .table > tbody > tr > td, .table > tfoot > tr > td {\n        border-top: 1px solid #e7eaec;\n        line-height: 1.42857;\n        padding: 8px;\n        vertical-align: middle;\n    }\n    .table>tbody>tr>td, .table>tbody>tr>th, .table>tfoot>tr>td, .table>tfoot>tr>th, .table>thead>tr>td, .table>thead>tr>th {\n        padding: 8px;\n        line-height: 1.42857143;\n        vertical-align: top;\n        border-top: 1px solid #ddd;\n    }\n    * {\n        -webkit-box-sizing: border-box;\n        -moz-box-sizing: border-box;\n        box-sizing: border-box;\n    }\n    user agent stylesheettr {\n        display: table-row;\n        vertical-align: inherit;\n        border-color: inherit;\n    }\n    .table > thead > tr > th, .table > tbody > tr > th, .table > tfoot > tr > th, .table > thead > tr > td, .table > tbody > tr > td, .table > tfoot > tr > td {\n        border-top: 1px solid #e7eaec;\n        line-height: 1.42857;\n        padding: 8px;\n        vertical-align: middle;\n    }\n    .table-bordered > thead > tr > th, .table-bordered > tbody > tr > th, .table-bordered > tfoot > tr > th, .table-bordered > thead > tr > td, .table-bordered > tbody > tr > td, .table-bordered > tfoot > tr > td {\n        border: 1px solid #e7e7e7;\n    }\n    .table-bordered>tbody>tr>td, .table-bordered>tbody>tr>th, .table-bordered>tfoot>tr>td, .table-bordered>tfoot>tr>th, .table-bordered>thead>tr>td, .table-bordered>thead>tr>th {\n        border: 1px solid #ddd;\n    }\n    .table>tbody>tr>td, .table>tbody>tr>th, .table>tfoot>tr>td, .table>tfoot>tr>th, .table>thead>tr>td, .table>thead>tr>th {\n        padding: 8px;\n        line-height: 1.42857143;\n        vertical-align: top;\n        border-top: 1px solid #ddd;\n    }\n    td, th {\n        padding: 0;\n    }\n\n    .ibox-title {\n        border-top:5px solid #1ab394;\n        width: 100%;\n        height: 60px;;\n    }\n    .ibox-title h6 {\n        display: inline-block;\n        font-size: 20px;\n        margin: 0 0 7px;\n        padding: 0;\n        text-overflow: ellipsis;\n        float: left;\n        margin-top: 15px;\n        margin-left: 30px;\n        margin-bottom: 5px;;\n    }\n\n    .ibox-title .label {\n        float: left;\n        margin-left: 4px;\n    }\n    .padding {\n        padding-bottom: 15px;\n        margin-top: 10px;;\n        margin-left: 20px;\n    }\n\n</style>\n<div class=\"col-sm-6\">\n    <div class=\"ibox float-e-margins\">\n        <div class=\"ibox-title\">\n            <h6>${SUBJECT}</h6>\n        </div>\n        <div class=\"ibox-content\">\n\n            <table class=\"table\">\n                <tbody>\n                <tr>\n                    <td style=\"border: 1px solid #f1f1f1;width: 20%;\"><br><span class=\"padding\">报警时间</span><br></td>\n                    <td style=\"border: 1px solid #f1f1f1;\"><br><span class=\"padding\">${TIME}</span><p></td>\n                </tr>\n                <tr>\n                    <td style=\"border: 1px solid #f1f1f1;width: 20%;\"><br><span class=\"padding\">检查项目</span><br></td>\n                    <td style=\"border: 1px solid #f1f1f1;\"><br><span class=\"padding\">${COMMAND}</span><p></td>\n\n                </tr>\n                <tr>\n                    <td style=\"border: 1px solid #f1f1f1;width: 20%;\"><br><span class=\"padding\">报警内容</span><br></td>\n                    <td style=\"border: 1px solid #f1f1f1;\"><br><span style=\"word-wrap:break-word;word-break;break-all \" class=\"padding\">${CONTENT}</span><p></td>\n\n                </tr>\n                <tr>\n                    <td style=\"border: 1px solid #f1f1f1;width: 20%;\"><br><span class=\"padding\">报警次数</span><br></td>\n                    <td style=\"border: 1px solid #f1f1f1;\"><br><span class=\"padding\">${ALARM_COUNT}</span><p></td>\n                </tr>\n                <tr>\n                    <td style=\"border: 1px solid #f1f1f1;width: 20%;\"><br><span class=\"padding\">异常服务器</span><br></td>\n                    <td style=\"border: 1px solid #f1f1f1;\"><br><span class=\"padding\">${SERVER}</span><p></td>\n\n                </tr>\n                <tr>\n                    <td style=\"border: 1px solid #f1f1f1;width: 20%;\"><br><span class=\"padding\">异常状态</span><br></td>\n                    <td style=\"border: 1px solid #f1f1f1;\"><br><span class=\"padding\">${STATUS}</span><p></td>\n\n                </tr>\n                </tbody>\n            </table>\n           <span style=\"color: red;font-size: 15px;\">该邮件为系统自动发出，请勿直接回复!\n            <span style=\"margin-left: 80%;color: #f7a54a\"><strong>阿修罗运维团队</strong></span></span>\n        </div>\n    </div>\n</div>\n\n\n\n                                                                    \n                                                                    \n                                                                    \n                                                                    \n                                                                    \n                                                                    \n                                                                    \n                                                                    ','monitor@asura.com','','',NULL,'','',NULL,'','',''),(5,'mobile','#!/usr/bin/python\n#--coding:utf-8\n# 2016-10-05\n# 短信发送脚本\nimport urllib\nimport cookielib\nimport urllib2\nimport json\nimport sys\n\nTOKEN = \"ElTahfAaQ7a\"\nURL = \"http://10..:8080/api/sms/send\"\n\ndef send_mess(mobile, messages):\n    def curl_post(url, data):\n\n        try:\n                req = urllib2.Request(url, data, headers = {\"Content-Type\":\"application/json\"})\n                resp = urllib2.urlopen(req,)\n                return resp.read()\n        except Exception, e:\n                print e\n                return\n\n    mobile = mobile.replace(\"|\", \",\").split(\",\")\n    mobile = set(mobile)\n    for i in  mobile:\n        data = {}\n        data[\"to\"] = i\n        data[\"content\"] = messages\n        data[\"token\"] = TOKEN\n        print curl_post(URL, json.dumps(data))\n\nmobile = sys.argv[1]\nmessages = \"\"\nfor d in range(2, len(sys.argv)):\n    messages += \" \"+sys.argv[d]\nprint messages\nsend_mess(mobile, messages)\n                                ','2016-12-27 22:21:08','zhaozq14',NULL,'','','',' 0 ','                                                                            发送邮件的html模板\n系统开放的字段有\n                                        ${SUBJECT}  // 主题\n                                        ${CONTENT}  // 内容\n                                        ${START}    // 异常开始时间\n                                        ${SERVER}   // 服务器地址\n                                        ${COMMAND}  // 监控项名称\n                                        ${TIME}     // 时间\n                                        ${ALARM_COUNT} // 报警次数\n                                        ${STATUS} // 状态\n可根据这些字段结合html做出漂亮的邮件内容\n                                                                    \n                                                                    \n                                                                    \n                                                                    \n                                                                    \n                                                                    \n                                                                    \n                                                                    \n                                                                    ','','','',NULL,'','',NULL,'','','');
/*!40000 ALTER TABLE `monitor_message_channel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `monitor_messages`
--

DROP TABLE IF EXISTS `monitor_messages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `monitor_messages` (
  `messages_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `member` text,
  `messages_time` timestamp NULL DEFAULT NULL COMMENT '插入数据时间',
  `server_id` int(11) DEFAULT NULL COMMENT '参考CMDB的服务器ID',
  `scripts_id` int(11) DEFAULT NULL COMMENT '参考scripts_id',
  `groups_id` int(11) DEFAULT NULL COMMENT '参考业务组的ID',
  `severtity_id` int(11) DEFAULT NULL COMMENT '参考severity_id',
  `email` text COMMENT '邮件地址',
  `mobile` text COMMENT '手机地址',
  `ding` text COMMENT '钉钉地址',
  `weixin` text COMMENT '微信地址',
  `messages` text COMMENT '报警消息',
  `alarm_count` int(11) DEFAULT NULL COMMENT '报警次数',
  PRIMARY KEY (`messages_id`),
  KEY `idx_m_mtiime` (`messages_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `monitor_messages`
--

LOCK TABLES `monitor_messages` WRITE;
/*!40000 ALTER TABLE `monitor_messages` DISABLE KEYS */;
/*!40000 ALTER TABLE `monitor_messages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `monitor_platform_server`
--

DROP TABLE IF EXISTS `monitor_platform_server`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `monitor_platform_server` (
  `platform_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ip` varchar(32) DEFAULT NULL COMMENT 'ip地址',
  `hostname` varchar(100) DEFAULT NULL COMMENT '主机名',
  `show_key` varchar(2000) DEFAULT NULL,
  `last_modify_time` varchar(32) DEFAULT NULL COMMENT '最近更新时间',
  PRIMARY KEY (`platform_id`),
  UNIQUE KEY `uidx_platform_server_ip` (`ip`)
) ENGINE=InnoDB AUTO_INCREMENT=2198 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `monitor_platform_server`
--

LOCK TABLES `monitor_platform_server` WRITE;
/*!40000 ALTER TABLE `monitor_platform_server` DISABLE KEYS */;
/*!40000 ALTER TABLE `monitor_platform_server` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `monitor_report_day`
--

DROP TABLE IF EXISTS `monitor_report_day`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `monitor_report_day` (
  `report_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `server_id` bigint(20) DEFAULT NULL,
  `operator` varchar(100) DEFAULT NULL COMMENT '故障处理人',
  `alarm_time` varchar(32) DEFAULT NULL COMMENT '报警时间',
  `scripts_id` int(11) DEFAULT NULL COMMENT '脚本ID，参考脚本ID',
  `severity_id` int(11) DEFAULT NULL COMMENT '报警级别,参考severity_id',
  `operator_time` varchar(32) DEFAULT NULL COMMENT '故障处理时间',
  `groups_id` int(11) DEFAULT NULL COMMENT '组ID',
  `index_name` varchar(132) DEFAULT NULL,
  `change_status` varchar(32) DEFAULT NULL COMMENT '修改状态',
  `start_time` varchar(32) DEFAULT NULL COMMENT '报警开始时间',
  PRIMARY KEY (`report_id`),
  KEY `report_day_index` (`alarm_time`,`scripts_id`,`groups_id`,`server_id`),
  KEY `idx_start_time` (`start_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `monitor_report_day`
--

LOCK TABLES `monitor_report_day` WRITE;
/*!40000 ALTER TABLE `monitor_report_day` DISABLE KEYS */;
/*!40000 ALTER TABLE `monitor_report_day` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `monitor_report_send`
--

DROP TABLE IF EXISTS `monitor_report_send`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `monitor_report_send` (
  `send_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `groups_id` int(11) DEFAULT NULL COMMENT '参数cmdb资源的组id',
  `emails` text COMMENT '自动发 送报表信息的邮件地址多个用逗号分隔',
  `last_modify_user` varchar(32) DEFAULT NULL COMMENT '最近修改用户',
  `last_modify_time` varchar(32) DEFAULT NULL COMMENT '最近修改时间',
  PRIMARY KEY (`send_id`),
  UNIQUE KEY `uidx_report_send` (`groups_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `monitor_report_send`
--

LOCK TABLES `monitor_report_send` WRITE;
/*!40000 ALTER TABLE `monitor_report_send` DISABLE KEYS */;
/*!40000 ALTER TABLE `monitor_report_send` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `monitor_scripts`
--

DROP TABLE IF EXISTS `monitor_scripts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `monitor_scripts` (
  `scripts_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `last_modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近修改时间',
  `last_modify_user` varchar(32) DEFAULT NULL COMMENT '最近修改用户',
  `is_valid` int(11) DEFAULT NULL COMMENT '是否有效',
  `alarm_mess` varchar(150) DEFAULT NULL COMMENT '报警消息',
  `file_name` varchar(200) DEFAULT NULL COMMENT '脚本名称',
  `content` text COMMENT '脚本内容',
  `recover_mess` varchar(150) DEFAULT NULL COMMENT '恢复信息',
  `description` varchar(150) DEFAULT NULL COMMENT '描述信息',
  `monitor_name` varchar(150) DEFAULT NULL COMMENT '监控名称',
  `arg1` text COMMENT '参数1',
  `arg2` varchar(500) DEFAULT NULL COMMENT '参数2',
  `arg3` varchar(500) DEFAULT NULL COMMENT '参数3',
  `arg4` varchar(500) DEFAULT NULL COMMENT '参数4',
  `arg5` varchar(500) DEFAULT NULL COMMENT '参数5',
  `arg6` varchar(500) DEFAULT NULL COMMENT '参数6',
  `arg7` varchar(500) DEFAULT NULL COMMENT '参数7',
  `arg8` varchar(500) DEFAULT NULL COMMENT '参数8',
  `arg8comm` varchar(100) DEFAULT NULL COMMENT '参数8描述',
  `arg7comm` varchar(100) DEFAULT NULL COMMENT '参数7描述',
  `arg6comm` varchar(100) DEFAULT NULL COMMENT '参数6描述',
  `arg5comm` varchar(100) DEFAULT NULL COMMENT '参数5描述',
  `arg4comm` varchar(100) DEFAULT NULL COMMENT '参数1描述',
  `arg3comm` varchar(100) DEFAULT NULL COMMENT '参数2描述',
  `arg2comm` varchar(100) DEFAULT NULL COMMENT '参数3描述',
  `arg1comm` varchar(100) DEFAULT NULL COMMENT '参数4描述',
  `anew` int(11) DEFAULT NULL COMMENT '多长时间重新发送',
  `isrecover` varchar(10) DEFAULT NULL COMMENT '是否发送恢复',
  PRIMARY KEY (`scripts_id`),
  UNIQUE KEY `uidx_file_name_monitor_name` (`monitor_name`,`file_name`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `monitor_scripts`
--

LOCK TABLES `monitor_scripts` WRITE;
/*!40000 ALTER TABLE `monitor_scripts` DISABLE KEYS */;
INSERT INTO `monitor_scripts` VALUES (1,'2017-01-17 12:54:57','zhaozq14',1,NULL,'os_log.py','#!/usr/bin/python\n#--coding:utf-8\n#参数1要监控的报错信息，多个以逗号分隔\n#参数2，要监控的文件,需要有写的权限\nimport time\nimport sys\nimport os\nimport json\nstatus = 0\n\ntf = time.strftime(\'%Y-%m-%d\',time.localtime(time.time()))\n\nresult = {}\n\nresult[\"groups\"] = \"logMonitor\"\nresult[\"name\"] = \"error\"\nresult[\"command\"] = sys.argv[0]\n\nkeyword = sys.argv[1]\nfile_path = sys.argv[2]\nfile_path = file_path.replace(\" \",\"\")\ncmd  = \'cat {1} | egrep -A2 -B2    \"{0}\"  |grep -v Binary | grep -v INFO |grep -v ^$ |egrep \"[0-9|a-z|A-Z]\" ; cat {1} >> {1}.bak.{2} && > {1} \'.format(\"|\".join(keyword.split(\",\")), file_path, tf)\ndata = os.popen(cmd).read()\nif not data :\n    result[\"value\"] = 1\n    result[\"status\"] = 1\n    result[\"messages\"] = \'no error\'\nelse:\n    result[\"value\"] = 0\n    result[\"status\"] = 2\n    result[\"messages\"] = \'{0}\'.format(data.replace(\"\'\", \"\"))\n\nprint json.dumps([result])                                                                 ',NULL,'监控日志报错','监控日志报错','Error,error','/home/asura/runtime/tomcat_8081/logs/catalina.out','','','','','','','','','','','','','监控的日志文件','报错关键字，多个用逗号分隔',NULL,NULL),(4,'2016-09-15 10:18:42','',1,NULL,'rabbitmq_monitor.py','#!/usr/bin/python\n#--coding:utf-8\n#\n\nimport json\nimport sys\nimport os\n\narg1 = sys.argv[1]\narg2 = sys.argv[2]\narg3 = sys.argv[3]\narg4 = sys.argv[4]\n\n\nresult = {}\n\nresult[\"groups\"] = \"rabbitmq\"\nresult[\"name\"] = \"messages_ready\"\nresult[\"command\"] = sys.argv[0]\n\ntry:\n    cmd  = \'curl http://{0}:{1}/api/overview --user \"{2}:{2}\" 2>/dev/null\'.format(arg1, arg2, arg3, arg4)\n    data = os.popen(cmd).read()\n    data = json.loads(data.replace(\"\'\",\'\"\').replace(\'u\"\', \'\"\'))\n\n    read = data.get(\"queue_totals\").get(\"messages_ready\")\n    if int(read) > 0:\n        result[\"value\"] = 1\n        result[\"status\"] = 2\n        result[\"messages\"] = \'messages_ready {0}\'.format(read)\n    else:\n        result[\"value\"] = 0\n        result[\"status\"] = 0\n        result[\"messages\"] = \'no error info {0}\'.format(read)\nexcept Exception, e:\n    result[\"value\"] = 0\n    result[\"status\"] = 2\n    result[\"messages\"] = str(e).replace(\"\'\",\"\") \n\n\nprint json.dumps(result)                                                                                                                                ',NULL,'监控rabbitmq消息队列为读取的数量','rabbitMq监控','10.10.25.1','15672','sms_rab','sms_rab','','','','','','','','','rabbitmq的密码','rabbitmq的用户名','rabbitmq的端口','监控的主机,ip地址',NULL,NULL),(5,'2017-01-09 06:04:52','zhaozq14',1,NULL,'memory.py','#!/usr/bin/python\n#--coding:utf-8\n#\n\nimport json\nimport sys\nimport os\nimport re\n\ntry:\n    arg1 = sys.argv[1]\n    arg2 = 100 \nexcept Exception:\n    arg1 = 100\n    arg2 = 100\n\n\ndef getData(param, data):\n   data = re.findall( r\"{0}.*\".format(param), data)[0].replace(\"  \",\" \")\n   data = data.replace(\"\\t\",\" \").split(\" \")\n   return int(data[len(data)-2])\n\ndef getResult(name, value, status, messages):\n    result = {}\n    result[\"groups\"] = \"memory\"\n    result[\"name\"] = name \n    result[\"command\"] = sys.argv[0]\n    result[\"status\"] = status \n    result[\"value\"] = value \n    result[\"messages\"] = str(messages) + str(arg1) + \" \"  + str(arg2)\n    list.append(result)\n   \n\nfile = \"/proc/meminfo\"\n\nlist = []\n\ndata = open(file, \"r\").read()\ntotle = getData(\"MemTotal:\", data)\nfree = getData(\"MemFree:\", data)\ncache = getData(\"Cached:\", data)\nbuffer = getData(\"Buffers:\", data)\nused = totle - free \n\n\nusedPercent = round(float(used) / int(totle) * 100, 2)\ntry:\n    if usedPercent > int(arg1):\n        status = 3\n    elif usedPercent > int(arg2):\n        status = 3\n    else:\n        status = 1\nexcept Exception:\n    status = 1\n   \nmessages = \"totle: {0}, used:{1}, free:{2}, used: {3}%\".format(totle, used, free, usedPercent).replace(\"\\n\", \"\")\n\nswapTotle = getData(\"SwapTotal:\", data)\nswapFree = getData(\"SwapFree:\", data)\nswapUsed = swapTotle - swapFree\n\ngetResult(\"system.mem.used.percent\", round(usedPercent, 2), status, messages)\ngetResult(\"system.mem.totle\", totle, 0, \"\")\ngetResult(\"system.mem.free\", free/1000, 0,free )\ngetResult(\"system.mem.used\", used/1000, 0,used)\ngetResult(\"system.mem.cache\", cache/1000, 0, cache) \ngetResult(\"system.mem.buffer\", buffer/1000, 0, buffer) \ngetResult(\"system.swap.used\", swapUsed, 0, swapUsed) \ngetResult(\"system.swap.free\", swapFree, 0, swapFree) \n\nprint json.dumps(list)                                                                ',NULL,'监控内存','监控内存使用','99','','','','','','','','','','','','','','','大于多少位警告',NULL,NULL),(7,'2016-11-15 00:29:45','',1,NULL,'cpu.py','#!/usr/bin/python\n#--coding:utf-8\n\nimport time\nimport sys\nimport os\nimport json\nstatus = 0\n\nlist = []\n\n\n#%user %nice %system %iowait %steal %idle\ndata = os.popen(\"sar 1 2 |tail -n 2 |grep -v Aver |sed \'s/AM//g;s/PM//g\' | awk \'{print $3,$4,$5,$6,$7,$8}\'\").read()\nif not data:\n  data = os.popen(\"top -d 1 -n 2 -b   |grep \'Cpu(s)\' |sed \'s/%..//g;s/,//g\'|awk \'{print $2,$4,$3,$6,$9,$5}\' |tail -n 1\").read()\ndata = data.replace(\"\\n\", \"\")\ndata = data.split(\" \")\n\ndef getData(name, dataKey):\n    result = {}\n    result[\"groups\"] = \"cpu\"\n    result[\"name\"] = name \n    result[\"command\"] = sys.argv[0]\n    result[\"value\"] = data[dataKey]\n    result[\"status\"] = \"1\"\n    result[\"messages\"] = name + \" \" + data[dataKey]\n    list.append(result)\n\n\ngetData(\"system.cpu.user\", 0)\ngetData(\"system.cpu.nice\", 1)\ngetData(\"system.cpu.system\", 2)\ngetData(\"system.cpu.iowait\", 3)\ngetData(\"system.cpu.steal\", 4)\ngetData(\"system.cpu.idle\", 5)\n\nprint json.dumps(list)',NULL,'监控cpu','监控cpu','','','','','','','','','','','','','','','','',NULL,NULL),(8,'2017-01-22 09:55:28','zhaozq14',1,NULL,'os_redis.py','#!/usr/bin/python\n#--coding:utf-8\n# 监控redis是否可以连接和获取到对应的数据\n\n\nimport telnetlib\nimport sys\nimport json\nimport socket\nimport re\nparam = sys.argv[1].split(\":\")\nHost = param[0] \nport = int(param[1])\ncommand = sys.argv[2]\n\nfind_data = sys.argv[3] \n\n\nresult = {}\n\nresult[\"groups\"] = \"redis\"\nresult[\"name\"] = \"set\"\nresult[\"command\"] = sys.argv[0]\n\nkeyword = sys.argv[1]\n\ndef redis(host, port, command):\n    try:\n        s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)\n        s.settimeout(12)\n        s.connect((host, port))\n        s.send(\"{0}\\r\\n\".format(command))\n        result = \"\"\n        data = s.recv(40960000)\n        datas = data.split(\"\\r\\n\")\n        for d in datas:\n           if re.findall(r\"^\\$.*\",d):\n               continue\n           result += d\n       \n        return [result, result]\n    except Exception,e:\n         print e\n         return [\"\", \"\"]\n\ntry:\n    tn = telnetlib.Telnet(Host, port=port, timeout=10)\n    data = redis(Host, port, command)[0]\n    if not data :\n        result[\"value\"] = 0\n        result[\"status\"] = 2\n        result[\"messages\"] = \"{0} get data faild {1}\".format(Host,  data)\n\n    if find_data in data:\n        result[\"value\"] = 1\n        result[\"status\"] = 1\n        result[\"messages\"] = data \n    else:\n        result[\"value\"] = 0\n        result[\"status\"] = 2\n        result[\"messages\"] = \"{0} Error data matching {1}\".format(Host,  data)\n\nexcept Exception, e:\n    result[\"value\"] = 0\n    result[\"status\"] = 2\n    result[\"messages\"] = \"{0} Error data matching {1}\".format(Host,  e)\n\nprint json.dumps([result])                                                                                               ',NULL,'监控redis服务器','监控redis状态','127.0.0.1:6379','set a a','OK','','','','','','','','','','','期望返回的结果','执行要测试的命令','redis服务器:端口',NULL,NULL),(9,'2016-11-25 03:11:57','zhaozq14',1,NULL,'os_soacheck.py','#!/usr/bin/python\n#--coding:utf-8\n# 监控soa提供者\nimport json\nimport sys\nimport telnetlib\n\n\nlocal = 1\nHost = [sys.argv[len(sys.argv)-1]]\nport = sys.argv[1]\ntry:\n    if len(sys.argv) > 2:\n         local = 0\n         Host = sys.argv[2].split(\",\")\nexcept Exception:\n    pass\n\nlist = []\nfor ip in Host:\n    if not ip : continue\n    result = {}\n    result[\"groups\"] = \"soa\"\n    result[\"name\"] = \"dubbo.status\"\n    result[\"command\"] = sys.argv[0]\n\n    status = 1\n    result[\"ip\"] = ip\n    try:\n        tn = telnetlib.Telnet(ip, port=int(port), timeout=6)\n        tn.write(\'status\\r\\n\')\n        data = tn.read_until(\"dubbo>\")\n        if \'OK\' in data:\n             status = 1\n             result[\"messages\"] = ip +\" \" + data\n             result[\"value\"] = 1\n        else:\n             status = 2\n             result[\"value\"] = 0\n             result[\"messages\"] = ip +\" \" + data\n    except Exception, e:\n        status = 2\n        result[\"value\"] = 0\n        result[\"messages\"] = str(e)\n\n    result[\"status\"] = status\n    list.append(result)\n\nprint json.dumps(list)                                                                ',NULL,'监控SOA服务','SOA提供者监控','20880','127.0.0.1','','','','','','','','','','','','','要监控的服务器地址,多个逗号分隔','dobbo服务端口',NULL,NULL),(10,'2016-11-11 07:31:43','',1,NULL,'os_ping.py','#!/usr/bin/python\n#--coding:utf-8\n# 执行ping监控 \nimport json\nimport sys\nimport os\nimport time\nimport thread\n\n\nlist = []\nHosts = sys.argv[1]\n\nhosts = set(Hosts.split(\",\"))\nhost_length = len(hosts)\ncount = [] \n\ndef ping(Host,pid):\n    start = int(time.time())\n    result = {}\n    result[\"groups\"] = \"ping\"\n    result[\"name\"] = \"ping\"\n    result[\"command\"] = sys.argv[0]\n    status = 1\n\n    command = \'ping %s -c 2 -i 0.2 -w 2 |tail -n2 |paste -s |grep -v ^$ \'%(Host)\n    data = os.popen(command).read()\n    if \"100% packet loss\" in data:\n         status = 2 \n    result[\"messages\"] = data\n    end = int(time.time())\n    result[\"value\"] = end - start\n    result[\"status\"] = status\n    result[\"ip\"] = Host \n    list.append(result)\n    count.append(1)\n\nfor Host in hosts:\n    if not Host:\n        count.append(1)\n        continue\n\n    thread.start_new_thread(ping, (Host, 0))\n\nwhile True:\n   if len(count) >= host_length:\n       break\n   time.sleep(0.3)\nprint json.dumps(list)                                                                ',NULL,'监控ping','ping监控','10.16.35.193,10.16.35.197','','','','','','','','','','','','','','','要监控的IP地址,多个用逗号分隔',NULL,NULL),(11,'2016-11-19 10:21:52','',1,NULL,'os_url.py','#!/usr/bin/python\n#--coding:utf-8\n#网站URL地址监控，主要是访问不正常的URL，比如500,502错误\nimport sys\nimport json\nimport urllib2\n\nimport time\ndef getPort(url):\n   try:\n       u = url.split(\":\")[2]\n   except Exception:\n       u = 80\n   return str(u)\n\nhttp = \"http\"\nstatus = 0\ncode = \'\'\nok = [200, 301, 302]\n\n\nurl = sys.argv[1] \ndesired_value = sys.argv[2] \ntime_out = sys.argv[3]\nhost = sys.argv[len(sys.argv)-1]\n\n\nresult = {}\nlist = []\nresult[\"groups\"] = \"url\"\nresult[\"name\"] = \"access\"\nresult[\"command\"] = sys.argv[0]\nstart = int(time.time())\nauthinfo = urllib2.HTTPBasicAuthHandler()\nproxy_support = urllib2.ProxyHandler({http : \"http://%s:%s\"%(host,getPort(url))})\nopener = urllib2.build_opener(proxy_support)\nurllib2.install_opener(opener)\nst = 0\ntry:\n    time_out = int(time_out)\nexcept Exception:\n    time_out = 5\ntry:\n    if http in url and \'https://\' not in url:\n        f = urllib2.urlopen(url, timeout=time_out)\n    else:\n        f = urllib2.urlopen(\'http://%s\'%(url), timeout=time_out)\n    data = f.read()\n    code = f.getcode()\n    for i in ok:\n         if int(code) == i :\n                 st = 1\n    if desired_value in data:\n        st = 1\n               \n    else:\n        st = 3\n\n    if st == 0:\n          status = 2\n          messages = \'{0} error {1}\'.format(url, str(code))\n    if st == 3:\n          status = 2\n          messages =  \'{0} {1} no desired data ,\'.format(host, url)\n    if st == 1:\n          status = 1\n          messages = \'{0} ok  {1}\'.format(url, str(code))\nexcept Exception,E:\n    status = 2\n    messages =  \'{0} error {1}\'.format(str(E), url)\n\nend = int(time.time())\nresult[\"messages\"] = messages\nresult[\"value\"] = end - start \nresult[\"status\"] = status\nlist.append(result) \nprint json.dumps(list)                                                                ',NULL,'监控url访问','监控服务器URL地址','http://www.xx.com/index.php','ok','5','','','','','','','','','','','访问超时时间','返回包含的数据','监控服务器URL地址',NULL,NULL),(12,'2016-11-03 06:06:38','',1,NULL,'os_loadavg.py','#!/usr/bin/python\n#--coding:utf-8\n\'\'\'\n    系统负载监控，loadavg\n    参数3，  （1,5,15）超过多少报警 \n    参数4，  （1,5,15）超过多少危险 \n\'\'\'\n\nimport json\nimport sys\nimport os\n\n\nlist = []\nresult = {}\n\n\nstat = {}\nstatus = 0\nmessages = [] \ndef countLoadavg(load1, load2, v):\n    status = \"\"\n    for i in range(0, 3):\n        if float(load2[i]) > float(load1[i]):\n            status = v\n            stat[\"status{0}\".format(i)] = v \n            messages.append(\'err:%s > %s [%s]\'%(load2[i], load1[i], str(load2)))\n        else:\n            status = 1\n            stat[\"status{0}\".format(i)] = 1\n            load3 = str(load2) \n            messages.append(\'ok:%s < %s %s,\'%(load2[i],load1[i],load3.replace(\",\",\"|\")))\n    return status\n\ntry:\n    loadw = sys.argv[1].split(\",\")\n    loadc = sys.argv[2].split(\",\")\nexcept Exception:\n    loadw = 100,100,100\n    loadc = 100,100,100\n\n\nload = open(\"/proc/loadavg\", \"r\").read().split(\" \")[0:3] \nstatus = countLoadavg(loadc, load, 2)\nif not status or status == 1 :\n    status = countLoadavg(loadw, load, 3)\n\n\nresult = {}\nresult[\"groups\"] = \"loadavg\"\nresult[\"command\"] = sys.argv[0]\nresult[\"status\"] = stat[\"status0\"]\nresult[\"value\"] = load[0] \nresult[\"name\"] = \"system.load.1\"\nresult[\"messages\"] = \"min1 {0} \".format(load[0])+ str(messages[0])\nlist.append(result)\n\n\nresult = {}\nresult[\"groups\"] = \"loadavg\"\nresult[\"command\"] = sys.argv[0]\nresult[\"status\"] = 1 \nresult[\"value\"] = load[1]\nresult[\"name\"] = \"system.load.5\"\nresult[\"messages\"] = \"min5 {0} \".format(load[1]) + str(messages[1])\nlist.append(result)\n\n\nresult = {}\nresult[\"groups\"] = \"loadavg\"\nresult[\"status\"] = 1 \nresult[\"command\"] = sys.argv[0]\nresult[\"value\"] = load[2]\nresult[\"name\"] = \"system.load.15\"\nresult[\"messages\"] = \"min15 {0} \".format(load[2]) + str(messages[2])\nlist.append(result)\n\nprint json.dumps(list)',NULL,'监控系统负载','服务器平均负载','50,20,15','25,10,8','','','','','','','','','','','','','1,5,15分钟负载,大于该项警告','1,5,15分钟负载,大于该项危险',NULL,NULL),(13,'2016-09-20 00:47:09','',1,NULL,'os_zk_stat.py','#!/usr/bin/python\n#--coding:utf-8\n# 监控zookeeper\n\nimport json\nimport sys\nimport os\nimport telnetlib\nimport re\n\nresult = {}\nresult[\"groups\"] = \"zookeeper\"\nresult[\"name\"] = \"status\"\nresult[\"command\"] = sys.argv[0]\n\nstatus = 1\narg1 = int(sys.argv[1])\nhost = sys.argv[len(sys.argv)-1] \ntry:\n    tn = telnetlib.Telnet(host, arg1)\n    tn.write(\'ruok\\n\')\n    messages = tn.read_until(\"*\")\n    tn.close()\nexcept Exception,E:\n    messages  = str(E)\n\nif \"imok\" in messages:\n    status = 1\n    value = 1\n    messages = \'ok %s\'%(messages)\nelse:\n    status = 2\n    value = 0\n    messages = \'error %s\'%(messages)\n\nresult[\"value\"] = value\nresult[\"messages\"] = messages\nprint json.dumps(result) ',NULL,'监控zookeeper系统,获取stat判断','zk状态监控','2181','','','','','','','','','','','','','','','zookeeper端口',NULL,NULL),(14,'2016-09-20 01:06:04','',1,NULL,'os_checkes.py','#!/usr/bin/python\n#--coding:utf-8\n# 监控es搜索的状态和集群节点数\nimport sys\nimport json\nimport urllib2\n\n\n# es集群的url地址\narg1 = sys.argv[1]\n# 集群节点数\narg2 = sys.argv[2]\n\nhttp = \"http\"\nstatus = 1\nfalse = False\ntrue = True\n\nresult = {}\n\nresult[\"groups\"] = \"elasticsearch\"\nresult[\"name\"] = \"cluster_status\"\nresult[\"command\"] = sys.argv[0]\nmessages = []\n\n\nfor url in arg1.split(\",\"):\n    if not url : continue\n    try:\n        f = urllib2.urlopen(url)\n        data = f.read()\n        data = json.loads(data) \n        if  data.get(\'status\') == \"green\":\n            number_of_nodes = data.get(\"number_of_nodes\")\n            if str(number_of_nodes) == str(arg2):\n                value = 1\n                messages.append(\'%s status ok  %s, node %s\'%(url, str(data.get(\"status\")), number_of_nodes ))\n            else:\n                status = 2\n                value = 0\n                messages.append(\'%s number_of_nodes exception %s,\'%(url, number_of_nodes))\n        else:\n            messages.append(\'%s error  %s,\'%(url, result.get(\"status\")))\n            value = 0\n    except Exception, E:\n        status = 2\n        value = 0\n        messages.append( \'%s error %s ,\'%(str(E), url))\n\nresult[\"messages\"] = messages\nresult[\"value\"] = value\nresult[\"status\"] = status \nprint json.dumps(result)',NULL,'通过es自己的工具，判断ES状态是否正常。','ES搜索集群状态监控','http://192.168.0.39:9200/_cluster/health?prettytrue','3','','','','','','','','','','','','','集群节点数，如果节点数不等于这个就报错','监控状态的URL，通过status属性是否为green',NULL,NULL),(15,'2016-09-28 01:20:11','',1,NULL,'os_dns_check.py','#!/usr/bin/python\n#--coding:utf-8\n#\n\nimport json\nimport sys\nimport os\nimport commands\nimport re\n\n\'\'\'\n    dns 解析监控\n    参数1写域名，多个用逗号分隔\n    参数2写IP，多个用逗号分开，和参数一的结果对应\n\'\'\'\nr = \'\'\n\n\narg1 = sys.argv[1]\narg2 = sys.argv[2]\ndname = arg1.split(\',\')\nipadd = arg2.split(\',\')\nlens  = len(dname)\nstatus = 0\nmessages = []\n\nhost = sys.argv[len(sys.argv)-1] \n\nresult = {}\nresult[\"groups\"] = \"dns\"\nresult[\"name\"] = \"lookup\"\nresult[\"command\"] = sys.argv[0]\n\nfor i in range(0, lens):\n    command = \'dig @%s %s\'%(host, dname[i])\n    data = commands.getstatusoutput(command)\n    data = data[1]\n    if ipadd[i] in data:\n        status = 1\n        value = re.findall(r\" Query time:.*\", data)[0]\n        value = value.split(\" \")[3]\n        messages.append(\'ok {0}, \'.format(data[1]))\n    else:\n        status = 2\n        try:\n            value = re.findall(r\" Query time:.*\", data)[0]\n            value = value.split(\" \")[3]\n        except Exception:\n            value = 100\n        e = \'error:{0} no record {1}, \'.format(dname[i], ipadd[i])\n        messages.append(e)\n\nresult[\"value\"] = value \nresult[\"status\"] = status \nresult[\"messages\"] = messages \n\nprint json.dumps(result)                                ',NULL,'dns解析监控参数1写域名，多个用逗号分隔参数2写IP，多个用逗号分开，和参数一的结果对应','DNS解析监控','www.os1.com,api.os2.com','10.102.10.12,10.102.10.12','','','','','','','','','','','','','要监控解析的域名解析结果，多个用,分隔，和参数1的域名解析结果保持对应','要监控解析的域名，多个用,分隔',NULL,NULL),(16,'2016-11-11 06:57:27','',1,NULL,'os_disk.py','#!/usr/bin/python\n#--coding:utf-8\n#\n\nimport json\nimport sys\nimport os\nimport re\n\n\nlist = []\n\n\ndef getData(info, crit, warn, value=\"\", name=\"\"):\n    try:\n            info = info.replace(\"\\n\", \"\")\n            data = info.split(\" \")\n            result = {}\n            result[\"groups\"] = \"disk\"\n            result[\"name\"] = name +\".\"+ data[0].replace(\"/\", \"SLASH\")\n            result[\"command\"] = sys.argv[0]\n            percent = data[4].replace(\"\\n\", \"\").replace(\"%\", \"\")\n            percent = int(percent)\n            status = 1\n            messages = info \n            if percent > warn:\n               status = 3\n               messages = \"{0} 警告 {1}% > {2}% used {3} totle:{4} free: {5}\".format( data[0], percent, warn, data[4], data[2], data[3])\n               if percent > crit: \n                    messages = \"{0} 危险 {1}% > {2}% used {3} totle:{4} free: {5}\".format( data[0], percent, crit , data[4], data[2], data[3])\n                    status = 2 \n\n            result[\"status\"] = status\n            result[\"value\"] = data[value].replace(\"%\", \"\")\n            result[\"messages\"] = str(messages)\n            list.append(result)\n    except Exception:\n         pass\n\n\n\ntry:\n    crit = int(sys.argv[1])\n    warn = int(sys.argv[2])\nexcept Exception:\n    crit = 90\n    warn = 80\n\ncmd = \"df -l |sed \'1d;/ /!N;s/\\\\n//;s/ \\+/ /;\'| awk \'$0 ~ /dev/ && $0 !~ /shm/ {print $NF,$2,$3,$4,$5}\'\"\nfor info in os.popen(cmd).readlines():\n   getData(info, crit, warn, 4, \"system.disk.used.percent\")\n\ncmd = \"df -l |sed \'1d;/ /!N;s/\\\\n//;s/ \\+/ /;\'| awk \'$0 ~ /dev/ && $0 !~ /shm/ {print $NF,$2,$3,$4,$5}\'\"\nfor info in os.popen(cmd).readlines():\n   getData(info, 100, 100, 1, \"system.disk.totle\")\n   getData(info, 100, 100, 2, \"system.disk.in_use\")\n   getData(info, 100, 100, 3, \"system.disk.free\")\n\ncmd = \"df -li |sed \'1d;/ /!N;s/\\\\n//;s/ \\+/ /;\' | awk \'$0 ~ /dev/ && $0 !~ /shm/ {print $NF,$2,$3,$4,$5}\'\"\nfor info in os.popen(cmd).readlines():\n       getData(info, crit, warn, 4, \"system.fs.inodes.use.percent\")\n       getData(info, 100, 100, 1, \"system.fs.inodes.totle\")\n       getData(info, 100, 100, 2, \"system.fs.inodes.in_use\")\n       getData(info, 100, 100, 3, \"system.fs.inodes.free\")\n\nprint json.dumps(list)',NULL,'监控硬盘使用率','硬盘使用率监控','90','80','','','','','','','','','','','','','大于多少警告','大于多少危险',NULL,NULL),(17,'2016-11-09 03:45:40','',1,NULL,'os_traffic.py','#!/usr/bin/python\n# --coding:utf-8\n# 2016-10-09\n# 获取网卡流量\nimport os\nimport json\nimport sys\nimport thread\nimport time\nlist = []\n\n\n\ncmd = \"ifconfig |grep \'HWaddr \' |awk \'{print $1}\'\"\n\n\ndef getData(name, status, data_v=\"\"):\n    if data_v:\n        byte=os.popen(\"\"\"ifconfig %s|grep \"%s\"|awk -F\':\' \'{print $3}\'|cut -d\' \' -f 1\"\"\"%(name, status)).read()\n    else:\n        byte=os.popen(\"\"\"ifconfig %s|grep \"%s\"|awk -F\':\' \'{print $2}\'|cut -d\' \' -f 1\"\"\"%(name, status)).read()\n    return byte\n\n\ndef getResult(name, type, status, data_v= \"\"):\n    name = name.replace(\"\\n\",  \"\")\n    byte1 = getData(name, status, data_v)\n    time.sleep(1)\n    byte2 = getData(name, status,data_v)\n    value = int(byte2) - int(byte1)\n    result = {}\n    result[\"groups\"] = \"traffic\"\n    result[\"name\"] = type +\".\"+ name\n    result[\"command\"] = sys.argv[0]\n    result[\"status\"] = 0 \n    result[\"value\"] = value\n    result[\"messages\"] = name +\" \" + type + \" \" + str(value)\n    list.append(result)\n   \nfor cart in os.popen(cmd).readlines():\n    if \"em\" not in cart and \"eth\" not in cart: continue\n    thread.start_new_thread(getResult, (cart, \"system.net.bytes.rcvd\", \"RX bytes\"))\n    thread.start_new_thread(getResult,  (cart, \"system.net.bytes.send\", \"TX bytes\",3))\n    thread.start_new_thread(getResult,  (cart, \"system.net.packets_in.count\", \"RX packets\"))\n    thread.start_new_thread(getResult,  (cart, \"system.net.packets_in.error\", \"RX packets:.*errors:\", 3))\n    thread.start_new_thread(getResult,  (cart, \"system.net.packets_out.error\", \"TX packets.*errors:\", 3))\n    thread.start_new_thread(getResult,  (cart, \"system.net.packets_out.count\", \"TX packets\"))\n\n\ntime.sleep(1.5)\nprint json.dumps(list)                                ',NULL,'监控服务器流量','监控服务器流量','','','','','','','','','','','','','','','','',NULL,NULL),(18,'2016-11-03 06:55:16','',1,NULL,'os_io.py','#!/usr/bin/python\n# --coding:utf-8\n# 2016-10-09\n# 获取磁盘IO性能\nimport os\nimport json\nimport sys\nimport thread\nimport time\nlist = []\n\n\n\ncmd = \"iostat -x -k  1 3 |awk \'{print $1,$4,$5,$6,$7,$10,$12}\'  |grep -v avg-cpu|tail -n 4|egrep \'[0-9]\'|egrep \'^sd|^vd\' \"\n\ndef getResult(name, type, length):\n    name = name.replace(\"\\n\",  \"\")\n    data = name.split(\" \")\n    result = {}\n    result[\"groups\"] = \"IO\"\n    result[\"name\"] = type + \".\" + data[0]\n    result[\"command\"] = sys.argv[0]\n    result[\"status\"] = 0 \n    value = data[length]\n    result[\"value\"] = data[length] \n    result[\"messages\"] = name +\" \" + type + \" \" + str(value)\n    list.append(result)\ndata =  os.popen(cmd).readlines()   \nfor name in data:\n    thread.start_new_thread(getResult, (name, \"system.io.r_s\", 1))\n    thread.start_new_thread(getResult, (name, \"system.io.w_s\", 2))\n    thread.start_new_thread(getResult, (name, \"system.io.Blk_read\", 3))\n    thread.start_new_thread(getResult, (name, \"system.io.Blk_wrtn\", 4))\n    thread.start_new_thread(getResult, (name, \"system.io.await\", 5))\n    thread.start_new_thread(getResult, (name, \"system.io.util\", 6))\n\ntime.sleep(0.5)\nprint json.dumps(list)',NULL,'监控磁盘IO状态','监控磁盘IO性能','','','','','','','','','','','','','','','','',NULL,NULL),(19,'2016-10-12 06:54:18','',1,NULL,'os_port.py','#!/usr/bin/python\n#--coding:utf-8\n\nimport socket\nimport json\nimport sys\n\nlist = []\narg1 = sys.argv[1]\nhost = sys.argv[2]\n\nmessages = \"\" \nstatus = 1\nfor port in arg1.split(\",\"):\n    if not port : continue\n    result = {}\n    result[\"groups\"] = \"port\"\n    result[\"name\"] = str(port)\n    try:\n        sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)\n        conn = sock.connect((host, int(port)))\n        if conn == None:\n            value = 1\n            messages += \'%s ok,\'%(str(port))\n        else:\n            value = 0\n            status = 2\n            messages += \'%s err,\'%(str(port))\n\n    except Exception,E:\n        value = 0\n        status = 2\n        messages += \'%s %s,\'%(str(port), str(E))\n\n    result[\"status\"] = status \n    result[\"value\"] = value \n    result[\"messages\"] = messages\n    result[\"command\"] = sys.argv[0]\n    list.append(result)\n\nprint json.dumps(list)',NULL,'监控服务器端口连通性','监控端口存活','22','','','','','','','','','','','','','','','要监控的端口号,多个用逗号分隔22,80,8081',NULL,NULL),(20,'2016-11-10 13:42:40','',1,NULL,'os_mysql.py','#!/usr/bin/python\n# --coding:utf-8\n# 20161019\nimport os\nimport re\nimport sys\nimport json\nimport time\n\nlist = []\n\ncmd = \"/usr/local/mysql/bin/mysqladmin extended-status\"\ndata = os.popen(cmd).readlines()\ntime.sleep(1)\ndata2 = os.popen(cmd).readlines()\n\ndef getResult(data,name):\n    d1 = []\n    for i in data:\n        if not i: continue\n        names = i.split(\"|\")\n        if len(names)> 1:\n            names = names[1].replace(\" \",\"\")\n        if names == name:\n           result = re.findall(r\"{0}.*\".format(name), i)\n           d1 = result[0].split(\"|\")\n    return d1\n\n\n# 去某个从参数的差值\ndef outData(name):\n    d1 = getResult(data, name) \n    d2 = getResult(data2, name) \n    if not d1 or not d2: return\n    return int(d2[1]) - int(d1[1])\n    \n    \n\n\ndef getData(name, isSleep = \"\", isDataCount=[], count_name=\"\"):\n    data_count_list = []\n    if not isDataCount:\n        data_count_list.append(name)\n    else:\n        data_count_list = isDataCount\n    result1 = 0\n    for name in data_count_list:\n        d1 = getResult(data, name) \n        d2 = getResult(data2, name) \n        if not d1 or not d2: return\n        result = {}\n        result[\"groups\"] = \"Mysql\"\n        result[\"name\"] = d1[0].replace(\" \", \"\")\n        result[\"command\"] = sys.argv[0]\n        result[\"status\"] = 1 \n\n        if count_name:\n            result1 += int(d2[1]) - int(d1[1])\n\n    if not isSleep:\n         result[\"value\"] = int(d2[1]) - int(d1[1])\n    else:\n         result[\"value\"] = int(d1[1])\n\n    if count_name:\n         result[\"name\"] = count_name\n         result[\"value\"] = int(result1)\n    list.append(result)\n\n\ndata_count_list = [\n  \"Com_select\",\n  \"Com_delete\",\n  \"Com_insert\",\n  \"Com_update\"\n]\n  \n\n# 取差值的\ndata_list = [\n\"Questions\",\n\"Com_select\",\n\"Com_delete\",\n\"Com_insert\",\n\"Com_update\",\n\"Innodb_row_lock_waits\",\n\"Slow_queries\",\n\"Qcache_hits\",\n]\n\n# 不取差值的\ndata_no_sleep_list = [\n \"Threads_connected\",\n]\n\nfor i in data_list:\n    getData(i,\"\")\n\nfor i in data_no_sleep_list:\n    getData(i, 1)\n\n\n\n# 获取命中率\nresult = {}\nresult[\"groups\"] = \"Mysql\"\nresult[\"name\"] = \"Hit\" \nresult[\"command\"] = sys.argv[0]\nresult[\"status\"] = 1 \nresult[\"value\"] = (outData(\"Innodb_buffer_pool_read_requests\") - outData(\"Innodb_buffer_pool_reads\"))*1.0/ (outData(\"Innodb_buffer_pool_read_requests\")+0.01) * 100.0\nlist.append(result)\n\ngetData(\"\",\"\",data_count_list,\"tps\")\nprint json.dumps(list)                                ',NULL,'mysql指标数据','监控Mysql性能指标','','','','','','','','','','','','','','','','',NULL,NULL),(21,'2016-11-09 07:23:23','',1,NULL,'nginx_phpfpm.py','#!/usr/bin/python\n# --coding:utf-8\n# 20161025\n\n\nimport os\nimport urllib2\nimport sys\nimport json\nimport time\n\ncmd = \"ps aux |egrep \'php|nginx\' -q ; echo $?\"\nresult = os.popen(cmd).read().replace(\"\\n\", \"\")\nif result == \"1\":\n   print \"[]\"\n   sys.exit(0)\n\n\n\'\'\'\n#This function used to\n#access web server data\n\'\'\'\ndef curl(url):\n   try:\n       data = urllib2.urlopen(url).read()\n       return data\n   except Exception:\n       pass\nlist = []\n\ndef getData(name, value, groups,ip=\"\"):\n    result ={}\n    result[\"name\"] = u\"%s\"%(name)\n    result[\"value\"] = value \n    result[\"status\"] = 1\n    result[\"groups\"] = groups \n    result[\"command\"] = sys.argv[0] \n    if ip and ip != \"127.0.0.1\" :\n       result[\"ip\"] = ip \n    list.append(result)\n\ndef getNginxRequest(ip):\n    url = \"http://%s/status\"%(ip)\n    data1 = curl(url)\n    time.sleep(1)\n    data2 = curl(url)\n    if not data1 or not data2: return \n    data1 = data1.split(\"\\n\")[2].split(\" \")\n    data2 = data2.split(\"\\n\")[2].split(\" \")\n    request_per_s = int(data2[3]) - int(data1[3])\n    conn_opened_per_s = int(data2[1]) - int(data1[1])\n    conn_dropped_per_s = int(data2[2]) - int(data2[1])\n    getData(\"nginx.net.request_per_s\", request_per_s, \"Nginx\")\n    getData(\"nginx.net.conn_opened_per_s\", conn_opened_per_s, \"Nginx\")\n    getData(\"nginx.net.conn_dropped_per_s\", conn_dropped_per_s, \"Nginx\")\n    \n\nactive_ = []\nreading_ = []\nwriting_ = []\nwaiting_ = []\n\nif len(sys.argv) > 2:\n   server_list = sys.argv[1].split(\",\") \nelse:\n   server_list = [\"127.0.0.1\"]\n\nfor ip in server_list:\n    \n    getNginxRequest(ip)\n    url = \"http://%s/status\"%(ip)\n    data = curl(url)\n    if not data: continue \n    data = data.split(\"\\n\")\n    active_.append(int(data[0].split(\" \")[2]))\n    reading_.append(int(data[3].split(\" \")[1]))\n    writing_.append(int(data[3].split(\" \")[3]))\n    waiting_.append(int(data[3].split(\" \")[5]))\n    getData(\"nginx.net.connections\", int(data[0].split(\" \")[2]), \"Nginx\", ip)\n    getData(\"nginx.net.reading\", int(data[3].split(\" \")[1]), \"Nginx\", ip)\n    getData(\"nginx.net.writing\", int(data[3].split(\" \")[3]), \"Nginx\", ip)\n    getData(\"nginx.net.waiting\", int(data[3].split(\" \")[3]), \"Nginx\", ip)\n\nif len(server_list) > 1:\n    getData(\"nginx.net.connections.cluster\", sum(active_), \"Nginx\")\n    getData(\"nginx.net.reading.clsuter\", sum(reading_), \"Nginx\")\n    getData(\"nginx.net.writing.cluster\", sum(writing_), \"Nginx\")\n    getData(\"nginx.net.waiting.cluster\", sum(waiting_), \"Nginx\")\n\nfor ip in server_list:\n    url = \"http://%s/monitor_status?json\"%(ip)\n    data = curl(url)\n    time.sleep(1)\n    if not data : continue\n    data = json.loads(data)\n    url = \"http://%s/monitor_status?json\"%(ip)\n    data2 = curl(url)\n    data2 = json.loads(data2)\n    getData(\"php_fpm.listen_queue.max_size\", data.get(\"max listen queue\"), \"PHP_FPM\", ip)\n    getData(\"php_fpm.listen_queue.size\", data.get(\"listen queue len\"), \"PHP_FPM\", ip)\n    getData(\"php_fpm.processes.active\", data.get(\"active processes\"), \"PHP_FPM\", ip)\n    getData(\"php_fpm.processes.idle\", data.get(\"idle processes\"), \"PHP_FPM\", ip)\n    getData(\"php_fpm.processes.totle\", data.get(\"totle processes\"), \"PHP_FPM\", ip)\n    getData(\"php_fpm.processes.max_active\", data.get(\"max active processes\"), \"PHP_FPM\", ip)\n    getData(\"php_fpm.processes.max_reached\", data.get(\"max children reached\"), \"PHP_FPM\", ip)\n    getData(\"php_fpm.requests.accepted_conn\", data2.get(\"accepted conn\") - data.get(\"accepted conn\"), \"PHP_FPM\", ip)\n    getData(\"php_fpm.requests.slow\", data.get(\"slow requests\"), \"PHP_FPM\", ip)\n\nprint json.dumps(list)                                 ',NULL,'nginx和phpfpm性能监控','nginx和phpfpm性能监控','','','','','','','','','','','','','','','','可选的，可以添加多个IP地址，多个用逗号分隔',NULL,NULL),(22,'2017-01-17 03:37:33','zhaozq14',1,NULL,'os_readlog.py','#!/usr/bin/python\n#--coding:utf-8\n# 20161121\n# 参数1要监控的脚本执行路径\n# 不能有空格\n# 主要调取一个自己写好的脚本如果有输出则报警\n\nimport time\nimport json\nimport os\nimport sys\nimport commands\n\n\ndef replace(string):\n    replace_str = [\";\",\" \",\"&\",\"(\", \")\", \"[\", \"]\", \">\" , \"<\" ,\"\\\\\"]\n    for i in replace_str:\n        string = string.replace(i, \"\")\n    return string\n\ntry:\n   cmd = sys.argv[1]\n   cmd = replace(cmd) \nexcept Exception:\n   cmd = \"echo error, no command found\"\n\nlockfile = \"/dev/shm/lock.\"+cmd.replace(\"/\", \"\")\n\n    \n\n\nresult = {}\nresult[\"name\"] = \"scripts.output.error\"\nresult[\"groups\"] = \"log\"\n\nif os.path.exists(lockfile):\n   status = 2\n   value = 1\n   result[\"messages\"] = \"command is locked\"\n\nelse:\n    f = open(lockfile, \"w\")\n    f.write(str(int(time.time())))\n    f.close()\n    data = commands.getstatusoutput(cmd) \n    if data[0] == 0:\n        if not data[1]:\n            status = 1\n            value = 0\n            result[\"messages\"] = \"no info\"\n        else:\n            status = 2\n            value = 1\n            result[\"messages\"] = \'%s,\'%(data[1].decode(\"utf-8\"))\n    \n    else:\n        status = 2\n        value = 1\n        result[\"messages\"] = \'%s,\'%(data[1].decode(\"utf-8\"))\n    os.remove(lockfile)\nresult[\"value\"] = value\nresult[\"status\"] = status\nresult[\"script\"] = sys.argv[0]\nresult[\"command\"] = sys.argv[0]\nprint json.dumps([result])                                                                                                ',NULL,'脚本有输出为报警，没有输出不报警','脚本数据监控','ls','','','','','','','','','','','','','','','要执行的脚本路径,不能有空格',NULL,NULL),(23,'2016-11-24 00:09:30','',1,NULL,'process_check.py','#!/usr/bin/python\n#--coding:utf-8\n#20161123\n# 进程监控\n# 参数为进程名\n# “”\n\nimport json\nimport sys\nimport os\nimport re\n\ntry:\n    arg1 = sys.argv[1]\nexcept Exception:\n    arg1 = \"\"\n\n\ndef replace(string):\n    replace_str = [\";\",\"&\",\"(\", \")\", \"[\", \"]\", \">\" , \"<\" ,\"\\\\\",\"\'\"]\n    for i in replace_str:\n        string = string.replace(i, \"\")\n    return string\n\ncheck_list = arg1.split(\",\")\ndef getResult(name, value, status, messages):\n    result = {}\n    result[\"groups\"] = \"process\"\n    result[\"name\"] = name.replace(\"/\", \"SLASH\")\n    result[\"command\"] = sys.argv[0]\n    result[\"status\"] = status\n    result[\"value\"] = value\n    result[\"messages\"] = messages\n    list.append(result)\n\nlist = []\n\nfor p in check_list:\n   p = replace(p)\n   cmd = \"ps aux|grep %s|grep -v grep|grep -v %s\"%(p, sys.argv[0])\n   data = os.popen(cmd).read()\n   if data:\n       getResult(p, 1, 1, \"ok \" + p)\n   else:\n       getResult(p, 0, 2, \"no process found \" + p)\nprint json.dumps(list)',NULL,'检查进程是否存在','进程存在检查','sshd','','','','','','','','','','','','','','','要检查的进程名,多个用逗号分隔',NULL,NULL),(24,'2017-01-13 08:12:21','zhaozq14',1,NULL,'soacheck.py','#!/usr/bin/python\n#--coding:utf-8\n# 监控soa提供者\nimport json\nimport sys\nimport telnetlib\nimport os\n\n\nlocal = 1\nHost = [sys.argv[len(sys.argv)-1]]\nport = sys.argv[1]\ntry:\n    if len(sys.argv) > 2:\n         local = 0\n         Host = sys.argv[2].split(\",\")\nexcept Exception:\n    pass\n\nlist = []\nfor ip in Host:\n    if not ip : continue\n    result = {}\n    result[\"groups\"] = \"soa\"\n    result[\"name\"] = \"dubbo.status\"\n    result[\"command\"] = sys.argv[0]\n\n    hostname = \"\"\n    status = 1\n    result[\"ip\"] = ip\n    \n    hostname = os.popen(\"hostname\").read().replace(\"\\n\",\"\")\n    try:\n        tn = telnetlib.Telnet(ip, port=int(port), timeout=6)\n        tn.write(\'status\\r\\n\')\n        data = tn.read_until(\"dubbo>\")\n        if \'OK\' in data:\n             status = 1\n             result[\"messages\"] = ip +\" \" + data +\" \" + hostname\n             result[\"value\"] = 1\n        else:\n             status = 2\n             result[\"value\"] = 0\n             result[\"messages\"] = ip +\" \" + data + \" \" + hostname\n    except Exception, e:\n        status = 2\n        result[\"value\"] = 0\n        result[\"messages\"] = str(port) + \" \"+ str(e) + hostname \n\n    result[\"status\"] = status\n    list.append(result)\n\nprint json.dumps(list)                                                                 ',NULL,'监控SOA服务','SOA提供者监控','20880','127.0.0.1','','','','','','','','','','','','','要监控的服务器地址,多个逗号分隔','dubbo服务端口',NULL,NULL);
/*!40000 ALTER TABLE `monitor_scripts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `monitor_severity`
--

DROP TABLE IF EXISTS `monitor_severity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `monitor_severity` (
  `severity_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `severity_name` varchar(100) DEFAULT NULL COMMENT '级别名称',
  `last_modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近修改时间',
  `last_modify_user` varchar(32) DEFAULT NULL COMMENT '最近修改用户',
  PRIMARY KEY (`severity_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `monitor_severity`
--

LOCK TABLES `monitor_severity` WRITE;
/*!40000 ALTER TABLE `monitor_severity` DISABLE KEYS */;
INSERT INTO `monitor_severity` VALUES (1,'正常','2016-08-31 08:55:57',NULL),(2,'警告','2016-08-31 08:56:22',NULL),(3,'危险','2016-08-31 08:56:34',NULL),(4,'未知','2016-08-31 08:56:38',NULL);
/*!40000 ALTER TABLE `monitor_severity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `monitor_system_scripts`
--

DROP TABLE IF EXISTS `monitor_system_scripts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `monitor_system_scripts` (
  `scripts_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `scripts_content` text COMMENT '脚本内容',
  `os_name` varchar(32) DEFAULT NULL COMMENT '操作系统类型',
  `last_modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近修改时间',
  `last_modify_user` varchar(100) DEFAULT NULL COMMENT '最近修改用户',
  `description` varchar(100) DEFAULT NULL COMMENT '描叙信息',
  PRIMARY KEY (`scripts_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `monitor_system_scripts`
--

LOCK TABLES `monitor_system_scripts` WRITE;
/*!40000 ALTER TABLE `monitor_system_scripts` DISABLE KEYS */;
INSERT INTO `monitor_system_scripts` VALUES (1,'#!/usr/bin/python\n#--coding:utf-8\n# 自定义上传服务器信息\n# 1、主机名\n# 2、服务器cpu\n# 3、服务器硬盘\n# 4、内存信息\n# 5、登陆信息\n# 6、网络\n\nimport json\nimport os\nimport re\nimport time\n\nversion = \"1.0.0.1\"\n\nlist = []\n\n\ndef getData(param, data, value=2):\n   data = re.findall( r\"{0}.*\".format(param), data)[0].replace(\"  \",\" \")\n   data = data.replace(\"\\t\",\" \").split(\" \")\n   return data[len(data)-value]\n\n\ndef getResult(name, lable, result):\n    data = {}\n    data[\"name\"] = name \n    data[\"lable\"] = lable\n    data[\"result\"] = result\n    list.append(data)\n\ndef replace(string):\n   for i in range(1,10):\n      string = string.replace(\"  \", \" \")\n   return string\n\ndef replaceList(strings):\n    strs = []\n    for string in strings:\n      strs.append(string.replace(\"\\n\", \"\"))\n    return strs\n\n## cpu\ncpu_data = open(\"/proc/cpuinfo\",\"r\").read()\ntry:\n   physical_id = re.findall( r\"{0}.*\".format(\"physical id\"), cpu_data)\n   physical_data = int(physical_id[len(physical_id)-1].replace(\"\\t\",\" \").split(\" \")[3])+1\nexcept Exception:\n   physical_data = 0\n\ngetResult(\"cache_size\", \"CPU\", getData(\"cache size\", cpu_data))\ngetResult(\"cpu_cores\", \"CPU\",  len(re.findall(r\"cpu cores\", cpu_data)))\ngetResult(\"cpu_logical_processors\", \"CPU\",  len(re.findall(r\"processor\", cpu_data)))\ngetResult(\"physical_id\", \"CPU\", physical_data) \ngetResult(\"family\", \"CPU\", getData(\"cpu family\", cpu_data, 1))\ngetResult(\"mhz\", \"CPU\", getData(\"cpu MHz\", cpu_data, 1))\ngetResult(\"model\", \"CPU\", getData(\"model\", cpu_data, 1))\ngetResult(\"model_name\", \"CPU\", re.findall(r\"model name.*\", cpu_data)[0].split(\"\\t\")[1])\ngetResult(\"stepping\", \"CPU\", getData(\"stepping\", cpu_data, 1)) \ngetResult(\"vendor_id\", \"CPU\", getData(\"vendor_id\", cpu_data, 1)) \n\n\n#FILESYSTEM\ndisk_data = os.popen(\" df -lh |sed \'1d;/ /!N;s/\\\\n//;s/ \\+/ /;\'| grep -v Mount\").readlines()\nfor d in disk_data:\n    d = replace(d.replace(\"\\t\",\" \"))\n    ddata = d.split(\" \")\n    getResult(ddata[0], \"FILESYSTEM\",\"mouted on: \" + ddata[len(ddata)-1].replace(\"\\n\",\"\") +\" size: \"+ ddata[len(ddata)-5] + \" used: \"+ ddata[len(ddata)-2] )\n\n## MEMORY \nmemory_data = open(\"/proc/meminfo\", \"r\").read()\ngetResult(\"memory_totle\", \"MEMORY\", str(int(getData(\"MemTotal\", memory_data, 2))/1000) +\" GB\") \ngetResult(\"memory_free\", \"MEMORY\", str(int(getData(\"MemFree\", memory_data, 2))/1000) +\" GB\") \ngetResult(\"swap_totle\", \"MEMORY\", str(int(getData(\"SwapTotal\", memory_data, 2))/1000) +\" GB\") \ngetResult(\"swap_free\", \"MEMORY\", str(int(getData(\"SwapFree\", memory_data, 2))/ 1000) +\" GB\") \n\n\n\n## NETWORK\nnetwork_data = os.popen(\"ip add  |egrep \'inet|ether\' |grep -v 127.0.0.1|grep global | awk \'{print $NF,$2}\'\").readlines()\ngetResult(\"ipaddress\", \"NETWORK\", network_data)\n\nnetwork_data = os.popen(\"ip add  |egrep \'ether\' |awk \'{print $2}\'\").readlines()\ngetResult(\"macaddress\", \"NETWORK\", network_data)\n\n\n### SYSTEM\ngetResult(\"process\", \"SYSTEM\", os.popen(\"ps aux |awk \'$3 > 0 {print $1,$3,$4,$11}\'\").read())\ngetResult(\"hostname\", \"SYSTEM\", os.popen(\"hostname\").read().replace(\"\\n\", \"\"))\ngetResult(\"route\", \"SYSTEM\", os.popen(\"route -n\").read())\ngetResult(\"open_port\", \"SYSTEM\", replaceList(os.popen(\"netstat -ntl\").readlines()))\ngetResult(\"version\", \"SYSTEM\", open(\"/proc/version\", \"r\").read().replace(\"\\n\", \"\"))\ngetResult(\"uptime\", \"SYSTEM\", os.popen(\"uptime\").read().replace(\"\\n\", \"\")) \ngetResult(\"time\", \"SYSTEM\", time.strftime(\'%Y-%m-%d %H:%M:%S\',time.localtime(time.time())))\n\nprint json.dumps(list)','Linux','2016-11-08 10:03:44','','上报linux系统的系统信息');
/*!40000 ALTER TABLE `monitor_system_scripts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `monitor_template`
--

DROP TABLE IF EXISTS `monitor_template`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `monitor_template` (
  `template_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '模板ID',
  `template_name` varchar(200) DEFAULT NULL COMMENT '模板名称',
  `templates` text COMMENT '模板拥有的模板',
  `last_modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近修改时间',
  `last_modify_user` varchar(32) DEFAULT NULL COMMENT '最近修改用户',
  `is_valid` int(11) DEFAULT NULL COMMENT '是否有效',
  `items` varchar(4000) DEFAULT NULL COMMENT '监控项目,多个用逗号分隔',
  `description` varchar(200) DEFAULT NULL COMMENT '描叙信息',
  PRIMARY KEY (`template_id`),
  UNIQUE KEY `uidx_monitor_template_template_name` (`template_name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `monitor_template`
--

LOCK TABLES `monitor_template` WRITE;
/*!40000 ALTER TABLE `monitor_template` DISABLE KEYS */;
INSERT INTO `monitor_template` VALUES (1,'基础监控模板','1','2016-10-09 07:35:31','',1,'2,3,6,7,8,9','基础监控，包括内存，cpu，硬盘等系统基础性能的监控');
/*!40000 ALTER TABLE `monitor_template` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `monitor_tigger`
--

DROP TABLE IF EXISTS `monitor_tigger`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `monitor_tigger` (
  `tigger_id` int(11) NOT NULL AUTO_INCREMENT,
  `tigger_name` varchar(300) DEFAULT NULL COMMENT '触发器名字',
  `expression` varchar(1000) DEFAULT NULL COMMENT '表达式,监控项  > == < memory.totle > 100 ',
  `is_valid` int(11) DEFAULT NULL COMMENT '是否有效,0无效,1有效',
  `last_modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近修改时间',
  `last_modify_user` varchar(32) DEFAULT NULL COMMENT '最近修改用户',
  `severity_id` int(11) DEFAULT NULL COMMENT '参考报警级别ID',
  `template_id` int(11) DEFAULT NULL COMMENT '所属模板ID，参考template_id',
  PRIMARY KEY (`tigger_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `monitor_tigger`
--

LOCK TABLES `monitor_tigger` WRITE;
/*!40000 ALTER TABLE `monitor_tigger` DISABLE KEYS */;
INSERT INTO `monitor_tigger` VALUES (1,NULL,NULL,NULL,'2016-08-20 01:51:11',NULL,NULL,NULL);
/*!40000 ALTER TABLE `monitor_tigger` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-01-31 11:00:39

CREATE TABLE `monitor_images_collection` (
  `collection_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `images` varchar(500) DEFAULT NULL COMMENT '收录图片地址',
  `create_time` varchar(32) DEFAULT NULL COMMENT '收录时间',
  `user` varchar(32) DEFAULT NULL COMMENT '收录人',
  `description` varchar(100) DEFAULT NULL COMMENT '描述信息',
  `last_modify_time` varchar(32) DEFAULT NULL,
  `ip` text comment "ip地址",
  PRIMARY KEY (`collection_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
