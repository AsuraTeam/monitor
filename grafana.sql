-- MySQL dump 10.13  Distrib 5.7.9, for linux-glibc2.5 (x86_64)
--
-- Host: localhost    Database: grafana
-- ------------------------------------------------------
-- Server version	5.7.9-log

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
-- Table structure for table `alert`
--

DROP TABLE IF EXISTS `alert`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `alert` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `dashboard_id` bigint(20) NOT NULL,
  `panel_id` bigint(20) NOT NULL,
  `org_id` bigint(20) NOT NULL,
  `name` varchar(255)  NOT NULL,
  `message` text  NOT NULL,
  `state` varchar(190)  NOT NULL,
  `settings` text  NOT NULL,
  `frequency` bigint(20) NOT NULL,
  `handler` bigint(20) NOT NULL,
  `severity` text  NOT NULL,
  `silenced` tinyint(1) NOT NULL,
  `execution_error` text  NOT NULL,
  `eval_data` text ,
  `eval_date` datetime DEFAULT NULL,
  `new_state_date` datetime NOT NULL,
  `state_changes` int(11) NOT NULL,
  `created` datetime NOT NULL,
  `updated` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_alert_org_id_id` (`org_id`,`id`),
  KEY `IDX_alert_state` (`state`),
  KEY `IDX_alert_dashboard_id` (`dashboard_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alert`
--

LOCK TABLES `alert` WRITE;
/*!40000 ALTER TABLE `alert` DISABLE KEYS */;
/*!40000 ALTER TABLE `alert` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `alert_notification`
--

DROP TABLE IF EXISTS `alert_notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `alert_notification` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `org_id` bigint(20) NOT NULL,
  `name` varchar(190)  NOT NULL,
  `type` varchar(255)  NOT NULL,
  `settings` text  NOT NULL,
  `created` datetime NOT NULL,
  `updated` datetime NOT NULL,
  `is_default` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UQE_alert_notification_org_id_name` (`org_id`,`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alert_notification`
--

LOCK TABLES `alert_notification` WRITE;
/*!40000 ALTER TABLE `alert_notification` DISABLE KEYS */;
/*!40000 ALTER TABLE `alert_notification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `annotation`
--

DROP TABLE IF EXISTS `annotation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `annotation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `org_id` bigint(20) NOT NULL,
  `alert_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `dashboard_id` bigint(20) DEFAULT NULL,
  `panel_id` bigint(20) DEFAULT NULL,
  `category_id` bigint(20) DEFAULT NULL,
  `type` varchar(25)  NOT NULL,
  `title` text  NOT NULL,
  `text` text  NOT NULL,
  `metric` varchar(255)  DEFAULT NULL,
  `prev_state` varchar(25)  NOT NULL,
  `new_state` varchar(25)  NOT NULL,
  `data` text  NOT NULL,
  `epoch` bigint(20) NOT NULL,
  `region_id` bigint(20) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `IDX_annotation_org_id_alert_id` (`org_id`,`alert_id`),
  KEY `IDX_annotation_org_id_type` (`org_id`,`type`),
  KEY `IDX_annotation_org_id_category_id` (`org_id`,`category_id`),
  KEY `IDX_annotation_org_id_dashboard_id_panel_id_epoch` (`org_id`,`dashboard_id`,`panel_id`,`epoch`),
  KEY `IDX_annotation_org_id_epoch` (`org_id`,`epoch`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `annotation`
--

LOCK TABLES `annotation` WRITE;
/*!40000 ALTER TABLE `annotation` DISABLE KEYS */;
/*!40000 ALTER TABLE `annotation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `api_key`
--

DROP TABLE IF EXISTS `api_key`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `api_key` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `org_id` bigint(20) NOT NULL,
  `name` varchar(190)  NOT NULL,
  `key` varchar(190)  NOT NULL,
  `role` varchar(255)  NOT NULL,
  `created` datetime NOT NULL,
  `updated` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UQE_api_key_key` (`key`),
  UNIQUE KEY `UQE_api_key_org_id_name` (`org_id`,`name`),
  KEY `IDX_api_key_org_id` (`org_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `api_key`
--

LOCK TABLES `api_key` WRITE;
/*!40000 ALTER TABLE `api_key` DISABLE KEYS */;
/*!40000 ALTER TABLE `api_key` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dashboard`
--

DROP TABLE IF EXISTS `dashboard`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dashboard` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` int(11) NOT NULL,
  `slug` varchar(189)  NOT NULL,
  `title` varchar(255)  NOT NULL,
  `data` mediumtext  NOT NULL,
  `org_id` bigint(20) NOT NULL,
  `created` datetime NOT NULL,
  `updated` datetime NOT NULL,
  `updated_by` int(11) DEFAULT NULL,
  `created_by` int(11) DEFAULT NULL,
  `gnet_id` bigint(20) DEFAULT NULL,
  `plugin_id` varchar(255)  DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UQE_dashboard_org_id_slug` (`org_id`,`slug`),
  KEY `IDX_dashboard_org_id` (`org_id`),
  KEY `IDX_dashboard_gnet_id` (`gnet_id`),
  KEY `IDX_dashboard_org_id_plugin_id` (`org_id`,`plugin_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dashboard`
--

--
-- Table structure for table `dashboard_snapshot`
--

DROP TABLE IF EXISTS `dashboard_snapshot`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dashboard_snapshot` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255)  NOT NULL,
  `key` varchar(190)  NOT NULL,
  `delete_key` varchar(190)  NOT NULL,
  `org_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `external` tinyint(1) NOT NULL,
  `external_url` varchar(255)  NOT NULL,
  `dashboard` mediumtext  NOT NULL,
  `expires` datetime NOT NULL,
  `created` datetime NOT NULL,
  `updated` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UQE_dashboard_snapshot_key` (`key`),
  UNIQUE KEY `UQE_dashboard_snapshot_delete_key` (`delete_key`),
  KEY `IDX_dashboard_snapshot_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dashboard_snapshot`
--

LOCK TABLES `dashboard_snapshot` WRITE;
/*!40000 ALTER TABLE `dashboard_snapshot` DISABLE KEYS */;
/*!40000 ALTER TABLE `dashboard_snapshot` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dashboard_tag`
--

DROP TABLE IF EXISTS `dashboard_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dashboard_tag` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `dashboard_id` bigint(20) NOT NULL,
  `term` varchar(50)  NOT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_dashboard_tag_dashboard_id` (`dashboard_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dashboard_tag`
--

--
-- Table structure for table `data_source`
--

DROP TABLE IF EXISTS `data_source`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `data_source` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `org_id` bigint(20) NOT NULL,
  `version` int(11) NOT NULL,
  `type` varchar(255)  NOT NULL,
  `name` varchar(190)  NOT NULL,
  `access` varchar(255)  NOT NULL,
  `url` varchar(255)  NOT NULL,
  `password` varchar(255)  DEFAULT NULL,
  `user` varchar(255)  DEFAULT NULL,
  `database` varchar(255)  DEFAULT NULL,
  `basic_auth` tinyint(1) NOT NULL,
  `basic_auth_user` varchar(255)  DEFAULT NULL,
  `basic_auth_password` varchar(255)  DEFAULT NULL,
  `is_default` tinyint(1) NOT NULL,
  `json_data` text ,
  `created` datetime NOT NULL,
  `updated` datetime NOT NULL,
  `with_credentials` tinyint(1) NOT NULL DEFAULT '0',
  `secure_json_data` text ,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UQE_data_source_org_id_name` (`org_id`,`name`),
  KEY `IDX_data_source_org_id` (`org_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `data_source`
--


--
-- Table structure for table `migration_log`
--

DROP TABLE IF EXISTS `migration_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `migration_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `migration_id` varchar(255)  NOT NULL,
  `sql` text  NOT NULL,
  `success` tinyint(1) NOT NULL,
  `error` text  NOT NULL,
  `timestamp` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=128 DEFAULT CHARSET=utf8 ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `migration_log`
--

LOCK TABLES `migration_log` WRITE;
/*!40000 ALTER TABLE `migration_log` DISABLE KEYS */;
INSERT INTO `migration_log` VALUES (1,'create migration_log table','CREATE TABLE IF NOT EXISTS `migration_log` (\n`id` BIGINT(20) PRIMARY KEY AUTO_INCREMENT NOT NULL\n, `migration_id` VARCHAR(255) CHARACTER SET utf8  NOT NULL\n, `sql` TEXT CHARACTER SET utf8  NOT NULL\n, `success` TINYINT(1) NOT NULL\n, `error` TEXT CHARACTER SET utf8  NOT NULL\n, `timestamp` DATETIME NOT NULL\n) ENGINE=InnoDB DEFAULT CHARSET utf8 ;',1,'','2017-07-07 21:40:21'),(2,'create user table','CREATE TABLE IF NOT EXISTS `user` (\n`id` BIGINT(20) PRIMARY KEY AUTO_INCREMENT NOT NULL\n, `version` INT NOT NULL\n, `login` VARCHAR(190) CHARACTER SET utf8  NOT NULL\n, `email` VARCHAR(190) CHARACTER SET utf8  NOT NULL\n, `name` VARCHAR(255) CHARACTER SET utf8  NULL\n, `password` VARCHAR(255) CHARACTER SET utf8  NULL\n, `salt` VARCHAR(50) CHARACTER SET utf8  NULL\n, `rands` VARCHAR(50) CHARACTER SET utf8  NULL\n, `company` VARCHAR(255) CHARACTER SET utf8  NULL\n, `account_id` BIGINT(20) NOT NULL\n, `is_admin` TINYINT(1) NOT NULL\n, `created` DATETIME NOT NULL\n, `updated` DATETIME NOT NULL\n) ENGINE=InnoDB DEFAULT CHARSET utf8 ;',1,'','2017-07-07 21:40:22'),(3,'add unique index user.login','CREATE UNIQUE INDEX `UQE_user_login` ON `user` (`login`);',1,'','2017-07-07 21:40:22'),(4,'add unique index user.email','CREATE UNIQUE INDEX `UQE_user_email` ON `user` (`email`);',1,'','2017-07-07 21:40:22'),(5,'drop index UQE_user_login - v1','DROP INDEX `UQE_user_login` ON `user`',1,'','2017-07-07 21:40:22'),(6,'drop index UQE_user_email - v1','DROP INDEX `UQE_user_email` ON `user`',1,'','2017-07-07 21:40:22'),(7,'Rename table user to user_v1 - v1','ALTER TABLE `user` RENAME TO `user_v1`',1,'','2017-07-07 21:40:22'),(8,'create user table v2','CREATE TABLE IF NOT EXISTS `user` (\n`id` BIGINT(20) PRIMARY KEY AUTO_INCREMENT NOT NULL\n, `version` INT NOT NULL\n, `login` VARCHAR(190) CHARACTER SET utf8  NOT NULL\n, `email` VARCHAR(190) CHARACTER SET utf8  NOT NULL\n, `name` VARCHAR(255) CHARACTER SET utf8  NULL\n, `password` VARCHAR(255) CHARACTER SET utf8  NULL\n, `salt` VARCHAR(50) CHARACTER SET utf8  NULL\n, `rands` VARCHAR(50) CHARACTER SET utf8  NULL\n, `company` VARCHAR(255) CHARACTER SET utf8  NULL\n, `org_id` BIGINT(20) NOT NULL\n, `is_admin` TINYINT(1) NOT NULL\n, `email_verified` TINYINT(1) NULL\n, `theme` VARCHAR(255) CHARACTER SET utf8  NULL\n, `created` DATETIME NOT NULL\n, `updated` DATETIME NOT NULL\n) ENGINE=InnoDB DEFAULT CHARSET utf8 ;',1,'','2017-07-07 21:40:23'),(9,'create index UQE_user_login - v2','CREATE UNIQUE INDEX `UQE_user_login` ON `user` (`login`);',1,'','2017-07-07 21:40:23'),(10,'create index UQE_user_email - v2','CREATE UNIQUE INDEX `UQE_user_email` ON `user` (`email`);',1,'','2017-07-07 21:40:23'),(11,'copy data_source v1 to v2','INSERT INTO `user` (`updated`\n, `id`\n, `company`\n, `org_id`\n, `is_admin`\n, `name`\n, `salt`\n, `rands`\n, `created`\n, `version`\n, `login`\n, `email`\n, `password`) SELECT `updated`\n, `id`\n, `company`\n, `account_id`\n, `is_admin`\n, `name`\n, `salt`\n, `rands`\n, `created`\n, `version`\n, `login`\n, `email`\n, `password` FROM `user_v1`',1,'','2017-07-07 21:40:23'),(12,'Drop old table user_v1','DROP TABLE IF EXISTS `user_v1`',1,'','2017-07-07 21:40:23'),(13,'Add column help_flags1 to user table','alter table `user` ADD COLUMN `help_flags1` BIGINT(20) NOT NULL DEFAULT 0 ',1,'','2017-07-07 21:40:23'),(14,'Update user table charset','ALTER TABLE `user` DEFAULT CHARACTER SET utf8 , MODIFY `login` VARCHAR(190) CHARACTER SET utf8  NOT NULL , MODIFY `email` VARCHAR(190) CHARACTER SET utf8  NOT NULL , MODIFY `name` VARCHAR(255) CHARACTER SET utf8  NULL , MODIFY `password` VARCHAR(255) CHARACTER SET utf8  NULL , MODIFY `salt` VARCHAR(50) CHARACTER SET utf8  NULL , MODIFY `rands` VARCHAR(50) CHARACTER SET utf8  NULL , MODIFY `company` VARCHAR(255) CHARACTER SET utf8  NULL , MODIFY `theme` VARCHAR(255) CHARACTER SET utf8  NULL ;',1,'','2017-07-07 21:40:23'),(15,'create temp user table v1-7','CREATE TABLE IF NOT EXISTS `temp_user` (\n`id` BIGINT(20) PRIMARY KEY AUTO_INCREMENT NOT NULL\n, `org_id` BIGINT(20) NOT NULL\n, `version` INT NOT NULL\n, `email` VARCHAR(190) CHARACTER SET utf8  NOT NULL\n, `name` VARCHAR(255) CHARACTER SET utf8  NULL\n, `role` VARCHAR(20) CHARACTER SET utf8  NULL\n, `code` VARCHAR(190) CHARACTER SET utf8  NOT NULL\n, `status` VARCHAR(20) CHARACTER SET utf8  NOT NULL\n, `invited_by_user_id` BIGINT(20) NULL\n, `email_sent` TINYINT(1) NOT NULL\n, `email_sent_on` DATETIME NULL\n, `remote_addr` VARCHAR(255) CHARACTER SET utf8  NULL\n, `created` DATETIME NOT NULL\n, `updated` DATETIME NOT NULL\n) ENGINE=InnoDB DEFAULT CHARSET utf8 ;',1,'','2017-07-07 21:40:24'),(16,'create index IDX_temp_user_email - v1-7','CREATE INDEX `IDX_temp_user_email` ON `temp_user` (`email`);',1,'','2017-07-07 21:40:24'),(17,'create index IDX_temp_user_org_id - v1-7','CREATE INDEX `IDX_temp_user_org_id` ON `temp_user` (`org_id`);',1,'','2017-07-07 21:40:24'),(18,'create index IDX_temp_user_code - v1-7','CREATE INDEX `IDX_temp_user_code` ON `temp_user` (`code`);',1,'','2017-07-07 21:40:24'),(19,'create index IDX_temp_user_status - v1-7','CREATE INDEX `IDX_temp_user_status` ON `temp_user` (`status`);',1,'','2017-07-07 21:40:24'),(20,'Update temp_user table charset','ALTER TABLE `temp_user` DEFAULT CHARACTER SET utf8 , MODIFY `email` VARCHAR(190) CHARACTER SET utf8  NOT NULL , MODIFY `name` VARCHAR(255) CHARACTER SET utf8  NULL , MODIFY `role` VARCHAR(20) CHARACTER SET utf8  NULL , MODIFY `code` VARCHAR(190) CHARACTER SET utf8  NOT NULL , MODIFY `status` VARCHAR(20) CHARACTER SET utf8  NOT NULL , MODIFY `remote_addr` VARCHAR(255) CHARACTER SET utf8  NULL ;',1,'','2017-07-07 21:40:25'),(21,'create star table','CREATE TABLE IF NOT EXISTS `star` (\n`id` BIGINT(20) PRIMARY KEY AUTO_INCREMENT NOT NULL\n, `user_id` BIGINT(20) NOT NULL\n, `dashboard_id` BIGINT(20) NOT NULL\n) ENGINE=InnoDB DEFAULT CHARSET utf8 ;',1,'','2017-07-07 21:40:25'),(22,'add unique index star.user_id_dashboard_id','CREATE UNIQUE INDEX `UQE_star_user_id_dashboard_id` ON `star` (`user_id`,`dashboard_id`);',1,'','2017-07-07 21:40:25'),(23,'create org table v1','CREATE TABLE IF NOT EXISTS `org` (\n`id` BIGINT(20) PRIMARY KEY AUTO_INCREMENT NOT NULL\n, `version` INT NOT NULL\n, `name` VARCHAR(190) CHARACTER SET utf8  NOT NULL\n, `address1` VARCHAR(255) CHARACTER SET utf8  NULL\n, `address2` VARCHAR(255) CHARACTER SET utf8  NULL\n, `city` VARCHAR(255) CHARACTER SET utf8  NULL\n, `state` VARCHAR(255) CHARACTER SET utf8  NULL\n, `zip_code` VARCHAR(50) CHARACTER SET utf8  NULL\n, `country` VARCHAR(255) CHARACTER SET utf8  NULL\n, `billing_email` VARCHAR(255) CHARACTER SET utf8  NULL\n, `created` DATETIME NOT NULL\n, `updated` DATETIME NOT NULL\n) ENGINE=InnoDB DEFAULT CHARSET utf8 ;',1,'','2017-07-07 21:40:25'),(24,'create index UQE_org_name - v1','CREATE UNIQUE INDEX `UQE_org_name` ON `org` (`name`);',1,'','2017-07-07 21:40:26'),(25,'create org_user table v1','CREATE TABLE IF NOT EXISTS `org_user` (\n`id` BIGINT(20) PRIMARY KEY AUTO_INCREMENT NOT NULL\n, `org_id` BIGINT(20) NOT NULL\n, `user_id` BIGINT(20) NOT NULL\n, `role` VARCHAR(20) CHARACTER SET utf8  NOT NULL\n, `created` DATETIME NOT NULL\n, `updated` DATETIME NOT NULL\n) ENGINE=InnoDB DEFAULT CHARSET utf8 ;',1,'','2017-07-07 21:40:26'),(26,'create index IDX_org_user_org_id - v1','CREATE INDEX `IDX_org_user_org_id` ON `org_user` (`org_id`);',1,'','2017-07-07 21:40:26'),(27,'create index UQE_org_user_org_id_user_id - v1','CREATE UNIQUE INDEX `UQE_org_user_org_id_user_id` ON `org_user` (`org_id`,`user_id`);',1,'','2017-07-07 21:40:26'),(28,'Drop old table account','DROP TABLE IF EXISTS `account`',1,'','2017-07-07 21:40:26'),(29,'Drop old table account_user','DROP TABLE IF EXISTS `account_user`',1,'','2017-07-07 21:40:26'),(30,'Update org table charset','ALTER TABLE `org` DEFAULT CHARACTER SET utf8 , MODIFY `name` VARCHAR(190) CHARACTER SET utf8  NOT NULL , MODIFY `address1` VARCHAR(255) CHARACTER SET utf8  NULL , MODIFY `address2` VARCHAR(255) CHARACTER SET utf8  NULL , MODIFY `city` VARCHAR(255) CHARACTER SET utf8  NULL , MODIFY `state` VARCHAR(255) CHARACTER SET utf8  NULL , MODIFY `zip_code` VARCHAR(50) CHARACTER SET utf8  NULL , MODIFY `country` VARCHAR(255) CHARACTER SET utf8  NULL , MODIFY `billing_email` VARCHAR(255) CHARACTER SET utf8  NULL ;',1,'','2017-07-07 21:40:27'),(31,'Update org_user table charset','ALTER TABLE `org_user` DEFAULT CHARACTER SET utf8 , MODIFY `role` VARCHAR(20) CHARACTER SET utf8  NOT NULL ;',1,'','2017-07-07 21:40:27'),(32,'create dashboard table','CREATE TABLE IF NOT EXISTS `dashboard` (\n`id` BIGINT(20) PRIMARY KEY AUTO_INCREMENT NOT NULL\n, `version` INT NOT NULL\n, `slug` VARCHAR(190) CHARACTER SET utf8  NOT NULL\n, `title` VARCHAR(255) CHARACTER SET utf8  NOT NULL\n, `data` TEXT CHARACTER SET utf8  NOT NULL\n, `account_id` BIGINT(20) NOT NULL\n, `created` DATETIME NOT NULL\n, `updated` DATETIME NOT NULL\n) ENGINE=InnoDB DEFAULT CHARSET utf8 ;',1,'','2017-07-07 21:40:27'),(33,'add index dashboard.account_id','CREATE INDEX `IDX_dashboard_account_id` ON `dashboard` (`account_id`);',1,'','2017-07-07 21:40:27'),(34,'add unique index dashboard_account_id_slug','CREATE UNIQUE INDEX `UQE_dashboard_account_id_slug` ON `dashboard` (`account_id`,`slug`);',1,'','2017-07-07 21:40:27'),(35,'create dashboard_tag table','CREATE TABLE IF NOT EXISTS `dashboard_tag` (\n`id` BIGINT(20) PRIMARY KEY AUTO_INCREMENT NOT NULL\n, `dashboard_id` BIGINT(20) NOT NULL\n, `term` VARCHAR(50) CHARACTER SET utf8  NOT NULL\n) ENGINE=InnoDB DEFAULT CHARSET utf8 ;',1,'','2017-07-07 21:40:28'),(36,'add unique index dashboard_tag.dasboard_id_term','CREATE UNIQUE INDEX `UQE_dashboard_tag_dashboard_id_term` ON `dashboard_tag` (`dashboard_id`,`term`);',1,'','2017-07-07 21:40:28'),(37,'drop index UQE_dashboard_tag_dashboard_id_term - v1','DROP INDEX `UQE_dashboard_tag_dashboard_id_term` ON `dashboard_tag`',1,'','2017-07-07 21:40:28'),(38,'Rename table dashboard to dashboard_v1 - v1','ALTER TABLE `dashboard` RENAME TO `dashboard_v1`',1,'','2017-07-07 21:40:28'),(39,'create dashboard v2','CREATE TABLE IF NOT EXISTS `dashboard` (\n`id` BIGINT(20) PRIMARY KEY AUTO_INCREMENT NOT NULL\n, `version` INT NOT NULL\n, `slug` VARCHAR(189) CHARACTER SET utf8  NOT NULL\n, `title` VARCHAR(255) CHARACTER SET utf8  NOT NULL\n, `data` TEXT CHARACTER SET utf8  NOT NULL\n, `org_id` BIGINT(20) NOT NULL\n, `created` DATETIME NOT NULL\n, `updated` DATETIME NOT NULL\n) ENGINE=InnoDB DEFAULT CHARSET utf8 ;',1,'','2017-07-07 21:40:28'),(40,'create index IDX_dashboard_org_id - v2','CREATE INDEX `IDX_dashboard_org_id` ON `dashboard` (`org_id`);',1,'','2017-07-07 21:40:29'),(41,'create index UQE_dashboard_org_id_slug - v2','CREATE UNIQUE INDEX `UQE_dashboard_org_id_slug` ON `dashboard` (`org_id`,`slug`);',1,'','2017-07-07 21:40:29'),(42,'copy dashboard v1 to v2','INSERT INTO `dashboard` (`version`\n, `slug`\n, `title`\n, `data`\n, `org_id`\n, `id`\n, `updated`\n, `created`) SELECT `version`\n, `slug`\n, `title`\n, `data`\n, `account_id`\n, `id`\n, `updated`\n, `created` FROM `dashboard_v1`',1,'','2017-07-07 21:40:29'),(43,'drop table dashboard_v1','DROP TABLE IF EXISTS `dashboard_v1`',1,'','2017-07-07 21:40:29'),(44,'alter dashboard.data to mediumtext v1','ALTER TABLE dashboard MODIFY data MEDIUMTEXT;',1,'','2017-07-07 21:40:29'),(45,'Add column updated_by in dashboard - v2','alter table `dashboard` ADD COLUMN `updated_by` INT NULL ',1,'','2017-07-07 21:40:29'),(46,'Add column created_by in dashboard - v2','alter table `dashboard` ADD COLUMN `created_by` INT NULL ',1,'','2017-07-07 21:40:30'),(47,'Add column gnetId in dashboard','alter table `dashboard` ADD COLUMN `gnet_id` BIGINT(20) NULL ',1,'','2017-07-07 21:40:30'),(48,'Add index for gnetId in dashboard','CREATE INDEX `IDX_dashboard_gnet_id` ON `dashboard` (`gnet_id`);',1,'','2017-07-07 21:40:30'),(49,'Add column plugin_id in dashboard','alter table `dashboard` ADD COLUMN `plugin_id` VARCHAR(255) CHARACTER SET utf8  NULL ',1,'','2017-07-07 21:40:30'),(50,'Add index for plugin_id in dashboard','CREATE INDEX `IDX_dashboard_org_id_plugin_id` ON `dashboard` (`org_id`,`plugin_id`);',1,'','2017-07-07 21:40:31'),(51,'Add index for dashboard_id in dashboard_tag','CREATE INDEX `IDX_dashboard_tag_dashboard_id` ON `dashboard_tag` (`dashboard_id`);',1,'','2017-07-07 21:40:31'),(52,'Update dashboard table charset','ALTER TABLE `dashboard` DEFAULT CHARACTER SET utf8 , MODIFY `slug` VARCHAR(189) CHARACTER SET utf8  NOT NULL , MODIFY `title` VARCHAR(255) CHARACTER SET utf8  NOT NULL , MODIFY `plugin_id` VARCHAR(255) CHARACTER SET utf8  NULL , MODIFY `data` MEDIUMTEXT CHARACTER SET utf8  NOT NULL ;',1,'','2017-07-07 21:40:31'),(53,'Update dashboard_tag table charset','ALTER TABLE `dashboard_tag` DEFAULT CHARACTER SET utf8 , MODIFY `term` VARCHAR(50) CHARACTER SET utf8  NOT NULL ;',1,'','2017-07-07 21:40:31'),(54,'create data_source table','CREATE TABLE IF NOT EXISTS `data_source` (\n`id` BIGINT(20) PRIMARY KEY AUTO_INCREMENT NOT NULL\n, `account_id` BIGINT(20) NOT NULL\n, `version` INT NOT NULL\n, `type` VARCHAR(255) CHARACTER SET utf8  NOT NULL\n, `name` VARCHAR(190) CHARACTER SET utf8  NOT NULL\n, `access` VARCHAR(255) CHARACTER SET utf8  NOT NULL\n, `url` VARCHAR(255) CHARACTER SET utf8  NOT NULL\n, `password` VARCHAR(255) CHARACTER SET utf8  NULL\n, `user` VARCHAR(255) CHARACTER SET utf8  NULL\n, `database` VARCHAR(255) CHARACTER SET utf8  NULL\n, `basic_auth` TINYINT(1) NOT NULL\n, `basic_auth_user` VARCHAR(255) CHARACTER SET utf8  NULL\n, `basic_auth_password` VARCHAR(255) CHARACTER SET utf8  NULL\n, `is_default` TINYINT(1) NOT NULL\n, `created` DATETIME NOT NULL\n, `updated` DATETIME NOT NULL\n) ENGINE=InnoDB DEFAULT CHARSET utf8 ;',1,'','2017-07-07 21:40:32'),(55,'add index data_source.account_id','CREATE INDEX `IDX_data_source_account_id` ON `data_source` (`account_id`);',1,'','2017-07-07 21:40:32'),(56,'add unique index data_source.account_id_name','CREATE UNIQUE INDEX `UQE_data_source_account_id_name` ON `data_source` (`account_id`,`name`);',1,'','2017-07-07 21:40:32'),(57,'drop index IDX_data_source_account_id - v1','DROP INDEX `IDX_data_source_account_id` ON `data_source`',1,'','2017-07-07 21:40:32'),(58,'drop index UQE_data_source_account_id_name - v1','DROP INDEX `UQE_data_source_account_id_name` ON `data_source`',1,'','2017-07-07 21:40:32'),(59,'Rename table data_source to data_source_v1 - v1','ALTER TABLE `data_source` RENAME TO `data_source_v1`',1,'','2017-07-07 21:40:32'),(60,'create data_source table v2','CREATE TABLE IF NOT EXISTS `data_source` (\n`id` BIGINT(20) PRIMARY KEY AUTO_INCREMENT NOT NULL\n, `org_id` BIGINT(20) NOT NULL\n, `version` INT NOT NULL\n, `type` VARCHAR(255) CHARACTER SET utf8  NOT NULL\n, `name` VARCHAR(190) CHARACTER SET utf8  NOT NULL\n, `access` VARCHAR(255) CHARACTER SET utf8  NOT NULL\n, `url` VARCHAR(255) CHARACTER SET utf8  NOT NULL\n, `password` VARCHAR(255) CHARACTER SET utf8  NULL\n, `user` VARCHAR(255) CHARACTER SET utf8  NULL\n, `database` VARCHAR(255) CHARACTER SET utf8  NULL\n, `basic_auth` TINYINT(1) NOT NULL\n, `basic_auth_user` VARCHAR(255) CHARACTER SET utf8  NULL\n, `basic_auth_password` VARCHAR(255) CHARACTER SET utf8  NULL\n, `is_default` TINYINT(1) NOT NULL\n, `json_data` TEXT CHARACTER SET utf8  NULL\n, `created` DATETIME NOT NULL\n, `updated` DATETIME NOT NULL\n) ENGINE=InnoDB DEFAULT CHARSET utf8 ;',1,'','2017-07-07 21:40:33'),(61,'create index IDX_data_source_org_id - v2','CREATE INDEX `IDX_data_source_org_id` ON `data_source` (`org_id`);',1,'','2017-07-07 21:40:33'),(62,'create index UQE_data_source_org_id_name - v2','CREATE UNIQUE INDEX `UQE_data_source_org_id_name` ON `data_source` (`org_id`,`name`);',1,'','2017-07-07 21:40:33'),(63,'copy data_source v1 to v2','INSERT INTO `data_source` (`database`\n, `basic_auth`\n, `password`\n, `type`\n, `access`\n, `user`\n, `is_default`\n, `created`\n, `org_id`\n, `name`\n, `url`\n, `basic_auth_password`\n, `updated`\n, `id`\n, `version`\n, `basic_auth_user`) SELECT `database`\n, `basic_auth`\n, `password`\n, `type`\n, `access`\n, `user`\n, `is_default`\n, `created`\n, `account_id`\n, `name`\n, `url`\n, `basic_auth_password`\n, `updated`\n, `id`\n, `version`\n, `basic_auth_user` FROM `data_source_v1`',1,'','2017-07-07 21:40:33'),(64,'Drop old table data_source_v1 #2','DROP TABLE IF EXISTS `data_source_v1`',1,'','2017-07-07 21:40:33'),(65,'Add column with_credentials','alter table `data_source` ADD COLUMN `with_credentials` TINYINT(1) NOT NULL DEFAULT 0 ',1,'','2017-07-07 21:40:33'),(66,'Add secure json data column','alter table `data_source` ADD COLUMN `secure_json_data` TEXT CHARACTER SET utf8  NULL ',1,'','2017-07-07 21:40:33'),(67,'Update data_source table charset','ALTER TABLE `data_source` DEFAULT CHARACTER SET utf8 , MODIFY `type` VARCHAR(255) CHARACTER SET utf8  NOT NULL , MODIFY `name` VARCHAR(190) CHARACTER SET utf8  NOT NULL , MODIFY `access` VARCHAR(255) CHARACTER SET utf8  NOT NULL , MODIFY `url` VARCHAR(255) CHARACTER SET utf8  NOT NULL , MODIFY `password` VARCHAR(255) CHARACTER SET utf8  NULL , MODIFY `user` VARCHAR(255) CHARACTER SET utf8  NULL , MODIFY `database` VARCHAR(255) CHARACTER SET utf8  NULL , MODIFY `basic_auth_user` VARCHAR(255) CHARACTER SET utf8  NULL , MODIFY `basic_auth_password` VARCHAR(255) CHARACTER SET utf8  NULL , MODIFY `json_data` TEXT CHARACTER SET utf8  NULL , MODIFY `secure_json_data` TEXT CHARACTER SET utf8  NULL ;',1,'','2017-07-07 21:40:34'),(68,'create api_key table','CREATE TABLE IF NOT EXISTS `api_key` (\n`id` BIGINT(20) PRIMARY KEY AUTO_INCREMENT NOT NULL\n, `account_id` BIGINT(20) NOT NULL\n, `name` VARCHAR(190) CHARACTER SET utf8  NOT NULL\n, `key` VARCHAR(64) CHARACTER SET utf8  NOT NULL\n, `role` VARCHAR(255) CHARACTER SET utf8  NOT NULL\n, `created` DATETIME NOT NULL\n, `updated` DATETIME NOT NULL\n) ENGINE=InnoDB DEFAULT CHARSET utf8 ;',1,'','2017-07-07 21:40:34'),(69,'add index api_key.account_id','CREATE INDEX `IDX_api_key_account_id` ON `api_key` (`account_id`);',1,'','2017-07-07 21:40:34'),(70,'add index api_key.key','CREATE UNIQUE INDEX `UQE_api_key_key` ON `api_key` (`key`);',1,'','2017-07-07 21:40:34'),(71,'add index api_key.account_id_name','CREATE UNIQUE INDEX `UQE_api_key_account_id_name` ON `api_key` (`account_id`,`name`);',1,'','2017-07-07 21:40:35'),(72,'drop index IDX_api_key_account_id - v1','DROP INDEX `IDX_api_key_account_id` ON `api_key`',1,'','2017-07-07 21:40:35'),(73,'drop index UQE_api_key_key - v1','DROP INDEX `UQE_api_key_key` ON `api_key`',1,'','2017-07-07 21:40:35'),(74,'drop index UQE_api_key_account_id_name - v1','DROP INDEX `UQE_api_key_account_id_name` ON `api_key`',1,'','2017-07-07 21:40:35'),(75,'Rename table api_key to api_key_v1 - v1','ALTER TABLE `api_key` RENAME TO `api_key_v1`',1,'','2017-07-07 21:40:35'),(76,'create api_key table v2','CREATE TABLE IF NOT EXISTS `api_key` (\n`id` BIGINT(20) PRIMARY KEY AUTO_INCREMENT NOT NULL\n, `org_id` BIGINT(20) NOT NULL\n, `name` VARCHAR(190) CHARACTER SET utf8  NOT NULL\n, `key` VARCHAR(190) CHARACTER SET utf8  NOT NULL\n, `role` VARCHAR(255) CHARACTER SET utf8  NOT NULL\n, `created` DATETIME NOT NULL\n, `updated` DATETIME NOT NULL\n) ENGINE=InnoDB DEFAULT CHARSET utf8 ;',1,'','2017-07-07 21:40:35'),(77,'create index IDX_api_key_org_id - v2','CREATE INDEX `IDX_api_key_org_id` ON `api_key` (`org_id`);',1,'','2017-07-07 21:40:35'),(78,'create index UQE_api_key_key - v2','CREATE UNIQUE INDEX `UQE_api_key_key` ON `api_key` (`key`);',1,'','2017-07-07 21:40:36'),(79,'create index UQE_api_key_org_id_name - v2','CREATE UNIQUE INDEX `UQE_api_key_org_id_name` ON `api_key` (`org_id`,`name`);',1,'','2017-07-07 21:40:36'),(80,'copy api_key v1 to v2','INSERT INTO `api_key` (`org_id`\n, `name`\n, `key`\n, `role`\n, `created`\n, `updated`\n, `id`) SELECT `account_id`\n, `name`\n, `key`\n, `role`\n, `created`\n, `updated`\n, `id` FROM `api_key_v1`',1,'','2017-07-07 21:40:36'),(81,'Drop old table api_key_v1','DROP TABLE IF EXISTS `api_key_v1`',1,'','2017-07-07 21:40:36'),(82,'Update api_key table charset','ALTER TABLE `api_key` DEFAULT CHARACTER SET utf8 , MODIFY `name` VARCHAR(190) CHARACTER SET utf8  NOT NULL , MODIFY `key` VARCHAR(190) CHARACTER SET utf8  NOT NULL , MODIFY `role` VARCHAR(255) CHARACTER SET utf8  NOT NULL ;',1,'','2017-07-07 21:40:36'),(83,'create dashboard_snapshot table v4','CREATE TABLE IF NOT EXISTS `dashboard_snapshot` (\n`id` BIGINT(20) PRIMARY KEY AUTO_INCREMENT NOT NULL\n, `name` VARCHAR(255) CHARACTER SET utf8  NOT NULL\n, `key` VARCHAR(190) CHARACTER SET utf8  NOT NULL\n, `dashboard` TEXT CHARACTER SET utf8  NOT NULL\n, `expires` DATETIME NOT NULL\n, `created` DATETIME NOT NULL\n, `updated` DATETIME NOT NULL\n) ENGINE=InnoDB DEFAULT CHARSET utf8 ;',1,'','2017-07-07 21:40:36'),(84,'drop table dashboard_snapshot_v4 #1','DROP TABLE IF EXISTS `dashboard_snapshot`',1,'','2017-07-07 21:40:37'),(85,'create dashboard_snapshot table v5 #2','CREATE TABLE IF NOT EXISTS `dashboard_snapshot` (\n`id` BIGINT(20) PRIMARY KEY AUTO_INCREMENT NOT NULL\n, `name` VARCHAR(255) CHARACTER SET utf8  NOT NULL\n, `key` VARCHAR(190) CHARACTER SET utf8  NOT NULL\n, `delete_key` VARCHAR(190) CHARACTER SET utf8  NOT NULL\n, `org_id` BIGINT(20) NOT NULL\n, `user_id` BIGINT(20) NOT NULL\n, `external` TINYINT(1) NOT NULL\n, `external_url` VARCHAR(255) CHARACTER SET utf8  NOT NULL\n, `dashboard` TEXT CHARACTER SET utf8  NOT NULL\n, `expires` DATETIME NOT NULL\n, `created` DATETIME NOT NULL\n, `updated` DATETIME NOT NULL\n) ENGINE=InnoDB DEFAULT CHARSET utf8 ;',1,'','2017-07-07 21:40:37'),(86,'create index UQE_dashboard_snapshot_key - v5','CREATE UNIQUE INDEX `UQE_dashboard_snapshot_key` ON `dashboard_snapshot` (`key`);',1,'','2017-07-07 21:40:37'),(87,'create index UQE_dashboard_snapshot_delete_key - v5','CREATE UNIQUE INDEX `UQE_dashboard_snapshot_delete_key` ON `dashboard_snapshot` (`delete_key`);',1,'','2017-07-07 21:40:37'),(88,'create index IDX_dashboard_snapshot_user_id - v5','CREATE INDEX `IDX_dashboard_snapshot_user_id` ON `dashboard_snapshot` (`user_id`);',1,'','2017-07-07 21:40:37'),(89,'alter dashboard_snapshot to mediumtext v2','ALTER TABLE dashboard_snapshot MODIFY dashboard MEDIUMTEXT;',1,'','2017-07-07 21:40:37'),(90,'Update dashboard_snapshot table charset','ALTER TABLE `dashboard_snapshot` DEFAULT CHARACTER SET utf8 , MODIFY `name` VARCHAR(255) CHARACTER SET utf8  NOT NULL , MODIFY `key` VARCHAR(190) CHARACTER SET utf8  NOT NULL , MODIFY `delete_key` VARCHAR(190) CHARACTER SET utf8  NOT NULL , MODIFY `external_url` VARCHAR(255) CHARACTER SET utf8  NOT NULL , MODIFY `dashboard` MEDIUMTEXT CHARACTER SET utf8  NOT NULL ;',1,'','2017-07-07 21:40:38'),(91,'create quota table v1','CREATE TABLE IF NOT EXISTS `quota` (\n`id` BIGINT(20) PRIMARY KEY AUTO_INCREMENT NOT NULL\n, `org_id` BIGINT(20) NULL\n, `user_id` BIGINT(20) NULL\n, `target` VARCHAR(190) CHARACTER SET utf8  NOT NULL\n, `limit` BIGINT(20) NOT NULL\n, `created` DATETIME NOT NULL\n, `updated` DATETIME NOT NULL\n) ENGINE=InnoDB DEFAULT CHARSET utf8 ;',1,'','2017-07-07 21:40:38'),(92,'create index UQE_quota_org_id_user_id_target - v1','CREATE UNIQUE INDEX `UQE_quota_org_id_user_id_target` ON `quota` (`org_id`,`user_id`,`target`);',1,'','2017-07-07 21:40:38'),(93,'Update quota table charset','ALTER TABLE `quota` DEFAULT CHARACTER SET utf8 , MODIFY `target` VARCHAR(190) CHARACTER SET utf8  NOT NULL ;',1,'','2017-07-07 21:40:38'),(94,'create plugin_setting table','CREATE TABLE IF NOT EXISTS `plugin_setting` (\n`id` BIGINT(20) PRIMARY KEY AUTO_INCREMENT NOT NULL\n, `org_id` BIGINT(20) NULL\n, `plugin_id` VARCHAR(190) CHARACTER SET utf8  NOT NULL\n, `enabled` TINYINT(1) NOT NULL\n, `pinned` TINYINT(1) NOT NULL\n, `json_data` TEXT CHARACTER SET utf8  NULL\n, `secure_json_data` TEXT CHARACTER SET utf8  NULL\n, `created` DATETIME NOT NULL\n, `updated` DATETIME NOT NULL\n) ENGINE=InnoDB DEFAULT CHARSET utf8 ;',1,'','2017-07-07 21:40:39'),(95,'create index UQE_plugin_setting_org_id_plugin_id - v1','CREATE UNIQUE INDEX `UQE_plugin_setting_org_id_plugin_id` ON `plugin_setting` (`org_id`,`plugin_id`);',1,'','2017-07-07 21:40:39'),(96,'Add column plugin_version to plugin_settings','alter table `plugin_setting` ADD COLUMN `plugin_version` VARCHAR(50) CHARACTER SET utf8  NULL ',1,'','2017-07-07 21:40:39'),(97,'Update plugin_setting table charset','ALTER TABLE `plugin_setting` DEFAULT CHARACTER SET utf8 , MODIFY `plugin_id` VARCHAR(190) CHARACTER SET utf8  NOT NULL , MODIFY `json_data` TEXT CHARACTER SET utf8  NULL , MODIFY `secure_json_data` TEXT CHARACTER SET utf8  NULL , MODIFY `plugin_version` VARCHAR(50) CHARACTER SET utf8  NULL ;',1,'','2017-07-07 21:40:39'),(98,'create session table','CREATE TABLE IF NOT EXISTS `session` (\n`key` CHAR(16) CHARACTER SET utf8  PRIMARY KEY NOT NULL\n, `data` BLOB NOT NULL\n, `expiry` INTEGER(255) NOT NULL\n) ENGINE=InnoDB DEFAULT CHARSET utf8 ;',1,'','2017-07-07 21:40:39'),(99,'Drop old table playlist table','DROP TABLE IF EXISTS `playlist`',1,'','2017-07-07 21:40:40'),(100,'Drop old table playlist_item table','DROP TABLE IF EXISTS `playlist_item`',1,'','2017-07-07 21:40:40'),(101,'create playlist table v2','CREATE TABLE IF NOT EXISTS `playlist` (\n`id` BIGINT(20) PRIMARY KEY AUTO_INCREMENT NOT NULL\n, `name` VARCHAR(255) CHARACTER SET utf8  NOT NULL\n, `interval` VARCHAR(255) CHARACTER SET utf8  NOT NULL\n, `org_id` BIGINT(20) NOT NULL\n) ENGINE=InnoDB DEFAULT CHARSET utf8 ;',1,'','2017-07-07 21:40:40'),(102,'create playlist item table v2','CREATE TABLE IF NOT EXISTS `playlist_item` (\n`id` BIGINT(20) PRIMARY KEY AUTO_INCREMENT NOT NULL\n, `playlist_id` BIGINT(20) NOT NULL\n, `type` VARCHAR(255) CHARACTER SET utf8  NOT NULL\n, `value` TEXT CHARACTER SET utf8  NOT NULL\n, `title` TEXT CHARACTER SET utf8  NOT NULL\n, `order` INT NOT NULL\n) ENGINE=InnoDB DEFAULT CHARSET utf8 ;',1,'','2017-07-07 21:40:40'),(103,'Update playlist table charset','ALTER TABLE `playlist` DEFAULT CHARACTER SET utf8 , MODIFY `name` VARCHAR(255) CHARACTER SET utf8  NOT NULL , MODIFY `interval` VARCHAR(255) CHARACTER SET utf8  NOT NULL ;',1,'','2017-07-07 21:40:40'),(104,'Update playlist_item table charset','ALTER TABLE `playlist_item` DEFAULT CHARACTER SET utf8 , MODIFY `type` VARCHAR(255) CHARACTER SET utf8  NOT NULL , MODIFY `value` TEXT CHARACTER SET utf8  NOT NULL , MODIFY `title` TEXT CHARACTER SET utf8  NOT NULL ;',1,'','2017-07-07 21:40:40'),(105,'drop preferences table v2','DROP TABLE IF EXISTS `preferences`',1,'','2017-07-07 21:40:41'),(106,'drop preferences table v3','DROP TABLE IF EXISTS `preferences`',1,'','2017-07-07 21:40:41'),(107,'create preferences table v3','CREATE TABLE IF NOT EXISTS `preferences` (\n`id` BIGINT(20) PRIMARY KEY AUTO_INCREMENT NOT NULL\n, `org_id` BIGINT(20) NOT NULL\n, `user_id` BIGINT(20) NOT NULL\n, `version` INT NOT NULL\n, `home_dashboard_id` BIGINT(20) NOT NULL\n, `timezone` VARCHAR(50) CHARACTER SET utf8  NOT NULL\n, `theme` VARCHAR(20) CHARACTER SET utf8  NOT NULL\n, `created` DATETIME NOT NULL\n, `updated` DATETIME NOT NULL\n) ENGINE=InnoDB DEFAULT CHARSET utf8 ;',1,'','2017-07-07 21:40:41'),(108,'Update preferences table charset','ALTER TABLE `preferences` DEFAULT CHARACTER SET utf8 , MODIFY `timezone` VARCHAR(50) CHARACTER SET utf8  NOT NULL , MODIFY `theme` VARCHAR(20) CHARACTER SET utf8  NOT NULL ;',1,'','2017-07-07 21:40:41'),(109,'create alert table v1','CREATE TABLE IF NOT EXISTS `alert` (\n`id` BIGINT(20) PRIMARY KEY AUTO_INCREMENT NOT NULL\n, `version` BIGINT(20) NOT NULL\n, `dashboard_id` BIGINT(20) NOT NULL\n, `panel_id` BIGINT(20) NOT NULL\n, `org_id` BIGINT(20) NOT NULL\n, `name` VARCHAR(255) CHARACTER SET utf8  NOT NULL\n, `message` TEXT CHARACTER SET utf8  NOT NULL\n, `state` VARCHAR(190) CHARACTER SET utf8  NOT NULL\n, `settings` TEXT CHARACTER SET utf8  NOT NULL\n, `frequency` BIGINT(20) NOT NULL\n, `handler` BIGINT(20) NOT NULL\n, `severity` TEXT CHARACTER SET utf8  NOT NULL\n, `silenced` TINYINT(1) NOT NULL\n, `execution_error` TEXT CHARACTER SET utf8  NOT NULL\n, `eval_data` TEXT CHARACTER SET utf8  NULL\n, `eval_date` DATETIME NULL\n, `new_state_date` DATETIME NOT NULL\n, `state_changes` INT NOT NULL\n, `created` DATETIME NOT NULL\n, `updated` DATETIME NOT NULL\n) ENGINE=InnoDB DEFAULT CHARSET utf8 ;',1,'','2017-07-07 21:40:41'),(110,'add index alert org_id & id ','CREATE INDEX `IDX_alert_org_id_id` ON `alert` (`org_id`,`id`);',1,'','2017-07-07 21:40:41'),(111,'add index alert state','CREATE INDEX `IDX_alert_state` ON `alert` (`state`);',1,'','2017-07-07 21:40:42'),(112,'add index alert dashboard_id','CREATE INDEX `IDX_alert_dashboard_id` ON `alert` (`dashboard_id`);',1,'','2017-07-07 21:40:42'),(113,'create alert_notification table v1','CREATE TABLE IF NOT EXISTS `alert_notification` (\n`id` BIGINT(20) PRIMARY KEY AUTO_INCREMENT NOT NULL\n, `org_id` BIGINT(20) NOT NULL\n, `name` VARCHAR(190) CHARACTER SET utf8  NOT NULL\n, `type` VARCHAR(255) CHARACTER SET utf8  NOT NULL\n, `settings` TEXT CHARACTER SET utf8  NOT NULL\n, `created` DATETIME NOT NULL\n, `updated` DATETIME NOT NULL\n) ENGINE=InnoDB DEFAULT CHARSET utf8 ;',1,'','2017-07-07 21:40:42'),(114,'Add column is_default','alter table `alert_notification` ADD COLUMN `is_default` TINYINT(1) NOT NULL DEFAULT 0 ',1,'','2017-07-07 21:40:42'),(115,'add index alert_notification org_id & name','CREATE UNIQUE INDEX `UQE_alert_notification_org_id_name` ON `alert_notification` (`org_id`,`name`);',1,'','2017-07-07 21:40:42'),(116,'Update alert table charset','ALTER TABLE `alert` DEFAULT CHARACTER SET utf8 , MODIFY `name` VARCHAR(255) CHARACTER SET utf8  NOT NULL , MODIFY `message` TEXT CHARACTER SET utf8  NOT NULL , MODIFY `state` VARCHAR(190) CHARACTER SET utf8  NOT NULL , MODIFY `settings` TEXT CHARACTER SET utf8  NOT NULL , MODIFY `severity` TEXT CHARACTER SET utf8  NOT NULL , MODIFY `execution_error` TEXT CHARACTER SET utf8  NOT NULL , MODIFY `eval_data` TEXT CHARACTER SET utf8  NULL ;',1,'','2017-07-07 21:40:42'),(117,'Update alert_notification table charset','ALTER TABLE `alert_notification` DEFAULT CHARACTER SET utf8 , MODIFY `name` VARCHAR(190) CHARACTER SET utf8  NOT NULL , MODIFY `type` VARCHAR(255) CHARACTER SET utf8  NOT NULL , MODIFY `settings` TEXT CHARACTER SET utf8  NOT NULL ;',1,'','2017-07-07 21:40:43'),(118,'Drop old annotation table v4','DROP TABLE IF EXISTS `annotation`',1,'','2017-07-07 21:40:43'),(119,'create annotation table v5','CREATE TABLE IF NOT EXISTS `annotation` (\n`id` BIGINT(20) PRIMARY KEY AUTO_INCREMENT NOT NULL\n, `org_id` BIGINT(20) NOT NULL\n, `alert_id` BIGINT(20) NULL\n, `user_id` BIGINT(20) NULL\n, `dashboard_id` BIGINT(20) NULL\n, `panel_id` BIGINT(20) NULL\n, `category_id` BIGINT(20) NULL\n, `type` VARCHAR(25) CHARACTER SET utf8  NOT NULL\n, `title` TEXT CHARACTER SET utf8  NOT NULL\n, `text` TEXT CHARACTER SET utf8  NOT NULL\n, `metric` VARCHAR(255) CHARACTER SET utf8  NULL\n, `prev_state` VARCHAR(25) CHARACTER SET utf8  NOT NULL\n, `new_state` VARCHAR(25) CHARACTER SET utf8  NOT NULL\n, `data` TEXT CHARACTER SET utf8  NOT NULL\n, `epoch` BIGINT(20) NOT NULL\n) ENGINE=InnoDB DEFAULT CHARSET utf8 ;',1,'','2017-07-07 21:40:43'),(120,'add index annotation 0 v3','CREATE INDEX `IDX_annotation_org_id_alert_id` ON `annotation` (`org_id`,`alert_id`);',1,'','2017-07-07 21:40:44'),(121,'add index annotation 1 v3','CREATE INDEX `IDX_annotation_org_id_type` ON `annotation` (`org_id`,`type`);',1,'','2017-07-07 21:40:44'),(122,'add index annotation 2 v3','CREATE INDEX `IDX_annotation_org_id_category_id` ON `annotation` (`org_id`,`category_id`);',1,'','2017-07-07 21:40:44'),(123,'add index annotation 3 v3','CREATE INDEX `IDX_annotation_org_id_dashboard_id_panel_id_epoch` ON `annotation` (`org_id`,`dashboard_id`,`panel_id`,`epoch`);',1,'','2017-07-07 21:40:44'),(124,'add index annotation 4 v3','CREATE INDEX `IDX_annotation_org_id_epoch` ON `annotation` (`org_id`,`epoch`);',1,'','2017-07-07 21:40:44'),(125,'Update annotation table charset','ALTER TABLE `annotation` DEFAULT CHARACTER SET utf8 , MODIFY `type` VARCHAR(25) CHARACTER SET utf8  NOT NULL , MODIFY `title` TEXT CHARACTER SET utf8  NOT NULL , MODIFY `text` TEXT CHARACTER SET utf8  NOT NULL , MODIFY `metric` VARCHAR(255) CHARACTER SET utf8  NULL , MODIFY `prev_state` VARCHAR(25) CHARACTER SET utf8  NOT NULL , MODIFY `new_state` VARCHAR(25) CHARACTER SET utf8  NOT NULL , MODIFY `data` TEXT CHARACTER SET utf8  NOT NULL ;',1,'','2017-07-07 21:40:44'),(126,'Add column region_id to annotation table','alter table `annotation` ADD COLUMN `region_id` BIGINT(20) NULL DEFAULT 0 ',1,'','2017-07-07 21:40:45'),(127,'create test_data table','CREATE TABLE IF NOT EXISTS `test_data` (\n`id` INT PRIMARY KEY AUTO_INCREMENT NOT NULL\n, `metric1` VARCHAR(20) CHARACTER SET utf8  NULL\n, `metric2` VARCHAR(150) CHARACTER SET utf8  NULL\n, `value_big_int` BIGINT(20) NULL\n, `value_double` DOUBLE NULL\n, `value_float` FLOAT NULL\n, `value_int` INT NULL\n, `time_epoch` BIGINT(20) NOT NULL\n, `time_date_time` DATETIME NOT NULL\n, `time_time_stamp` TIMESTAMP NOT NULL\n) ENGINE=InnoDB DEFAULT CHARSET utf8 ;',1,'','2017-07-07 21:40:45');
/*!40000 ALTER TABLE `migration_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `org`
--

DROP TABLE IF EXISTS `org`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `org` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` int(11) NOT NULL,
  `name` varchar(190)  NOT NULL,
  `address1` varchar(255)  DEFAULT NULL,
  `address2` varchar(255)  DEFAULT NULL,
  `city` varchar(255)  DEFAULT NULL,
  `state` varchar(255)  DEFAULT NULL,
  `zip_code` varchar(50)  DEFAULT NULL,
  `country` varchar(255)  DEFAULT NULL,
  `billing_email` varchar(255)  DEFAULT NULL,
  `created` datetime NOT NULL,
  `updated` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UQE_org_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `org`
--

LOCK TABLES `org` WRITE;
/*!40000 ALTER TABLE `org` DISABLE KEYS */;
INSERT INTO `org` VALUES (1,0,'Main Org.','','','','','','',NULL,'2017-07-07 21:40:46','2017-07-07 21:40:46');
/*!40000 ALTER TABLE `org` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `org_user`
--

DROP TABLE IF EXISTS `org_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `org_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `org_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `role` varchar(20)  NOT NULL,
  `created` datetime NOT NULL,
  `updated` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UQE_org_user_org_id_user_id` (`org_id`,`user_id`),
  KEY `IDX_org_user_org_id` (`org_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `org_user`
--

LOCK TABLES `org_user` WRITE;
/*!40000 ALTER TABLE `org_user` DISABLE KEYS */;
INSERT INTO `org_user` VALUES (1,1,1,'Admin','2017-07-07 21:40:46','2017-07-07 21:40:46');
/*!40000 ALTER TABLE `org_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `playlist`
--

DROP TABLE IF EXISTS `playlist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `playlist` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255)  NOT NULL,
  `interval` varchar(255)  NOT NULL,
  `org_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `playlist`
--

LOCK TABLES `playlist` WRITE;
/*!40000 ALTER TABLE `playlist` DISABLE KEYS */;
/*!40000 ALTER TABLE `playlist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `playlist_item`
--

DROP TABLE IF EXISTS `playlist_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `playlist_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `playlist_id` bigint(20) NOT NULL,
  `type` varchar(255)  NOT NULL,
  `value` text  NOT NULL,
  `title` text  NOT NULL,
  `order` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `playlist_item`
--

LOCK TABLES `playlist_item` WRITE;
/*!40000 ALTER TABLE `playlist_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `playlist_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `plugin_setting`
--

DROP TABLE IF EXISTS `plugin_setting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `plugin_setting` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `org_id` bigint(20) DEFAULT NULL,
  `plugin_id` varchar(190)  NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  `pinned` tinyint(1) NOT NULL,
  `json_data` text ,
  `secure_json_data` text ,
  `created` datetime NOT NULL,
  `updated` datetime NOT NULL,
  `plugin_version` varchar(50)  DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UQE_plugin_setting_org_id_plugin_id` (`org_id`,`plugin_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plugin_setting`
--

LOCK TABLES `plugin_setting` WRITE;
/*!40000 ALTER TABLE `plugin_setting` DISABLE KEYS */;
/*!40000 ALTER TABLE `plugin_setting` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `preferences`
--

DROP TABLE IF EXISTS `preferences`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `preferences` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `org_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `version` int(11) NOT NULL,
  `home_dashboard_id` bigint(20) NOT NULL,
  `timezone` varchar(50)  NOT NULL,
  `theme` varchar(20)  NOT NULL,
  `created` datetime NOT NULL,
  `updated` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `preferences`
--

LOCK TABLES `preferences` WRITE;
/*!40000 ALTER TABLE `preferences` DISABLE KEYS */;
/*!40000 ALTER TABLE `preferences` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quota`
--

DROP TABLE IF EXISTS `quota`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `quota` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `org_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `target` varchar(190)  NOT NULL,
  `limit` bigint(20) NOT NULL,
  `created` datetime NOT NULL,
  `updated` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UQE_quota_org_id_user_id_target` (`org_id`,`user_id`,`target`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quota`
--

LOCK TABLES `quota` WRITE;
/*!40000 ALTER TABLE `quota` DISABLE KEYS */;
/*!40000 ALTER TABLE `quota` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `session`
--

DROP TABLE IF EXISTS `session`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `session` (
  `key` char(16)  NOT NULL,
  `data` blob NOT NULL,
  `expiry` int(255) NOT NULL,
  PRIMARY KEY (`key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `session`
--

LOCK TABLES `session` WRITE;
/*!40000 ALTER TABLE `session` DISABLE KEYS */;
/*!40000 ALTER TABLE `session` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `star`
--

DROP TABLE IF EXISTS `star`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `star` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `dashboard_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UQE_star_user_id_dashboard_id` (`user_id`,`dashboard_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `star`
--

LOCK TABLES `star` WRITE;
/*!40000 ALTER TABLE `star` DISABLE KEYS */;
/*!40000 ALTER TABLE `star` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `temp_user`
--

DROP TABLE IF EXISTS `temp_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `temp_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `org_id` bigint(20) NOT NULL,
  `version` int(11) NOT NULL,
  `email` varchar(190)  NOT NULL,
  `name` varchar(255)  DEFAULT NULL,
  `role` varchar(20)  DEFAULT NULL,
  `code` varchar(190)  NOT NULL,
  `status` varchar(20)  NOT NULL,
  `invited_by_user_id` bigint(20) DEFAULT NULL,
  `email_sent` tinyint(1) NOT NULL,
  `email_sent_on` datetime DEFAULT NULL,
  `remote_addr` varchar(255)  DEFAULT NULL,
  `created` datetime NOT NULL,
  `updated` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_temp_user_email` (`email`),
  KEY `IDX_temp_user_org_id` (`org_id`),
  KEY `IDX_temp_user_code` (`code`),
  KEY `IDX_temp_user_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `temp_user`
--

LOCK TABLES `temp_user` WRITE;
/*!40000 ALTER TABLE `temp_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `temp_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `test_data`
--

DROP TABLE IF EXISTS `test_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `test_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `metric1` varchar(20)  DEFAULT NULL,
  `metric2` varchar(150)  DEFAULT NULL,
  `value_big_int` bigint(20) DEFAULT NULL,
  `value_double` double DEFAULT NULL,
  `value_float` float DEFAULT NULL,
  `value_int` int(11) DEFAULT NULL,
  `time_epoch` bigint(20) NOT NULL,
  `time_date_time` datetime NOT NULL,
  `time_time_stamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test_data`
--

LOCK TABLES `test_data` WRITE;
/*!40000 ALTER TABLE `test_data` DISABLE KEYS */;
/*!40000 ALTER TABLE `test_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` int(11) NOT NULL,
  `login` varchar(190)  NOT NULL,
  `email` varchar(190)  NOT NULL,
  `name` varchar(255)  DEFAULT NULL,
  `password` varchar(255)  DEFAULT NULL,
  `salt` varchar(50)  DEFAULT NULL,
  `rands` varchar(50)  DEFAULT NULL,
  `company` varchar(255)  DEFAULT NULL,
  `org_id` bigint(20) NOT NULL,
  `is_admin` tinyint(1) NOT NULL,
  `email_verified` tinyint(1) DEFAULT NULL,
  `theme` varchar(255)  DEFAULT NULL,
  `created` datetime NOT NULL,
  `updated` datetime NOT NULL,
  `help_flags1` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UQE_user_login` (`login`),
  UNIQUE KEY `UQE_user_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,0,'admin','admin@localhost','','dbaa045ae19f377fe989674bbb5a5b5df27c15aa763b1b093f6ce2c3b0f45bd984c8951669988862fddc41d64412d59b5001','ftcDOzL8qL','GmjtgZbbaX','',1,1,0,'','2017-07-07 21:40:46','2017-07-07 21:40:46',0);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-07-08 17:26:47
