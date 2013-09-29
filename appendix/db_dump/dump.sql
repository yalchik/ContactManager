-- MySQL dump 10.13  Distrib 5.5.16, for Win32 (x86)
--
-- Host: localhost    Database: contactsdb
-- ------------------------------------------------------
-- Server version	5.5.29

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
-- Table structure for table `attachments`
--

DROP TABLE IF EXISTS `attachments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `attachments` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `path` varchar(255) DEFAULT NULL,
  `comment` text,
  `date` date DEFAULT NULL,
  `contact_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `contact_id` (`contact_id`),
  CONSTRAINT `attachments_ibfk_1` FOREIGN KEY (`contact_id`) REFERENCES `contacts` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attachments`
--

LOCK TABLES `attachments` WRITE;
/*!40000 ALTER TABLE `attachments` DISABLE KEYS */;
INSERT INTO `attachments` VALUES (4,'pic','uploads/attachment?name=upload-8150539846121167655pic.zip','картинка в архиве','2013-08-05',1);
/*!40000 ALTER TABLE `attachments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contacts`
--

DROP TABLE IF EXISTS `contacts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contacts` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `firstname` varchar(45) DEFAULT NULL,
  `lastname` varchar(45) DEFAULT NULL,
  `midname` varchar(45) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `sex` enum('unknown','male','female') DEFAULT NULL,
  `citizenship` varchar(45) DEFAULT NULL,
  `marital_status` enum('unknown','single','married') DEFAULT NULL,
  `website` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `job` varchar(45) DEFAULT NULL,
  `country` varchar(45) DEFAULT NULL,
  `city` varchar(45) DEFAULT NULL,
  `street` varchar(45) DEFAULT NULL,
  `house` int(10) unsigned DEFAULT NULL,
  `apartment` int(10) unsigned DEFAULT NULL,
  `postcode` int(10) unsigned DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contacts`
--

LOCK TABLES `contacts` WRITE;
/*!40000 ALTER TABLE `contacts` DISABLE KEYS */;
INSERT INTO `contacts` VALUES (1,'Илья','Яльчик','Николаевич','1993-03-13','male','белорусское','unknown','','yalchik.ilya@gmail.com','','Беларусь','Минск','',11,85,0,'images\\silhouette.png'),(2,'Зоя','Яльчик','Иосифовна','1966-11-12','female','белорусское','unknown','','yalchik.zoya@gmail.com','ВС РБ','Беларусь','Поставы','',17,19,0,'images\\silhouette.png'),(3,'Николай','Яльчик','Николаевич','1966-12-23','male','белорусское','unknown','','yalchik.nickolay@gmail.com','ВС РБ','Беларусь','Поставы','',17,19,0,'images\\silhouette.png'),(6,'Владимир','Голенков','Васильевич','1950-11-01','male','белорусское','unknown','','golenkov@gmail.com','БГУИР','Беларусь','Минск','',0,0,0,NULL),(7,'Валерьян','Ивашенко','Петрович','1960-11-11','male','белорусское','unknown','','ivashenko@gmail.com','БГУИР','Беларусь','Минск','',0,0,0,NULL),(8,'Дмитрий','Лазуркин','Александрович','1987-08-05','male','белорусское','unknown','','lazurkin@gmail.com','БГУИР','Беларусь','Минск','',0,0,0,'images\\silhouette.png'),(9,'Константин','Уваров','Александрович',NULL,'male','белорусское','unknown','','uvarov@gmail.com','БГУИР','Беларусь','Минск','',0,0,0,NULL),(10,'Ирина','Давыденко','Тимофеевна','1985-08-05','female','','unknown','','','','','','',0,0,0,'images\\silhouette.png'),(11,'Наталья','Гулякина','Анатольевна','1950-08-06','female','','unknown','','gulakina@gmail.com','','','','',0,0,0,'images\\silhouette.png'),(14,'Дмитрий','Колб','Григорьевич','1970-08-07','male','белорусское','unknown','','kolb@gmail.com','БГУИР','Беларусь','Минск','',0,0,0,'images\\silhouette.png'),(15,'Роман','Сердюков','Евгеньевич','1960-08-07','male','белорусское','unknown','','serdukov@gmail.com','БГУИР','Беларусь','Минск','',0,0,0,'images\\silhouette.png'),(16,'Иван','Жуков','Иванович','1980-08-06','male','белорусское','unknown','','zukov@qwe.ru','БГУИР','Беларусь','Минск','',0,0,0,'images\\silhouette.png');
/*!40000 ALTER TABLE `contacts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `phones`
--

DROP TABLE IF EXISTS `phones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `phones` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `code1` varchar(5) DEFAULT NULL,
  `code2` varchar(7) DEFAULT NULL,
  `phoneNumber` varchar(10) DEFAULT NULL,
  `phoneType` enum('mobile','home') DEFAULT NULL,
  `comment` text,
  `contact_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `contact_id` (`contact_id`),
  CONSTRAINT `phones_ibfk_1` FOREIGN KEY (`contact_id`) REFERENCES `contacts` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `phones`
--

LOCK TABLES `phones` WRITE;
/*!40000 ALTER TABLE `phones` DISABLE KEYS */;
INSERT INTO `phones` VALUES (1,'375','29','2119680','mobile','мой номер мобильного телефона',1),(2,'8','02155','46318','home','домашний номер',1),(4,'375','29','7161683','mobile','',2),(5,'8','02155','46318','home','звонить после 7 вечера',2),(6,'375','29','5935578','mobile','',3),(7,'8','02155','46318','home','',3);
/*!40000 ALTER TABLE `phones` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2013-08-05 13:02:24
