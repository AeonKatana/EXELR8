-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: localhost    Database: schedulingdb2
-- ------------------------------------------------------
-- Server version	8.0.27

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
-- Table structure for table `activity_log`
--

DROP TABLE IF EXISTS `activity_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `activity_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `action` varchar(255) DEFAULT NULL,
  `date` datetime(6) DEFAULT NULL,
  `target` varchar(255) DEFAULT NULL,
  `targetlink` varchar(255) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3amb1k7y7rfb2k7sp4pyvhq7b` (`user_id`),
  CONSTRAINT `FK3amb1k7y7rfb2k7sp4pyvhq7b` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=165 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_log`
--

LOCK TABLES `activity_log` WRITE;
/*!40000 ALTER TABLE `activity_log` DISABLE KEYS */;
INSERT INTO `activity_log` VALUES (120,' has completed a task but late','2022-05-22 10:36:40.023553','Test 1 Test','/department/7/trash',3),(121,'has assigned a daily task for Aeon Katana','2022-05-22 10:37:49.782177','Test 1 Test','#',1),(122,'has assigned a daily task for Aeon Katana','2022-05-22 10:38:08.453302','Test 1 ad','#',1),(123,'has assigned a daily task for Aeon Katana','2022-05-22 10:40:47.871043','Test 1 Mobile','#',1),(124,' has completed a task on time','2022-05-22 10:41:21.019808','Test 1 Test','/department/7/trash',3),(125,' has completed a task on time','2022-05-22 10:41:49.953203','Test 1 ad','/department/7/trash',3),(126,' has completed a task on time','2022-05-22 10:41:58.494903','Test 1 Mobile','/department/7/trash',3),(127,' has completed a task on time','2022-05-22 10:42:07.174535','Eat 1 Create','/department/7/trash',3),(128,'has created a company','2022-05-22 10:56:34.848121','Pizza','#',1),(129,'has created a user','2022-05-22 10:56:34.848121','John Wick','#',7),(130,'has assigned a daily task for Aeon Katana','2022-05-22 11:01:05.689735','Test 1 Notification','#',1),(131,'has created a daily task','2022-05-22 11:46:57.500572','Visit 1 House','/department/7',3),(132,'has deleted a task temporarily','2022-05-22 18:46:12.723845','Visit 1 House','/department/7/trash',1),(133,'has undid a task deletion','2022-05-22 18:46:14.286516','Visit 1 House','/department/7',1),(134,'has edited a task','2022-05-22 18:46:22.147832','Visit 1 Houses','/department/7',1),(135,'has undid a task completion','2022-05-22 18:46:34.933637','Eat 1 Buy','/department/7',1),(136,' has completed a task on time','2022-05-22 18:46:46.880736','Eat 1 Buy','/department/7/trash',1),(137,'has undid a task completion','2022-05-22 18:46:48.603096','Eat 1 Buy','/department/7',1),(138,'has assigned a daily task for Mark Zuckerberg','2022-05-22 18:47:29.342682','Test 1 facebook','#',1),(139,' has completed a task but late','2022-05-22 18:50:20.573863','Test 1 Notification','/department/7/trash',3),(140,' has completed a task on time','2022-05-22 18:51:12.096648','Visit 1 Houses','/department/7/trash',3),(141,' has completed a task on time','2022-05-22 18:53:02.687248','Eat 1 Buy','/department/7/trash',3),(142,'has assigned a daily task for Aeon Katana','2022-05-22 19:08:28.817140','Test 1 record','#',1),(143,'has assigned a daily task for Mark Zuckerberg','2022-05-22 19:08:28.826024','Test 1 record','#',1),(144,' has completed a task on time','2022-05-22 19:08:53.075608','Test 1 record','/department/7/trash',1),(145,'has undid a task completion','2022-05-22 19:08:54.789722','Test 1 record','/department/7',1),(146,' has completed a task on time','2022-05-22 19:09:05.395333','Test 1 record','/department/7/trash',1),(147,'has deleted a task temporarily','2022-05-22 19:09:24.158617','Test 1 facebook','/department/7/trash',1),(148,'has undid a task deletion','2022-05-22 19:09:34.966978','Test 1 facebook','/department/7',1),(149,' has completed a task on time','2022-05-22 19:12:38.088767','Test 1 record','/department/7/trash',3),(150,'has undid a task completion','2022-05-22 19:12:40.285535','Test 1 record','/department/7',3),(151,' has completed a task on time','2022-05-22 19:12:42.256747','Test 1 record','/department/7/trash',3),(152,'has assigned a daily task for Aeon Katana','2022-05-22 19:14:12.232711','Test 1 Notification','#',1),(153,' has completed a task on time','2022-05-22 19:14:38.749618','Test 1 Notification','/department/7/trash',3),(154,'has created a daily task','2022-05-23 10:57:39.743132','Test 1 lazy','/department/7',3),(155,' has completed a task on time','2022-05-23 10:57:50.352245','Test 1 lazy','/department/7/trash',3),(156,' has completed a task on time','2022-05-23 11:05:44.369323','Test 1 facebook','/department/7/trash',2),(157,'has assigned a daily task for Mark Zuckerberg','2022-05-23 11:13:10.370515','test 1 task','#',1),(158,'has assigned a daily task for Mark Zuckerberg','2022-05-30 09:02:50.653292','Test 1 overdue','#',1),(159,'has undid a task completion','2022-05-31 13:32:27.869365','Test 1 Mobile','/department/7',1),(160,'has deleted a task temporarily','2022-05-31 13:32:57.619815','Test 1 overdue','/department/7/trash',1),(161,'has undid a task deletion','2022-06-01 10:22:44.627418','Test 1 overdue','/department/7',1),(162,'has deleted a task temporarily','2022-06-01 10:23:56.349345','Test 1 overdue','/department/7/trash',1),(163,'has undid a task deletion','2022-06-01 10:23:57.557499','Test 1 overdue','/department/7',1),(164,'has deleted a task temporarily','2022-06-01 10:30:46.671219','Test 1 Mobile','/department/7/trash',1);
/*!40000 ALTER TABLE `activity_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `company`
--

DROP TABLE IF EXISTS `company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `company` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `color` varchar(255) DEFAULT NULL,
  `compname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `company`
--

LOCK TABLES `company` WRITE;
/*!40000 ALTER TABLE `company` DISABLE KEYS */;
INSERT INTO `company` VALUES (1,'#007bff','Facebook'),(2,'#03b6e2','Twitter'),(3,'#aeb120','Pizza');
/*!40000 ALTER TABLE `company` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `company_dna`
--

DROP TABLE IF EXISTS `company_dna`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `company_dna` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `history` varchar(255) DEFAULT NULL,
  `company_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKi18vb90n48kwtqed7osqjvgtx` (`company_id`),
  CONSTRAINT `FKi18vb90n48kwtqed7osqjvgtx` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `company_dna`
--

LOCK TABLES `company_dna` WRITE;
/*!40000 ALTER TABLE `company_dna` DISABLE KEYS */;
INSERT INTO `company_dna` VALUES (1,'今は何もできなっかたdaadsfds',1),(2,'This company has no purpose yet',2),(3,'hdakljfakldjflasjdfasd',3);
/*!40000 ALTER TABLE `company_dna` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `core_value`
--

DROP TABLE IF EXISTS `core_value`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `core_value` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `dna_id` bigint DEFAULT NULL,
  `indicator` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKkm9vw098xwnqjtp5aohw2gpfp` (`dna_id`),
  CONSTRAINT `FKkm9vw098xwnqjtp5aohw2gpfp` FOREIGN KEY (`dna_id`) REFERENCES `company_dna` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `core_value`
--

LOCK TABLES `core_value` WRITE;
/*!40000 ALTER TABLE `core_value` DISABLE KEYS */;
INSERT INTO `core_value` VALUES (5,'Diligency blah blah','Diligency',1,'Muhay'),(6,'adfadsf','Integrity',1,'adsfdsf'),(7,'Yeah','Twitter',2,'Twitter go brrrr'),(8,'breathe in breathe out','Heart',3,'Water breathing'),(9,'breathe in breathe out','Mind',3,'Water breathing'),(10,'djalk','jaldkjs',1,'dkfjadlfj'),(11,'ABCDE','HONESTY',1,'ABCDE'),(12,'adfd','adfd',1,'adfd'),(13,'sdd','dsdsf',1,'sadfdsf'),(14,'GHJKL','TRANSPARENCY',1,'ABCDD');
/*!40000 ALTER TABLE `core_value` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `daily_task`
--

DROP TABLE IF EXISTS `daily_task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `daily_task` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `done` bit(1) NOT NULL,
  `note` varchar(255) DEFAULT NULL,
  `recurring` bit(1) NOT NULL,
  `starteddate` datetime(6) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `until` date DEFAULT NULL,
  `assignedby_id` bigint DEFAULT NULL,
  `department_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKoblatd7odc9w98xj0n3wum2na` (`assignedby_id`),
  KEY `FKpo4trk3tsijoir14on2w6qkw8` (`department_id`),
  KEY `FKns722uvvsqtqqrt0019mdr4s7` (`user_id`),
  CONSTRAINT `FKns722uvvsqtqqrt0019mdr4s7` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKoblatd7odc9w98xj0n3wum2na` FOREIGN KEY (`assignedby_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKpo4trk3tsijoir14on2w6qkw8` FOREIGN KEY (`department_id`) REFERENCES `department` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `daily_task`
--

LOCK TABLES `daily_task` WRITE;
/*!40000 ALTER TABLE `daily_task` DISABLE KEYS */;
INSERT INTO `daily_task` VALUES (43,'Test 1 Test',_binary '','No Notes',_binary '\0','2022-05-21 08:44:41.812101',NULL,'2022-05-20',1,7,3,_binary '\0'),(44,'Eat 1 Create',_binary '','No Notes',_binary '\0','2022-05-21 08:50:31.075614',NULL,'2022-05-22',1,7,3,_binary '\0'),(45,'Eat 1 Buy',_binary '','No Notes',_binary '\0','2022-05-21 09:05:22.766149',NULL,'2022-05-22',1,7,3,_binary '\0'),(46,'Test 1 Test',_binary '','No Notes',_binary '\0','2022-05-22 10:37:49.773200',NULL,'2022-05-23',1,7,3,_binary '\0'),(47,'Test 1 ad',_binary '','No Notes',_binary '\0','2022-05-22 10:38:08.446321',NULL,'2022-05-23',1,7,3,_binary '\0'),(48,'Test 1 Mobile',_binary '\0','No Notes',_binary '\0','2022-05-22 10:40:47.862255',NULL,'2022-05-23',1,7,3,_binary ''),(49,'Test 1 Notification',_binary '','No Notes',_binary '\0','2022-05-22 11:01:05.680952',NULL,'2022-05-21',1,7,3,_binary '\0'),(50,'Visit 1 Houses',_binary '','No Notes',_binary '\0','2022-05-22 18:46:22.147832',NULL,'2022-05-23',3,7,3,_binary '\0'),(51,'Test 1 facebook',_binary '','No Notes',_binary '\0','2022-05-22 18:47:29.328720',NULL,'2022-05-23',1,7,2,_binary '\0'),(52,'Test 1 record',_binary '','No Notes',_binary '\0','2022-05-22 19:08:28.803164',NULL,'2022-05-23',1,7,3,_binary '\0'),(53,'Test 1 record',_binary '','No Notes',_binary '\0','2022-05-22 19:08:28.813146',NULL,'2022-05-23',1,7,2,_binary '\0'),(54,'Test 1 Notification',_binary '','No Notes',_binary '\0','2022-05-22 19:14:12.227697',NULL,'2022-05-23',1,7,3,_binary '\0'),(55,'Test 1 lazy',_binary '','No Notes',_binary '\0','2022-05-23 10:57:39.711244',NULL,'2022-05-24',3,7,3,_binary '\0'),(57,'Test 1 overdue',_binary '\0','No Notes',_binary '\0','2022-05-30 09:02:50.620128',NULL,'2022-05-29',1,7,2,_binary '\0');
/*!40000 ALTER TABLE `daily_task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `department`
--

DROP TABLE IF EXISTS `department`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `department` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `deptname` varchar(255) DEFAULT NULL,
  `company_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKh1m88q0f7sc0mk76kju4kcn6f` (`company_id`),
  CONSTRAINT `FKh1m88q0f7sc0mk76kju4kcn6f` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `department`
--

LOCK TABLES `department` WRITE;
/*!40000 ALTER TABLE `department` DISABLE KEYS */;
INSERT INTO `department` VALUES (7,'Marketing Department',1);
/*!40000 ALTER TABLE `department` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notification`
--

DROP TABLE IF EXISTS `notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notification` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `action` varchar(255) DEFAULT NULL,
  `actiontarget` varchar(255) DEFAULT NULL,
  `date` datetime(6) DEFAULT NULL,
  `targetlink` varchar(255) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `seen` bit(1) NOT NULL,
  `actionuser_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKb0yvoep4h4k92ipon31wmdf7e` (`user_id`),
  KEY `FKhw6iup5xbsvxewfn18thbu3pw` (`actionuser_id`),
  CONSTRAINT `FKb0yvoep4h4k92ipon31wmdf7e` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKhw6iup5xbsvxewfn18thbu3pw` FOREIGN KEY (`actionuser_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=109 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notification`
--

LOCK TABLES `notification` WRITE;
/*!40000 ALTER TABLE `notification` DISABLE KEYS */;
INSERT INTO `notification` VALUES (76,'has assigned you a daily task','Eat 1 Buy','2022-05-21 09:05:22.797065','/department/7',3,_binary '',1),(77,'has completed a daily task you\'ve assigned','Eat 1 Buy','2022-05-21 09:15:32.804847','/department/7/trash',1,_binary '',3),(78,'has completed a daily task you\'ve assigned','Test 1 Test','2022-05-22 10:36:40.111351','/department/7/trash',1,_binary '',3),(79,'has assigned you a daily task','Test 1 Test','2022-05-22 10:37:49.777188','/department/7',3,_binary '',1),(80,'has assigned you a daily task','Test 1 ad','2022-05-22 10:38:08.451307','/department/7',3,_binary '',1),(81,'has assigned you a daily task','Test 1 Mobile','2022-05-22 10:40:47.867140','/department/7',3,_binary '',1),(82,'has completed a daily task you\'ve assigned','Test 1 Test','2022-05-22 10:41:21.032495','/department/7/trash',1,_binary '',3),(83,'has completed a daily task you\'ve assigned','Test 1 ad','2022-05-22 10:41:49.965890','/department/7/trash',1,_binary '',3),(84,'has completed a daily task you\'ve assigned','Test 1 Mobile','2022-05-22 10:41:58.506614','/department/7/trash',1,_binary '',3),(85,'has completed a daily task you\'ve assigned','Eat 1 Create','2022-05-22 10:42:07.184294','/department/7/trash',1,_binary '',3),(86,'has assigned you a daily task','Test 1 Notification','2022-05-22 11:01:05.687784','/department/7',3,_binary '',1),(87,'has assigned you a daily task','Test 1 facebook','2022-05-22 18:47:29.332816','/department/7',2,_binary '',1),(88,'has completed a daily task you\'ve assigned','Test 1 Notification','2022-05-22 18:50:20.592644','/department/7/trash',1,_binary '',3),(89,'has completed a daily task you\'ve assigned','Eat 1 Buy','2022-05-22 18:53:02.700739','/department/7/trash',1,_binary '',3),(90,'has assigned you a daily task','Test 1 record','2022-05-22 19:08:28.811101','/department/7',3,_binary '',1),(91,'has assigned you a daily task','Test 1 record','2022-05-22 19:08:28.815630','/department/7',2,_binary '',1),(92,'has completed a daily task you\'ve assigned','Test 1 record','2022-05-22 19:12:38.104579','/department/7/trash',1,_binary '',3),(93,'has completed a daily task you\'ve assigned','Test 1 record','2022-05-22 19:12:42.265723','/department/7/trash',1,_binary '',3),(94,'has assigned you a daily task','Test 1 Notification','2022-05-22 19:14:12.231171','/department/7',3,_binary '',1),(95,'has completed a daily task you\'ve assigned','Test 1 Notification','2022-05-22 19:14:38.763485','/department/7/trash',1,_binary '',3),(96,'has completed a daily task you\'ve assigned','Test 1 facebook','2022-05-23 11:05:44.387722','/department/7/trash',1,_binary '',2),(97,'has assigned you a daily task','test 1 task','2022-05-23 11:13:10.361022','/department/15',2,_binary '',1),(98,'has kicked you from the department','Marketing Department','2022-05-24 10:05:57.304191','/department/7',3,_binary '',1),(99,'has kicked you from the department','Marketing Department','2022-05-24 10:25:23.770290','/department/7',3,_binary '',1),(100,'has kicked you from the department','Marketing Department','2022-05-24 10:26:27.436047','/department/7',3,_binary '',1),(101,'has kicked you from the department','Marketing Department','2022-05-24 10:28:09.887993','/department/7',3,_binary '',1),(102,'has kicked you from the department','Marketing Department','2022-05-24 10:30:11.026751','/department/7',3,_binary '',1),(103,'has kicked you from the department','Marketing Department','2022-05-24 10:33:04.227159','/department/7',3,_binary '',1),(104,'has kicked you from the department','Marketing Department','2022-05-24 10:35:48.139591','/department/7',3,_binary '',1),(105,'has kicked you from the department','Marketing Department','2022-05-24 10:38:05.421252','/department/7',3,_binary '',1),(106,'has kicked you from the department','Marketing Department','2022-05-24 10:59:11.912963','/department/7',3,_binary '',1),(107,'has kicked you from the department','Marketing Department','2022-05-24 11:01:35.157794','/department/7',3,_binary '',1),(108,'has assigned you a daily task','Test 1 overdue','2022-05-30 09:02:50.644769','/department/7',2,_binary '\0',1);
/*!40000 ALTER TABLE `notification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `password_token`
--

DROP TABLE IF EXISTS `password_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `password_token` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `token` varchar(255) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKm1ug9u624tahdket1qqn0s9cg` (`user_id`),
  CONSTRAINT `FKm1ug9u624tahdket1qqn0s9cg` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `password_token`
--

LOCK TABLES `password_token` WRITE;
/*!40000 ALTER TABLE `password_token` DISABLE KEYS */;
INSERT INTO `password_token` VALUES (1,'8175ec16-b116-4e58-95a9-5020ad59b085',2);
/*!40000 ALTER TABLE `password_token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `registration_token`
--

DROP TABLE IF EXISTS `registration_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `registration_token` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `token` varchar(255) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKcrd9sxl870jgkncp327q0p2f5` (`user_id`),
  CONSTRAINT `FKcrd9sxl870jgkncp327q0p2f5` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `registration_token`
--

LOCK TABLES `registration_token` WRITE;
/*!40000 ALTER TABLE `registration_token` DISABLE KEYS */;
INSERT INTO `registration_token` VALUES (1,'db869944-77c5-4361-a10f-33a3205ca3f7',2),(2,'89dab7a8-e242-4b31-a8ae-96c86a57bd45',3),(3,'a0b635bc-21ba-4db7-82d5-b144b5745bc1',4),(4,'b6698080-7cc4-4a38-8918-c3f6aaae75f4',5),(5,'deb1f225-04ed-481e-ba1b-cbf66198e0d1',6),(6,'d884508d-b8dc-497e-8787-89f6f038dab8',7);
/*!40000 ALTER TABLE `registration_token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `rolename` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'SUPERADMIN'),(2,'MASTERADMIN'),(3,'EXECUTIVE'),(4,'MANAGER'),(5,'PROJLEAD'),(6,'SUPERVISOR'),(7,'ASSOCIATE');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `scorecard`
--

DROP TABLE IF EXISTS `scorecard`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `scorecard` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `corecompetencies` varchar(255) DEFAULT NULL,
  `definition` varchar(255) DEFAULT NULL,
  `educationalbg` varchar(255) DEFAULT NULL,
  `indicators` varchar(255) DEFAULT NULL,
  `mainscorecard` varchar(255) DEFAULT NULL,
  `metrics` varchar(255) DEFAULT NULL,
  `perforaccel` varchar(255) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `customer` varchar(255) DEFAULT NULL,
  `roledesc` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5yedng6a1gyq8xjl7hr4yumet` (`user_id`),
  CONSTRAINT `FK5yedng6a1gyq8xjl7hr4yumet` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `scorecard`
--

LOCK TABLES `scorecard` WRITE;
/*!40000 ALTER TABLE `scorecard` DISABLE KEYS */;
INSERT INTO `scorecard` VALUES (7,'sdl;jfsdlfj','asdljflsdaj','asdsl;kfjalsd;fj','lasjdf;adsf','Attak enemies','asdlkfjasldkjf','blah blah blah blah',2,NULL,'Provide backup'),(8,'','','','','','','',3,NULL,'Help people');
/*!40000 ALTER TABLE `scorecard` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `contactno` bigint NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `enabled` bit(1) NOT NULL,
  `firstname` varchar(255) DEFAULT NULL,
  `lastname` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `violationcount` bigint NOT NULL,
  `company_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK2yuxsfrkkrnkn5emoobcnnc3r` (`company_id`),
  CONSTRAINT `FK2yuxsfrkkrnkn5emoobcnnc3r` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,639564412627,'rean@gmail.com',_binary '','Rean','Schwarzer','$2a$10$R3v/rd4r2B6SttkABobTvOqX2p.4iWNpoxMBaD4zRtM0suPMoa8B.',17,NULL),(2,639564412627,'arantemiguel21@gmail.com',_binary '','Mark','Zuckerberg','$2a$10$XpYWKedD1RJERe4FI2Ys9uNTVFrxuPcmwZOAyXRDY7JUVomK9CcHC',16,1),(3,639564412627,'aeonkatana@gmail.com',_binary '','Aeon','Katana','$2a$10$5DlwaXurwSvBKj8G9kP4TeoQ4KQcfO03jmZTS9JUjos74RfSTUZH.',17,1),(4,639564412627,'juanmiguelarante21@gmail.com',_binary '\0','Ash','Ketchum',NULL,29,1),(5,639564412627,'lyagami@gmail.com',_binary '\0','Light','Yagami',NULL,29,1),(6,639564412627,'thisisonlytet@gmail.com',_binary '\0','Elon','Musk',NULL,27,2),(7,639564412627,'abcde@gmail.com',_binary '\0','John','Wick',NULL,14,3);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_department`
--

DROP TABLE IF EXISTS `user_department`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_department` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `deptrole` varchar(255) DEFAULT NULL,
  `department_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKh9wprwvoo5il7qqahjc7hgcax` (`department_id`),
  KEY `FK1alh47saqbwnimxd9o564o4vm` (`user_id`),
  CONSTRAINT `FK1alh47saqbwnimxd9o564o4vm` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKh9wprwvoo5il7qqahjc7hgcax` FOREIGN KEY (`department_id`) REFERENCES `department` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_department`
--

LOCK TABLES `user_department` WRITE;
/*!40000 ALTER TABLE `user_department` DISABLE KEYS */;
INSERT INTO `user_department` VALUES (19,'MEMBER',7,2),(31,'MEMBER',7,3),(32,'MEMBER',7,4);
/*!40000 ALTER TABLE `user_department` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `role_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKa68196081fvovjhkek5m97n3y` (`role_id`),
  KEY `FK859n2jvi8ivhui0rl0esws6o` (`user_id`),
  CONSTRAINT `FK859n2jvi8ivhui0rl0esws6o` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKa68196081fvovjhkek5m97n3y` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (1,1,1),(2,3,2),(3,3,3),(4,6,4),(5,7,5),(6,2,6),(7,2,7);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-06-03 15:52:25
