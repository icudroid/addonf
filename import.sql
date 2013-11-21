CREATE DATABASE  IF NOT EXISTS `addonf` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `addonf`;
-- MySQL dump 10.13  Distrib 5.5.34, for debian-linux-gnu (x86_64)
--
-- Host: 127.0.0.1    Database: addonf
-- ------------------------------------------------------
-- Server version	5.5.34-0ubuntu0.13.10.1

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
-- Table structure for table `ad`
--

DROP TABLE IF EXISTS `ad`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ad` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `type` int(11) DEFAULT NULL,
  `video` varchar(255) DEFAULT NULL,
  `brand_id` bigint(20) DEFAULT NULL,
  `product_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_eb92b69e606d4520bba76874da0` (`brand_id`),
  KEY `FK_e114940a795442b7978fabd8040` (`product_id`),
  KEY `FK_4effe185d85f45d7ac3b4ab7a25` (`brand_id`),
  KEY `FK_46919413813b433c8675a78963b` (`product_id`),
  KEY `FK_87dc6bd8ec7247758e1a6b781da` (`brand_id`),
  KEY `FK_16fcdf09ce024e9e896fae7ab31` (`product_id`),
  KEY `FK_7059e018860e49878afa8506dcc` (`brand_id`),
  KEY `FK_26ed42044cd24fdda9606218892` (`product_id`),
  KEY `FK_a8927d4d14984a56b7fc7e661ac` (`brand_id`),
  KEY `FK_f5b25c521626472eb7f876549b7` (`product_id`),
  KEY `FK_2b39de9e692d499fa463021ae34` (`brand_id`),
  KEY `FK_6ed3097b7d784492a3dd480cfd2` (`product_id`),
  KEY `FK_e415d1ec50114454a6bf41b7df7` (`brand_id`),
  KEY `FK_209a7c83dcbd491eb0aa60a1c42` (`product_id`),
  KEY `FK_4aabdf78802a4f2aa21d806e450` (`brand_id`),
  KEY `FK_af34bd0d38f34255a673023997f` (`product_id`),
  KEY `FK_7970069a887e42f1a97bea6afe4` (`brand_id`),
  KEY `FK_3b40b6af04434b76a5f7050748a` (`product_id`),
  KEY `FK_eadea3ff3d9449e7bfe34ed993e` (`brand_id`),
  KEY `FK_b6fb8dd339b54eb586e5604ee5f` (`product_id`),
  KEY `FK_3a21ecc05fab43f5afb2f612933` (`brand_id`),
  KEY `FK_e436a72d96b84cd39948c1f5c8e` (`product_id`),
  KEY `FK_a00c5f2007af4a5f9f9b91b6d27` (`brand_id`),
  KEY `FK_14d9822708134adba84bc6dc18b` (`product_id`),
  CONSTRAINT `FK_14d9822708134adba84bc6dc18b` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `FK_16fcdf09ce024e9e896fae7ab31` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `FK_209a7c83dcbd491eb0aa60a1c42` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `FK_26ed42044cd24fdda9606218892` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `FK_2b39de9e692d499fa463021ae34` FOREIGN KEY (`brand_id`) REFERENCES `brand` (`id`),
  CONSTRAINT `FK_3b40b6af04434b76a5f7050748a` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `FK_46919413813b433c8675a78963b` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `FK_4aabdf78802a4f2aa21d806e450` FOREIGN KEY (`brand_id`) REFERENCES `brand` (`id`),
  CONSTRAINT `FK_4effe185d85f45d7ac3b4ab7a25` FOREIGN KEY (`brand_id`) REFERENCES `brand` (`id`),
  CONSTRAINT `FK_6ed3097b7d784492a3dd480cfd2` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `FK_7059e018860e49878afa8506dcc` FOREIGN KEY (`brand_id`) REFERENCES `brand` (`id`),
  CONSTRAINT `FK_7970069a887e42f1a97bea6afe4` FOREIGN KEY (`brand_id`) REFERENCES `brand` (`id`),
  CONSTRAINT `FK_87dc6bd8ec7247758e1a6b781da` FOREIGN KEY (`brand_id`) REFERENCES `brand` (`id`),
  CONSTRAINT `FK_a00c5f2007af4a5f9f9b91b6d27` FOREIGN KEY (`brand_id`) REFERENCES `brand` (`id`),
  CONSTRAINT `FK_a8927d4d14984a56b7fc7e661ac` FOREIGN KEY (`brand_id`) REFERENCES `brand` (`id`),
  CONSTRAINT `FK_af34bd0d38f34255a673023997f` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `FK_b6fb8dd339b54eb586e5604ee5f` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `FK_e114940a795442b7978fabd8040` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `FK_e415d1ec50114454a6bf41b7df7` FOREIGN KEY (`brand_id`) REFERENCES `brand` (`id`),
  CONSTRAINT `FK_eadea3ff3d9449e7bfe34ed993e` FOREIGN KEY (`brand_id`) REFERENCES `brand` (`id`),
  CONSTRAINT `FK_eb92b69e606d4520bba76874da0` FOREIGN KEY (`brand_id`) REFERENCES `brand` (`id`),
  CONSTRAINT `FK_f5b25c521626472eb7f876549b7` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ad`
--

LOCK TABLES `ad` WRITE;
/*!40000 ALTER TABLE `ad` DISABLE KEYS */;
INSERT INTO `ad` VALUES (1,2,'barilla.flv.webm',14,NULL),(2,1,'CA.flv.webm',28,NULL),(3,1,'castorama.flv.webm',15,NULL),(4,4,'hm.flv.webm',17,NULL),(5,0,'michelin.flv.webm',19,NULL),(6,1,'numericable.flv.webm',21,NULL),(7,0,'peugeot_508.flv.webm',22,4),(8,0,'pub_c3.flv.webm',3,8),(9,5,'pub__CTC_3.flv.webm',16,NULL),(10,1,'pub_groupama_Taurus.flv.webm',5,NULL),(11,1,'groupama_alain.flv.webm',5,NULL),(12,6,'pub__kinect_accolade.flv.webm',20,NULL),(13,6,'pub__kinect_sportsv2.flv.webm',20,NULL),(14,6,'pub__xbox_dance_cent.flv.webm',20,NULL),(15,3,'pub__lancome.flv.webm',18,NULL),(16,1,'pub__sfrbt.flv.webm',23,NULL),(17,2,'pub__stmichel.flv.webm',24,NULL),(18,0,'pub_verso-s_2011_hp.flv.webm',25,NULL),(19,3,'pub__WILKINSON.flv.webm',26,NULL),(20,2,'vahine.flv.webm',27,1),(21,2,'auchan.flv.webm',29,NULL),(22,1,'macif.flv.webm',30,NULL),(23,1,'pj.flv.webm',31,NULL);
/*!40000 ALTER TABLE `ad` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ad_country`
--

DROP TABLE IF EXISTS `ad_country`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ad_country` (
  `ad_id` bigint(20) NOT NULL,
  `country_id` bigint(20) NOT NULL,
  KEY `FK_859af428179f4eb4b92eea75eef` (`country_id`),
  KEY `FK_be8bf8cc22df4bd8bb38cbdea07` (`ad_id`),
  CONSTRAINT `FK_859af428179f4eb4b92eea75eef` FOREIGN KEY (`country_id`) REFERENCES `country` (`id`),
  CONSTRAINT `FK_be8bf8cc22df4bd8bb38cbdea07` FOREIGN KEY (`ad_id`) REFERENCES `ad` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ad_country`
--

LOCK TABLES `ad_country` WRITE;
/*!40000 ALTER TABLE `ad_country` DISABLE KEYS */;
/*!40000 ALTER TABLE `ad_country` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ad_response`
--

DROP TABLE IF EXISTS `ad_response`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ad_response` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `response` varchar(255) DEFAULT NULL,
  `rule_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_60d8422765124ecea2f6003be7d` (`rule_id`),
  KEY `FK_7712fa2c11404d47a957bbe2621` (`rule_id`),
  KEY `FK_c2b94b9937524054a8ad69dc420` (`rule_id`),
  KEY `FK_c1893884fef549e8b38d6ee84de` (`rule_id`),
  KEY `FK_58fbadce15c94a03b1552296989` (`rule_id`),
  KEY `FK_b3f964fa757f4e79acb2486aec1` (`rule_id`),
  KEY `FK_855c163e08d548b09235028782b` (`rule_id`),
  KEY `FK_2c41bdb2a2c6498598d2e661bf4` (`rule_id`),
  KEY `FK_70331375eb0849e48963b336b79` (`rule_id`),
  KEY `FK_478011e509a34c39a7e2979958e` (`rule_id`),
  KEY `FK_c3c846040c5043eab2fba1324c9` (`rule_id`),
  KEY `FK_a476c0d9e75948ba8bcbf462be2` (`rule_id`),
  CONSTRAINT `FK_2c41bdb2a2c6498598d2e661bf4` FOREIGN KEY (`rule_id`) REFERENCES `ad_rule` (`id`),
  CONSTRAINT `FK_478011e509a34c39a7e2979958e` FOREIGN KEY (`rule_id`) REFERENCES `ad_rule` (`id`),
  CONSTRAINT `FK_58fbadce15c94a03b1552296989` FOREIGN KEY (`rule_id`) REFERENCES `ad_rule` (`id`),
  CONSTRAINT `FK_60d8422765124ecea2f6003be7d` FOREIGN KEY (`rule_id`) REFERENCES `ad_rule` (`id`),
  CONSTRAINT `FK_70331375eb0849e48963b336b79` FOREIGN KEY (`rule_id`) REFERENCES `ad_rule` (`id`),
  CONSTRAINT `FK_7712fa2c11404d47a957bbe2621` FOREIGN KEY (`rule_id`) REFERENCES `ad_rule` (`id`),
  CONSTRAINT `FK_855c163e08d548b09235028782b` FOREIGN KEY (`rule_id`) REFERENCES `ad_rule` (`id`),
  CONSTRAINT `FK_a476c0d9e75948ba8bcbf462be2` FOREIGN KEY (`rule_id`) REFERENCES `ad_rule` (`id`),
  CONSTRAINT `FK_b3f964fa757f4e79acb2486aec1` FOREIGN KEY (`rule_id`) REFERENCES `ad_rule` (`id`),
  CONSTRAINT `FK_c1893884fef549e8b38d6ee84de` FOREIGN KEY (`rule_id`) REFERENCES `ad_rule` (`id`),
  CONSTRAINT `FK_c2b94b9937524054a8ad69dc420` FOREIGN KEY (`rule_id`) REFERENCES `ad_rule` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ad_response`
--

LOCK TABLES `ad_response` WRITE;
/*!40000 ALTER TABLE `ad_response` DISABLE KEYS */;
INSERT INTO `ad_response` VALUES (1,'9,95 €',4),(2,'9,99 €',4),(3,'8,95 €',4),(4,'10000 kilomètres',5),(5,'8000 kilomètres',5),(6,'12000 kilomètres',5),(7,'84 %',22),(8,'94 %',22),(9,'92 %',22),(10,'Vivre ici',23),(11,'Aller d\'ici',23),(12,'Venir d\'ici',23);
/*!40000 ALTER TABLE `ad_response` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ad_response_player`
--

DROP TABLE IF EXISTS `ad_response_player`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ad_response_player` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `number` int(11) DEFAULT NULL,
  `ad_score_id` bigint(20) DEFAULT NULL,
  `possibility_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_df821ba7d3f84fda9770e8bf79c` (`ad_score_id`),
  KEY `FK_a1eaed1cf9e84fa8bd8666194f6` (`possibility_id`),
  CONSTRAINT `FK_a1eaed1cf9e84fa8bd8666194f6` FOREIGN KEY (`possibility_id`) REFERENCES `possibility` (`id`),
  CONSTRAINT `FK_df821ba7d3f84fda9770e8bf79c` FOREIGN KEY (`ad_score_id`) REFERENCES `ad_score` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=97 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ad_response_player`
--

LOCK TABLES `ad_response_player` WRITE;
/*!40000 ALTER TABLE `ad_response_player` DISABLE KEYS */;
INSERT INTO `ad_response_player` VALUES (1,0,21,NULL),(2,1,21,NULL),(3,2,21,349),(4,3,21,NULL),(5,4,21,355),(6,5,21,358),(7,0,23,NULL),(8,1,23,NULL),(9,2,23,NULL),(10,3,23,370),(11,4,23,NULL),(12,5,23,376),(13,0,25,379),(14,1,25,NULL),(15,2,25,NULL),(16,3,25,NULL),(17,4,25,NULL),(18,5,25,394),(19,0,27,397),(20,1,27,NULL),(21,2,27,NULL),(22,3,27,406),(23,4,27,NULL),(24,5,27,412),(25,0,29,415),(26,1,29,418),(27,2,29,421),(28,3,29,NULL),(29,4,29,NULL),(30,5,29,NULL),(31,0,31,NULL),(32,1,31,NULL),(33,2,31,NULL),(34,3,31,NULL),(35,4,31,NULL),(36,5,31,NULL),(37,0,33,451),(38,1,33,NULL),(39,2,33,NULL),(40,3,33,460),(41,4,33,NULL),(42,5,33,NULL),(43,0,35,NULL),(44,1,35,NULL),(45,2,35,475),(46,3,35,478),(47,4,35,NULL),(48,5,35,NULL),(49,0,37,487),(50,1,37,NULL),(51,2,37,NULL),(52,3,37,NULL),(53,4,37,499),(54,5,37,NULL),(55,0,43,577),(56,1,43,580),(57,2,43,583),(58,3,43,586),(59,4,43,589),(60,5,43,592),(61,0,46,613),(62,1,46,NULL),(63,2,46,619),(64,3,46,622),(65,4,46,625),(66,5,46,628),(67,0,49,649),(68,1,49,652),(69,2,49,655),(70,3,49,658),(71,4,49,661),(72,5,49,664),(73,0,82,1225),(74,1,82,1228),(75,2,82,1231),(76,3,82,1234),(77,4,82,1237),(78,5,82,1240),(79,0,106,1639),(80,1,106,1642),(81,2,106,NULL),(82,3,106,1648),(83,4,106,1651),(84,5,106,1654),(85,0,108,1657),(86,1,108,1660),(87,2,108,1663),(88,3,108,1666),(89,4,108,1669),(90,5,108,1672),(91,0,110,1675),(92,1,110,1678),(93,2,110,1681),(94,3,110,1684),(95,4,110,1687),(96,5,110,1690);
/*!40000 ALTER TABLE `ad_response_player` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ad_rule`
--

DROP TABLE IF EXISTS `ad_rule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ad_rule` (
  `classe` varchar(31) NOT NULL,
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `end_date` date DEFAULT NULL,
  `question` varchar(255) DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `correct_id` bigint(20) DEFAULT NULL,
  `ad_id` bigint(20) DEFAULT NULL,
  `sex` int(11) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `age_max` int(11) DEFAULT NULL,
  `age_min` int(11) DEFAULT NULL,
  `amount` double DEFAULT NULL,
  `country_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_fde7dcf1ec7749909f72330080a` (`correct_id`),
  KEY `FK_51dac9c9228f4d8e8cb37a1c464` (`ad_id`),
  KEY `FK_43e7122bccf74e069c8fc410592` (`correct_id`),
  KEY `FK_f3633b70a35c451499116beefa9` (`ad_id`),
  KEY `FK_b959e42f931b469b92d082d7ff8` (`correct_id`),
  KEY `FK_265523d3cdae41f1ac249253043` (`ad_id`),
  KEY `FK_5d86203d52144543b5e9a2eacde` (`correct_id`),
  KEY `FK_8e5d5690b95d4708824879c2499` (`ad_id`),
  KEY `FK_c602250f7ef14b6da69b8d8606b` (`correct_id`),
  KEY `FK_f7fb6e6c627c4dc58798502de69` (`ad_id`),
  KEY `FK_d2bdab9d081e4443801eaa687b9` (`correct_id`),
  KEY `FK_64feb1dd5a27489bb9ca30490ae` (`ad_id`),
  KEY `FK_6bdfb18fcec54816bff120987d6` (`correct_id`),
  KEY `FK_d7e00bc95d8f48729352d61169a` (`ad_id`),
  KEY `FK_193bb7d935084d0ab206b958c37` (`correct_id`),
  KEY `FK_58b3b569142c45b7b4800dce3bf` (`ad_id`),
  KEY `FK_64ce23ceca54460d8ab69224f57` (`correct_id`),
  KEY `FK_f9efaa84567049d4b2b1378c98b` (`ad_id`),
  KEY `FK_304549dad2fd45fd9f9d37db538` (`correct_id`),
  KEY `FK_402bc4003afd4b3baed0eb904e2` (`ad_id`),
  KEY `FK_a8ae93f391a2430f8d6e3172cab` (`correct_id`),
  KEY `FK_3880fc9e92bd4877a3ef235aa21` (`ad_id`),
  KEY `FK_9e40707cef1f424eaec7496c841` (`correct_id`),
  KEY `FK_da887209f18e4080a6728992c73` (`ad_id`),
  KEY `FK_6dc7bc2964924094936aebd2b38` (`country_id`),
  CONSTRAINT `FK_6dc7bc2964924094936aebd2b38` FOREIGN KEY (`country_id`) REFERENCES `country` (`id`),
  CONSTRAINT `FK_193bb7d935084d0ab206b958c37` FOREIGN KEY (`correct_id`) REFERENCES `ad_response` (`id`),
  CONSTRAINT `FK_265523d3cdae41f1ac249253043` FOREIGN KEY (`ad_id`) REFERENCES `ad` (`id`),
  CONSTRAINT `FK_304549dad2fd45fd9f9d37db538` FOREIGN KEY (`correct_id`) REFERENCES `ad_response` (`id`),
  CONSTRAINT `FK_402bc4003afd4b3baed0eb904e2` FOREIGN KEY (`ad_id`) REFERENCES `ad` (`id`),
  CONSTRAINT `FK_43e7122bccf74e069c8fc410592` FOREIGN KEY (`correct_id`) REFERENCES `ad_response` (`id`),
  CONSTRAINT `FK_51dac9c9228f4d8e8cb37a1c464` FOREIGN KEY (`ad_id`) REFERENCES `ad` (`id`),
  CONSTRAINT `FK_58b3b569142c45b7b4800dce3bf` FOREIGN KEY (`ad_id`) REFERENCES `ad` (`id`),
  CONSTRAINT `FK_5d86203d52144543b5e9a2eacde` FOREIGN KEY (`correct_id`) REFERENCES `ad_response` (`id`),
  CONSTRAINT `FK_64ce23ceca54460d8ab69224f57` FOREIGN KEY (`correct_id`) REFERENCES `ad_response` (`id`),
  CONSTRAINT `FK_64feb1dd5a27489bb9ca30490ae` FOREIGN KEY (`ad_id`) REFERENCES `ad` (`id`),
  CONSTRAINT `FK_6bdfb18fcec54816bff120987d6` FOREIGN KEY (`correct_id`) REFERENCES `ad_response` (`id`),
  CONSTRAINT `FK_8e5d5690b95d4708824879c2499` FOREIGN KEY (`ad_id`) REFERENCES `ad` (`id`),
  CONSTRAINT `FK_9e40707cef1f424eaec7496c841` FOREIGN KEY (`correct_id`) REFERENCES `ad_response` (`id`),
  CONSTRAINT `FK_b959e42f931b469b92d082d7ff8` FOREIGN KEY (`correct_id`) REFERENCES `ad_response` (`id`),
  CONSTRAINT `FK_c602250f7ef14b6da69b8d8606b` FOREIGN KEY (`correct_id`) REFERENCES `ad_response` (`id`),
  CONSTRAINT `FK_d2bdab9d081e4443801eaa687b9` FOREIGN KEY (`correct_id`) REFERENCES `ad_response` (`id`),
  CONSTRAINT `FK_d7e00bc95d8f48729352d61169a` FOREIGN KEY (`ad_id`) REFERENCES `ad` (`id`),
  CONSTRAINT `FK_da887209f18e4080a6728992c73` FOREIGN KEY (`ad_id`) REFERENCES `ad` (`id`),
  CONSTRAINT `FK_f3633b70a35c451499116beefa9` FOREIGN KEY (`ad_id`) REFERENCES `ad` (`id`),
  CONSTRAINT `FK_f7fb6e6c627c4dc58798502de69` FOREIGN KEY (`ad_id`) REFERENCES `ad` (`id`),
  CONSTRAINT `FK_f9efaa84567049d4b2b1378c98b` FOREIGN KEY (`ad_id`) REFERENCES `ad` (`id`),
  CONSTRAINT `FK_fde7dcf1ec7749909f72330080a` FOREIGN KEY (`correct_id`) REFERENCES `ad_response` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ad_rule`
--

LOCK TABLES `ad_rule` WRITE;
/*!40000 ALTER TABLE `ad_rule` DISABLE KEYS */;
INSERT INTO `ad_rule` VALUES ('Brand',1,'2014-01-01','Quelle est la marque ?','2011-10-19',NULL,1,NULL,NULL,NULL,NULL,NULL,NULL),('Brand',2,'2014-01-01','Quelle est la marque ?','2011-10-19',NULL,2,NULL,NULL,NULL,NULL,NULL,NULL),('Brand',3,'2014-01-01','Quelle est la marque ?','2011-10-19',NULL,3,NULL,NULL,NULL,NULL,NULL,NULL),('Open',4,'2014-01-01','Quel est le prix du Top ?','2011-10-19',1,4,NULL,NULL,NULL,NULL,NULL,NULL),('Open',5,'2014-01-01','Combien faites vous de kilomètre en plus ?','2011-10-19',4,5,NULL,NULL,NULL,NULL,NULL,NULL),('Brand',6,'2014-01-01','Quelle est la marque ?','2011-10-19',NULL,6,NULL,NULL,NULL,NULL,NULL,NULL),('Product',7,'2014-01-01','Quel est le véhicule ?','2011-10-19',NULL,7,NULL,NULL,NULL,NULL,NULL,NULL),('Product',8,'2014-01-01','Quel est le véhicule ?','2011-10-19',NULL,8,NULL,NULL,NULL,NULL,NULL,NULL),('Brand',9,'2014-01-01','Quelle est la marque ?','2011-10-19',NULL,9,NULL,NULL,NULL,NULL,NULL,NULL),('Brand',10,'2014-01-01','Quelle est la marque ?','2011-10-19',NULL,10,NULL,NULL,NULL,NULL,NULL,NULL),('Brand',11,'2014-01-01','Quelle est la marque ?','2011-10-19',NULL,11,NULL,NULL,NULL,NULL,NULL,NULL),('Brand',12,'2014-01-01','Quelle est la marque ?','2011-10-19',NULL,12,NULL,NULL,NULL,NULL,NULL,NULL),('Brand',13,'2014-01-01','Quelle est la marque ?','2011-10-19',NULL,13,NULL,NULL,NULL,NULL,NULL,NULL),('Brand',14,'2014-01-01','Quelle est la marque ?','2011-10-19',NULL,14,NULL,NULL,NULL,NULL,NULL,NULL),('Brand',15,'2014-01-01','Quelle est la marque ?','2011-10-19',NULL,15,NULL,NULL,NULL,NULL,NULL,NULL),('Brand',16,'2014-01-01','Quelle est la marque ?','2011-10-19',NULL,16,NULL,NULL,NULL,NULL,NULL,NULL),('Brand',17,'2014-01-01','Quelle est la marque ?','2011-10-19',NULL,17,NULL,NULL,NULL,NULL,NULL,NULL),('Brand',18,'2014-01-01','Quelle est la marque ?','2011-10-19',NULL,18,NULL,NULL,NULL,NULL,NULL,NULL),('Brand',19,'2014-01-01','Quelle est la marque ?','2011-10-19',NULL,19,NULL,NULL,NULL,NULL,NULL,NULL),('Product',20,'2014-01-01','Quel est le produit ?','2011-10-19',NULL,20,NULL,NULL,NULL,NULL,NULL,NULL),('Brand',21,'2014-01-01','Quelle est la marque ?','2011-10-19',NULL,21,NULL,NULL,NULL,NULL,NULL,NULL),('Open',22,'2014-01-01','Quel est le % de sociétaire MACIF satisfaits ?','2011-10-19',8,22,NULL,NULL,NULL,NULL,NULL,NULL),('Open',23,'2014-01-01','Quel est le slogan ?','2011-10-19',10,23,NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `ad_rule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ad_score`
--

DROP TABLE IF EXISTS `ad_score`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ad_score` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `score` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=111 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ad_score`
--

LOCK TABLES `ad_score` WRITE;
/*!40000 ALTER TABLE `ad_score` DISABLE KEYS */;
INSERT INTO `ad_score` VALUES (1,0),(2,0),(3,0),(4,0),(5,0),(6,0),(7,0),(8,0),(9,0),(10,0),(11,0),(12,0),(13,0),(14,0),(15,0),(16,0),(17,0),(18,0),(19,0),(20,0),(21,3),(22,0),(23,2),(24,0),(25,2),(26,0),(27,3),(28,0),(29,3),(30,0),(31,0),(32,0),(33,2),(34,0),(35,2),(36,0),(37,2),(38,0),(39,0),(40,0),(41,0),(42,0),(43,6),(44,0),(45,0),(46,5),(47,0),(48,0),(49,6),(50,0),(51,0),(52,0),(53,0),(54,0),(55,0),(56,0),(57,0),(58,0),(59,0),(60,0),(61,0),(62,0),(63,0),(64,0),(65,0),(66,0),(67,0),(68,0),(69,0),(70,0),(71,0),(72,0),(73,0),(74,0),(75,0),(76,0),(77,0),(78,0),(79,0),(80,0),(81,0),(82,6),(83,0),(84,0),(85,0),(86,0),(87,0),(88,0),(90,0),(91,0),(92,0),(93,0),(94,0),(95,0),(96,0),(97,0),(98,0),(99,0),(100,0),(101,0),(102,0),(103,0),(104,0),(105,0),(106,5),(107,0),(108,6),(109,0),(110,6);
/*!40000 ALTER TABLE `ad_score` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `adchoise`
--

DROP TABLE IF EXISTS `adchoise`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `adchoise` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `number` int(11) DEFAULT NULL,
  `question` varchar(255) DEFAULT NULL,
  `ad_game_id` bigint(20) DEFAULT NULL,
  `correct_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_49683d0e09614ff68d70bdd6de1` (`ad_game_id`),
  KEY `FK_25af2f73af9848e8896ff1e98ed` (`correct_id`),
  KEY `FK_4393a8e554c6485296c8fbf49d8` (`ad_game_id`),
  KEY `FK_83f95663a6d048e7b6c7517bd2e` (`correct_id`),
  KEY `FK_d3198ec14bf84449abc13a72f7a` (`ad_game_id`),
  KEY `FK_92f8c98fdb56453e98b2d060db1` (`correct_id`),
  KEY `FK_6af05b193be141098db90bacc12` (`ad_game_id`),
  KEY `FK_490d3fd720df4d1ea7bf1ab7c3d` (`correct_id`),
  KEY `FK_a9c6bb88f292437dae3ba389050` (`ad_game_id`),
  KEY `FK_950948740d03457e90ccc1ea86e` (`correct_id`),
  KEY `FK_1c8594f510194caa939b66352c4` (`ad_game_id`),
  KEY `FK_4d00d0fe5c86444e957fcd1c5e8` (`correct_id`),
  KEY `FK_69c67aeafa7d4b8db29cbc12d37` (`ad_game_id`),
  KEY `FK_69782e46f6594feeaf8d9ee29c2` (`correct_id`),
  KEY `FK_e7306449496d414d9cfef185eb2` (`ad_game_id`),
  KEY `FK_55475f4de2034f06a0ee3566e67` (`correct_id`),
  KEY `FK_4a9f73225ad54df8a38914e1cd0` (`ad_game_id`),
  KEY `FK_33b7fb58875a4a72b565e8abbc5` (`correct_id`),
  KEY `FK_1994fe2df9cf4d4c943acd53bb3` (`ad_game_id`),
  KEY `FK_b8225a8d9f8b4ae695411ebbd0c` (`correct_id`),
  KEY `FK_dc73e458460f4dcda93d2430f17` (`ad_game_id`),
  KEY `FK_f644500a56d14d1ea55ef28e385` (`correct_id`),
  KEY `FK_57d81801ce4f440e8affbf7497c` (`ad_game_id`),
  KEY `FK_fd74e25c1852427db084a35fb86` (`correct_id`),
  CONSTRAINT `FK_1994fe2df9cf4d4c943acd53bb3` FOREIGN KEY (`ad_game_id`) REFERENCES `adgame` (`id`),
  CONSTRAINT `FK_1c8594f510194caa939b66352c4` FOREIGN KEY (`ad_game_id`) REFERENCES `adgame` (`id`),
  CONSTRAINT `FK_25af2f73af9848e8896ff1e98ed` FOREIGN KEY (`correct_id`) REFERENCES `possibility` (`id`),
  CONSTRAINT `FK_33b7fb58875a4a72b565e8abbc5` FOREIGN KEY (`correct_id`) REFERENCES `possibility` (`id`),
  CONSTRAINT `FK_4393a8e554c6485296c8fbf49d8` FOREIGN KEY (`ad_game_id`) REFERENCES `adgame` (`id`),
  CONSTRAINT `FK_490d3fd720df4d1ea7bf1ab7c3d` FOREIGN KEY (`correct_id`) REFERENCES `possibility` (`id`),
  CONSTRAINT `FK_49683d0e09614ff68d70bdd6de1` FOREIGN KEY (`ad_game_id`) REFERENCES `adgame` (`id`),
  CONSTRAINT `FK_4a9f73225ad54df8a38914e1cd0` FOREIGN KEY (`ad_game_id`) REFERENCES `adgame` (`id`),
  CONSTRAINT `FK_4d00d0fe5c86444e957fcd1c5e8` FOREIGN KEY (`correct_id`) REFERENCES `possibility` (`id`),
  CONSTRAINT `FK_55475f4de2034f06a0ee3566e67` FOREIGN KEY (`correct_id`) REFERENCES `possibility` (`id`),
  CONSTRAINT `FK_57d81801ce4f440e8affbf7497c` FOREIGN KEY (`ad_game_id`) REFERENCES `adgame` (`id`),
  CONSTRAINT `FK_69782e46f6594feeaf8d9ee29c2` FOREIGN KEY (`correct_id`) REFERENCES `possibility` (`id`),
  CONSTRAINT `FK_69c67aeafa7d4b8db29cbc12d37` FOREIGN KEY (`ad_game_id`) REFERENCES `adgame` (`id`),
  CONSTRAINT `FK_6af05b193be141098db90bacc12` FOREIGN KEY (`ad_game_id`) REFERENCES `adgame` (`id`),
  CONSTRAINT `FK_83f95663a6d048e7b6c7517bd2e` FOREIGN KEY (`correct_id`) REFERENCES `possibility` (`id`),
  CONSTRAINT `FK_92f8c98fdb56453e98b2d060db1` FOREIGN KEY (`correct_id`) REFERENCES `possibility` (`id`),
  CONSTRAINT `FK_950948740d03457e90ccc1ea86e` FOREIGN KEY (`correct_id`) REFERENCES `possibility` (`id`),
  CONSTRAINT `FK_a9c6bb88f292437dae3ba389050` FOREIGN KEY (`ad_game_id`) REFERENCES `adgame` (`id`),
  CONSTRAINT `FK_b8225a8d9f8b4ae695411ebbd0c` FOREIGN KEY (`correct_id`) REFERENCES `possibility` (`id`),
  CONSTRAINT `FK_d3198ec14bf84449abc13a72f7a` FOREIGN KEY (`ad_game_id`) REFERENCES `adgame` (`id`),
  CONSTRAINT `FK_e7306449496d414d9cfef185eb2` FOREIGN KEY (`ad_game_id`) REFERENCES `adgame` (`id`),
  CONSTRAINT `FK_fd74e25c1852427db084a35fb86` FOREIGN KEY (`correct_id`) REFERENCES `possibility` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=565 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `adchoise`
--

LOCK TABLES `adchoise` WRITE;
/*!40000 ALTER TABLE `adchoise` DISABLE KEYS */;
INSERT INTO `adchoise` VALUES (1,0,'Quel est le véhicule ?',1,1),(2,1,'Quelle est la marque ?',1,4),(3,2,'Quelle est la marque ?',1,7),(4,3,'Quel est le véhicule ?',1,10),(5,4,'Quelle est la marque ?',1,13),(6,5,'Quelle est la marque ?',1,16),(7,0,'Quel est le produit ?',2,19),(8,1,'Quelle est la marque ?',2,22),(9,2,'Quelle est la marque ?',2,25),(10,3,'Quelle est la marque ?',2,28),(11,4,'Quel est le % de sociétaire MACIF satisfaits ?',2,31),(12,5,'Quelle est la marque ?',2,34),(13,0,'Quelle est la marque ?',3,37),(14,1,'Quelle est la marque ?',3,40),(15,2,'Quelle est la marque ?',3,43),(16,3,'Quel est le véhicule ?',3,46),(17,4,'Quelle est la marque ?',3,49),(18,5,'Quel est le prix du Top ?',3,52),(19,0,'Quelle est la marque ?',4,55),(20,1,'Quel est le % de sociétaire MACIF satisfaits ?',4,58),(21,2,'Quel est le produit ?',4,61),(22,3,'Quel est le véhicule ?',4,64),(23,4,'Quelle est la marque ?',4,67),(24,5,'Quelle est la marque ?',4,70),(25,0,'Combien faites vous de kilomètre en plus ?',5,73),(26,1,'Quel est le % de sociétaire MACIF satisfaits ?',5,76),(27,2,'Quelle est la marque ?',5,79),(28,3,'Quel est le véhicule ?',5,82),(29,4,'Quelle est la marque ?',5,85),(30,5,'Quel est le véhicule ?',5,88),(31,0,'Quelle est la marque ?',6,91),(32,1,'Quelle est la marque ?',6,94),(33,2,'Quelle est la marque ?',6,97),(34,3,'Quelle est la marque ?',6,100),(35,4,'Quelle est la marque ?',6,103),(36,5,'Quel est le véhicule ?',6,106),(37,0,'Quelle est la marque ?',7,109),(38,1,'Quelle est la marque ?',7,112),(39,2,'Quelle est la marque ?',7,115),(40,3,'Quelle est la marque ?',7,118),(41,4,'Quelle est la marque ?',7,121),(42,5,'Quelle est la marque ?',7,124),(43,0,'Quelle est la marque ?',8,127),(44,1,'Quelle est la marque ?',8,130),(45,2,'Quelle est la marque ?',8,133),(46,3,'Quelle est la marque ?',8,136),(47,4,'Quelle est la marque ?',8,139),(48,5,'Quelle est la marque ?',8,142),(49,0,'Quelle est la marque ?',9,145),(50,1,'Quelle est la marque ?',9,148),(51,2,'Quel est le prix du Top ?',9,151),(52,3,'Combien faites vous de kilomètre en plus ?',9,154),(53,4,'Quelle est la marque ?',9,157),(54,5,'Quelle est la marque ?',9,160),(55,0,'Quelle est la marque ?',10,163),(56,1,'Quelle est la marque ?',10,166),(57,2,'Quelle est la marque ?',10,169),(58,3,'Quelle est la marque ?',10,172),(59,4,'Quel est le % de sociétaire MACIF satisfaits ?',10,175),(60,5,'Quelle est la marque ?',10,178),(61,0,'Quelle est la marque ?',11,181),(62,1,'Quelle est la marque ?',11,184),(63,2,'Quelle est la marque ?',11,187),(64,3,'Quelle est la marque ?',11,190),(65,4,'Combien faites vous de kilomètre en plus ?',11,193),(66,5,'Quelle est la marque ?',11,196),(67,0,'Quelle est la marque ?',12,199),(68,1,'Quel est le véhicule ?',12,202),(69,2,'Quelle est la marque ?',12,205),(70,3,'Quelle est la marque ?',12,208),(71,4,'Quelle est la marque ?',12,211),(72,5,'Quel est le véhicule ?',12,214),(73,0,'Quelle est la marque ?',13,217),(74,1,'Quelle est la marque ?',13,220),(75,2,'Quelle est la marque ?',13,223),(76,3,'Quelle est la marque ?',13,226),(77,4,'Quelle est la marque ?',13,229),(78,5,'Combien faites vous de kilomètre en plus ?',13,232),(79,0,'Quelle est la marque ?',14,235),(80,1,'Quel est le prix du Top ?',14,238),(81,2,'Quel est le % de sociétaire MACIF satisfaits ?',14,241),(82,3,'Quelle est la marque ?',14,244),(83,4,'Quel est le produit ?',14,247),(84,5,'Quelle est la marque ?',14,250),(85,0,'Quel est le prix du Top ?',15,253),(86,1,'Quelle est la marque ?',15,256),(87,2,'Quelle est la marque ?',15,259),(88,3,'Quelle est la marque ?',15,262),(89,4,'Quelle est la marque ?',15,265),(90,5,'Quelle est la marque ?',15,268),(91,0,'Combien faites vous de kilomètre en plus ?',16,271),(92,1,'Quelle est la marque ?',16,274),(93,2,'Quelle est la marque ?',16,277),(94,3,'Quel est le slogan ?',16,280),(95,4,'Quel est le prix du Top ?',16,283),(96,5,'Quelle est la marque ?',16,286),(97,0,'Quelle est la marque ?',17,289),(98,1,'Quelle est la marque ?',17,292),(99,2,'Quel est le slogan ?',17,295),(100,3,'Quelle est la marque ?',17,298),(101,4,'Quel est le % de sociétaire MACIF satisfaits ?',17,301),(102,5,'Quel est le véhicule ?',17,304),(103,0,'Quelle est la marque ?',18,307),(104,1,'Quelle est la marque ?',18,310),(105,2,'Quel est le prix du Top ?',18,313),(106,3,'Quelle est la marque ?',18,316),(107,4,'Quel est le véhicule ?',18,319),(108,5,'Combien faites vous de kilomètre en plus ?',18,322),(109,0,'Quel est le produit ?',19,325),(110,1,'Quel est le % de sociétaire MACIF satisfaits ?',19,328),(111,2,'Quelle est la marque ?',19,331),(112,3,'Quelle est la marque ?',19,334),(113,4,'Quelle est la marque ?',19,337),(114,5,'Quelle est la marque ?',19,340),(115,0,'Quelle est la marque ?',20,343),(116,1,'Quelle est la marque ?',20,346),(117,2,'Quelle est la marque ?',20,349),(118,3,'Quelle est la marque ?',20,352),(119,4,'Quelle est la marque ?',20,355),(120,5,'Quelle est la marque ?',20,358),(121,0,'Quelle est la marque ?',21,361),(122,1,'Quelle est la marque ?',21,364),(123,2,'Quelle est la marque ?',21,367),(124,3,'Quel est le prix du Top ?',21,370),(125,4,'Quel est le véhicule ?',21,373),(126,5,'Quel est le produit ?',21,376),(127,0,'Quelle est la marque ?',22,379),(128,1,'Quel est le véhicule ?',22,382),(129,2,'Quelle est la marque ?',22,385),(130,3,'Quelle est la marque ?',22,388),(131,4,'Quelle est la marque ?',22,391),(132,5,'Quel est le prix du Top ?',22,394),(133,0,'Quelle est la marque ?',23,397),(134,1,'Quelle est la marque ?',23,400),(135,2,'Quelle est la marque ?',23,403),(136,3,'Quelle est la marque ?',23,406),(137,4,'Quelle est la marque ?',23,409),(138,5,'Quel est le % de sociétaire MACIF satisfaits ?',23,412),(139,0,'Quel est le % de sociétaire MACIF satisfaits ?',24,415),(140,1,'Quelle est la marque ?',24,418),(141,2,'Quelle est la marque ?',24,421),(142,3,'Quelle est la marque ?',24,424),(143,4,'Combien faites vous de kilomètre en plus ?',24,427),(144,5,'Quel est le véhicule ?',24,430),(145,0,'Quel est le slogan ?',25,433),(146,1,'Quelle est la marque ?',25,436),(147,2,'Quelle est la marque ?',25,439),(148,3,'Quelle est la marque ?',25,442),(149,4,'Quelle est la marque ?',25,445),(150,5,'Quelle est la marque ?',25,448),(151,0,'Quelle est la marque ?',26,451),(152,1,'Quelle est la marque ?',26,454),(153,2,'Quelle est la marque ?',26,457),(154,3,'Quelle est la marque ?',26,460),(155,4,'Combien faites vous de kilomètre en plus ?',26,463),(156,5,'Quelle est la marque ?',26,466),(157,0,'Quelle est la marque ?',27,469),(158,1,'Quelle est la marque ?',27,472),(159,2,'Quelle est la marque ?',27,475),(160,3,'Quelle est la marque ?',27,478),(161,4,'Quelle est la marque ?',27,481),(162,5,'Combien faites vous de kilomètre en plus ?',27,484),(163,0,'Quelle est la marque ?',28,487),(164,1,'Quelle est la marque ?',28,490),(165,2,'Quelle est la marque ?',28,493),(166,3,'Quelle est la marque ?',28,496),(167,4,'Quel est le prix du Top ?',28,499),(168,5,'Quelle est la marque ?',28,502),(169,0,'Quelle est la marque ?',29,505),(170,1,'Quelle est la marque ?',29,508),(171,2,'Quelle est la marque ?',29,511),(172,3,'Quel est le véhicule ?',29,514),(173,4,'Quelle est la marque ?',29,517),(174,5,'Quelle est la marque ?',29,520),(175,0,'Quelle est la marque ?',30,523),(176,1,'Quelle est la marque ?',30,526),(177,2,'Quelle est la marque ?',30,529),(178,3,'Quelle est la marque ?',30,532),(179,4,'Quel est le % de sociétaire MACIF satisfaits ?',30,535),(180,5,'Quelle est la marque ?',30,538),(181,0,'Quelle est la marque ?',31,541),(182,1,'Combien faites vous de kilomètre en plus ?',31,544),(183,2,'Quelle est la marque ?',31,547),(184,3,'Quelle est la marque ?',31,550),(185,4,'Quelle est la marque ?',31,553),(186,5,'Quelle est la marque ?',31,556),(187,0,'Quelle est la marque ?',32,559),(188,1,'Quelle est la marque ?',32,562),(189,2,'Quelle est la marque ?',32,565),(190,3,'Quelle est la marque ?',32,568),(191,4,'Quel est le % de sociétaire MACIF satisfaits ?',32,571),(192,5,'Quelle est la marque ?',32,574),(193,0,'Quelle est la marque ?',33,577),(194,1,'Quel est le véhicule ?',33,580),(195,2,'Quelle est la marque ?',33,583),(196,3,'Quelle est la marque ?',33,586),(197,4,'Quelle est la marque ?',33,589),(198,5,'Quelle est la marque ?',33,592),(199,0,'Quelle est la marque ?',34,595),(200,1,'Quelle est la marque ?',34,598),(201,2,'Quel est le % de sociétaire MACIF satisfaits ?',34,601),(202,3,'Quel est le produit ?',34,604),(203,4,'Quelle est la marque ?',34,607),(204,5,'Quel est le prix du Top ?',34,610),(205,0,'Quelle est la marque ?',35,613),(206,1,'Quel est le slogan ?',35,616),(207,2,'Quelle est la marque ?',35,619),(208,3,'Quelle est la marque ?',35,622),(209,4,'Quelle est la marque ?',35,625),(210,5,'Quelle est la marque ?',35,628),(211,0,'Quelle est la marque ?',36,631),(212,1,'Quelle est la marque ?',36,634),(213,2,'Quel est le produit ?',36,637),(214,3,'Quelle est la marque ?',36,640),(215,4,'Quel est le prix du Top ?',36,643),(216,5,'Quelle est la marque ?',36,646),(217,0,'Quelle est la marque ?',37,649),(218,1,'Quel est le prix du Top ?',37,652),(219,2,'Quel est le véhicule ?',37,655),(220,3,'Quelle est la marque ?',37,658),(221,4,'Quelle est la marque ?',37,661),(222,5,'Quelle est la marque ?',37,664),(223,0,'Quel est le slogan ?',38,667),(224,1,'Quelle est la marque ?',38,670),(225,2,'Quelle est la marque ?',38,673),(226,3,'Quel est le véhicule ?',38,676),(227,4,'Quelle est la marque ?',38,679),(228,5,'Quelle est la marque ?',38,682),(229,0,'Quelle est la marque ?',39,685),(230,1,'Quelle est la marque ?',39,688),(231,2,'Quel est le slogan ?',39,691),(232,3,'Quelle est la marque ?',39,694),(233,4,'Quelle est la marque ?',39,697),(234,5,'Quelle est la marque ?',39,700),(235,0,'Quel est le véhicule ?',40,703),(236,1,'Quelle est la marque ?',40,706),(237,2,'Quelle est la marque ?',40,709),(238,3,'Quelle est la marque ?',40,712),(239,4,'Quelle est la marque ?',40,715),(240,5,'Quelle est la marque ?',40,718),(241,0,'Quelle est la marque ?',41,721),(242,1,'Quelle est la marque ?',41,724),(243,2,'Quelle est la marque ?',41,727),(244,3,'Quel est le prix du Top ?',41,730),(245,4,'Quel est le % de sociétaire MACIF satisfaits ?',41,733),(246,5,'Quelle est la marque ?',41,736),(247,0,'Quelle est la marque ?',42,739),(248,1,'Quelle est la marque ?',42,742),(249,2,'Quel est le véhicule ?',42,745),(250,3,'Quelle est la marque ?',42,748),(251,4,'Quelle est la marque ?',42,751),(252,5,'Quelle est la marque ?',42,754),(253,0,'Quelle est la marque ?',43,757),(254,1,'Quelle est la marque ?',43,760),(255,2,'Combien faites vous de kilomètre en plus ?',43,763),(256,3,'Quelle est la marque ?',43,766),(257,4,'Quelle est la marque ?',43,769),(258,5,'Quel est le produit ?',43,772),(259,0,'Quelle est la marque ?',44,775),(260,1,'Quel est le % de sociétaire MACIF satisfaits ?',44,778),(261,2,'Quel est le véhicule ?',44,781),(262,3,'Quelle est la marque ?',44,784),(263,4,'Quel est le produit ?',44,787),(264,5,'Quelle est la marque ?',44,790),(265,0,'Quelle est la marque ?',45,793),(266,1,'Quel est le produit ?',45,796),(267,2,'Quelle est la marque ?',45,799),(268,3,'Quel est le % de sociétaire MACIF satisfaits ?',45,802),(269,4,'Quelle est la marque ?',45,805),(270,5,'Quelle est la marque ?',45,808),(271,0,'Quelle est la marque ?',46,811),(272,1,'Quelle est la marque ?',46,814),(273,2,'Quelle est la marque ?',46,817),(274,3,'Quelle est la marque ?',46,820),(275,4,'Quelle est la marque ?',46,823),(276,5,'Quelle est la marque ?',46,826),(277,0,'Quelle est la marque ?',47,829),(278,1,'Quel est le % de sociétaire MACIF satisfaits ?',47,832),(279,2,'Quelle est la marque ?',47,835),(280,3,'Quelle est la marque ?',47,838),(281,4,'Quelle est la marque ?',47,841),(282,5,'Quelle est la marque ?',47,844),(283,0,'Quel est le véhicule ?',48,847),(284,1,'Quelle est la marque ?',48,850),(285,2,'Quelle est la marque ?',48,853),(286,3,'Quelle est la marque ?',48,856),(287,4,'Quelle est la marque ?',48,859),(288,5,'Quel est le véhicule ?',48,862),(289,0,'Quelle est la marque ?',49,865),(290,1,'Quel est le véhicule ?',49,868),(291,2,'Quelle est la marque ?',49,871),(292,3,'Quel est le slogan ?',49,874),(293,4,'Combien faites vous de kilomètre en plus ?',49,877),(294,5,'Quel est le produit ?',49,880),(295,0,'Quelle est la marque ?',50,883),(296,1,'Quelle est la marque ?',50,886),(297,2,'Quel est le véhicule ?',50,889),(298,3,'Quelle est la marque ?',50,892),(299,4,'Quelle est la marque ?',50,895),(300,5,'Quelle est la marque ?',50,898),(301,0,'Quelle est la marque ?',51,901),(302,1,'Quel est le véhicule ?',51,904),(303,2,'Quelle est la marque ?',51,907),(304,3,'Quel est le slogan ?',51,910),(305,4,'Quelle est la marque ?',51,913),(306,5,'Quelle est la marque ?',51,916),(307,0,'Quel est le produit ?',52,919),(308,1,'Combien faites vous de kilomètre en plus ?',52,922),(309,2,'Quelle est la marque ?',52,925),(310,3,'Quelle est la marque ?',52,928),(311,4,'Quel est le prix du Top ?',52,931),(312,5,'Quelle est la marque ?',52,934),(313,0,'Quelle est la marque ?',53,937),(314,1,'Quelle est la marque ?',53,940),(315,2,'Quel est le % de sociétaire MACIF satisfaits ?',53,943),(316,3,'Quel est le véhicule ?',53,946),(317,4,'Quelle est la marque ?',53,949),(318,5,'Quelle est la marque ?',53,952),(319,0,'Quel est le véhicule ?',54,955),(320,1,'Quelle est la marque ?',54,958),(321,2,'Quelle est la marque ?',54,961),(322,3,'Quelle est la marque ?',54,964),(323,4,'Quel est le produit ?',54,967),(324,5,'Quelle est la marque ?',54,970),(325,0,'Quelle est la marque ?',55,973),(326,1,'Quelle est la marque ?',55,976),(327,2,'Quel est le slogan ?',55,979),(328,3,'Quel est le prix du Top ?',55,982),(329,4,'Quelle est la marque ?',55,985),(330,5,'Quelle est la marque ?',55,988),(331,0,'Quelle est la marque ?',56,991),(332,1,'Quel est le % de sociétaire MACIF satisfaits ?',56,994),(333,2,'Quelle est la marque ?',56,997),(334,3,'Quelle est la marque ?',56,1000),(335,4,'Quelle est la marque ?',56,1003),(336,5,'Quelle est la marque ?',56,1006),(337,0,'Quelle est la marque ?',57,1009),(338,1,'Quelle est la marque ?',57,1012),(339,2,'Quelle est la marque ?',57,1015),(340,3,'Quel est le % de sociétaire MACIF satisfaits ?',57,1018),(341,4,'Quel est le produit ?',57,1021),(342,5,'Combien faites vous de kilomètre en plus ?',57,1024),(343,0,'Quel est le slogan ?',58,1027),(344,1,'Quelle est la marque ?',58,1030),(345,2,'Quelle est la marque ?',58,1033),(346,3,'Quel est le produit ?',58,1036),(347,4,'Quelle est la marque ?',58,1039),(348,5,'Quel est le véhicule ?',58,1042),(349,0,'Combien faites vous de kilomètre en plus ?',59,1045),(350,1,'Quelle est la marque ?',59,1048),(351,2,'Quelle est la marque ?',59,1051),(352,3,'Quel est le véhicule ?',59,1054),(353,4,'Quelle est la marque ?',59,1057),(354,5,'Quelle est la marque ?',59,1060),(355,0,'Quelle est la marque ?',60,1063),(356,1,'Quelle est la marque ?',60,1066),(357,2,'Quelle est la marque ?',60,1069),(358,3,'Quelle est la marque ?',60,1072),(359,4,'Quelle est la marque ?',60,1075),(360,5,'Quelle est la marque ?',60,1078),(361,0,'Quelle est la marque ?',61,1081),(362,1,'Quel est le véhicule ?',61,1084),(363,2,'Quel est le % de sociétaire MACIF satisfaits ?',61,1087),(364,3,'Quelle est la marque ?',61,1090),(365,4,'Quelle est la marque ?',61,1093),(366,5,'Quelle est la marque ?',61,1096),(367,0,'Quelle est la marque ?',62,1099),(368,1,'Quelle est la marque ?',62,1102),(369,2,'Quelle est la marque ?',62,1105),(370,3,'Quel est le prix du Top ?',62,1108),(371,4,'Quel est le slogan ?',62,1111),(372,5,'Quelle est la marque ?',62,1114),(373,0,'Quelle est la marque ?',63,1117),(374,1,'Quelle est la marque ?',63,1120),(375,2,'Quel est le véhicule ?',63,1123),(376,3,'Quelle est la marque ?',63,1126),(377,4,'Quelle est la marque ?',63,1129),(378,5,'Quelle est la marque ?',63,1132),(379,0,'Quelle est la marque ?',64,1135),(380,1,'Quel est le produit ?',64,1138),(381,2,'Quel est le véhicule ?',64,1141),(382,3,'Quelle est la marque ?',64,1144),(383,4,'Quelle est la marque ?',64,1147),(384,5,'Quel est le prix du Top ?',64,1150),(385,0,'Quelle est la marque ?',65,1153),(386,1,'Quel est le véhicule ?',65,1156),(387,2,'Quelle est la marque ?',65,1159),(388,3,'Quelle est la marque ?',65,1162),(389,4,'Quelle est la marque ?',65,1165),(390,5,'Quelle est la marque ?',65,1168),(391,0,'Quelle est la marque ?',66,1171),(392,1,'Quelle est la marque ?',66,1174),(393,2,'Quelle est la marque ?',66,1177),(394,3,'Quelle est la marque ?',66,1180),(395,4,'Quel est le produit ?',66,1183),(396,5,'Quelle est la marque ?',66,1186),(397,0,'Quelle est la marque ?',67,1189),(398,1,'Quel est le véhicule ?',67,1192),(399,2,'Quel est le % de sociétaire MACIF satisfaits ?',67,1195),(400,3,'Quelle est la marque ?',67,1198),(401,4,'Quel est le prix du Top ?',67,1201),(402,5,'Quelle est la marque ?',67,1204),(403,0,'Quel est le véhicule ?',68,1207),(404,1,'Quelle est la marque ?',68,1210),(405,2,'Quelle est la marque ?',68,1213),(406,3,'Quelle est la marque ?',68,1216),(407,4,'Quelle est la marque ?',68,1219),(408,5,'Quelle est la marque ?',68,1222),(409,0,'Quel est le produit ?',69,1225),(410,1,'Quel est le véhicule ?',69,1228),(411,2,'Quelle est la marque ?',69,1231),(412,3,'Quelle est la marque ?',69,1234),(413,4,'Quelle est la marque ?',69,1237),(414,5,'Quelle est la marque ?',69,1240),(415,0,'Quel est le produit ?',70,1243),(416,1,'Quelle est la marque ?',70,1246),(417,2,'Quel est le véhicule ?',70,1249),(418,3,'Quelle est la marque ?',70,1252),(419,4,'Quelle est la marque ?',70,1255),(420,5,'Quel est le véhicule ?',70,1258),(421,0,'Quelle est la marque ?',71,1261),(422,1,'Quel est le slogan ?',71,1264),(423,2,'Quelle est la marque ?',71,1267),(424,3,'Quel est le véhicule ?',71,1270),(425,4,'Quelle est la marque ?',71,1273),(426,5,'Quelle est la marque ?',71,1276),(427,0,'Quel est le véhicule ?',72,1279),(428,1,'Quelle est la marque ?',72,1282),(429,2,'Quelle est la marque ?',72,1285),(430,3,'Quelle est la marque ?',72,1288),(431,4,'Quelle est la marque ?',72,1291),(432,5,'Quelle est la marque ?',72,1294),(433,0,'Quel est le slogan ?',73,1297),(434,1,'Quelle est la marque ?',73,1300),(435,2,'Quelle est la marque ?',73,1303),(436,3,'Quelle est la marque ?',73,1306),(437,4,'Quelle est la marque ?',73,1309),(438,5,'Quelle est la marque ?',73,1312),(439,0,'Quelle est la marque ?',74,1315),(440,1,'Quelle est la marque ?',74,1318),(441,2,'Quelle est la marque ?',74,1321),(442,3,'Quel est le véhicule ?',74,1324),(443,4,'Quelle est la marque ?',74,1327),(444,5,'Quel est le prix du Top ?',74,1330),(445,0,'Quel est le prix du Top ?',75,1333),(446,1,'Combien faites vous de kilomètre en plus ?',75,1336),(447,2,'Quelle est la marque ?',75,1339),(448,3,'Quel est le véhicule ?',75,1342),(449,4,'Quelle est la marque ?',75,1345),(450,5,'Quelle est la marque ?',75,1348),(457,0,'Quelle est la marque ?',77,1369),(458,1,'Quelle est la marque ?',77,1372),(459,2,'Quelle est la marque ?',77,1375),(460,3,'Quelle est la marque ?',77,1378),(461,4,'Combien faites vous de kilomètre en plus ?',77,1381),(462,5,'Quel est le produit ?',77,1384),(463,0,'Quelle est la marque ?',78,1387),(464,1,'Quelle est la marque ?',78,1390),(465,2,'Quelle est la marque ?',78,1393),(466,3,'Quelle est la marque ?',78,1396),(467,4,'Quelle est la marque ?',78,1399),(468,5,'Quelle est la marque ?',78,1402),(469,0,'Quelle est la marque ?',79,1405),(470,1,'Quelle est la marque ?',79,1408),(471,2,'Quelle est la marque ?',79,1411),(472,3,'Quel est le % de sociétaire MACIF satisfaits ?',79,1414),(473,4,'Quelle est la marque ?',79,1417),(474,5,'Quelle est la marque ?',79,1420),(475,0,'Quel est le produit ?',80,1423),(476,1,'Quelle est la marque ?',80,1426),(477,2,'Quelle est la marque ?',80,1429),(478,3,'Quelle est la marque ?',80,1432),(479,4,'Quelle est la marque ?',80,1435),(480,5,'Quel est le véhicule ?',80,1438),(481,0,'Quel est le prix du Top ?',81,1441),(482,1,'Quelle est la marque ?',81,1444),(483,2,'Quel est le véhicule ?',81,1447),(484,3,'Quelle est la marque ?',81,1450),(485,4,'Combien faites vous de kilomètre en plus ?',81,1453),(486,5,'Quelle est la marque ?',81,1456),(487,0,'Quel est le véhicule ?',82,1459),(488,1,'Quelle est la marque ?',82,1462),(489,2,'Quelle est la marque ?',82,1465),(490,3,'Quel est le prix du Top ?',82,1468),(491,4,'Quel est le slogan ?',82,1471),(492,5,'Quelle est la marque ?',82,1474),(493,0,'Quelle est la marque ?',83,1477),(494,1,'Quel est le véhicule ?',83,1480),(495,2,'Quel est le produit ?',83,1483),(496,3,'Quelle est la marque ?',83,1486),(497,4,'Quelle est la marque ?',83,1489),(498,5,'Quel est le % de sociétaire MACIF satisfaits ?',83,1492),(499,0,'Quel est le véhicule ?',84,1495),(500,1,'Quelle est la marque ?',84,1498),(501,2,'Quelle est la marque ?',84,1501),(502,3,'Quelle est la marque ?',84,1504),(503,4,'Quelle est la marque ?',84,1507),(504,5,'Quelle est la marque ?',84,1510),(505,0,'Quelle est la marque ?',85,1513),(506,1,'Quelle est la marque ?',85,1516),(507,2,'Quelle est la marque ?',85,1519),(508,3,'Quelle est la marque ?',85,1522),(509,4,'Combien faites vous de kilomètre en plus ?',85,1525),(510,5,'Quelle est la marque ?',85,1528),(511,0,'Quelle est la marque ?',86,1531),(512,1,'Quelle est la marque ?',86,1534),(513,2,'Quelle est la marque ?',86,1537),(514,3,'Combien faites vous de kilomètre en plus ?',86,1540),(515,4,'Quelle est la marque ?',86,1543),(516,5,'Quelle est la marque ?',86,1546),(517,0,'Quelle est la marque ?',87,1549),(518,1,'Quelle est la marque ?',87,1552),(519,2,'Quel est le % de sociétaire MACIF satisfaits ?',87,1555),(520,3,'Quelle est la marque ?',87,1558),(521,4,'Quelle est la marque ?',87,1561),(522,5,'Quel est le véhicule ?',87,1564),(523,0,'Quelle est la marque ?',88,1567),(524,1,'Quel est le slogan ?',88,1570),(525,2,'Quelle est la marque ?',88,1573),(526,3,'Quelle est la marque ?',88,1576),(527,4,'Quelle est la marque ?',88,1579),(528,5,'Quel est le véhicule ?',88,1582),(529,0,'Quelle est la marque ?',89,1585),(530,1,'Quelle est la marque ?',89,1588),(531,2,'Quelle est la marque ?',89,1591),(532,3,'Quelle est la marque ?',89,1594),(533,4,'Quel est le véhicule ?',89,1597),(534,5,'Quelle est la marque ?',89,1600),(535,0,'Quelle est la marque ?',90,1603),(536,1,'Quelle est la marque ?',90,1606),(537,2,'Quel est le produit ?',90,1609),(538,3,'Quelle est la marque ?',90,1612),(539,4,'Quelle est la marque ?',90,1615),(540,5,'Quelle est la marque ?',90,1618),(541,0,'Quelle est la marque ?',91,1621),(542,1,'Quelle est la marque ?',91,1624),(543,2,'Quel est le véhicule ?',91,1627),(544,3,'Quelle est la marque ?',91,1630),(545,4,'Quelle est la marque ?',91,1633),(546,5,'Quelle est la marque ?',91,1636),(547,0,'Quel est le produit ?',92,1639),(548,1,'Quelle est la marque ?',92,1642),(549,2,'Quel est le slogan ?',92,1645),(550,3,'Quelle est la marque ?',92,1648),(551,4,'Quelle est la marque ?',92,1651),(552,5,'Quelle est la marque ?',92,1654),(553,0,'Quel est le slogan ?',93,1657),(554,1,'Quelle est la marque ?',93,1660),(555,2,'Quel est le % de sociétaire MACIF satisfaits ?',93,1663),(556,3,'Quelle est la marque ?',93,1666),(557,4,'Quelle est la marque ?',93,1669),(558,5,'Quelle est la marque ?',93,1672),(559,0,'Quel est le % de sociétaire MACIF satisfaits ?',94,1675),(560,1,'Quelle est la marque ?',94,1678),(561,2,'Quelle est la marque ?',94,1681),(562,3,'Quelle est la marque ?',94,1684),(563,4,'Quelle est la marque ?',94,1687),(564,5,'Quelle est la marque ?',94,1690);
/*!40000 ALTER TABLE `adchoise` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `adgame`
--

DROP TABLE IF EXISTS `adgame`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `adgame` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `generated` datetime DEFAULT NULL,
  `min_score` int(11) DEFAULT NULL,
  `status_game` int(11) DEFAULT NULL,
  `player_id` bigint(20) DEFAULT NULL,
  `score_id` bigint(20) DEFAULT NULL,
  `classe` varchar(31) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_53025fee922e4490bccccce68d3` (`player_id`),
  KEY `FK_7722fa75104d4487a83f189b9ad` (`score_id`),
  KEY `FK_2a18d5cd6eb44a248c1f15a7c7e` (`player_id`),
  KEY `FK_cd178e48e8a349e69f76c47428e` (`score_id`),
  KEY `FK_89cad157337440928fd771e8a97` (`player_id`),
  KEY `FK_60a54a7f9f6e4c0481993121753` (`score_id`),
  KEY `FK_190c149a943e4c44a9d539e6ae5` (`player_id`),
  KEY `FK_833f4fedc99a474d97aed19e949` (`score_id`),
  KEY `FK_1c7817c2ffb042528529749a0d4` (`player_id`),
  KEY `FK_d0999beca1b74cffb702a0f99e3` (`score_id`),
  KEY `FK_f3bc1c3704df4e0eb506478c9d3` (`player_id`),
  KEY `FK_45fa022970934ba1b98ae73c9a5` (`score_id`),
  KEY `FK_c8374f22c11b423380389e9c16a` (`player_id`),
  KEY `FK_7379ac609973491abe2e3f68ef4` (`score_id`),
  KEY `FK_e88d9a76dd5143179bfb2b42529` (`player_id`),
  KEY `FK_98ba9957dfda4b428e4a07f7725` (`score_id`),
  KEY `FK_60768e4f630349c3861bcc38c2a` (`player_id`),
  KEY `FK_15c1f5a8077c44bfbf57d4867e6` (`score_id`),
  KEY `FK_d146bc032b9b4a8784221ed23f1` (`player_id`),
  KEY `FK_4d1a97f0a0ff459d84cd3c00605` (`score_id`),
  KEY `FK_5af9740423564de9af62f582936` (`player_id`),
  KEY `FK_ce68753c4e5246d6b6aa9052350` (`score_id`),
  KEY `FK_c23f26c266f343189eacba7a367` (`player_id`),
  KEY `FK_00e6ba7fd46d4bb8beb1244ec74` (`score_id`),
  CONSTRAINT `FK_00e6ba7fd46d4bb8beb1244ec74` FOREIGN KEY (`score_id`) REFERENCES `ad_score` (`id`),
  CONSTRAINT `FK_15c1f5a8077c44bfbf57d4867e6` FOREIGN KEY (`score_id`) REFERENCES `ad_score` (`id`),
  CONSTRAINT `FK_190c149a943e4c44a9d539e6ae5` FOREIGN KEY (`player_id`) REFERENCES `player` (`id`),
  CONSTRAINT `FK_1c7817c2ffb042528529749a0d4` FOREIGN KEY (`player_id`) REFERENCES `player` (`id`),
  CONSTRAINT `FK_2a18d5cd6eb44a248c1f15a7c7e` FOREIGN KEY (`player_id`) REFERENCES `player` (`id`),
  CONSTRAINT `FK_45fa022970934ba1b98ae73c9a5` FOREIGN KEY (`score_id`) REFERENCES `ad_score` (`id`),
  CONSTRAINT `FK_4d1a97f0a0ff459d84cd3c00605` FOREIGN KEY (`score_id`) REFERENCES `ad_score` (`id`),
  CONSTRAINT `FK_53025fee922e4490bccccce68d3` FOREIGN KEY (`player_id`) REFERENCES `player` (`id`),
  CONSTRAINT `FK_60768e4f630349c3861bcc38c2a` FOREIGN KEY (`player_id`) REFERENCES `player` (`id`),
  CONSTRAINT `FK_60a54a7f9f6e4c0481993121753` FOREIGN KEY (`score_id`) REFERENCES `ad_score` (`id`),
  CONSTRAINT `FK_7379ac609973491abe2e3f68ef4` FOREIGN KEY (`score_id`) REFERENCES `ad_score` (`id`),
  CONSTRAINT `FK_7722fa75104d4487a83f189b9ad` FOREIGN KEY (`score_id`) REFERENCES `ad_score` (`id`),
  CONSTRAINT `FK_833f4fedc99a474d97aed19e949` FOREIGN KEY (`score_id`) REFERENCES `ad_score` (`id`),
  CONSTRAINT `FK_89cad157337440928fd771e8a97` FOREIGN KEY (`player_id`) REFERENCES `player` (`id`),
  CONSTRAINT `FK_98ba9957dfda4b428e4a07f7725` FOREIGN KEY (`score_id`) REFERENCES `ad_score` (`id`),
  CONSTRAINT `FK_c23f26c266f343189eacba7a367` FOREIGN KEY (`player_id`) REFERENCES `player` (`id`),
  CONSTRAINT `FK_c8374f22c11b423380389e9c16a` FOREIGN KEY (`player_id`) REFERENCES `player` (`id`),
  CONSTRAINT `FK_cd178e48e8a349e69f76c47428e` FOREIGN KEY (`score_id`) REFERENCES `ad_score` (`id`),
  CONSTRAINT `FK_d0999beca1b74cffb702a0f99e3` FOREIGN KEY (`score_id`) REFERENCES `ad_score` (`id`),
  CONSTRAINT `FK_d146bc032b9b4a8784221ed23f1` FOREIGN KEY (`player_id`) REFERENCES `player` (`id`),
  CONSTRAINT `FK_e88d9a76dd5143179bfb2b42529` FOREIGN KEY (`player_id`) REFERENCES `player` (`id`),
  CONSTRAINT `FK_f3bc1c3704df4e0eb506478c9d3` FOREIGN KEY (`player_id`) REFERENCES `player` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=95 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `adgame`
--

LOCK TABLES `adgame` WRITE;
/*!40000 ALTER TABLE `adgame` DISABLE KEYS */;
INSERT INTO `adgame` VALUES (1,'2013-11-14 11:32:48',0,2,1,1,''),(2,'2013-11-14 12:30:52',0,2,1,2,''),(3,'2013-11-14 13:20:19',0,2,1,3,''),(4,'2013-11-14 13:26:11',0,2,1,4,''),(5,'2013-11-14 13:48:17',0,2,1,5,''),(6,'2013-11-14 13:49:00',0,2,1,6,''),(7,'2013-11-14 13:49:37',0,2,1,7,''),(8,'2013-11-14 14:35:51',0,2,1,8,''),(9,'2013-11-14 14:35:53',0,2,1,9,''),(10,'2013-11-14 14:40:58',0,2,1,10,''),(11,'2013-11-14 14:42:31',0,2,1,11,''),(12,'2013-11-14 15:27:12',0,2,1,12,''),(13,'2013-11-14 15:30:32',0,2,1,13,''),(14,'2013-11-14 15:32:52',0,2,1,14,''),(15,'2013-11-14 15:34:44',0,2,1,15,''),(16,'2013-11-14 15:51:51',0,2,1,16,''),(17,'2013-11-14 15:59:29',0,2,1,17,''),(18,'2013-11-14 16:01:30',0,2,1,18,''),(19,'2013-11-14 16:03:04',0,2,1,19,''),(20,'2013-11-14 16:04:46',0,0,1,21,''),(21,'2013-11-14 16:13:48',0,0,1,23,''),(22,'2013-11-14 16:15:21',0,0,1,25,''),(23,'2013-11-14 16:15:45',0,0,1,27,''),(24,'2013-11-14 16:18:08',0,0,1,29,''),(25,'2013-11-14 16:18:52',0,0,1,31,''),(26,'2013-11-14 16:20:42',0,0,1,33,''),(27,'2013-11-14 16:23:29',0,0,1,35,''),(28,'2013-11-14 16:41:48',0,0,1,37,''),(29,'2013-11-18 10:45:18',0,2,1,38,''),(30,'2013-11-18 10:46:18',0,2,1,39,''),(31,'2013-11-18 10:46:21',0,2,1,40,''),(32,'2013-11-18 10:55:44',0,2,1,41,''),(33,'2013-11-18 10:56:18',0,0,1,43,''),(34,'2013-11-18 10:56:36',0,2,1,44,''),(35,'2013-11-18 11:02:06',0,0,1,46,''),(36,'2013-11-18 11:02:42',0,2,1,47,''),(37,'2013-11-18 11:03:17',0,0,1,49,''),(38,'2013-11-18 11:03:29',0,2,1,50,''),(39,'2013-11-18 11:10:23',0,2,1,51,''),(40,'2013-11-18 11:10:32',0,2,1,52,''),(41,'2013-11-18 11:39:52',0,2,1,53,''),(42,'2013-11-18 11:40:37',0,2,1,54,''),(43,'2013-11-18 11:49:03',0,2,1,55,''),(44,'2013-11-18 11:49:11',0,2,1,56,''),(45,'2013-11-18 11:49:37',0,2,1,57,''),(46,'2013-11-18 11:52:01',0,2,1,58,''),(47,'2013-11-18 11:53:57',0,2,1,59,''),(48,'2013-11-18 11:59:54',0,2,1,60,''),(49,'2013-11-18 12:01:05',0,2,1,61,''),(50,'2013-11-18 12:33:07',0,2,1,62,''),(51,'2013-11-18 13:46:07',0,2,1,63,''),(52,'2013-11-18 14:28:50',0,2,1,64,''),(53,'2013-11-18 14:29:19',0,2,1,65,''),(54,'2013-11-19 14:40:15',0,2,1,66,''),(55,'2013-11-19 14:41:30',0,2,1,67,''),(56,'2013-11-19 14:46:30',0,2,1,68,''),(57,'2013-11-19 14:48:32',0,2,1,69,''),(58,'2013-11-19 15:33:55',0,2,1,70,''),(59,'2013-11-19 15:34:04',0,2,1,71,''),(60,'2013-11-21 12:25:40',0,2,1,72,''),(61,'2013-11-21 12:29:58',0,2,1,73,''),(62,'2013-11-21 12:31:15',0,2,1,74,''),(63,'2013-11-21 12:32:26',0,2,1,75,''),(64,'2013-11-21 12:36:52',0,2,1,76,''),(65,'2013-11-21 12:39:01',0,2,1,77,''),(66,'2013-11-21 12:40:48',0,2,1,78,''),(67,'2013-11-21 12:44:10',0,2,1,79,''),(68,'2013-11-21 12:45:37',0,2,1,80,''),(69,'2013-11-21 12:46:16',0,0,1,82,''),(70,'2013-11-21 14:50:37',3,2,1,83,''),(71,'2013-11-21 14:52:36',3,2,1,84,''),(72,'2013-11-21 14:53:02',3,2,1,85,''),(73,'2013-11-21 14:53:46',3,2,1,86,''),(74,'2013-11-21 14:54:41',3,2,1,87,''),(75,'2013-11-21 14:56:49',3,2,1,88,''),(77,'2013-11-21 15:00:48',3,2,1,90,''),(78,'2013-11-21 15:01:47',3,2,1,91,''),(79,'2013-11-21 15:02:52',3,2,1,92,''),(80,'2013-11-21 15:02:59',3,2,1,93,''),(81,'2013-11-21 15:04:16',3,2,1,94,''),(82,'2013-11-21 15:04:45',3,2,1,95,''),(83,'2013-11-21 15:07:42',3,2,1,96,''),(84,'2013-11-21 15:19:25',3,2,1,97,''),(85,'2013-11-21 15:20:47',3,2,1,98,''),(86,'2013-11-21 15:21:50',3,2,1,99,''),(87,'2013-11-21 15:23:15',3,2,1,100,''),(88,'2013-11-21 15:24:32',3,2,1,101,''),(89,'2013-11-21 15:26:26',3,2,1,102,''),(90,'2013-11-21 15:54:41',3,2,1,103,''),(91,'2013-11-21 15:56:24',3,2,1,104,''),(92,'2013-11-21 16:00:03',3,0,1,106,''),(93,'2013-11-21 16:00:21',3,0,1,108,''),(94,'2013-11-21 17:07:26',3,0,1,110,'Music');
/*!40000 ALTER TABLE `adgame` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `adgame_media`
--

DROP TABLE IF EXISTS `adgame_media`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `adgame_media` (
  `adgame_id` bigint(20) NOT NULL,
  `media_id` bigint(20) NOT NULL,
  KEY `FK_f60e7372d92045bd9de06f96bfb` (`media_id`),
  KEY `FK_f52f814dd40e4a53b3534d05cdd` (`adgame_id`),
  CONSTRAINT `FK_f52f814dd40e4a53b3534d05cdd` FOREIGN KEY (`adgame_id`) REFERENCES `adgame` (`id`),
  CONSTRAINT `FK_f60e7372d92045bd9de06f96bfb` FOREIGN KEY (`media_id`) REFERENCES `media` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `adgame_media`
--

LOCK TABLES `adgame_media` WRITE;
/*!40000 ALTER TABLE `adgame_media` DISABLE KEYS */;
/*!40000 ALTER TABLE `adgame_media` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `adrule_country`
--

DROP TABLE IF EXISTS `adrule_country`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `adrule_country` (
  `adrule_id` bigint(20) NOT NULL,
  `country_id` bigint(20) NOT NULL,
  KEY `FK_d943ca1d4f7b4294b06dca72501` (`country_id`),
  KEY `FK_2b52c5099d44421c96e50c991aa` (`adrule_id`),
  CONSTRAINT `FK_2b52c5099d44421c96e50c991aa` FOREIGN KEY (`adrule_id`) REFERENCES `ad_rule` (`id`),
  CONSTRAINT `FK_d943ca1d4f7b4294b06dca72501` FOREIGN KEY (`country_id`) REFERENCES `country` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `adrule_country`
--

LOCK TABLES `adrule_country` WRITE;
/*!40000 ALTER TABLE `adrule_country` DISABLE KEYS */;
/*!40000 ALTER TABLE `adrule_country` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `album_artist`
--

DROP TABLE IF EXISTS `album_artist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `album_artist` (
  `music_id` bigint(20) NOT NULL,
  `album_id` bigint(20) NOT NULL,
  KEY `FK_e868b9879e5b40c8ad3f4c97bff` (`album_id`),
  KEY `FK_fb896fefeb674474afa94f3970b` (`music_id`),
  KEY `FK_ba8c3703199d428ba25130e0965` (`album_id`),
  CONSTRAINT `FK_ba8c3703199d428ba25130e0965` FOREIGN KEY (`album_id`) REFERENCES `media` (`id`),
  CONSTRAINT `FK_e868b9879e5b40c8ad3f4c97bff` FOREIGN KEY (`album_id`) REFERENCES `media` (`id`),
  CONSTRAINT `FK_fb896fefeb674474afa94f3970b` FOREIGN KEY (`music_id`) REFERENCES `media` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `album_artist`
--

LOCK TABLES `album_artist` WRITE;
/*!40000 ALTER TABLE `album_artist` DISABLE KEYS */;
/*!40000 ALTER TABLE `album_artist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `brand`
--

DROP TABLE IF EXISTS `brand`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `brand` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `logo` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `brand`
--

LOCK TABLES `brand` WRITE;
/*!40000 ALTER TABLE `brand` DISABLE KEYS */;
INSERT INTO `brand` VALUES (1,'lu.jpg','LU'),(2,'intermarche.jpg','Intermarché'),(3,'citroen.jpg','Citroën'),(4,'ingdirect.png','Ing Direct'),(5,'groupama.png','Groupama'),(6,'nissan.jpg','Nissan'),(7,'maif.gif','Maif'),(8,'lexus.gif','Lexus'),(9,'danone.jpg','Danone'),(10,'gillette.jpg','Gillette'),(11,'krys.png','Krys'),(12,'ford.jpg','Ford'),(13,'alsa.png','Alsa'),(14,'barilla.jpg','Barilla'),(15,'castorama.jpg','Castorama'),(16,'ctc_logo.png','CTC'),(17,'hm.jpg','H&M'),(18,'lancome.jpg','Lancome Paris'),(19,'michelin.jpg','Michelin'),(20,'microsoft.jpg','Microsoft'),(21,'numericable.jpg','Numéricable'),(22,'peugeot.jpg','Peugeot'),(23,'sfr.jpg','SFR'),(24,'stmichel.jpg','St Michel'),(25,'toyota.gif','Toyota'),(26,'wilkinson.jpg','Wilkinson'),(27,'logo-vahine.png','Vahine'),(28,'ca.jpg','Crédit agricole'),(29,'auchan.png','Auchan'),(30,'macif.gif','MACIF'),(31,'pj.png','Pages jaunes');
/*!40000 ALTER TABLE `brand` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `country`
--

DROP TABLE IF EXISTS `country`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `country` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_931c11ec21054b949076f58b863` (`code`),
  UNIQUE KEY `UK_63dcc65e1a244b32b57c1feeb60` (`code`),
  UNIQUE KEY `UK_aa93dc282154456dbf45adc57e0` (`code`),
  UNIQUE KEY `UK_1f546b1f7841404a858c5a8fef1` (`code`),
  UNIQUE KEY `UK_9bba69cd6afe44dfad3b0292ca8` (`code`),
  UNIQUE KEY `UK_47f2e19143204776aadd0081889` (`code`),
  UNIQUE KEY `UK_9a22284872d84171a25f04c8e4b` (`code`),
  UNIQUE KEY `UK_01f302b04d4144d3bcc30924c69` (`code`),
  UNIQUE KEY `UK_1e333b02da114f6e8502917375f` (`code`),
  UNIQUE KEY `UK_91b9842ed51f45488d68bedf2d4` (`code`),
  UNIQUE KEY `UK_5c94ad27bf334e4c8ce689154a6` (`code`),
  UNIQUE KEY `UK_df4326a67843423d83cef656617` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `country`
--

LOCK TABLES `country` WRITE;
/*!40000 ALTER TABLE `country` DISABLE KEYS */;
INSERT INTO `country` VALUES (1,'FR_fr');
/*!40000 ALTER TABLE `country` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `genre`
--

DROP TABLE IF EXISTS `genre`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `genre` (
  `classe` varchar(31) NOT NULL,
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code_genre` varchar(255) DEFAULT NULL,
  `genre` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `genre`
--

LOCK TABLES `genre` WRITE;
/*!40000 ALTER TABLE `genre` DISABLE KEYS */;
/*!40000 ALTER TABLE `genre` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `goose_case`
--

DROP TABLE IF EXISTS `goose_case`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `goose_case` (
  `classe` varchar(31) NOT NULL,
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `number` int(11) DEFAULT NULL,
  `value` double DEFAULT NULL,
  `gooselevel_id` bigint(20) DEFAULT NULL,
  `goosejump_id` bigint(20) DEFAULT NULL,
  `reduction_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_d4c06639dc5f43488d97bd72946` (`gooselevel_id`),
  KEY `FK_178fb0bbfc9a47928e329815cb0` (`goosejump_id`),
  KEY `FK_9bd368af0a464587a20b95f6e33` (`reduction_id`),
  KEY `FK_460f586df39548c79987af000ce` (`gooselevel_id`),
  KEY `FK_dc1572d5763248a48dadec6f17f` (`goosejump_id`),
  KEY `FK_228ededb9b9b49939e9bd8bdcfe` (`reduction_id`),
  KEY `FK_fba8718a3de04dc79682b82a5d2` (`gooselevel_id`),
  KEY `FK_8cfd2cc0b2fd43f2901cb5a3f12` (`goosejump_id`),
  KEY `FK_26a5c4bb3d50417db54da1cd33c` (`reduction_id`),
  KEY `FK_ac4ff87e297b43a49932937061b` (`gooselevel_id`),
  KEY `FK_84859ee02f6c4be0b518f314774` (`goosejump_id`),
  KEY `FK_9b333037e60f4f63adc9b8aef62` (`reduction_id`),
  KEY `FK_d87020043dbd40c38f14db6a4d0` (`gooselevel_id`),
  KEY `FK_0d18281d54cb4cc6beecf88eae1` (`goosejump_id`),
  KEY `FK_a7086aa5839f4c08bf6d1e15afd` (`reduction_id`),
  KEY `FK_0046c1b2cca449fb8664c86673a` (`gooselevel_id`),
  KEY `FK_5400cbe3d0d0492595a3e828413` (`goosejump_id`),
  KEY `FK_74aab561ce8e4cd191c79d0a27c` (`reduction_id`),
  KEY `FK_ea199f3a9c2e4b68b2f6d0ab1be` (`gooselevel_id`),
  KEY `FK_02e647194ade4538a25c2307c51` (`goosejump_id`),
  KEY `FK_0c59721b4d1c492a9ac95d7a09f` (`reduction_id`),
  KEY `FK_0ea1b7cef68b4d7a8aec841f202` (`gooselevel_id`),
  KEY `FK_85ce1d02252a4f84a20373c50cd` (`goosejump_id`),
  KEY `FK_157213b53a094abf9ff46476732` (`reduction_id`),
  KEY `FK_5bd5c2986cfd477c889e7d68bfa` (`gooselevel_id`),
  KEY `FK_b0c39ab8dfaf4d5d961dc86b6a8` (`goosejump_id`),
  KEY `FK_4e8fea9df600470693b7b4f5302` (`reduction_id`),
  KEY `FK_8a23792114004b87a67fc11338b` (`gooselevel_id`),
  KEY `FK_6ecdcf2b48a0418fb112ecbadd7` (`goosejump_id`),
  KEY `FK_5fa3ff6bcb6b4e74bedab3ef4b6` (`reduction_id`),
  KEY `FK_2e1d7cc8229e45d5823b1e1cea6` (`gooselevel_id`),
  KEY `FK_baf5ab44fe344da9ade554cc51f` (`goosejump_id`),
  KEY `FK_18f3e140e09b4a549325c0a25a4` (`reduction_id`),
  KEY `FK_d7c50c896bea48a1a6baa2893a8` (`gooselevel_id`),
  KEY `FK_0f9379447b72463c8dff27bd546` (`goosejump_id`),
  KEY `FK_b6bc376f22a84c89963daa5c85d` (`reduction_id`),
  CONSTRAINT `FK_0046c1b2cca449fb8664c86673a` FOREIGN KEY (`gooselevel_id`) REFERENCES `goose_level` (`id`),
  CONSTRAINT `FK_02e647194ade4538a25c2307c51` FOREIGN KEY (`goosejump_id`) REFERENCES `goose_case` (`id`),
  CONSTRAINT `FK_0c59721b4d1c492a9ac95d7a09f` FOREIGN KEY (`reduction_id`) REFERENCES `reduction` (`id`),
  CONSTRAINT `FK_0d18281d54cb4cc6beecf88eae1` FOREIGN KEY (`goosejump_id`) REFERENCES `goose_case` (`id`),
  CONSTRAINT `FK_0ea1b7cef68b4d7a8aec841f202` FOREIGN KEY (`gooselevel_id`) REFERENCES `goose_level` (`id`),
  CONSTRAINT `FK_0f9379447b72463c8dff27bd546` FOREIGN KEY (`goosejump_id`) REFERENCES `goose_case` (`id`),
  CONSTRAINT `FK_157213b53a094abf9ff46476732` FOREIGN KEY (`reduction_id`) REFERENCES `reduction` (`id`),
  CONSTRAINT `FK_178fb0bbfc9a47928e329815cb0` FOREIGN KEY (`goosejump_id`) REFERENCES `goose_case` (`id`),
  CONSTRAINT `FK_228ededb9b9b49939e9bd8bdcfe` FOREIGN KEY (`reduction_id`) REFERENCES `reduction` (`id`),
  CONSTRAINT `FK_26a5c4bb3d50417db54da1cd33c` FOREIGN KEY (`reduction_id`) REFERENCES `reduction` (`id`),
  CONSTRAINT `FK_460f586df39548c79987af000ce` FOREIGN KEY (`gooselevel_id`) REFERENCES `goose_level` (`id`),
  CONSTRAINT `FK_4e8fea9df600470693b7b4f5302` FOREIGN KEY (`reduction_id`) REFERENCES `reduction` (`id`),
  CONSTRAINT `FK_5400cbe3d0d0492595a3e828413` FOREIGN KEY (`goosejump_id`) REFERENCES `goose_case` (`id`),
  CONSTRAINT `FK_5bd5c2986cfd477c889e7d68bfa` FOREIGN KEY (`gooselevel_id`) REFERENCES `goose_level` (`id`),
  CONSTRAINT `FK_5fa3ff6bcb6b4e74bedab3ef4b6` FOREIGN KEY (`reduction_id`) REFERENCES `reduction` (`id`),
  CONSTRAINT `FK_6ecdcf2b48a0418fb112ecbadd7` FOREIGN KEY (`goosejump_id`) REFERENCES `goose_case` (`id`),
  CONSTRAINT `FK_74aab561ce8e4cd191c79d0a27c` FOREIGN KEY (`reduction_id`) REFERENCES `reduction` (`id`),
  CONSTRAINT `FK_84859ee02f6c4be0b518f314774` FOREIGN KEY (`goosejump_id`) REFERENCES `goose_case` (`id`),
  CONSTRAINT `FK_85ce1d02252a4f84a20373c50cd` FOREIGN KEY (`goosejump_id`) REFERENCES `goose_case` (`id`),
  CONSTRAINT `FK_8a23792114004b87a67fc11338b` FOREIGN KEY (`gooselevel_id`) REFERENCES `goose_level` (`id`),
  CONSTRAINT `FK_8cfd2cc0b2fd43f2901cb5a3f12` FOREIGN KEY (`goosejump_id`) REFERENCES `goose_case` (`id`),
  CONSTRAINT `FK_9b333037e60f4f63adc9b8aef62` FOREIGN KEY (`reduction_id`) REFERENCES `reduction` (`id`),
  CONSTRAINT `FK_9bd368af0a464587a20b95f6e33` FOREIGN KEY (`reduction_id`) REFERENCES `reduction` (`id`),
  CONSTRAINT `FK_a7086aa5839f4c08bf6d1e15afd` FOREIGN KEY (`reduction_id`) REFERENCES `reduction` (`id`),
  CONSTRAINT `FK_ac4ff87e297b43a49932937061b` FOREIGN KEY (`gooselevel_id`) REFERENCES `goose_level` (`id`),
  CONSTRAINT `FK_b0c39ab8dfaf4d5d961dc86b6a8` FOREIGN KEY (`goosejump_id`) REFERENCES `goose_case` (`id`),
  CONSTRAINT `FK_b6bc376f22a84c89963daa5c85d` FOREIGN KEY (`reduction_id`) REFERENCES `reduction` (`id`),
  CONSTRAINT `FK_d4c06639dc5f43488d97bd72946` FOREIGN KEY (`gooselevel_id`) REFERENCES `goose_level` (`id`),
  CONSTRAINT `FK_d7c50c896bea48a1a6baa2893a8` FOREIGN KEY (`gooselevel_id`) REFERENCES `goose_level` (`id`),
  CONSTRAINT `FK_d87020043dbd40c38f14db6a4d0` FOREIGN KEY (`gooselevel_id`) REFERENCES `goose_level` (`id`),
  CONSTRAINT `FK_dc1572d5763248a48dadec6f17f` FOREIGN KEY (`goosejump_id`) REFERENCES `goose_case` (`id`),
  CONSTRAINT `FK_ea199f3a9c2e4b68b2f6d0ab1be` FOREIGN KEY (`gooselevel_id`) REFERENCES `goose_level` (`id`),
  CONSTRAINT `FK_fba8718a3de04dc79682b82a5d2` FOREIGN KEY (`gooselevel_id`) REFERENCES `goose_level` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=82 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `goose_case`
--

LOCK TABLES `goose_case` WRITE;
/*!40000 ALTER TABLE `goose_case` DISABLE KEYS */;
INSERT INTO `goose_case` VALUES ('StartLevel',1,1,0,1,NULL,NULL),('EndLevel',2,63,0,1,NULL,NULL),('AdPot',3,2,0.15,1,NULL,NULL),('AdPot',4,3,0.15,1,NULL,NULL),('AdPot',5,4,0.15,1,NULL,NULL),('AdPot',6,5,0.15,1,NULL,NULL),('Jump',7,6,0,1,13,NULL),('AdPot',8,7,0.15,1,NULL,NULL),('AdPot',9,8,0.15,1,NULL,NULL),('AdPot',10,9,0.15,1,NULL,NULL),('AdPot',11,10,0.15,1,NULL,NULL),('AdPot',12,11,0.15,1,NULL,NULL),('AdPot',13,12,0.15,1,NULL,NULL),('AdPot',14,13,0.15,1,NULL,NULL),('AdPot',15,14,0.15,1,NULL,NULL),('AdPot',16,15,0.15,1,NULL,NULL),('AdPot',17,16,0.15,1,NULL,NULL),('AdPot',18,17,0.15,1,NULL,NULL),('AdPot',19,18,0.15,1,NULL,NULL),('Jail',20,19,0,1,NULL,NULL),('AdPot',21,20,0.15,1,NULL,NULL),('AdPot',22,21,0.15,1,NULL,NULL),('AdPot',23,22,0.15,1,NULL,NULL),('AdPot',24,23,0.15,1,NULL,NULL),('AdPot',25,24,0.15,1,NULL,NULL),('AdPot',26,25,0.15,1,NULL,NULL),('AdPot',27,26,0.15,1,NULL,NULL),('AdPot',28,27,0.15,1,NULL,NULL),('AdPot',29,28,0.15,1,NULL,NULL),('AdPot',30,29,0.15,1,NULL,NULL),('AdPot',31,30,0.15,1,NULL,NULL),('Jail',32,31,0,1,NULL,NULL),('AdPot',33,32,0.15,1,NULL,NULL),('AdPot',34,33,0.15,1,NULL,NULL),('AdPot',35,34,0.15,1,NULL,NULL),('AdPot',36,35,0.15,1,NULL,NULL),('AdPot',37,36,0.15,1,NULL,NULL),('AdPot',38,37,0.15,1,NULL,NULL),('AdPot',39,38,0.15,1,NULL,NULL),('AdPot',40,39,0.15,1,NULL,NULL),('AdPot',41,40,0.15,1,NULL,NULL),('AdPot',42,41,0.15,1,NULL,NULL),('Jump',43,42,0,1,30,NULL),('AdPot',44,43,0.15,1,NULL,NULL),('AdPot',45,44,0.15,1,NULL,NULL),('AdPot',46,45,0.15,1,NULL,NULL),('AdPot',47,46,0.15,1,NULL,NULL),('AdPot',48,47,0.15,1,NULL,NULL),('AdPot',49,48,0.15,1,NULL,NULL),('AdPot',50,49,0.15,1,NULL,NULL),('AdPot',51,50,0.15,1,NULL,NULL),('AdPot',52,51,0.15,1,NULL,NULL),('Jail',53,52,0,1,NULL,NULL),('AdPot',54,53,0.15,1,NULL,NULL),('AdPot',55,54,0.15,1,NULL,NULL),('AdPot',56,55,0.15,1,NULL,NULL),('AdPot',57,56,0.15,1,NULL,NULL),('AdPot',58,57,0.15,1,NULL,NULL),('Dead',59,58,0,1,NULL,NULL),('AdPot',60,59,0.15,1,NULL,NULL),('AdPot',61,60,0.15,1,NULL,NULL),('AdPot',62,61,0.15,1,NULL,NULL),('AdPot',63,62,0.15,1,NULL,NULL),('StartLevel',64,1,0,2,NULL,NULL),('EndLevel',65,10,0.15,2,NULL,NULL),('AdPot',66,2,0.15,2,NULL,NULL),('AdPot',67,3,0.15,2,NULL,NULL),('AdPot',68,4,0.15,2,NULL,NULL),('AdPot',69,5,0.15,2,NULL,NULL),('AdPot',70,6,0.15,2,NULL,NULL),('AdPot',71,7,0.15,2,NULL,NULL),('AdPot',72,8,0.15,2,NULL,NULL),('AdPot',73,9,0.15,2,NULL,NULL),('StartLevel',74,1,0,3,NULL,NULL),('EndLevel',75,7,0.15,3,NULL,NULL),('AdPot',76,2,0,3,NULL,NULL),('AdPot',77,3,0,3,NULL,NULL),('Win',78,4,0,3,NULL,NULL),('AdPot',79,5,0,3,NULL,NULL),('AdPot',80,6,0,3,NULL,NULL);
/*!40000 ALTER TABLE `goose_case` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `goose_game`
--

DROP TABLE IF EXISTS `goose_game`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `goose_game` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `goose_game`
--

LOCK TABLES `goose_game` WRITE;
/*!40000 ALTER TABLE `goose_game` DISABLE KEYS */;
INSERT INTO `goose_game` VALUES (1),(2);
/*!40000 ALTER TABLE `goose_game` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `goose_level`
--

DROP TABLE IF EXISTS `goose_level`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `goose_level` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `level` bigint(20) DEFAULT NULL,
  `value` double DEFAULT NULL,
  `end_id` bigint(20) DEFAULT NULL,
  `start_id` bigint(20) DEFAULT NULL,
  `goose_game_id` bigint(20) DEFAULT NULL,
  `min_score` int(11) DEFAULT NULL,
  `nb_max_ad_by_play` int(11) DEFAULT NULL,
  `multiple` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_95677f3ff1a14f2d9285f4b561d` (`end_id`),
  KEY `FK_97cb2caddc6540d39b4ca6a4374` (`start_id`),
  KEY `FK_87e89160bd044a82862a40748bc` (`goose_game_id`),
  KEY `FK_4c30baa26acd4257b226536a91c` (`end_id`),
  KEY `FK_4ca1cfc0ece14e4c892433542ef` (`start_id`),
  KEY `FK_d022cf5ff33946259a9512d1c1d` (`goose_game_id`),
  KEY `FK_328475b9f75042748d95a396ff9` (`end_id`),
  KEY `FK_b7b13221239648bab8e0d080de4` (`start_id`),
  KEY `FK_2c7d419634ad456aa5a2fb2dee0` (`goose_game_id`),
  KEY `FK_5db51db9ba5d449f8c5880f0086` (`end_id`),
  KEY `FK_f6cdece4dd0b49ab89b9bf9ec0e` (`start_id`),
  KEY `FK_3994c682921e45cf92241788b94` (`goose_game_id`),
  KEY `FK_28fe8c876e3e4b86814a3d51a45` (`end_id`),
  KEY `FK_5a9c465ffe394aeb9d8afdeb83f` (`start_id`),
  KEY `FK_8a8a565aef7b4a15aae1ab4827e` (`goose_game_id`),
  KEY `FK_45bc8740a2a94e44887fa725b2c` (`end_id`),
  KEY `FK_93132bd54f124befabc4be24706` (`start_id`),
  KEY `FK_bf18d4cf1e6e4a5d81c46971e39` (`goose_game_id`),
  KEY `FK_30bd725895414fb98f14cf78bed` (`end_id`),
  KEY `FK_369d97627fd647b5b1d49c008aa` (`start_id`),
  KEY `FK_6e619b75237344da9f469a1d22b` (`goose_game_id`),
  KEY `FK_3cd8b992646042b7927eb45fccc` (`end_id`),
  KEY `FK_84e665af78e442278183eb36b01` (`start_id`),
  KEY `FK_1fde5e3062094d3ab8589747f04` (`goose_game_id`),
  KEY `FK_40e4dfb95cf642d489ef723e074` (`end_id`),
  KEY `FK_1f2990caf7f64bdd953e2db2216` (`start_id`),
  KEY `FK_33e4d41360ca44ed96465a37ae0` (`goose_game_id`),
  KEY `FK_ab6e0879e3354d96bf74b880a56` (`end_id`),
  KEY `FK_7cc10f88763b449194153c5d3b5` (`start_id`),
  KEY `FK_d271d9970b6242edba3a57d8f25` (`goose_game_id`),
  KEY `FK_233a069946fb4210b9aa61b70be` (`end_id`),
  KEY `FK_5b015f2423944e12a36d11e3ab2` (`start_id`),
  KEY `FK_2b673930e57547ff9873bd01ff9` (`goose_game_id`),
  KEY `FK_c5367b5b197a43bd98f31f9ae95` (`end_id`),
  KEY `FK_336a9e2099924d01917e0064ecf` (`start_id`),
  KEY `FK_0aa7514f7e6543b1bb13b67f115` (`goose_game_id`),
  CONSTRAINT `FK_0aa7514f7e6543b1bb13b67f115` FOREIGN KEY (`goose_game_id`) REFERENCES `goose_game` (`id`),
  CONSTRAINT `FK_1f2990caf7f64bdd953e2db2216` FOREIGN KEY (`start_id`) REFERENCES `goose_case` (`id`),
  CONSTRAINT `FK_1fde5e3062094d3ab8589747f04` FOREIGN KEY (`goose_game_id`) REFERENCES `goose_game` (`id`),
  CONSTRAINT `FK_28fe8c876e3e4b86814a3d51a45` FOREIGN KEY (`end_id`) REFERENCES `goose_case` (`id`),
  CONSTRAINT `FK_2c7d419634ad456aa5a2fb2dee0` FOREIGN KEY (`goose_game_id`) REFERENCES `goose_game` (`id`),
  CONSTRAINT `FK_30bd725895414fb98f14cf78bed` FOREIGN KEY (`end_id`) REFERENCES `goose_case` (`id`),
  CONSTRAINT `FK_328475b9f75042748d95a396ff9` FOREIGN KEY (`end_id`) REFERENCES `goose_case` (`id`),
  CONSTRAINT `FK_336a9e2099924d01917e0064ecf` FOREIGN KEY (`start_id`) REFERENCES `goose_case` (`id`),
  CONSTRAINT `FK_33e4d41360ca44ed96465a37ae0` FOREIGN KEY (`goose_game_id`) REFERENCES `goose_game` (`id`),
  CONSTRAINT `FK_369d97627fd647b5b1d49c008aa` FOREIGN KEY (`start_id`) REFERENCES `goose_case` (`id`),
  CONSTRAINT `FK_3994c682921e45cf92241788b94` FOREIGN KEY (`goose_game_id`) REFERENCES `goose_game` (`id`),
  CONSTRAINT `FK_3cd8b992646042b7927eb45fccc` FOREIGN KEY (`end_id`) REFERENCES `goose_case` (`id`),
  CONSTRAINT `FK_40e4dfb95cf642d489ef723e074` FOREIGN KEY (`end_id`) REFERENCES `goose_case` (`id`),
  CONSTRAINT `FK_45bc8740a2a94e44887fa725b2c` FOREIGN KEY (`end_id`) REFERENCES `goose_case` (`id`),
  CONSTRAINT `FK_4c30baa26acd4257b226536a91c` FOREIGN KEY (`end_id`) REFERENCES `goose_case` (`id`),
  CONSTRAINT `FK_4ca1cfc0ece14e4c892433542ef` FOREIGN KEY (`start_id`) REFERENCES `goose_case` (`id`),
  CONSTRAINT `FK_5a9c465ffe394aeb9d8afdeb83f` FOREIGN KEY (`start_id`) REFERENCES `goose_case` (`id`),
  CONSTRAINT `FK_5db51db9ba5d449f8c5880f0086` FOREIGN KEY (`end_id`) REFERENCES `goose_case` (`id`),
  CONSTRAINT `FK_6e619b75237344da9f469a1d22b` FOREIGN KEY (`goose_game_id`) REFERENCES `goose_game` (`id`),
  CONSTRAINT `FK_7cc10f88763b449194153c5d3b5` FOREIGN KEY (`start_id`) REFERENCES `goose_case` (`id`),
  CONSTRAINT `FK_84e665af78e442278183eb36b01` FOREIGN KEY (`start_id`) REFERENCES `goose_case` (`id`),
  CONSTRAINT `FK_87e89160bd044a82862a40748bc` FOREIGN KEY (`goose_game_id`) REFERENCES `goose_game` (`id`),
  CONSTRAINT `FK_8a8a565aef7b4a15aae1ab4827e` FOREIGN KEY (`goose_game_id`) REFERENCES `goose_game` (`id`),
  CONSTRAINT `FK_93132bd54f124befabc4be24706` FOREIGN KEY (`start_id`) REFERENCES `goose_case` (`id`),
  CONSTRAINT `FK_95677f3ff1a14f2d9285f4b561d` FOREIGN KEY (`end_id`) REFERENCES `goose_case` (`id`),
  CONSTRAINT `FK_97cb2caddc6540d39b4ca6a4374` FOREIGN KEY (`start_id`) REFERENCES `goose_case` (`id`),
  CONSTRAINT `FK_ab6e0879e3354d96bf74b880a56` FOREIGN KEY (`end_id`) REFERENCES `goose_case` (`id`),
  CONSTRAINT `FK_b7b13221239648bab8e0d080de4` FOREIGN KEY (`start_id`) REFERENCES `goose_case` (`id`),
  CONSTRAINT `FK_bf18d4cf1e6e4a5d81c46971e39` FOREIGN KEY (`goose_game_id`) REFERENCES `goose_game` (`id`),
  CONSTRAINT `FK_c5367b5b197a43bd98f31f9ae95` FOREIGN KEY (`end_id`) REFERENCES `goose_case` (`id`),
  CONSTRAINT `FK_d022cf5ff33946259a9512d1c1d` FOREIGN KEY (`goose_game_id`) REFERENCES `goose_game` (`id`),
  CONSTRAINT `FK_d271d9970b6242edba3a57d8f25` FOREIGN KEY (`goose_game_id`) REFERENCES `goose_game` (`id`),
  CONSTRAINT `FK_f6cdece4dd0b49ab89b9bf9ec0e` FOREIGN KEY (`start_id`) REFERENCES `goose_case` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `goose_level`
--

LOCK TABLES `goose_level` WRITE;
/*!40000 ALTER TABLE `goose_level` DISABLE KEYS */;
INSERT INTO `goose_level` VALUES (1,0,3.7499999999999987,2,1,1,0,6,1),(2,1,0,65,64,1,NULL,NULL,1),(3,1,0,75,74,2,3,6,0);
/*!40000 ALTER TABLE `goose_level` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `goose_token`
--

DROP TABLE IF EXISTS `goose_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `goose_token` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `goosecase_id` bigint(20) DEFAULT NULL,
  `player_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_6c1775cc8e974a96b5848cc9600` (`goosecase_id`),
  KEY `FK_bae7d339685c475e99c65adec3f` (`goosecase_id`),
  KEY `FK_a4d3a19e0840416a91f1cfc07bc` (`goosecase_id`),
  KEY `FK_ea09d461c50344b7af76272c137` (`goosecase_id`),
  KEY `FK_ec576e5bcc8b421281680d1a901` (`goosecase_id`),
  KEY `FK_ebd3245fbd724db88b91b57fa6c` (`goosecase_id`),
  KEY `FK_71e6e8e87aea45beaed270a96e1` (`goosecase_id`),
  KEY `FK_3ca22d292f6a4d2881331e79aaf` (`goosecase_id`),
  KEY `FK_96917aae95dc47cf9e2a076d73d` (`goosecase_id`),
  KEY `FK_f46aa4dd0d3546f089ee3569eb5` (`goosecase_id`),
  KEY `FK_77f354e4f9ad4741afead6e58a4` (`goosecase_id`),
  KEY `FK_7436af49881b4bdd815653409b0` (`goosecase_id`),
  KEY `FK_dfd0c74758ef4d6184935fabb21` (`player_id`),
  CONSTRAINT `FK_dfd0c74758ef4d6184935fabb21` FOREIGN KEY (`player_id`) REFERENCES `player` (`id`),
  CONSTRAINT `FK_3ca22d292f6a4d2881331e79aaf` FOREIGN KEY (`goosecase_id`) REFERENCES `goose_case` (`id`),
  CONSTRAINT `FK_6c1775cc8e974a96b5848cc9600` FOREIGN KEY (`goosecase_id`) REFERENCES `goose_case` (`id`),
  CONSTRAINT `FK_71e6e8e87aea45beaed270a96e1` FOREIGN KEY (`goosecase_id`) REFERENCES `goose_case` (`id`),
  CONSTRAINT `FK_7436af49881b4bdd815653409b0` FOREIGN KEY (`goosecase_id`) REFERENCES `goose_case` (`id`),
  CONSTRAINT `FK_96917aae95dc47cf9e2a076d73d` FOREIGN KEY (`goosecase_id`) REFERENCES `goose_case` (`id`),
  CONSTRAINT `FK_a4d3a19e0840416a91f1cfc07bc` FOREIGN KEY (`goosecase_id`) REFERENCES `goose_case` (`id`),
  CONSTRAINT `FK_bae7d339685c475e99c65adec3f` FOREIGN KEY (`goosecase_id`) REFERENCES `goose_case` (`id`),
  CONSTRAINT `FK_ea09d461c50344b7af76272c137` FOREIGN KEY (`goosecase_id`) REFERENCES `goose_case` (`id`),
  CONSTRAINT `FK_ebd3245fbd724db88b91b57fa6c` FOREIGN KEY (`goosecase_id`) REFERENCES `goose_case` (`id`),
  CONSTRAINT `FK_ec576e5bcc8b421281680d1a901` FOREIGN KEY (`goosecase_id`) REFERENCES `goose_case` (`id`),
  CONSTRAINT `FK_f46aa4dd0d3546f089ee3569eb5` FOREIGN KEY (`goosecase_id`) REFERENCES `goose_case` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `goose_token`
--

LOCK TABLES `goose_token` WRITE;
/*!40000 ALTER TABLE `goose_token` DISABLE KEYS */;
INSERT INTO `goose_token` VALUES (1,33,NULL),(2,19,1),(3,75,1);
/*!40000 ALTER TABLE `goose_token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `goose_win`
--

DROP TABLE IF EXISTS `goose_win`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `goose_win` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `status` int(11) DEFAULT NULL,
  `value` double DEFAULT NULL,
  `windate` datetime DEFAULT NULL,
  `gooselevel_id` bigint(20) DEFAULT NULL,
  `player_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_d7f2f623983d4daaba741d1c99c` (`gooselevel_id`),
  KEY `FK_51b2dc58ba56461d8fb89319583` (`player_id`),
  CONSTRAINT `FK_51b2dc58ba56461d8fb89319583` FOREIGN KEY (`player_id`) REFERENCES `player` (`id`),
  CONSTRAINT `FK_d7f2f623983d4daaba741d1c99c` FOREIGN KEY (`gooselevel_id`) REFERENCES `goose_level` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `goose_win`
--

LOCK TABLES `goose_win` WRITE;
/*!40000 ALTER TABLE `goose_win` DISABLE KEYS */;
INSERT INTO `goose_win` VALUES (1,0,0,'2013-11-21 14:54:17',3,1),(2,0,0,'2013-11-21 14:54:17',3,1),(4,0,0,'2013-11-21 16:00:35',3,1),(5,0,0,'2013-11-21 16:00:35',3,1),(6,0,0,'2013-11-21 17:07:42',3,1),(7,0,0,'2013-11-21 17:07:42',3,1);
/*!40000 ALTER TABLE `goose_win` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `media`
--

DROP TABLE IF EXISTS `media`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `media` (
  `classe` varchar(31) NOT NULL,
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `duration` bigint(20) DEFAULT NULL,
  `file` varchar(255) DEFAULT NULL,
  `img_presentation` varchar(255) DEFAULT NULL,
  `jacket` varchar(255) DEFAULT NULL,
  `nb_ads_needed` int(11) DEFAULT NULL,
  `release_date` date DEFAULT NULL,
  `thumb_jacket` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `mp3sample` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `media`
--

LOCK TABLES `media` WRITE;
/*!40000 ALTER TABLE `media` DISABLE KEYS */;
/*!40000 ALTER TABLE `media` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `media_genre`
--

DROP TABLE IF EXISTS `media_genre`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `media_genre` (
  `media_id` bigint(20) NOT NULL,
  `genre_id` bigint(20) NOT NULL,
  KEY `FK_44059c4c1f70472ca4835f08bef` (`genre_id`),
  KEY `FK_38eba79148b1436b87e44da154a` (`media_id`),
  CONSTRAINT `FK_38eba79148b1436b87e44da154a` FOREIGN KEY (`media_id`) REFERENCES `media` (`id`),
  CONSTRAINT `FK_44059c4c1f70472ca4835f08bef` FOREIGN KEY (`genre_id`) REFERENCES `genre` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `media_genre`
--

LOCK TABLES `media_genre` WRITE;
/*!40000 ALTER TABLE `media_genre` DISABLE KEYS */;
/*!40000 ALTER TABLE `media_genre` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `media_productor`
--

DROP TABLE IF EXISTS `media_productor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `media_productor` (
  `media_id` bigint(20) NOT NULL,
  `productor_id` bigint(20) NOT NULL,
  KEY `FK_2bbc8de09d45441790865452e96` (`productor_id`),
  KEY `FK_980c82a02e52408cb722faffb9b` (`media_id`),
  CONSTRAINT `FK_980c82a02e52408cb722faffb9b` FOREIGN KEY (`media_id`) REFERENCES `media` (`id`),
  CONSTRAINT `FK_2bbc8de09d45441790865452e96` FOREIGN KEY (`productor_id`) REFERENCES `person` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `media_productor`
--

LOCK TABLES `media_productor` WRITE;
/*!40000 ALTER TABLE `media_productor` DISABLE KEYS */;
/*!40000 ALTER TABLE `media_productor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `music_artist`
--

DROP TABLE IF EXISTS `music_artist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `music_artist` (
  `music_id` bigint(20) NOT NULL,
  `artist_id` bigint(20) NOT NULL,
  KEY `FK_f2f0d36526e24e49afdc8a15570` (`artist_id`),
  KEY `FK_ecf4edca54154347bfe7c8fb9a2` (`music_id`),
  CONSTRAINT `FK_ecf4edca54154347bfe7c8fb9a2` FOREIGN KEY (`music_id`) REFERENCES `media` (`id`),
  CONSTRAINT `FK_f2f0d36526e24e49afdc8a15570` FOREIGN KEY (`artist_id`) REFERENCES `person` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `music_artist`
--

LOCK TABLES `music_artist` WRITE;
/*!40000 ALTER TABLE `music_artist` DISABLE KEYS */;
/*!40000 ALTER TABLE `music_artist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `partener`
--

DROP TABLE IF EXISTS `partener`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `partener` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `web_site` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `partener`
--

LOCK TABLES `partener` WRITE;
/*!40000 ALTER TABLE `partener` DISABLE KEYS */;
/*!40000 ALTER TABLE `partener` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `person`
--

DROP TABLE IF EXISTS `person`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `person` (
  `classe` varchar(31) NOT NULL,
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `website` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `person`
--

LOCK TABLES `person` WRITE;
/*!40000 ALTER TABLE `person` DISABLE KEYS */;
/*!40000 ALTER TABLE `person` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `player`
--

DROP TABLE IF EXISTS `player`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `player` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `account_expired` tinyint(1) NOT NULL,
  `account_locked` tinyint(1) NOT NULL,
  `address` varchar(150) DEFAULT NULL,
  `city` varchar(50) DEFAULT NULL,
  `comp_address` varchar(150) DEFAULT NULL,
  `postal_code` varchar(15) DEFAULT NULL,
  `province` varchar(100) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `credentials_expired` tinyint(1) NOT NULL,
  `email` varchar(255) NOT NULL,
  `account_enabled` tinyint(1) DEFAULT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `newsletter` tinyint(1) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `password_hint` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `sex` varchar(255) DEFAULT NULL,
  `username` varchar(50) NOT NULL,
  `version` int(11) DEFAULT NULL,
  `website` varchar(255) DEFAULT NULL,
  `country_id` bigint(20) DEFAULT NULL,
  `token_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_3b8fbc5097484df88ee151b8379` (`email`),
  UNIQUE KEY `UK_1bac9131063845c6aa720ef77ef` (`username`),
  UNIQUE KEY `UK_9a592e7b17af4fc4b1fcf740c61` (`email`),
  UNIQUE KEY `UK_48d61f37e2ad4c97b993f0bf664` (`username`),
  UNIQUE KEY `UK_46dafd50e764445a832c68271e7` (`email`),
  UNIQUE KEY `UK_dd81108fdb7e4505aa095b6e94c` (`username`),
  UNIQUE KEY `UK_77772e34121a47d284c2a2ce901` (`email`),
  UNIQUE KEY `UK_8457673d255f4224b464c40de5c` (`username`),
  UNIQUE KEY `UK_3d8631c24f074836903dba2b4c6` (`email`),
  UNIQUE KEY `UK_1c9abc51031d4b3b95bca09135d` (`username`),
  UNIQUE KEY `UK_363ce0e2aaea4ff7aaf209e2ca3` (`email`),
  UNIQUE KEY `UK_fd15cfdd0d5b4dde96f137e3e83` (`username`),
  UNIQUE KEY `UK_30a2db43487b43b0b2e7969f41b` (`email`),
  UNIQUE KEY `UK_7a80f3e838504588a4c61e4448d` (`username`),
  UNIQUE KEY `UK_7c0e1384fa9d416a98e67527bb5` (`email`),
  UNIQUE KEY `UK_cb9a9bd8c18247438cabbd8808a` (`username`),
  UNIQUE KEY `UK_260b926630fd466ab2c050fbaf5` (`email`),
  UNIQUE KEY `UK_290761a6fcb8492a86cc5dfaf55` (`username`),
  UNIQUE KEY `UK_34b4f6f4f2da45918ab5f89c5d7` (`email`),
  UNIQUE KEY `UK_23348565218649b0ae0a78e6b64` (`username`),
  UNIQUE KEY `UK_36c5ae367a4d4d7693e818ec8ef` (`email`),
  UNIQUE KEY `UK_02eb3591a1794481b946e62954a` (`username`),
  UNIQUE KEY `UK_4275fdde79dd4132919f56bc9b7` (`email`),
  UNIQUE KEY `UK_cc2b6e735d92422caca084d1f3d` (`username`),
  KEY `FK_0f6453e3baec4402bda115034da` (`country_id`),
  KEY `FK_4892d2861b37458b8bcb77ed6a6` (`token_id`),
  KEY `FK_13a18bc22bf740a9897e3902c64` (`country_id`),
  KEY `FK_92058523787e415db867222b583` (`token_id`),
  KEY `FK_83a5d1f556474dc2b7fcf44e81c` (`country_id`),
  KEY `FK_37fb737f0c7646a392647463d69` (`token_id`),
  KEY `FK_aa767fe663bd408680018c5cb94` (`country_id`),
  KEY `FK_b1d1770899dc45eb83c328b1c31` (`token_id`),
  KEY `FK_e53b59abd1ca4a7283fe4e50add` (`country_id`),
  KEY `FK_658ec46011564600b47d660d77a` (`token_id`),
  KEY `FK_856205677fbb4705b595c659e6b` (`country_id`),
  KEY `FK_fc02c27207064973b1031a2e1c8` (`token_id`),
  KEY `FK_71c9c1e6b919481abd09421313c` (`country_id`),
  KEY `FK_601e189072fd42e2aec42e382ae` (`token_id`),
  KEY `FK_c7928c8aec56414b8138efd3372` (`country_id`),
  KEY `FK_d2ded844d8d84d95a1e06593305` (`token_id`),
  KEY `FK_c1fa7f6475804bc68e24a1934ba` (`country_id`),
  KEY `FK_0d95985527c04d97a4abdaa7298` (`token_id`),
  KEY `FK_bcece70607dc4178aede157493b` (`country_id`),
  KEY `FK_a26fe446f69e4047a37bf529528` (`token_id`),
  KEY `FK_095f382b366544bc8766700b043` (`country_id`),
  KEY `FK_0d845a1bba71416a8fc903d9224` (`token_id`),
  KEY `FK_bbb481a0cec94012aadfdb222f2` (`country_id`),
  KEY `FK_864c1d5563334257b9f9c422cfd` (`token_id`),
  CONSTRAINT `FK_0d95985527c04d97a4abdaa7298` FOREIGN KEY (`token_id`) REFERENCES `goose_token` (`id`),
  CONSTRAINT `FK_0f6453e3baec4402bda115034da` FOREIGN KEY (`country_id`) REFERENCES `country` (`id`),
  CONSTRAINT `FK_13a18bc22bf740a9897e3902c64` FOREIGN KEY (`country_id`) REFERENCES `country` (`id`),
  CONSTRAINT `FK_37fb737f0c7646a392647463d69` FOREIGN KEY (`token_id`) REFERENCES `goose_token` (`id`),
  CONSTRAINT `FK_4892d2861b37458b8bcb77ed6a6` FOREIGN KEY (`token_id`) REFERENCES `goose_token` (`id`),
  CONSTRAINT `FK_601e189072fd42e2aec42e382ae` FOREIGN KEY (`token_id`) REFERENCES `goose_token` (`id`),
  CONSTRAINT `FK_658ec46011564600b47d660d77a` FOREIGN KEY (`token_id`) REFERENCES `goose_token` (`id`),
  CONSTRAINT `FK_71c9c1e6b919481abd09421313c` FOREIGN KEY (`country_id`) REFERENCES `country` (`id`),
  CONSTRAINT `FK_83a5d1f556474dc2b7fcf44e81c` FOREIGN KEY (`country_id`) REFERENCES `country` (`id`),
  CONSTRAINT `FK_856205677fbb4705b595c659e6b` FOREIGN KEY (`country_id`) REFERENCES `country` (`id`),
  CONSTRAINT `FK_864c1d5563334257b9f9c422cfd` FOREIGN KEY (`token_id`) REFERENCES `goose_token` (`id`),
  CONSTRAINT `FK_92058523787e415db867222b583` FOREIGN KEY (`token_id`) REFERENCES `goose_token` (`id`),
  CONSTRAINT `FK_a26fe446f69e4047a37bf529528` FOREIGN KEY (`token_id`) REFERENCES `goose_token` (`id`),
  CONSTRAINT `FK_aa767fe663bd408680018c5cb94` FOREIGN KEY (`country_id`) REFERENCES `country` (`id`),
  CONSTRAINT `FK_b1d1770899dc45eb83c328b1c31` FOREIGN KEY (`token_id`) REFERENCES `goose_token` (`id`),
  CONSTRAINT `FK_bbb481a0cec94012aadfdb222f2` FOREIGN KEY (`country_id`) REFERENCES `country` (`id`),
  CONSTRAINT `FK_bcece70607dc4178aede157493b` FOREIGN KEY (`country_id`) REFERENCES `country` (`id`),
  CONSTRAINT `FK_c1fa7f6475804bc68e24a1934ba` FOREIGN KEY (`country_id`) REFERENCES `country` (`id`),
  CONSTRAINT `FK_c7928c8aec56414b8138efd3372` FOREIGN KEY (`country_id`) REFERENCES `country` (`id`),
  CONSTRAINT `FK_d2ded844d8d84d95a1e06593305` FOREIGN KEY (`token_id`) REFERENCES `goose_token` (`id`),
  CONSTRAINT `FK_e53b59abd1ca4a7283fe4e50add` FOREIGN KEY (`country_id`) REFERENCES `country` (`id`),
  CONSTRAINT `FK_fc02c27207064973b1031a2e1c8` FOREIGN KEY (`token_id`) REFERENCES `goose_token` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `player`
--

LOCK TABLES `player` WRITE;
/*!40000 ALTER TABLE `player` DISABLE KEYS */;
INSERT INTO `player` VALUES (1,0,0,'','','','','','1977-05-01',0,'dimitri.kahn@adbeback.fr',1,'Dimitri','KAHN',0,'7c4a8d09ca3762af61e59520943dc26494f8941b',NULL,NULL,'Mr','adbeback',3,NULL,1,1),(2,0,0,NULL,NULL,NULL,NULL,NULL,'2014-07-11',0,'qsdsqd@qsdsqd.Fr',1,NULL,NULL,NULL,'9ed316e3870997867d9ed76c3169119edfcb32d7',NULL,NULL,'Mme','qsdsq',0,NULL,NULL,NULL),(3,0,0,NULL,NULL,NULL,NULL,NULL,'2014-07-11',0,'dktest_1@mtzik.fr',1,NULL,NULL,NULL,'01caee3b952e84a894029923bddad19fe6900a4d',NULL,NULL,'Mme','xcvxc',0,NULL,NULL,NULL);
/*!40000 ALTER TABLE `player` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `possibility`
--

DROP TABLE IF EXISTS `possibility`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `possibility` (
  `classe` varchar(31) NOT NULL,
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `price` double DEFAULT NULL,
  `answer` varchar(255) DEFAULT NULL,
  `ad_id` bigint(20) DEFAULT NULL,
  `brand_id` bigint(20) DEFAULT NULL,
  `product_id` bigint(20) DEFAULT NULL,
  `possibility_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_91d92c2ce5c14e419e1357095cf` (`ad_id`),
  KEY `FK_2c42063b6f7740da85e0cee399d` (`brand_id`),
  KEY `FK_5da0cb97c2d6404eb5b412496fa` (`product_id`),
  KEY `FK_e6296c34a3e84beab78fb5697ca` (`possibility_id`),
  KEY `FK_e499f2f1e38240ee98bf5d203f5` (`ad_id`),
  KEY `FK_b555dbfb90984f169e4a7a49799` (`brand_id`),
  KEY `FK_5cc87072bac840c9bdfdde3dc44` (`product_id`),
  KEY `FK_3500ecc8c9644336a334bef1f7f` (`possibility_id`),
  KEY `FK_681ea4ba15b84e14a26fca28929` (`ad_id`),
  KEY `FK_15ec8bfcf7b74923bd42dcd9ca3` (`brand_id`),
  KEY `FK_24120065ba964b9abded9a1ff33` (`product_id`),
  KEY `FK_11794c1ab89c4e37bfcc54cdcb2` (`possibility_id`),
  KEY `FK_652a1d9c79f040beb2699b75fd1` (`ad_id`),
  KEY `FK_6afb5e6badeb4386ab84a95b5cb` (`brand_id`),
  KEY `FK_c618fdb4694e42c081527a2c823` (`product_id`),
  KEY `FK_ff899ac263094388a893f02dc7c` (`possibility_id`),
  KEY `FK_95849a2e619143188dd852b38a5` (`ad_id`),
  KEY `FK_af7ba7b028dd42829d58bef69b2` (`brand_id`),
  KEY `FK_ec05b759615341c8bd9bf20247a` (`product_id`),
  KEY `FK_d84682ac85024b7b96da4f1c16b` (`possibility_id`),
  KEY `FK_78dad78e6a4a43ab86c9566dc0b` (`ad_id`),
  KEY `FK_9f8d6446856c45aca61cf5c9064` (`brand_id`),
  KEY `FK_0c0d04d5f3ec4ee7a56b58f46df` (`product_id`),
  KEY `FK_d2132c3e28e84e569ede2074acd` (`possibility_id`),
  KEY `FK_6873605d9397425ebf9b38b1a8d` (`ad_id`),
  KEY `FK_f33e5e67fb744b2b865345c9573` (`brand_id`),
  KEY `FK_09b5a98fe8a64ce8a281393ef9d` (`product_id`),
  KEY `FK_659052c66dac43bfa55654c68ed` (`possibility_id`),
  KEY `FK_cbcb2a583b0e413ca9422b5d5c2` (`ad_id`),
  KEY `FK_f58548dd84624d328ae770daa23` (`brand_id`),
  KEY `FK_ebe3b9d6ff4d4d509a982c46d42` (`product_id`),
  KEY `FK_c52c5b97ba3c4552bae14517f71` (`possibility_id`),
  KEY `FK_0417e9f2145040a29fc22c03c63` (`ad_id`),
  KEY `FK_63990cec6d6546d984edc969b2b` (`brand_id`),
  KEY `FK_0e44bb496ff6428199247dd00a6` (`product_id`),
  KEY `FK_93e298292a3b4cc3abaa61f3705` (`possibility_id`),
  KEY `FK_e5d271c33d8240fab3b5762419a` (`ad_id`),
  KEY `FK_38eeb0b68c90466c818794382b1` (`brand_id`),
  KEY `FK_141d8fbc0d194d8e9f998eba1e6` (`product_id`),
  KEY `FK_434b671388204f1f8a442111a97` (`possibility_id`),
  KEY `FK_a9317fd5c5f34feb963e7d8ed57` (`ad_id`),
  KEY `FK_c60192d4e9b048b48845a128576` (`brand_id`),
  KEY `FK_a4415f0217cc4a89b8e442714cd` (`product_id`),
  KEY `FK_42215e63976d46f089ae2fbc979` (`possibility_id`),
  KEY `FK_d6ed2ba3c9da402fb65afbc12be` (`ad_id`),
  KEY `FK_64f2a5a2becd4bda87ff9e32bd3` (`brand_id`),
  KEY `FK_ec8cfb7f9ef641a1aa615b7a750` (`product_id`),
  KEY `FK_e3aa5b96bf614d4bbc15d80965a` (`possibility_id`),
  CONSTRAINT `FK_0417e9f2145040a29fc22c03c63` FOREIGN KEY (`ad_id`) REFERENCES `ad` (`id`),
  CONSTRAINT `FK_09b5a98fe8a64ce8a281393ef9d` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `FK_0c0d04d5f3ec4ee7a56b58f46df` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `FK_0e44bb496ff6428199247dd00a6` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `FK_11794c1ab89c4e37bfcc54cdcb2` FOREIGN KEY (`possibility_id`) REFERENCES `adchoise` (`id`),
  CONSTRAINT `FK_141d8fbc0d194d8e9f998eba1e6` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `FK_15ec8bfcf7b74923bd42dcd9ca3` FOREIGN KEY (`brand_id`) REFERENCES `brand` (`id`),
  CONSTRAINT `FK_24120065ba964b9abded9a1ff33` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `FK_2c42063b6f7740da85e0cee399d` FOREIGN KEY (`brand_id`) REFERENCES `brand` (`id`),
  CONSTRAINT `FK_3500ecc8c9644336a334bef1f7f` FOREIGN KEY (`possibility_id`) REFERENCES `adchoise` (`id`),
  CONSTRAINT `FK_38eeb0b68c90466c818794382b1` FOREIGN KEY (`brand_id`) REFERENCES `brand` (`id`),
  CONSTRAINT `FK_434b671388204f1f8a442111a97` FOREIGN KEY (`possibility_id`) REFERENCES `adchoise` (`id`),
  CONSTRAINT `FK_5cc87072bac840c9bdfdde3dc44` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `FK_5da0cb97c2d6404eb5b412496fa` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `FK_63990cec6d6546d984edc969b2b` FOREIGN KEY (`brand_id`) REFERENCES `brand` (`id`),
  CONSTRAINT `FK_64f2a5a2becd4bda87ff9e32bd3` FOREIGN KEY (`brand_id`) REFERENCES `brand` (`id`),
  CONSTRAINT `FK_652a1d9c79f040beb2699b75fd1` FOREIGN KEY (`ad_id`) REFERENCES `ad` (`id`),
  CONSTRAINT `FK_659052c66dac43bfa55654c68ed` FOREIGN KEY (`possibility_id`) REFERENCES `adchoise` (`id`),
  CONSTRAINT `FK_681ea4ba15b84e14a26fca28929` FOREIGN KEY (`ad_id`) REFERENCES `ad` (`id`),
  CONSTRAINT `FK_6873605d9397425ebf9b38b1a8d` FOREIGN KEY (`ad_id`) REFERENCES `ad` (`id`),
  CONSTRAINT `FK_6afb5e6badeb4386ab84a95b5cb` FOREIGN KEY (`brand_id`) REFERENCES `brand` (`id`),
  CONSTRAINT `FK_78dad78e6a4a43ab86c9566dc0b` FOREIGN KEY (`ad_id`) REFERENCES `ad` (`id`),
  CONSTRAINT `FK_91d92c2ce5c14e419e1357095cf` FOREIGN KEY (`ad_id`) REFERENCES `ad` (`id`),
  CONSTRAINT `FK_93e298292a3b4cc3abaa61f3705` FOREIGN KEY (`possibility_id`) REFERENCES `adchoise` (`id`),
  CONSTRAINT `FK_95849a2e619143188dd852b38a5` FOREIGN KEY (`ad_id`) REFERENCES `ad` (`id`),
  CONSTRAINT `FK_9f8d6446856c45aca61cf5c9064` FOREIGN KEY (`brand_id`) REFERENCES `brand` (`id`),
  CONSTRAINT `FK_af7ba7b028dd42829d58bef69b2` FOREIGN KEY (`brand_id`) REFERENCES `brand` (`id`),
  CONSTRAINT `FK_b555dbfb90984f169e4a7a49799` FOREIGN KEY (`brand_id`) REFERENCES `brand` (`id`),
  CONSTRAINT `FK_c52c5b97ba3c4552bae14517f71` FOREIGN KEY (`possibility_id`) REFERENCES `adchoise` (`id`),
  CONSTRAINT `FK_c618fdb4694e42c081527a2c823` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `FK_cbcb2a583b0e413ca9422b5d5c2` FOREIGN KEY (`ad_id`) REFERENCES `ad` (`id`),
  CONSTRAINT `FK_d2132c3e28e84e569ede2074acd` FOREIGN KEY (`possibility_id`) REFERENCES `adchoise` (`id`),
  CONSTRAINT `FK_d6ed2ba3c9da402fb65afbc12be` FOREIGN KEY (`ad_id`) REFERENCES `ad` (`id`),
  CONSTRAINT `FK_d84682ac85024b7b96da4f1c16b` FOREIGN KEY (`possibility_id`) REFERENCES `adchoise` (`id`),
  CONSTRAINT `FK_e3aa5b96bf614d4bbc15d80965a` FOREIGN KEY (`possibility_id`) REFERENCES `adchoise` (`id`),
  CONSTRAINT `FK_e499f2f1e38240ee98bf5d203f5` FOREIGN KEY (`ad_id`) REFERENCES `ad` (`id`),
  CONSTRAINT `FK_e5d271c33d8240fab3b5762419a` FOREIGN KEY (`ad_id`) REFERENCES `ad` (`id`),
  CONSTRAINT `FK_e6296c34a3e84beab78fb5697ca` FOREIGN KEY (`possibility_id`) REFERENCES `adchoise` (`id`),
  CONSTRAINT `FK_ebe3b9d6ff4d4d509a982c46d42` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `FK_ec05b759615341c8bd9bf20247a` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `FK_ec8cfb7f9ef641a1aa615b7a750` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `FK_f33e5e67fb744b2b865345c9573` FOREIGN KEY (`brand_id`) REFERENCES `brand` (`id`),
  CONSTRAINT `FK_f58548dd84624d328ae770daa23` FOREIGN KEY (`brand_id`) REFERENCES `brand` (`id`),
  CONSTRAINT `FK_ff899ac263094388a893f02dc7c` FOREIGN KEY (`possibility_id`) REFERENCES `adchoise` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1693 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `possibility`
--

LOCK TABLES `possibility` WRITE;
/*!40000 ALTER TABLE `possibility` DISABLE KEYS */;
INSERT INTO `possibility` VALUES ('Product',1,NULL,NULL,8,NULL,8,1),('Product',2,NULL,NULL,8,NULL,7,1),('Product',3,NULL,NULL,8,NULL,9,1),('Brand',4,NULL,NULL,19,26,NULL,2),('Brand',5,NULL,NULL,19,30,NULL,2),('Brand',6,NULL,NULL,19,18,NULL,2),('Brand',7,NULL,NULL,12,20,NULL,3),('Brand',8,NULL,NULL,12,1,NULL,3),('Brand',9,NULL,NULL,12,25,NULL,3),('Product',10,NULL,NULL,7,NULL,4,4),('Product',11,NULL,NULL,7,NULL,6,4),('Product',12,NULL,NULL,7,NULL,5,4),('Brand',13,NULL,NULL,10,5,NULL,5),('Brand',14,NULL,NULL,10,9,NULL,5),('Brand',15,NULL,NULL,10,24,NULL,5),('Brand',16,NULL,NULL,16,23,NULL,6),('Brand',17,NULL,NULL,16,14,NULL,6),('Brand',18,NULL,NULL,16,18,NULL,6),('Product',19,NULL,NULL,20,NULL,1,7),('Product',20,NULL,NULL,20,NULL,3,7),('Product',21,NULL,NULL,20,NULL,2,7),('Brand',22,NULL,NULL,19,26,NULL,8),('Brand',23,NULL,NULL,19,18,NULL,8),('Brand',24,NULL,NULL,19,27,NULL,8),('Brand',25,NULL,NULL,2,28,NULL,9),('Brand',26,NULL,NULL,2,10,NULL,9),('Brand',27,NULL,NULL,2,19,NULL,9),('Brand',28,NULL,NULL,10,5,NULL,10),('Brand',29,NULL,NULL,10,16,NULL,10),('Brand',30,NULL,NULL,10,28,NULL,10),('OpenPossibility',31,NULL,'94 %',22,NULL,NULL,11),('OpenPossibility',32,NULL,'92 %',22,NULL,NULL,11),('OpenPossibility',33,NULL,'84 %',22,NULL,NULL,11),('Brand',34,NULL,NULL,21,29,NULL,12),('Brand',35,NULL,NULL,21,3,NULL,12),('Brand',36,NULL,NULL,21,2,NULL,12),('Brand',37,NULL,NULL,9,16,NULL,13),('Brand',38,NULL,NULL,9,14,NULL,13),('Brand',39,NULL,NULL,9,22,NULL,13),('Brand',40,NULL,NULL,18,25,NULL,14),('Brand',41,NULL,NULL,18,4,NULL,14),('Brand',42,NULL,NULL,18,8,NULL,14),('Brand',43,NULL,NULL,15,18,NULL,15),('Brand',44,NULL,NULL,15,15,NULL,15),('Brand',45,NULL,NULL,15,10,NULL,15),('Product',46,NULL,NULL,8,NULL,8,16),('Product',47,NULL,NULL,8,NULL,9,16),('Product',48,NULL,NULL,8,NULL,7,16),('Brand',49,NULL,NULL,10,5,NULL,17),('Brand',50,NULL,NULL,10,9,NULL,17),('Brand',51,NULL,NULL,10,12,NULL,17),('OpenPossibility',52,NULL,'9,95 €',4,NULL,NULL,18),('OpenPossibility',53,NULL,'8,95 €',4,NULL,NULL,18),('OpenPossibility',54,NULL,'9,99 €',4,NULL,NULL,18),('Brand',55,NULL,NULL,9,16,NULL,19),('Brand',56,NULL,NULL,9,31,NULL,19),('Brand',57,NULL,NULL,9,15,NULL,19),('OpenPossibility',58,NULL,'94 %',22,NULL,NULL,20),('OpenPossibility',59,NULL,'92 %',22,NULL,NULL,20),('OpenPossibility',60,NULL,'84 %',22,NULL,NULL,20),('Product',61,NULL,NULL,20,NULL,1,21),('Product',62,NULL,NULL,20,NULL,2,21),('Product',63,NULL,NULL,20,NULL,3,21),('Product',64,NULL,NULL,8,NULL,8,22),('Product',65,NULL,NULL,8,NULL,9,22),('Product',66,NULL,NULL,8,NULL,7,22),('Brand',67,NULL,NULL,10,5,NULL,23),('Brand',68,NULL,NULL,10,22,NULL,23),('Brand',69,NULL,NULL,10,18,NULL,23),('Brand',70,NULL,NULL,21,29,NULL,24),('Brand',71,NULL,NULL,21,16,NULL,24),('Brand',72,NULL,NULL,21,20,NULL,24),('OpenPossibility',73,NULL,'10000 kilomètres',5,NULL,NULL,25),('OpenPossibility',74,NULL,'8000 kilomètres',5,NULL,NULL,25),('OpenPossibility',75,NULL,'12000 kilomètres',5,NULL,NULL,25),('OpenPossibility',76,NULL,'94 %',22,NULL,NULL,26),('OpenPossibility',77,NULL,'84 %',22,NULL,NULL,26),('OpenPossibility',78,NULL,'92 %',22,NULL,NULL,26),('Brand',79,NULL,NULL,17,24,NULL,27),('Brand',80,NULL,NULL,17,19,NULL,27),('Brand',81,NULL,NULL,17,22,NULL,27),('Product',82,NULL,NULL,8,NULL,8,28),('Product',83,NULL,NULL,8,NULL,9,28),('Product',84,NULL,NULL,8,NULL,7,28),('Brand',85,NULL,NULL,3,15,NULL,29),('Brand',86,NULL,NULL,3,19,NULL,29),('Brand',87,NULL,NULL,3,13,NULL,29),('Product',88,NULL,NULL,7,NULL,4,30),('Product',89,NULL,NULL,7,NULL,5,30),('Product',90,NULL,NULL,7,NULL,6,30),('Brand',91,NULL,NULL,6,21,NULL,31),('Brand',92,NULL,NULL,6,31,NULL,31),('Brand',93,NULL,NULL,6,9,NULL,31),('Brand',94,NULL,NULL,19,26,NULL,32),('Brand',95,NULL,NULL,19,7,NULL,32),('Brand',96,NULL,NULL,19,12,NULL,32),('Brand',97,NULL,NULL,17,24,NULL,33),('Brand',98,NULL,NULL,17,10,NULL,33),('Brand',99,NULL,NULL,17,5,NULL,33),('Brand',100,NULL,NULL,2,28,NULL,34),('Brand',101,NULL,NULL,2,17,NULL,34),('Brand',102,NULL,NULL,2,13,NULL,34),('Brand',103,NULL,NULL,21,29,NULL,35),('Brand',104,NULL,NULL,21,26,NULL,35),('Brand',105,NULL,NULL,21,14,NULL,35),('Product',106,NULL,NULL,7,NULL,4,36),('Product',107,NULL,NULL,7,NULL,6,36),('Product',108,NULL,NULL,7,NULL,5,36),('Brand',109,NULL,NULL,1,14,NULL,37),('Brand',110,NULL,NULL,1,29,NULL,37),('Brand',111,NULL,NULL,1,22,NULL,37),('Brand',112,NULL,NULL,13,20,NULL,38),('Brand',113,NULL,NULL,13,17,NULL,38),('Brand',114,NULL,NULL,13,6,NULL,38),('Brand',115,NULL,NULL,14,20,NULL,39),('Brand',116,NULL,NULL,14,9,NULL,39),('Brand',117,NULL,NULL,14,27,NULL,39),('Brand',118,NULL,NULL,6,21,NULL,40),('Brand',119,NULL,NULL,6,20,NULL,40),('Brand',120,NULL,NULL,6,4,NULL,40),('Brand',121,NULL,NULL,21,29,NULL,41),('Brand',122,NULL,NULL,21,14,NULL,41),('Brand',123,NULL,NULL,21,15,NULL,41),('Brand',124,NULL,NULL,10,5,NULL,42),('Brand',125,NULL,NULL,10,28,NULL,42),('Brand',126,NULL,NULL,10,4,NULL,42),('Brand',127,NULL,NULL,18,25,NULL,43),('Brand',128,NULL,NULL,18,24,NULL,43),('Brand',129,NULL,NULL,18,4,NULL,43),('Brand',130,NULL,NULL,9,16,NULL,44),('Brand',131,NULL,NULL,9,24,NULL,44),('Brand',132,NULL,NULL,9,14,NULL,44),('Brand',133,NULL,NULL,13,20,NULL,45),('Brand',134,NULL,NULL,13,14,NULL,45),('Brand',135,NULL,NULL,13,17,NULL,45),('Brand',136,NULL,NULL,2,28,NULL,46),('Brand',137,NULL,NULL,2,11,NULL,46),('Brand',138,NULL,NULL,2,7,NULL,46),('Brand',139,NULL,NULL,1,14,NULL,47),('Brand',140,NULL,NULL,1,2,NULL,47),('Brand',141,NULL,NULL,1,25,NULL,47),('Brand',142,NULL,NULL,21,29,NULL,48),('Brand',143,NULL,NULL,21,15,NULL,48),('Brand',144,NULL,NULL,21,19,NULL,48),('Brand',145,NULL,NULL,1,14,NULL,49),('Brand',146,NULL,NULL,1,10,NULL,49),('Brand',147,NULL,NULL,1,20,NULL,49),('Brand',148,NULL,NULL,17,24,NULL,50),('Brand',149,NULL,NULL,17,5,NULL,50),('Brand',150,NULL,NULL,17,23,NULL,50),('OpenPossibility',151,NULL,'9,95 €',4,NULL,NULL,51),('OpenPossibility',152,NULL,'9,99 €',4,NULL,NULL,51),('OpenPossibility',153,NULL,'8,95 €',4,NULL,NULL,51),('OpenPossibility',154,NULL,'10000 kilomètres',5,NULL,NULL,52),('OpenPossibility',155,NULL,'8000 kilomètres',5,NULL,NULL,52),('OpenPossibility',156,NULL,'12000 kilomètres',5,NULL,NULL,52),('Brand',157,NULL,NULL,19,26,NULL,53),('Brand',158,NULL,NULL,19,19,NULL,53),('Brand',159,NULL,NULL,19,13,NULL,53),('Brand',160,NULL,NULL,3,15,NULL,54),('Brand',161,NULL,NULL,3,27,NULL,54),('Brand',162,NULL,NULL,3,23,NULL,54),('Brand',163,NULL,NULL,16,23,NULL,55),('Brand',164,NULL,NULL,16,4,NULL,55),('Brand',165,NULL,NULL,16,11,NULL,55),('Brand',166,NULL,NULL,6,21,NULL,56),('Brand',167,NULL,NULL,6,29,NULL,56),('Brand',168,NULL,NULL,6,13,NULL,56),('Brand',169,NULL,NULL,12,20,NULL,57),('Brand',170,NULL,NULL,12,23,NULL,57),('Brand',171,NULL,NULL,12,7,NULL,57),('Brand',172,NULL,NULL,18,25,NULL,58),('Brand',173,NULL,NULL,18,5,NULL,58),('Brand',174,NULL,NULL,18,12,NULL,58),('OpenPossibility',175,NULL,'94 %',22,NULL,NULL,59),('OpenPossibility',176,NULL,'84 %',22,NULL,NULL,59),('OpenPossibility',177,NULL,'92 %',22,NULL,NULL,59),('Brand',178,NULL,NULL,3,15,NULL,60),('Brand',179,NULL,NULL,3,7,NULL,60),('Brand',180,NULL,NULL,3,4,NULL,60),('Brand',181,NULL,NULL,11,5,NULL,61),('Brand',182,NULL,NULL,11,11,NULL,61),('Brand',183,NULL,NULL,11,21,NULL,61),('Brand',184,NULL,NULL,14,20,NULL,62),('Brand',185,NULL,NULL,14,16,NULL,62),('Brand',186,NULL,NULL,14,9,NULL,62),('Brand',187,NULL,NULL,17,24,NULL,63),('Brand',188,NULL,NULL,17,27,NULL,63),('Brand',189,NULL,NULL,17,12,NULL,63),('Brand',190,NULL,NULL,19,26,NULL,64),('Brand',191,NULL,NULL,19,23,NULL,64),('Brand',192,NULL,NULL,19,1,NULL,64),('OpenPossibility',193,NULL,'10000 kilomètres',5,NULL,NULL,65),('OpenPossibility',194,NULL,'12000 kilomètres',5,NULL,NULL,65),('OpenPossibility',195,NULL,'8000 kilomètres',5,NULL,NULL,65),('Brand',196,NULL,NULL,2,28,NULL,66),('Brand',197,NULL,NULL,2,11,NULL,66),('Brand',198,NULL,NULL,2,31,NULL,66),('Brand',199,NULL,NULL,9,16,NULL,67),('Brand',200,NULL,NULL,9,7,NULL,67),('Brand',201,NULL,NULL,9,28,NULL,67),('Product',202,NULL,NULL,8,NULL,8,68),('Product',203,NULL,NULL,8,NULL,9,68),('Product',204,NULL,NULL,8,NULL,7,68),('Brand',205,NULL,NULL,19,26,NULL,69),('Brand',206,NULL,NULL,19,9,NULL,69),('Brand',207,NULL,NULL,19,29,NULL,69),('Brand',208,NULL,NULL,16,23,NULL,70),('Brand',209,NULL,NULL,16,29,NULL,70),('Brand',210,NULL,NULL,16,16,NULL,70),('Brand',211,NULL,NULL,6,21,NULL,71),('Brand',212,NULL,NULL,6,30,NULL,71),('Brand',213,NULL,NULL,6,11,NULL,71),('Product',214,NULL,NULL,7,NULL,4,72),('Product',215,NULL,NULL,7,NULL,6,72),('Product',216,NULL,NULL,7,NULL,5,72),('Brand',217,NULL,NULL,2,28,NULL,73),('Brand',218,NULL,NULL,2,31,NULL,73),('Brand',219,NULL,NULL,2,18,NULL,73),('Brand',220,NULL,NULL,12,20,NULL,74),('Brand',221,NULL,NULL,12,25,NULL,74),('Brand',222,NULL,NULL,12,26,NULL,74),('Brand',223,NULL,NULL,19,26,NULL,75),('Brand',224,NULL,NULL,19,11,NULL,75),('Brand',225,NULL,NULL,19,17,NULL,75),('Brand',226,NULL,NULL,13,20,NULL,76),('Brand',227,NULL,NULL,13,29,NULL,76),('Brand',228,NULL,NULL,13,13,NULL,76),('Brand',229,NULL,NULL,18,25,NULL,77),('Brand',230,NULL,NULL,18,26,NULL,77),('Brand',231,NULL,NULL,18,8,NULL,77),('OpenPossibility',232,NULL,'10000 kilomètres',5,NULL,NULL,78),('OpenPossibility',233,NULL,'12000 kilomètres',5,NULL,NULL,78),('OpenPossibility',234,NULL,'8000 kilomètres',5,NULL,NULL,78),('Brand',235,NULL,NULL,10,5,NULL,79),('Brand',236,NULL,NULL,10,14,NULL,79),('Brand',237,NULL,NULL,10,18,NULL,79),('OpenPossibility',238,NULL,'9,95 €',4,NULL,NULL,80),('OpenPossibility',239,NULL,'9,99 €',4,NULL,NULL,80),('OpenPossibility',240,NULL,'8,95 €',4,NULL,NULL,80),('OpenPossibility',241,NULL,'94 %',22,NULL,NULL,81),('OpenPossibility',242,NULL,'92 %',22,NULL,NULL,81),('OpenPossibility',243,NULL,'84 %',22,NULL,NULL,81),('Brand',244,NULL,NULL,19,26,NULL,82),('Brand',245,NULL,NULL,19,3,NULL,82),('Brand',246,NULL,NULL,19,16,NULL,82),('Product',247,NULL,NULL,20,NULL,1,83),('Product',248,NULL,NULL,20,NULL,2,83),('Product',249,NULL,NULL,20,NULL,3,83),('Brand',250,NULL,NULL,15,18,NULL,84),('Brand',251,NULL,NULL,15,15,NULL,84),('Brand',252,NULL,NULL,15,19,NULL,84),('OpenPossibility',253,NULL,'9,95 €',4,NULL,NULL,85),('OpenPossibility',254,NULL,'8,95 €',4,NULL,NULL,85),('OpenPossibility',255,NULL,'9,99 €',4,NULL,NULL,85),('Brand',256,NULL,NULL,14,20,NULL,86),('Brand',257,NULL,NULL,14,22,NULL,86),('Brand',258,NULL,NULL,14,13,NULL,86),('Brand',259,NULL,NULL,17,24,NULL,87),('Brand',260,NULL,NULL,17,2,NULL,87),('Brand',261,NULL,NULL,17,18,NULL,87),('Brand',262,NULL,NULL,11,5,NULL,88),('Brand',263,NULL,NULL,11,28,NULL,88),('Brand',264,NULL,NULL,11,27,NULL,88),('Brand',265,NULL,NULL,9,16,NULL,89),('Brand',266,NULL,NULL,9,4,NULL,89),('Brand',267,NULL,NULL,9,27,NULL,89),('Brand',268,NULL,NULL,21,29,NULL,90),('Brand',269,NULL,NULL,21,12,NULL,90),('Brand',270,NULL,NULL,21,13,NULL,90),('OpenPossibility',271,NULL,'10000 kilomètres',5,NULL,NULL,91),('OpenPossibility',272,NULL,'8000 kilomètres',5,NULL,NULL,91),('OpenPossibility',273,NULL,'12000 kilomètres',5,NULL,NULL,91),('Brand',274,NULL,NULL,9,16,NULL,92),('Brand',275,NULL,NULL,9,15,NULL,92),('Brand',276,NULL,NULL,9,3,NULL,92),('Brand',277,NULL,NULL,10,5,NULL,93),('Brand',278,NULL,NULL,10,25,NULL,93),('Brand',279,NULL,NULL,10,20,NULL,93),('OpenPossibility',280,NULL,'Vivre ici',23,NULL,NULL,94),('OpenPossibility',281,NULL,'Venir d\'ici',23,NULL,NULL,94),('OpenPossibility',282,NULL,'Aller d\'ici',23,NULL,NULL,94),('OpenPossibility',283,NULL,'9,95 €',4,NULL,NULL,95),('OpenPossibility',284,NULL,'9,99 €',4,NULL,NULL,95),('OpenPossibility',285,NULL,'8,95 €',4,NULL,NULL,95),('Brand',286,NULL,NULL,16,23,NULL,96),('Brand',287,NULL,NULL,16,6,NULL,96),('Brand',288,NULL,NULL,16,28,NULL,96),('Brand',289,NULL,NULL,6,21,NULL,97),('Brand',290,NULL,NULL,6,17,NULL,97),('Brand',291,NULL,NULL,6,30,NULL,97),('Brand',292,NULL,NULL,15,18,NULL,98),('Brand',293,NULL,NULL,15,27,NULL,98),('Brand',294,NULL,NULL,15,14,NULL,98),('OpenPossibility',295,NULL,'Vivre ici',23,NULL,NULL,99),('OpenPossibility',296,NULL,'Venir d\'ici',23,NULL,NULL,99),('OpenPossibility',297,NULL,'Aller d\'ici',23,NULL,NULL,99),('Brand',298,NULL,NULL,21,29,NULL,100),('Brand',299,NULL,NULL,21,12,NULL,100),('Brand',300,NULL,NULL,21,9,NULL,100),('OpenPossibility',301,NULL,'94 %',22,NULL,NULL,101),('OpenPossibility',302,NULL,'84 %',22,NULL,NULL,101),('OpenPossibility',303,NULL,'92 %',22,NULL,NULL,101),('Product',304,NULL,NULL,8,NULL,8,102),('Product',305,NULL,NULL,8,NULL,7,102),('Product',306,NULL,NULL,8,NULL,9,102),('Brand',307,NULL,NULL,6,21,NULL,103),('Brand',308,NULL,NULL,6,16,NULL,103),('Brand',309,NULL,NULL,6,13,NULL,103),('Brand',310,NULL,NULL,17,24,NULL,104),('Brand',311,NULL,NULL,17,21,NULL,104),('Brand',312,NULL,NULL,17,15,NULL,104),('OpenPossibility',313,NULL,'9,95 €',4,NULL,NULL,105),('OpenPossibility',314,NULL,'8,95 €',4,NULL,NULL,105),('OpenPossibility',315,NULL,'9,99 €',4,NULL,NULL,105),('Brand',316,NULL,NULL,1,14,NULL,106),('Brand',317,NULL,NULL,1,25,NULL,106),('Brand',318,NULL,NULL,1,6,NULL,106),('Product',319,NULL,NULL,8,NULL,8,107),('Product',320,NULL,NULL,8,NULL,7,107),('Product',321,NULL,NULL,8,NULL,9,107),('OpenPossibility',322,NULL,'10000 kilomètres',5,NULL,NULL,108),('OpenPossibility',323,NULL,'12000 kilomètres',5,NULL,NULL,108),('OpenPossibility',324,NULL,'8000 kilomètres',5,NULL,NULL,108),('Product',325,NULL,NULL,20,NULL,1,109),('Product',326,NULL,NULL,20,NULL,3,109),('Product',327,NULL,NULL,20,NULL,2,109),('OpenPossibility',328,NULL,'94 %',22,NULL,NULL,110),('OpenPossibility',329,NULL,'92 %',22,NULL,NULL,110),('OpenPossibility',330,NULL,'84 %',22,NULL,NULL,110),('Brand',331,NULL,NULL,11,5,NULL,111),('Brand',332,NULL,NULL,11,16,NULL,111),('Brand',333,NULL,NULL,11,21,NULL,111),('Brand',334,NULL,NULL,1,14,NULL,112),('Brand',335,NULL,NULL,1,26,NULL,112),('Brand',336,NULL,NULL,1,2,NULL,112),('Brand',337,NULL,NULL,15,18,NULL,113),('Brand',338,NULL,NULL,15,4,NULL,113),('Brand',339,NULL,NULL,15,9,NULL,113),('Brand',340,NULL,NULL,17,24,NULL,114),('Brand',341,NULL,NULL,17,31,NULL,114),('Brand',342,NULL,NULL,17,1,NULL,114),('Brand',343,NULL,NULL,2,28,NULL,115),('Brand',344,NULL,NULL,2,17,NULL,115),('Brand',345,NULL,NULL,2,12,NULL,115),('Brand',346,NULL,NULL,6,21,NULL,116),('Brand',347,NULL,NULL,6,27,NULL,116),('Brand',348,NULL,NULL,6,1,NULL,116),('Brand',349,NULL,NULL,13,20,NULL,117),('Brand',350,NULL,NULL,13,17,NULL,117),('Brand',351,NULL,NULL,13,3,NULL,117),('Brand',352,NULL,NULL,10,5,NULL,118),('Brand',353,NULL,NULL,10,14,NULL,118),('Brand',354,NULL,NULL,10,16,NULL,118),('Brand',355,NULL,NULL,11,5,NULL,119),('Brand',356,NULL,NULL,11,28,NULL,119),('Brand',357,NULL,NULL,11,8,NULL,119),('Brand',358,NULL,NULL,17,24,NULL,120),('Brand',359,NULL,NULL,17,20,NULL,120),('Brand',360,NULL,NULL,17,19,NULL,120),('Brand',361,NULL,NULL,13,20,NULL,121),('Brand',362,NULL,NULL,13,13,NULL,121),('Brand',363,NULL,NULL,13,7,NULL,121),('Brand',364,NULL,NULL,12,20,NULL,122),('Brand',365,NULL,NULL,12,23,NULL,122),('Brand',366,NULL,NULL,12,19,NULL,122),('Brand',367,NULL,NULL,17,24,NULL,123),('Brand',368,NULL,NULL,17,21,NULL,123),('Brand',369,NULL,NULL,17,17,NULL,123),('OpenPossibility',370,NULL,'9,95 €',4,NULL,NULL,124),('OpenPossibility',371,NULL,'8,95 €',4,NULL,NULL,124),('OpenPossibility',372,NULL,'9,99 €',4,NULL,NULL,124),('Product',373,NULL,NULL,8,NULL,8,125),('Product',374,NULL,NULL,8,NULL,9,125),('Product',375,NULL,NULL,8,NULL,7,125),('Product',376,NULL,NULL,20,NULL,1,126),('Product',377,NULL,NULL,20,NULL,2,126),('Product',378,NULL,NULL,20,NULL,3,126),('Brand',379,NULL,NULL,1,14,NULL,127),('Brand',380,NULL,NULL,1,27,NULL,127),('Brand',381,NULL,NULL,1,1,NULL,127),('Product',382,NULL,NULL,8,NULL,8,128),('Product',383,NULL,NULL,8,NULL,9,128),('Product',384,NULL,NULL,8,NULL,7,128),('Brand',385,NULL,NULL,3,15,NULL,129),('Brand',386,NULL,NULL,3,7,NULL,129),('Brand',387,NULL,NULL,3,10,NULL,129),('Brand',388,NULL,NULL,13,20,NULL,130),('Brand',389,NULL,NULL,13,28,NULL,130),('Brand',390,NULL,NULL,13,29,NULL,130),('Brand',391,NULL,NULL,14,20,NULL,131),('Brand',392,NULL,NULL,14,24,NULL,131),('Brand',393,NULL,NULL,14,15,NULL,131),('OpenPossibility',394,NULL,'9,95 €',4,NULL,NULL,132),('OpenPossibility',395,NULL,'8,95 €',4,NULL,NULL,132),('OpenPossibility',396,NULL,'9,99 €',4,NULL,NULL,132),('Brand',397,NULL,NULL,16,23,NULL,133),('Brand',398,NULL,NULL,16,20,NULL,133),('Brand',399,NULL,NULL,16,17,NULL,133),('Brand',400,NULL,NULL,2,28,NULL,134),('Brand',401,NULL,NULL,2,24,NULL,134),('Brand',402,NULL,NULL,2,11,NULL,134),('Brand',403,NULL,NULL,11,5,NULL,135),('Brand',404,NULL,NULL,11,27,NULL,135),('Brand',405,NULL,NULL,11,8,NULL,135),('Brand',406,NULL,NULL,17,24,NULL,136),('Brand',407,NULL,NULL,17,1,NULL,136),('Brand',408,NULL,NULL,17,12,NULL,136),('Brand',409,NULL,NULL,13,20,NULL,137),('Brand',410,NULL,NULL,13,29,NULL,137),('Brand',411,NULL,NULL,13,17,NULL,137),('OpenPossibility',412,NULL,'94 %',22,NULL,NULL,138),('OpenPossibility',413,NULL,'84 %',22,NULL,NULL,138),('OpenPossibility',414,NULL,'92 %',22,NULL,NULL,138),('OpenPossibility',415,NULL,'94 %',22,NULL,NULL,139),('OpenPossibility',416,NULL,'92 %',22,NULL,NULL,139),('OpenPossibility',417,NULL,'84 %',22,NULL,NULL,139),('Brand',418,NULL,NULL,2,28,NULL,140),('Brand',419,NULL,NULL,2,9,NULL,140),('Brand',420,NULL,NULL,2,10,NULL,140),('Brand',421,NULL,NULL,11,5,NULL,141),('Brand',422,NULL,NULL,11,15,NULL,141),('Brand',423,NULL,NULL,11,22,NULL,141),('Brand',424,NULL,NULL,12,20,NULL,142),('Brand',425,NULL,NULL,12,21,NULL,142),('Brand',426,NULL,NULL,12,28,NULL,142),('OpenPossibility',427,NULL,'10000 kilomètres',5,NULL,NULL,143),('OpenPossibility',428,NULL,'8000 kilomètres',5,NULL,NULL,143),('OpenPossibility',429,NULL,'12000 kilomètres',5,NULL,NULL,143),('Product',430,NULL,NULL,8,NULL,8,144),('Product',431,NULL,NULL,8,NULL,9,144),('Product',432,NULL,NULL,8,NULL,7,144),('OpenPossibility',433,NULL,'Vivre ici',23,NULL,NULL,145),('OpenPossibility',434,NULL,'Venir d\'ici',23,NULL,NULL,145),('OpenPossibility',435,NULL,'Aller d\'ici',23,NULL,NULL,145),('Brand',436,NULL,NULL,17,24,NULL,146),('Brand',437,NULL,NULL,17,13,NULL,146),('Brand',438,NULL,NULL,17,10,NULL,146),('Brand',439,NULL,NULL,18,25,NULL,147),('Brand',440,NULL,NULL,18,1,NULL,147),('Brand',441,NULL,NULL,18,12,NULL,147),('Brand',442,NULL,NULL,21,29,NULL,148),('Brand',443,NULL,NULL,21,7,NULL,148),('Brand',444,NULL,NULL,21,20,NULL,148),('Brand',445,NULL,NULL,10,5,NULL,149),('Brand',446,NULL,NULL,10,3,NULL,149),('Brand',447,NULL,NULL,10,10,NULL,149),('Brand',448,NULL,NULL,14,20,NULL,150),('Brand',449,NULL,NULL,14,17,NULL,150),('Brand',450,NULL,NULL,14,1,NULL,150),('Brand',451,NULL,NULL,15,18,NULL,151),('Brand',452,NULL,NULL,15,30,NULL,151),('Brand',453,NULL,NULL,15,6,NULL,151),('Brand',454,NULL,NULL,21,29,NULL,152),('Brand',455,NULL,NULL,21,31,NULL,152),('Brand',456,NULL,NULL,21,14,NULL,152),('Brand',457,NULL,NULL,16,23,NULL,153),('Brand',458,NULL,NULL,16,5,NULL,153),('Brand',459,NULL,NULL,16,10,NULL,153),('Brand',460,NULL,NULL,17,24,NULL,154),('Brand',461,NULL,NULL,17,16,NULL,154),('Brand',462,NULL,NULL,17,15,NULL,154),('OpenPossibility',463,NULL,'10000 kilomètres',5,NULL,NULL,155),('OpenPossibility',464,NULL,'12000 kilomètres',5,NULL,NULL,155),('OpenPossibility',465,NULL,'8000 kilomètres',5,NULL,NULL,155),('Brand',466,NULL,NULL,6,21,NULL,156),('Brand',467,NULL,NULL,6,3,NULL,156),('Brand',468,NULL,NULL,6,28,NULL,156),('Brand',469,NULL,NULL,15,18,NULL,157),('Brand',470,NULL,NULL,15,1,NULL,157),('Brand',471,NULL,NULL,15,16,NULL,157),('Brand',472,NULL,NULL,9,16,NULL,158),('Brand',473,NULL,NULL,9,27,NULL,158),('Brand',474,NULL,NULL,9,19,NULL,158),('Brand',475,NULL,NULL,2,28,NULL,159),('Brand',476,NULL,NULL,2,13,NULL,159),('Brand',477,NULL,NULL,2,14,NULL,159),('Brand',478,NULL,NULL,12,20,NULL,160),('Brand',479,NULL,NULL,12,25,NULL,160),('Brand',480,NULL,NULL,12,10,NULL,160),('Brand',481,NULL,NULL,14,20,NULL,161),('Brand',482,NULL,NULL,14,25,NULL,161),('Brand',483,NULL,NULL,14,29,NULL,161),('OpenPossibility',484,NULL,'10000 kilomètres',5,NULL,NULL,162),('OpenPossibility',485,NULL,'8000 kilomètres',5,NULL,NULL,162),('OpenPossibility',486,NULL,'12000 kilomètres',5,NULL,NULL,162),('Brand',487,NULL,NULL,2,28,NULL,163),('Brand',488,NULL,NULL,2,22,NULL,163),('Brand',489,NULL,NULL,2,7,NULL,163),('Brand',490,NULL,NULL,14,20,NULL,164),('Brand',491,NULL,NULL,14,3,NULL,164),('Brand',492,NULL,NULL,14,16,NULL,164),('Brand',493,NULL,NULL,19,26,NULL,165),('Brand',494,NULL,NULL,19,5,NULL,165),('Brand',495,NULL,NULL,19,8,NULL,165),('Brand',496,NULL,NULL,13,20,NULL,166),('Brand',497,NULL,NULL,13,7,NULL,166),('Brand',498,NULL,NULL,13,10,NULL,166),('OpenPossibility',499,NULL,'9,95 €',4,NULL,NULL,167),('OpenPossibility',500,NULL,'8,95 €',4,NULL,NULL,167),('OpenPossibility',501,NULL,'9,99 €',4,NULL,NULL,167),('Brand',502,NULL,NULL,1,14,NULL,168),('Brand',503,NULL,NULL,1,24,NULL,168),('Brand',504,NULL,NULL,1,27,NULL,168),('Brand',505,NULL,NULL,12,20,NULL,169),('Brand',506,NULL,NULL,12,26,NULL,169),('Brand',507,NULL,NULL,12,8,NULL,169),('Brand',508,NULL,NULL,11,5,NULL,170),('Brand',509,NULL,NULL,11,29,NULL,170),('Brand',510,NULL,NULL,11,25,NULL,170),('Brand',511,NULL,NULL,14,20,NULL,171),('Brand',512,NULL,NULL,14,21,NULL,171),('Brand',513,NULL,NULL,14,18,NULL,171),('Product',514,NULL,NULL,7,NULL,4,172),('Product',515,NULL,NULL,7,NULL,6,172),('Product',516,NULL,NULL,7,NULL,5,172),('Brand',517,NULL,NULL,15,18,NULL,173),('Brand',518,NULL,NULL,15,29,NULL,173),('Brand',519,NULL,NULL,15,12,NULL,173),('Brand',520,NULL,NULL,17,24,NULL,174),('Brand',521,NULL,NULL,17,29,NULL,174),('Brand',522,NULL,NULL,17,25,NULL,174),('Brand',523,NULL,NULL,21,29,NULL,175),('Brand',524,NULL,NULL,21,16,NULL,175),('Brand',525,NULL,NULL,21,28,NULL,175),('Brand',526,NULL,NULL,18,25,NULL,176),('Brand',527,NULL,NULL,18,28,NULL,176),('Brand',528,NULL,NULL,18,31,NULL,176),('Brand',529,NULL,NULL,9,16,NULL,177),('Brand',530,NULL,NULL,9,7,NULL,177),('Brand',531,NULL,NULL,9,14,NULL,177),('Brand',532,NULL,NULL,16,23,NULL,178),('Brand',533,NULL,NULL,16,28,NULL,178),('Brand',534,NULL,NULL,16,3,NULL,178),('OpenPossibility',535,NULL,'94 %',22,NULL,NULL,179),('OpenPossibility',536,NULL,'92 %',22,NULL,NULL,179),('OpenPossibility',537,NULL,'84 %',22,NULL,NULL,179),('Brand',538,NULL,NULL,2,28,NULL,180),('Brand',539,NULL,NULL,2,15,NULL,180),('Brand',540,NULL,NULL,2,3,NULL,180),('Brand',541,NULL,NULL,6,21,NULL,181),('Brand',542,NULL,NULL,6,10,NULL,181),('Brand',543,NULL,NULL,6,1,NULL,181),('OpenPossibility',544,NULL,'10000 kilomètres',5,NULL,NULL,182),('OpenPossibility',545,NULL,'8000 kilomètres',5,NULL,NULL,182),('OpenPossibility',546,NULL,'12000 kilomètres',5,NULL,NULL,182),('Brand',547,NULL,NULL,18,25,NULL,183),('Brand',548,NULL,NULL,18,28,NULL,183),('Brand',549,NULL,NULL,18,2,NULL,183),('Brand',550,NULL,NULL,19,26,NULL,184),('Brand',551,NULL,NULL,19,11,NULL,184),('Brand',552,NULL,NULL,19,8,NULL,184),('Brand',553,NULL,NULL,1,14,NULL,185),('Brand',554,NULL,NULL,1,24,NULL,185),('Brand',555,NULL,NULL,1,22,NULL,185),('Brand',556,NULL,NULL,14,20,NULL,186),('Brand',557,NULL,NULL,14,13,NULL,186),('Brand',558,NULL,NULL,14,17,NULL,186),('Brand',559,NULL,NULL,21,29,NULL,187),('Brand',560,NULL,NULL,21,8,NULL,187),('Brand',561,NULL,NULL,21,15,NULL,187),('Brand',562,NULL,NULL,3,15,NULL,188),('Brand',563,NULL,NULL,3,29,NULL,188),('Brand',564,NULL,NULL,3,21,NULL,188),('Brand',565,NULL,NULL,12,20,NULL,189),('Brand',566,NULL,NULL,12,16,NULL,189),('Brand',567,NULL,NULL,12,4,NULL,189),('Brand',568,NULL,NULL,9,16,NULL,190),('Brand',569,NULL,NULL,9,27,NULL,190),('Brand',570,NULL,NULL,9,31,NULL,190),('OpenPossibility',571,NULL,'94 %',22,NULL,NULL,191),('OpenPossibility',572,NULL,'84 %',22,NULL,NULL,191),('OpenPossibility',573,NULL,'92 %',22,NULL,NULL,191),('Brand',574,NULL,NULL,10,5,NULL,192),('Brand',575,NULL,NULL,10,1,NULL,192),('Brand',576,NULL,NULL,10,23,NULL,192),('Brand',577,NULL,NULL,11,5,NULL,193),('Brand',578,NULL,NULL,11,30,NULL,193),('Brand',579,NULL,NULL,11,15,NULL,193),('Product',580,NULL,NULL,8,NULL,8,194),('Product',581,NULL,NULL,8,NULL,7,194),('Product',582,NULL,NULL,8,NULL,9,194),('Brand',583,NULL,NULL,9,16,NULL,195),('Brand',584,NULL,NULL,9,1,NULL,195),('Brand',585,NULL,NULL,9,10,NULL,195),('Brand',586,NULL,NULL,2,28,NULL,196),('Brand',587,NULL,NULL,2,19,NULL,196),('Brand',588,NULL,NULL,2,13,NULL,196),('Brand',589,NULL,NULL,6,21,NULL,197),('Brand',590,NULL,NULL,6,29,NULL,197),('Brand',591,NULL,NULL,6,28,NULL,197),('Brand',592,NULL,NULL,3,15,NULL,198),('Brand',593,NULL,NULL,3,28,NULL,198),('Brand',594,NULL,NULL,3,4,NULL,198),('Brand',595,NULL,NULL,17,24,NULL,199),('Brand',596,NULL,NULL,17,3,NULL,199),('Brand',597,NULL,NULL,17,10,NULL,199),('Brand',598,NULL,NULL,3,15,NULL,200),('Brand',599,NULL,NULL,3,17,NULL,200),('Brand',600,NULL,NULL,3,2,NULL,200),('OpenPossibility',601,NULL,'94 %',22,NULL,NULL,201),('OpenPossibility',602,NULL,'92 %',22,NULL,NULL,201),('OpenPossibility',603,NULL,'84 %',22,NULL,NULL,201),('Product',604,NULL,NULL,20,NULL,1,202),('Product',605,NULL,NULL,20,NULL,3,202),('Product',606,NULL,NULL,20,NULL,2,202),('Brand',607,NULL,NULL,18,25,NULL,203),('Brand',608,NULL,NULL,18,18,NULL,203),('Brand',609,NULL,NULL,18,14,NULL,203),('OpenPossibility',610,NULL,'9,95 €',4,NULL,NULL,204),('OpenPossibility',611,NULL,'9,99 €',4,NULL,NULL,204),('OpenPossibility',612,NULL,'8,95 €',4,NULL,NULL,204),('Brand',613,NULL,NULL,19,26,NULL,205),('Brand',614,NULL,NULL,19,24,NULL,205),('Brand',615,NULL,NULL,19,14,NULL,205),('OpenPossibility',616,NULL,'Vivre ici',23,NULL,NULL,206),('OpenPossibility',617,NULL,'Aller d\'ici',23,NULL,NULL,206),('OpenPossibility',618,NULL,'Venir d\'ici',23,NULL,NULL,206),('Brand',619,NULL,NULL,2,28,NULL,207),('Brand',620,NULL,NULL,2,12,NULL,207),('Brand',621,NULL,NULL,2,9,NULL,207),('Brand',622,NULL,NULL,12,20,NULL,208),('Brand',623,NULL,NULL,12,6,NULL,208),('Brand',624,NULL,NULL,12,23,NULL,208),('Brand',625,NULL,NULL,14,20,NULL,209),('Brand',626,NULL,NULL,14,11,NULL,209),('Brand',627,NULL,NULL,14,28,NULL,209),('Brand',628,NULL,NULL,1,14,NULL,210),('Brand',629,NULL,NULL,1,10,NULL,210),('Brand',630,NULL,NULL,1,28,NULL,210),('Brand',631,NULL,NULL,2,28,NULL,211),('Brand',632,NULL,NULL,2,30,NULL,211),('Brand',633,NULL,NULL,2,20,NULL,211),('Brand',634,NULL,NULL,15,18,NULL,212),('Brand',635,NULL,NULL,15,2,NULL,212),('Brand',636,NULL,NULL,15,11,NULL,212),('Product',637,NULL,NULL,20,NULL,1,213),('Product',638,NULL,NULL,20,NULL,3,213),('Product',639,NULL,NULL,20,NULL,2,213),('Brand',640,NULL,NULL,12,20,NULL,214),('Brand',641,NULL,NULL,12,7,NULL,214),('Brand',642,NULL,NULL,12,27,NULL,214),('OpenPossibility',643,NULL,'9,95 €',4,NULL,NULL,215),('OpenPossibility',644,NULL,'9,99 €',4,NULL,NULL,215),('OpenPossibility',645,NULL,'8,95 €',4,NULL,NULL,215),('Brand',646,NULL,NULL,9,16,NULL,216),('Brand',647,NULL,NULL,9,26,NULL,216),('Brand',648,NULL,NULL,9,22,NULL,216),('Brand',649,NULL,NULL,17,24,NULL,217),('Brand',650,NULL,NULL,17,14,NULL,217),('Brand',651,NULL,NULL,17,17,NULL,217),('OpenPossibility',652,NULL,'9,95 €',4,NULL,NULL,218),('OpenPossibility',653,NULL,'8,95 €',4,NULL,NULL,218),('OpenPossibility',654,NULL,'9,99 €',4,NULL,NULL,218),('Product',655,NULL,NULL,7,NULL,4,219),('Product',656,NULL,NULL,7,NULL,6,219),('Product',657,NULL,NULL,7,NULL,5,219),('Brand',658,NULL,NULL,14,20,NULL,220),('Brand',659,NULL,NULL,14,27,NULL,220),('Brand',660,NULL,NULL,14,14,NULL,220),('Brand',661,NULL,NULL,9,16,NULL,221),('Brand',662,NULL,NULL,9,1,NULL,221),('Brand',663,NULL,NULL,9,26,NULL,221),('Brand',664,NULL,NULL,11,5,NULL,222),('Brand',665,NULL,NULL,11,29,NULL,222),('Brand',666,NULL,NULL,11,6,NULL,222),('OpenPossibility',667,NULL,'Vivre ici',23,NULL,NULL,223),('OpenPossibility',668,NULL,'Venir d\'ici',23,NULL,NULL,223),('OpenPossibility',669,NULL,'Aller d\'ici',23,NULL,NULL,223),('Brand',670,NULL,NULL,1,14,NULL,224),('Brand',671,NULL,NULL,1,30,NULL,224),('Brand',672,NULL,NULL,1,1,NULL,224),('Brand',673,NULL,NULL,12,20,NULL,225),('Brand',674,NULL,NULL,12,25,NULL,225),('Brand',675,NULL,NULL,12,10,NULL,225),('Product',676,NULL,NULL,7,NULL,4,226),('Product',677,NULL,NULL,7,NULL,6,226),('Product',678,NULL,NULL,7,NULL,5,226),('Brand',679,NULL,NULL,19,26,NULL,227),('Brand',680,NULL,NULL,19,23,NULL,227),('Brand',681,NULL,NULL,19,21,NULL,227),('Brand',682,NULL,NULL,15,18,NULL,228),('Brand',683,NULL,NULL,15,19,NULL,228),('Brand',684,NULL,NULL,15,8,NULL,228),('Brand',685,NULL,NULL,2,28,NULL,229),('Brand',686,NULL,NULL,2,17,NULL,229),('Brand',687,NULL,NULL,2,30,NULL,229),('Brand',688,NULL,NULL,21,29,NULL,230),('Brand',689,NULL,NULL,21,23,NULL,230),('Brand',690,NULL,NULL,21,17,NULL,230),('OpenPossibility',691,NULL,'Vivre ici',23,NULL,NULL,231),('OpenPossibility',692,NULL,'Venir d\'ici',23,NULL,NULL,231),('OpenPossibility',693,NULL,'Aller d\'ici',23,NULL,NULL,231),('Brand',694,NULL,NULL,16,23,NULL,232),('Brand',695,NULL,NULL,16,21,NULL,232),('Brand',696,NULL,NULL,16,12,NULL,232),('Brand',697,NULL,NULL,14,20,NULL,233),('Brand',698,NULL,NULL,14,13,NULL,233),('Brand',699,NULL,NULL,14,23,NULL,233),('Brand',700,NULL,NULL,18,25,NULL,234),('Brand',701,NULL,NULL,18,7,NULL,234),('Brand',702,NULL,NULL,18,2,NULL,234),('Product',703,NULL,NULL,7,NULL,4,235),('Product',704,NULL,NULL,7,NULL,5,235),('Product',705,NULL,NULL,7,NULL,6,235),('Brand',706,NULL,NULL,19,26,NULL,236),('Brand',707,NULL,NULL,19,13,NULL,236),('Brand',708,NULL,NULL,19,6,NULL,236),('Brand',709,NULL,NULL,11,5,NULL,237),('Brand',710,NULL,NULL,11,29,NULL,237),('Brand',711,NULL,NULL,11,15,NULL,237),('Brand',712,NULL,NULL,12,20,NULL,238),('Brand',713,NULL,NULL,12,27,NULL,238),('Brand',714,NULL,NULL,12,21,NULL,238),('Brand',715,NULL,NULL,6,21,NULL,239),('Brand',716,NULL,NULL,6,30,NULL,239),('Brand',717,NULL,NULL,6,20,NULL,239),('Brand',718,NULL,NULL,15,18,NULL,240),('Brand',719,NULL,NULL,15,22,NULL,240),('Brand',720,NULL,NULL,15,2,NULL,240),('Brand',721,NULL,NULL,9,16,NULL,241),('Brand',722,NULL,NULL,9,20,NULL,241),('Brand',723,NULL,NULL,9,13,NULL,241),('Brand',724,NULL,NULL,21,29,NULL,242),('Brand',725,NULL,NULL,21,8,NULL,242),('Brand',726,NULL,NULL,21,22,NULL,242),('Brand',727,NULL,NULL,14,20,NULL,243),('Brand',728,NULL,NULL,14,11,NULL,243),('Brand',729,NULL,NULL,14,4,NULL,243),('OpenPossibility',730,NULL,'9,95 €',4,NULL,NULL,244),('OpenPossibility',731,NULL,'8,95 €',4,NULL,NULL,244),('OpenPossibility',732,NULL,'9,99 €',4,NULL,NULL,244),('OpenPossibility',733,NULL,'94 %',22,NULL,NULL,245),('OpenPossibility',734,NULL,'92 %',22,NULL,NULL,245),('OpenPossibility',735,NULL,'84 %',22,NULL,NULL,245),('Brand',736,NULL,NULL,6,21,NULL,246),('Brand',737,NULL,NULL,6,15,NULL,246),('Brand',738,NULL,NULL,6,17,NULL,246),('Brand',739,NULL,NULL,16,23,NULL,247),('Brand',740,NULL,NULL,16,17,NULL,247),('Brand',741,NULL,NULL,16,18,NULL,247),('Brand',742,NULL,NULL,21,29,NULL,248),('Brand',743,NULL,NULL,21,13,NULL,248),('Brand',744,NULL,NULL,21,2,NULL,248),('Product',745,NULL,NULL,8,NULL,8,249),('Product',746,NULL,NULL,8,NULL,7,249),('Product',747,NULL,NULL,8,NULL,9,249),('Brand',748,NULL,NULL,11,5,NULL,250),('Brand',749,NULL,NULL,11,15,NULL,250),('Brand',750,NULL,NULL,11,22,NULL,250),('Brand',751,NULL,NULL,2,28,NULL,251),('Brand',752,NULL,NULL,2,14,NULL,251),('Brand',753,NULL,NULL,2,11,NULL,251),('Brand',754,NULL,NULL,13,20,NULL,252),('Brand',755,NULL,NULL,13,27,NULL,252),('Brand',756,NULL,NULL,13,6,NULL,252),('Brand',757,NULL,NULL,17,24,NULL,253),('Brand',758,NULL,NULL,17,15,NULL,253),('Brand',759,NULL,NULL,17,2,NULL,253),('Brand',760,NULL,NULL,12,20,NULL,254),('Brand',761,NULL,NULL,12,7,NULL,254),('Brand',762,NULL,NULL,12,18,NULL,254),('OpenPossibility',763,NULL,'10000 kilomètres',5,NULL,NULL,255),('OpenPossibility',764,NULL,'12000 kilomètres',5,NULL,NULL,255),('OpenPossibility',765,NULL,'8000 kilomètres',5,NULL,NULL,255),('Brand',766,NULL,NULL,21,29,NULL,256),('Brand',767,NULL,NULL,21,2,NULL,256),('Brand',768,NULL,NULL,21,30,NULL,256),('Brand',769,NULL,NULL,19,26,NULL,257),('Brand',770,NULL,NULL,19,5,NULL,257),('Brand',771,NULL,NULL,19,19,NULL,257),('Product',772,NULL,NULL,20,NULL,1,258),('Product',773,NULL,NULL,20,NULL,3,258),('Product',774,NULL,NULL,20,NULL,2,258),('Brand',775,NULL,NULL,15,18,NULL,259),('Brand',776,NULL,NULL,15,16,NULL,259),('Brand',777,NULL,NULL,15,1,NULL,259),('OpenPossibility',778,NULL,'94 %',22,NULL,NULL,260),('OpenPossibility',779,NULL,'84 %',22,NULL,NULL,260),('OpenPossibility',780,NULL,'92 %',22,NULL,NULL,260),('Product',781,NULL,NULL,7,NULL,4,261),('Product',782,NULL,NULL,7,NULL,6,261),('Product',783,NULL,NULL,7,NULL,5,261),('Brand',784,NULL,NULL,18,25,NULL,262),('Brand',785,NULL,NULL,18,4,NULL,262),('Brand',786,NULL,NULL,18,27,NULL,262),('Product',787,NULL,NULL,20,NULL,1,263),('Product',788,NULL,NULL,20,NULL,3,263),('Product',789,NULL,NULL,20,NULL,2,263),('Brand',790,NULL,NULL,3,15,NULL,264),('Brand',791,NULL,NULL,3,13,NULL,264),('Brand',792,NULL,NULL,3,20,NULL,264),('Brand',793,NULL,NULL,17,24,NULL,265),('Brand',794,NULL,NULL,17,27,NULL,265),('Brand',795,NULL,NULL,17,16,NULL,265),('Product',796,NULL,NULL,20,NULL,1,266),('Product',797,NULL,NULL,20,NULL,3,266),('Product',798,NULL,NULL,20,NULL,2,266),('Brand',799,NULL,NULL,14,20,NULL,267),('Brand',800,NULL,NULL,14,8,NULL,267),('Brand',801,NULL,NULL,14,10,NULL,267),('OpenPossibility',802,NULL,'94 %',22,NULL,NULL,268),('OpenPossibility',803,NULL,'92 %',22,NULL,NULL,268),('OpenPossibility',804,NULL,'84 %',22,NULL,NULL,268),('Brand',805,NULL,NULL,19,26,NULL,269),('Brand',806,NULL,NULL,19,3,NULL,269),('Brand',807,NULL,NULL,19,8,NULL,269),('Brand',808,NULL,NULL,11,5,NULL,270),('Brand',809,NULL,NULL,11,27,NULL,270),('Brand',810,NULL,NULL,11,26,NULL,270),('Brand',811,NULL,NULL,9,16,NULL,271),('Brand',812,NULL,NULL,9,27,NULL,271),('Brand',813,NULL,NULL,9,25,NULL,271),('Brand',814,NULL,NULL,10,5,NULL,272),('Brand',815,NULL,NULL,10,7,NULL,272),('Brand',816,NULL,NULL,10,21,NULL,272),('Brand',817,NULL,NULL,13,20,NULL,273),('Brand',818,NULL,NULL,13,22,NULL,273),('Brand',819,NULL,NULL,13,9,NULL,273),('Brand',820,NULL,NULL,1,14,NULL,274),('Brand',821,NULL,NULL,1,31,NULL,274),('Brand',822,NULL,NULL,1,15,NULL,274),('Brand',823,NULL,NULL,6,21,NULL,275),('Brand',824,NULL,NULL,6,6,NULL,275),('Brand',825,NULL,NULL,6,22,NULL,275),('Brand',826,NULL,NULL,11,5,NULL,276),('Brand',827,NULL,NULL,11,9,NULL,276),('Brand',828,NULL,NULL,11,10,NULL,276),('Brand',829,NULL,NULL,1,14,NULL,277),('Brand',830,NULL,NULL,1,31,NULL,277),('Brand',831,NULL,NULL,1,4,NULL,277),('OpenPossibility',832,NULL,'94 %',22,NULL,NULL,278),('OpenPossibility',833,NULL,'92 %',22,NULL,NULL,278),('OpenPossibility',834,NULL,'84 %',22,NULL,NULL,278),('Brand',835,NULL,NULL,3,15,NULL,279),('Brand',836,NULL,NULL,3,29,NULL,279),('Brand',837,NULL,NULL,3,30,NULL,279),('Brand',838,NULL,NULL,18,25,NULL,280),('Brand',839,NULL,NULL,18,31,NULL,280),('Brand',840,NULL,NULL,18,1,NULL,280),('Brand',841,NULL,NULL,12,20,NULL,281),('Brand',842,NULL,NULL,12,7,NULL,281),('Brand',843,NULL,NULL,12,15,NULL,281),('Brand',844,NULL,NULL,6,21,NULL,282),('Brand',845,NULL,NULL,6,25,NULL,282),('Brand',846,NULL,NULL,6,9,NULL,282),('Product',847,NULL,NULL,7,NULL,4,283),('Product',848,NULL,NULL,7,NULL,6,283),('Product',849,NULL,NULL,7,NULL,5,283),('Brand',850,NULL,NULL,11,5,NULL,284),('Brand',851,NULL,NULL,11,28,NULL,284),('Brand',852,NULL,NULL,11,10,NULL,284),('Brand',853,NULL,NULL,15,18,NULL,285),('Brand',854,NULL,NULL,15,27,NULL,285),('Brand',855,NULL,NULL,15,21,NULL,285),('Brand',856,NULL,NULL,2,28,NULL,286),('Brand',857,NULL,NULL,2,13,NULL,286),('Brand',858,NULL,NULL,2,2,NULL,286),('Brand',859,NULL,NULL,3,15,NULL,287),('Brand',860,NULL,NULL,3,7,NULL,287),('Brand',861,NULL,NULL,3,8,NULL,287),('Product',862,NULL,NULL,8,NULL,8,288),('Product',863,NULL,NULL,8,NULL,9,288),('Product',864,NULL,NULL,8,NULL,7,288),('Brand',865,NULL,NULL,10,5,NULL,289),('Brand',866,NULL,NULL,10,13,NULL,289),('Brand',867,NULL,NULL,10,6,NULL,289),('Product',868,NULL,NULL,7,NULL,4,290),('Product',869,NULL,NULL,7,NULL,5,290),('Product',870,NULL,NULL,7,NULL,6,290),('Brand',871,NULL,NULL,15,18,NULL,291),('Brand',872,NULL,NULL,15,17,NULL,291),('Brand',873,NULL,NULL,15,31,NULL,291),('OpenPossibility',874,NULL,'Vivre ici',23,NULL,NULL,292),('OpenPossibility',875,NULL,'Venir d\'ici',23,NULL,NULL,292),('OpenPossibility',876,NULL,'Aller d\'ici',23,NULL,NULL,292),('OpenPossibility',877,NULL,'10000 kilomètres',5,NULL,NULL,293),('OpenPossibility',878,NULL,'12000 kilomètres',5,NULL,NULL,293),('OpenPossibility',879,NULL,'8000 kilomètres',5,NULL,NULL,293),('Product',880,NULL,NULL,20,NULL,1,294),('Product',881,NULL,NULL,20,NULL,3,294),('Product',882,NULL,NULL,20,NULL,2,294),('Brand',883,NULL,NULL,6,21,NULL,295),('Brand',884,NULL,NULL,6,20,NULL,295),('Brand',885,NULL,NULL,6,8,NULL,295),('Brand',886,NULL,NULL,10,5,NULL,296),('Brand',887,NULL,NULL,10,16,NULL,296),('Brand',888,NULL,NULL,10,1,NULL,296),('Product',889,NULL,NULL,8,NULL,8,297),('Product',890,NULL,NULL,8,NULL,9,297),('Product',891,NULL,NULL,8,NULL,7,297),('Brand',892,NULL,NULL,2,28,NULL,298),('Brand',893,NULL,NULL,2,20,NULL,298),('Brand',894,NULL,NULL,2,13,NULL,298),('Brand',895,NULL,NULL,14,20,NULL,299),('Brand',896,NULL,NULL,14,19,NULL,299),('Brand',897,NULL,NULL,14,14,NULL,299),('Brand',898,NULL,NULL,17,24,NULL,300),('Brand',899,NULL,NULL,17,18,NULL,300),('Brand',900,NULL,NULL,17,15,NULL,300),('Brand',901,NULL,NULL,10,5,NULL,301),('Brand',902,NULL,NULL,10,21,NULL,301),('Brand',903,NULL,NULL,10,19,NULL,301),('Product',904,NULL,NULL,7,NULL,4,302),('Product',905,NULL,NULL,7,NULL,5,302),('Product',906,NULL,NULL,7,NULL,6,302),('Brand',907,NULL,NULL,9,16,NULL,303),('Brand',908,NULL,NULL,9,27,NULL,303),('Brand',909,NULL,NULL,9,14,NULL,303),('OpenPossibility',910,NULL,'Vivre ici',23,NULL,NULL,304),('OpenPossibility',911,NULL,'Venir d\'ici',23,NULL,NULL,304),('OpenPossibility',912,NULL,'Aller d\'ici',23,NULL,NULL,304),('Brand',913,NULL,NULL,11,5,NULL,305),('Brand',914,NULL,NULL,11,20,NULL,305),('Brand',915,NULL,NULL,11,24,NULL,305),('Brand',916,NULL,NULL,16,23,NULL,306),('Brand',917,NULL,NULL,16,17,NULL,306),('Brand',918,NULL,NULL,16,19,NULL,306),('Product',919,NULL,NULL,20,NULL,1,307),('Product',920,NULL,NULL,20,NULL,3,307),('Product',921,NULL,NULL,20,NULL,2,307),('OpenPossibility',922,NULL,'10000 kilomètres',5,NULL,NULL,308),('OpenPossibility',923,NULL,'12000 kilomètres',5,NULL,NULL,308),('OpenPossibility',924,NULL,'8000 kilomètres',5,NULL,NULL,308),('Brand',925,NULL,NULL,2,28,NULL,309),('Brand',926,NULL,NULL,2,17,NULL,309),('Brand',927,NULL,NULL,2,12,NULL,309),('Brand',928,NULL,NULL,6,21,NULL,310),('Brand',929,NULL,NULL,6,23,NULL,310),('Brand',930,NULL,NULL,6,14,NULL,310),('OpenPossibility',931,NULL,'9,95 €',4,NULL,NULL,311),('OpenPossibility',932,NULL,'9,99 €',4,NULL,NULL,311),('OpenPossibility',933,NULL,'8,95 €',4,NULL,NULL,311),('Brand',934,NULL,NULL,21,29,NULL,312),('Brand',935,NULL,NULL,21,10,NULL,312),('Brand',936,NULL,NULL,21,3,NULL,312),('Brand',937,NULL,NULL,15,18,NULL,313),('Brand',938,NULL,NULL,15,2,NULL,313),('Brand',939,NULL,NULL,15,3,NULL,313),('Brand',940,NULL,NULL,14,20,NULL,314),('Brand',941,NULL,NULL,14,24,NULL,314),('Brand',942,NULL,NULL,14,12,NULL,314),('OpenPossibility',943,NULL,'94 %',22,NULL,NULL,315),('OpenPossibility',944,NULL,'92 %',22,NULL,NULL,315),('OpenPossibility',945,NULL,'84 %',22,NULL,NULL,315),('Product',946,NULL,NULL,7,NULL,4,316),('Product',947,NULL,NULL,7,NULL,6,316),('Product',948,NULL,NULL,7,NULL,5,316),('Brand',949,NULL,NULL,11,5,NULL,317),('Brand',950,NULL,NULL,11,30,NULL,317),('Brand',951,NULL,NULL,11,13,NULL,317),('Brand',952,NULL,NULL,1,14,NULL,318),('Brand',953,NULL,NULL,1,12,NULL,318),('Brand',954,NULL,NULL,1,28,NULL,318),('Product',955,NULL,NULL,7,NULL,4,319),('Product',956,NULL,NULL,7,NULL,5,319),('Product',957,NULL,NULL,7,NULL,6,319),('Brand',958,NULL,NULL,9,16,NULL,320),('Brand',959,NULL,NULL,9,19,NULL,320),('Brand',960,NULL,NULL,9,27,NULL,320),('Brand',961,NULL,NULL,19,26,NULL,321),('Brand',962,NULL,NULL,19,5,NULL,321),('Brand',963,NULL,NULL,19,10,NULL,321),('Brand',964,NULL,NULL,1,14,NULL,322),('Brand',965,NULL,NULL,1,2,NULL,322),('Brand',966,NULL,NULL,1,22,NULL,322),('Product',967,NULL,NULL,20,NULL,1,323),('Product',968,NULL,NULL,20,NULL,3,323),('Product',969,NULL,NULL,20,NULL,2,323),('Brand',970,NULL,NULL,2,28,NULL,324),('Brand',971,NULL,NULL,2,25,NULL,324),('Brand',972,NULL,NULL,2,8,NULL,324),('Brand',973,NULL,NULL,2,28,NULL,325),('Brand',974,NULL,NULL,2,21,NULL,325),('Brand',975,NULL,NULL,2,2,NULL,325),('Brand',976,NULL,NULL,15,18,NULL,326),('Brand',977,NULL,NULL,15,11,NULL,326),('Brand',978,NULL,NULL,15,7,NULL,326),('OpenPossibility',979,NULL,'Vivre ici',23,NULL,NULL,327),('OpenPossibility',980,NULL,'Venir d\'ici',23,NULL,NULL,327),('OpenPossibility',981,NULL,'Aller d\'ici',23,NULL,NULL,327),('OpenPossibility',982,NULL,'9,95 €',4,NULL,NULL,328),('OpenPossibility',983,NULL,'9,99 €',4,NULL,NULL,328),('OpenPossibility',984,NULL,'8,95 €',4,NULL,NULL,328),('Brand',985,NULL,NULL,10,5,NULL,329),('Brand',986,NULL,NULL,10,29,NULL,329),('Brand',987,NULL,NULL,10,14,NULL,329),('Brand',988,NULL,NULL,18,25,NULL,330),('Brand',989,NULL,NULL,18,29,NULL,330),('Brand',990,NULL,NULL,18,12,NULL,330),('Brand',991,NULL,NULL,10,5,NULL,331),('Brand',992,NULL,NULL,10,26,NULL,331),('Brand',993,NULL,NULL,10,6,NULL,331),('OpenPossibility',994,NULL,'94 %',22,NULL,NULL,332),('OpenPossibility',995,NULL,'92 %',22,NULL,NULL,332),('OpenPossibility',996,NULL,'84 %',22,NULL,NULL,332),('Brand',997,NULL,NULL,13,20,NULL,333),('Brand',998,NULL,NULL,13,26,NULL,333),('Brand',999,NULL,NULL,13,18,NULL,333),('Brand',1000,NULL,NULL,3,15,NULL,334),('Brand',1001,NULL,NULL,3,31,NULL,334),('Brand',1002,NULL,NULL,3,2,NULL,334),('Brand',1003,NULL,NULL,9,16,NULL,335),('Brand',1004,NULL,NULL,9,8,NULL,335),('Brand',1005,NULL,NULL,9,24,NULL,335),('Brand',1006,NULL,NULL,16,23,NULL,336),('Brand',1007,NULL,NULL,16,12,NULL,336),('Brand',1008,NULL,NULL,16,7,NULL,336),('Brand',1009,NULL,NULL,15,18,NULL,337),('Brand',1010,NULL,NULL,15,7,NULL,337),('Brand',1011,NULL,NULL,15,3,NULL,337),('Brand',1012,NULL,NULL,3,15,NULL,338),('Brand',1013,NULL,NULL,3,24,NULL,338),('Brand',1014,NULL,NULL,3,12,NULL,338),('Brand',1015,NULL,NULL,13,20,NULL,339),('Brand',1016,NULL,NULL,13,30,NULL,339),('Brand',1017,NULL,NULL,13,15,NULL,339),('OpenPossibility',1018,NULL,'94 %',22,NULL,NULL,340),('OpenPossibility',1019,NULL,'84 %',22,NULL,NULL,340),('OpenPossibility',1020,NULL,'92 %',22,NULL,NULL,340),('Product',1021,NULL,NULL,20,NULL,1,341),('Product',1022,NULL,NULL,20,NULL,2,341),('Product',1023,NULL,NULL,20,NULL,3,341),('OpenPossibility',1024,NULL,'10000 kilomètres',5,NULL,NULL,342),('OpenPossibility',1025,NULL,'8000 kilomètres',5,NULL,NULL,342),('OpenPossibility',1026,NULL,'12000 kilomètres',5,NULL,NULL,342),('OpenPossibility',1027,NULL,'Vivre ici',23,NULL,NULL,343),('OpenPossibility',1028,NULL,'Aller d\'ici',23,NULL,NULL,343),('OpenPossibility',1029,NULL,'Venir d\'ici',23,NULL,NULL,343),('Brand',1030,NULL,NULL,3,15,NULL,344),('Brand',1031,NULL,NULL,3,7,NULL,344),('Brand',1032,NULL,NULL,3,31,NULL,344),('Brand',1033,NULL,NULL,19,26,NULL,345),('Brand',1034,NULL,NULL,19,20,NULL,345),('Brand',1035,NULL,NULL,19,21,NULL,345),('Product',1036,NULL,NULL,20,NULL,1,346),('Product',1037,NULL,NULL,20,NULL,3,346),('Product',1038,NULL,NULL,20,NULL,2,346),('Brand',1039,NULL,NULL,11,5,NULL,347),('Brand',1040,NULL,NULL,11,17,NULL,347),('Brand',1041,NULL,NULL,11,7,NULL,347),('Product',1042,NULL,NULL,8,NULL,8,348),('Product',1043,NULL,NULL,8,NULL,9,348),('Product',1044,NULL,NULL,8,NULL,7,348),('OpenPossibility',1045,NULL,'10000 kilomètres',5,NULL,NULL,349),('OpenPossibility',1046,NULL,'8000 kilomètres',5,NULL,NULL,349),('OpenPossibility',1047,NULL,'12000 kilomètres',5,NULL,NULL,349),('Brand',1048,NULL,NULL,1,14,NULL,350),('Brand',1049,NULL,NULL,1,7,NULL,350),('Brand',1050,NULL,NULL,1,28,NULL,350),('Brand',1051,NULL,NULL,21,29,NULL,351),('Brand',1052,NULL,NULL,21,13,NULL,351),('Brand',1053,NULL,NULL,21,17,NULL,351),('Product',1054,NULL,NULL,7,NULL,4,352),('Product',1055,NULL,NULL,7,NULL,6,352),('Product',1056,NULL,NULL,7,NULL,5,352),('Brand',1057,NULL,NULL,17,24,NULL,353),('Brand',1058,NULL,NULL,17,4,NULL,353),('Brand',1059,NULL,NULL,17,31,NULL,353),('Brand',1060,NULL,NULL,3,15,NULL,354),('Brand',1061,NULL,NULL,3,6,NULL,354),('Brand',1062,NULL,NULL,3,4,NULL,354),('Brand',1063,NULL,NULL,3,15,NULL,355),('Brand',1064,NULL,NULL,3,28,NULL,355),('Brand',1065,NULL,NULL,3,25,NULL,355),('Brand',1066,NULL,NULL,14,20,NULL,356),('Brand',1067,NULL,NULL,14,29,NULL,356),('Brand',1068,NULL,NULL,14,5,NULL,356),('Brand',1069,NULL,NULL,2,28,NULL,357),('Brand',1070,NULL,NULL,2,14,NULL,357),('Brand',1071,NULL,NULL,2,10,NULL,357),('Brand',1072,NULL,NULL,15,18,NULL,358),('Brand',1073,NULL,NULL,15,8,NULL,358),('Brand',1074,NULL,NULL,15,16,NULL,358),('Brand',1075,NULL,NULL,10,5,NULL,359),('Brand',1076,NULL,NULL,10,21,NULL,359),('Brand',1077,NULL,NULL,10,3,NULL,359),('Brand',1078,NULL,NULL,19,26,NULL,360),('Brand',1079,NULL,NULL,19,22,NULL,360),('Brand',1080,NULL,NULL,19,8,NULL,360),('Brand',1081,NULL,NULL,21,29,NULL,361),('Brand',1082,NULL,NULL,21,7,NULL,361),('Brand',1083,NULL,NULL,21,22,NULL,361),('Product',1084,NULL,NULL,7,NULL,4,362),('Product',1085,NULL,NULL,7,NULL,6,362),('Product',1086,NULL,NULL,7,NULL,5,362),('OpenPossibility',1087,NULL,'94 %',22,NULL,NULL,363),('OpenPossibility',1088,NULL,'92 %',22,NULL,NULL,363),('OpenPossibility',1089,NULL,'84 %',22,NULL,NULL,363),('Brand',1090,NULL,NULL,18,25,NULL,364),('Brand',1091,NULL,NULL,18,31,NULL,364),('Brand',1092,NULL,NULL,18,10,NULL,364),('Brand',1093,NULL,NULL,11,5,NULL,365),('Brand',1094,NULL,NULL,11,24,NULL,365),('Brand',1095,NULL,NULL,11,29,NULL,365),('Brand',1096,NULL,NULL,15,18,NULL,366),('Brand',1097,NULL,NULL,15,7,NULL,366),('Brand',1098,NULL,NULL,15,26,NULL,366),('Brand',1099,NULL,NULL,6,21,NULL,367),('Brand',1100,NULL,NULL,6,24,NULL,367),('Brand',1101,NULL,NULL,6,27,NULL,367),('Brand',1102,NULL,NULL,3,15,NULL,368),('Brand',1103,NULL,NULL,3,16,NULL,368),('Brand',1104,NULL,NULL,3,20,NULL,368),('Brand',1105,NULL,NULL,13,20,NULL,369),('Brand',1106,NULL,NULL,13,5,NULL,369),('Brand',1107,NULL,NULL,13,10,NULL,369),('OpenPossibility',1108,NULL,'9,95 €',4,NULL,NULL,370),('OpenPossibility',1109,NULL,'8,95 €',4,NULL,NULL,370),('OpenPossibility',1110,NULL,'9,99 €',4,NULL,NULL,370),('OpenPossibility',1111,NULL,'Vivre ici',23,NULL,NULL,371),('OpenPossibility',1112,NULL,'Aller d\'ici',23,NULL,NULL,371),('OpenPossibility',1113,NULL,'Venir d\'ici',23,NULL,NULL,371),('Brand',1114,NULL,NULL,1,14,NULL,372),('Brand',1115,NULL,NULL,1,1,NULL,372),('Brand',1116,NULL,NULL,1,19,NULL,372),('Brand',1117,NULL,NULL,9,16,NULL,373),('Brand',1118,NULL,NULL,9,22,NULL,373),('Brand',1119,NULL,NULL,9,5,NULL,373),('Brand',1120,NULL,NULL,11,5,NULL,374),('Brand',1121,NULL,NULL,11,30,NULL,374),('Brand',1122,NULL,NULL,11,7,NULL,374),('Product',1123,NULL,NULL,8,NULL,8,375),('Product',1124,NULL,NULL,8,NULL,9,375),('Product',1125,NULL,NULL,8,NULL,7,375),('Brand',1126,NULL,NULL,3,15,NULL,376),('Brand',1127,NULL,NULL,3,17,NULL,376),('Brand',1128,NULL,NULL,3,8,NULL,376),('Brand',1129,NULL,NULL,15,18,NULL,377),('Brand',1130,NULL,NULL,15,24,NULL,377),('Brand',1131,NULL,NULL,15,12,NULL,377),('Brand',1132,NULL,NULL,12,20,NULL,378),('Brand',1133,NULL,NULL,12,8,NULL,378),('Brand',1134,NULL,NULL,12,22,NULL,378),('Brand',1135,NULL,NULL,2,28,NULL,379),('Brand',1136,NULL,NULL,2,10,NULL,379),('Brand',1137,NULL,NULL,2,14,NULL,379),('Product',1138,NULL,NULL,20,NULL,1,380),('Product',1139,NULL,NULL,20,NULL,2,380),('Product',1140,NULL,NULL,20,NULL,3,380),('Product',1141,NULL,NULL,8,NULL,8,381),('Product',1142,NULL,NULL,8,NULL,9,381),('Product',1143,NULL,NULL,8,NULL,7,381),('Brand',1144,NULL,NULL,15,18,NULL,382),('Brand',1145,NULL,NULL,15,20,NULL,382),('Brand',1146,NULL,NULL,15,22,NULL,382),('Brand',1147,NULL,NULL,9,16,NULL,383),('Brand',1148,NULL,NULL,9,7,NULL,383),('Brand',1149,NULL,NULL,9,25,NULL,383),('OpenPossibility',1150,NULL,'9,95 €',4,NULL,NULL,384),('OpenPossibility',1151,NULL,'9,99 €',4,NULL,NULL,384),('OpenPossibility',1152,NULL,'8,95 €',4,NULL,NULL,384),('Brand',1153,NULL,NULL,17,24,NULL,385),('Brand',1154,NULL,NULL,17,31,NULL,385),('Brand',1155,NULL,NULL,17,20,NULL,385),('Product',1156,NULL,NULL,7,NULL,4,386),('Product',1157,NULL,NULL,7,NULL,6,386),('Product',1158,NULL,NULL,7,NULL,5,386),('Brand',1159,NULL,NULL,9,16,NULL,387),('Brand',1160,NULL,NULL,9,24,NULL,387),('Brand',1161,NULL,NULL,9,11,NULL,387),('Brand',1162,NULL,NULL,15,18,NULL,388),('Brand',1163,NULL,NULL,15,13,NULL,388),('Brand',1164,NULL,NULL,15,7,NULL,388),('Brand',1165,NULL,NULL,16,23,NULL,389),('Brand',1166,NULL,NULL,16,30,NULL,389),('Brand',1167,NULL,NULL,16,7,NULL,389),('Brand',1168,NULL,NULL,1,14,NULL,390),('Brand',1169,NULL,NULL,1,12,NULL,390),('Brand',1170,NULL,NULL,1,17,NULL,390),('Brand',1171,NULL,NULL,16,23,NULL,391),('Brand',1172,NULL,NULL,16,8,NULL,391),('Brand',1173,NULL,NULL,16,16,NULL,391),('Brand',1174,NULL,NULL,11,5,NULL,392),('Brand',1175,NULL,NULL,11,26,NULL,392),('Brand',1176,NULL,NULL,11,8,NULL,392),('Brand',1177,NULL,NULL,19,26,NULL,393),('Brand',1178,NULL,NULL,19,5,NULL,393),('Brand',1179,NULL,NULL,19,17,NULL,393),('Brand',1180,NULL,NULL,12,20,NULL,394),('Brand',1181,NULL,NULL,12,15,NULL,394),('Brand',1182,NULL,NULL,12,28,NULL,394),('Product',1183,NULL,NULL,20,NULL,1,395),('Product',1184,NULL,NULL,20,NULL,2,395),('Product',1185,NULL,NULL,20,NULL,3,395),('Brand',1186,NULL,NULL,14,20,NULL,396),('Brand',1187,NULL,NULL,14,9,NULL,396),('Brand',1188,NULL,NULL,14,17,NULL,396),('Brand',1189,NULL,NULL,19,26,NULL,397),('Brand',1190,NULL,NULL,19,2,NULL,397),('Brand',1191,NULL,NULL,19,11,NULL,397),('Product',1192,NULL,NULL,7,NULL,4,398),('Product',1193,NULL,NULL,7,NULL,5,398),('Product',1194,NULL,NULL,7,NULL,6,398),('OpenPossibility',1195,NULL,'94 %',22,NULL,NULL,399),('OpenPossibility',1196,NULL,'92 %',22,NULL,NULL,399),('OpenPossibility',1197,NULL,'84 %',22,NULL,NULL,399),('Brand',1198,NULL,NULL,11,5,NULL,400),('Brand',1199,NULL,NULL,11,20,NULL,400),('Brand',1200,NULL,NULL,11,28,NULL,400),('OpenPossibility',1201,NULL,'9,95 €',4,NULL,NULL,401),('OpenPossibility',1202,NULL,'8,95 €',4,NULL,NULL,401),('OpenPossibility',1203,NULL,'9,99 €',4,NULL,NULL,401),('Brand',1204,NULL,NULL,13,20,NULL,402),('Brand',1205,NULL,NULL,13,14,NULL,402),('Brand',1206,NULL,NULL,13,31,NULL,402),('Product',1207,NULL,NULL,8,NULL,8,403),('Product',1208,NULL,NULL,8,NULL,7,403),('Product',1209,NULL,NULL,8,NULL,9,403),('Brand',1210,NULL,NULL,13,20,NULL,404),('Brand',1211,NULL,NULL,13,5,NULL,404),('Brand',1212,NULL,NULL,13,2,NULL,404),('Brand',1213,NULL,NULL,19,26,NULL,405),('Brand',1214,NULL,NULL,19,18,NULL,405),('Brand',1215,NULL,NULL,19,24,NULL,405),('Brand',1216,NULL,NULL,12,20,NULL,406),('Brand',1217,NULL,NULL,12,26,NULL,406),('Brand',1218,NULL,NULL,12,17,NULL,406),('Brand',1219,NULL,NULL,14,20,NULL,407),('Brand',1220,NULL,NULL,14,22,NULL,407),('Brand',1221,NULL,NULL,14,14,NULL,407),('Brand',1222,NULL,NULL,1,14,NULL,408),('Brand',1223,NULL,NULL,1,3,NULL,408),('Brand',1224,NULL,NULL,1,19,NULL,408),('Product',1225,NULL,NULL,20,NULL,1,409),('Product',1226,NULL,NULL,20,NULL,2,409),('Product',1227,NULL,NULL,20,NULL,3,409),('Product',1228,NULL,NULL,8,NULL,8,410),('Product',1229,NULL,NULL,8,NULL,9,410),('Product',1230,NULL,NULL,8,NULL,7,410),('Brand',1231,NULL,NULL,6,21,NULL,411),('Brand',1232,NULL,NULL,6,27,NULL,411),('Brand',1233,NULL,NULL,6,16,NULL,411),('Brand',1234,NULL,NULL,21,29,NULL,412),('Brand',1235,NULL,NULL,21,3,NULL,412),('Brand',1236,NULL,NULL,21,31,NULL,412),('Brand',1237,NULL,NULL,10,5,NULL,413),('Brand',1238,NULL,NULL,10,10,NULL,413),('Brand',1239,NULL,NULL,10,19,NULL,413),('Brand',1240,NULL,NULL,13,20,NULL,414),('Brand',1241,NULL,NULL,13,19,NULL,414),('Brand',1242,NULL,NULL,13,9,NULL,414),('Product',1243,NULL,NULL,20,NULL,1,415),('Product',1244,NULL,NULL,20,NULL,3,415),('Product',1245,NULL,NULL,20,NULL,2,415),('Brand',1246,NULL,NULL,10,5,NULL,416),('Brand',1247,NULL,NULL,10,19,NULL,416),('Brand',1248,NULL,NULL,10,24,NULL,416),('Product',1249,NULL,NULL,7,NULL,4,417),('Product',1250,NULL,NULL,7,NULL,6,417),('Product',1251,NULL,NULL,7,NULL,5,417),('Brand',1252,NULL,NULL,16,23,NULL,418),('Brand',1253,NULL,NULL,16,21,NULL,418),('Brand',1254,NULL,NULL,16,30,NULL,418),('Brand',1255,NULL,NULL,6,21,NULL,419),('Brand',1256,NULL,NULL,6,2,NULL,419),('Brand',1257,NULL,NULL,6,24,NULL,419),('Product',1258,NULL,NULL,8,NULL,8,420),('Product',1259,NULL,NULL,8,NULL,7,420),('Product',1260,NULL,NULL,8,NULL,9,420),('Brand',1261,NULL,NULL,18,25,NULL,421),('Brand',1262,NULL,NULL,18,23,NULL,421),('Brand',1263,NULL,NULL,18,16,NULL,421),('OpenPossibility',1264,NULL,'Vivre ici',23,NULL,NULL,422),('OpenPossibility',1265,NULL,'Aller d\'ici',23,NULL,NULL,422),('OpenPossibility',1266,NULL,'Venir d\'ici',23,NULL,NULL,422),('Brand',1267,NULL,NULL,1,14,NULL,423),('Brand',1268,NULL,NULL,1,27,NULL,423),('Brand',1269,NULL,NULL,1,22,NULL,423),('Product',1270,NULL,NULL,8,NULL,8,424),('Product',1271,NULL,NULL,8,NULL,7,424),('Product',1272,NULL,NULL,8,NULL,9,424),('Brand',1273,NULL,NULL,21,29,NULL,425),('Brand',1274,NULL,NULL,21,7,NULL,425),('Brand',1275,NULL,NULL,21,23,NULL,425),('Brand',1276,NULL,NULL,15,18,NULL,426),('Brand',1277,NULL,NULL,15,30,NULL,426),('Brand',1278,NULL,NULL,15,5,NULL,426),('Product',1279,NULL,NULL,7,NULL,4,427),('Product',1280,NULL,NULL,7,NULL,6,427),('Product',1281,NULL,NULL,7,NULL,5,427),('Brand',1282,NULL,NULL,2,28,NULL,428),('Brand',1283,NULL,NULL,2,14,NULL,428),('Brand',1284,NULL,NULL,2,7,NULL,428),('Brand',1285,NULL,NULL,3,15,NULL,429),('Brand',1286,NULL,NULL,3,7,NULL,429),('Brand',1287,NULL,NULL,3,11,NULL,429),('Brand',1288,NULL,NULL,17,24,NULL,430),('Brand',1289,NULL,NULL,17,15,NULL,430),('Brand',1290,NULL,NULL,17,23,NULL,430),('Brand',1291,NULL,NULL,12,20,NULL,431),('Brand',1292,NULL,NULL,12,14,NULL,431),('Brand',1293,NULL,NULL,12,25,NULL,431),('Brand',1294,NULL,NULL,21,29,NULL,432),('Brand',1295,NULL,NULL,21,21,NULL,432),('Brand',1296,NULL,NULL,21,18,NULL,432),('OpenPossibility',1297,NULL,'Vivre ici',23,NULL,NULL,433),('OpenPossibility',1298,NULL,'Aller d\'ici',23,NULL,NULL,433),('OpenPossibility',1299,NULL,'Venir d\'ici',23,NULL,NULL,433),('Brand',1300,NULL,NULL,11,5,NULL,434),('Brand',1301,NULL,NULL,11,6,NULL,434),('Brand',1302,NULL,NULL,11,20,NULL,434),('Brand',1303,NULL,NULL,2,28,NULL,435),('Brand',1304,NULL,NULL,2,29,NULL,435),('Brand',1305,NULL,NULL,2,10,NULL,435),('Brand',1306,NULL,NULL,17,24,NULL,436),('Brand',1307,NULL,NULL,17,19,NULL,436),('Brand',1308,NULL,NULL,17,20,NULL,436),('Brand',1309,NULL,NULL,14,20,NULL,437),('Brand',1310,NULL,NULL,14,26,NULL,437),('Brand',1311,NULL,NULL,14,13,NULL,437),('Brand',1312,NULL,NULL,1,14,NULL,438),('Brand',1313,NULL,NULL,1,25,NULL,438),('Brand',1314,NULL,NULL,1,6,NULL,438),('Brand',1315,NULL,NULL,6,21,NULL,439),('Brand',1316,NULL,NULL,6,25,NULL,439),('Brand',1317,NULL,NULL,6,19,NULL,439),('Brand',1318,NULL,NULL,3,15,NULL,440),('Brand',1319,NULL,NULL,3,21,NULL,440),('Brand',1320,NULL,NULL,3,1,NULL,440),('Brand',1321,NULL,NULL,2,28,NULL,441),('Brand',1322,NULL,NULL,2,15,NULL,441),('Brand',1323,NULL,NULL,2,17,NULL,441),('Product',1324,NULL,NULL,8,NULL,8,442),('Product',1325,NULL,NULL,8,NULL,9,442),('Product',1326,NULL,NULL,8,NULL,7,442),('Brand',1327,NULL,NULL,14,20,NULL,443),('Brand',1328,NULL,NULL,14,24,NULL,443),('Brand',1329,NULL,NULL,14,18,NULL,443),('OpenPossibility',1330,NULL,'9,95 €',4,NULL,NULL,444),('OpenPossibility',1331,NULL,'9,99 €',4,NULL,NULL,444),('OpenPossibility',1332,NULL,'8,95 €',4,NULL,NULL,444),('OpenPossibility',1333,NULL,'9,95 €',4,NULL,NULL,445),('OpenPossibility',1334,NULL,'9,99 €',4,NULL,NULL,445),('OpenPossibility',1335,NULL,'8,95 €',4,NULL,NULL,445),('OpenPossibility',1336,NULL,'10000 kilomètres',5,NULL,NULL,446),('OpenPossibility',1337,NULL,'12000 kilomètres',5,NULL,NULL,446),('OpenPossibility',1338,NULL,'8000 kilomètres',5,NULL,NULL,446),('Brand',1339,NULL,NULL,19,26,NULL,447),('Brand',1340,NULL,NULL,19,15,NULL,447),('Brand',1341,NULL,NULL,19,2,NULL,447),('Product',1342,NULL,NULL,7,NULL,4,448),('Product',1343,NULL,NULL,7,NULL,5,448),('Product',1344,NULL,NULL,7,NULL,6,448),('Brand',1345,NULL,NULL,11,5,NULL,449),('Brand',1346,NULL,NULL,11,11,NULL,449),('Brand',1347,NULL,NULL,11,20,NULL,449),('Brand',1348,NULL,NULL,13,20,NULL,450),('Brand',1349,NULL,NULL,13,7,NULL,450),('Brand',1350,NULL,NULL,13,19,NULL,450),('Brand',1369,NULL,NULL,2,28,NULL,457),('Brand',1370,NULL,NULL,2,7,NULL,457),('Brand',1371,NULL,NULL,2,18,NULL,457),('Brand',1372,NULL,NULL,1,14,NULL,458),('Brand',1373,NULL,NULL,1,7,NULL,458),('Brand',1374,NULL,NULL,1,22,NULL,458),('Brand',1375,NULL,NULL,15,18,NULL,459),('Brand',1376,NULL,NULL,15,29,NULL,459),('Brand',1377,NULL,NULL,15,6,NULL,459),('Brand',1378,NULL,NULL,14,20,NULL,460),('Brand',1379,NULL,NULL,14,10,NULL,460),('Brand',1380,NULL,NULL,14,17,NULL,460),('OpenPossibility',1381,NULL,'10000 kilomètres',5,NULL,NULL,461),('OpenPossibility',1382,NULL,'12000 kilomètres',5,NULL,NULL,461),('OpenPossibility',1383,NULL,'8000 kilomètres',5,NULL,NULL,461),('Product',1384,NULL,NULL,20,NULL,1,462),('Product',1385,NULL,NULL,20,NULL,2,462),('Product',1386,NULL,NULL,20,NULL,3,462),('Brand',1387,NULL,NULL,21,29,NULL,463),('Brand',1388,NULL,NULL,21,7,NULL,463),('Brand',1389,NULL,NULL,21,25,NULL,463),('Brand',1390,NULL,NULL,14,20,NULL,464),('Brand',1391,NULL,NULL,14,10,NULL,464),('Brand',1392,NULL,NULL,14,5,NULL,464),('Brand',1393,NULL,NULL,18,25,NULL,465),('Brand',1394,NULL,NULL,18,8,NULL,465),('Brand',1395,NULL,NULL,18,12,NULL,465),('Brand',1396,NULL,NULL,1,14,NULL,466),('Brand',1397,NULL,NULL,1,3,NULL,466),('Brand',1398,NULL,NULL,1,24,NULL,466),('Brand',1399,NULL,NULL,2,28,NULL,467),('Brand',1400,NULL,NULL,2,25,NULL,467),('Brand',1401,NULL,NULL,2,1,NULL,467),('Brand',1402,NULL,NULL,10,5,NULL,468),('Brand',1403,NULL,NULL,10,29,NULL,468),('Brand',1404,NULL,NULL,10,16,NULL,468),('Brand',1405,NULL,NULL,3,15,NULL,469),('Brand',1406,NULL,NULL,3,18,NULL,469),('Brand',1407,NULL,NULL,3,7,NULL,469),('Brand',1408,NULL,NULL,11,5,NULL,470),('Brand',1409,NULL,NULL,11,29,NULL,470),('Brand',1410,NULL,NULL,11,9,NULL,470),('Brand',1411,NULL,NULL,15,18,NULL,471),('Brand',1412,NULL,NULL,15,24,NULL,471),('Brand',1413,NULL,NULL,15,25,NULL,471),('OpenPossibility',1414,NULL,'94 %',22,NULL,NULL,472),('OpenPossibility',1415,NULL,'92 %',22,NULL,NULL,472),('OpenPossibility',1416,NULL,'84 %',22,NULL,NULL,472),('Brand',1417,NULL,NULL,13,20,NULL,473),('Brand',1418,NULL,NULL,13,27,NULL,473),('Brand',1419,NULL,NULL,13,25,NULL,473),('Brand',1420,NULL,NULL,12,20,NULL,474),('Brand',1421,NULL,NULL,12,3,NULL,474),('Brand',1422,NULL,NULL,12,2,NULL,474),('Product',1423,NULL,NULL,20,NULL,1,475),('Product',1424,NULL,NULL,20,NULL,2,475),('Product',1425,NULL,NULL,20,NULL,3,475),('Brand',1426,NULL,NULL,9,16,NULL,476),('Brand',1427,NULL,NULL,9,8,NULL,476),('Brand',1428,NULL,NULL,9,27,NULL,476),('Brand',1429,NULL,NULL,17,24,NULL,477),('Brand',1430,NULL,NULL,17,30,NULL,477),('Brand',1431,NULL,NULL,17,6,NULL,477),('Brand',1432,NULL,NULL,3,15,NULL,478),('Brand',1433,NULL,NULL,3,16,NULL,478),('Brand',1434,NULL,NULL,3,2,NULL,478),('Brand',1435,NULL,NULL,6,21,NULL,479),('Brand',1436,NULL,NULL,6,2,NULL,479),('Brand',1437,NULL,NULL,6,25,NULL,479),('Product',1438,NULL,NULL,8,NULL,8,480),('Product',1439,NULL,NULL,8,NULL,7,480),('Product',1440,NULL,NULL,8,NULL,9,480),('OpenPossibility',1441,NULL,'9,95 €',4,NULL,NULL,481),('OpenPossibility',1442,NULL,'8,95 €',4,NULL,NULL,481),('OpenPossibility',1443,NULL,'9,99 €',4,NULL,NULL,481),('Brand',1444,NULL,NULL,2,28,NULL,482),('Brand',1445,NULL,NULL,2,2,NULL,482),('Brand',1446,NULL,NULL,2,26,NULL,482),('Product',1447,NULL,NULL,7,NULL,4,483),('Product',1448,NULL,NULL,7,NULL,5,483),('Product',1449,NULL,NULL,7,NULL,6,483),('Brand',1450,NULL,NULL,21,29,NULL,484),('Brand',1451,NULL,NULL,21,23,NULL,484),('Brand',1452,NULL,NULL,21,24,NULL,484),('OpenPossibility',1453,NULL,'10000 kilomètres',5,NULL,NULL,485),('OpenPossibility',1454,NULL,'12000 kilomètres',5,NULL,NULL,485),('OpenPossibility',1455,NULL,'8000 kilomètres',5,NULL,NULL,485),('Brand',1456,NULL,NULL,3,15,NULL,486),('Brand',1457,NULL,NULL,3,4,NULL,486),('Brand',1458,NULL,NULL,3,18,NULL,486),('Product',1459,NULL,NULL,8,NULL,8,487),('Product',1460,NULL,NULL,8,NULL,9,487),('Product',1461,NULL,NULL,8,NULL,7,487),('Brand',1462,NULL,NULL,18,25,NULL,488),('Brand',1463,NULL,NULL,18,7,NULL,488),('Brand',1464,NULL,NULL,18,14,NULL,488),('Brand',1465,NULL,NULL,10,5,NULL,489),('Brand',1466,NULL,NULL,10,20,NULL,489),('Brand',1467,NULL,NULL,10,2,NULL,489),('OpenPossibility',1468,NULL,'9,95 €',4,NULL,NULL,490),('OpenPossibility',1469,NULL,'9,99 €',4,NULL,NULL,490),('OpenPossibility',1470,NULL,'8,95 €',4,NULL,NULL,490),('OpenPossibility',1471,NULL,'Vivre ici',23,NULL,NULL,491),('OpenPossibility',1472,NULL,'Venir d\'ici',23,NULL,NULL,491),('OpenPossibility',1473,NULL,'Aller d\'ici',23,NULL,NULL,491),('Brand',1474,NULL,NULL,6,21,NULL,492),('Brand',1475,NULL,NULL,6,2,NULL,492),('Brand',1476,NULL,NULL,6,27,NULL,492),('Brand',1477,NULL,NULL,14,20,NULL,493),('Brand',1478,NULL,NULL,14,11,NULL,493),('Brand',1479,NULL,NULL,14,7,NULL,493),('Product',1480,NULL,NULL,8,NULL,8,494),('Product',1481,NULL,NULL,8,NULL,7,494),('Product',1482,NULL,NULL,8,NULL,9,494),('Product',1483,NULL,NULL,20,NULL,1,495),('Product',1484,NULL,NULL,20,NULL,2,495),('Product',1485,NULL,NULL,20,NULL,3,495),('Brand',1486,NULL,NULL,16,23,NULL,496),('Brand',1487,NULL,NULL,16,21,NULL,496),('Brand',1488,NULL,NULL,16,10,NULL,496),('Brand',1489,NULL,NULL,17,24,NULL,497),('Brand',1490,NULL,NULL,17,27,NULL,497),('Brand',1491,NULL,NULL,17,3,NULL,497),('OpenPossibility',1492,NULL,'94 %',22,NULL,NULL,498),('OpenPossibility',1493,NULL,'84 %',22,NULL,NULL,498),('OpenPossibility',1494,NULL,'92 %',22,NULL,NULL,498),('Product',1495,NULL,NULL,8,NULL,8,499),('Product',1496,NULL,NULL,8,NULL,7,499),('Product',1497,NULL,NULL,8,NULL,9,499),('Brand',1498,NULL,NULL,19,26,NULL,500),('Brand',1499,NULL,NULL,19,3,NULL,500),('Brand',1500,NULL,NULL,19,14,NULL,500),('Brand',1501,NULL,NULL,10,5,NULL,501),('Brand',1502,NULL,NULL,10,16,NULL,501),('Brand',1503,NULL,NULL,10,13,NULL,501),('Brand',1504,NULL,NULL,15,18,NULL,502),('Brand',1505,NULL,NULL,15,6,NULL,502),('Brand',1506,NULL,NULL,15,10,NULL,502),('Brand',1507,NULL,NULL,11,5,NULL,503),('Brand',1508,NULL,NULL,11,11,NULL,503),('Brand',1509,NULL,NULL,11,30,NULL,503),('Brand',1510,NULL,NULL,17,24,NULL,504),('Brand',1511,NULL,NULL,17,13,NULL,504),('Brand',1512,NULL,NULL,17,16,NULL,504),('Brand',1513,NULL,NULL,3,15,NULL,505),('Brand',1514,NULL,NULL,3,20,NULL,505),('Brand',1515,NULL,NULL,3,12,NULL,505),('Brand',1516,NULL,NULL,15,18,NULL,506),('Brand',1517,NULL,NULL,15,27,NULL,506),('Brand',1518,NULL,NULL,15,2,NULL,506),('Brand',1519,NULL,NULL,2,28,NULL,507),('Brand',1520,NULL,NULL,2,1,NULL,507),('Brand',1521,NULL,NULL,2,9,NULL,507),('Brand',1522,NULL,NULL,13,20,NULL,508),('Brand',1523,NULL,NULL,13,1,NULL,508),('Brand',1524,NULL,NULL,13,27,NULL,508),('OpenPossibility',1525,NULL,'10000 kilomètres',5,NULL,NULL,509),('OpenPossibility',1526,NULL,'8000 kilomètres',5,NULL,NULL,509),('OpenPossibility',1527,NULL,'12000 kilomètres',5,NULL,NULL,509),('Brand',1528,NULL,NULL,11,5,NULL,510),('Brand',1529,NULL,NULL,11,12,NULL,510),('Brand',1530,NULL,NULL,11,28,NULL,510),('Brand',1531,NULL,NULL,15,18,NULL,511),('Brand',1532,NULL,NULL,15,19,NULL,511),('Brand',1533,NULL,NULL,15,26,NULL,511),('Brand',1534,NULL,NULL,9,16,NULL,512),('Brand',1535,NULL,NULL,9,19,NULL,512),('Brand',1536,NULL,NULL,9,1,NULL,512),('Brand',1537,NULL,NULL,13,20,NULL,513),('Brand',1538,NULL,NULL,13,21,NULL,513),('Brand',1539,NULL,NULL,13,12,NULL,513),('OpenPossibility',1540,NULL,'10000 kilomètres',5,NULL,NULL,514),('OpenPossibility',1541,NULL,'12000 kilomètres',5,NULL,NULL,514),('OpenPossibility',1542,NULL,'8000 kilomètres',5,NULL,NULL,514),('Brand',1543,NULL,NULL,17,24,NULL,515),('Brand',1544,NULL,NULL,17,7,NULL,515),('Brand',1545,NULL,NULL,17,19,NULL,515),('Brand',1546,NULL,NULL,11,5,NULL,516),('Brand',1547,NULL,NULL,11,30,NULL,516),('Brand',1548,NULL,NULL,11,16,NULL,516),('Brand',1549,NULL,NULL,14,20,NULL,517),('Brand',1550,NULL,NULL,14,26,NULL,517),('Brand',1551,NULL,NULL,14,6,NULL,517),('Brand',1552,NULL,NULL,17,24,NULL,518),('Brand',1553,NULL,NULL,17,28,NULL,518),('Brand',1554,NULL,NULL,17,10,NULL,518),('OpenPossibility',1555,NULL,'94 %',22,NULL,NULL,519),('OpenPossibility',1556,NULL,'92 %',22,NULL,NULL,519),('OpenPossibility',1557,NULL,'84 %',22,NULL,NULL,519),('Brand',1558,NULL,NULL,13,20,NULL,520),('Brand',1559,NULL,NULL,13,8,NULL,520),('Brand',1560,NULL,NULL,13,22,NULL,520),('Brand',1561,NULL,NULL,11,5,NULL,521),('Brand',1562,NULL,NULL,11,3,NULL,521),('Brand',1563,NULL,NULL,11,15,NULL,521),('Product',1564,NULL,NULL,8,NULL,8,522),('Product',1565,NULL,NULL,8,NULL,9,522),('Product',1566,NULL,NULL,8,NULL,7,522),('Brand',1567,NULL,NULL,14,20,NULL,523),('Brand',1568,NULL,NULL,14,7,NULL,523),('Brand',1569,NULL,NULL,14,23,NULL,523),('OpenPossibility',1570,NULL,'Vivre ici',23,NULL,NULL,524),('OpenPossibility',1571,NULL,'Aller d\'ici',23,NULL,NULL,524),('OpenPossibility',1572,NULL,'Venir d\'ici',23,NULL,NULL,524),('Brand',1573,NULL,NULL,9,16,NULL,525),('Brand',1574,NULL,NULL,9,12,NULL,525),('Brand',1575,NULL,NULL,9,29,NULL,525),('Brand',1576,NULL,NULL,6,21,NULL,526),('Brand',1577,NULL,NULL,6,22,NULL,526),('Brand',1578,NULL,NULL,6,12,NULL,526),('Brand',1579,NULL,NULL,17,24,NULL,527),('Brand',1580,NULL,NULL,17,7,NULL,527),('Brand',1581,NULL,NULL,17,28,NULL,527),('Product',1582,NULL,NULL,8,NULL,8,528),('Product',1583,NULL,NULL,8,NULL,9,528),('Product',1584,NULL,NULL,8,NULL,7,528),('Brand',1585,NULL,NULL,6,21,NULL,529),('Brand',1586,NULL,NULL,6,6,NULL,529),('Brand',1587,NULL,NULL,6,27,NULL,529),('Brand',1588,NULL,NULL,15,18,NULL,530),('Brand',1589,NULL,NULL,15,6,NULL,530),('Brand',1590,NULL,NULL,15,27,NULL,530),('Brand',1591,NULL,NULL,12,20,NULL,531),('Brand',1592,NULL,NULL,12,25,NULL,531),('Brand',1593,NULL,NULL,12,7,NULL,531),('Brand',1594,NULL,NULL,14,20,NULL,532),('Brand',1595,NULL,NULL,14,23,NULL,532),('Brand',1596,NULL,NULL,14,16,NULL,532),('Product',1597,NULL,NULL,7,NULL,4,533),('Product',1598,NULL,NULL,7,NULL,5,533),('Product',1599,NULL,NULL,7,NULL,6,533),('Brand',1600,NULL,NULL,13,20,NULL,534),('Brand',1601,NULL,NULL,13,3,NULL,534),('Brand',1602,NULL,NULL,13,28,NULL,534),('Brand',1603,NULL,NULL,19,26,NULL,535),('Brand',1604,NULL,NULL,19,9,NULL,535),('Brand',1605,NULL,NULL,19,23,NULL,535),('Brand',1606,NULL,NULL,11,5,NULL,536),('Brand',1607,NULL,NULL,11,18,NULL,536),('Brand',1608,NULL,NULL,11,29,NULL,536),('Product',1609,NULL,NULL,20,NULL,1,537),('Product',1610,NULL,NULL,20,NULL,3,537),('Product',1611,NULL,NULL,20,NULL,2,537),('Brand',1612,NULL,NULL,17,24,NULL,538),('Brand',1613,NULL,NULL,17,10,NULL,538),('Brand',1614,NULL,NULL,17,15,NULL,538),('Brand',1615,NULL,NULL,15,18,NULL,539),('Brand',1616,NULL,NULL,15,5,NULL,539),('Brand',1617,NULL,NULL,15,9,NULL,539),('Brand',1618,NULL,NULL,12,20,NULL,540),('Brand',1619,NULL,NULL,12,22,NULL,540),('Brand',1620,NULL,NULL,12,18,NULL,540),('Brand',1621,NULL,NULL,9,16,NULL,541),('Brand',1622,NULL,NULL,9,10,NULL,541),('Brand',1623,NULL,NULL,9,19,NULL,541),('Brand',1624,NULL,NULL,18,25,NULL,542),('Brand',1625,NULL,NULL,18,11,NULL,542),('Brand',1626,NULL,NULL,18,3,NULL,542),('Product',1627,NULL,NULL,7,NULL,4,543),('Product',1628,NULL,NULL,7,NULL,5,543),('Product',1629,NULL,NULL,7,NULL,6,543),('Brand',1630,NULL,NULL,21,29,NULL,544),('Brand',1631,NULL,NULL,21,24,NULL,544),('Brand',1632,NULL,NULL,21,31,NULL,544),('Brand',1633,NULL,NULL,13,20,NULL,545),('Brand',1634,NULL,NULL,13,1,NULL,545),('Brand',1635,NULL,NULL,13,23,NULL,545),('Brand',1636,NULL,NULL,17,24,NULL,546),('Brand',1637,NULL,NULL,17,15,NULL,546),('Brand',1638,NULL,NULL,17,30,NULL,546),('Product',1639,NULL,NULL,20,NULL,1,547),('Product',1640,NULL,NULL,20,NULL,3,547),('Product',1641,NULL,NULL,20,NULL,2,547),('Brand',1642,NULL,NULL,14,20,NULL,548),('Brand',1643,NULL,NULL,14,7,NULL,548),('Brand',1644,NULL,NULL,14,12,NULL,548),('OpenPossibility',1645,NULL,'Vivre ici',23,NULL,NULL,549),('OpenPossibility',1646,NULL,'Venir d\'ici',23,NULL,NULL,549),('OpenPossibility',1647,NULL,'Aller d\'ici',23,NULL,NULL,549),('Brand',1648,NULL,NULL,17,24,NULL,550),('Brand',1649,NULL,NULL,17,10,NULL,550),('Brand',1650,NULL,NULL,17,3,NULL,550),('Brand',1651,NULL,NULL,6,21,NULL,551),('Brand',1652,NULL,NULL,6,19,NULL,551),('Brand',1653,NULL,NULL,6,10,NULL,551),('Brand',1654,NULL,NULL,2,28,NULL,552),('Brand',1655,NULL,NULL,2,19,NULL,552),('Brand',1656,NULL,NULL,2,26,NULL,552),('OpenPossibility',1657,NULL,'Vivre ici',23,NULL,NULL,553),('OpenPossibility',1658,NULL,'Venir d\'ici',23,NULL,NULL,553),('OpenPossibility',1659,NULL,'Aller d\'ici',23,NULL,NULL,553),('Brand',1660,NULL,NULL,21,29,NULL,554),('Brand',1661,NULL,NULL,21,24,NULL,554),('Brand',1662,NULL,NULL,21,1,NULL,554),('OpenPossibility',1663,NULL,'94 %',22,NULL,NULL,555),('OpenPossibility',1664,NULL,'84 %',22,NULL,NULL,555),('OpenPossibility',1665,NULL,'92 %',22,NULL,NULL,555),('Brand',1666,NULL,NULL,1,14,NULL,556),('Brand',1667,NULL,NULL,1,19,NULL,556),('Brand',1668,NULL,NULL,1,15,NULL,556),('Brand',1669,NULL,NULL,17,24,NULL,557),('Brand',1670,NULL,NULL,17,2,NULL,557),('Brand',1671,NULL,NULL,17,17,NULL,557),('Brand',1672,NULL,NULL,12,20,NULL,558),('Brand',1673,NULL,NULL,12,27,NULL,558),('Brand',1674,NULL,NULL,12,25,NULL,558),('OpenPossibility',1675,NULL,'94 %',22,NULL,NULL,559),('OpenPossibility',1676,NULL,'84 %',22,NULL,NULL,559),('OpenPossibility',1677,NULL,'92 %',22,NULL,NULL,559),('Brand',1678,NULL,NULL,13,20,NULL,560),('Brand',1679,NULL,NULL,13,2,NULL,560),('Brand',1680,NULL,NULL,13,18,NULL,560),('Brand',1681,NULL,NULL,9,16,NULL,561),('Brand',1682,NULL,NULL,9,22,NULL,561),('Brand',1683,NULL,NULL,9,27,NULL,561),('Brand',1684,NULL,NULL,15,18,NULL,562),('Brand',1685,NULL,NULL,15,15,NULL,562),('Brand',1686,NULL,NULL,15,23,NULL,562),('Brand',1687,NULL,NULL,19,26,NULL,563),('Brand',1688,NULL,NULL,19,20,NULL,563),('Brand',1689,NULL,NULL,19,19,NULL,563),('Brand',1690,NULL,NULL,1,14,NULL,564),('Brand',1691,NULL,NULL,1,10,NULL,564),('Brand',1692,NULL,NULL,1,30,NULL,564);
/*!40000 ALTER TABLE `possibility` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ad_price` double DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `logo` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `public_price` double DEFAULT NULL,
  `brand_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_f652ed6c649e4467acdf140b7a4` (`brand_id`),
  KEY `FK_c876560f87924ffeb79b856992c` (`brand_id`),
  KEY `FK_fdaba352e7f242889c4b845c429` (`brand_id`),
  KEY `FK_8dd2b4dc08ba470d980b895f14e` (`brand_id`),
  KEY `FK_0f9b5771003649e2bd7bcf8f6c7` (`brand_id`),
  KEY `FK_5f2f70d68bcb4f9aaf69c634b41` (`brand_id`),
  KEY `FK_9dfeaf7370584bdd90c2b3e20d1` (`brand_id`),
  KEY `FK_938062aeea1b49eab6a3404b03e` (`brand_id`),
  KEY `FK_b851653a560a42cd88901b9821e` (`brand_id`),
  KEY `FK_a551c2d68d134e3aa48acd1e7f0` (`brand_id`),
  KEY `FK_4209afcfa8214c29bd0bed27a27` (`brand_id`),
  KEY `FK_c33325c47ff84186937b1d8cf30` (`brand_id`),
  CONSTRAINT `FK_0f9b5771003649e2bd7bcf8f6c7` FOREIGN KEY (`brand_id`) REFERENCES `brand` (`id`),
  CONSTRAINT `FK_5f2f70d68bcb4f9aaf69c634b41` FOREIGN KEY (`brand_id`) REFERENCES `brand` (`id`),
  CONSTRAINT `FK_8dd2b4dc08ba470d980b895f14e` FOREIGN KEY (`brand_id`) REFERENCES `brand` (`id`),
  CONSTRAINT `FK_938062aeea1b49eab6a3404b03e` FOREIGN KEY (`brand_id`) REFERENCES `brand` (`id`),
  CONSTRAINT `FK_9dfeaf7370584bdd90c2b3e20d1` FOREIGN KEY (`brand_id`) REFERENCES `brand` (`id`),
  CONSTRAINT `FK_a551c2d68d134e3aa48acd1e7f0` FOREIGN KEY (`brand_id`) REFERENCES `brand` (`id`),
  CONSTRAINT `FK_b851653a560a42cd88901b9821e` FOREIGN KEY (`brand_id`) REFERENCES `brand` (`id`),
  CONSTRAINT `FK_c33325c47ff84186937b1d8cf30` FOREIGN KEY (`brand_id`) REFERENCES `brand` (`id`),
  CONSTRAINT `FK_c876560f87924ffeb79b856992c` FOREIGN KEY (`brand_id`) REFERENCES `brand` (`id`),
  CONSTRAINT `FK_f652ed6c649e4467acdf140b7a4` FOREIGN KEY (`brand_id`) REFERENCES `brand` (`id`),
  CONSTRAINT `FK_fdaba352e7f242889c4b845c429` FOREIGN KEY (`brand_id`) REFERENCES `brand` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,NULL,'','svm.jpg','Sucre vanillé de Madagascar',NULL,27),(2,NULL,'','sv.jpg','Sucre vanillé',NULL,27),(3,NULL,'','sve.jpg','Sucre vanilliné',NULL,27),(4,NULL,'',NULL,'Peugeot 508 SW',NULL,22),(5,NULL,'',NULL,'Peugeot 508',NULL,22),(6,NULL,'',NULL,'Peugeot 307',NULL,22),(7,NULL,'','ds3.jpg','DS3',NULL,3),(8,NULL,'','c3.jpg','C3',NULL,3),(9,NULL,'','nc4.jpg','C4',NULL,3);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reduction`
--

DROP TABLE IF EXISTS `reduction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `reduction` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `percentage_value` double DEFAULT NULL,
  `reduction_code` varchar(255) DEFAULT NULL,
  `value` double DEFAULT NULL,
  `partener_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_03f67b4fb4c44561a12947117b3` (`partener_id`),
  KEY `FK_1bac7258f3b74f70920c0e08d37` (`partener_id`),
  KEY `FK_c51203c1cfe34f539074bf032d5` (`partener_id`),
  KEY `FK_7bc0f943546a408e9d30c47477e` (`partener_id`),
  KEY `FK_50b86c73739b40a2a266c5a504c` (`partener_id`),
  KEY `FK_92bd7d593c094eb9bbf15c5f4f7` (`partener_id`),
  KEY `FK_2db886a7510446749746dbd0a63` (`partener_id`),
  KEY `FK_22cca25fd59e4f2d8f61aa712af` (`partener_id`),
  KEY `FK_027d676f7799412e8147efef61c` (`partener_id`),
  KEY `FK_8bd3e60b15eb42efa070fd59ae5` (`partener_id`),
  KEY `FK_2eabde1407ea4e28b8363c40571` (`partener_id`),
  KEY `FK_eac01775b301439996f834b1216` (`partener_id`),
  CONSTRAINT `FK_027d676f7799412e8147efef61c` FOREIGN KEY (`partener_id`) REFERENCES `partener` (`id`),
  CONSTRAINT `FK_03f67b4fb4c44561a12947117b3` FOREIGN KEY (`partener_id`) REFERENCES `partener` (`id`),
  CONSTRAINT `FK_1bac7258f3b74f70920c0e08d37` FOREIGN KEY (`partener_id`) REFERENCES `partener` (`id`),
  CONSTRAINT `FK_22cca25fd59e4f2d8f61aa712af` FOREIGN KEY (`partener_id`) REFERENCES `partener` (`id`),
  CONSTRAINT `FK_2db886a7510446749746dbd0a63` FOREIGN KEY (`partener_id`) REFERENCES `partener` (`id`),
  CONSTRAINT `FK_50b86c73739b40a2a266c5a504c` FOREIGN KEY (`partener_id`) REFERENCES `partener` (`id`),
  CONSTRAINT `FK_7bc0f943546a408e9d30c47477e` FOREIGN KEY (`partener_id`) REFERENCES `partener` (`id`),
  CONSTRAINT `FK_8bd3e60b15eb42efa070fd59ae5` FOREIGN KEY (`partener_id`) REFERENCES `partener` (`id`),
  CONSTRAINT `FK_92bd7d593c094eb9bbf15c5f4f7` FOREIGN KEY (`partener_id`) REFERENCES `partener` (`id`),
  CONSTRAINT `FK_c51203c1cfe34f539074bf032d5` FOREIGN KEY (`partener_id`) REFERENCES `partener` (`id`),
  CONSTRAINT `FK_eac01775b301439996f834b1216` FOREIGN KEY (`partener_id`) REFERENCES `partener` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reduction`
--

LOCK TABLES `reduction` WRITE;
/*!40000 ALTER TABLE `reduction` DISABLE KEYS */;
/*!40000 ALTER TABLE `reduction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(64) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (-2,'Default role for all Users','ROLE_USER'),(-1,'Administrator role (can edit Users)','ROLE_ADMIN');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_role` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FK_89a62043dbbe43b383e52b84492` (`role_id`),
  KEY `FK_a330a46e99b443f5ad634630985` (`user_id`),
  CONSTRAINT `FK_89a62043dbbe43b383e52b84492` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `FK_a330a46e99b443f5ad634630985` FOREIGN KEY (`user_id`) REFERENCES `player` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (1,-2),(2,-2),(3,-2);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `video_actor`
--

DROP TABLE IF EXISTS `video_actor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `video_actor` (
  `video_id` bigint(20) NOT NULL,
  `actor_id` bigint(20) NOT NULL,
  KEY `FK_1f9cfb4813b54968a8d96ec8ce8` (`actor_id`),
  KEY `FK_2b92724cc91f4779a636ea2d913` (`video_id`),
  CONSTRAINT `FK_2b92724cc91f4779a636ea2d913` FOREIGN KEY (`video_id`) REFERENCES `media` (`id`),
  CONSTRAINT `FK_1f9cfb4813b54968a8d96ec8ce8` FOREIGN KEY (`actor_id`) REFERENCES `person` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `video_actor`
--

LOCK TABLES `video_actor` WRITE;
/*!40000 ALTER TABLE `video_actor` DISABLE KEYS */;
/*!40000 ALTER TABLE `video_actor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `view_media`
--

DROP TABLE IF EXISTS `view_media`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `view_media` (
  `view_id` bigint(20) NOT NULL,
  `media_id` bigint(20) NOT NULL,
  KEY `FK_4150e407e0df4544b7c273b993a` (`media_id`),
  KEY `FK_76dffda6442649cc8f7e357bfd5` (`view_id`),
  CONSTRAINT `FK_76dffda6442649cc8f7e357bfd5` FOREIGN KEY (`view_id`) REFERENCES `viewed_media` (`id`),
  CONSTRAINT `FK_4150e407e0df4544b7c273b993a` FOREIGN KEY (`media_id`) REFERENCES `media` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `view_media`
--

LOCK TABLES `view_media` WRITE;
/*!40000 ALTER TABLE `view_media` DISABLE KEYS */;
/*!40000 ALTER TABLE `view_media` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `viewed_media`
--

DROP TABLE IF EXISTS `viewed_media`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `viewed_media` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `viewed` datetime DEFAULT NULL,
  `is_win_view` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `viewed_media`
--

LOCK TABLES `viewed_media` WRITE;
/*!40000 ALTER TABLE `viewed_media` DISABLE KEYS */;
/*!40000 ALTER TABLE `viewed_media` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2013-11-21 17:51:08
